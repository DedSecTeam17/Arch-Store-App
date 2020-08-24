package com.example.arch_store.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.arch_store.R
import com.example.arch_store.databinding.FragmentSignInBinding
import com.example.arch_store.view_models.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.afollestad.vvalidator.form
import com.example.arch_store.models.User
import com.example.arch_store.utils.DataState
import com.example.arch_store.utils.UserSession
import com.example.arch_store.view_models.AuthStateEvent
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SignInFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var session: UserSession
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment

        val binding: FragmentSignInBinding =
            DataBindingUtil.inflate<FragmentSignInBinding>(
                inflater,
                R.layout.fragment_sign_in,
                container,
                false
            );



        binding.createNewAccount.setOnClickListener {
            println("New account...")
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment);


        }
        subscribeObservers(binding);
        formValidationAndSubmit(binding)

        return binding.root;
    }


    private fun formValidationAndSubmit(binding: FragmentSignInBinding) {
        form {
            input(binding.identifier) {
                isNotEmpty()
                length().atLeast(6)


            }
            input(binding.password) {
                isNotEmpty()
                length().atLeast(6)
            }
            submitWith(binding.logIn) { result ->
                if (result.success()) {
                    viewModel.setStateEvent(
                        AuthStateEvent.SignIning,
                        identifier = binding.identifier.text.toString(),
                        password = binding.password.text.toString(),
                        userName = "",
                        email = ""
                    );
                }
                // this block is only called if form is valid.
                // do something with a valid form state.
            }
        }
    }

    private fun subscribeObservers(binding: FragmentSignInBinding) {
        viewModel.user.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<User> -> {
                    session.setUserData(dataState.data)
                    binding.loading.visibility = View.GONE

                    findNavController().navigate(R.id.action_signInFragment_to_home_dest);
//                    save this to shared pref
                }
                is DataState.Error -> {
                    println("Error login.")
                    binding.loading.visibility = View.GONE
                    Toast.makeText(
                        requireActivity(),
                        "User name or password wrong",
                        Toast.LENGTH_LONG
                    ).show();


                }
                is DataState.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                    println("Loading...")
                }
            }
        })
    }

}