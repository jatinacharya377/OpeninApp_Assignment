package com.assignment.openinapp.ui.fragments

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.assignment.openinapp.databinding.FragmentSplashBinding

class FragmentSplash : FragmentBase<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private fun initUi() {
        Handler(Looper.getMainLooper()).postDelayed({
            val action = FragmentSplashDirections.actionSplashToDashboard()
            findNavController().navigate(action)
        }, 500)
    }

    override fun setUpUi() {
        initUi()
    }
}