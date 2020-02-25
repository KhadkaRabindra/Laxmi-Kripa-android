package com.maxx.eparchi.utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import static com.maxx.eparchi.utils.kotlin.utils.extensions.FontUtilsKt.applyCustomFont;


public class FontEditText extends EditText {


    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public FontEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public FontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public FontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;
        this.defStyle = defStyle;
        init();
    }

    private void init() {
        applyCustomFont(this, context, attrs);
        /*Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/myfont.ttf");
        this.setTypeface(font);*/
    }

    /*@Override
    public void setTypeface(Typeface tf, int style) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/myfont.ttf");
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/myfont.ttf");
        super.setTypeface(tf);
    }*/
}