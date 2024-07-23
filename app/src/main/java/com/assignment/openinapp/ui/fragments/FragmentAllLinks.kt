package com.assignment.openinapp.ui.fragments

import androidx.navigation.fragment.findNavController
import com.assignment.openinapp.databinding.FragmentAllLinksBinding
import com.assignment.openinapp.ui.adapters.LinkListAdapter
import com.assignment.openinapp.utils.copyTextToClipboard
import com.assignment.openinapp.viewmodel.DashboardViewModel

class FragmentAllLinks : FragmentBase<FragmentAllLinksBinding>(FragmentAllLinksBinding::inflate), LinkListAdapter.LinkListener {

    private val dashboardVM by lazy { getViewModel(DashboardViewModel::class.java, true) }
    private val linkListAdapter by lazy { LinkListAdapter() }

    private fun initUi() {
        binding.cvBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.rvLinks.adapter = linkListAdapter
        linkListAdapter.setListener(this)
        when (dashboardVM.selectedLink) {
            1 -> linkListAdapter.setItems(dashboardVM.topLinks)
            2 -> linkListAdapter.setItems(dashboardVM.recentLinks)
        }
    }

    override fun setUpUi() {
        initUi()
    }

    override fun onClickCopyLink(link: String?) {
        link?.let {
            context?.copyTextToClipboard(it)
        }
    }
}