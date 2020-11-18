package com.reign.test.ui

import com.reign.test.ui.articles.ArticlesFragment.Companion.newInstance

class MainActivity : SingleFragmentActivity() {
    override fun createFragment() = newInstance()
}