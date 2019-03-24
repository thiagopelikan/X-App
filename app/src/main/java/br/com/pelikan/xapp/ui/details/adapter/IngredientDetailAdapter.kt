package br.com.pelikan.xapp.ui.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.pelikan.xapp.R
import br.com.pelikan.xapp.models.Ingredient
import br.com.pelikan.xapp.ui.details.interfaces.IngredientItemOnChangeListener
import br.com.pelikan.xapp.utils.PriceUtils
import kotlinx.android.synthetic.main.layout_ingredient_details_item.view.*

class IngredientDetailAdapter
    (
        private val context: Context,
        private val ingredientList: MutableList<Ingredient>,
        private val ingredientItemOnChangeListener: IngredientItemOnChangeListener
    ) : Adapter<IngredientDetailAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_ingredient_details_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredientList[position]

        holder.itemView.ingredientItemMinusImageView.setOnClickListener(View.OnClickListener {
            val quantity = Integer.parseInt(holder.itemView.tag.toString()) - 1
            if(quantity >= 0) {
                holder.itemView.tag = quantity
            }
            updateQuantity(holder.itemView, ingredient.id)
        })

        holder.itemView.ingredientItemPlusImageView.setOnClickListener(View.OnClickListener {
            val quantity = Integer.parseInt(holder.itemView.tag.toString()) + 1
            if(quantity <= 5) {
                holder.itemView.tag = quantity
            }
            updateQuantity(holder.itemView, ingredient.id)
        })

        updateQuantity(holder.itemView, ingredient.id)
        holder.bindView(context.applicationContext, ingredient)
    }

    fun refresh(ingredientList: MutableList<Ingredient>) {
        this.ingredientList.clear()
        this.ingredientList.addAll(ingredientList)
        notifyDataSetChanged()
    }

    private fun updateQuantity(itemView: View, itemId: Int){
        if(itemView.tag == null){
            itemView.tag = 0
        }
        itemView.ingredientItemQuantityTextView.text = itemView.tag.toString()
        ingredientItemOnChangeListener.onItemChange(itemId, itemView.tag as Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(context: Context, ingredient: Ingredient) {
            itemView.ingredientItemNameTextView.text = ingredient.name
            itemView.ingredientItemPriceTextView.text = PriceUtils.getFormattedPrice(ingredient.price)
        }
    }

}