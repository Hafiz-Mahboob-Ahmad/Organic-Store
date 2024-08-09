package com.sa.organicStore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.databinding.RvSavedProductItemBinding
import com.sa.organicStore.databinding.RvVerticalBinding
import com.sa.organicStore.model.ProductModel

class SavedAdapter(
    private val dataList: ArrayList<ProductEntity>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<SavedAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onDeleteClick(position:Int )
    }

    inner class MyViewHolder(private val binding: RvSavedProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition)
            }
            binding.ivRemoveSavedProduct.setOnClickListener {
                itemClickListener.onDeleteClick(adapterPosition)
            }
        }

        fun bind(pack: ProductEntity) {
            binding.ivBundlePack.setImageResource(pack.image[0])
            binding.tvBundlePack.text = pack.name
            binding.tvPackIngredients.text = pack.ingredients
            binding.tvPackOfferPrice.text = "$" + pack.offerPrice.toString()
            binding.tvPackRegularPrice.text = "$" + pack.actualPrice.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvSavedProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}