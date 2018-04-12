package com.gmail.samehadar.iosdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.annotation.UiThread;
import android.text.Html;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gmail.samehadar.iosdialog.utils.DialogUtils;

import java.util.ArrayList;

/**
 * Created by Vitalu on 4/17/2017. "Head Up Display" HUD
 */

public class IOSDialog extends Dialog {

    protected IOSDialog.Builder builder;
    //TODO:: change to custom view, when realize custom view
    protected ViewGroup rootView;
    protected RelativeLayout movableContainer;
    protected LinearLayout titleFrame;
    protected ImageView titleIcon;
    protected TextView title;
    protected CamomileSpinner spinner;
    protected TextView message;

    private IOSDialog(IOSDialog.Builder builder) {
        super(builder.context, builder.theme);
        final LayoutInflater inflater = LayoutInflater.from(builder.context);
        rootView = (ViewGroup) inflater.inflate(DialogInit.getInflateLayout(builder), null);
        this.builder = builder;
        DialogInit.init(this);
    }

    private IOSDialog(Context context, int theme) {
        super(context, theme);
    }

    //TODO:: add indeterminate support:
    //The second property tells the dialog whether to display a spinner. If false, the ProgressDialog will contain a real progress bar, and you will need to periodically update the UI with the current completion percent of your background task.

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        AnimationDrawable spinnerAnimation = (AnimationDrawable) spinner.getBackground();
        spinnerAnimation.start();
    }

