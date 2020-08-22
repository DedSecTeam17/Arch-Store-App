package com.example.arch_store.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arch_store.R
import com.example.arch_store.adapters.FavListAdapter
import com.example.arch_store.adapters.FavListListener
import com.example.arch_store.databinding.FragmentEditProfileBinding
import com.example.arch_store.databinding.FragmentFavourtiesBinding

class FavourtiesFragment : Fragment(), FavListListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentFavourtiesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favourties, container, false)

        var favListAdapter: FavListAdapter = FavListAdapter(
            products = arrayListOf("", "", "", ""),
            ctx = activity!!.applicationContext,
            favListListener = this
        )
        binding.favProducts.apply {
            adapter = favListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        return binding.root
    }

    override fun onItemClicked() {


        findNavController().navigate(R.id.action_favourtiesFragment_to_productDetailFragment)
    }


}