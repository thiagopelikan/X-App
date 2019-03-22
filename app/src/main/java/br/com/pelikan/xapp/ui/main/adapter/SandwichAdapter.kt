package br.com.pelikan.xapp.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.pelikan.xapp.R
import br.com.pelikan.xapp.models.Sandwich
import br.com.pelikan.xapp.utils.GlideApp
import br.com.pelikan.xapp.utils.NumberUtils
import kotlinx.android.synthetic.main.layout_sandwich_item.view.*


class SandwichAdapter
    (
        private val context: Context,
        private val sandwichList: MutableList<Sandwich>
    ) : Adapter<SandwichAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_sandwich_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sandwichList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sandwich = sandwichList[position]
        holder.bindView(context.applicationContext, sandwich)
    }

    fun refresh(sandwichList: MutableList<Sandwich>) {
        this.sandwichList.clear()
        this.sandwichList.addAll(sandwichList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(context: Context, sandwich: Sandwich) {
            itemView.sandwichNameAndPriceTextView.text = sandwich.name

            itemView.sandwichIngredientsTextView.text = android.text.TextUtils.join(", ", sandwich.ingredientList)

            itemView.sandwichNameAndPriceTextView.text = itemView.sandwichNameAndPriceTextView.text.toString() + " (" + NumberUtils.getFormattedPrice(
                sandwich.price!!
            ) + ") "

            itemView.sandwichImageView.post {
                GlideApp
                    .with(context)
                    .load(sandwich.image)
                    .placeholder(R.drawable.loading_placeholder)
                    .error(R.drawable.error_placeholder)
                    .override(itemView.sandwichImageView.width, itemView.sandwichImageView.height)
                    .centerInside()
                    .into(itemView.sandwichImageView)
            }
        }
    }

}