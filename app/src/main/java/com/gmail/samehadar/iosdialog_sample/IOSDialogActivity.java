package com.gmail.samehadar.iosdialog_sample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

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
                .setMessageContent("My message")
                .setCancelable(true)
                .setMessageContentGravity(Gravity.END)
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
                .setMessageContentGravity(Gravity.END)
                .build();
        dialog2.show();
    }
}
