package com.example.mytrashyapp.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.auth0.android.jwt.JWT
import com.example.mytrashyapp.R

import com.example.mytrashyapp.databinding.FragmentProfileBinding
import com.example.mytrashyapp.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Profile : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getUserEmail.asLiveData().observe(viewLifecycleOwner, Observer { token ->
            var email: String?
            if (token != null) {
                email = JWT(token).getClaim("sub").asString()
            } else {
                email = "Email not found"
            }
            binding.textEmail.text = email
        })

        binding.buttonGoToLogin.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_profile_to_loginFragment)
        }
    }



}