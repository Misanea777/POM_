package com.example.mytrashyapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.example.mytrashyapp.ui.MainActivity
import com.example.mytrashyapp.databinding.FragmentLoginBinding
import com.example.mytrashyapp.data.network.AuthApi
import com.example.mytrashyapp.data.network.Resource
import com.example.mytrashyapp.data.repository.AuthRep
import com.example.mytrashyapp.ui.base.BaseFragment
import com.example.mytrashyapp.ui.utils.enable
import com.example.mytrashyapp.ui.utils.startNewActivity
import com.example.mytrashyapp.ui.utils.visible


class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRep>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressBar.visible(false)
        binding.buttonLogin.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(false)
            when(it) {
                is Resource.Success -> {

                        viewModel.saveAuthToken(it.value.jwt)
                        requireActivity().startNewActivity(MainActivity::class.java)

                }
                is Resource.Failure ->
                    Toast.makeText(requireContext(), "Login failure", Toast.LENGTH_LONG).show()
            }
        })

        binding.editTextTextPassword.addTextChangedListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            binding.buttonLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.buttonLogin.setOnClickListener{
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val pswd = binding.editTextTextPassword.text.toString().trim()
            binding.progressBar.visible(true)
            viewModel.login(email, pswd)
        }
        
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRep(remoteDataSrc.buildApi(AuthApi::class.java), userPreferences)
}

