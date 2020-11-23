package com.reign.test.ui.articles

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reign.test.R
import com.reign.test.data.models.Hit
import com.reign.test.databinding.FragmentHitsBinding
import kotlinx.android.synthetic.main.fragment_hits.*
import org.koin.android.viewmodel.ext.android.viewModel


class HitsFragment : Fragment(), HitClickListener {

    private var hitsAdapter: HitsAdapter? = null

    private val articlesViewModel by viewModel<HitsViewModel>()
    private lateinit var binding: FragmentHitsBinding

    // inflate your view here
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_hits, container, false)
        val root = binding.root
        binding.lifecycleOwner = this
        binding.viewModel = articlesViewModel
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articlesViewModel.articlesLiveData.observe(viewLifecycleOwner, {
            it?.let { it1 -> hitsAdapter?.setHits(it1) }
        })

        setView()
        articlesViewModel.getArticles()
    }

    private fun setView() {
        hitsAdapter = HitsAdapter(context, this, articlesViewModel)

        with(articlesRecyclerView) {
            val linearLayoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.VERTICAL
            }

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
            articlesViewModel.getArticles()
            itemsSwipeToRefresh.isRefreshing = false
        }
    }

    companion object {
        fun newInstance() = HitsFragment()
    }

    override fun onItemClick(hit: Hit) {
        hit.story_url?.let {
            val action = HitsFragmentDirections.actionArticlesFragmentToHitFragment(
                it
            )
            findNavController().navigate(action)
        }
    }
}