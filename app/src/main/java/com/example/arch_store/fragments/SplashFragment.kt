package com.example.arch_store.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.arch_store.MainActivity
import com.example.arch_store.R


class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        waitForSplashScreen();

        var view: View = inflater.inflate(R.layout.fragment_splash, container, false)
        return view
    }

    private fun waitForSplashScreen() {
        val timer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {

                findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
//                (activity as MainActivity?)?.toolbar?.visibility = View.VISIBLE
//                (activity as MainActivity?)?.bottomNav?.visibility = View.VISIBLE


            }
        }
        timer.start()
    }

}