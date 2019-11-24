package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
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
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import static org.jsoup.nodes.Entities.EscapeMode.base;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    //private View mContentView;
    private TextView mTextView;
    private TextView mDeg;
    private TextView mUnits;
    private TextView mDegSymbol;
    private TextView mHum;
    private TextView mConditions;
    private ImageView mWIcon;
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
    private RadioButton mRadio4;
    private RadioButton mRadio5;
    private RadioButton mRadio6;
    private RadioButton mRadio7;
    private RadioButton mRadio8;
    private RadioButton mRadio9;
    private RadioButton mRadio10;
    private RadioButton mRadio11;

    ProgressDialog progressDialog;

    private String APIOpen="400b0e4928077be78efaf4523cd3a3b5";
    private String APIAccu="GXj9XbCK7EOk5cVRnAOVN62PdDGJaTD6";
    private String APIDark="e478e283b61f95dc70771be89db8ce1c";
    private String APIBit="307702986d074885b6bdf41d74768e0c";
    private String APIStack="516a194fae7c7db19dc99de1bfce0c6e\n";

    private String City;
    private String Code;
    private String CodeTxt;
    private String CallUrl;
    private String CallCode;
    private String Coords;
    private String Temp;

    private float Temperature;
    private float Humidity;
    private String[] DateTxt= new String[1000];
    private float[] TemperatureData= new float[1000];
    private float[] HumidityData= new float[1000];


    private String HumidityTxt;
    private String Contents;
    private String Units;
    private String UnitsTxt;
    private String SiteUse;
    private String OutList;
    private String Date;
    private String CityKey;
    private String WeatherCon;
    private String WeatherConIcon;
    private int i;
    private int Period;
    private int ArraySize;
    private String Longitude,Latitude;
    private String jsonSTR;
    private long DateUnix;
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
        mDeg = findViewById(R.id.textTemp);
        mUnits = findViewById(R.id.textUnit);
        mDegSymbol=findViewById(R.id.textDeg);
        mHum=findViewById(R.id.textHum);
        mConditions=findViewById(R.id.WeatherState);
        //mTextView.setMovementMethod(new ScrollingMovementMethod());

        mButton1= (Button) findViewById(R.id.button);
        mCity=(EditText) findViewById(R.id.editText);
        //mCode=(EditText) findViewById(R.id.editText2);
        mRadio1= (RadioButton) findViewById(R.id.radioButton);
        mRadio2= (RadioButton) findViewById(R.id.radioButton2);
        mRadio3= (RadioButton) findViewById(R.id.radioButton3);
        mRadio4= (RadioButton) findViewById(R.id.radioButton5);
        mRadio5= (RadioButton) findViewById(R.id.radioButton6);
        mRadio6= (RadioButton) findViewById(R.id.radioButton7);

        mRadio7= (RadioButton) findViewById(R.id.radioButton4);
        mRadio8= (RadioButton) findViewById(R.id.radioButton8);
        mRadio9= (RadioButton) findViewById(R.id.radioButton9);
        mRadio10= (RadioButton) findViewById(R.id.radioButton10);
        mRadio11= (RadioButton) findViewById(R.id.radioButton11);

        mWIcon= (ImageView) findViewById(R.id.imageView2);

        City="London";Code="";
        //Code="uk";
        Units="C";Period=1;
        SiteUse="OpenWeather";
        mCity.setText(City);
        //mCode.setText(Code);
        //mTextView.setText("");
        Temperature=0;
        Humidity=0;

        mDeg.setText("--");
        mHum.setText("Humidity "+"--"+" %");
        mConditions.setText("--");


        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                City=mCity.getText().toString();
                //Code=mCode.getText().toString();
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

        mRadio4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio4.setChecked(true);
                mRadio5.setChecked(false);
                mRadio6.setChecked(false);
                Period=1;
            }
        });

        mRadio5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio4.setChecked(false);
                mRadio5.setChecked(true);
                mRadio6.setChecked(false);
                Period=2;
            }
        });
        mRadio6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio4.setChecked(false);
                mRadio5.setChecked(false);
                mRadio6.setChecked(true);
                Period=3;
            }
        });
        mRadio7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio7.setChecked(true);
                mRadio8.setChecked(false);
                mRadio9.setChecked(false);
                mRadio10.setChecked(false);
                mRadio11.setChecked(false);
                SiteUse="OpenWeather";
            }

        });
        mRadio8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio7.setChecked(false);
                mRadio8.setChecked(true);
                mRadio9.setChecked(false);
                mRadio10.setChecked(false);
                mRadio11.setChecked(false);
                SiteUse="AccuWeather";
            }
        });

        mRadio9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio7.setChecked(false);
                mRadio8.setChecked(false);
                mRadio9.setChecked(true);
                mRadio10.setChecked(false);
                mRadio11.setChecked(false);
                SiteUse="WeatherStack";
            }
        });
        mRadio10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio7.setChecked(false);
                mRadio8.setChecked(false);
                mRadio9.setChecked(false);
                mRadio10.setChecked(true);
                mRadio11.setChecked(false);
                SiteUse="DarkSky";
            }
        });
        mRadio11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio7.setChecked(false);
                mRadio8.setChecked(false);
                mRadio9.setChecked(false);
                mRadio10.setChecked(false);
                mRadio11.setChecked(true);
                SiteUse="Weatherbit";
            }
        });


    }

    public String ConvertUNIXtoDate(long unixSeconds) {

        //long unixSeconds = DateUnix;
        java.util.Date date = new java.util.Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Athens")); //NEW
        String formattedDate = sdf.format(date);
        Date = formattedDate;

        return Date;

    }


    public String loadJSONFromAsset(String File) {

        InputStream is= null;
        try {
            is = getAssets().open(File);
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();

            jsonSTR=new String(buffer,"UTF-8"); //PREV


        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonSTR;
    }


    public void OpenDetailed()
    {
        //for (i=0;i<5;i++) dateList.add(WeatherRec[i].DateTxt);

        Intent intent=new Intent(this,WeatherDetails.class);
        intent.putExtra("Period",Period);
        intent.putExtra("Contents",Contents);
        startActivity(intent);
    }

    private class Content extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(FullscreenActivity.this);
            progressDialog.show();
        }

        int ConvertToDp(int x)
        {
            int ret;
            final float scale = getBaseContext().getResources().getDisplayMetrics().density;

            ret =  (int)(x * scale + 0.5f);
        return ret;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //if (OutText=="") OutText="Problem: Probably city not found";

            if (OutText=="")
            {
                mDeg.setText("--");
                mHum.setText("Humidity "+"--"+" %");
                mConditions.setText("--");
                progressDialog.dismiss();
                return;
            }


            int valuex,valuey,deglen;
            int mainx,mainy;
            int l,t;
            int minusx;
            minusx=85;

            mDeg.setText(String.valueOf(Temperature));
            mHum.setText("Humidity "+String.valueOf(Humidity)+" %");
            mConditions.setText(WeatherCon);

            if (SiteUse=="OpenWeather")
            {
                //check for "mist" string ret
                if (WeatherCon.equals("Clear")) mWIcon.setImageResource(R.drawable.clearday);
                if (WeatherCon.equals("Clouds")) mWIcon.setImageResource(R.drawable.clouds);

                if (WeatherCon.equals("Mist")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equals("Smoke")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equals("Haze")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equals("Dust")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equals("Fog")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equals("Sand")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equals("Ash")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equals("Squall")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equals("Tornado")) mWIcon.setImageResource(R.drawable.clouds);

                if (WeatherCon.equals("Rain")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equals("Drizzle")) mWIcon.setImageResource(R.drawable.drizzleday); //Light Rain
                if (WeatherCon.equals("Thunderstorm")) mWIcon.setImageResource(R.drawable.thunder);
                if (WeatherCon.equals("Snow")) mWIcon.setImageResource(R.drawable.snow);
            }

            if (SiteUse=="AccuWeather")
            {

                //Clear
                if (WeatherCon.equalsIgnoreCase("Sunny")) mWIcon.setImageResource(R.drawable.clearday);
                if (WeatherCon.equalsIgnoreCase("Mostly sunny")) mWIcon.setImageResource(R.drawable.clearday);
                if (WeatherCon.equalsIgnoreCase("Partly sunny")) mWIcon.setImageResource(R.drawable.clearday);
                if (WeatherCon.equalsIgnoreCase("Intermittent clouds")) mWIcon.setImageResource(R.drawable.clearday);

                //Clouds
                if (WeatherCon.equalsIgnoreCase("Cloudy")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equalsIgnoreCase("Mostly cloudy")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equalsIgnoreCase("Hazy sunshine")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equalsIgnoreCase("Dreary (Overcast)")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equalsIgnoreCase("Fog")) mWIcon.setImageResource(R.drawable.clouds);

                //Rain
                if (WeatherCon.equalsIgnoreCase("Showers")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Mostly Cloudy w/ Showers")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Partly Sunny w/ Showers")) mWIcon.setImageResource(R.drawable.rain);

                //Thunders
                if (WeatherCon.equalsIgnoreCase("T-Storms")) mWIcon.setImageResource(R.drawable.thunder);
                if (WeatherCon.equalsIgnoreCase("Mostly Cloudy w/ T-Storms")) mWIcon.setImageResource(R.drawable.thunder);
                if (WeatherCon.equalsIgnoreCase("Partly Sunny w/ T-Storms")) mWIcon.setImageResource(R.drawable.thunder);

                if (WeatherCon.equalsIgnoreCase("Rain")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Flurries")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Mostly Cloudy w/ Flurries")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Partly Sunny w/ Flurries")) mWIcon.setImageResource(R.drawable.rain);

                //Snow
                if (WeatherCon.equalsIgnoreCase("Snow")) mWIcon.setImageResource(R.drawable.snow);
                if (WeatherCon.equalsIgnoreCase("Mostly Cloudy w/ Snow")) mWIcon.setImageResource(R.drawable.snow);
                if (WeatherCon.equalsIgnoreCase("Ice")) mWIcon.setImageResource(R.drawable.snow);
                if (WeatherCon.equalsIgnoreCase("Sleet")) mWIcon.setImageResource(R.drawable.snow); //Χιονόνερο
                if (WeatherCon.equalsIgnoreCase("Freezing rain")) mWIcon.setImageResource(R.drawable.snow);
                if (WeatherCon.equalsIgnoreCase("Rain and snow")) mWIcon.setImageResource(R.drawable.snow);

                //Windy (Clouds)
                if (WeatherCon.equalsIgnoreCase("Windy")) mWIcon.setImageResource(R.drawable.clouds);

                //Clear
                if (WeatherCon.equalsIgnoreCase("Clear")) mWIcon.setImageResource(R.drawable.clearday);
                if (WeatherCon.equalsIgnoreCase("Mostly clear")) mWIcon.setImageResource(R.drawable.clearday);
                if (WeatherCon.equalsIgnoreCase("Partly cloudy")) mWIcon.setImageResource(R.drawable.clouds);

                //Clouds
                if (WeatherCon.equalsIgnoreCase("Intermittent clouds")) mWIcon.setImageResource(R.drawable.clouds);

                //Rain

                if (WeatherCon.equalsIgnoreCase("Partly Cloudy w/ Showers")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Mostly Cloudy w/ Showers")) mWIcon.setImageResource(R.drawable.rain);

                if (WeatherCon.equalsIgnoreCase("Partly Cloudy w/ T-Storms")) mWIcon.setImageResource(R.drawable.thunder);
                if (WeatherCon.equalsIgnoreCase("Mostly Cloudy w/ T-Storms")) mWIcon.setImageResource(R.drawable.thunder);

                if (WeatherCon.equalsIgnoreCase("Mostly Cloudy w/ Flurries")) mWIcon.setImageResource(R.drawable.rain);

                //Snow
                if (WeatherCon.equalsIgnoreCase("Mostly Cloudy w/ Snow")) mWIcon.setImageResource(R.drawable.snow);

            }

            if (SiteUse=="DarkSky")
            {
                if (WeatherConIcon.equalsIgnoreCase("clear-day")) mWIcon.setImageResource(R.drawable.clearday);
                if (WeatherConIcon.equalsIgnoreCase("clear-night")) mWIcon.setImageResource(R.drawable.clearday); //CHECK

                if (WeatherConIcon.equalsIgnoreCase("party-cloudy-day")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherConIcon.equalsIgnoreCase("party-cloudy-night")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherConIcon.equalsIgnoreCase("cloudy")) mWIcon.setImageResource(R.drawable.clouds);

                if (WeatherConIcon.equalsIgnoreCase("rain")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherConIcon.equalsIgnoreCase("sleet")) mWIcon.setImageResource(R.drawable.rain); //Χιονόνερο
                if (WeatherConIcon.equalsIgnoreCase("snow")) mWIcon.setImageResource(R.drawable.snow);
                if (WeatherConIcon.equalsIgnoreCase("wind")) mWIcon.setImageResource(R.drawable.clouds);
                
                if (WeatherConIcon.equalsIgnoreCase("fog")) mWIcon.setImageResource(R.drawable.clouds); //Ομίχλη

                //NEW //CHECK
                if (WeatherCon.equalsIgnoreCase("Mostly Cloudy")) mWIcon.setImageResource(R.drawable.clouds);
                }

            if (SiteUse=="Weatherbit") {
                //check for "mist" string ret
                if (WeatherCon.equalsIgnoreCase("Clear Sky")) mWIcon.setImageResource(R.drawable.clearday);

                if (WeatherCon.equalsIgnoreCase("Few Clouds")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equalsIgnoreCase("Scattered clouds")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equalsIgnoreCase("Broken clouds")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equalsIgnoreCase("Overcast clouds")) mWIcon.setImageResource(R.drawable.clouds);

                if (WeatherCon.equalsIgnoreCase("Unknown Precipitation")) mWIcon.setImageResource(R.drawable.rain);

                if (WeatherCon.equalsIgnoreCase("Fog")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equalsIgnoreCase("Sand/dust")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equalsIgnoreCase("Haze")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equalsIgnoreCase("Smoke")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.equalsIgnoreCase("Mist")) mWIcon.setImageResource(R.drawable.clouds);

                if (WeatherCon.equalsIgnoreCase("Flurries")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Heavy snow shower")) mWIcon.setImageResource(R.drawable.rain); //Χιονόνερο
                if (WeatherCon.equalsIgnoreCase("Snow shower")) mWIcon.setImageResource(R.drawable.rain);

                if (WeatherCon.equalsIgnoreCase("Heavy sleet")) mWIcon.setImageResource(R.drawable.rain); //Χιονόνερο
                if (WeatherCon.equalsIgnoreCase("Sleet")) mWIcon.setImageResource(R.drawable.rain); //Χιονόνερο
                if (WeatherCon.equalsIgnoreCase("Mix snow/rain")) mWIcon.setImageResource(R.drawable.rain); //Χιονόνερο

                if (WeatherCon.equalsIgnoreCase("Heavy Snow")) mWIcon.setImageResource(R.drawable.snow);
                if (WeatherCon.equalsIgnoreCase("Snow")) mWIcon.setImageResource(R.drawable.snow);
                if (WeatherCon.equalsIgnoreCase("Light Snow")) mWIcon.setImageResource(R.drawable.snow);

                if (WeatherCon.equalsIgnoreCase("Heavy shower rain")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Shower rain")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Light shower rain")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Freezing rain")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Heavy Rain")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Moderate Rain")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Light Rain")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Heavy Drizzle")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Drizzle")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.equalsIgnoreCase("Light Drizzle")) mWIcon.setImageResource(R.drawable.rain);

                if (WeatherCon.equalsIgnoreCase("Thunderstorm with Hail")) mWIcon.setImageResource(R.drawable.thunder);
                if (WeatherCon.equalsIgnoreCase("Thunderstorm with drizzle")) mWIcon.setImageResource(R.drawable.thunder);
                if (WeatherCon.equalsIgnoreCase("Thunderstorm with light drizzle")) mWIcon.setImageResource(R.drawable.thunder);
                if (WeatherCon.equalsIgnoreCase("Thunderstorm with heavy rain")) mWIcon.setImageResource(R.drawable.thunder);
                if (WeatherCon.equalsIgnoreCase("Thunderstorm with rain")) mWIcon.setImageResource(R.drawable.thunder);
                if (WeatherCon.equalsIgnoreCase("Thunderstorm with light rain")) mWIcon.setImageResource(R.drawable.thunder);

            }

            if (SiteUse=="WeatherStack")
            {
                if (WeatherCon.equalsIgnoreCase("Clear")) mWIcon.setImageResource(R.drawable.clearday);
                if (WeatherCon.equalsIgnoreCase("Cloudy")) mWIcon.setImageResource(R.drawable.clouds); //?
                if (WeatherCon.equalsIgnoreCase("Partly Cloudy")) mWIcon.setImageResource(R.drawable.clouds);

                //if (WeatherCon.equalsIgnoreCase("Rain")) mWIcon.setImageResource(R.drawable.rain);//>
                //if (WeatherCon.equalsIgnoreCase("Moderate rain")) mWIcon.setImageResource(R.drawable.rain);

                if (WeatherCon.contains("Cloudy")) mWIcon.setImageResource(R.drawable.clouds);
                if (WeatherCon.contains("cloudy")) mWIcon.setImageResource(R.drawable.clouds);

                if (WeatherCon.contains("Rain")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.contains("rain")) mWIcon.setImageResource(R.drawable.rain);

                if (WeatherCon.contains("Snow")) mWIcon.setImageResource(R.drawable.snow);
                if (WeatherCon.contains("snow")) mWIcon.setImageResource(R.drawable.snow);

                if (WeatherCon.contains("Thunder")) mWIcon.setImageResource(R.drawable.thunder);
                if (WeatherCon.contains("thunder")) mWIcon.setImageResource(R.drawable.thunder);

                if (WeatherCon.contains("Drizzle")) mWIcon.setImageResource(R.drawable.rain);
                if (WeatherCon.contains("drizzle")) mWIcon.setImageResource(R.drawable.rain);

            }


            mUnits.setText(Units);
            FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            final float scale = getBaseContext().getResources().getDisplayMetrics().density;

            // convert the DP into pixel

            deglen=String.valueOf(Temperature).length();
            valuex=165;valuey=490;
            if (deglen>5) valuex+=10;

            valuex-=minusx;
            params1.setMargins(ConvertToDp(valuex),ConvertToDp(valuey),0,0);
            mDeg.setLayoutParams(params1);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

            // convert the DP into pixel

            deglen=String.valueOf(Temperature).length();
            valuex=210;valuey=480;valuex+=deglen*10;
            if (deglen>5) valuex+=10;

            valuex-=minusx;
            params.setMargins(ConvertToDp(valuex),ConvertToDp(valuey),0,0);
            mDegSymbol.setLayoutParams(params);

            FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

            deglen=String.valueOf(Temperature).length();
            valuex=210+30;valuey=480;valuex+=deglen*10;
            if (deglen>5) valuex+=10;

            valuex-=minusx;
            params2.setMargins(ConvertToDp(valuex),ConvertToDp(valuey),0,0);

            mUnits.setLayoutParams(params2);

            //mTextView.setText(OutText);
            progressDialog.dismiss();
        }

        void ReadFromOpen()
        {
            if (Units=="C") UnitsTxt="&units=metric";     //Celsius
            if (Units=="F") UnitsTxt="&units=imperial";   //Fahrenheit
            if (Units=="K") UnitsTxt="";                  //Kelvin

            if (Code.length()<2) CodeTxt=""; else CodeTxt=","+Code;

            if (Period==1)
                try {
                    CallUrl="http://api.openweathermap.org/data/2.5/weather?q="+City+CodeTxt+UnitsTxt+"&APPID="+APIOpen;
                    //doc = Jsoup.connect(CallUrl).ignoreContentType(true).get();
                    Contents="";
                    Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();

                    try {

                        JSONObject jsonObj = new JSONObject(Contents);
                        JSONObject obj2=jsonObj.getJSONObject("coord");
                        JSONObject obj3=jsonObj.getJSONObject("main");

                        JSONArray WeatherArray =jsonObj.getJSONArray("weather");
                        JSONObject json2 = WeatherArray.getJSONObject(0);

                        CallCode=jsonObj.getString("cod"); //404 = City not found
                        Coords="Latitude:"+obj2.getString("lat");
                        Coords+="\n"+"Longitude:"+obj2.getString("lon");
                        Temp="Temperature:"+obj3.getString("temp")+" "+Units;
                        HumidityTxt="Humidity:"+obj3.getString("humidity")+"%";

                        Temperature=Float.valueOf(obj3.getString("temp"));
                        Humidity=Float.valueOf(obj3.getString("humidity"));
                        WeatherCon=json2.getString("main");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //OutText=Contents;
                    OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                            +Coords+"\n"+Temp+"\n"+HumidityTxt;
                    //OutText=CallUrl;

                } catch (IOException e) {
                    e.printStackTrace();
                }


            if (Period==3)
                try {
                    CallUrl="http://api.openweathermap.org/data/2.5/forecast?q="+City+CodeTxt+UnitsTxt+"&APPID="+APIOpen;
                    //doc = Jsoup.connect(CallUrl).ignoreContentType(true).get();
                    Contents="";
                    Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();

                    try {
                        JSONObject jsonObj = new JSONObject(Contents);
                        JSONArray baseArray =jsonObj.getJSONArray("list");

                        OutList="";

                        JSONObject objcoords1 = jsonObj.getJSONObject("city");
                        JSONObject objcoords2 = objcoords1.getJSONObject("coord");
                        Coords="Latitude:"+objcoords2.getString("lat");
                        Coords+="\n"+"Longitude:"+objcoords2.getString("lon");
                        ArraySize=baseArray.length();

                        for (int i = 0; i < baseArray.length(); i++) {
                            JSONObject json2 = baseArray.getJSONObject(i);
                            JSONObject obj2 = json2.getJSONObject("main");

                            Temp = obj2.getString("temp") + " " + Units;
                            HumidityTxt = "Humidity:" + obj2.getString("humidity") + "%";
                            Date=json2.getString("dt_txt");

                            DateTxt[i]=Date;
                            TemperatureData[i]=Float.valueOf(obj2.getString("temp"));
                            HumidityData[i]=Float.valueOf(obj2.getString("humidity"));
                            //OutList+="Date:"+Date+"\n"+"ArrayPos:"+(i+1)+"\n"+Temp+"\n"+Humidity+"\n";

                            OutList+="---------------------------------------------\n"+"Date:"+Date+
                                    "\n---------------------------------------------\n"+
                                    Temp+"\n"+HumidityTxt+"\n";

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    OutText="City:"+City+CodeTxt+"\n"+Coords+"\n"+
                            OutList;

                    //OutText=Contents;
                    //OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                    //  +Temp+"\n"+Humidity;

                    //OutText=CallUrl;



                } catch (IOException e) {
                    e.printStackTrace();
                }


            if (Period==3)
            {

                Contents="Dates\n";

                for (i=0;i<ArraySize;i++)
                {
                Contents+=DateTxt[i]+"\n"+"Temp:"+TemperatureData[i]+Units+"  Humidity:"+HumidityData[i]+"%"+"\n";
                }


                OpenDetailed();
            }
            return;
        }



        void ReadFromAccu()
        {
            if (Units=="C") UnitsTxt="&units=metric";     //Celsius
            if (Units=="F") UnitsTxt="&units=imperial";   //Fahrenheit
            if (Units=="K") UnitsTxt="";                  //Kelvin

            if (Code.length()<2) CodeTxt=""; else CodeTxt=","+Code;

            //CityKey="328328"; //London
            //CallUrl="http://dataservice.accuweather.com/locations/v1/cities/search?q=London&apikey=GXj9XbCK7EOk5cVRnAOVN62PdDGJaTD6";
            //CallUrl="http://dataservice.accuweather.com/locations/v1/cities/search?q="+City+"&apikey="+APIAccu;

            //First Call
            try {
                CallUrl="http://dataservice.accuweather.com/locations/v1/cities/search?q="+City+"&apikey="+APIAccu;
                Contents="";
                Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();
                Contents=Contents.substring(1,Contents.length());

                try {

                    JSONObject jsonObj = new JSONObject(Contents);
                    JSONObject obj2 = jsonObj.getJSONObject("GeoPosition");
                    CityKey=jsonObj.getString("Key");
                    Coords="Latitude:"+obj2.getString("Latitude");
                    Coords+="\n"+"Longitude:"+obj2.getString("Longitude");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            catch (IOException e) {
                e.printStackTrace();
            }

            //Second Call
            if (Period==1)
            try {
                //CallUrl="http://api.openweathermap.org/data/2.5/weather?q="+City+CodeTxt+UnitsTxt+"&APPID="+API;
                //CallUrl="http://dataservice.accuweather.com/locations/v1/cities/search?q="+City+"&apikey="+APIAccu;
                CallUrl="http://dataservice.accuweather.com/currentconditions/v1/"+CityKey+"?apikey="+APIAccu+"&details=true";

                Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();
                Contents=Contents.substring(1,Contents.length());
                try {
                    JSONObject jsonObj = new JSONObject(Contents);
                    JSONObject obj2 = jsonObj.getJSONObject("Temperature");
                    JSONObject obj3;

                    if (Units=="C")
                        obj3 = obj2.getJSONObject("Metric");
                    else
                    if (Units=="F")
                        obj3 = obj2.getJSONObject("Imperial");
                    else
                        obj3 = obj2.getJSONObject("Metric");

                    Temp="Temperature:"+obj3.getString("Value")+" "+Units;
                    HumidityTxt="Humidity:"+jsonObj.getString("RelativeHumidity")+"%";

                    Temperature=Float.valueOf(obj3.getString("Value"));
                    Humidity=Float.valueOf(jsonObj.getString("RelativeHumidity"));

                    WeatherCon=jsonObj.getString("WeatherText");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //"WeatherText"
                //OutText=WeatherText;
                //OutText=Contents;

                OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                        +Coords+"\n"+Temp+"\n"+HumidityTxt;

                //OutText=CityKey;
                //OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                //      +Coords+"\n"+Temp+"\n"+Humidity;
                //OutText=CallUrl;

            } catch (IOException e) {
                e.printStackTrace();
            }


            //CHECK Not Ready Yet
            if (Period==2)
            {
                try {
                    //CallUrl="http://api.openweathermap.org/data/2.5/weather?q="+City+CodeTxt+UnitsTxt+"&APPID="+API;
                    //CallUrl="http://dataservice.accuweather.com/locations/v1/cities/search?q="+City+"&apikey="+APIAccu;
                    //CallUrl="http://dataservice.accuweather.com/currentconditions/v1/"+CityKey+"?apikey="+APIAccu+"&details=true";
                    CallUrl="http://dataservice.accuweather.com/currentconditions/v1/"+CityKey+"/historical/24?apikey="+APIAccu;
                    Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();
                    Contents=Contents.substring(1,Contents.length());
                    try {
                        JSONObject jsonObj = new JSONObject(Contents);
                        JSONObject obj2 = jsonObj.getJSONObject("Temperature");
                        JSONObject obj3;

                        if (Units=="C")
                            obj3 = obj2.getJSONObject("Metric");
                        else
                        if (Units=="F")
                            obj3 = obj2.getJSONObject("Imperial");
                        else
                            obj3 = obj2.getJSONObject("Metric");

                        Temp="Temperature:"+obj3.getString("Value")+" "+Units;
                        HumidityTxt="Humidity:"+jsonObj.getString("RelativeHumidity")+"%";

                        Temperature=Float.valueOf(obj3.getString("Value"));
                        Humidity=Float.valueOf(jsonObj.getString("RelativeHumidity"));

                        WeatherCon=jsonObj.getString("WeatherText");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //"WeatherText"
                    //OutText=WeatherText;
                    //OutText=Contents;

                    OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                            +Coords+"\n"+Temp+"\n"+HumidityTxt;

                    //OutText=CityKey;
                    //OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                    //      +Coords+"\n"+Temp+"\n"+Humidity;
                    //OutText=CallUrl;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if (Period==3)
            {


                if (Units=="C") UnitsTxt="&metric=true";
                if (Units=="F") UnitsTxt="";


                try {
                    CallUrl="http://dataservice.accuweather.com/forecasts/v1/daily/5day/"+CityKey+"?apikey="+APIAccu+UnitsTxt;
                    Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();

                try {



                    JSONObject jsonObj = new JSONObject(Contents);
                    JSONArray baseArray=jsonObj.getJSONArray("DailyForecasts");
                    //JSONObject obj2 = jsonObj.getJSONObject("Temperature");
                    //JSONObject obj3;

                /*
                if (Units=="C")
                    obj3 = obj2.getJSONObject("Metric");
                else
                if (Units=="F")
                    obj3 = obj2.getJSONObject("Imperial");
                else
                    obj3 = obj2.getJSONObject("Metric");

                Temp="Temperature:"+obj3.getString("Value")+" "+Units;
                Humidity="Humidity:"+jsonObj.getString("RelativeHumidity")+"%";
                */


                    OutList="";
                    ArraySize=baseArray.length();

                    for (i = 0; i < baseArray.length(); i++) {
                        JSONObject json2 = baseArray.getJSONObject(i);
                        JSONObject obj2 = json2.getJSONObject("Temperature");
                        JSONObject obj3 = obj2.getJSONObject("Maximum");

                        Temp = "Temp:" + obj3.getString("Value") + " " + Units;
                        HumidityTxt="--";
                        //Humidity = "Humidity:" + obj2.getString("humidity") + "%";
                        //DateUnix=Long.valueOf(json2.getString("time"));
                        //Date=ConvertUNIXtoDate(DateUnix);
                        Date=json2.getString("Date");


                        DateTxt[i]=Date;
                        TemperatureData[i]=Float.valueOf(obj3.getString("Value"));
                        HumidityData[i]=0;

                        OutList+="---------------------------------------------\n"+"Date:"+Date+
                                "\n---------------------------------------------\n"+
                                Temp+"\n"+Humidity+"\n";

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //OutText=Temp;

                //OutText=OutList;
                OutText=Contents;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if (Period==3)
            {

                Contents="Dates\n";

                //Contents+=OutText;
                for (i=0;i<ArraySize;i++)
                {
                    Contents+=DateTxt[i]+"\n"+"Temp:"+TemperatureData[i]+Units+"  Humidity:"+HumidityData[i]+"%"+"\n";
                }


                OpenDetailed();
            }


            return;
        }

        void ReadFromDark()
        {

            if (Units=="C") UnitsTxt="?units=si";     //Celsius
            if (Units=="F") UnitsTxt="";   //Fahrenheit
            //if (Units=="F") UnitsTxt="&units=imperial";   //Fahrenheit
            //if (Units=="K") UnitsTxt="";                  //Kelvin

            if (Code.length()<2) CodeTxt=""; else CodeTxt=","+Code;

            float HumFloat;
            //First Call (Get Coords)

            try {
                CallUrl="http://api.openweathermap.org/data/2.5/weather?q="+City+CodeTxt+"&APPID="+APIOpen;
                //doc = Jsoup.connect(CallUrl).ignoreContentType(true).get();
                Contents="";
                Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();

                try {
                    JSONObject jsonObj = new JSONObject(Contents);
                    JSONObject obj2=jsonObj.getJSONObject("coord");
                    CallCode=jsonObj.getString("cod"); //404 = City not found
                    Latitude=obj2.getString("lat");
                    Longitude=obj2.getString("lon");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //OutText=Contents;
                OutText=Latitude+","+Longitude;
                //OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                //      +Coords+"\n"+Temp+"\n"+Humidity;
                //OutText=CallUrl;

            } catch (IOException e) {
                e.printStackTrace();
            }


            //Second call (Call DarkSky)
            //Contents=loadJSONFromAsset("DarkSky.json");
            //if (2<1)
            if (Period==1)
                try {
                    CallUrl="https://api.darksky.net/forecast/"+APIDark+"/"+Latitude+","+Longitude+UnitsTxt;
                    //CallUrl="https://api.darksky.net/forecast/153f92e90eba11f8a60979ad1f5d791b/37.8267,-122.4233";
                    //doc = Jsoup.connect(CallUrl).ignoreContentType(true).get();
                    Contents="";
                    Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();

                    try {
                        JSONObject jsonObj = new JSONObject(Contents);
                        JSONObject obj2=jsonObj.getJSONObject("currently");
                        Coords="Latitude:"+Latitude;
                        Coords+="\n"+"Longitude:"+Longitude;

                        Temp="Temperature:"+obj2.getString("temperature")+" "+Units;
                        HumFloat=Float.valueOf(obj2.getString("humidity"));
                        HumidityTxt="Humidity:"+(int)(HumFloat*100)+"%";

                        Temperature=Float.valueOf(obj2.getString("temperature"));
                        Humidity=Float.valueOf(obj2.getString("humidity"))*100;
                        WeatherCon=obj2.getString("summary");
                        WeatherConIcon=obj2.getString("icon");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //OutText=Contents;
                    //OutText=Coords+"\n"+Temp;
                    OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                            +Coords+"\n"+Temp+"\n"+Humidity;
                    //OutText=CallUrl;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            if (Period==2)
                try {
                    //CallUrl="http://api.openweathermap.org/data/2.5/forecast?q="+City+CodeTxt+UnitsTxt+"&APPID="+APIOpen;
                    //CallUrl="http://api.openweathermap.org/data/2.5/forecast?q="+City+"&APPID="+APIOpen;
                    //doc = Jsoup.connect(CallUrl).ignoreContentType(true).get();

                    CallUrl="https://api.darksky.net/forecast/"+APIDark+"/"+Latitude+","+Longitude+UnitsTxt;

                    Contents="";
                    Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();

                    Coords="Latitude:"+Latitude;
                    Coords+="\n"+"Longitude:"+Longitude;

                    try {
                        JSONObject jsonObj = new JSONObject(Contents);
                        JSONObject obj2=jsonObj.getJSONObject("hourly");
                        JSONArray baseArray=obj2.getJSONArray("data");

                        OutList="";
                        ArraySize=baseArray.length();
                        for (i = 0; i < baseArray.length(); i++) {
                            JSONObject json2 = baseArray.getJSONObject(i);
                            //JSONObject obj3 = json2.getJSONObject("temperatureMaxTime");

                            Temp = "Temp:" + json2.getString("temperature") + " " + Units;
                            HumidityTxt = "Humidity:" + json2.getString("humidity") + "%";
                            DateUnix=Long.valueOf(json2.getString("time"));
                            Date=ConvertUNIXtoDate(DateUnix);

                            WeatherCon="";
                            WeatherConIcon="";

                            DateTxt[i]=Date;
                            TemperatureData[i]=Float.valueOf(json2.getString("temperature"));
                            HumidityData[i]=Float.valueOf(json2.getString("humidity"))*100;


                            OutList+="---------------------------------------------\n"+"Date:"+Date+
                                    "\n---------------------------------------------\n"+
                                    Temp+"\n"+Humidity+"\n";

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    OutText="City:"+City+CodeTxt+"\n"+Coords+"\n"+
                            OutList;

                    //OutText=Contents;
                    //OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                    //  +Temp+"\n"+Humidity;

                    //OutText=CallUrl;



                } catch (IOException e) {
                    e.printStackTrace();
                }


            if (Period==3)
                try {
                    //CallUrl="http://api.openweathermap.org/data/2.5/forecast?q="+City+CodeTxt+UnitsTxt+"&APPID="+APIOpen;
                    //CallUrl="http://api.openweathermap.org/data/2.5/forecast?q="+City+"&APPID="+APIOpen;
                    //doc = Jsoup.connect(CallUrl).ignoreContentType(true).get();

                    CallUrl="https://api.darksky.net/forecast/"+APIDark+"/"+Latitude+","+Longitude+UnitsTxt;

                    Contents="";
                    Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();

                    Coords="Latitude:"+Latitude;
                    Coords+="\n"+"Longitude:"+Longitude;

                    try {
                        JSONObject jsonObj = new JSONObject(Contents);
                        JSONObject obj2=jsonObj.getJSONObject("daily");
                        JSONArray baseArray=obj2.getJSONArray("data");

                        OutList="";
                        ArraySize=baseArray.length();
                        for (i = 0; i < baseArray.length(); i++) {
                            JSONObject json2 = baseArray.getJSONObject(i);
                            //JSONObject obj3 = json2.getJSONObject("temperatureMaxTime");

                            Temp = "Temp:"+json2.getString("temperatureMax") + " " + Units;
                            HumidityTxt = "Humidity:" + json2.getString("humidity") + "%";
                            DateUnix=Long.valueOf(json2.getString("time"));
                            Date=ConvertUNIXtoDate(DateUnix);

                            WeatherCon="";
                            WeatherConIcon="";

                            DateTxt[i]=Date;
                            TemperatureData[i]=Float.valueOf(json2.getString("temperatureMax"));
                            HumidityData[i]=Float.valueOf(json2.getString("humidity"))*100;


                            OutList+="---------------------------------------------\n"+"Date:"+Date+
                                    "\n---------------------------------------------\n"+
                                    Temp+"\n"+Humidity+"\n";

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    OutText="City:"+City+CodeTxt+"\n"+Coords+"\n"+
                            OutList;

                    //OutText=Contents;
                    //OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                    //  +Temp+"\n"+Humidity;

                    //OutText=CallUrl;



                } catch (IOException e) {
                    e.printStackTrace();
                }


            if (Period==2)
            {

                Contents="Dates\n";

                for (i=0;i<ArraySize;i++)
                {
                    Contents+=DateTxt[i]+"\n"+"Temp:"+TemperatureData[i]+Units+"  Humidity:"+HumidityData[i]+"%"+"\n";
                }


                OpenDetailed();
            }


            if (Period==3)
            {

                Contents="Dates\n";

                for (i=0;i<ArraySize;i++)
                {
                    Contents+=DateTxt[i]+"\n"+"Temp:"+TemperatureData[i]+Units+"  Humidity:"+HumidityData[i]+"%"+"\n";
                }


                OpenDetailed();
            }


            return;
        }



        void ReadFromBit()
        {
            //if (Units=="C") UnitsTxt="&units=M";   //Celsius
            if (Units=="C") UnitsTxt="";   //Celsius (Default)
            if (Units=="F") UnitsTxt="&units=I";   //Fahrenheit
            if (Units=="K") UnitsTxt="&units=S";   //Kelvin

            if (Code.length()<2) CodeTxt=""; else CodeTxt=","+Code;

            if (Period==1)
                try {

                    CallUrl="https://api.weatherbit.io/v2.0/current?city="+City+"&key="+APIBit+UnitsTxt;

                    Contents="";
                    Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();

                    try {
                        //JSONObject jsonObj = new JSONObject(Contents);
                        JSONObject jsonObj = null;
                        jsonObj = new JSONObject(Contents);
                        JSONArray baseArray =jsonObj.getJSONArray("data");
                        JSONObject json2 = baseArray.getJSONObject(0);
                        JSONObject obj2=json2.getJSONObject("weather");

                        Temp = json2.getString("temp") + " " + Units;
                        //JSONObject obj2 = json2.getJSONObject("weather");

                        Temperature=Float.valueOf(json2.getString("temp"));
                        Humidity=Float.valueOf(json2.getString("rh"));

                        Coords="Latitude:"+json2.getString("lat");
                        Coords+="\n"+"Longitude:"+json2.getString("lon");
                        HumidityTxt="Humidity:"+json2.getString("rh")+"%"; //rh = Relative Humidity

                        WeatherCon=obj2.getString("description");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //OutText=Contents;
                    //OutText=Temp;
                    OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                            +Coords+"\n"+Temp+"\n"+HumidityTxt;
                    //OutText=CallUrl;

                } catch (IOException e) {
                    e.printStackTrace();
                }



            //if (Period==2) OutText="Period: Last 24 hours";
            //if (Period==3) OutText="Period: Last 5 days";

            return;
        }

        void ReadFromStack()
        {
            if (Units=="C") UnitsTxt="&units=m";   //Celsius
            if (Units=="F") UnitsTxt="&units=f";   //Fahrenheit
            if (Units=="K") UnitsTxt="&units=s";   //Kelvin

            if (Code.length()<2) CodeTxt=""; else CodeTxt=","+Code;
            //http://api.weatherstack.com/current?access_key=e4c1390e4110c5a78e89c99f56b94b08&query=London
            if (Period==1)
                try {
                    //CallUrl="http://api.openweathermap.org/data/2.5/weather?q="+City+CodeTxt+UnitsTxt+"&APPID="+APIOpen;
                    //CallUrl="http://api.weatherstack.com/current?access_key=e4c1390e4110c5a78e89c99f56b94b08&query=London";


                    CallUrl="http://api.weatherstack.com/current?access_key="+APIStack+"&query="+City+UnitsTxt;


                    //doc = Jsoup.connect(CallUrl).ignoreContentType(true).get();
                    Contents="";
                    Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();

                    try {
                        JSONObject jsonObj = new JSONObject(Contents);

                        JSONObject obj2=jsonObj.getJSONObject("location");
                        JSONObject obj3=jsonObj.getJSONObject("current");


                        //CallCode=jsonObj.getString("cod"); //404 = City not found
                        Coords="Latitude:"+obj2.getString("lat");
                        Coords+="\n"+"Longitude:"+obj2.getString("lon");
                        Temp="Temperature:"+obj3.getString("temperature")+" "+Units;
                        HumidityTxt="Humidity:"+obj3.getString("humidity")+"%";

                        Temperature=Float.valueOf(obj3.getString("temperature"));
                        Humidity=Float.valueOf(obj3.getString("humidity"));
                        WeatherCon=obj3.getString("weather_descriptions");
                        WeatherCon=WeatherCon.substring(2,WeatherCon.length()-2);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //OutText=Contents;
                    //OutText=Humidity;

                    OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                            +Coords+"\n"+Temp+"\n"+Humidity;
                    //OutText=CallUrl;

                } catch (IOException e) {
                    e.printStackTrace();
                }


            if (Period==3)
                try {
                    CallUrl="http://api.openweathermap.org/data/2.5/forecast?q="+City+CodeTxt+UnitsTxt+"&APPID="+APIOpen;
                    //doc = Jsoup.connect(CallUrl).ignoreContentType(true).get();
                    Contents="";
                    Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();

                    try {
                        JSONObject jsonObj = new JSONObject(Contents);
                        JSONArray baseArray =jsonObj.getJSONArray("list");

                        OutList="";

                        JSONObject objcoords1 = jsonObj.getJSONObject("city");
                        JSONObject objcoords2 = objcoords1.getJSONObject("coord");
                        Coords="Latitude:"+objcoords2.getString("lat");
                        Coords+="\n"+"Longitude:"+objcoords2.getString("lon");

                        for (int i = 0; i < baseArray.length(); i++) {
                            JSONObject json2 = baseArray.getJSONObject(i);
                            JSONObject obj2 = json2.getJSONObject("main");

                            Temp = obj2.getString("temp") + " " + Units;
                            HumidityTxt = "Humidity:" + obj2.getString("humidity") + "%";
                            Date=json2.getString("dt_txt");


                            //OutList+="Date:"+Date+"\n"+"ArrayPos:"+(i+1)+"\n"+Temp+"\n"+Humidity+"\n";

                            OutList+="---------------------------------------------\n"+"Date:"+Date+
                                    "\n---------------------------------------------\n"+
                                    Temp+"\n"+Humidity+"\n";

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    OutText="City:"+City+CodeTxt+"\n"+Coords+"\n"+
                            OutList;

                    //OutText=Contents;
                    //OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                    //  +Temp+"\n"+Humidity;

                    //OutText=CallUrl;



                } catch (IOException e) {
                    e.printStackTrace();
                }

            if (Period==2) OutText="Period: Last 24 hours";
            //if (Period==3) OutText="Period: Last 5 days";

            return;
        }


        @Override
        protected Void doInBackground(Void... voids) {

            Document doc= null;
            OutText="";
            WeatherCon="";

            if (SiteUse=="OpenWeather") ReadFromOpen();
            if (SiteUse=="AccuWeather") ReadFromAccu();
            if (SiteUse=="DarkSky") ReadFromDark();
            if (SiteUse=="Weatherbit") ReadFromBit();
            if (SiteUse=="WeatherStack") ReadFromStack();

            return null;
        }
    }


}
