package com.mandiri.goldmarket.presentation.maintab.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.model.History
import com.mandiri.goldmarket.databinding.HistoryListItemBinding
import com.mandiri.goldmarket.utils.Formatter

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var histories = mutableListOf<History>()

    inner class HistoryViewHolder(val binding: HistoryListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.binding.history = histories.reversed()[position]
    }

    override fun getItemCount(): Int {
        return histories.size
    }

    fun updateData(history: List<History>) {
        histories.clear()
        histories.addAll(history)
        notifyDataSetChanged()
    }

}