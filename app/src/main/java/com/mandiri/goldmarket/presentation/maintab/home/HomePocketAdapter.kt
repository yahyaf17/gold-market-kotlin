package com.mandiri.goldmarket.presentation.maintab.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mandiri.goldmarket.data.model.Pocket
import com.mandiri.goldmarket.databinding.PocketListItemBinding
import com.mandiri.goldmarket.utils.Formatter

class HomePocketAdapter: RecyclerView.Adapter<HomePocketAdapter.HomePocketViewHolder>() {

    private var pockets = mutableListOf<Pocket>()

    inner class HomePocketViewHolder(val binding: PocketListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePocketViewHolder {
        val binding = PocketListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomePocketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePocketViewHolder, position: Int) {
        with(holder) {
            with(pockets[position]) {
                binding.textTitlePocket.text = this.name
                binding.textProductType.text = this.product
                binding.textAmount.text = "${this.amount} gram"
                binding.textBalance.text = Formatter.rupiahFormatter(this.totalPrice)
            }
        }
    }

    override fun getItemCount(): Int {
        return pockets.size
    }

    fun updateData(pocket: List<Pocket>) {
        pockets.clear()
        pockets.addAll(pocket)
        notifyDataSetChanged()
    }

}