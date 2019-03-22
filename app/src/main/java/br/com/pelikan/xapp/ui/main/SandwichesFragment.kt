package br.com.pelikan.xapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.pelikan.xapp.ui.details.SandwichDetailsActivity
import br.com.pelikan.xapp.ui.main.`interface`.ItemClickListener
import br.com.pelikan.xapp.ui.main.adapter.SandwichAdapter
import br.com.pelikan.xapp.viewmodel.SandwichIngredientViewModel
import kotlinx.android.synthetic.main.fragment_main.*


class SandwichesFragment: BaseFragment() {

    private lateinit var mAdapter : SandwichAdapter
    private lateinit var mSandwichIngredientViewModel: SandwichIngredientViewModel
    private var shouldSkipAnimation : Boolean = false

    companion object {
        fun newInstance(): SandwichesFragment {
            return SandwichesFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = SandwichAdapter(context!!.applicationContext, mutableListOf(), object : ItemClickListener {
            override fun onItemClick(itemId: Int) {
                val intent = Intent(activity, SandwichDetailsActivity::class.java)
                startActivity(intent)
            }
        })
        mainRecyclerView.adapter = mAdapter
        mainRecyclerView.layoutManager = LinearLayoutManager(context!!.applicationContext) as RecyclerView.LayoutManager?

        mSandwichIngredientViewModel = ViewModelProviders.of(this).get(SandwichIngredientViewModel::class.java)

        mSandwichIngredientViewModel.getAllSandwiches().observe(viewLifecycleOwner, Observer { sandwichList ->
            if (sandwichList.isNotEmpty()) {
                hideEmptyLayout()
                mAdapter.refresh(sandwichList.toMutableList())
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