package com.petko.fruitapp.utils

import kotlinx.serialization.Serializable

@Serializable
data class Fruit(
    val name: String,
    val id: Int,
    val family: String,
    val genus: String,
    val order: String,
    val nutritions: Nutrition
)

@Serializable
data class Nutrition(
    val carbohydrates: Double,
    val protein: Double,
    val fat: Double,
    val calories: Int,
    val sugar: Double,
)
