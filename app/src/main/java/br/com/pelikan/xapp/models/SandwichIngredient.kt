package br.com.pelikan.xapp.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index


@Entity(
    tableName = "sandwiches_join_ingredients",
    primaryKeys = ["ingredientId", "sandwichId"],
    foreignKeys = [
        ForeignKey(
            entity = Sandwich::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("sandwichId"),
            onDelete = CASCADE),
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("ingredientId"),
            onDelete = CASCADE)],
    indices = [Index(value = ["sandwichId", "ingredientId"])]
)
data class SandwichIngredient(val sandwichId: Int, val ingredientId: Int)