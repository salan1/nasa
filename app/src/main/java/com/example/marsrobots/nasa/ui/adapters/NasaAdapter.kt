package com.example.marsrobots.nasa.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marsrobots.databinding.LayoutNasaItemBinding
import com.example.marsrobots.nasa.models.NasaItem

class NasaAdapter(
    inline val onClick: ((item: NasaItem) -> Unit)? = null,
    inline val onLongClick: ((item: NasaItem) -> Unit)? = null
) : PagingDataAdapter<NasaItem, NasaAdapter.NasaViewHolder>(NasaItemDiffCallback()) {

    override fun onBindViewHolder(holder: NasaViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindTo(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NasaViewHolder {
        val binding =
            LayoutNasaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NasaViewHolder(binding)
    }

    inner class NasaViewHolder(private val binding: LayoutNasaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: NasaItem) = with(binding) {
            root.setOnClickListener { onClick?.invoke(item) }
            root.setOnLongClickListener {
                onLongClick?.invoke(item)
                true
            }

            textViewHeading.text = item.heading
            textViewSubhheading.text = item.date

            Glide.with(root.context)
                .load(item.imageUrl)
                .into(imageView)
        }
    }

}