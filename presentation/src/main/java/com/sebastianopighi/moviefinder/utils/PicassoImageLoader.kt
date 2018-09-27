package com.sebastianopighi.moviefinder.utils

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PicassoImageLoader(private val picasso: Picasso) {

    fun load(url: String, imageView: ImageView, callback: (Boolean) -> Unit) {
        picasso.load(url).into(imageView, FetchCallback(callback))
    }

    fun load(url: String, imageView: ImageView, fadeEffect: Boolean) {
        if (fadeEffect)
            picasso.load(url).into(imageView)
        else
            picasso.load(url).noFade().into(imageView)
    }

    private class FetchCallback(val delegate: (Boolean) -> Unit): Callback {
        override fun onSuccess() {
            delegate(true)
        }

        override fun onError() {
            delegate(false)
        }

    }
}