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

    private lateinit var mAdapter : PromosAdapter
    private lateinit var mPromosViewModel: PromoViewModel

    companion object {
        fun newInstance(): PromosFragment {
            return PromosFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = PromosAdapter(context!!.applicationContext, mutableListOf())
        mainRecyclerView.adapter = mAdapter
        mainRecyclerView.layoutManager = LinearLayoutManager(context!!.applicationContext) as RecyclerView.LayoutManager?

        mPromosViewModel = ViewModelProviders.of(this).get(PromoViewModel::class.java)

        mPromosViewModel.getAllPromos().observe(viewLifecycleOwner, Observer { promoList ->
            if(promoList.isNotEmpty()){
                hideEmptyLayout()
                mAdapter.refresh(promoList.toMutableList())
                mainRecyclerView.scheduleLayoutAnimation();
            }else {
                showEmptyLayout()
            }
        })
    }

    override fun getLayout(): Int{
        return R.layout.fragment_main_promo
    }
}