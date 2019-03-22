package br.com.pelikan.xapp.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.pelikan.xapp.dao.*
import br.com.pelikan.xapp.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@androidx.room.Database(entities = [Ingredient::class, Sandwich::class, SandwichIngredient::class, Promotion::class, Order::class], version = 1)
abstract class XAppDatabase : RoomDatabase() {

    abstract fun sandwichIngredientDao(): SandwichIngredientDao

    abstract fun sandwichDao(): SandwichDao

    abstract fun ingredientDao(): IngredientDao

    abstract fun promotionDao(): PromotionDao

    abstract fun orderDao(): OrderDao

    private class XAppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
//                    database.ingredientDao().deleteAll()
//                    database.sandwichDao().deleteAll()
//                    database.sandwichIngredientDao().deleteAll()
//
//                    populateIngredients(database.ingredientDao())
//                    populateSandwiches(database.sandwichDao())
//                    populateSandwichIngredients(database.sandwichIngredientDao())
                }
            }
        }

        fun populateIngredients(ingredientDao: IngredientDao) {
            var ingredient = Ingredient(1, "Alface", 0.4, "https://goo.gl/9DhCgk")
            ingredientDao.insert(ingredient)

            var ingredient2 = Ingredient(2, "Bacon", 2.0, "https://goo.gl/8qkVH0")
            ingredientDao.insert(ingredient2)

            var ingredient3 = Ingredient(3, "Hamburguer de Carne", 3.0, "https://goo.gl/U01SnT")
            ingredientDao.insert(ingredient3)

            var ingredient4 = Ingredient(4, "Ovo", 0.8, "https://goo.gl/weL1Rj")
            ingredientDao.insert(ingredient4)

            var ingredient5 = Ingredient(5, "Queijo", 1.50, "https://goo.gl/D69Ow2")
            ingredientDao.insert(ingredient5)

            var ingredient6 = Ingredient(6, "Pão com gergelim", 1.0, "https://goo.gl/evgjyj")
            ingredientDao.insert(ingredient6)
        }

        fun populateSandwiches(sandwichDao: SandwichDao) {
            var sandwich = Sandwich(1, "X-Bacon", "https://goo.gl/W9WdaF", emptyList())
            sandwichDao.insert(sandwich)

            var sandwich2 = Sandwich(2, "X-Burger", "https://goo.gl/Cjfxi9", emptyList())
            sandwichDao.insert(sandwich2)

            var sandwich3 = Sandwich(3, "X-Egg", "https://goo.gl/x4rNIq", emptyList())
            sandwichDao.insert(sandwich3)

            var sandwich4 = Sandwich(4, "X-Egg Bacon", "https://goo.gl/2L0lqg", emptyList())
            sandwichDao.insert(sandwich4)
        }

        fun populateSandwichIngredients(sandwichIngredientDao: SandwichIngredientDao) {
            sandwichIngredientDao.insert(SandwichIngredient(1, 2))
            sandwichIngredientDao.insert(SandwichIngredient(1, 3))
            sandwichIngredientDao.insert(SandwichIngredient(1, 5))
            sandwichIngredientDao.insert(SandwichIngredient(1, 6))

            sandwichIngredientDao.insert(SandwichIngredient(2, 3))
            sandwichIngredientDao.insert(SandwichIngredient(2, 5))
            sandwichIngredientDao.insert(SandwichIngredient(2, 6))

            sandwichIngredientDao.insert(SandwichIngredient(3, 3))
            sandwichIngredientDao.insert(SandwichIngredient(3, 4))
            sandwichIngredientDao.insert(SandwichIngredient(3, 5))
            sandwichIngredientDao.insert(SandwichIngredient(3, 6))

            sandwichIngredientDao.insert(SandwichIngredient(4, 1))
            sandwichIngredientDao.insert(SandwichIngredient(4, 2))
            sandwichIngredientDao.insert(SandwichIngredient(4, 3))
            sandwichIngredientDao.insert(SandwichIngredient(4, 4))
            sandwichIngredientDao.insert(SandwichIngredient(4, 5))
            sandwichIngredientDao.insert(SandwichIngredient(4, 6))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: XAppDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope): XAppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    XAppDatabase::class.java,
                    "xapp_database"
                )
                .addCallback(XAppDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}