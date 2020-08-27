package com.example.arch_store.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.arch_store.R
import com.example.arch_store.databinding.FragmentOrderSuccessBinding
import com.example.arch_store.databinding.FragmentSignInBinding

class OrderSuccessFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentOrderSuccessBinding =
            DataBindingUtil.inflate<FragmentOrderSuccessBinding>(
                inflater,
                R.layout.fragment_order_success,
                container,
                false
            );

        binding.toMyOrder.setOnClickListener {
            findNavController().navigate(
                OrderSuccessFragmentDirections.actionOrderSuccessFragmentToOrdersFragment(
                    true
                )
            )
        }

        return binding.root
    }

}