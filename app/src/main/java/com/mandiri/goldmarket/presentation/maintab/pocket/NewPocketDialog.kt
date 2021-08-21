package com.mandiri.goldmarket.presentation.maintab.pocket

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.presentation.maintab.home.HomeViewModel
import com.mandiri.goldmarket.utils.CustomSharedPreferences

class NewPocketDialog: DialogFragment() {

    private lateinit var pocketNameText: EditText
    private lateinit var customerId: String
    private val viewModel by lazy {
        requireParentFragment().viewModels<HomeViewModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = CustomSharedPreferences(requireContext())
        customerId = sharedPref.retrieveString(CustomSharedPreferences.Key.CUSTOMER_ID).toString()
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
                    DialogInterface.OnClickListener { _, _ ->
                        viewModel.value.createNewPocketRoom(pocketNameText.text.toString(), customerId)
                        viewModel.value.getHomeInfo(1)
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { _, _ ->
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

