package br.com.pelikan.xapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import br.com.pelikan.xapp.dao.IngredientDao
import br.com.pelikan.xapp.models.Order
import br.com.pelikan.xapp.models.Promotion
import br.com.pelikan.xapp.utils.PriceUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class OrderViewModel(application: Application) : BaseViewModel(application) {

    private val allOrders: LiveData<List<Order>>
    private val ingredientDao : IngredientDao = xAppDatabase.ingredientDao()

    init {
        val orderDao = xAppDatabase.orderDao()
        allOrders = orderDao.getAll()
    }

    fun getAllOrders(): LiveData<List<Order>> {
        return allOrders
    }

    fun handleExtraIngredient(order : Order, extraIngredientId : Int, extraIngredientQuantity : Int, promoList : List<Promotion>? ): Deferred<Order?> {
        return CoroutineScope(Dispatchers.IO).async {

            var extraIngredientQuantity = extraIngredientQuantity

            if(order.extraIngredientList == null){
                order.extraIngredientList = arrayListOf()
            }

            if(order.extraIngredientIdList == null){
                order.extraIngredientIdList = arrayListOf()
            }

            order.extraIngredientList!!.removeAll { ingredient ->  ingredient.id == extraIngredientId}
            order.extraIngredientIdList!!.removeAll { ingredientId ->  ingredientId == extraIngredientId}

            val ingredient = ingredientDao.getIngredient(extraIngredientId)

            if (ingredient != null) {
                while (extraIngredientQuantity > 0) {
                    order.extraIngredientList!!.add(ingredient)
                    order.extraIngredientIdList!!.add(ingredient.id)
                    extraIngredientQuantity--
                }
            }

            if(order.promoDiscountList == null){
                order.promoDiscountList = arrayListOf()
            }

            order.promoDiscountList!!.clear()
            if(promoList != null){
                for(promotion in promoList){
                    val discountPromotionList = PriceUtils.getPromoListFromOrder(order, promotion)
                    if(discountPromotionList != null) {
                        order.promoDiscountList!!.addAll(discountPromotionList)
                    }
                }
            }


            order.price = PriceUtils.getPriceFromOrder(order)
            return@async order
        }
    }
}