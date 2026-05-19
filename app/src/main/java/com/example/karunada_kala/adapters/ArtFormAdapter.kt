package com.example.karunada_kala.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.karunada_kala.databinding.ItemArtFormBinding
import com.example.karunada_kala.model.ArtForm

class ArtFormAdapter(private val onItemClick: (ArtForm) -> Unit) :
    ListAdapter<ArtForm, ArtFormAdapter.ArtFormViewHolder>(ArtFormDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtFormViewHolder {
        val binding = ItemArtFormBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArtFormViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtFormViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ArtFormViewHolder(private val binding: ItemArtFormBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(artForm: ArtForm) {
            binding.artName.text = artForm.name
            binding.artRegion.text = artForm.region
            binding.artImage.load(artForm.imageUrl)
            binding.root.setOnClickListener { onItemClick(artForm) }
        }
    }

    class ArtFormDiffCallback : DiffUtil.ItemCallback<ArtForm>() {
        override fun areItemsTheSame(oldItem: ArtForm, newItem: ArtForm): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArtForm, newItem: ArtForm): Boolean {
            return oldItem == newItem
        }
    }
}