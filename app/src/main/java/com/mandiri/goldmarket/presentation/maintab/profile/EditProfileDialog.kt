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
import com.mandiri.goldmarket.data.remote.request.customer.CustomerRequest
import com.mandiri.goldmarket.data.remote.response.customer.CustomerResponse

class EditProfileDialog: DialogFragment() {

    private lateinit var customerId: String
    private lateinit var customer: CustomerResponse
    private lateinit var firstNameView: EditText
    private lateinit var lastNameView: EditText
    private val viewModel by lazy {
        requireParentFragment().viewModels<ProfileViewModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            customerId = this.getString(USERNAME_EDIT).toString()
        }
        viewModel.value.findCustomerById()
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
                            CustomerRequest(
                                id = customerId,
                                firstName = firstNameView.text.toString(),
                                lastName = lastNameView.text.toString(),
                                email = customer.email,
                                userName = customer.userName,
                                userPassword = "null",
                                address = customer.address,
                                status = customer.status,
                                dateOfBirth = customer.dateOfBirth,
                                photoProfile = customer.photoProfile
                            )
                        )
                        viewModel.value.getProfileInfo()
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

        fun newInstance(id: String) = EditProfileDialog().apply {
            arguments = Bundle().apply {
                putString(USERNAME_EDIT, id)
            }
        }
    }

}