package com.sebastianopighi.moviefinder.adapters

import android.support.v7.widget.RecyclerView

class RecyclerViewScrollListener(private val layoutManager: RecyclerView.LayoutManager) : EndlessRecyclerViewScrollListener(layoutManager) {

    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {

    }

}