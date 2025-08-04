package com.example.shubhamphad.shaadibandhanapp.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.facebook.shimmer.ShimmerFrameLayout


fun ProgressBar.showLoading(targetView: View?, show: Boolean) {
    isVisible = show
    targetView?.isVisible = !show
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun ShimmerFrameLayout.startShimmering(showView: Boolean = true) {
    this.startShimmer()
    this.visibility = if (showView) View.VISIBLE else View.GONE
}

fun ShimmerFrameLayout.stopShimmering(hideView: Boolean = true) {
    this.stopShimmer()
    this.visibility = if (hideView) View.GONE else View.VISIBLE
}