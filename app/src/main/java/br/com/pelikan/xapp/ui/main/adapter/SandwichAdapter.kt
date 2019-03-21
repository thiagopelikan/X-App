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
import kotlinx.android.synthetic.main.layout_sandwich_item.view.*

class SandwichAdapter
    (
        private val context: Context,
        private val content: MutableList<Sandwich>
    ) : Adapter<SandwichAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_sandwich_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return content.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content = content[position]
        holder.bindView(context.applicationContext, content)
    }

    fun refresh(contentList: MutableList<Sandwich>) {
        this.content.clear()
        this.content.addAll(contentList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(context: Context, sandwich: Sandwich) {
            itemView.sandwichNameTextView.text = sandwich.name
            itemView.sandwichImageView.post {
                GlideApp
                    .with(context)
                    .load(sandwich.image)
                    .placeholder(R.drawable.loading_placeholder)
                    .error(R.drawable.error_placeholder)
                    .centerCrop()
                    .into(itemView.sandwichImageView)
            }
        }
    }

}