//        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
//        //затемнение
//        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//        lp.dimAmount=0.2f;
//        dialog.getWindow().setAttributes(lp);
////        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);



    public static class Builder {

        protected RelativeLayout.LayoutParams customLayoutParams;
        protected ArrayList<Pair<Integer, ? super Integer>> layoutParamsRules;

        protected Context context;
        protected View customView;
        protected CharSequence title;
        protected CharSequence message;
        protected boolean cancelable;
        protected boolean indeterminate;
        protected float dimAmount;
        protected int spinnerDuration;

        protected int titleGravity;
        protected int messageGravity;

        protected OnShowListener showListener;
        protected OnCancelListener cancelListener;
        protected OnDismissListener dismissListener;
        protected OnKeyListener keyListener;

        protected Typeface regularFont;
        protected Typeface mediumFont;

        protected int theme;

        protected int titleColor;
        protected int messageColor;
        protected int spinnerColor;
        protected int backgroundColor;

        protected boolean spinnerClockwise = true;

        protected boolean oneShot = false;

        protected boolean isTitleColorSet = false;
        protected boolean isMessageColorSet = false;
        protected boolean isSpinnerColorSet = false;
        protected boolean isBackgroundColorSet = false;

        public Builder(Context context) {
            this.layoutParamsRules = new ArrayList<>();
            this.context = context;
            this.theme = R.style.CamomileDialog;
            this.cancelable = true;
            this.titleGravity = Gravity.CENTER;
            this.messageGravity = Gravity.CENTER;
            this.dimAmount = 0.2f;

            if (this.mediumFont == null) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        this.mediumFont = Typeface.create("sans-serif-medium", Typeface.NORMAL);
                    } else {
                        this.mediumFont = Typeface.create("sans-serif", Typeface.BOLD);
                    }
                } catch (Exception ignored) {
                    this.mediumFont = Typeface.DEFAULT_BOLD;
                }
            }
            if (this.regularFont == null) {
                try {
                    this.regularFont = Typeface.create("sans-serif", Typeface.NORMAL);
                } catch (Exception ignored) {
                    this.regularFont = Typeface.SANS_SERIF;
                    if (this.regularFont == null) {
                        this.regularFont = Typeface.DEFAULT;
                    }
                }
            }
        }

        public Builder setTitle(@StringRes int titleRes) {
            setTitle(this.context.getText(titleRes));
            return this;
        }

        public Builder setTitle(@NonNull CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setTitleGravity(int gravity) {
            this.titleGravity = gravity;
            return this;
        }

        public Builder setMessageContent(@StringRes int messageContent) {
            return setMessageContent(messageContent, false);
        }

        public Builder setMessageContent(@StringRes int messageContentRes, boolean html) {
            CharSequence text = this.context.getText(messageContentRes);
            if (html) {
                text = Html.fromHtml(text.toString().replace("\n", "<br/>"));
            }
            return setMessageContent(text);
        }

        public Builder setMessageContent(@StringRes int messageContentRes, Object... formatArgs) {
            String str = String.format(this.context.getString(messageContentRes), formatArgs)
                    .replace("\n", "<br/>");
            //noinspection deprecation
            return setMessageContent(Html.fromHtml(str));
        }

        public Builder setMessageContent(@NonNull CharSequence messageContent) {
            if (this.customView != null) {
                throw new IllegalStateException("You cannot setMessageContent() " +
                        "when you're using a custom view.");
            }
            this.message = messageContent;
            return this;
        }

        public Builder setMessageContentGravity(int gravity) {
            this.messageGravity = gravity;
            return this;
        }

        public Builder setMessageColor(@ColorInt int color) {
            this.messageColor = color;
            this.isMessageColorSet = true;
            return this;
        }

        public Builder setMessageColorRes(@ColorRes int colorRes) {
            return setMessageColor(DialogUtils.getColor(this.context, colorRes));
        }

        public Builder setMessageColorAttr(@AttrRes int colorAttr) {
            return setMessageColor(DialogUtils.resolveColor(this.context, colorAttr));
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setOnCancelListener(@NonNull OnCancelListener cancelListener) {
            this.cancelListener = cancelListener;
            return this;
        }

        public Builder setOnShowListener(@NonNull OnShowListener showListener) {
            this.showListener = showListener;
            return this;
        }

        public Builder setOnDismissListener(@NonNull OnDismissListener dismissListener) {
            this.dismissListener = dismissListener;
            return this;
        }

        public Builder setOnKeyListener(@NonNull OnKeyListener keyListener) {
            this.keyListener = keyListener;
            return this;
        }

        /**
         * @param indeterminate indeterminate
         * @return {@link Builder} IOSDialog.Builder
         * @deprecated Do not use this method, it's not working now!
         */
        @Deprecated
        public Builder setIndeterminate(boolean indeterminate) {
            this.indeterminate = indeterminate;
            return this;
        }

        /**
         * @param dimAmount dimAmount
         * @return {@link Builder} IOSDialog.Builder
         * @deprecated Do not use this method, it's not working now!
         */
        @Deprecated
        public Builder setDimAmount(float dimAmount) {
            this.dimAmount = dimAmount;
            return this;
        }

        public Builder setSpinnerColor(@ColorInt int color) {
            this.spinnerColor = color;
            this.isSpinnerColorSet = true;
            return this;
        }

        public Builder setSpinnerColorRes(@ColorRes int colorRes) {
            return setSpinnerColor(DialogUtils.getColor(this.context, colorRes));
        }

        public Builder setSpinnerColorAttr(@AttrRes int colorAttr) {
            return setSpinnerColor(DialogUtils.resolveColor(this.context, colorAttr));
        }

        public Builder setSpinnerDuration(int spinnerDuration) {
            this.spinnerDuration = spinnerDuration;
            return this;
        }

        public Builder setSpinnerClockwise(boolean spinnerClockwise) {
            this.spinnerClockwise = spinnerClockwise;
            return this;
        }

        public Builder setOneShot(boolean oneShot) {
            this.oneShot = oneShot;
            return this;
        }

        public Builder setTitleColor(@ColorInt int color) {
            this.titleColor = color;
            this.isTitleColorSet = true;
            return this;
        }

        public Builder setTitleColorRes(@ColorRes int colorRes) {
            return setTitleColor(DialogUtils.getColor(this.context, colorRes));
        }

        public Builder setTitleColorAttr(@AttrRes int colorAttr) {
            return setTitleColor(DialogUtils.resolveColor(this.context, colorAttr));
        }

        /**
         * Be careful! Your added rules would be applied to this {@link android.widget.RelativeLayout.LayoutParams}
         * @return {@link Builder} IOSDialog.Builder
         */
        public Builder setLayoutParams(RelativeLayout.LayoutParams params) {
            this.customLayoutParams = params;
            return this;
        }

        /**
         * This function just applies your layout verb, such as {@link android.widget.RelativeLayout.LayoutParams#CENTER_VERTICAL} to {@link CamomileSpinner} view
         * @return {@link Builder} IOSDialog.Builder
         */
        public Builder addLayoutParamsRule(int verb) {
            this.layoutParamsRules.add(Pair.create(verb, null));
            return this;
        }

        /**
         * This function just applies your layout verb, such as {@link android.widget.RelativeLayout.LayoutParams#CENTER_VERTICAL} to {@link CamomileSpinner} view
         * @return {@link Builder} IOSDialog.Builder
         */
        public Builder addLayoutParamsRule(int verb, int subject) {
            this.layoutParamsRules.add(Pair.create(verb, subject));
            return this;
        }

        /**
         * @param color IOSDialog background ColorInt
         * @return {@link Builder} IOSDialog.Builder
         * @deprecated Do not use this method, it's not working now!
         */
        @Deprecated
        public Builder setBackgroundColor(@ColorInt int color) {
            this.backgroundColor = color;
            this.isBackgroundColorSet = true;
            return this;
        }

        /**
         * @param colorRes IOSDialog background colorRes
         * @return {@link Builder} IOSDialog.Builder
         * @deprecated Do not use this method, it's not working now!
         */
        @Deprecated
        public Builder setBackgroundColorRes(@ColorRes int colorRes) {
            return setBackgroundColor(DialogUtils.getColor(this.context, colorRes));
        }

        /**
         * @param colorAttr IOSDialog background colorAttr
         * @return {@link Builder} IOSDialog.Builder
         * @deprecated Do not use this method, it's not working now!
         */
        @Deprecated
        public Builder setBackgroundColorAttr(@AttrRes int colorAttr) {
            return setBackgroundColor(DialogUtils.resolveColor(this.context, colorAttr));
        }

        public Builder setTheme(@StyleRes int theme) {
            this.theme = theme;
            return this;
        }

        //TODO:: add setCustomView
        //TODO:: add set title icon

        @UiThread
        public IOSDialog build() {
            return new IOSDialog(this);
        }

        @UiThread
        public IOSDialog show() {
            IOSDialog dialog = build();
            dialog.show();
            return dialog;
        }

    }

    //methods for future features
    private Drawable adjust(Drawable d) {
        int to = Color.RED;

        //Need to copy to ensure that the bitmap is mutable.
        Bitmap src = ((BitmapDrawable) d).getBitmap();
        Bitmap bitmap = src.copy(Bitmap.Config.ARGB_8888, true);
        for(int x = 0;x < bitmap.getWidth();x++)
            for(int y = 0;y < bitmap.getHeight();y++)
                if(match(bitmap.getPixel(x, y)))
                    bitmap.setPixel(x, y, to);

        return new BitmapDrawable(bitmap);
    }

    private Bitmap adjust(Bitmap src) {
        int to = Color.RED;

        Bitmap bitmap = src.copy(Bitmap.Config.ARGB_8888, true);
        for(int x = 0;x < bitmap.getWidth();x++)
            for(int y = 0;y < bitmap.getHeight();y++)
                if(match(bitmap.getPixel(x, y)))
                    bitmap.setPixel(x, y, to);

        return bitmap;
    }

    private static final int[] FROM_COLOR = new int[]{49, 179, 110};
    private static final int THRESHOLD = 3;
    private boolean match(int pixel) {
        //There may be a better way to match, but I wanted to do a comparison ignoring
        //transparency, so I couldn't just do a direct integer compare.
        return Math.abs(Color.red(pixel) - FROM_COLOR[0]) < THRESHOLD &&
                Math.abs(Color.green(pixel) - FROM_COLOR[1]) < THRESHOLD &&
                Math.abs(Color.blue(pixel) - FROM_COLOR[2]) < THRESHOLD;
    }

}
