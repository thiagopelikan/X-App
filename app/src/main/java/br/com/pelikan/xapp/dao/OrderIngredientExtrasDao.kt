package br.com.pelikan.xapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.RoomWarnings
import br.com.pelikan.xapp.models.Ingredient
import br.com.pelikan.xapp.models.OrderIngredientExtras

@Dao
interface OrderIngredientExtrasDao {

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("""
        SELECT * FROM ingredients INNER JOIN order_join_ingredients ON
        ingredients.id = order_join_ingredients.ingredientId WHERE
        order_join_ingredients.orderId = :orderId
        """)
    fun getExtraIngredientsFromOrder(orderId: Int): List<Ingredient>

    @Query("DELETE from order_join_ingredients")
    fun deleteAll()

    @Insert(onConflict = REPLACE)
    fun insert(orderIngredient: OrderIngredientExtras);
}