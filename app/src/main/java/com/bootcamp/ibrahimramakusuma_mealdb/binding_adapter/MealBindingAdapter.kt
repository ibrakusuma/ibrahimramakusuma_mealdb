package com.bootcamp.ibrahimramakusuma_mealdb.binding_adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bootcamp.ibrahimramakusuma_mealdb.R
import com.bumptech.glide.Glide

object MealBindingAdapter {
    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, imageUrl: String?) {
        val placeHolderDrawable = R.drawable.img_placeholder
        Glide.with(imageView)
            .load(imageUrl)
            .placeholder(placeHolderDrawable)
            .error(placeHolderDrawable)
            .into(imageView)
    }
}