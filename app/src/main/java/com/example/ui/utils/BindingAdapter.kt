package com.example.ui.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setImageSrc")
fun ImageView.setImageRes(imgUrl:String?){
    imgUrl?.let {
        Glide.with(this).load(it).into(this)
    }
}