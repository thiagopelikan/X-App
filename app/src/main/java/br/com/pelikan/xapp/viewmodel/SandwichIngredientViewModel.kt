package br.com.pelikan.xapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import br.com.pelikan.xapp.dao.IngredientDao
import br.com.pelikan.xapp.dao.SandwichDao
import br.com.pelikan.xapp.dao.SandwichIngredientDao
import br.com.pelikan.xapp.database.XAppDatabase
import br.com.pelikan.xapp.models.Ingredient
import br.com.pelikan.xapp.models.Sandwich
import br.com.pelikan.xapp.models.SandwichIngredient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SandwichIngredientViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val sandwichIngredientDao : SandwichIngredientDao
    private val sandwichDao : SandwichDao
    private val ingredientDao : IngredientDao

    private val allSandwiches: LiveData<List<Sandwich>>
    private val allIngredient: LiveData<List<Ingredient>>

    private val allSandwichesWithIngredients = MediatorLiveData<List<Sandwich>>()

    init {
        val xAppDatabase = XAppDatabase.getDatabase(application, scope);
        sandwichIngredientDao = xAppDatabase.SandwichIngredientDao()
        sandwichDao = xAppDatabase.SandwichDao()
        ingredientDao = xAppDatabase.IngredientDao()

        allSandwiches = sandwichDao.getAll()
        allIngredient = ingredientDao.getAll()

        allSandwichesWithIngredients.addSource(allSandwiches
        ) { sandwichListLive ->
            if (sandwichListLive != null) {
                Thread(Runnable {
                    for (sandwich in sandwichListLive) {
                        addIngredientsToSandwich(sandwich)
                    }
                    allSandwichesWithIngredients.postValue(sandwichListLive)
                }).start()
            } else {
                allSandwichesWithIngredients.setValue(null)
            }
        }
    }

    private fun addIngredientsToSandwich(sandwich: Sandwich): Sandwich {
        sandwich.ingredientList = sandwichIngredientDao.getIngredientsFromSandwich(sandwich.id)
        return sandwich
    }

    fun getAllSandwiches(): LiveData<List<Sandwich>> {
        return allSandwichesWithIngredients
    }

    fun getAllIngredients(): LiveData<List<Ingredient>> {
        return allIngredient
    }

    fun insertSandwich(sandwich: Sandwich) = scope.launch(Dispatchers.IO) {
        sandwichDao.insert(sandwich)
        for (ingredient in sandwich.ingredientList) {
            val join = SandwichIngredient(sandwich.id, ingredient.id)
            sandwichIngredientDao.insert(join);
        }
        sandwichDao.insert(sandwich)
    }

    fun deleteSandwich(sandwich: Sandwich) = scope.launch(Dispatchers.IO) {
        sandwichDao.delete(sandwich)
    }

    fun deleteIngredient(ingredient: Ingredient) = scope.launch(Dispatchers.IO) {
        ingredientDao.delete(ingredient)
    }

    fun insertIngredient(ingredient: Ingredient) = scope.launch(Dispatchers.IO) {
        ingredientDao.insert(ingredient)
    }

    fun insertSandwichIngredient(sandwichIngredient: SandwichIngredient) = scope.launch(Dispatchers.IO) {
        sandwichIngredientDao.insert(sandwichIngredient)
    }
}