package com.petko.fruitapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.petko.fruitapp.utils.Fruit
import com.petko.fruitapp.utils.Nutrition

@Entity(tableName = "fruits")
data class FruitDB(
    val name: String,
    @PrimaryKey
    val id: Int,
    val family: String,
    val genus: String,
    val order: String,
    val carbohydrates: Double,
    val protein: Double,
    val fat: Double,
    val calories: Double,
    val sugar: Double
) {
    fun toFruit(): Fruit {
        return Fruit(
            name = name,
            id = id,
            family = family,
            genus = genus,
            order = order,
            nutritions = Nutrition(
                carbohydrates = carbohydrates,
                protein = protein,
                fat = fat,
                calories = calories,
                sugar = sugar
            )
        )
    }
}