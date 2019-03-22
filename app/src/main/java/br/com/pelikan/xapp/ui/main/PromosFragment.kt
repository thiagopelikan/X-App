package br.com.pelikan.xapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.pelikan.xapp.R
import br.com.pelikan.xapp.ui.main.adapter.PromosAdapter
import br.com.pelikan.xapp.viewmodel.PromoViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class PromosFragment : BaseFragment() {

    private lateinit var adapter : PromosAdapter
    private lateinit var promosViewModel: PromoViewModel
    private var shouldSkipAnimation : Boolean = false

    companion object {
        fun newInstance(): PromosFragment {
            return PromosFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PromosAdapter(context!!.applicationContext, mutableListOf())
        mainRecyclerView.adapter = adapter
        mainRecyclerView.layoutManager = LinearLayoutManager(context!!.applicationContext) as RecyclerView.LayoutManager?

        promosViewModel = ViewModelProviders.of(this).get(PromoViewModel::class.java)

        promosViewModel.getAllPromos().observe(viewLifecycleOwner, Observer { promoList ->
            if(promoList.isNotEmpty()){
                hideEmptyLayout()
                adapter.refresh(promoList.toMutableList())
                if(!shouldSkipAnimation) {
                    mainRecyclerView.scheduleLayoutAnimation();
                    shouldSkipAnimation = true
                }
            }else {
                showEmptyLayout()
            }
        })
    }

    override fun getLayout(): Int{
        return R.layout.fragment_main_promo
    }
}