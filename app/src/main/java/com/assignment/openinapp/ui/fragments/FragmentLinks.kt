package com.assignment.openinapp.ui.fragments

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.assignment.openinapp.R
import com.assignment.openinapp.databinding.FragmentLinksBinding
import com.assignment.openinapp.ui.adapters.LinkListAdapter
import com.assignment.openinapp.utils.HourAxisValueFormatter
import com.assignment.openinapp.utils.IntegerValueFormatter
import com.assignment.openinapp.utils.UiData
import com.assignment.openinapp.utils.copyTextToClipboard
import com.assignment.openinapp.utils.openWhatsApp
import com.assignment.openinapp.utils.toIntOrZero
import com.assignment.openinapp.utils.toStringOrNA
import com.assignment.openinapp.viewmodel.DashboardViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class FragmentLinks : FragmentBase<FragmentLinksBinding>(FragmentLinksBinding::inflate),
    LinkListAdapter.LinkListener,
    View.OnClickListener {

    private val dashboardVM by lazy { getViewModel(DashboardViewModel::class.java, true) }
    private val linkListAdapter by lazy { LinkListAdapter() }

    private fun getNavController(): NavController? {
        val parent = (parentFragment as? NavHostFragment)?.parentFragment as? FragmentDashboard
        return parent?.findNavController()
    }

    private fun initListeners() {
        binding.cvFaq.setOnClickListener(this)
        binding.cvRecentLinks.setOnClickListener(this)
        binding.cvTalkWithUs.setOnClickListener(this)
        binding.cvTopLinks.setOnClickListener(this)
        binding.cvViewAllLinks.setOnClickListener(this)
        binding.cvViewAnalytics.setOnClickListener(this)
        linkListAdapter.setListener(this)
    }

    private fun initObservers() {
        dashboardVM.dashboardDetails.observe(viewLifecycleOwner) { uiData ->
            when (uiData) {
                is UiData.Error -> binding.layoutShimmerDashboard.stopShimmer()
                is UiData.Loading -> {
                    binding.layoutShimmerDashboard.startShimmer()
                    dashboardVM.clearErrorCallback()
                }
                is UiData.Success -> {
                    uiData.data.let { data ->
                        binding.layoutDetails.visibility = View.VISIBLE
                        binding.layoutHeader.visibility = View.VISIBLE
                        binding.layoutShimmerDashboard.stopShimmer()
                        binding.layoutShimmerDashboard.visibility = View.GONE

                        binding.tvTopLocation.text = data.top_location.toStringOrNA()
                        binding.tvTopSource.text = data.top_source.toStringOrNA()
                        binding.tvTotalClicks.text = data.total_clicks.toIntOrZero()

                        dashboardVM.recentLinks.clear()
                        dashboardVM.recentLinks.addAll(data.data.recent_links)
                        dashboardVM.topLinks.clear()
                        dashboardVM.topLinks.addAll(data.data.top_links)
                        dashboardVM.whatsAppNo = data.support_whatsapp_number.toStringOrNA()

                        if (dashboardVM.topLinks.size > 4) linkListAdapter.setItems(dashboardVM.topLinks.subList(0, 4))
                        else linkListAdapter.setItems(dashboardVM.topLinks)

                        data.data.overall_url_chart?.let { chart ->
                            val entries = chart.entries.map { entry ->
                                Entry(entry.key.substringBefore(":").toFloat(), entry.value.toFloat())
                            }
                            val lineDataSet = LineDataSet(entries, "Overall URL Chart")
                            val lineData = LineData(lineDataSet)
                            binding.lineChart.data = lineData

                            val xAxis: XAxis = binding.lineChart.xAxis
                            xAxis.position = XAxis.XAxisPosition.BOTTOM
                            xAxis.setDrawGridLines(true)
                            xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.black)
                            xAxis.typeface = ResourcesCompat.getFont(requireContext(), R.font.figtree_regular)
                            xAxis.valueFormatter = HourAxisValueFormatter(chart.keys.toList())
                            xAxis.granularity = 3f

                            val leftAxis: YAxis = binding.lineChart.axisLeft
                            leftAxis.setDrawGridLines(true)
                            leftAxis.textColor = ContextCompat.getColor(requireContext(), R.color.black)
                            leftAxis.typeface = ResourcesCompat.getFont(requireContext(), R.font.figtree_regular)
                            leftAxis.axisLineColor = ContextCompat.getColor(requireContext(), R.color.black)
                            leftAxis.valueFormatter = IntegerValueFormatter()
                            leftAxis.granularity = 1f

                            val rightAxis: YAxis = binding.lineChart.axisRight
                            rightAxis.isEnabled = false
                            binding.lineChart.axisRight.isEnabled = false
                            binding.lineChart.invalidate()
                        }
                    }
                }
            }
        }
        dashboardVM.errorCallback.observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                binding.cvError.visibility = View.VISIBLE
                binding.layoutShimmerDashboard.stopShimmer()
                binding.layoutShimmerDashboard.visibility = View.GONE
                binding.tvError.text = error
            }
        }
    }

    private fun initUi() {
        binding.rvLinks.adapter = linkListAdapter
    }

    override fun onClick(p0: View?) {
        when (p0)  {
            binding.cvFaq -> {}
            binding.cvRecentLinks -> {
                binding.cvRecentLinks.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.brandeis_blue))
                binding.cvTopLinks.setCardBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))
                binding.tvTitleRecentLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.tvTitleTopLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.spanish_gray))
                if (dashboardVM.recentLinks.size > 4) linkListAdapter.setItems(dashboardVM.recentLinks.subList(0, 4))
                else linkListAdapter.setItems(dashboardVM.recentLinks)
                dashboardVM.selectedLink = 2
            }
            binding.cvTalkWithUs -> context.openWhatsApp(dashboardVM.whatsAppNo)
            binding.cvTopLinks -> {
                binding.cvRecentLinks.setCardBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))
                binding.cvTopLinks.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.brandeis_blue))
                binding.tvTitleRecentLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.spanish_gray))
                binding.tvTitleTopLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                if (dashboardVM.topLinks.size > 4) linkListAdapter.setItems(dashboardVM.topLinks.subList(0, 4))
                else linkListAdapter.setItems(dashboardVM.topLinks)
                dashboardVM.selectedLink = 1
            }
            binding.cvViewAllLinks -> {
                val action = FragmentDashboardDirections.actionDashboardToAllLinks()
                getNavController()?.navigate(action)
            }
            binding.cvViewAnalytics -> {}
        }
    }

    override fun onClickCopyLink(link: String?) {
        link?.let {
            context?.copyTextToClipboard(it)
        }
    }

    override fun setUpUi() {
        initUi()
        initListeners()
        initObservers()
    }
}