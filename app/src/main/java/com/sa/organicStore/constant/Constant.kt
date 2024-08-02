package com.sa.organicStore.constant

import com.sa.organicStore.R
import com.sa.organicStore.model.ProductModel
import java.lang.reflect.Array

object Constant {
    fun getOurNewItemData(): ArrayList<ProductModel> {
        return arrayListOf(
            ProductModel(
                image = arrayListOf(
                    R.drawable.rice_flour,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Spices Bundle",
                ingredients = "Turmeric, Pepper, Cumin",
                offerPrice = 45,
                actualPrice = 60,
                description = "Rice flour product details description",
                weight = 6,
                weightUnit = "Kg",
                quantityCounter = 2
            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.turmeric_powder,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Herbal Tea Pack",
                ingredients = "Green Tea, Mint, Lemon",
                offerPrice = 50,
                actualPrice = 65,
                description = "Tamarind rice powder product details description",
                weight = 8,
                weightUnit = "Gm",
                quantityCounter = 20
            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.turmeric_powder,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Soup Mix Pack, jhkjbkjkjkjhjhkkhggggggggggggggggggg",
                ingredients = "Vegetables, Spices, Herbs",
                offerPrice = 30,
                actualPrice = 45,
                description = "Turmeric powder product details description",
                weight = 8,
                weightUnit = "Kg",
                quantityCounter = 4
            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.turmeric_powder,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Tamarind Rice Pack",
                ingredients = "Rice, Tamarind, Spices",
                offerPrice = 35,
                actualPrice = 50,
                description = "Tamarind rice powder product details description",
                weight = 6,
                weightUnit = "Kg",
                quantityCounter = 1
            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.turmeric_powder,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Onion Oil Pack",
                ingredients = "Onion, Oil, Spices",
                offerPrice = 40,
                actualPrice = 55,
                description = "Turmeric powder product details description",
                weight = 2,
                weightUnit = "Kg",
                quantityCounter = 3
            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.turmeric_powder,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Herbal Tea Pack",
                ingredients = "Green Tea, Mint, Lemon",
                offerPrice = 50,
                actualPrice = 65,
                description = "Tamarind rice powder product details description",
                weight = 8,
                weightUnit = "Gm",
                quantityCounter = 20
            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.turmeric_powder,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Soup Mix Pack, jhkjbkjkjkjhjhkkhggggggggggggggggggg",
                ingredients = "Vegetables, Spices, Herbs",
                offerPrice = 30,
                actualPrice = 45,
                description = "Turmeric powder product details description",
                weight = 8,
                weightUnit = "Kg",
                quantityCounter = 4
            )
        )
    }

    fun getPopularPackData(): ArrayList<ProductModel> {
        return arrayListOf(
            ProductModel(
                image = arrayListOf(
                    R.drawable.tamarind_rice_powder,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Tamarind Rice Pack",
                ingredients = "Rice, Tamarind, Spices",
                offerPrice = 35,
                actualPrice = 50,
                description = "Tamarind rice powder product details description",
                weight = 8,
                weightUnit = "Gm",
                quantityCounter = 20
            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.rice_flour,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Spices Bundle",
                ingredients = "Turmeric, Pepper, Cumin",
                offerPrice = 45,
                actualPrice = 60,
                description = "Tamarind rice powder product details description",
                weight = 6,
                weightUnit = "Kg",
                quantityCounter = 1
            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.tamarind_rice_powder,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Herbal Tea Pack",
                ingredients = "Green Tea, Mint, Lemon",
                offerPrice = 50,
                actualPrice = 65,
                description = "Tamarind rice powder product details description",
                weight = 8,
                weightUnit = "Gm",
                quantityCounter = 20
            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.turmeric_powder,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Soup Mix Pack",
                ingredients = "Vegetables, Spices, Herbs",
                offerPrice = 30,
                actualPrice = 45,
                description = "Turmeric powder product details description",
                weight = 8,
                weightUnit = "Kg",
                quantityCounter = 4
            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.rice_flour,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Onion Oil Pack",
                ingredients = "Onion, Oil, Spices",
                offerPrice = 40,
                actualPrice = 55,
                description = "Tamarind rice powder product details description",
                weight = 8,
                weightUnit = "Gm",
                quantityCounter = 20
            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.rice_flour,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Tamarind Rice Pack",
                ingredients = "Rice, Tamarind, Spices",
                offerPrice = 35,
                actualPrice = 50,
                description = "Tamarind rice powder product details description",
                weight = 8,
                weightUnit = "Gm",
                quantityCounter = 20
            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.rice_flour,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Spices Bundle",
                ingredients = "Turmeric, Pepper, Cumin",
                offerPrice = 45,
                actualPrice = 60,
                description = "Tamarind rice powder product details description",
                weight = 6,
                weightUnit = "Kg",
                quantityCounter = 0
            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.turmeric_powder,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Herbal Tea Pack",
                ingredients = "Green Tea, Mint, Lemon",
                offerPrice = 50,
                actualPrice = 65,
                description = "Tamarind rice powder product details description",
                weight = 8,
                weightUnit = "Kg",
                quantityCounter = 0
            )
        )
    }

    fun getCardData(): ArrayList<ProductModel> {
        return arrayListOf(
            ProductModel(
                image = arrayListOf(
                    R.drawable.turmeric_powder,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Herbal Tea Pack",
                ingredients = "Green Tea, Mint, Lemon",
                offerPrice = 10,
                actualPrice = 12,
                description = "Tamarind rice powder product details description",
                weight = 8,
                weightUnit = "Kg",
                quantityCounter = 1
            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.rice_flour,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Onion Oil Pack",
                ingredients = "Onion, Oil, Spices",
                offerPrice = 20,
                actualPrice = 22,
                description = "Tamarind rice powder product details description",
                weight = 8,
                weightUnit = "Kg",
                quantityCounter = 1

            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.rice_flour,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Tamarind Rice Pack",
                ingredients = "Rice, Tamarind, Spices",
                offerPrice = 30,
                actualPrice = 32,
                description = "Tamarind rice powder product details description",
                weight = 8,
                weightUnit = "Kg",
                quantityCounter = 1

            ),
            ProductModel(
                image = arrayListOf(
                    R.drawable.rice_flour,
                    R.drawable.rice_flour,
                    R.drawable.tamarind_rice_powder
                ),
                name = "Spices Bundle",
                ingredients = "Turmeric, Pepper, Cumin",
                offerPrice = 40,
                actualPrice = 42,
                description = "Tamarind rice powder product details description",
                weight = 6,
                weightUnit = "Kg",
                quantityCounter = 1
            ),

            )
    }
}
