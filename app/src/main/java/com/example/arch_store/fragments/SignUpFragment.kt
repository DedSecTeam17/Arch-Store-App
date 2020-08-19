package com.example.arch_store.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.arch_store.R
import com.example.arch_store.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var biding: FragmentSignUpBinding = DataBindingUtil.inflate<FragmentSignUpBinding>(
            inflater, R.layout.fragment_sign_up, container, false
        );


        biding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_home_dest)

        }
        biding.alreadyHavAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)

        }

        return biding.root
    }


}