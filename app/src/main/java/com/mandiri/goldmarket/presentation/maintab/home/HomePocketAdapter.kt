package com.mandiri.goldmarket.presentation.maintab.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mandiri.goldmarket.data.remote.response.pocket.PocketResponse
import com.mandiri.goldmarket.databinding.PocketListItemBinding

class HomePocketAdapter(private val onClickListener: OnClickItem): RecyclerView.Adapter<HomePocketAdapter.HomePocketViewHolder>() {

    private var pockets = mutableListOf<PocketResponse>()

    inner class HomePocketViewHolder(val binding: PocketListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePocketViewHolder {
        val binding = PocketListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomePocketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePocketViewHolder, position: Int) {
        holder.binding.apply {
            pocket = pockets[position]
            cardPocket.setOnClickListener {
                onClickListener.onChangePocket(position)
            }
            btnEditPocket.setOnClickListener {
                onClickListener.onEditPocket(position)
            }
            btnDelete.setOnClickListener {
                onClickListener.onDeletePocket(position)
            }
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
        fun onEditPocket(position: Int)
        fun onDeletePocket(position: Int)
    }

}