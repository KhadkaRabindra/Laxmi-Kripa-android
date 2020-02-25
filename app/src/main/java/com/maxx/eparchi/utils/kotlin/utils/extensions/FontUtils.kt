package com.maxx.eparchi.utils.kotlin.utils.extensions

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import com.maxx.eparchi.R
import java.util.*


private val mAndroidSchema = "http://schemas.android.com/apk/res/android"

/**
 * adds custom font to views
 */
fun applyCustomFont(customFontTextView: TextView, context: Context, attrs: AttributeSet?) {
    val attributeArray = context.obtainStyledAttributes(
        attrs,
        R.styleable.View
    )

    // check if a special textStyle was used (e.g. bold)
    var textStyle = attributeArray.getInt(R.styleable.View_textStyle, 0)

    // if nothing extra was used, fall back to regular android:textStyle parameter
    if (attrs != null && textStyle == Typeface.NORMAL) {
        textStyle = attrs.getAttributeIntValue(mAndroidSchema, "textStyle", Typeface.NORMAL)
    }
    customFontTextView.typeface = selectTypeStyle(context, textStyle)
    attributeArray.recycle()
}

/**
 * selects font according to textStyle
 */
private fun selectTypeStyle(context: Context, textStyle: Int): Typeface? {

    return when (textStyle) {
        Typeface.NORMAL // regular
        -> FontCache.getTypeface(context.getString(R.string.gotham), context)

        Typeface.BOLD // bold
        -> FontCache.getTypeface(context.getString(R.string.gothamBold), context)

        Typeface.ITALIC // italic
        -> FontCache.getTypeface(context.getString(R.string.gothamItalic), context)

        Typeface.BOLD_ITALIC // bold italic
        -> FontCache.getTypeface(context.getString(R.string.gothamBoldItalic), context)

        10 // extra bold, equals @integer/extraBold
        -> FontCache.getTypeface(context.getString(R.string.gothamExtraBold), context)

        else -> FontCache.getTypeface(context.getString(R.string.gotham), context)
    }

}

fun applyBoldFont(customFontTextView: TextView, context: Context, attrs: AttributeSet?) {
    val attributeArray = context.obtainStyledAttributes(
        attrs,
        R.styleable.View
    )

    customFontTextView.typeface = FontCache.getTypeface(context.getString(R.string.gothamBold), context)
    attributeArray.recycle()
}

object FontCache {

    private val fontCache = HashMap<String, Typeface>()

    fun getTypeface(fontName: String, context: Context): Typeface? {
        var typeface: Typeface? = fontCache[fontName]

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.assets, fontName)
            } catch (e: Exception) {
                return null
            }

            fontCache.put(fontName, typeface)
        }

        return typeface
    }
}
