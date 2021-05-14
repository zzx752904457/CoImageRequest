package com.example.myapplication

import android.content.Context

object ImageLoaderHelper {
    private var imageBaseLoader: ActualImageLoader? = null
    fun createImageLoader(context: Context) = imageBaseLoader ?: newImageLoader(context)

    private fun newImageLoader(context: Context): ActualImageLoader {
        imageBaseLoader?.let {
            return it
        }
        val newLoader = ActualImageLoader.Builder(context).build()
        imageBaseLoader = newLoader
        return newLoader
    }
}
