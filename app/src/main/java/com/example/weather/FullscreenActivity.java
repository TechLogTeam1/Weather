package com.example.weather;

import android.annotation.SuppressLint;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telecom.Call;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.jsoup.nodes.Entities.EscapeMode.base;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    //private View mContentView;
    private TextView mTextView;


    // Delayed removal of status and navigation bar

    // Note that some of these constants are new as of API 16 (Jelly Bean)
    // and API 19 (KitKat). It is safe to use them, as they are inlined
    // at compile-time and do nothing on earlier devices.

    private View mControlsView;
    private String OutText;
    private Button mButton1;
    private EditText mCity,mCode;
    private RadioButton mRadio1;
    private RadioButton mRadio2;
    private RadioButton mRadio3;

    ProgressDialog progressDialog;

    private String API="400b0e4928077be78efaf4523cd3a3b5";
    private String City;
    private String Code;
    private String CodeTxt;
    private String CallUrl;
    private String CallCode;
    private String Coords;
    private String Temp;
    private String Humidity;
    private String Contents;
    private String Units;
    private String UnitsTxt;
    private int i;

    //Edit AndroidManifest.xml
    //Add <uses-permission android:name="android.permission.INTERNET" />
    //after package command

    //If not runs in newer emulator (e.x. Pixel 2 API 29) simple uninstall app
    // from mobile emulator and run again

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mControlsView = findViewById(R.id.fullscreen_content_controls);
        //mContentView = findViewById(R.id.fullscreen_content);
        mTextView = findViewById(R.id.textView);
        mTextView.setMovementMethod(new ScrollingMovementMethod());

        mButton1= (Button) findViewById(R.id.button);
        mCity=(EditText) findViewById(R.id.editText);
        mCode=(EditText) findViewById(R.id.editText2);
        mRadio1= (RadioButton) findViewById(R.id.radioButton);
        mRadio2= (RadioButton) findViewById(R.id.radioButton2);
        mRadio3= (RadioButton) findViewById(R.id.radioButton3);

        City="London";Code="uk";
        Units="C";
        mCity.setText(City);
        mCode.setText(Code);
        mTextView.setText("");

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                City=mCity.getText().toString();
                Code=mCode.getText().toString();
                Content content=new Content();
                content.execute();

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

    private class Content extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(FullscreenActivity.this);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (OutText=="") OutText="Problem: Probably city not found";

            mTextView.setText(OutText);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Document doc= null;
            OutText="";
            //Code="";
            //Units="C";
            //City="London";Code="uk";
            //City="Paris";Code="fr";
            //City="Nigrita";Code="gr";

            if (Units=="C") UnitsTxt="&units=metric";     //Celsius
            if (Units=="F") UnitsTxt="&units=imperial";   //Fahrenheit
            if (Units=="K") UnitsTxt="";                  //Kelvin

            if (Code.length()<2) CodeTxt=""; else CodeTxt=","+Code;

            try {
                CallUrl="http://api.openweathermap.org/data/2.5/weather?q="+City+CodeTxt+UnitsTxt+"&APPID="+API;
                //doc = Jsoup.connect(CallUrl).ignoreContentType(true).get();
                Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();

                try {
                    JSONObject jsonObj = new JSONObject(Contents);
                    JSONObject obj2=jsonObj.getJSONObject("coord");
                    JSONObject obj3=jsonObj.getJSONObject("main");

                    CallCode=jsonObj.getString("cod"); //404 = City not found
                    Coords="Latitude:"+obj2.getString("lat");
                    Coords+="\n"+"Longitude:"+obj2.getString("lon");
                    Temp="Temperature:"+obj3.getString("temp")+" "+Units;
                    Humidity="Humidity:"+obj3.getString("humidity")+"%";

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //OutText=Contents;
                OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                        +Coords+"\n"+Temp+"\n"+Humidity;
                //OutText=CallUrl;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


}