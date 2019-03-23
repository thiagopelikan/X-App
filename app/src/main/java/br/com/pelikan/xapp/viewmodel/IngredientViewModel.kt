package br.com.pelikan.xapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import br.com.pelikan.xapp.models.Ingredient

class IngredientViewModel(application: Application) : BaseViewModel(application) {

    private val allIngredients: LiveData<List<Ingredient>>

    init {
        val ingredientDao = xAppDatabase.ingredientDao()
        allIngredients = ingredientDao.getAll()
    }

    fun getAllIngredients(): LiveData<List<Ingredient>> {
        return allIngredients
    }
}