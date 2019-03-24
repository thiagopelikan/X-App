package br.com.pelikan.xapp.ui.details

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import androidx.work.WorkManager
import br.com.pelikan.xapp.R
import br.com.pelikan.xapp.models.Order
import br.com.pelikan.xapp.models.Promotion
import br.com.pelikan.xapp.policies.OrderPolicy
import br.com.pelikan.xapp.sync.worker.SyncWorkerUtils
import br.com.pelikan.xapp.ui.BaseActivity
import br.com.pelikan.xapp.ui.details.adapter.IngredientDetailAdapter
import br.com.pelikan.xapp.ui.details.adapter.PromoDetailsAdapter
import br.com.pelikan.xapp.ui.details.interfaces.IngredientItemOnChangeListener
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

    private lateinit var ingredientDetailAdapter : IngredientDetailAdapter
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

        ingredientDetailAdapter = IngredientDetailAdapter(
            applicationContext,
            mutableListOf(),
            object : IngredientItemOnChangeListener {
                override fun onItemChange(itemId: Int, quantity: Int) {

                    scope.launch {
                        val changedOrder =
                            orderViewModel.handleExtraIngredientAsync(order, itemId, quantity, promoList).await()

                        if (changedOrder == null) {
                            //SOMETHING GETS WRONG
                            finish()
                        }
                        order = changedOrder!!
                        updateValues()
                    }
                }

            })
        detailsSandwichExtraIngredientsRecyclerView.adapter = ingredientDetailAdapter
        detailsSandwichExtraIngredientsRecyclerView.layoutManager = LinearLayoutManager(applicationContext)

        promoDetailAdapter =
            PromoDetailsAdapter(applicationContext, mutableListOf())
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
            detailsProgressLayout.visibility = View.VISIBLE
            WorkManager.getInstance().getWorkInfoByIdLiveData( SyncWorkerUtils.callWorker(order)).observe(this, Observer { workInfo ->
                if(workInfo.state == WorkInfo.State.SUCCEEDED){
                    Toast.makeText(this, getString(R.string.order_added), Toast.LENGTH_SHORT).show()
                    finish()
                }else if((workInfo.state == WorkInfo.State.FAILED) || (workInfo.state == WorkInfo.State.BLOCKED) || (workInfo.state == WorkInfo.State.CANCELLED)){
                    Toast.makeText(this, getString(R.string.order_add_error), Toast.LENGTH_SHORT).show()
                    detailsProgressLayout.visibility = View.GONE
                }
            })
        }

        scope.launch {
            val sandwich =  sandwichIngredientViewModel.getSandwichAsync(sandwichId).await()

            if(sandwich == null){
                //SOMETHING GETS WRONG
                finish()
            }
            order.sandwich = sandwich
            order.sandwichId = sandwich!!.id
            initializeHeader()
            initializeContent()

            startPostponedEnterTransition()
        }
    }

    private fun initializeContent() {

        ingredientViewModel.getAllIngredients().observe(this, Observer { ingredientsList ->
            if(ingredientsList.isNotEmpty()){
                ingredientDetailAdapter.refresh(ingredientsList.toMutableList())
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
        supportActionBar?.title = OrderPolicy.getSandwichRealName(order)

        detailsSandwichNameTextView.text = OrderPolicy.getSandwichRealName(order)
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
