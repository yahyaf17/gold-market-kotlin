package com.mandiri.goldmarket.presentation.maintab.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mandiri.goldmarket.R

object HistoryIconBindingAdapter {

    @BindingAdapter("purchaseTypeInt")
    @JvmStatic
    fun setTrxTypeText(imageView: ImageView, purchaseType: Int) {
        if (purchaseType == 1) {
            imageView.setImageResource(R.drawable.ic_up_arrow)
        }
        if (purchaseType == 0) {
            imageView.setImageResource(R.drawable.ic_down_arrow)
        }
    }

    @BindingAdapter("purchaseTypeIntText")
    @JvmStatic
    fun setTrxTypeText(textView: TextView, purchaseType: Int) {
        if (purchaseType == 1) {
            textView.text = textView.context.getString(R.string.buy)
        }
        if (purchaseType == 0) {
            textView.text = textView.context.getString(R.string.sell)
        }
    }

}