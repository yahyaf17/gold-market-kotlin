package com.mandiri.goldmarket.presentation.maintab.pocket

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mandiri.goldmarket.R

object PocketBindingAdapter {

    @BindingAdapter("productCode")
    @JvmStatic
    fun setHistoryIcon(textView: TextView, pocketCode: Int) {
        when(pocketCode) {
            1 -> textView.text = textView.context.getString(R.string.gold)
            2 -> textView.text = textView.context.getString(R.string.silver)
            3 -> textView.text = textView.context.getString(R.string.platinum)
        }
    }

}