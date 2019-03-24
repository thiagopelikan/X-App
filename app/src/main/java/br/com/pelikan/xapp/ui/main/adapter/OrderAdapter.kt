package br.com.pelikan.xapp.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.pelikan.xapp.R
import br.com.pelikan.xapp.models.Order
import br.com.pelikan.xapp.policies.OrderPolicy
import br.com.pelikan.xapp.utils.GlideApp
import br.com.pelikan.xapp.utils.PriceUtils
import kotlinx.android.synthetic.main.layout_order_item.view.*

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
            itemView.orderNameAndPriceTextView.text = OrderPolicy.getSandwichRealName(order)
            itemView.orderIngredientsTextView.text = android.text.TextUtils.join(", ", order.sandwich!!.ingredientList)
            itemView.orderNameAndPriceTextView.text = itemView.orderNameAndPriceTextView.text.toString() + " (" + PriceUtils.getFormattedPrice(
                order.price
            ) + ") "
            if((order.extraIngredientList != null) && (!order.extraIngredientList!!.isEmpty())) {
                itemView.orderIngredientsExtraTextView.text = "Extras: " +
                    android.text.TextUtils.join(", ", order.extraIngredientList)
                itemView.orderIngredientsExtraTextView.visibility = VISIBLE
            }else{
                itemView.orderIngredientsExtraTextView.visibility = GONE
            }
            itemView.orderImageView.post {
                GlideApp
                    .with(context)
                    .load(order.sandwich!!.image)
                    .placeholder(R.drawable.loading_placeholder)
                    .error(R.drawable.error_placeholder)
                    .override(itemView.orderImageView.width, itemView.orderImageView.height)
                    .centerInside()
                    .into(itemView.orderImageView)
            }
        }
    }

}