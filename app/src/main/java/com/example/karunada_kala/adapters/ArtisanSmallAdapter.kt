package com.example.karunada_kala.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.karunada_kala.databinding.ItemArtisanSmallBinding
import com.example.karunada_kala.model.Artisan

class ArtisanSmallAdapter(private val onItemClick: (Artisan) -> Unit) :
    ListAdapter<Artisan, ArtisanSmallAdapter.ArtisanSmallViewHolder>(ArtisanDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtisanSmallViewHolder {
        val binding = ItemArtisanSmallBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArtisanSmallViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtisanSmallViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ArtisanSmallViewHolder(private val binding: ItemArtisanSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(artisan: Artisan) {
            binding.artisanName.text = artisan.name
            binding.artisanImage.load(artisan.photoUrl)
            binding.root.setOnClickListener { onItemClick(artisan) }
        }
    }

    class ArtisanDiffCallback : DiffUtil.ItemCallback<Artisan>() {
        override fun areItemsTheSame(oldItem: Artisan, newItem: Artisan): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Artisan, newItem: Artisan): Boolean =
            oldItem == newItem
    }
}