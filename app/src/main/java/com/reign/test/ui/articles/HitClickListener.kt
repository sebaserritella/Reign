package com.reign.test.ui.articles

import com.reign.test.data.models.Hit

interface HitClickListener {
    fun onItemClick(hit: Hit)
}