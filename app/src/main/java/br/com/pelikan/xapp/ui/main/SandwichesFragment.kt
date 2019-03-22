package br.com.pelikan.xapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.pelikan.xapp.ui.main.adapter.SandwichAdapter
import br.com.pelikan.xapp.viewmodel.SandwichIngredientViewModel
import kotlinx.android.synthetic.main.fragment_main.*


class SandwichesFragment: BaseFragment()  {

    private lateinit var mAdapter : SandwichAdapter
    private lateinit var mSandwichIngredientViewModel: SandwichIngredientViewModel

    companion object {
        fun newInstance(): SandwichesFragment {
            return SandwichesFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = SandwichAdapter(context!!.applicationContext, mutableListOf())
        mainRecyclerView.adapter = mAdapter
        mainRecyclerView.layoutManager = LinearLayoutManager(context!!.applicationContext) as RecyclerView.LayoutManager?

        mSandwichIngredientViewModel = ViewModelProviders.of(this).get(SandwichIngredientViewModel::class.java)

        mSandwichIngredientViewModel.getAllSandwiches().observe(viewLifecycleOwner, Observer { sandwichList ->
            if (sandwichList.isNotEmpty()) {
                hideEmptyLayout()
                mAdapter.refresh(sandwichList.toMutableList())
                mainRecyclerView.scheduleLayoutAnimation();
            } else {
                showEmptyLayout()
            }
        })
    }

}