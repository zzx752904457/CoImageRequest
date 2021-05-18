package com.example.myapplication

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log
import androidx.core.content.getSystemService
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import okhttp3.Call
import okhttp3.OkHttpClient

class ActualImageLoader(private val bitmapCache: BitmapCache, private val callFactory: Call.Factory) {

    companion object {
        const val STATUS_SUCCESS = 0
        const val STATUS_FAILED = 1
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun loadImage(url: String?, liveData: MutableLiveData<ImageTarget>): ImageDispose {
        val imageResult = ImageDispose()
        val imageTarget = ImageTarget()
        imageResult.job = scope.launch(
            CoroutineExceptionHandler { _, throwable ->
                Log.e("ImageLoader", throwable.message ?: "")
                imageTarget.status = STATUS_FAILED
                liveData.postValue(imageTarget)
            }
        ) {
            val bitmap = withContext(Dispatchers.IO) {
                val imageRequest = ImageActualRequest(bitmapCache, callFactory)
                imageRequest.request(url)
            }
            imageTarget.status = STATUS_SUCCESS
            imageTarget.bitmap = bitmap
            liveData.postValue(imageTarget)
        }
        return imageResult
    }

    class Builder(private val context: Context) {
        fun build(): ActualImageLoader {
            return ActualImageLoader(
                BitmapCache(calculateAvailableMemorySize(context)),
                OkHttpClient.Builder().build()
            )
        }

        private fun calculateAvailableMemorySize(context: Context): Int {
            val memoryClassMegabytes = try {
                val activityManager: ActivityManager = context.getSystemService() ?: throw NullPointerException("Null ActivityManager Exception")
                val isLargeHeap = (context.applicationInfo.flags and ApplicationInfo.FLAG_LARGE_HEAP) != 0
                if (isLargeHeap) {
                    activityManager.largeMemoryClass
                } else {
                    activityManager.memoryClass
                }
            } catch (_: Exception) {
                256
            }
            return memoryClassMegabytes * 1024 * 1024
        }
    }
}
