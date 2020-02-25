package com.maxx.eparchi.utils.kotlin.utils.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

fun writeToFile(data: String) {
    val newData = data.replace("\\s+","");
    // Get the directory for the user's public pictures directory.
    val path = Environment.getExternalStoragePublicDirectory(
            //Environment.DIRECTORY_PICTURES
            Environment.DIRECTORY_DCIM + "/YourFolder/"
    );

    // Make sure the path directory exists.
    if (!path.exists()) {
        // Make it, if it doesn't exit
        path.mkdirs();
    }

    val file = File(path, "config.txt");

    // Save your stream, don't forget to flush() it before closing it.

    try {
        file.createNewFile();
        val fOut = FileOutputStream(file);
        val myOutWriter = OutputStreamWriter(fOut);
        myOutWriter.append(newData);

        myOutWriter.close();

        fOut.flush();
        fOut.close();
    } catch (e: Exception) {
        Log.e("Exception", "File write failed: " + e.toString());
    }
}

fun createBitmapFromFile(filePath: String?): Bitmap {
    val options = BitmapFactory.Options()
    options.inPreferredConfig = Bitmap.Config.ARGB_8888
    return BitmapFactory.decodeFile(filePath, options)
}
