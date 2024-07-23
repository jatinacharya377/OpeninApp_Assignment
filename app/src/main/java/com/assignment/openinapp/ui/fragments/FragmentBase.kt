package com.assignment.openinapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.assignment.openinapp.ui.Inflate
import com.assignment.openinapp.viewmodel.ViewModelBase

abstract class FragmentBase<VB: ViewBinding>(private val inflate: Inflate<VB>): Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    abstract fun setUpUi()

    fun <T : ViewModelBase> getViewModel(entityClass: Class<T>, shared: Boolean = false): T {
        return if (shared) ViewModelProvider(requireActivity())[entityClass]
        else ViewModelProvider(this)[entityClass]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }
}