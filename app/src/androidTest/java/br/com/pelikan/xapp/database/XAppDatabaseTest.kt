package br.com.pelikan.xapp.database

import android.content.Context
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.pelikan.xapp.LiveDataTestUtil
import br.com.pelikan.xapp.PopulateDatabaseUtils
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class XAppDatabaseTest {

    private lateinit var database: XAppDatabase

    @Before
    @Throws(Exception::class)
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            XAppDatabase::class.java
        )
        .allowMainThreadQueries()
        .build()


        database.ingredientDao().deleteAll()
        database.sandwichDao().deleteAll()
        database.sandwichIngredientDao().deleteAll()

        PopulateDatabaseUtils.populateIngredients(database.ingredientDao())
        PopulateDatabaseUtils.populateSandwiches(database.sandwichDao())
        PopulateDatabaseUtils.populateSandwichIngredients(database.sandwichIngredientDao())
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun testGetAllSandwichesSizeIsCorrect() {

        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })

        val sandwichList = LiveDataTestUtil.getValue(database.sandwichDao().getAll())
        assertThat(sandwichList.size, `is`(4))

        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}