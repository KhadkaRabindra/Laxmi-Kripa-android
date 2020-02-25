package com.maxx.eparchi.utils.view;

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.maxx.eparchi.utils.kotlin.utils.extensions.applyCustomFont

public class FontTextView : TextView {
    constructor(context: Context?) : super(context) {
        init(null);
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs);
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs);
    }

    private fun init(attrs: AttributeSet?) {
        applyCustomFont(this, context, attrs)
    }
}