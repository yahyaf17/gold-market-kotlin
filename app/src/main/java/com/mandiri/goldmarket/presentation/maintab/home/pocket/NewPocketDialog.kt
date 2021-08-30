package com.mandiri.goldmarket.presentation.maintab.home.pocket

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.remote.response.product.ProductResponse
import com.mandiri.goldmarket.presentation.maintab.home.HomeViewModel
import com.mandiri.goldmarket.utils.CustomSharedPreferences

class NewPocketDialog: DialogFragment() {

    private lateinit var pocketNameText: EditText
    private lateinit var productListView: AutoCompleteTextView
    private val viewModel by lazy {
        requireParentFragment().viewModels<HomeViewModel>()
    }
    private lateinit var productSelected: ProductResponse

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val sharedPrefInit = requireParentFragment().requireContext()
                .getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
            val sharedPref = CustomSharedPreferences(sharedPrefInit)
            val customerId = sharedPref.retrieveValue(CustomSharedPreferences.Key.CUSTOMER_ID)
            builder.setView(inflater.inflate(R.layout.new_pocket_dialog, null).apply {
                val products = viewModel.value.productsLiveData.value!!
                val productNameList: List<String> = products.map { p -> p.productName }
                val adapter = ArrayAdapter(this.context, R.layout.product_list_item, productNameList)
                pocketNameText = findViewById(R.id.pocket_name_dialog)
                productListView = findViewById(R.id.list_of_product)
                productListView.apply {
                    setAdapter(adapter)
                    setOnItemClickListener { _, _, position, _ ->
                        productSelected = products[position]
                    }
                }
            })
                .setPositiveButton("Create",
                    DialogInterface.OnClickListener { _, _ ->
                        viewModel.value.apply {
                            createNewPocket(
                                pocketNameText.text.toString(),
                                customerId!!,
                                productSelected.id)
                            getHomeInfo(productSelected.id)
                        }
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

