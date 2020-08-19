package com.example.arch_store.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.arch_store.R
import com.example.arch_store.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

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

        binding.logIn.setOnClickListener {
            println("Login...")
            findNavController().navigate(R.id.action_signInFragment_to_home_dest);
        }

        binding.createNewAccount.setOnClickListener {
            println("New account...")
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment);


        }

        return binding.root;
    }


}