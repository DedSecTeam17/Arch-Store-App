package com.example.arch_store.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.arch_store.R
import com.example.arch_store.databinding.FragmentProfileBinding
import com.example.arch_store.utils.UserSession
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    @Inject
    lateinit var session: UserSession

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        binding.userName.text = session.getUserData().UserName

        binding.editProfilePic.setOnClickListener {
            findNavController().navigate(R.id.action_profile_dest_to_editProfileFragment)
        }
        binding.favProducts.setOnClickListener {
            findNavController().navigate(R.id.action_profile_dest_to_favourtiesFragment)

        }
        binding.myOrders.setOnClickListener {
            findNavController().navigate(R.id.action_profile_dest_to_ordersFragment)

        }
        binding.logOut.setOnClickListener {
            session.logOut()
            findNavController().navigate(R.id.action_profile_dest_to_splashFragment)

        }
        return binding.root
    }

}