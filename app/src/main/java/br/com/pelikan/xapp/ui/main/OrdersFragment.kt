package br.com.pelikan.xapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.pelikan.xapp.ui.main.adapter.OrderAdapter
import br.com.pelikan.xapp.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class OrdersFragment : BaseFragment() {

    private lateinit var mAdapter : OrderAdapter
    private lateinit var mOrderViewModel: OrderViewModel

    companion object {
        fun newInstance(): OrdersFragment {
            return OrdersFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = OrderAdapter(context!!.applicationContext, mutableListOf())
        mainRecyclerView.adapter = mAdapter
        mainRecyclerView.layoutManager = LinearLayoutManager(context!!.applicationContext) as RecyclerView.LayoutManager?

        mOrderViewModel = ViewModelProviders.of(this).get(OrderViewModel::class.java)

        mOrderViewModel.getAllOrders().observe(viewLifecycleOwner, Observer { orderList ->
            if(orderList.isNotEmpty()){
                hideEmptyLayout()
                mAdapter.refresh(orderList.toMutableList())
            }else {
                showEmptyLayout()
            }
        })
    }
}