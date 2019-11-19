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
    private String APIStack="516a194fae7c7db19dc99de1bfce0c6e\n";

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
    private String SiteUse;
    private String OutList;
    private String Date;
    private String CityKey;
    private int i;
    private int Period;
    private String Longitude,Latitude;
    //Edit AndroidManifest.xml
    //Add <uses-permission android:name="android.permission.INTERNET" />
    //after package command

    //If not runs in newer emulator (e.x. Pixel 2 API 29) simple uninstall app
    // from mobile emulator and run again
    //
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

        City="London";Code="";
        Units="C";Period=1;
        SiteUse="OpenWeather";
        mCity.setText(City);
        //mCode.setText(Code);
        mTextView.setText("");

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
                            Humidity = "Humidity:" + obj2.getString("humidity") + "%";
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
                    Humidity="Humidity:"+jsonObj.getString("RelativeHumidity")+"%";

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //"WeatherText"
                //OutText=WeatherText;
                //OutText=Contents;

                OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                        +Coords+"\n"+Temp+"\n"+Humidity;

                //OutText=CityKey;
                //OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                //      +Coords+"\n"+Temp+"\n"+Humidity;
                //OutText=CallUrl;

            } catch (IOException e) {
                e.printStackTrace();
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
                // OutText=Latitude+","+Longitude;
                OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                        +Coords+"\n"+Temp+"\n"+Humidity;
                //OutText=CallUrl;

            } catch (IOException e) {
                e.printStackTrace();
            }


            //Second call (Call DarkSky)
            //Contents=loadJSONFromAsset("DarkSky.json");
            //if (2<1)
            try {
                CallUrl="https://api.darksky.net/forecast/"+APIDark+"/"+Latitude+","+Longitude+UnitsTxt;
                //https://api.darksky.net/forecast/153f92e90eba11f8a60979ad1f5d791b/37.8267,-122.4233?units=si
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
                    Humidity="Humidity:"+(int)(HumFloat*100)+"%";

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

            //RUNS
            /*
            try {
                JSONObject jsonObj = new JSONObject(Contents);
                JSONObject obj2=jsonObj.getJSONObject("currently");
                Temp=obj2.getString("temperature");


            } catch (JSONException e) {
                e.printStackTrace();
            }


            OutText=Temp;
*/
            return;
        }



        @Override
        protected Void doInBackground(Void... voids) {

            Document doc= null;
            OutText="";

            if (SiteUse=="OpenWeather") ReadFromOpen();
            if (SiteUse=="AccuWeather") ReadFromAccu();
            if (SiteUse=="DarkSky") ReadFromDark();
            if (SiteUse=="WeatherStack") ReadFromStack();
            return null;
        }
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
                    Humidity="Humidity:"+obj3.getString("humidity")+"%";



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
                        Humidity = "Humidity:" + obj2.getString("humidity") + "%";
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


    void ReadFromStackPrev()
    {
        if (Units=="C") UnitsTxt="&units=metric";     //Celsius
        if (Units=="F") UnitsTxt="&units=imperial";   //Fahrenheit
        if (Units=="K") UnitsTxt="";                  //Kelvin

        if (Code.length()<2) CodeTxt=""; else CodeTxt=","+Code;


        //First Call
        try {
            CallUrl="http://api.weatherstack.com/current?access_key="+APIStack+"&query="+City;
            Contents="";
            Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();
            Contents=Contents.substring(1,Contents.length());

            try {

                JSONObject jsonObj = new JSONObject(Contents);
                JSONObject obj2 = jsonObj.getJSONObject("location");
                CityKey=jsonObj.getString("name");
                Coords="Latitude:"+obj2.getString("lat");
                Coords+="\n"+"Longitude:"+obj2.getString("lon");

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //Second Call
        try {
            //CallUrl="http://api.openweathermap.org/data/2.5/weather?q="+City+CodeTxt+UnitsTxt+"&APPID="+API;
            //CallUrl="http://dataservice.accuweather.com/locations/v1/cities/search?q="+City+"&apikey="+APIAccu;
            CallUrl="http://api.weatherstack.com/current?access_key="+APIStack+"&query="+City;

            Contents= Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();
            Contents=Contents.substring(1,Contents.length());
            try {
                JSONObject jsonObj = new JSONObject(Contents);
                JSONObject obj2 = jsonObj.getJSONObject("request");
                JSONObject obj3;

                if (Units=="m")
                    obj3 = obj2.getJSONObject("Metric");
                else
                if (Units=="f")
                    obj3 = obj2.getJSONObject("Imperial");
                else
                    obj3 = obj2.getJSONObject("Metric");

                Temp="temperature:"+obj3.getString("Value")+" "+Units;
                Humidity="humidity:"+jsonObj.getString("RelativeHumidity")+"%";

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //"WeatherText"
            //OutText=WeatherText;
            //OutText=Contents;

            OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                    +Coords+"\n"+Temp+"\n"+Humidity;

            //OutText=CityKey;
            //OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
            //      +Coords+"\n"+Temp+"\n"+Humidity;
            //OutText=CallUrl;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return;
    }

}
