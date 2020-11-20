package com.reign.test.ui.articles

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.reign.test.R
import com.reign.test.data.models.Hit
import com.reign.test.databinding.ItemArticleBinding

class ArticlesAdapter(
    val context: Context?,
    val clickListener: HitClickListener ,
    val articlesViewModel: ArticlesViewModel
) : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    var hitList: List<Hit> = ArrayList()

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
        holder.onBind(position, articlesViewModel)
    }

    fun setHits(hits: List<Hit>) {
        val sortedHits =
            hits.toMutableList().filter { x -> !x.deleted }.sortedByDescending { x -> x.created_at }

        this.hitList = sortedHits
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(private val viewBinding: ItemArticleBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun onBind(position: Int, articlesViewModel: ArticlesViewModel) {
            val row = hitList[position]
            viewBinding.hit = row
            viewBinding.hitClickInterface = clickListener

            viewBinding.deleteTextView.setOnClickListener {
                articlesViewModel.deleteItem(row);
                row.markDeleted()
                notifyItemChanged(position)
            }
        }
    }

}


