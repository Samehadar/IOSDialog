package com.gmail.samehadar.iosdialog;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.gmail.samehadar.iosdialog.utils.DialogUtils;

/**
 * Created by Vitalu on 4/19/2017.
 */
//TODO:: try to extends from view
public class CamomileSpinner extends android.support.v7.widget.AppCompatImageView {

    public static final int DEFAULT_DURATION = 60;
    @ColorInt public static final int DEFAULT_COLOR;
    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            DEFAULT_COLOR = Resources.getSystem().getColor(android.R.color.white, null);
        } else {
            DEFAULT_COLOR = Resources.getSystem().getColor(android.R.color.white);
        }
    }
    public static final boolean DEFAULT_CLOCKWISE = true;

    private int spinnerColor;
    private int duration;
    private boolean clockwise = DEFAULT_CLOCKWISE;

    public CamomileSpinner(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.setBackground(ContextCompat.getDrawable(context, R.drawable.spinner));
        } else {
            this.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.spinner));
        }
    }

    public CamomileSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCustomAttr(context, attrs);
    }

    public CamomileSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomAttr(context, attrs);
    }

//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    public CamomileSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        initCustomAttr(context, attrs);
//    }

    public CamomileSpinner(Context context, @ColorInt int spinnerColor) {
        this(context, spinnerColor, DEFAULT_DURATION);
    }

    public CamomileSpinner(Context context, @ColorInt int spinnerColor, int duration) {
        this(context, spinnerColor, duration, DEFAULT_CLOCKWISE);
    }

    public CamomileSpinner(Context context, @ColorInt int spinnerColor, int duration, boolean clockwise) {
        super(context);
        this.spinnerColor = spinnerColor;
        this.duration = duration;
        this.clockwise = clockwise;
        buildAnimation(context);
    }

    private void initCustomAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CamomileSpinner,
                0, 0);
        try {
            duration = typedArray.getInteger(R.styleable.CamomileSpinner_duration, DEFAULT_DURATION);
            spinnerColor = typedArray.getColor(R.styleable.CamomileSpinner_spinnerColor, DEFAULT_COLOR);
            clockwise = typedArray.getBoolean(R.styleable.CamomileSpinner_clockwise, DEFAULT_CLOCKWISE);
            buildAnimation(context);
        } finally {
            typedArray.recycle();
        }
    }

    private void buildAnimation(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (spinnerColor == DEFAULT_COLOR && duration == DEFAULT_DURATION && clockwise == DEFAULT_CLOCKWISE) { //create from xml if default params
                this.setBackground(DialogUtils.createAnimation(context));
            } else {                                                                                                                       //create programmatically if params is not default
                this.setBackground(DialogUtils.createAnimation(context, spinnerColor, duration, clockwise));
            }
        } else {
            if (spinnerColor == DEFAULT_COLOR && duration == DEFAULT_DURATION && clockwise == DEFAULT_CLOCKWISE) {
                this.setBackgroundDrawable(DialogUtils.createAnimation(context));
            } else {
                this.setBackgroundDrawable(DialogUtils.createAnimation(context, spinnerColor, duration, clockwise));
            }
        }
    }

    public void start() {
        ((AnimationDrawable) this.getBackground()).start();
    }

    public void stop() {
        ((AnimationDrawable)this.getBackground()).stop();
    }

//    public void recreateWithParams(@ColorInt int color, int duration, boolean clockwise) {
//     TODO:: in future
//    }

}
