package com.mandiri.goldmarket.presentation.maintab.pocket

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.model.Pocket
import com.mandiri.goldmarket.presentation.maintab.home.HomeViewModel
import com.mandiri.goldmarket.presentation.maintab.profile.EditProfileDialog
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import com.mandiri.goldmarket.utils.CustomSharedPreferences.CustomerId
import com.mandiri.goldmarket.utils.CustomSharedPreferences.Username
import kotlin.properties.Delegates

class NewPocketDialog: DialogFragment() {

    private lateinit var pocketNameText: EditText
    private lateinit var sharedPref: SharedPreferences
    private var customerId by Delegates.notNull<Int>()
    private val viewModel by lazy {
        requireParentFragment().viewModels<HomeViewModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = CustomSharedPreferences.credentialsPref(requireContext())
        customerId = sharedPref.CustomerId
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            builder.setView(inflater.inflate(R.layout.new_pocket_dialog, null).apply {
                pocketNameText = findViewById(R.id.pocket_name_dialog)
            })
                .setPositiveButton("Create",
                    DialogInterface.OnClickListener { dialog, id ->
                        viewModel.value.createNewPocketRoom(pocketNameText.text.toString(), customerId)
                        viewModel.value.getHomeInfo(customerId, 1)
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        const val TAG = "newPocket"
        fun newInstance() = NewPocketDialog()
    }
}

