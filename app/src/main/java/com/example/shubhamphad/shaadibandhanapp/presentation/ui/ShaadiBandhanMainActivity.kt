package com.example.shubhamphad.shaadibandhanapp.presentation.ui

import android.os.Bundle
import com.example.shubhamphad.shaadibandhanapp.R
import com.example.shubhamphad.shaadibandhanapp.databinding.ActivityShaadiBandhanBinding
import com.example.shubhamphad.shaadibandhanapp.presentation.base.BaseActivity
import com.example.shubhamphad.shaadibandhanapp.utils.NavigationFragmentSupport

class ShaadiBandhanMainActivity :
    BaseActivity<ActivityShaadiBandhanBinding>(ActivityShaadiBandhanBinding::inflate),
    NavigationFragmentSupport {

    override fun navId() = R.id.nav_host_main


    fun setToolbar(
        header: String,
        search: Boolean = false
    ) {
        binding.toolbar.tvTitle.text = header
    }
}