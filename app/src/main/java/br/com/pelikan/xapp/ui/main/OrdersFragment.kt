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

    private lateinit var adapter : OrderAdapter
    private lateinit var orderViewModel: OrderViewModel
    private var shouldSkipAnimation : Boolean = false

    companion object {
        fun newInstance(): OrdersFragment {
            return OrdersFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = OrderAdapter(context!!.applicationContext, mutableListOf())
        mainRecyclerView.adapter = adapter
        mainRecyclerView.layoutManager = LinearLayoutManager(context!!.applicationContext) as RecyclerView.LayoutManager?

        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel::class.java)

        orderViewModel.getAllOrders().observe(viewLifecycleOwner, Observer { orderList ->
            if(orderList.isNotEmpty()){
                hideEmptyLayout()
                adapter.refresh(orderList.toMutableList())
                if(!shouldSkipAnimation) {
                    mainRecyclerView.scheduleLayoutAnimation();
                    shouldSkipAnimation = true
                }
            }else {
                showEmptyLayout()
            }
        })
    }
}