package com.mandiri.goldmarket.presentation.maintab.pocket

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryImpl

class NewPocketDialog: DialogFragment() {

    private  val factory =  object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PocketViewModel(PocketRepositoryImpl()) as T
        }
    }
    private val viewModelPocket: PocketViewModel by viewModels { factory }
    private lateinit var pocketNameText: EditText

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
                        viewModelPocket.createNewPocket(pocketNameText.text.toString())
//                        viewModel.value.get(customer.username)
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

