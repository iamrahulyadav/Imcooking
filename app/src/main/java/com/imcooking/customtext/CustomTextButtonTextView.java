package com.imcooking.customtext;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by lenovo on 4/13/2018.
 */

public class CustomTextButtonTextView extends android.support.v7.widget.AppCompatTextView {

    public CustomTextButtonTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextButtonTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextButtonTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat-Regular.ttf");
        setTypeface(tf ,1);

    }

}

