package com.example.arch_store.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.afollestad.vvalidator.form
import com.example.arch_store.R
import com.example.arch_store.databinding.FragmentSignInBinding
import com.example.arch_store.databinding.FragmentSignUpBinding
import com.example.arch_store.models.User
import com.example.arch_store.utils.DataState
import com.example.arch_store.utils.UserSession
import com.example.arch_store.view_models.AuthStateEvent
import com.example.arch_store.view_models.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SignUpFragment : Fragment() {
    @ExperimentalCoroutinesApi
    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var session: UserSession


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var binding: FragmentSignUpBinding = DataBindingUtil.inflate<FragmentSignUpBinding>(
            inflater, R.layout.fragment_sign_up, container, false
        );

        subscribeObservers(binding)

        formValidationAndSubmit(binding)
        binding.alreadyHavAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        return binding.root
    }

    private fun formValidationAndSubmit(binding: FragmentSignUpBinding) {
        form {
            input(binding.email) {
                isNotEmpty()
                isEmail()

            }
            input(binding.password) {
                isNotEmpty()
                length().atLeast(6)
            }
            input(binding.userName) {
                isNotEmpty()
                length().atLeast(6)
            }
            submitWith(binding.signUp) { result ->
                if (result.success()) {
                    viewModel.setStateEvent(
                        AuthStateEvent.SignUp,
                        identifier = "",
                        password = binding.password.text.toString(),
                        userName = binding.userName.text.toString(),
                        email = binding.email.text.toString()
                    );
                }
                // this block is only called if form is valid.
                // do something with a valid form state.
            }
        }
    }

    private fun subscribeObservers(binding: FragmentSignUpBinding) {
        viewModel.user.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<User> -> {
                    session.setUserData(dataState.data)
                    binding.loading.visibility = View.GONE

                    findNavController().navigate(R.id.action_signUpFragment_to_home_dest)
//                    save this to shared pref
                }
                is DataState.Error -> {
                    println("Error login.")
                    binding.loading.visibility = View.GONE
                    Toast.makeText(requireActivity(), "User name used", Toast.LENGTH_LONG).show();


                }
                is DataState.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                    println("Loading...")
                }
            }
        })
    }

}