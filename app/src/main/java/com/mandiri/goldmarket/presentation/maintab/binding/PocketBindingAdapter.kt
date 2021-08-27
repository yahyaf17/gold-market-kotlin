package com.mandiri.goldmarket.presentation.maintab.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mandiri.goldmarket.R

object PocketBindingAdapter {

    @BindingAdapter("productCode")
    @JvmStatic
    fun setProductName(textView: TextView, pocketCode: Int) {
        when(pocketCode) {
            1 -> textView.text = textView.context.getString(R.string.gold)
            2 -> textView.text = textView.context.getString(R.string.silver)
            3 -> textView.text = textView.context.getString(R.string.platinum)
        }
    }

    @BindingAdapter("productImage")
    @JvmStatic
    fun setProductImage(imageView: ImageView, productName: Int) {
        if (productName == 1) {
            imageView.setImageResource(R.drawable.ic_gold)
        }
        if (productName == 2) {
            imageView.setImageResource(R.drawable.ic_silver)
        }
    }

}