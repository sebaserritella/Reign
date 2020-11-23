package com.reign.test.ui.articles

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.reign.test.R
import com.reign.test.data.models.Hit
import com.reign.test.databinding.ItemArticleBinding


class HitsAdapter(
    val context: Context?,
    val clickListener: HitClickListener,
    val hitsViewModel: HitsViewModel
) : RecyclerView.Adapter<HitsAdapter.ArticleViewHolder>() {

    var hitList: MutableList<Hit> = ArrayList()
    private val viewBinderHelper = ViewBinderHelper()

    fun closeMenu(position: Int) {
        hitList.removeAt(position)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val viewBinding: ItemArticleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_article, parent, false
        )
        return ArticleViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return hitList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.onBind(position, hitsViewModel)
    }

    fun setHits(hits: MutableList<Hit>) {
        val sortedHits =
            hits.sortedByDescending { x -> x.created_at }.toMutableList()

        this.hitList = sortedHits
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(private val viewBinding: ItemArticleBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {



        fun onBind(position: Int, hitsViewModel: HitsViewModel) {
            val row = hitList[position]
            viewBinding.hit = row
            viewBinding.hitClickInterface = clickListener
            viewBinderHelper.setOpenOnlyOne(true);
            viewBinderHelper.bind(viewBinding.swipelayout, position.toString());
            viewBinderHelper.closeLayout(position.toString());

            viewBinding.deleteTextView.setOnClickListener {
                hitsViewModel.deleteItem(position.toString(), row, hitList)
                notifyItemChanged(position)
            }
        }
    }

}


