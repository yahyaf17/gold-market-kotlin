package com.mandiri.goldmarket.utils

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageButton
import com.mandiri.goldmarket.R

object ButtonUtils {
    fun showPasswordUtils(toggleOn: Boolean, passText: EditText, imgBtn: ImageButton) {
        if (toggleOn) {
            passText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            imgBtn.setImageResource(R.drawable.ic_password_visible)
        } else {
            passText.transformationMethod = PasswordTransformationMethod.getInstance()
            imgBtn.setImageResource(R.drawable.ic_password_hidden)
        }
    }
}