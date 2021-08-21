package com.mandiri.goldmarket.presentation.maintab.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mandiri.goldmarket.data.model.Pocket
import com.mandiri.goldmarket.data.remote.response.pocket.PocketResponse
import com.mandiri.goldmarket.databinding.PocketListItemBinding
import com.mandiri.goldmarket.utils.Formatter

class HomePocketAdapter(private val onClickListener: OnClickItem): RecyclerView.Adapter<HomePocketAdapter.HomePocketViewHolder>() {

    private var pockets = mutableListOf<PocketResponse>()

    inner class HomePocketViewHolder(val binding: PocketListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePocketViewHolder {
        val binding = PocketListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomePocketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePocketViewHolder, position: Int) {
        holder.binding.pocket = pockets[position]
        holder.binding.cardPocket.setOnClickListener {
            onClickListener.onChangePocket(position)
        }
    }

    override fun getItemCount(): Int {
        return pockets.size
    }

    fun updateData(pocket: List<PocketResponse>?) {
        pockets.clear()
        if (pocket != null) {
            pockets.addAll(pocket)
        }
        notifyDataSetChanged()
    }

    interface OnClickItem {
        fun onChangePocket(position: Int)
    }

}