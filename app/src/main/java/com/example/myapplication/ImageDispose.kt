package com.example.myapplication

import kotlinx.coroutines.Job
class ImageDispose {
    var job: Job? = null

    fun cancel() {
        job?.cancel()
    }
}

