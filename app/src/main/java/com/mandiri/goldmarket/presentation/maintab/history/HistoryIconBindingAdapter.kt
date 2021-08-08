package com.mandiri.goldmarket.presentation.maintab.history

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mandiri.goldmarket.R

object HistoryIconBindingAdapter {

    @BindingAdapter("purchaseType")
    @JvmStatic
    fun setHistoryIcon(imageView: ImageView, purchaseType: String) {
        if (purchaseType == "Buy") {
            imageView.setImageResource(R.drawable.ic_up_arrow)
        }
        if (purchaseType == "Sell") {
            imageView.setImageResource(R.drawable.ic_down_arrow)
        }
    }

}