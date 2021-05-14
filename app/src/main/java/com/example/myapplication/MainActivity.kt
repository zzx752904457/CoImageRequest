package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.ImageLoader
import coil.load
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageLoader = ImageLoader.Builder(this)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .allowRgb565(true)
            .build()

        iv_img.setOnClickListener {
//            iv_img.load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1391298429,1374223519&fm=26&gp=0.jpg", imageLoader = imageLoader)
            iv_img.loadImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1391298429,1374223519&fm=26&gp=0.jpg")
        }
    }
}
