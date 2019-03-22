package br.com.pelikan.xapp.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.pelikan.xapp.R
import br.com.pelikan.xapp.models.Order

class OrderAdapter
    (
        private val context: Context,
        private val orderList: MutableList<Order>
    ) : Adapter<OrderAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_order_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orderList[position]
        holder.bindView(context.applicationContext, order)
    }

    fun refresh(orderList: MutableList<Order>) {
        this.orderList.clear()
        this.orderList.addAll(orderList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(context: Context, order: Order) {
//            itemView.promoTitleTextView.text = promotion.name
//            itemView.promoDescriptionTextView.text = promotion.description
        }
    }

}