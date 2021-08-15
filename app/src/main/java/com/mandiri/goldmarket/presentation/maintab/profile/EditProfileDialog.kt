package com.mandiri.goldmarket.presentation.maintab.profile

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.model.Customer
import kotlin.properties.Delegates

class EditProfileDialog: DialogFragment() {

    private var customerId by Delegates.notNull<Int>()
    private lateinit var customer: Customer
    private lateinit var firstNameView: EditText
    private lateinit var lastNameView: EditText
    private val viewModel by lazy {
        requireParentFragment().viewModels<ProfileViewModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            customerId = this.getInt(USERNAME_EDIT)
        }
        viewModel.value.findCustomerById(customerId)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            customer = viewModel.value.customerLiveData.value!!
            Log.d(TAG, "onCreateDialog: ${customer.firstName} ${customer.lastName} ${customer.email}")
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            builder.setView(inflater.inflate(R.layout.edit_profile_dialog, null).apply {
                firstNameView = findViewById(R.id.first_name_dialog)
                lastNameView  = findViewById(R.id.last_name_dialog)
                firstNameView.setText(customer.firstName)
                lastNameView.setText(customer.lastName)
            })
                .setPositiveButton("Update",
                    DialogInterface.OnClickListener { _, _ ->
                        viewModel.value.updateCustomerData(
                            Customer(
                                customerId,
                                firstNameView.text.toString(),
                                lastNameView.text.toString(),
                                customer.email,
                                customer.username,
                                customer.password
                            )
                        )
                        viewModel.value.getProfileInfo(customerId)
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { _, _ ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        const val TAG = "editProfileTag"

        private const val USERNAME_EDIT = "customer"

        fun newInstance(id: Int) = EditProfileDialog().apply {
            arguments = Bundle().apply {
                putInt(USERNAME_EDIT, id)
            }
        }
    }

}