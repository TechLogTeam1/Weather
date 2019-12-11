package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.jsoup.nodes.Entities.EscapeMode.base;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
//First
public class FullscreenActivity extends AppCompatActivity {
//public class FullscreenActivity extends AppCompatActivity implements Serializable{

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
    private Button mButton2;
    private Button mButton3;
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
    private String APIStack="516a194fae7c7db19dc99de1bfce0c6e";

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
    private String WeatherCon1;
    private String WeatherConT;
    private String WeatherConIcon;
    private int i;
    private int Period;
    private int PrevPeriod,LastPeriod;
    private int ArraySize;
    private String Longitude,Latitude;
    private String jsonSTR;
    private long DateUnix;
    private String SearchCity;
    private boolean SearchCityOn,ContinueRec;
    boolean CoordsRun;
    private double Lat,Lon;


    static class HistoryDataClass
    {
        int pos;
        String Site;
        String City;
        String Date;
        float Temperature;
        float Humidity;
        String Units;
        String WeatherCon;
        String Comment;
    }

    HistoryDataClass HistoryData[]=new HistoryDataClass[10000];

    public static class Global1 {
        public static HistoryDataClass HistoryData[] = new HistoryDataClass[10000];
        public static HistoryDataClass SearchData[] = new HistoryDataClass[10000];
        public static int ArraySize;
        public static int SearchArraySize;
        public static boolean ServiceRun;
        public static String City;
        public static Date DateFrom;
        public static Date DateTo;
        public static int Period;
        public static String Contents;
        public static boolean FullHistory;
        public static float FromT,ToT;
        public static boolean Clear,Clouds,Rain,Snow,Thunder;

    }

    private int HistoryPos;
    private String TempT,HumidityT,UnitsT,SiteT,CommentT;
    private String CurrentDateT;
    private String Temp1;
    private String Humidity1;
    private String Site1;
    private String Comment1;
    private int searchpos;




    //Edit AndroidManifest.xml
    //Add <uses-permission android:name="android.permission.INTERNET" />
    //after package command

    //If not runs in newer emulator (e.x. Pixel 2 API 29) simple uninstall app
    // from mobile emulator and run again


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Global1.ServiceRun=false;
        //SetAlarmService();

        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mTextView = findViewById(R.id.textView);
        mDeg = findViewById(R.id.textTemp);
        mUnits = findViewById(R.id.textUnit);
        mDegSymbol=findViewById(R.id.textDeg);
        mHum=findViewById(R.id.textHum);
        mConditions=findViewById(R.id.WeatherState);

        mButton1= (Button) findViewById(R.id.button);
        mButton2= (Button) findViewById(R.id.buttonHistory);
        mButton3= (Button) findViewById(R.id.buttonHistory2);
        mCity=(EditText) findViewById(R.id.editText);
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
        Units="C";Period=1;LastPeriod=1;
        SiteUse="OpenWeather";
        mCity.setText(City);
        //mCode.setText(Code);
        //mTextView.setText("");
        Temperature=0;
        Humidity=0;

        Global1.FromT=(float) -273.15;
        Global1.ToT=100;

        Global1.Clear=true;
        Global1.Clouds=true;
        Global1.Rain=true;
        Global1.Snow=true;
        Global1.Thunder=true;

        mDeg.setText("--");
        mHum.setText("Humidity "+"--"+" %");
        mConditions.setText("--");

        //ReadHistory();

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                City=mCity.getText().toString();
                //Code=mCode.getText().toString();
                Content content=new Content();
                content.execute();

            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


           SearchCityOn=false;
           Global1.FullHistory=true;
           ReadHistory();

            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchCity=mCity.getText().toString();
                //SearchCityOn=true; //PREV
                Global1.City=SearchCity;
                //Global1.FullHistory=true; //PREV
                Global1.FullHistory=false;
                SearchCityOn=false;

