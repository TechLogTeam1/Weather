package com.example.weather;

import com.example.weather.FullscreenActivity.Global1;
import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AdvSearch extends AppCompatActivity {


    private Button mButtonSel;
    private Button mButtonDis;
    private Button mButtonClear;
    private EditText mFromTemp,mToTemp;

    private CheckBox mCheckClear;
    private CheckBox mCheckClouds;
    private CheckBox mCheckRain;
    private CheckBox mCheckSnow;
    private CheckBox mCheckThunder;

    private RadioButton mRadio1;
    private RadioButton mRadio2;
    private RadioButton mRadio3;
    private String Units;
    private float FromT,ToT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_adv_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mButtonSel= (Button) findViewById(R.id.buttonSelAll1);
        mButtonDis= (Button) findViewById(R.id.buttonDisAll1);
        mButtonClear= (Button) findViewById(R.id.buttonClear);

        mFromTemp= (EditText) findViewById(R.id.editTextFromT);
        mToTemp= (EditText) findViewById(R.id.editTextToT);

        mCheckClear=(CheckBox)findViewById(R.id.checkBox7);
        mCheckClouds=(CheckBox)findViewById(R.id.checkBox8);
        mCheckRain=(CheckBox)findViewById(R.id.checkBox9);
        mCheckSnow=(CheckBox)findViewById(R.id.checkBox10);
        mCheckThunder=(CheckBox)findViewById(R.id.checkBox11);

        mRadio1= (RadioButton) findViewById(R.id.radioButton12);
        mRadio2= (RadioButton) findViewById(R.id.radioButton13);
        mRadio3= (RadioButton) findViewById(R.id.radioButton14);

        mFromTemp.setText(String.valueOf(Global1.FromT));
        mToTemp.setText(String.valueOf(Global1.ToT));

        mButtonSel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCheckClear.setChecked(true);
                mCheckClouds.setChecked(true);
                mCheckRain.setChecked(true);
                mCheckSnow.setChecked(true);
                mCheckThunder.setChecked(true);
            }
        });

        mButtonDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckClear.setChecked(false);
                mCheckClouds.setChecked(false);
                mCheckRain.setChecked(false);
                mCheckSnow.setChecked(false);
                mCheckThunder.setChecked(false);
            }
        });

        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckClear.setChecked(true);
                mCheckClouds.setChecked(true);
                mCheckRain.setChecked(true);
                mCheckSnow.setChecked(true);
                mCheckThunder.setChecked(true);
                Global1.FromT=(float)-273.15;
                Global1.ToT=100;
                mFromTemp.setText("-273.15");
                mToTemp.setText("100.0");
            }
        });

        mFromTemp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FromT=Float.valueOf(mFromTemp.getText().toString());
                Global1.FromT=FromT;
            }
        });

        mToTemp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ToT=Float.valueOf(mToTemp.getText().toString());
                Global1.ToT=ToT;
            }
        });

        mRadio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio1.setChecked(true);
                mRadio2.setChecked(false);
                mRadio3.setChecked(false);
                Units="C";
            }
        });

        mRadio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio1.setChecked(false);
                mRadio2.setChecked(true);
                mRadio3.setChecked(false);
                Units="F";
            }
        });
        mRadio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio1.setChecked(false);
                mRadio2.setChecked(false);
                mRadio3.setChecked(true);
                Units="K";
            }
        });

    }



}
