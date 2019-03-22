package br.com.pelikan.xapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.pelikan.xapp.models.Promotion

@Dao
interface PromotionDao {

    @Query("SELECT * from promotions")
    fun getAll(): LiveData<List<Promotion>>

    @Insert(onConflict = REPLACE)
    fun insert(promotion: Promotion)

    @Insert(onConflict = REPLACE)
    fun insertAll(promotionList: List<Promotion>)

    @Query("DELETE from promotions")
    fun deleteAll()

    @Delete
    fun delete(promotion: Promotion)

}