package br.com.pelikan.xapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import br.com.pelikan.xapp.dao.SandwichDao
import br.com.pelikan.xapp.dao.SandwichIngredientDao
import br.com.pelikan.xapp.models.Sandwich
import br.com.pelikan.xapp.utils.IngredientUtils

class SandwichIngredientViewModel(application: Application) : BaseViewModel(application) {

    private val sandwichIngredientDao : SandwichIngredientDao = xAppDatabase.sandwichIngredientDao()
    private val sandwichDao : SandwichDao = xAppDatabase.sandwichDao()
    private val allSandwiches: LiveData<List<Sandwich>>
    private val allSandwichesWithIngredients = MediatorLiveData<List<Sandwich>>()

    init {
        allSandwiches = sandwichDao.getAll()
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
        sandwich.price = IngredientUtils.getPriceFromIngredients(sandwich.ingredientList);
        return sandwich
    }

    fun getAllSandwiches(): LiveData<List<Sandwich>> {
        return allSandwichesWithIngredients
    }
}