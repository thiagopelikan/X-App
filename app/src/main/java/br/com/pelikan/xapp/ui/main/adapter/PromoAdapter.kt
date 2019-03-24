package br.com.pelikan.xapp.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.pelikan.xapp.R
import br.com.pelikan.xapp.models.Promotion
import kotlinx.android.synthetic.main.layout_promo_item.view.*

class PromoAdapter
    (
        private val context: Context,
        private val promoList: MutableList<Promotion>
    ) : Adapter<PromoAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_promo_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return promoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val promo = promoList[position]
        holder.bindView(context.applicationContext, promo)
    }

    fun refresh(promoList: MutableList<Promotion>) {
        this.promoList.clear()
        this.promoList.addAll(promoList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(context: Context, promotion: Promotion) {
            itemView.promoTitleTextView.text = promotion.name
            itemView.promoDescriptionTextView.text = promotion.description
        }
    }

}