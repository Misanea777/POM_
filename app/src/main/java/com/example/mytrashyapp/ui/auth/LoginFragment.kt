package com.example.mytrashyapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.mytrashyapp.databinding.FragmentLoginBinding
import com.example.mytrashyapp.data.network.AuthApi
import com.example.mytrashyapp.data.network.Resource
import com.example.mytrashyapp.data.network.repository.AuthRep
import com.example.mytrashyapp.ui.base.BaseFragment
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRep>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    lifecycleScope.launch{
                        userPreferences.saveAuthToken(it.value.toString())
                    }
                }
                is Resource.Failure ->
                    Toast.makeText(requireContext(), "Login failure", Toast.LENGTH_LONG).show()
            }
        })

        binding.buttonLogin.setOnClickListener{
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val pswd = binding.editTextTextPassword.text.toString().trim()
            viewModel.login(email, pswd)
        }
        
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRep(remoteDataSrc.buildApi(AuthApi::class.java))
}

