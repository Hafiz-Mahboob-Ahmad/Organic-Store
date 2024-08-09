package com.sa.organicStore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.databinding.RvVerticalBinding
import com.sa.organicStore.model.ProductModel

class ProductAdapter(
    private val dataList: ArrayList<ProductEntity>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onSaveButtonClick(position: Int)
        fun onImageClick(position: Int)
    }

    inner class MyViewHolder(private val binding: RvVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.ivAdd.setOnClickListener {
                itemClickListener.onSaveButtonClick(adapterPosition)
            }
            binding.ivBundlePack.setOnClickListener {
                itemClickListener.onImageClick(adapterPosition)
            }
        }

        fun bind(pack: ProductEntity) {
            binding.ivBundlePack.setImageResource(pack.image[0])
            binding.tvBundlePack.text = pack.name
            binding.tvPackIngredients.text = pack.ingredients
            binding.tvPackOfferPrice.text = "$" + pack.offerPrice.toString()
            binding.tvPackRegularPrice.text = "$" + pack.actualPrice.toString()

            if ( pack.quantityCounter == 0){
                binding.tvQuantity.visibility = View.GONE
            } else {
                binding.tvQuantity.text = pack.quantityCounter.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}