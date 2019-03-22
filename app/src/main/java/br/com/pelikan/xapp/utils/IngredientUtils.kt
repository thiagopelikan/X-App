package br.com.pelikan.xapp.utils

import br.com.pelikan.xapp.models.Ingredient

class IngredientUtils{

    companion object {
        fun getPriceFromIngredients(ingredientList: List<Ingredient>): Double {
            var totalPrice = 0.0

            for(ingredient in ingredientList){
                totalPrice += ingredient.price!!
            }

            return totalPrice;
        }
    }

}