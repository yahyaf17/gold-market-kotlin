package com.mandiri.goldmarket.presentation.maintab.home.pocket

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.remote.request.pocket.Customer
import com.mandiri.goldmarket.data.remote.request.pocket.EditPocketRequest
import com.mandiri.goldmarket.data.remote.request.pocket.Product
import com.mandiri.goldmarket.data.remote.response.pocket.PocketResponse
import com.mandiri.goldmarket.presentation.maintab.home.HomeViewModel

class EditPocketDialog: DialogFragment() {

    private lateinit var pocketNameText: EditText
    private lateinit var pocketId: String
    private lateinit var pocket: PocketResponse
    private val viewModel by lazy {
        requireParentFragment().viewModels<HomeViewModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            pocketId = this.getString(POCKET_ID).toString()
        }
        viewModel.value.getCurrentPocket(pocketId)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            subscribe()
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            builder.setView(inflater.inflate(R.layout.edit_pocket_dialog, null).apply {
                viewModel.value.pocketSelectedLiveData.observe(requireParentFragment().viewLifecycleOwner) { p ->
                    pocketNameText = findViewById(R.id.pocket_name_dialog)
                    pocketNameText.setText(p.pocketName)
                    pocket = p
                }
            })
                .setPositiveButton("Create",
                    DialogInterface.OnClickListener { _, _ ->
                        viewModel.value.apply {
                            editPocketName(
                                EditPocketRequest(
                                    pocketId,
                                    pocketNameText.text.toString(),
                                    pocket.pocketQty,
                                    pocket.totalAmount,
                                    Customer(
                                        pocket.customer.id
                                    ),
                                    Product(
                                        pocket.product.id
                                    )
                                )
                            )
                            getHomeInfo(pocket.product.id)
                        }
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { _, _ ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun subscribe() {

    }

    companion object {
        const val TAG = "editPocketTag"

        private const val POCKET_ID = "ID"

        fun newInstance(pocket: String) = EditPocketDialog().apply {
            arguments = Bundle().apply {
                putString(POCKET_ID, pocket)
            }
        }
    }

}