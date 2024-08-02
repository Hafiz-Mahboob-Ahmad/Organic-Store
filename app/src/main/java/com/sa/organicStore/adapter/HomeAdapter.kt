package com.sa.organicStore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sa.organicStore.databinding.RvHorizontalBinding
import com.sa.organicStore.model.ProductModel

class HomeAdapter(
    private val dataList: ArrayList<ProductModel>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(pack: ProductModel)
    }

    inner class MyViewHolder(private val binding: RvHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(dataList[adapterPosition])
            }
        }

        fun bind(pack: ProductModel) {
            binding.ivBundlePack.setImageResource(pack.image[0])
            binding.tvBundlePack.text = pack.name
            binding.tvPackIngredients.text = pack.ingredients
            binding.tvPackOfferPrice.text = "$" + pack.offerPrice.toString()
            binding.tvPackRegularPrice.text = "$" + pack.actualPrice.toString()
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
}