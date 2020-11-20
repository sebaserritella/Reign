package com.reign.test.ui.articles

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reign.test.R
import com.reign.test.data.models.Hit
import com.reign.test.databinding.FragmentArticlesBinding
import kotlinx.android.synthetic.main.fragment_articles.*
import org.koin.android.viewmodel.ext.android.viewModel


class ArticlesFragment : Fragment(), HitClickListener {

    private var hitsAdapter: ArticlesAdapter? = null

    private val articlesViewModel by viewModel<ArticlesViewModel>()
    private lateinit var binding: FragmentArticlesBinding

    private val url = "https://stackoverflow.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    // inflate your view here
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_articles, container, false)
        val root = binding.root
        binding.lifecycleOwner = this
        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setView()
        binding.viewModel = articlesViewModel

        articlesViewModel.articlesLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && it.hits.isNotEmpty()) {
                hitsAdapter?.setHits(it.hits)
            }
        })

        articlesViewModel.getAllArticles()
    }

    private fun setView() {
        val linearLayoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.VERTICAL
        }
        hitsAdapter = ArticlesAdapter(context, this)

        with(articlesRecyclerView) {
            layoutManager = linearLayoutManager
            adapter = hitsAdapter
            val divider = DividerItemDecoration(
                context,
                linearLayoutManager.orientation
            )

            addItemDecoration(divider)
        }

        this.context?.let {
            ContextCompat.getColor(
                it, R.color.colorAccent
            )
        }?.let { itemsSwipeToRefresh.setProgressBackgroundColorSchemeColor(it) }
        itemsSwipeToRefresh.setColorSchemeColors(Color.WHITE)

        itemsSwipeToRefresh.setOnRefreshListener {
            articlesViewModel.getAllArticles()
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