package com.gmail.samehadar.iosdialog_sample;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.gmail.samehadar.iosdialog.utils.DialogUtils;

/**
 * CamomileSpinner examples
 */

public class WithCamomileSpinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.with_spinner_activity);

        RelativeLayout activity_main = (RelativeLayout) findViewById(R.id.rootView);

        CamomileSpinner spinner1 = (CamomileSpinner) findViewById(R.id.spinner1);
        spinner1.start();
        CamomileSpinner spinner2 = (CamomileSpinner) findViewById(R.id.spinner2);
        spinner2.start();
        CamomileSpinner spinner3 = (CamomileSpinner) findViewById(R.id.spinner3);
        spinner3.start();

        CamomileSpinner spinner4 = new CamomileSpinner(
                WithCamomileSpinnerActivity.this,
                DialogUtils.getColor(this, R.color.colorGreen),
                CamomileSpinner.DEFAULT_DURATION,
                false
        );
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            params.setMargins(
                    (int) getResources().getDimension(R.dimen.camomile_spinner_margin),
                    (int) getResources().getDimension(R.dimen.camomile_spinner_margin),
                    (int) getResources().getDimension(R.dimen.camomile_spinner_margin),
                    (int) getResources().getDimension(R.dimen.camomile_spinner_margin));
        }
        spinner4.setLayoutParams(params);
        spinner4.start();
        activity_main.addView(spinner4);

    }

}
