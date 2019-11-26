package com.example.weather;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.time.Period;
import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WeatherDetails extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;

    private TextView mTitleText;
    private TextView mDataTxt;

    class WeatherRecord{
        private String DateTxt;
        private float Temperature;
        private float Humidity;
    }

    WeatherRecord[] WeatherRec=new WeatherDetails.WeatherRecord[1000];
    private String dateArray[]=new String[1000];
    private int i;
    private String Contents;

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int Period;
        setContentView(R.layout.activity_weather_details);

        mContentView = findViewById(R.id.fullscreen_content);
        mTitleText=findViewById(R.id.textTitle);
        mDataTxt=findViewById(R.id.textData);

        Intent intent = getIntent();
        Period=intent.getIntExtra("Period",0);
        Contents=intent.getStringExtra("Contents");

        mDataTxt.setMovementMethod(new ScrollingMovementMethod());

        if (Period==2) {
            mTitleText.setText("Next 24 hours");
            mDataTxt.setText(Contents);
        }
        if (Period==3)
        {
            //ArrayList<WeatherRecord> recList = (ArrayList<WeatherRecord>) bundle.getSerializable("WeatherRec");
            mTitleText.setText("Next 5 days");
            mDataTxt.setText(Contents);

        }

        if (Period==10)
        {
            //ArrayList<WeatherRecord> recList = (ArrayList<WeatherRecord>) bundle.getSerializable("WeatherRec");
            mTitleText.setText("History");
            mDataTxt.setText(Contents);

        }

    }




    //@SuppressLint("InlinedApi")
    }
