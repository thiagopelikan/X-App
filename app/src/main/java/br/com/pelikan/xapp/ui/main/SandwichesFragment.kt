package br.com.pelikan.xapp.ui.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.pelikan.xapp.ui.details.SandwichDetailsActivity
import br.com.pelikan.xapp.ui.main.interfaces.SandwichItemClickListener
import br.com.pelikan.xapp.ui.main.adapter.SandwichAdapter
import br.com.pelikan.xapp.viewmodel.SandwichIngredientViewModel
import kotlinx.android.synthetic.main.fragment_main.*


class SandwichesFragment: BaseFragment() {

    private lateinit var adapter : SandwichAdapter
    private lateinit var sandwichIngredientViewModel: SandwichIngredientViewModel
    private var shouldSkipAnimation : Boolean = false

    companion object {
        fun newInstance(): SandwichesFragment {
            return SandwichesFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SandwichAdapter(context!!.applicationContext, mutableListOf(), object : SandwichItemClickListener {
            override fun onItemClick(itemId: Int, transitionView : View?) {
                val intent = Intent(activity, SandwichDetailsActivity::class.java)
                intent.putExtra(SandwichDetailsActivity.TARGET_SANDWICH_ID, itemId)

                val options = ActivityOptions
                    .makeSceneTransitionAnimation(activity, transitionView, "image")

                startActivity(intent, options.toBundle())
            }
        })
        mainRecyclerView.adapter = adapter
        mainRecyclerView.layoutManager = LinearLayoutManager(context!!.applicationContext) as RecyclerView.LayoutManager?

        sandwichIngredientViewModel = ViewModelProviders.of(this).get(SandwichIngredientViewModel::class.java)

        sandwichIngredientViewModel.getAllSandwiches().observe(viewLifecycleOwner, Observer { sandwichList ->
            if (sandwichList.isNotEmpty()) {
                hideEmptyLayout()
                adapter.refresh(sandwichList.toMutableList())
                if(!shouldSkipAnimation) {
                    mainRecyclerView.scheduleLayoutAnimation();
                    shouldSkipAnimation = true
                }
            } else {
                showEmptyLayout()
            }
        })
    }
}