                SearchHistory();
                Period=LastPeriod;

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
                Period=1;LastPeriod=1;
            }
        });

        mRadio5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio4.setChecked(false);
                mRadio5.setChecked(true);
                mRadio6.setChecked(false);
                Period=2;LastPeriod=2;
            }
        });
        mRadio6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio4.setChecked(false);
                mRadio5.setChecked(false);
                mRadio6.setChecked(true);
                Period=3;LastPeriod=3;
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

    public void SetAlarmService()

    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Athens")); //NEW

        //calendar.set(Calendar.YEAR,2019);
        //calendar.set(Calendar.MONTH,12);
        //calendar.set(Calendar.DAY_OF_MONTH,1);

        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 24);
        calendar.set(Calendar.SECOND, 0);
        setAlarm(calendar.getTimeInMillis());

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


    private void setAlarm(long time) {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, AlarmService.class);

        //creating a pending intent using the intent

        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0); //PREV
        //PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT); //NEW
        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi); //PREV
        //am.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }


    public void OpenDetailed()
    {

        Intent intent=new Intent(this,WeatherDetails.class);

        Global1.Period=Period;
        //Global1.Contents=Contents;
        //intent.putExtra("Period",Period);
        //intent.putExtra("Contents",Contents);
        startActivity(intent);
    }

    public void SearchHistory()
    {
        Period=10;Global1.Period=Period;
        //Global1.Contents=Contents;

        ReadHistory();

        Period=10;Global1.Period=Period;

        Intent intent=new Intent(this,Search.class);
        startActivity(intent);


    }

    public void ReadHistory()

    {
        String datapath,strdata;
        char[] scArray = new char[29];
        datapath=getApplicationInfo().dataDir+"/Weatherdat.txt";
        int o;
        int stopPos;
        float tempf,humf;
        tempf=0;
        humf=0;
        HistoryPos=0;


        try {
            File fileSc = new File(datapath);
            FileReader fileReader = new FileReader(fileSc);
            StringBuffer stringBuffer = new StringBuffer();
            int numCharsRead = 0; //NEW
            char[] charArrayCity = new char[50]; //PREV
            char[] charArray = new char[29]; //PREV
            char[] charArray2 = new char[10]; //PREV
            char[] charArray3 = new char[10]; //PREV
            char[] charArray4=new char[1];
            char[] charArray5=new char[100];
            char[] charArray6=new char[20];
            //char[] charArray = new char[16];
            OutText="";
            //PREV

            ArraySize=(int)(fileSc.length()-1)/320;
            Global1.ArraySize=ArraySize;
            for (i=0;i<=(fileSc.length()-1)/320;i++)
               {
                TempT="";HumidityT="";WeatherConT="";SiteT="";CommentT="";

                fileReader.read(charArray6);
                stringBuffer.append(charArray6, 0, 20);
                SiteT = stringBuffer.toString();
                stringBuffer.delete(0, 20);

                fileReader.read(charArrayCity);
                stringBuffer.append(charArrayCity, 0, 50);
                City = stringBuffer.toString();
                stringBuffer.delete(0, 50);

                fileReader.read(charArray);
                stringBuffer.append(charArray, 0, 29);
                Date = stringBuffer.toString();
                stringBuffer.delete(0, 29);

                fileReader.read(charArray2);
                stringBuffer.append(charArray2, 0, 10);
                TempT = stringBuffer.toString();
                stringBuffer.delete(0, 10);

                fileReader.read(charArray3);
                stringBuffer.append(charArray3, 0, 10);
                HumidityT = stringBuffer.toString();
                stringBuffer.delete(0, 10);

                fileReader.read(charArray5);
                stringBuffer.append(charArray5, 0, 100);
                WeatherConT = stringBuffer.toString();
                stringBuffer.delete(0, 100);

                fileReader.read(charArray4);
                stringBuffer.append(charArray4, 0, 1);
                UnitsT = stringBuffer.toString();
                stringBuffer.delete(0, 1);

                fileReader.read(charArray5);
                stringBuffer.append(charArray5, 0, 100);
                CommentT = stringBuffer.toString();
                stringBuffer.delete(0, 100);

                Global1.HistoryData[HistoryPos]=new HistoryDataClass();
                Global1.HistoryData[HistoryPos].Site=SiteT;
                Global1.HistoryData[HistoryPos].City=City;
                Global1.HistoryData[HistoryPos].Date=Date;
                Global1.HistoryData[HistoryPos].Temperature=Float.valueOf(TempT);
                Global1.HistoryData[HistoryPos].Humidity=Float.valueOf(HumidityT);
                Global1.HistoryData[HistoryPos].WeatherCon=WeatherConT; //CHECK
                Global1.HistoryData[HistoryPos].Units=UnitsT;
                Global1.HistoryData[HistoryPos].Comment=CommentT;
                Global1.HistoryData[HistoryPos].pos=HistoryPos;

                HistoryPos++;

                ContinueRec=false;

                   //if (SearchCityOn)
                   //if (Global1.HistoryData[HistoryPos-1].City.contains(SearchCity)) ContinueRec=true;


                   //if (!SearchCityOn) ContinueRec=true;

                   //if (ContinueRec)
                         OutText+="Site:"+Global1.HistoryData[HistoryPos-1].Site+"\n"+
                        "City:"+Global1.HistoryData[HistoryPos-1].City+"\n"
                        +"Date:"+Global1.HistoryData[HistoryPos-1].Date+"\n"+
                        "Temp:"+Global1.HistoryData[HistoryPos-1].Temperature+" "+ Global1.HistoryData[HistoryPos-1].Units+"\n"+
                        "Humidity:"+Global1.HistoryData[HistoryPos-1].Humidity+"%\n"
                        +"Conditions:"+Global1.HistoryData[HistoryPos-1].WeatherCon+"\n"
                        +"Comment:"+Global1.HistoryData[HistoryPos-1].Comment+"\n";
                        //+"Pos:"+Global1.HistoryData[HistoryPos-1].pos+"\n";


            }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        PrevPeriod=Period;
        Period=10;
        Contents="Records\n";

        if (!SearchCityOn)
        {
            searchpos = 0;
            for (i = 0; i < HistoryPos; i++) {
                    Global1.SearchData[searchpos]=new HistoryDataClass();
                    Global1.SearchData[searchpos].pos = i;
                    Global1.SearchData[searchpos].City = Global1.HistoryData[i].City;
                    Global1.SearchData[searchpos].Temperature = Global1.HistoryData[i].Temperature;
                    Global1.SearchData[searchpos].Humidity = Global1.HistoryData[i].Humidity;
                    Global1.SearchData[searchpos].Comment = Global1.HistoryData[i].Comment;
                    Global1.SearchData[searchpos].Site = Global1.HistoryData[i].Site;
                    Global1.SearchData[searchpos].WeatherCon = Global1.HistoryData[i].WeatherCon;
                    Global1.SearchData[searchpos].Date = Global1.HistoryData[i].Date;
                    Global1.SearchData[searchpos].Units = Global1.HistoryData[i].Units;

                    searchpos++;
                }
            }

        if (SearchCityOn) {
            searchpos = 0;
            for (i = 0; i < HistoryPos; i++) {
                if (Global1.HistoryData[i].City.toLowerCase().contains(SearchCity.toLowerCase())) {
                    Global1.SearchData[searchpos]=new HistoryDataClass();
                    Global1.SearchData[searchpos].pos = i;
                    Global1.SearchData[searchpos].City = Global1.HistoryData[i].City;
                    Global1.SearchData[searchpos].Temperature = Global1.HistoryData[i].Temperature;
                    Global1.SearchData[searchpos].Humidity = Global1.HistoryData[i].Humidity;
                    Global1.SearchData[searchpos].Comment = Global1.HistoryData[i].Comment;
                    Global1.SearchData[searchpos].Site = Global1.HistoryData[i].Site;
                    Global1.SearchData[searchpos].WeatherCon = Global1.HistoryData[i].WeatherCon;
                    Global1.SearchData[searchpos].Date = Global1.HistoryData[i].Date;
                    Global1.SearchData[searchpos].Units = Global1.HistoryData[i].Units;

                    searchpos++;
                }
            }
        }

        Global1.Period=Period;
        Global1.SearchArraySize=searchpos;
        Global1.Contents=OutText;

        if (Global1.FullHistory) OpenDetailed();

        Period=PrevPeriod;

        return;
    }

    public void SaveHistory()
    {


        //Get Current Day
        String CurrentDate;
        String Path;
        float TempF,HumF;
        String PrevTemp,PrevHum,PrevCon,PrevSite;

        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss z";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
        String formattedDate= dateFormat.format(date);
        CurrentDate=formattedDate;

        PrevTemp=TempT;
        PrevHum=HumidityT;
        PrevCon=WeatherConT;
        PrevSite=SiteT;

        TempT=Temp1;
        HumidityT=Humidity1;
        WeatherConT=WeatherCon1;
        SiteT=Site1;
        CommentT=Comment1;
        CurrentDateT=CurrentDate;

        for (i=SiteT.length();i<20;i++) SiteT+=" ";
        for (i=City.length();i<50;i++) City+=" ";
        for (i=TempT.length();i<10;i++) TempT+=" ";
        for (i=HumidityT.length();i<10;i++) HumidityT+=" ";
        for (i=WeatherConT.length();i<100;i++) WeatherConT+=" ";
        for (i=CommentT.length();i<100;i++) CommentT+=" ";
        for (i=CurrentDateT.length();i<29;i++) CurrentDateT+=" ";

        Global1.HistoryData[HistoryPos]=new HistoryDataClass();
        Global1.HistoryData[HistoryPos].Site=Site1;
        Global1.HistoryData[HistoryPos].City=City;
        Global1.HistoryData[HistoryPos].Date=CurrentDate;
        Global1.HistoryData[HistoryPos].Temperature=Float.valueOf(Temp1);
        Global1.HistoryData[HistoryPos].Humidity=Float.valueOf(Humidity1);
        Global1.HistoryData[HistoryPos].WeatherCon=WeatherCon1;
        Global1.HistoryData[HistoryPos].Units=Units;
        Global1.HistoryData[HistoryPos].Comment=Comment1;

        UnitsT=Units;

        int strlen;

        String datapath;
        datapath=getApplicationInfo().dataDir+"/Weatherdat.txt";
        File fileSc = new File(datapath);

        try {


            //fileSc.createNewFile();

            FileWriter fileWriter = new FileWriter(fileSc,true); //Append File
            //FileWriter fileWriter = new FileWriter(fileSc); //New File

            fileWriter.write(SiteT, 0, 20);
            fileWriter.write(City, 0, 50);
            //fileWriter.write(CurrentDate, 0, 29); //HERE'S PROBLEM WITH MOBILE, DON'T RUN!!!
            fileWriter.write(CurrentDateT, 0, 29);
            fileWriter.write(TempT,0,10);
            fileWriter.write(HumidityT,0,10);
            fileWriter.write(WeatherConT,0,100);

            fileWriter.write(UnitsT,0,1);
            fileWriter.write(CommentT,0,100);


            fileWriter.flush();

            fileWriter.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        HistoryPos++;

        TempT=PrevTemp;
        HumidityT=PrevHum;
        WeatherConT=PrevCon;

        return;
    }
    private class Content extends AsyncTask<Void,Void,Void>
    {
        int commapos;
        String LatStr,LonStr;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            CoordsRun=false;
            if (City.charAt(0)=='"') CoordsRun=true;

            if (CoordsRun)
            {
                for (i = 0; i <= City.length(); i++)
                    if (City.charAt(i) == ',')
                    {
                        commapos = i;
                        break;
                    }

                LatStr = City.substring(1, commapos);
                LonStr = City.substring(commapos + 1, City.length() - 1);
                Lat = Double.valueOf(LatStr);
                Lon = Double.valueOf(LonStr);
            }
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


        void ReadFromOpen() //PREV
        //public void ReadFromOpen()

        {
            if (Units=="C") UnitsTxt="&units=metric";     //Celsius
            if (Units=="F") UnitsTxt="&units=imperial";   //Fahrenheit
            if (Units=="K") UnitsTxt="";                  //Kelvin

            if (Code.length()<2) CodeTxt=""; else CodeTxt=","+Code;

            if (Period==1)
                try {

                    if (!CoordsRun)
                        CallUrl="http://api.openweathermap.org/data/2.5/weather?q="+City+CodeTxt+UnitsTxt+"&APPID="+APIOpen;

                    if (CoordsRun)
                        CallUrl="http://api.openweathermap.org/data/2.5/weather?lat="+LatStr+"&lon="+LonStr+UnitsTxt+"&APPID="+APIOpen;


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



                        //Save History
                        TempT="Temperature:"+obj3.getString("temp")+" "+Units;
                        HumidityT="Humidity:"+obj3.getString("humidity")+"%";
                        Temp1=obj3.getString("temp");
                        Humidity1=obj3.getString("humidity");
                        WeatherCon1=json2.getString("main");
                        Site1="Open Weather Map";
                        Comment1="None";
                        SaveHistory();


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

                    if (!CoordsRun)
                        CallUrl="http://api.openweathermap.org/data/2.5/forecast?q="+City+CodeTxt+UnitsTxt+"&APPID="+APIOpen;

                    if (CoordsRun)
                        CallUrl="http://api.openweathermap.org/data/2.5/forecast?lat="+LatStr+"&lon="+LonStr+UnitsTxt+"&APPID="+APIOpen;

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

                Global1.Contents=Contents;

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

                if (!CoordsRun)
                    CallUrl="http://dataservice.accuweather.com/locations/v1/cities/search?q="+City+"&apikey="+APIAccu;

                if (CoordsRun)
                    CallUrl="http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?q="+LatStr+","+LonStr+"&apikey="+APIAccu;

                if (CoordsRun) return; //CHECK !!!

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

                        Temp1=obj3.getString("Value");
                        Humidity1=jsonObj.getString("RelativeHumidity");
                        WeatherCon1=jsonObj.getString("WeatherText");
                        Site1="AccuWeather";
                        Comment1="None";
                        SaveHistory();

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

                Global1.Contents=Contents;
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

            if (!CoordsRun) {


                try {
                    CallUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + City + CodeTxt + "&APPID=" + APIOpen;
                    //doc = Jsoup.connect(CallUrl).ignoreContentType(true).get();
                    Contents = "";
                    Contents = Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();

                    try {
                        JSONObject jsonObj = new JSONObject(Contents);
                        JSONObject obj2 = jsonObj.getJSONObject("coord");
                        CallCode = jsonObj.getString("cod"); //404 = City not found
                        Latitude = obj2.getString("lat");
                        Longitude = obj2.getString("lon");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //OutText=Contents;
                    OutText = Latitude + "," + Longitude;
                    //OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                    //      +Coords+"\n"+Temp+"\n"+Humidity;
                    //OutText=CallUrl;

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } //!CoordsRun if


            if (CoordsRun) {Latitude=LatStr;Longitude=LonStr;}
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

                        Temp1=obj2.getString("temperature");
                        Humidity1=String.valueOf(Float.valueOf(obj2.getString("humidity"))*100);
                        WeatherCon1=obj2.getString("summary");
                        Site1="Dark Sky";
                        Comment1="None";
                        SaveHistory();



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


                Global1.Contents=Contents;
                OpenDetailed();
            }


            if (Period==3)
            {

                Contents="Dates\n";

                for (i=0;i<ArraySize;i++)
                {
                    Contents+=DateTxt[i]+"\n"+"Temp:"+TemperatureData[i]+Units+"  Humidity:"+HumidityData[i]+"%"+"\n";
                }


                Global1.Contents=Contents;
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

                    if (!CoordsRun)
                        CallUrl="https://api.weatherbit.io/v2.0/current?city="+City+"&key="+APIBit+UnitsTxt;

                    if (CoordsRun)
                        CallUrl="https://api.weatherbit.io/v2.0/current?lat="+LatStr+"&lon="+LonStr+"&key="+APIBit+UnitsTxt;

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

                        Temp1=json2.getString("temp");
                        Humidity1=json2.getString("rh");
                        WeatherCon1=obj2.getString("description");
                        Site1="Weatherbit.io";
                        Comment1="None";
                        SaveHistory();


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


                    if (!CoordsRun)
                        CallUrl="http://api.weatherstack.com/current?access_key="+APIStack+"&query="+City+UnitsTxt;

                    if (CoordsRun)
                        CallUrl="http://api.weatherstack.com/current?access_key="+APIStack+"&query="+LatStr+","+LonStr+UnitsTxt;


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

                        Temp1=obj3.getString("temperature");
                        Humidity1=obj3.getString("humidity");
                        WeatherCon1=obj3.getString("weather_descriptions");
                        WeatherCon1=WeatherCon1.substring(2,WeatherCon1.length()-2);
                        Site1="Weather Stacks";
                        Comment1="None";
                        SaveHistory();


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


                    //WRONG //CHECK !!!!!!!!
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
