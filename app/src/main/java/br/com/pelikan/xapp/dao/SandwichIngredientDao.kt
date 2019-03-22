package br.com.pelikan.xapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.RoomWarnings
import br.com.pelikan.xapp.models.Ingredient
import br.com.pelikan.xapp.models.SandwichIngredient

@Dao
interface SandwichIngredientDao {

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("""
        SELECT * FROM ingredients INNER JOIN sandwiches_join_ingredients ON
        ingredients.id = sandwiches_join_ingredients.ingredientId WHERE
        sandwiches_join_ingredients.sandwichId = :sandwichId
        """)
    fun getIngredientsFromSandwich(sandwichId: Int): List<Ingredient>

    @Query("DELETE from sandwiches_join_ingredients")
    fun deleteAll()

    @Insert(onConflict = REPLACE)
    fun insert(sandwichIngredient: SandwichIngredient);
}