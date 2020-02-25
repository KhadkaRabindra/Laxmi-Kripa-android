package com.maxx.eparchi.utils.kotlin.utils.extensions

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.maxx.eparchi.R
import com.squareup.picasso.Picasso
import java.io.*


/*Scan a media file*/
fun Context.scanMediaFile(photo: File?) {
    val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
    val contentUri = Uri.fromFile(photo)
    mediaScanIntent.data = contentUri
    applicationContext.sendBroadcast(mediaScanIntent)
}

/**/
fun Context.compressImage(imageUri: Uri?): String {
    val filePath = imageUri?.path
    var scaledBitmap: Bitmap? = null

    val options = BitmapFactory.Options()

    //      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
    //      you try the use the bitmap here, you will get null.
    options.inJustDecodeBounds = true
    var bmp = BitmapFactory.decodeFile(filePath, options)

    var actualHeight = options.outHeight
    var actualWidth = options.outWidth

    //      max Height and width values of the compressed image is taken as 816x612

    val maxHeight = 816.0f
    val maxWidth = 612.0f
    var imgRatio = (actualWidth / actualHeight).toFloat()
    val maxRatio = maxWidth / maxHeight

    //      width and height values are set maintaining the aspect ratio of the image

    if (actualHeight > maxHeight || actualWidth > maxWidth) {
        if (imgRatio < maxRatio) {
            imgRatio = maxHeight / actualHeight
            actualWidth = (imgRatio * actualWidth).toInt()
            actualHeight = maxHeight.toInt()
        } else if (imgRatio > maxRatio) {
            imgRatio = maxWidth / actualWidth
            actualHeight = (imgRatio * actualHeight).toInt()
            actualWidth = maxWidth.toInt()
        } else {
            actualHeight = maxHeight.toInt()
            actualWidth = maxWidth.toInt()
        }
    }
    //      setting inSampleSize value allows to load a scaled down version of the original image
    options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
    //      inJustDecodeBounds set to false to load the actual bitmap
    options.inJustDecodeBounds = false
    //      this options allow android to claim the bitmap memory if it runs low on memory
    options.inPurgeable = true
    options.inInputShareable = true
    options.inTempStorage = ByteArray(16 * 1024)
    try {
        //          load the bitmap from its path
        bmp = BitmapFactory.decodeFile(filePath, options)
    } catch (exception: OutOfMemoryError) {
        exception.printStackTrace()

    }

    try {
        scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
    } catch (exception: OutOfMemoryError) {
        exception.printStackTrace()
    }

    val ratioX = actualWidth / options.outWidth.toFloat()
    val ratioY = actualHeight / options.outHeight.toFloat()
    val middleX = actualWidth / 2.0f
    val middleY = actualHeight / 2.0f

    val scaleMatrix = Matrix()
    scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

    val canvas = Canvas(scaledBitmap)
    canvas.matrix = scaleMatrix
    canvas.drawBitmap(bmp, middleX - bmp.width / 2, middleY - bmp.height / 2, Paint(Paint.FILTER_BITMAP_FLAG))

    //      check the rotation of the image and display it properly
    val exif: ExifInterface
    try {
        exif = ExifInterface(filePath)

        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION, 0
        )
        Log.d("EXIF", "Exif: " + orientation)
        val matrix = Matrix()
        if (orientation == 6) {
            matrix.postRotate(90.0f)
            Log.d("EXIF", "Exif: " + orientation)
        } else if (orientation == 3) {
            matrix.postRotate(180.0f)
            Log.d("EXIF", "Exif: " + orientation)
        } else if (orientation == 8) {
            matrix.postRotate(270.0f)
            Log.d("EXIF", "Exif: " + orientation)
        }
        scaledBitmap = Bitmap.createBitmap(
            scaledBitmap, 0, 0,
            scaledBitmap!!.width, scaledBitmap.height, matrix,
            true
        )
    } catch (e: IOException) {
        e.printStackTrace()
    }

    var out: FileOutputStream? = null
    val filename = getFileName()
    try {
        out = FileOutputStream(filename)

        //          write the compressed bitmap at the destination specified by filename.
        scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, out)

    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
    return filename
}

fun Context.getFileName(): String {
    val file: File = File(
        Environment.getExternalStorageDirectory().path, getString(R.string.image_directory_name) + "/" +
                getString(R.string.default_icon)
    )
    if (!file.exists()) {
        file.mkdirs()
    }
    val uriSting = file.absolutePath + "/" + System.currentTimeMillis() + ".jpg"
    return uriSting
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
        val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
        inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
    }
    val totalPixels = (width * height).toFloat()
    val totalReqPixelsCap = reqWidth * reqHeight * 2
    while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
        inSampleSize++
    }
    return inSampleSize
}

fun encodeImage(path: String?): String {

    val imagefile = File(path)
    val fis = FileInputStream(imagefile)
    val bitmap = BitmapFactory.decodeStream(fis)
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val imageBytes = baos.toByteArray()
    return android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT)
}

fun removePhoto(circularImageView: ImageView, addProfileImageView: ImageView, imagePath: String) {
    //customerFaceImagePath = ""//clear profile image uri

    setPhoto(circularImageView, addProfileImageView, imagePath)
}

fun setPhoto(circleImageView: ImageView, imageview: ImageView, imagePath: String) {
    if (!TextUtils.isEmpty(imagePath)) {
        val file = File(imagePath)
        if (file.exists()) {//profile picture available
            imageview.visibility = View.GONE
            circleImageView.visibility = View.VISIBLE

            circleImageView.loadLocalURLWithCenterCrop(imagePath)
        } else {
            imageview.visibility = View.VISIBLE
            circleImageView.visibility = View.GONE
        }
    } else {
        imageview.visibility = View.VISIBLE
        circleImageView.visibility = View.GONE
    }
}

fun ImageView.loadLocalURLWithCenterCrop(imagePath: String) {
    Picasso.with(context)
        .load(Uri.fromFile(File(imagePath)))
        .fit()
        .centerCrop()
        .into(this)
}

fun ImageView.loadLocalURL(imagePath: String) {
    Picasso.with(context)
        .load(Uri.fromFile(File(imagePath)))
        /*.fit()
        .centerCrop()*/
        .into(this)
}

fun Context.getSelectedImagePath(photoUri: Uri): Any? {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = applicationContext.contentResolver.query(photoUri, projection, null, null, null)
    if (cursor == null)
        return null
    val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor.moveToFirst()
    val path = cursor.getString(column_index)
    cursor.close()
    return path
}

fun Context.getRealPathFromURI(uri: Uri): String {
    val cursor = applicationContext.contentResolver.query(uri, null, null, null, null)
    cursor.moveToFirst()
    val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
    return cursor.getString(idx)
}

fun getBitmapFromBase64String(encodedImage: String): Bitmap {
    val decodedString = Base64.decode(encodedImage, Base64.DEFAULT)
    val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    return decodedByte
}