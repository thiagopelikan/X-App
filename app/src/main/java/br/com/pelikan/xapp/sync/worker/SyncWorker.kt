package br.com.pelikan.xapp.sync.worker

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.com.pelikan.xapp.XAppApplication
import br.com.pelikan.xapp.XAppApplication.Companion.PREFS_LAST_UPDATE
import br.com.pelikan.xapp.dao.*
import br.com.pelikan.xapp.database.XAppDatabase
import br.com.pelikan.xapp.models.*
import br.com.pelikan.xapp.sync.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import kotlin.coroutines.CoroutineContext

class SyncWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private var sandwichIngredientDao : SandwichIngredientDao
    private var sandwichDao : SandwichDao
    private var ingredientDao : IngredientDao
    private var promotionDao : PromotionDao
    private var orderDao : OrderDao

    private var order: Order? = null

    init {
        val xAppDatabase = XAppDatabase.getDatabase(this.applicationContext, scope);
        sandwichIngredientDao = xAppDatabase.sandwichIngredientDao()
        sandwichDao = xAppDatabase.sandwichDao()
        ingredientDao = xAppDatabase.ingredientDao()
        promotionDao = xAppDatabase.promotionDao()
        orderDao = xAppDatabase.orderDao()

//        if(workerParams.inputData.)
//        val extraDate = workerParams.inputData.getLong(DATE_EXTRA_WORKER, 0)
//        date = Date(extraDate)
    }

    override fun doWork(): Result {

        var hasError = false
        val countDownLatch = CountDownLatch(1)

        val lastUpdate = XAppApplication.getSharedPreferences().getLong(PREFS_LAST_UPDATE, 0);
        val subscribe = Repository.with().forSync().sync(lastUpdate, order).subscribe(
            { dataContext ->
                    Log.d("TAG", "YES! WE DID IT! " + dataContext.lastUpdate);
                    scope.launch(Dispatchers.IO) {

                        if(dataContext != null){
                            insertIngredientList(dataContext.ingredientList)
                            insertSandwichList(dataContext.sandwichList)
                            insertPromoList(dataContext.promoList)
                            insertOrderList(dataContext.orderList)

                            XAppApplication.getSharedPreferences().edit().putLong(PREFS_LAST_UPDATE, dataContext.lastUpdate).apply()
                        }
                    }

                    countDownLatch.countDown()
            },
            { error ->
                    Log.d("TAG", "OPS " + error.message);
                    hasError = true
                    countDownLatch.countDown()
            })

        try {
            countDownLatch.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return if (hasError) {
            ListenableWorker.Result.failure()
        } else {
            ListenableWorker.Result.success()
        }
    }


    private fun insertIngredientList(ingredientList: List<Ingredient>?){
        if(ingredientList != null) {
            ingredientDao.deleteAll()
            ingredientDao.insertAll(ingredientList)
        }
    }

    private fun insertSandwichList(sandwichList: List<Sandwich>?){
        if(sandwichList != null) {
            sandwichDao.deleteAll()
            for (sandwich in sandwichList) {
                insertSandwich(sandwich)
            }
        }
    }

    private fun insertSandwich(sandwich: Sandwich) {
        sandwichDao.insert(sandwich)
        for (ingredientId in sandwich.ingredientIdList) {
            val join = SandwichIngredient(sandwich.id, ingredientId)
            sandwichIngredientDao.insert(join);
        }
    }

    private fun insertPromoList(promoList: List<Promotion>?){
        if(promoList != null) {
            promotionDao.deleteAll()
            promotionDao.insertAll(promoList)
        }
    }

    private fun insertOrderList(orderList: List<Order>?){
        if(orderList != null) {
            orderDao.deleteAll()
            orderDao.insertAll(orderList)
        }
    }

    companion object {
        const val ORDER_EXTRA_WORKER = "order_extra_worker"
    }
}