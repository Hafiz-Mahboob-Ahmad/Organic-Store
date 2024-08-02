package com.sa.organicStore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sa.organicStore.databinding.VpProductImageItemBinding

class ProductImagesAdapter(private val images: ArrayList<Int>) : RecyclerView.Adapter<ProductImagesAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: VpProductImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageRes: Int) {
            binding.ivProduct.setImageResource(imageRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = VpProductImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size
}