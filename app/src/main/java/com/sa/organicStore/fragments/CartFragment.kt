package com.sa.organicStore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sa.organicStore.adapter.CartAdapter
import com.sa.organicStore.constant.Constant
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.databinding.FragmentCartBinding
import com.sa.organicStore.model.ProductModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cardAdapter: CartAdapter
    private val productList: ArrayList<ProductEntity> = Constant.getCardData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setupClickListeners()
        updateUI()
    }

    private fun setRecyclerView() {
        cardAdapter = CartAdapter(productList, object : CartAdapter.CartListener {
            override fun onDelete(position: Int) {
                productList.removeAt(position)
                cardAdapter.notifyItemRemoved(position)
                updateUI()
            }

            override fun onIncreaseQuantity(position: Int) {
                productList[position].quantityCounter += 1
                cardAdapter.notifyItemChanged(position)
                updateUI()
            }

            override fun onDecreaseQuantity(position: Int) {
                if (productList[position].quantityCounter > 0) {
                    productList[position].quantityCounter -= 1
                    if (productList[position].quantityCounter == 0) {
                        onDelete(position)
                    }
                    cardAdapter.notifyItemChanged(position)
                    updateUI()
                }
            }
        })

        binding.rvCardItem.adapter = cardAdapter
        binding.rvCardItem.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupClickListeners() {
        binding.ivBackArrow.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnApply.setOnClickListener {
            Toast.makeText(requireContext(), "Apply clicked", Toast.LENGTH_SHORT).show()
        }
        binding.btnCheckOut.setOnClickListener {
            Toast.makeText(requireContext(), "Checkout clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        var calculatedWeight = 0
        var calculatedPrice = 0
        var calculatedDiscount = 0
        var calculatedTotalPrice = 0
        var calculatedQuantity = 0

        for (i in productList.indices) {
            calculatedQuantity += productList[i].quantityCounter
            calculatedWeight += productList[i].weight * productList[i].quantityCounter
            calculatedPrice += productList[i].actualPrice * productList[i].quantityCounter
            val discount = productList[i].actualPrice - productList[i].offerPrice
            calculatedDiscount += discount * productList[i].quantityCounter
            calculatedTotalPrice = calculatedPrice - calculatedDiscount
        }

        binding.tvTotalItem.text = calculatedQuantity.toString()
        binding.tvWeight.text = "$calculatedWeight Kg"
        binding.tvPrice.text = "$ $calculatedPrice"
        binding.tvDiscount.text = "$ $calculatedDiscount"
        binding.tvTotalPrice.text = "S $calculatedTotalPrice"
    }
}