package com.reign.test.ui.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.reign.test.R
import com.reign.test.data.models.Hit
import com.reign.test.databinding.ItemArticleBinding

class CustomRecyclerViewAdapter(private val clickListener: HitClickListener) :
    RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder>() {

    private val items = mutableListOf<Hit>()

    fun setData(data: MutableList<Hit>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val viewBinding: ItemArticleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_article, parent, false
        )

        return CustomViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.viewBinding.apply {
            hit = items[position]
            hitClickInterface = clickListener
            executePendingBindings()
        }
    }

    inner class CustomViewHolder(val viewBinding: ItemArticleBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}