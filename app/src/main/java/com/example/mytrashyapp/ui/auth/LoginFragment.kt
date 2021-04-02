package com.example.mytrashyapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.mytrashyapp.R
import com.example.mytrashyapp.databinding.FragmentLoginBinding
import com.example.mytrashyapp.network.AuthApi
import com.example.mytrashyapp.network.RemoteDataSrc
import com.example.mytrashyapp.network.Resource
import com.example.mytrashyapp.network.repository.AuthRep
import com.example.mytrashyapp.ui.base.BaseFragment


class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRep>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
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

