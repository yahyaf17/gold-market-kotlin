package com.mandiri.goldmarket.presentation.maintab.history

import android.widget.ImageView
import android.widget.TextView
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

    @BindingAdapter("purchaseTypeInt")
    @JvmStatic
    fun setTrxIcon(imageView: ImageView, purchaseType: Int) {
        if (purchaseType == 1) {
            imageView.setImageResource(R.drawable.ic_up_arrow)
        }
        if (purchaseType == 0) {
            imageView.setImageResource(R.drawable.ic_down_arrow)
        }
    }

    @BindingAdapter("purchaseTypeIntText")
    @JvmStatic
    fun setTrxIcon(textView: TextView, purchaseType: Int) {
        if (purchaseType == 1) {
            textView.text = textView.context.getString(R.string.buy)
        }
        if (purchaseType == 0) {
            textView.text = textView.context.getString(R.string.sell)
        }
    }

}