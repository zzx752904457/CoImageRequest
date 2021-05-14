package com.example.myapplication

import android.graphics.Bitmap
import android.util.LruCache

class BitmapCache(private val maxSize: Int) {
    private val cache by lazy {
        LruCache<String, Bitmap>(maxSize)
    }

    @Synchronized
    fun get(key: String): Bitmap? {
        return cache.get(key)
    }

    @Synchronized
    fun set(key: String, bitmap: Bitmap): Boolean {
        return cache.put(key, bitmap) != null
    }

    @Synchronized
    fun remove(key: String): Boolean {
        return cache.remove(key) != null
    }

    @Synchronized
    fun clear() {
        cache.trimToSize(-1)
    }
}
