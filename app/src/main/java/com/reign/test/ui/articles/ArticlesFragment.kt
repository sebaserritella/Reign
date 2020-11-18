package com.reign.test.ui.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.reign.test.R
import com.reign.test.data.models.Hit
import com.reign.test.databinding.FragmentArticlesBinding
import kotlinx.android.synthetic.main.fragment_articles.*
import org.koin.android.viewmodel.ext.android.viewModel

class ArticlesFragment : Fragment(), HitClickListener {

    private var adapter: ArticlesAdapter? = null

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
                adapter?.setHits(it.hits)
            }
        })

    }

    private fun setView() {
        adapter = ArticlesAdapter(context, this)
        articlesRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        articlesRecyclerView.adapter = adapter
        articlesRecyclerView.isNestedScrollingEnabled = false
    }

    companion object {
        fun newInstance() = ArticlesFragment()
    }

    override fun onItemClick(hit: Hit) {
        Toast.makeText(this.context, "click ", Toast.LENGTH_LONG)
        /*
        (activity as MainActivity).replaceFragment(CountriesDetailsFragment.newInstance(country),
            R.id.fragment_container, "countriesdetails")

         */
    }
}