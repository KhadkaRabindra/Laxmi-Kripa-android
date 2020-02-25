package com.maxx.eparchi.utils.kotlin.utils.extensions

import android.graphics.Bitmap
import android.widget.ImageView
import com.squareup.picasso.Picasso
import android.graphics.BitmapFactory
import android.util.Base64


/**
 * Created by maxx on 4/26/18.
 */
fun ImageView.loadImage(url: String?) {
    Picasso.with(context).load(url).into(this)
}

fun getBitmapFromBase64EncodedString(encodedImage: String?) : Bitmap?{
    val decodedString = Base64.decode(encodedImage, Base64.DEFAULT)
    val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    return decodedByte
}