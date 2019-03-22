package br.com.pelikan.xapp.utils

import br.com.pelikan.xapp.models.Ingredient
import java.text.NumberFormat
import java.util.*

class NumberUtils{

    companion object {
        fun getFormattedPrice(price : Double): String{
            val locale = Locale("pt", "BR")
            val currencyFormatter = NumberFormat.getCurrencyInstance(locale)
            return currencyFormatter.format(price)
        }
    }

}