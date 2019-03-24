package br.com.pelikan.xapp.policies

import br.com.pelikan.xapp.models.Order

class OrderPolicy{
    companion object {
        fun getSandwichRealName(order : Order) : String?{
            return if((order.extraIngredientList != null) && (order.extraIngredientList!!.size > 0) && (order.sandwich != null)){
                order.sandwich!!.name + " " +  "- do seu jeito"
            }else if(order.sandwich != null){
                order.sandwich!!.name
            }else{
                null
            }
        }
    }
}