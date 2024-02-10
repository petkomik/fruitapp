package com.petko.fruitapp.utils

import com.petko.fruitapp.data.FruitDB
import kotlinx.serialization.Serializable

@Serializable
data class Fruit(
    val name: String,
    val id: Int,
    val family: String,
    val genus: String,
    val order: String,
    val nutritions: Nutrition
) {
    fun toFruitDB(): FruitDB {
        return FruitDB(
            name = name,
            id = id,
            family = family,
            genus = genus,
            order = order,
            carbohydrates = nutritions.carbohydrates,
            protein = nutritions.protein,
            fat = nutritions.fat,
            calories = nutritions.calories,
            sugar = nutritions.sugar
        )
    }
}

@Serializable
data class Nutrition(
    val carbohydrates: Double,
    val protein: Double,
    val fat: Double,
    val calories: Double,
    val sugar: Double,
)
