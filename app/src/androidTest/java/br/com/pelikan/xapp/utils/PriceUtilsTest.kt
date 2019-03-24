package br.com.pelikan.xapp.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import br.com.pelikan.xapp.models.DataContext
import br.com.pelikan.xapp.models.Ingredient
import br.com.pelikan.xapp.models.Order
import br.com.pelikan.xapp.sync.payload.DataContextResponseJson
import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PriceUtilsTest{
    private var dataContext : DataContext? = null

    @Before
    fun prepare(){
        val context = ApplicationProvider.getApplicationContext<Context>()

        val dummyDataContext = Gson().fromJson<DataContextResponseJson>(
            FileUtils.readJsonFromAssetsFile(context, "dummy_context.json"),
            DataContextResponseJson::class.java
        )
        dataContext = dummyDataContext.toObject()
    }

    @Test
    @Throws(Exception::class)
    fun testDefaultSandwichPriceIsCorrect() {
        var sandwich = dataContext!!.sandwichList!!.find { sandwich -> sandwich.id == 1 }

        var ingredientList : MutableList<Ingredient> = mutableListOf()
        for(ingredient in sandwich!!.ingredientList){
            ingredientList.add(dataContext!!.ingredientList!!.find { ingredientItem -> ingredientItem.id == ingredient.id }!!)
        }

        //ASSERT X-BACON PRICE
        assertEquals(
            PriceUtils.getPriceFromIngredients(ingredientList),
            7.5,
            0.0)

        sandwich = dataContext!!.sandwichList!!.find { sandwich -> sandwich.id == 2 }

        ingredientList = mutableListOf()
        for(ingredient in sandwich!!.ingredientList){
            ingredientList.add(dataContext!!.ingredientList!!.find { ingredientItem -> ingredientItem.id == ingredient.id }!!)
        }

        //ASSERT X-BURGER PRICE
        assertEquals(
            PriceUtils.getPriceFromIngredients(ingredientList),
            5.5,
            0.0)

        sandwich = dataContext!!.sandwichList!!.find { sandwich -> sandwich.id == 3 }

        ingredientList = mutableListOf()
        for(ingredient in sandwich!!.ingredientList){
            ingredientList.add(dataContext!!.ingredientList!!.find { ingredientItem -> ingredientItem.id == ingredient.id }!!)
        }

        //ASSERT X-EGG PRICE
        assertEquals(
            PriceUtils.getPriceFromIngredients(ingredientList),
            6.3,
            0.0)


        sandwich = dataContext!!.sandwichList!!.find { sandwich -> sandwich.id == 4 }

        ingredientList = mutableListOf()
        for(ingredient in sandwich!!.ingredientList){
            ingredientList.add(dataContext!!.ingredientList!!.find { ingredientItem -> ingredientItem.id == ingredient.id }!!)
        }

        //ASSERT X-EGG BACON PRICE
        assertEquals(
            PriceUtils.getPriceFromIngredients(ingredientList),
            8.7,
            0.0)


    }

    @Test
    @Throws(Exception::class)
    fun testDefaultSandwichDoesNotContainsPromo() {
        var sandwich = dataContext!!.sandwichList!!.find { sandwich -> sandwich.id == 1 }

        var ingredientList : MutableList<Ingredient> = mutableListOf()
        for(ingredient in sandwich!!.ingredientList){
            ingredientList.add(dataContext!!.ingredientList!!.find { ingredientItem -> ingredientItem.id == ingredient.id }!!)
        }

        var order = Order()
        order.sandwich = sandwich

        //ASSERT X-BACON DOES NOT CONTAINS PROMO
        for(promotion in dataContext!!.promoList!!){
            assertNull(PriceUtils.getPromoListFromOrder(order, promotion))
        }

        sandwich = dataContext!!.sandwichList!!.find { sandwich -> sandwich.id == 2 }

        ingredientList = mutableListOf()
        for(ingredient in sandwich!!.ingredientList){
            ingredientList.add(dataContext!!.ingredientList!!.find { ingredientItem -> ingredientItem.id == ingredient.id }!!)
        }

        order = Order()
        order.sandwich = sandwich

        //ASSERT X-BURGER DOES NOT CONTAINS PROMO
        for(promotion in dataContext!!.promoList!!){
            assertNull(PriceUtils.getPromoListFromOrder(order, promotion))
        }

        sandwich = dataContext!!.sandwichList!!.find { sandwich -> sandwich.id == 3 }

        ingredientList = mutableListOf()
        for(ingredient in sandwich!!.ingredientList){
            ingredientList.add(dataContext!!.ingredientList!!.find { ingredientItem -> ingredientItem.id == ingredient.id }!!)
        }

        order = Order()
        order.sandwich = sandwich

        //ASSERT X-EGG DOES NOT CONTAINS PROMO
        for(promotion in dataContext!!.promoList!!){
            assertNull(PriceUtils.getPromoListFromOrder(order, promotion))
        }


        sandwich = dataContext!!.sandwichList!!.find { sandwich -> sandwich.id == 4 }

        ingredientList = mutableListOf()
        for(ingredient in sandwich!!.ingredientList){
            ingredientList.add(dataContext!!.ingredientList!!.find { ingredientItem -> ingredientItem.id == ingredient.id }!!)
        }

        order = Order()
        order.sandwich = sandwich

        //ASSERT X-EGG BACON DOES NOT CONTAINS PROMO
        for(promotion in dataContext!!.promoList!!){
            assertNull(PriceUtils.getPromoListFromOrder(order, promotion))
        }
    }

    @Test
    @Throws(Exception::class)
    fun testPriceForPromoMuitaCarne() {
        var sandwich = dataContext!!.sandwichList!!.find { sandwich -> sandwich.id == 1 }

        var ingredientList : MutableList<Ingredient> = mutableListOf()
        for(ingredient in sandwich!!.ingredientList){
            ingredientList.add(dataContext!!.ingredientList!!.find { ingredientItem -> ingredientItem.id == ingredient.id }!!)
        }
        sandwich.ingredientList = ingredientList

        var ingredientExtraList : MutableList<Ingredient> = mutableListOf()
        ingredientExtraList.add(dataContext!!.ingredientList!!.find {ingredient -> ingredient.id == 3}!!)
        ingredientExtraList.add(dataContext!!.ingredientList!!.find {ingredient -> ingredient.id == 3}!!)

        var order = Order()
        order.sandwich = sandwich
        order.extraIngredientList = ingredientExtraList

        order.promoDiscountList = mutableListOf()
        for(promotion in dataContext!!.promoList!!){
            val discountPromotionList = PriceUtils.getPromoListFromOrder(order, promotion)
            if(discountPromotionList != null) {
                order.promoDiscountList!!.addAll(discountPromotionList)
            }
        }

        assertEquals(
            PriceUtils.getPriceFromOrder(order),
            10.5,
            0.0)
    }

    @Test
    @Throws(Exception::class)
    fun testPriceForMultiplePromoMuitaCarne() {
        var sandwich = dataContext!!.sandwichList!!.find { sandwich -> sandwich.id == 1 }

        var ingredientList : MutableList<Ingredient> = mutableListOf()
        for(ingredient in sandwich!!.ingredientList){
            ingredientList.add(dataContext!!.ingredientList!!.find { ingredientItem -> ingredientItem.id == ingredient.id }!!)
        }
        sandwich.ingredientList = ingredientList

        var ingredientExtraList : MutableList<Ingredient> = mutableListOf()
        ingredientExtraList.add(dataContext!!.ingredientList!!.find {ingredient -> ingredient.id == 3}!!)
        ingredientExtraList.add(dataContext!!.ingredientList!!.find {ingredient -> ingredient.id == 3}!!)
        ingredientExtraList.add(dataContext!!.ingredientList!!.find {ingredient -> ingredient.id == 3}!!)
        ingredientExtraList.add(dataContext!!.ingredientList!!.find {ingredient -> ingredient.id == 3}!!)
        ingredientExtraList.add(dataContext!!.ingredientList!!.find {ingredient -> ingredient.id == 3}!!)

        var order = Order()
        order.sandwich = sandwich
        order.extraIngredientList = ingredientExtraList

        order.promoDiscountList = mutableListOf()
        for(promotion in dataContext!!.promoList!!){
            val discountPromotionList = PriceUtils.getPromoListFromOrder(order, promotion)
            if(discountPromotionList != null) {
                order.promoDiscountList!!.addAll(discountPromotionList)
            }
        }

        assertEquals(
            PriceUtils.getPriceFromOrder(order),
            16.5,
            0.0)
    }

    @Test
    @Throws(Exception::class)
    fun testPriceForPromoMuitoQueijo() {
        var sandwich = dataContext!!.sandwichList!!.find { sandwich -> sandwich.id == 1 }

        var ingredientList : MutableList<Ingredient> = mutableListOf()
        for(ingredient in sandwich!!.ingredientList){
            ingredientList.add(dataContext!!.ingredientList!!.find { ingredientItem -> ingredientItem.id == ingredient.id }!!)
        }
        sandwich.ingredientList = ingredientList

        var ingredientExtraList : MutableList<Ingredient> = mutableListOf()
        ingredientExtraList.add(dataContext!!.ingredientList!!.find {ingredient -> ingredient.id == 5}!!)
        ingredientExtraList.add(dataContext!!.ingredientList!!.find {ingredient -> ingredient.id == 5}!!)

        var order = Order()
        order.sandwich = sandwich
        order.extraIngredientList = ingredientExtraList

        order.promoDiscountList = mutableListOf()
        for(promotion in dataContext!!.promoList!!){
            val discountPromotionList = PriceUtils.getPromoListFromOrder(order, promotion)
            if(discountPromotionList != null) {
                order.promoDiscountList!!.addAll(discountPromotionList)
            }
        }

        assertEquals(
            PriceUtils.getPriceFromOrder(order),
            9.0,
            0.0)
    }

    @Test
    @Throws(Exception::class)
    fun testPriceForPromoLight() {
        var sandwich = dataContext!!.sandwichList!!.find { sandwich -> sandwich.id == 2 }

        var ingredientList : MutableList<Ingredient> = mutableListOf()
        for(ingredient in sandwich!!.ingredientList){
            ingredientList.add(dataContext!!.ingredientList!!.find { ingredientItem -> ingredientItem.id == ingredient.id }!!)
        }
        sandwich.ingredientList = ingredientList

        var ingredientExtraList : MutableList<Ingredient> = mutableListOf()
        ingredientExtraList.add(dataContext!!.ingredientList!!.find {ingredient -> ingredient.id == 1}!!)

        var order = Order()
        order.sandwich = sandwich
        order.extraIngredientList = ingredientExtraList

        order.promoDiscountList = mutableListOf()
        for(promotion in dataContext!!.promoList!!){
            val discountPromotionList = PriceUtils.getPromoListFromOrder(order, promotion)
            if(discountPromotionList != null) {
                order.promoDiscountList!!.addAll(discountPromotionList)
            }
        }

        assertEquals(
            PriceUtils.getPriceFromOrder(order),
            5.31,
            0.1)
    }

}