package com.assignment.openinapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.assignment.openinapp.ui.Inflate

abstract class RecyclerAdapterBase<LT, VB: ViewBinding>(
    private val inflate: Inflate<VB>
): RecyclerView.Adapter<RecyclerAdapterBase<LT, VB>.ViewHolder>() {

    var list = ArrayList<LT>()

    abstract fun onBind(binding: VB, item: LT, position: Int)

    @SuppressLint("NotifyDataSetChanged")
    fun reset() {
        list.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<LT>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapterBase<LT, VB>.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflate.invoke(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterBase<LT, VB>.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private val binding: VB): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = list[position]
            onBind(binding, item, position)
        }
    }
}