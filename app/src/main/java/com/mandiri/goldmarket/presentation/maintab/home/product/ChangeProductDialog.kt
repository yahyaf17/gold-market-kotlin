package com.mandiri.goldmarket.presentation.maintab.home.product

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.remote.response.product.ProductResponse
import com.mandiri.goldmarket.presentation.maintab.home.HomeViewModel

class ChangeProductDialog: DialogFragment() {

    private val viewModel by lazy {
        requireParentFragment().viewModels<HomeViewModel>()
    }
    private lateinit var productListView: AutoCompleteTextView
    private lateinit var productSelected: ProductResponse

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            builder.setView(inflater.inflate(R.layout.change_product_dialog, null).apply {
                val products = viewModel.value.productsLiveData.value!!
                val productNameList = products.map { p -> p.productName }
                val adapter = ArrayAdapter(this.context, R.layout.product_list_item, productNameList)
                productListView = findViewById(R.id.list_of_product)
                productListView.apply {
                    setAdapter(adapter)
                    setOnItemClickListener { _, _, position, _ ->
                        productSelected = products[position]
                    }
                }
            })
                .setPositiveButton("Change",
                    DialogInterface.OnClickListener { _, _ ->
                        viewModel.value.getHomeInfo(productSelected.id)
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { _, _ ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        const val TAG = "changeProduct"
        fun newInstance() = ChangeProductDialog()
    }

}