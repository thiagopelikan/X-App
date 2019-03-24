package br.com.pelikan.xapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import br.com.pelikan.xapp.dao.IngredientDao
import br.com.pelikan.xapp.dao.OrderIngredientExtrasDao
import br.com.pelikan.xapp.dao.SandwichDao
import br.com.pelikan.xapp.dao.SandwichIngredientDao
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
    private val sandwichIngredientDao : SandwichIngredientDao = xAppDatabase.sandwichIngredientDao()
    private val orderIngredientDao : OrderIngredientExtrasDao = xAppDatabase.orderIngredientExtras()
    private val sandwichDao : SandwichDao = xAppDatabase.sandwichDao()
    private val allOrdersWithSandwichAndIngredients = MediatorLiveData<List<Order>>()
    init {
        val orderDao = xAppDatabase.orderDao()
        allOrders = orderDao.getAll()
        allOrdersWithSandwichAndIngredients.addSource(allOrders
        ) { ordersListLive ->
            if (ordersListLive != null) {
                Thread(kotlinx.coroutines.Runnable {
                    for (order in ordersListLive) {
                        order.sandwich = sandwichDao.getSandwich(order.sandwichId)
                        order.sandwich!!.ingredientList = sandwichIngredientDao.getIngredientsFromSandwich(order.sandwichId)
                        order.sandwich!!.price = PriceUtils.getPriceFromIngredients(order.sandwich!!.ingredientList)

                        order.extraIngredientList = orderIngredientDao.getExtraIngredientsFromOrder(order.id).toMutableList()
                    }
                    allOrdersWithSandwichAndIngredients.postValue(ordersListLive)
                }).start()
            } else {
                allOrdersWithSandwichAndIngredients.setValue(null)
            }
        }
    }

    fun getAllOrders(): LiveData<List<Order>> {
        return allOrdersWithSandwichAndIngredients
    }

    fun handleExtraIngredientAsync(order : Order, extraIngredientId : Int, extraIngredientQuantity : Int, promoList : List<Promotion>? ): Deferred<Order?> {
        return CoroutineScope(Dispatchers.IO).async {

            var extraIngredientQty = extraIngredientQuantity

            if(order.extraIngredientList == null){
                order.extraIngredientList = arrayListOf()
            }

            order.extraIngredientList!!.removeAll { ingredient ->  ingredient.id == extraIngredientId}
            val ingredient = ingredientDao.getIngredient(extraIngredientId)

            if (ingredient != null) {
                while (extraIngredientQty > 0) {
                    order.extraIngredientList!!.add(ingredient)
                    extraIngredientQty --
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