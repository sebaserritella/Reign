package com.reign.test.ui.articles

import android.os.Bundle
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
            adapter = CustomRecyclerViewAdapter(this@ArticlesFragment)
            vm = getViewModel()
        }
        super.onCreate(savedInstanceState)

        binding.itemsSwipeToRefresh.setOnRefreshListener {
            binding.vm?.forceUpdate()
        }

        binding.vm?.fetching?.observe(this, {
            binding.itemsSwipeToRefresh.isRefreshing = it
        })
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