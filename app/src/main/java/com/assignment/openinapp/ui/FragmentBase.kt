package com.assignment.openinapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.assignment.openinapp.viewmodel.ViewModelBase

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

