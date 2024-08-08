package com.sa.organicStore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sa.organicStore.adapter.CartAdapter
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.databinding.FragmentCartBinding
import com.sa.organicStore.utils.UserPrefs
import com.sa.organicStore.viewmodel.CartViewModel
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val cartViewModel: CartViewModel by viewModels()
    private var userId by Delegates.notNull<Int>()
    private lateinit var cardAdapter: CartAdapter
    private lateinit var productList: ArrayList<ProductEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        userId = UserPrefs(requireContext()).getUser()!!.userId
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchCartProducts()
        collectStateFlows()
        setupClickListeners()
        updateTotal()
    }

    private fun fetchCartProducts() {
        val userId = UserPrefs(requireContext()).getUser()!!.userId
        cartViewModel.getCartProducts(userId)
    }

    private fun collectStateFlows() {
        lifecycleScope.launch {
            cartViewModel.cartProducts.collect {
                productList = ArrayList()
                productList.addAll(it)
                setRecyclerView(productList)
            }
        }
    }

    private fun setRecyclerView(productList: ArrayList<ProductEntity>) {
        cardAdapter = CartAdapter(productList, object : CartAdapter.CartListener {
            override fun onDelete(position: Int) {
                productList.removeAt(position)
                cardAdapter.notifyItemRemoved(position)

                //delete from table
                deleteCartProduct(productList[position].productId)
                updateTotal()
            }

            override fun onIncreaseQuantity(position: Int) {
                productList[position].quantityCounter++
                cardAdapter.notifyItemChanged(position)

                //increase quantity in cart table
                updateQuantity(productList[position])
                updateTotal()
            }

            override fun onDecreaseQuantity(position: Int) {
                if (productList[position].quantityCounter > 1) {
                    productList[position].quantityCounter--
                    updateQuantity(productList[position])
                    cardAdapter.notifyItemChanged(position)
                } else {
                    deleteCartProduct(productList[position].productId)
                    productList.removeAt(position)
                    cardAdapter.notifyItemRemoved(position)
                }
                updateTotal()
            }
        })

        binding.rvCardItem.adapter = cardAdapter
        binding.rvCardItem.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun updateQuantity(product: ProductEntity) {
        cartViewModel.updateCartProduct(product.quantityCounter, userId, product.productId)
    }

    private fun deleteCartProduct(productId: Int) {
        cartViewModel.deleteCartProduct(userId, productId)
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

    private fun updateTotal() {
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