package com.example.myapplication

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

@JvmSynthetic
fun ImageView.loadImage(url: String?): ImageDispose {
    val liveData = MutableLiveData<ImageTarget>()
    val dispose = ImageLoaderHelper.createImageLoader(context.applicationContext).loadImage(url, liveData)
    val listener = object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View?) {
        }

        override fun onViewDetachedFromWindow(v: View?) {
            dispose.cancel()
        }
    }
    addOnAttachStateChangeListener(listener)
    if (context is LifecycleOwner) {
        liveData.observe(context as LifecycleOwner, {
            removeOnAttachStateChangeListener(listener)
            it?.bitmap?.let { bitmap ->
                setImageBitmap(bitmap)
            }
        })
    }
    return dispose
}
