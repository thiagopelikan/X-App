package br.com.pelikan.xapp.viewmodel

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.test.core.app.ApplicationProvider
import br.com.pelikan.xapp.LiveDataTestUtil
import br.com.pelikan.xapp.PopulateDatabaseUtils
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class SandwichIngredientViewModelTest {


    private lateinit var viewModel: SandwichIngredientViewModel

    @Before
    fun setUp() {
        viewModel = SandwichIngredientViewModel(ApplicationProvider.getApplicationContext())

        viewModel.xAppDatabase.ingredientDao().deleteAll()
        viewModel.xAppDatabase.sandwichDao().deleteAll()
        viewModel.xAppDatabase.sandwichIngredientDao().deleteAll()

        PopulateDatabaseUtils.populateIngredients(viewModel.xAppDatabase.ingredientDao())
        PopulateDatabaseUtils.populateSandwiches(viewModel.xAppDatabase.sandwichDao())
        PopulateDatabaseUtils.populateSandwichIngredients(viewModel.xAppDatabase.sandwichIngredientDao())
    }

    @Test
    fun getAllSandwiches() {
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


        val sandwichList = LiveDataTestUtil.getValue(viewModel.getAllSandwiches())
        assertThat(sandwichList.size, CoreMatchers.`is`(4))

        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}