package com.assignment.openinapp.ui.adapters

import com.assignment.openinapp.data.model.Link
import com.assignment.openinapp.databinding.LayoutItemLinkBinding

class LinkListAdapter : RecyclerAdapterBase<Link, LayoutItemLinkBinding>(LayoutItemLinkBinding::inflate) {

    private lateinit var listener: LinkListener

    fun setListener(listener: LinkListener) {
        this.listener = listener
    }

    interface LinkListener {
        fun onClickCopyLink(link: String?)
    }

    override fun onBind(binding: LayoutItemLinkBinding, item: Link, position: Int) {
        binding.link = item
        binding.tvLink.setOnClickListener {
            listener.onClickCopyLink(item.web_link)
        }
    }
}