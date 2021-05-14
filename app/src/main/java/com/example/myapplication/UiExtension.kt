package com.example.myapplication

import android.widget.ImageView

@JvmSynthetic
fun ImageView.loadImage(url: String?): ImageDispose {
    val dispose = ImageLoaderHelper.createImageLoader(context).loadImage(url)
    setImageBitmap(dispose.bitmap)
    return dispose
}
