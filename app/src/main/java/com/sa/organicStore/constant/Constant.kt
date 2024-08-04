package com.sa.organicStore.constant

import com.sa.organicStore.R
import com.sa.organicStore.database.entities.ProductEntity

object Constant {
    fun getOurNewItemData(): ArrayList<ProductEntity> {
        return arrayListOf(
            ProductEntity(
                image = arrayListOf(
                    R.drawable.cardamom_hari_ilaichi,
                    R.drawable.cardamom_hari_ilaichi,
                    R.drawable.cardamom_hari_ilaichi
                ),
                name = "Cardamom Hari Ilaichi",
                ingredients = "Turmeric, Pepper, Cumin",
                offerPrice = 7,
                actualPrice = 8,
                description = "it is used in so many dishes like cardamom braid bread, cookies, even it is used in tea and coffee as well, It makes tea and coffee tastier.",
                weight = 1,
            ),
            ProductEntity(
                image = arrayListOf(
                    R.drawable.tomato_ketchup,
                    R.drawable.tomato_ketchup,
                    R.drawable.tomato_ketchup
                ),
                name = "Tomato Ketchup",
                ingredients = "Mushrooms, Oysters, Mussels, Egg whites, Grapes or Walnuts",
                offerPrice = 8,
                actualPrice = 9,
                description = "Include onions, allspice, coriander, cloves, cumin, garlic, and mustard, and sometimes include celery, cinnamon, or ginger.",
                weight = 4,
            ),
            ProductEntity(
                image = arrayListOf(
                    R.drawable.soya_sause,
                    R.drawable.soya_sause,
                    R.drawable.soya_sause
                ),
                name = "Soya Sause",
                ingredients = "Vegetables, Spices, Herbs",
                offerPrice = 11,
                actualPrice = 13,
                description = "Soy sauce (sometimes called soya sauce in British English) is a liquid condiment of Chinese origin, traditionally made from a fermented paste of soybeans, roasted grain, brine, and Aspergillus oryzae or Aspergillus sojae molds.",
                weight = 3,
            ),
            ProductEntity(
                image = arrayListOf(
                    R.drawable.kabuli_chana,
                    R.drawable.kabuli_chana,
                    R.drawable.kabuli_chana
                ),
                name = "Chana Pack",
                ingredients = "Black Pepper, Red Chili, Clive",
                offerPrice = 9,
                actualPrice = 11,
                description = "It looks kind of like a wrinkled hazelnut. Its nutty and creamy flavour, firm texture and minimal fat make it a versatile ingredient.",
                weight = 6,
            ),
            ProductEntity(
                image = arrayListOf(
                    R.drawable.besan_pack,
                    R.drawable.besan_pack,
                    R.drawable.besan_pack
                ),
                name = "Besan Pack",
                ingredients = "Pinch of turmeric, Yoghurt",
                offerPrice = 5,
                actualPrice = 6,
                description = " deeply cleanse the skin and balance sebum production. Method: To restore your skin's radiance, combine two tablespoons of besan, a few drops of lemon juice, and one tablespoon of milk cream. The mixture should have a smooth consistency.",
                weight = 2,
            ),
            ProductEntity(
                image = arrayListOf(
                    R.drawable.chia_seeds,
                    R.drawable.chia_seeds,
                    R.drawable.chia_seeds
                ),
                name = "Chia Seeds Pack",
                ingredients = "Selenium, iron, magnesium, and calcium.",
                offerPrice = 14,
                actualPrice = 16,
                description = "full of nutrients, including omega-3 fatty acids, iron, calcium, and antioxidants. they have a mild, nutty flavor and can be a good addition to a balanced diet.",
                weight = 1,
            ),
            ProductEntity(
                image = arrayListOf(
                    R.drawable.turmeric_powder,
                    R.drawable.turmeric_powder,
                    R.drawable.turmeric_powder
                ),
                name = "Turmeric Powder",
                ingredients = "Vegetables, Spices, Herbs",
                offerPrice = 5,
                actualPrice = 6,
                description = "Made with the world's finest turmeric, Shan Khalis Haldee Powder adds rich color, unique flavor and tempting aroma to your everyday meals",
                weight = 2,
            )
        )
    }

    fun getPopularPackData(): ArrayList<ProductEntity> {
        return arrayListOf(
            ProductEntity(
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
            ProductEntity(
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
            ProductEntity(
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
            ProductEntity(
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
            ProductEntity(
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
            ProductEntity(
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
            ProductEntity(
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
            ProductEntity(
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

    fun getCardData(): ArrayList<ProductEntity> {
        return arrayListOf(
            ProductEntity(
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
            ProductEntity(
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
            ProductEntity(
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
            ProductEntity(
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
