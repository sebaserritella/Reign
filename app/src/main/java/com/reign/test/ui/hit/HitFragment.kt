package com.reign.test.ui.hit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.reign.test.R
import com.reign.test.databinding.FragmentHitBinding
import org.koin.android.viewmodel.ext.android.viewModel


class HitFragment : Fragment() {

    private val hitViewModel by viewModel<HitViewModel>()
    private lateinit var binding: FragmentHitBinding

    private var url: String = ""

    // inflate your view here
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_hit, container, false)
        val root = binding.root
        binding.lifecycleOwner = this
        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = hitViewModel

        arguments?.let { this.url = HitFragmentArgs.fromBundle(it).url }

        setView()
    }

    private fun setView() {
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        binding.webview.loadUrl(url)

        binding.backButton.setOnClickListener {
            this.requireActivity().onBackPressed()
        }
    }

    companion object {
        fun newInstance() = HitFragment()
    }
}