package com.example.marsrobots.nasa.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.marsrobots.nasa.models.NasaItem

class NasaItemDiffCallback : DiffUtil.ItemCallback<NasaItem>() {
    override fun areItemsTheSame(oldItem: NasaItem, newItem: NasaItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NasaItem, newItem: NasaItem): Boolean =
        oldItem == newItem
}