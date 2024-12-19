package com.example.homeproject.ui.utilities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream


fun encodeBitmapToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun decodeBase64ToBitmap(encodedImage: String): Bitmap {
    val decodedByte = Base64.decode(encodedImage, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
}

fun getBitmapFromResource(context: Context, resourceId: Int): Bitmap {
    return BitmapFactory.decodeResource(context.resources, resourceId)
}
