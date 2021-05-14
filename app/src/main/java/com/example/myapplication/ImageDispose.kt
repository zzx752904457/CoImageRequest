package com.example.myapplication

import android.graphics.Bitmap
import kotlinx.coroutines.Job
class ImageDispose {
    var status: Int = ActualImageLoader.STATUS_SUCCESS
    var job: Job? = null
    var bitmap: Bitmap? = null

    fun cancel() {
        job?.cancel()
    }
}

