package com.reign.test.ui.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reign.test.R
import com.reign.test.data.models.Hit
import com.reign.test.databinding.FragmentArticlesBinding
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlinx.android.synthetic.main.item_article.*
import org.koin.android.viewmodel.ext.android.viewModel


class ArticlesFragment : Fragment(), HitClickListener {

    private var hitsAdapter: ArticlesAdapter? = null

    private val articlesViewModel by viewModel<ArticlesViewModel>()
    private lateinit var mViewDataBinding: FragmentArticlesBinding

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

        mViewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_articles, container, false)
        val root = mViewDataBinding.root
        mViewDataBinding.lifecycleOwner = this
        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setView()
        mViewDataBinding.viewModel = articlesViewModel
        articlesViewModel.getAllArticles()
        articlesViewModel.articlesLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && it.hits.isNotEmpty()) {
                hitsAdapter?.setHits(it.hits)
            }
        })
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
    }

    companion object {
        fun newInstance() = ArticlesFragment()
    }

    override fun onItemClick(hit: Hit) {
        Toast.makeText(this.context, "click ", Toast.LENGTH_LONG)
    }
}