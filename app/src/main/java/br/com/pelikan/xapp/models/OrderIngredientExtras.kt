package br.com.pelikan.xapp.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "order_join_ingredients",
    foreignKeys = [
        ForeignKey(
            entity = Order::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("orderId"),
            onDelete = CASCADE),
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("ingredientId"),
            onDelete = CASCADE)],
    indices = [Index(value = ["orderId", "ingredientId"])]
)
data class OrderIngredientExtras(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val orderId: Int,
    val ingredientId: Int)
{
    constructor(orderId: Int, ingredientId: Int) : this(0, orderId, ingredientId)
}