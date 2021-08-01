package com.mandiri.goldmarket.presentation.maintab.history

import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.model.History
import com.mandiri.goldmarket.databinding.HistoryListItemBinding
import com.mandiri.goldmarket.utils.Formatter
import java.util.*

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var histories = mutableListOf<History>()

    class HistoryViewHolder(val binding: HistoryListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        with(holder) {
            with(histories[position]) {
                binding.textProduct.text = this.product
                binding.textPocketName.text = this.pocketName
                binding.textPurchaseType.text = this.purchaseType
                binding.textDate.text = Formatter.dateFormatter(this.purchaseDate)
                binding.textAmount.text = "${this.amount} gram"
                binding.textTotalPrice.text = Formatter.rupiahFormatter(this.totalPrice)
                if (this.purchaseType.equals("Buy")) {
                    binding.imageHistory.setImageResource(R.drawable.ic_up_arrow)
                }
                if (this.purchaseType.equals("Sell")) {
                    binding.imageHistory.setImageResource(R.drawable.ic_down_arrow)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return histories.size
    }

    fun updateData(customer: List<History>) {
        histories.clear()
        histories.addAll(customer)
        notifyDataSetChanged()
    }

}