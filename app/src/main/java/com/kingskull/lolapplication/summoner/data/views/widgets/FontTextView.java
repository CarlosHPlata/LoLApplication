package com.kingskull.lolapplication.summoner.data.views.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.summoner.data.views.widgets.fontTextView.TypeFaceCache;

/**
 * Created by Usuario on 06/09/2015.
 */
public class FontTextView extends TextView {

    public FontTextView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    public FontTextView (Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public FontTextView (Context context) {
        super(context);
    }


    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
        String customFont = a.getString(R.styleable.TextViewPlus_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf = TypeFaceCache.getTypeFace(asset, ctx);
        setTypeface(tf);
        return true;
    }

}
