package br.com.pelikan.xapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import br.com.pelikan.xapp.dao.SandwichDao
import br.com.pelikan.xapp.dao.SandwichIngredientDao
import br.com.pelikan.xapp.models.Sandwich
import br.com.pelikan.xapp.utils.PriceUtils
import kotlinx.coroutines.*

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
                        updateIngredientsAndPriceForSandwich(sandwich)
                    }
                    allSandwichesWithIngredients.postValue(sandwichListLive)
                }).start()
            } else {
                allSandwichesWithIngredients.setValue(null)
            }
        }
    }

    fun updateIngredientsAndPriceForSandwich(sandwich: Sandwich){
        sandwich.ingredientList = sandwichIngredientDao.getIngredientsFromSandwich(sandwich.id)
        sandwich.price = PriceUtils.getPriceFromIngredients(sandwich.ingredientList)
    }

    fun getAllSandwiches(): LiveData<List<Sandwich>> {
        return allSandwichesWithIngredients
    }

    fun getSandwichAsync(sandwichId : Int): Deferred<Sandwich?>{
        return CoroutineScope(Dispatchers.IO).async {
            val sandwich = sandwichDao.getSandwich(sandwichId)
            return@async (if (sandwich != null) {
                updateIngredientsAndPriceForSandwich(sandwich)
                sandwich
            } else {
                null
            })
        }
    }
}