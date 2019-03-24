package br.com.pelikan.xapp.utils

import br.com.pelikan.xapp.models.Ingredient
import br.com.pelikan.xapp.models.Order
import br.com.pelikan.xapp.models.Promotion
import java.text.NumberFormat
import java.util.*

class PriceUtils{

    companion object {
        fun getPriceFromIngredients(ingredientList: List<Ingredient>?): Double {
            var totalPrice = 0.0

            if(ingredientList != null) {
                for (ingredient in ingredientList) {
                    totalPrice += ingredient.price!!
                }
            }

            return totalPrice;
        }

        fun getFormattedPrice(price : Double?): String{
            var priceDouble = price
            if(price == null){
                priceDouble = 0.0
            }
            val locale = Locale("pt", "BR")
            val currencyFormatter = NumberFormat.getCurrencyInstance(locale)
            return currencyFormatter.format(priceDouble)
        }

        fun getPriceFromOrder(order: Order): Double {
            var totalPrice = 0.0

            if(order.sandwich != null) {
                totalPrice += getPriceFromIngredients(order.sandwich!!.ingredientList)
            }

            if(order.extraIngredientList != null) {
                totalPrice += getPriceFromIngredients(order.extraIngredientList)
            }

            if(order.promoDiscountList != null){
                for(promotion in order.promoDiscountList!!){
                    when(promotion.id){
                        //Light
                        1 -> {
                            totalPrice *= 0.9
                        }
                        //Muita Carne
                        2 -> {
                            val ingredient = order.extraIngredientList!!.find { ingredient ->  ingredient.id == 3 }
                            totalPrice -= ingredient!!.price!!
                        }
                        //Muito Queijo
                        3 -> {
                            val ingredient = order.extraIngredientList!!.find { ingredient ->  ingredient.id == 5 }
                            totalPrice -= ingredient!!.price!!
                        }
                    }
                }
            }

            return totalPrice;
        }

        fun getPromoListFromOrder(order: Order, promotion: Promotion?): List<Promotion>? {

            var consolidatedList : MutableList<Ingredient> = mutableListOf()
            if(order.sandwich != null) {
                consolidatedList.addAll(order.sandwich!!.ingredientList)
            }

            if(order.extraIngredientList != null) {
                consolidatedList.addAll(order.extraIngredientList!!)
            }

            if((promotion == null) || (consolidatedList.isEmpty())){
                return null
            }

            when(promotion.id){
                //Light
                1 -> {
                    if((consolidatedList.contains(Ingredient(1))) && ((!consolidatedList.contains(Ingredient(2))))){
                        return arrayOf(promotion).asList()
                    }
                }
                //Muita Carne
                2 -> {
                    consolidatedList = consolidatedList.filter { ingredient -> ingredient.id == 3 }.toMutableList()
                    if(!consolidatedList.isEmpty()){
                        var foundPromos = consolidatedList.size / 3
                        val promoList : MutableList<Promotion> = mutableListOf()
                        while(foundPromos > 0){
                            promoList.add(promotion)
                            foundPromos --;
                        }

                        if(promoList.isEmpty()){
                            return null
                        }

                        return promoList
                    }
                }
                //Muito Queijo
                3 -> {
                    consolidatedList = consolidatedList.filter { ingredient -> ingredient.id == 5 }.toMutableList()
                    if(!consolidatedList.isEmpty()){
                        var foundPromos = consolidatedList.size / 3
                        val promoList : MutableList<Promotion> = mutableListOf()
                        while(foundPromos > 0){
                            promoList.add(promotion)
                            foundPromos --;
                        }

                        if(promoList.isEmpty()){
                            return null
                        }

                        return promoList
                    }
                }
            }

            return null
        }
    }

}