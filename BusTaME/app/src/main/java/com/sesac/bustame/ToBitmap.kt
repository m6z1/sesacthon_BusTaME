package com.sesac.bustame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

fun vectorToBitmap(context: Context, vectorDrawableId: Int): Bitmap? {
    val vectorDrawable: Drawable? = ContextCompat.getDrawable(context, vectorDrawableId)
    vectorDrawable ?: return null

    val bitmap: Bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable.draw(canvas)

    return bitmap
}
