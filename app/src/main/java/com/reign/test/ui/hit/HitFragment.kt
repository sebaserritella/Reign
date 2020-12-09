package com.reign.test.ui.hit

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.reign.test.R
import com.reign.test.base.DatabindingFragment
import com.reign.test.databinding.FragmentHitBinding
import org.koin.android.viewmodel.ext.android.getViewModel


class HitFragment : DatabindingFragment() {

    private val binding: FragmentHitBinding by binding(R.layout.fragment_hit)

    private var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = this@HitFragment
            vm = getViewModel()
        }
        super.onCreate(savedInstanceState)

        arguments?.let { this.url = HitFragmentArgs.fromBundle(it).url }
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        binding.webview.loadUrl(url)

        binding.backButton.setOnClickListener {
            //val action = HitFragmentDirections.actionHitFragmentToArticlesFragment()
            //findNavController().navigate(action)
            this.activity?.onBackPressed()
        }

    }
}