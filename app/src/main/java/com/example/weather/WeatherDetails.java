package com.example.weather;

import com.example.weather.FullscreenActivity.Global1;
import android.annotation.SuppressLint;

import androidx.annotation.RestrictTo;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

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
    private Button mCommit;
    private EditText mEditCommit;

    private int ScrollY,ScrollPos,ScrollId;

    private int i,o;
    private String Contents;

    private String TempT,HumidityT,UnitsT,SiteT,CommentT,CityT;
    private String Temp1,Humidity1,Site1,Comment1;
    private String WeatherCon1,Units1,City1,Date1;
    private String City;
    private String DateT,WeatherConT;

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

    public void SaveHistory2()
    {

        //Get Current Day
        String CurrentDate;
        String Path;
        float TempF,HumF;
        String PrevTemp,PrevHum,PrevCon,PrevSite;




        int strlen;

        String datapath;
        datapath=getApplicationInfo().dataDir+"/Weatherdat.txt";
        File fileSc = new File(datapath);


        try {
            //Global1.ArraySize=(int)(fileSc.length()-1)/320;
            FileWriter fileWriter = new FileWriter(fileSc); //New File
              for (o=0;o<=Global1.ArraySize;o++)
              {


                Site1 = Global1.HistoryData[o].Site;
                City1 = Global1.HistoryData[o].City;
                Date1 = Global1.HistoryData[o].Date;
                Temp1 = String.valueOf(Global1.HistoryData[o].Temperature);
                Humidity1 = String.valueOf(Global1.HistoryData[o].Humidity);
                WeatherCon1 = Global1.HistoryData[o].WeatherCon;
                Units1 = Global1.HistoryData[o].Units;
                Comment1 = Global1.HistoryData[o].Comment;
                TempT="";HumidityT="";WeatherConT="";SiteT="";CommentT="";CityT="";DateT="";

                  TempT=Temp1;
                  HumidityT=Humidity1;
                  WeatherConT=WeatherCon1;
                  SiteT=Site1;
                  CommentT=Comment1;
                  CityT=City1;
                  DateT=Date1; //Check
                  UnitsT=Units1;

                  for (i=SiteT.length();i<20;i++) SiteT+=" ";
                  for (i=CityT.length();i<50;i++) CityT+=" ";
                  for (i=TempT.length();i<10;i++) TempT+=" ";
                  for (i=HumidityT.length();i<10;i++) HumidityT+=" ";
                  for (i=WeatherConT.length();i<100;i++) WeatherConT+=" ";
                  for (i=CommentT.length();i<100;i++) CommentT+=" ";
                  for (i=DateT.length();i<29;i++) DateT+=" ";

                fileWriter.write(SiteT, 0, 20);
                fileWriter.write(CityT, 0, 50);
                fileWriter.write(DateT, 0, 29);
                fileWriter.write(TempT, 0, 10);
                fileWriter.write(HumidityT, 0, 10);
                fileWriter.write(WeatherConT, 0, 100);
                fileWriter.write(UnitsT, 0, 1);
                fileWriter.write(CommentT, 0, 100);

            }
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int Period;
        setContentView(R.layout.activity_weather_details);

        mContentView = findViewById(R.id.fullscreen_content);
        mTitleText=findViewById(R.id.textTitle);
        mDataTxt=findViewById(R.id.textData);
        mCommit=(Button) findViewById(R.id.button2);
        mEditCommit=(EditText) findViewById(R.id.editCommit);

        Intent intent = getIntent();
        Period=intent.getIntExtra("Period",0);
        Contents=intent.getStringExtra("Contents");
         mDataTxt.setMovementMethod(new ScrollingMovementMethod());

        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

       ScrollY=mDataTxt.getScrollY();
       ScrollPos=ScrollY/mDataTxt.getLineHeight();
       ScrollId=ScrollPos/7;

       int hpos;
       int hpos2;

       //PREV
       //Global1.HistoryData[ScrollId].Comment=mEditCommit.getText().toString();

        //Global1.HistoryData[Global1.HistoryData[ScrollId].pos].Comment=mEditCommit.getText().toString();
       //Global1.HistoryData[Global1.HistoryData[ScrollId].pos].Comment=mEditCommit.getText().toString();
        hpos=Global1.SearchData[ScrollId].pos;
        Global1.HistoryData[hpos].Comment=mEditCommit.getText().toString();
        //mEditCommit.setText(String.valueOf(hpos));

       SaveHistory2();
            }
        });

        if (Period==2) {

            mCommit.setVisibility(View.GONE);
            mEditCommit.setVisibility(View.GONE);

            mTitleText.setText("Next 24 hours");
            mDataTxt.setText(Contents);

        }
        if (Period==3)
        {
            mCommit.setVisibility(View.GONE);
            mEditCommit.setVisibility(View.GONE);

            mTitleText.setText("Next 5 days");
            mDataTxt.setText(Contents);

        }

        if (Period==10)
        {

            mCommit.setVisibility(View.VISIBLE);
            mEditCommit.setVisibility(View.VISIBLE);

            mTitleText.setText("History");

            Contents="";

            for (i=0;i<Global1.SearchArraySize;i++)
            {
                Contents+="Site:"+Global1.SearchData[i].Site+"\n"+
                        "City:"+Global1.SearchData[i].City+"\n"
                        +"Date:"+Global1.SearchData[i].Date+"\n"+
                        "Temp:"+Global1.SearchData[i].Temperature+" "+ Global1.SearchData[i].Units+"\n"+
                        "Humidity:"+Global1.SearchData[i].Humidity+"%\n"
                        +"Conditions:"+Global1.SearchData[i].WeatherCon+"\n"
                        +"Comment:"+Global1.SearchData[i].Comment+"\n";

            }

            for (i=1;i<=10;i++) Contents+="\n"; //Help for choosing from top line
            mDataTxt.setText(Contents);
        }

    }


    }
