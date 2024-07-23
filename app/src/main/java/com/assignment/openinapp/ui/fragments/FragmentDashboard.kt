package com.assignment.openinapp.ui.fragments

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.assignment.openinapp.R
import com.assignment.openinapp.databinding.FragmentDashboardBinding

class FragmentDashboard : FragmentBase<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {

    private fun initUi() {
        val hostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_bottom_nav) as NavHostFragment
        val navController = hostFragment.findNavController()
        binding.bnvDashboard.setupWithNavController(navController)
        binding.bnvDashboard.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_links -> navController.navigate(R.id.fragment_links)
                R.id.fragment_courses -> navController.navigate(R.id.fragment_courses)
                R.id.fragment_campaigns -> navController.navigate(R.id.fragment_campaigns)
                R.id.fragment_profile -> navController.navigate(R.id.fragment_profile)
            }
            true
        }
    }

    override fun setUpUi() {
        initUi()
    }
}