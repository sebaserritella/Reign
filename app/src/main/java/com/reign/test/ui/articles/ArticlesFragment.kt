package com.reign.test.ui.articles

import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.reign.test.R
import com.reign.test.base.DatabindingFragment
import com.reign.test.data.models.Hit
import com.reign.test.databinding.FragmentArticlesBinding
import kotlinx.android.synthetic.main.fragment_articles.*
import org.koin.android.viewmodel.ext.android.getViewModel


class ArticlesFragment : DatabindingFragment(), HitClickListener {

    private val binding: FragmentArticlesBinding by binding(R.layout.fragment_articles)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = this@ArticlesFragment
            adapter = CustomRecyclerViewAdapter()
            vm = getViewModel()
        }
        super.onCreate(savedInstanceState)
    }

    private fun setView() {
        this.context?.let {
            ContextCompat.getColor(
                it, R.color.colorAccent
            )
        }?.let { itemsSwipeToRefresh.setProgressBackgroundColorSchemeColor(it) }
        itemsSwipeToRefresh.setColorSchemeColors(Color.WHITE)

        itemsSwipeToRefresh.setOnRefreshListener {
            itemsSwipeToRefresh.isRefreshing = false
        }
    }

    companion object {
        fun newInstance() = ArticlesFragment()
    }

    override fun onItemClick(hit: Hit) {
        hit.story_url?.let {
            val action = ArticlesFragmentDirections.actionArticlesFragmentToHitFragment(
                it
            )
            findNavController().navigate(action)
        }
    }
}