package com.sa.organicStore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.databinding.RvHorizontalBinding

class HomeAdapter(
    private val dataList: ArrayList<ProductEntity>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onSaveButtonClick(position: Int, adapter: HomeAdapter)
        fun onImageClick(position: Int, adapter: HomeAdapter)
    }

    inner class MyViewHolder(private val binding: RvHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.ivAdd.setOnClickListener {
                itemClickListener.onSaveButtonClick(adapterPosition, this@HomeAdapter)
            }
            binding.ivBundlePack.setOnClickListener {
                itemClickListener.onImageClick(adapterPosition, this@HomeAdapter)
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
