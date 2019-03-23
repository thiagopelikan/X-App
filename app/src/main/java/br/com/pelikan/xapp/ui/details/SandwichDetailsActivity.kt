package br.com.pelikan.xapp.ui.details

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.pelikan.xapp.R
import br.com.pelikan.xapp.models.Order
import br.com.pelikan.xapp.models.Promotion
import br.com.pelikan.xapp.sync.worker.SyncWorkerUtils
import br.com.pelikan.xapp.ui.BaseActivity
import br.com.pelikan.xapp.ui.main.`interface`.IngredientItemOnChangeListener
import br.com.pelikan.xapp.ui.main.adapter.IngredientsDetailAdapter
import br.com.pelikan.xapp.ui.main.adapter.PromoDetailsAdapter
import br.com.pelikan.xapp.utils.GlideApp
import br.com.pelikan.xapp.utils.PriceUtils
import br.com.pelikan.xapp.viewmodel.IngredientViewModel
import br.com.pelikan.xapp.viewmodel.OrderViewModel
import br.com.pelikan.xapp.viewmodel.PromoViewModel
import br.com.pelikan.xapp.viewmodel.SandwichIngredientViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_sandwich_details.*
import kotlinx.android.synthetic.main.content_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class SandwichDetailsActivity : BaseActivity() {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    val scope = CoroutineScope(coroutineContext)

    private lateinit var ingredientsDetailAdapter : IngredientsDetailAdapter
    private lateinit var promoDetailAdapter : PromoDetailsAdapter

    private lateinit var orderViewModel: OrderViewModel
    private lateinit var ingredientViewModel: IngredientViewModel
    private lateinit var promoViewModel: PromoViewModel
    private lateinit var sandwichIngredientViewModel: SandwichIngredientViewModel

    private lateinit var promoList : List<Promotion>

    private var order: Order = Order()

    private var sandwichId : Int = 0
    private val outAnimation = AlphaAnimation(1.0f, 0.0f)
    private val inAnimation = AlphaAnimation(0.0f, 1.0f)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sandwich_details)

        postponeEnterTransition();

        injectExtras()

        ingredientsDetailAdapter = IngredientsDetailAdapter(applicationContext, mutableListOf(), object : IngredientItemOnChangeListener {
            override fun onItemChange(itemId: Int, quantity: Int) {

                scope.launch {
                    val changedOrder =  orderViewModel.handleExtraIngredient(order, itemId, quantity, promoList).await()

                    if(changedOrder == null){
                        //SOMETHING GETS WRONG
                        finish()
                    }
                    order = changedOrder!!
                    updateValues()
                }
            }

        })
        detailsSandwichExtraIngredientsRecyclerView.adapter = ingredientsDetailAdapter
        detailsSandwichExtraIngredientsRecyclerView.layoutManager = LinearLayoutManager(applicationContext) as RecyclerView.LayoutManager?

        promoDetailAdapter = PromoDetailsAdapter(applicationContext, mutableListOf())
        detailsSandwichDiscountRecyclerView.adapter = promoDetailAdapter
        detailsSandwichDiscountRecyclerView.layoutManager = LinearLayoutManager(applicationContext) as RecyclerView.LayoutManager?

        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel::class.java)
        sandwichIngredientViewModel = ViewModelProviders.of(this).get(SandwichIngredientViewModel::class.java)
        promoViewModel = ViewModelProviders.of(this).get(PromoViewModel::class.java)
        ingredientViewModel = ViewModelProviders.of(this).get(IngredientViewModel::class.java)

        promoViewModel.getAllPromos().observe(this, Observer { promoList ->
            this.promoList = promoList
        })

        detailsTotalPriceButton.setOnClickListener {
            SyncWorkerUtils.callWorker(order)
            //TODO SHOW PROGRESS BAR
            //TODO WAIT ADD PROCESS
        }

        scope.launch {
            val sandwich =  sandwichIngredientViewModel.getSandwichAsync(sandwichId).await()

            if(sandwich == null){
                //SOMETHING GETS WRONG
                finish()
            }
            order.sandwich = sandwich

            initializeHeader()
            initializeContent()

            startPostponedEnterTransition()
        }
    }

    private fun initializeContent() {

        ingredientViewModel.getAllIngredients().observe(this, Observer { ingredientsList ->
            if(ingredientsList.isNotEmpty()){
                ingredientsDetailAdapter.refresh(ingredientsList.toMutableList())
            }
        })

        updateValues()

    }

    private fun initializeHeader() {

        detailsCollapsingToolbar.setContentScrimColor(getColor(R.color.colorPrimary))
        inAnimation.duration = 200
        outAnimation.duration = 500

        detailsAppBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) == appBarLayout.totalScrollRange) {
                animation(detailsToolbarContainer, false)
            } else if (verticalOffset == 0) {
                animation(detailsToolbarContainer, true)
            }
        })

        detailsToolbarTitle.text = ""
        detailsToolbarTitle.visibility = View.GONE

        setSupportActionBar(detailsToolbar)
        supportActionBar?.title = order.sandwich?.name

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        detailsToolbar.setNavigationOnClickListener { onBackPressed() }

        detailsHeaderImageView.post {
            GlideApp
                .with(this)
                .load(order.sandwich?.image)
                .placeholder(R.drawable.loading_placeholder)
                .error(R.drawable.error_placeholder)
                .override(detailsHeaderImageView.width, detailsHeaderImageView.height)
                .centerInside()
                .into(detailsHeaderImageView)
        }
    }

    private fun injectExtras() {
        val extras = intent.extras
        if (extras != null) {
            if (extras.containsKey(TARGET_SANDWICH_ID)) {
                this.sandwichId = extras.getInt(TARGET_SANDWICH_ID)
            }
        }
    }

    private fun updateValues(){
        detailsSandwichNameTextView.text = order.sandwich?.name
        detailsSandwichIngredientsTextView.text = android.text.TextUtils.join(", ", order.sandwich?.ingredientList)
        detailsSandwichIngredientsPriceTextView.text = PriceUtils.getFormattedPrice(PriceUtils.getPriceFromIngredients(order.sandwich?.ingredientList))
        detailsSandwichExtraIngredientsPriceTextView.text = PriceUtils.getFormattedPrice(PriceUtils.getPriceFromIngredients(order.extraIngredientList))

        promoDetailAdapter.refresh(order.promoDiscountList)
        detailsTotalPriceButton.text = getString(R.string.add_order) + " " + PriceUtils.getFormattedPrice(order.price);
    }

    private fun animation(view: View, isIn: Boolean) {
        if (isIn && view.visibility == View.VISIBLE)
            return
        if (!isIn && view.visibility == View.INVISIBLE)
            return

        view.startAnimation(if (isIn) inAnimation else outAnimation)
        if (isIn)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.INVISIBLE
    }

    companion object {
        const val TARGET_SANDWICH_ID = "sandwichId"
    }
}
