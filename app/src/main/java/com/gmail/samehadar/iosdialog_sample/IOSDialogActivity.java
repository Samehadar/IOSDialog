package com.gmail.samehadar.iosdialog_sample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.RelativeLayout;

import com.gmail.samehadar.iosdialog.IOSDialog;


/**
 * IOSDialog examples
 */
public class IOSDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final IOSDialog dialog0 = new IOSDialog.Builder(IOSDialogActivity.this)
                .setTitle("Default IOS bar")
                .setTitleColorRes(R.color.gray)
                .addLayoutParamsRule(RelativeLayout.ALIGN_BOTTOM)
                .build();

        final IOSDialog dialog1 = new IOSDialog.Builder(IOSDialogActivity.this)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog0.show();
                    }
                })
                .setDimAmount(3)
                .setSpinnerColorRes(R.color.colorGreen)
                .setMessageColorRes(R.color.colorAccent)
                .setTitle(R.string.standard_title)
                .setTitleColorRes(R.color.colorPrimary)
                .setMessageContent("My very-very long message that would be resized if cannot fit in the screen")
                .setCancelable(true)
                .setMessageContentGravity(Gravity.END)
                .addLayoutParamsRule(RelativeLayout.ALIGN_PARENT_LEFT)
                .build();

        IOSDialog dialog2 = new IOSDialog.Builder(IOSDialogActivity.this)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog1.show();
                    }
                })
                .setSpinnerColorRes(R.color.ios_gray_text)
                .setMessageColorRes(R.color.primaryDark)
                .setTitleColorRes(R.color.accent)
                .setMessageContent("My message")
                .setCancelable(true)
                .setSpinnerClockwise(false)
                .setSpinnerDuration(120)
                .addLayoutParamsRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                .setMessageContentGravity(Gravity.END)
                .setOneShot(false)
                .build();
        dialog2.show();
    }
}
