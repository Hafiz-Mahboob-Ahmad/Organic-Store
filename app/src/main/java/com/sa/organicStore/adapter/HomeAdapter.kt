package com.sa.organicStore.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.databinding.RvHorizontalBinding

class HomeAdapter(
    private val dataList: ArrayList<ProductEntity>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onSaveButtonClick(position: Int)
        fun onImageClick(position: Int)
        fun onIncreaseQuantity(position: Int)
        fun onDecreaseQuantity(position: Int)
    }

    inner class MyViewHolder(private val binding: RvHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.ivSave.setOnClickListener {
                itemClickListener.onSaveButtonClick(adapterPosition)
            }
            binding.ivDecreaseQuantity.setOnClickListener {
                itemClickListener.onDecreaseQuantity(adapterPosition)
            }
            binding.ivIncreaseQuantity.setOnClickListener {
                itemClickListener.onIncreaseQuantity(adapterPosition)
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
            binding.tvPackRegularPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            if ( pack.quantityCounter == 0){
                binding.tvQuantity.visibility = View.GONE
                binding.ivDecreaseQuantity.visibility = View.GONE
            } else {
                binding.tvQuantity.visibility = View.VISIBLE
                binding.tvQuantity.text = pack.quantityCounter.toString()
                binding.ivDecreaseQuantity.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun getItemAtPosition(position: Int): ProductEntity {
        return dataList[position]
    }
}
