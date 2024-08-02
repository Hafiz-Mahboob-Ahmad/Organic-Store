package com.sa.organicStore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sa.organicStore.databinding.RvCartPageBinding
import com.sa.organicStore.model.ProductModel

class CartAdapter(
    private val dataList: ArrayList<ProductModel>,
    private val cartClickListener: CartListener
) : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    interface CartListener {
        fun onDelete(position: Int)
        fun onIncreaseQuantity(position: Int)
        fun onDecreaseQuantity(position: Int)
    }

    inner class MyViewHolder(private val binding: RvCartPageBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.ivDelete.setOnClickListener {
                cartClickListener.onDelete(adapterPosition)
            }
            binding.ivIncreaseQuantity.setOnClickListener {
                cartClickListener.onIncreaseQuantity(adapterPosition)
            }
            binding.ivDecreaseQuantity.setOnClickListener {
                cartClickListener.onDecreaseQuantity(adapterPosition)
            }
        }

        fun bind(pack: ProductModel) {
            binding.ivProduct.setImageResource(pack.image[0])
            binding.tvProductName.text = pack.name
            binding.tvWeight.text = pack.weight.toString()
            binding.tvQuantityCounter.text = pack.quantityCounter.toString()
            binding.tvPrice.text = "$ ${pack.actualPrice}"
            val weight = pack.weight * pack.quantityCounter
            binding.tvWeight.text = weight.toString()
            binding.tvWeightUnit.text = pack.weightUnit
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvCartPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}