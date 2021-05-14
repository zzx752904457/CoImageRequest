package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import coil.network.HttpException
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException
import java.lang.NullPointerException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ImageActualRequest(private val bitmapCache: BitmapCache, private val callFactory: Call.Factory) {

    suspend fun request(url: String?): Bitmap? {
        if (url.isNullOrEmpty()) {
            return null
        }
        return if (bitmapCache.get(url) != null) {
            bitmapCache.get(url)
        } else {
            val request = Request.Builder().url(url).headers(Headers.Builder().build())
            val result = suspendCancellableCoroutine<Response> { continuation ->
                callFactory.newCall(request.build()).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        continuation.resumeWithException(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        continuation.resume(response)
                    }
                })
            }
            if (!result.isSuccessful) {
                result.body()?.close()
                throw HttpException(result)
            } else {
                val body = result.body() ?: throw NullPointerException("Null body exception")
                val bitmap = BitmapFactory.decodeStream(body.source().inputStream())
                bitmapCache.set(url, bitmap)
                bitmap
            }
        }
    }
}
