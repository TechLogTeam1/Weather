package com.example.weather;

import com.example.weather.FullscreenActivity.Global1;

import android.annotation.SuppressLint;

import androidx.annotation.RestrictTo;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.example.weather.FullscreenActivity.Global1.HistoryData;

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
    private String Contents,ContentsTmp;

    private String TempT,HumidityT,UnitsT,SiteT,CommentT,CityT;
    private String Temp1,Humidity1,Site1,Comment1;
    private String WeatherCon1,Units1,City1,Date1;
    private String City;
    private String DateT,WeatherConT;
    private boolean cont;
    private int searchcnt;
    private Date DateExp,DateExp2;
    private String DateExpSTR,DateExp2STR;
    private int posStr;
    private String OutText;
    private int ServicePos;
    private String TimeT;

    private String NameT;
    private String LatStrT;
    private String LonStrT;
    private int CoordsPos;


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

    public String ConvertLongtoDate(long unixSeconds) {


        Date date;

        date=new Date();
        date=new Date(unixSeconds); //Not X 1000
        String strDateFormat = "HH:mm:ss z";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }

    public void SaveHistory2()
    {

        //Get Current Day
        String CurrentDate;
        String Path;
        float TempF,HumF;
        String PrevTemp,PrevHum,PrevCon,PrevSite;
        int s;



        int strlen;

        String datapath;
        datapath=getApplicationInfo().dataDir+"/Weatherdat.txt";
        File fileSc = new File(datapath);
        OutText="";

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


                //NEW //CHECK
                posStr=HistoryData[o].City.length()-1;
                for (s=HistoryData[o].City.length()-1; s>0; s--) if (Global1.HistoryData[o].City.charAt(s)!=' ') {posStr=s+1;break;}
                Global1.HistoryData[o].City=Global1.HistoryData[o].City.substring(0,posStr);

                posStr=HistoryData[o].WeatherCon.length()-1;
                for (s=HistoryData[o].WeatherCon.length()-1; s>0; s--) if (Global1.HistoryData[o].WeatherCon.charAt(s)!=' ') {posStr=s+1;break;}
                Global1.HistoryData[o].WeatherCon=Global1.HistoryData[o].WeatherCon.substring(0,posStr);

                posStr=HistoryData[o].Comment.length()-1;
                for (s=HistoryData[o].Comment.length()-1; s>0; s--) if (Global1.HistoryData[o].Comment.charAt(s)!=' ') {posStr=s+1;break;}
                Global1.HistoryData[o].Comment=Global1.HistoryData[o].Comment.substring(0,posStr);

                OutText+="Site:"+Global1.HistoryData[o].Site+"\n"+
                        "City:"+Global1.HistoryData[o].City+"\n"
                        +"Date:"+Global1.HistoryData[o].Date+"\n"+
                        "Temp:"+Global1.HistoryData[o].Temperature+" "+ Global1.HistoryData[o].Units+"\n"+
                        "Humidity:"+Global1.HistoryData[o].Humidity+"%\n"
                        +"Conditions:"+Global1.HistoryData[o].WeatherCon+"\n"
                        +"Comment:"+Global1.HistoryData[o].Comment+"\n"+
                        "_____________________________\n";



            }
            fileWriter.flush();
            fileWriter.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Contents=OutText;
        return;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int Period;
        setContentView(R.layout.activity_weather_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mContentView = findViewById(R.id.fullscreen_content);
        mTitleText=findViewById(R.id.textTitle);
        mDataTxt=findViewById(R.id.textData);
        mCommit=(Button) findViewById(R.id.button2);
        mEditCommit=(EditText) findViewById(R.id.editCommit);

        Global1.ServiceSelDel=-1;
        Global1.NameSelDel=-1;

        Intent intent = getIntent();
        //Period=intent.getIntExtra("Period",0);
        //Contents=intent.getStringExtra("Contents");
        mDataTxt.setMovementMethod(new ScrollingMovementMethod());

        //NEW
        Period = Global1.Period;
        Contents=Global1.Contents;

        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if (Period!=11) { //PREV
                if ((Period!=11) && (Period!=12)) { //NEW
                //if (Period<10) {

                    ScrollY = mDataTxt.getScrollY();
                    ScrollPos = ScrollY / mDataTxt.getLineHeight();
                    //ScrollId=ScrollPos/7; //PREV
                    ScrollId = ScrollPos / 8; //ADDED _____________________

                    int hpos;
                    int hpos2;

                    hpos = Global1.SearchData[ScrollId].pos;
                    Global1.HistoryData[hpos].Comment = mEditCommit.getText().toString();
                    Global1.SearchData[hpos].Comment = mEditCommit.getText().toString(); //NEW //CHECK

                    SaveHistory2(); //if for all
                    for (i=1;i<=10;i++) Contents+="\n"; //Help for choosing from top line
                    //if (Period==10) recRefresh();

                    mDataTxt.setText(Contents); //NEW
                    mDataTxt.setScrollY(ScrollY); //NEW

                }

                if (Period==12) {

                    ScrollY = mDataTxt.getScrollY();
                    ScrollPos = ScrollY / mDataTxt.getLineHeight();
                    ScrollId=ScrollPos/3;
                    Global1.NameSelDel=ScrollId;
                    DeleteName(); //NEW //CHECK
                    mDataTxt.setScrollY(ScrollY); //NEW
                }


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

        if (Period==10) //Search
        {

            mCommit.setVisibility(View.VISIBLE);
            mEditCommit.setVisibility(View.VISIBLE);

            mTitleText.setText("History");

            Contents="";
            Contents=Global1.Contents; //NEW

            for (i=0;i<Global1.SearchArraySize;i++)
            {
                cont=false;
                searchcnt=0;

                SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss z");
                sdf.setTimeZone(TimeZone.getTimeZone("Europe/Athens")); //NEW
                //String formattedDate = sdf.format(DateExp);
                //DateExpSTR=formattedDate;


                try {

                    DateExp=sdf.parse(Global1.HistoryData[i].Date);
                } catch (ParseException e) {
                    e.printStackTrace();

                }

/*
            if (Global1.FullHistory) cont=true;

            if (!Global1.FullHistory) {
                if (Global1.HistoryData[i].City.toLowerCase().contains(Global1.City.toLowerCase())) searchcnt++;
                if ((DateExp.after(Global1.DateFrom)) && (DateExp.before(Global1.DateTo))) searchcnt++;
            }

            if (searchcnt==2) cont=true;

*/

                posStr=49;
                for (o=49; o>0; o--) if (Global1.SearchData[i].City.charAt(o)!=' ') {posStr=o+1;break;}
                Global1.SearchData[i].City=Global1.SearchData[i].City.substring(0,posStr);

                posStr=99;
                for (o=99; o>0; o--) if (Global1.SearchData[i].WeatherCon.charAt(o)!=' ') {posStr=o+1;break;}
                Global1.SearchData[i].WeatherCon=Global1.SearchData[i].WeatherCon.substring(0,posStr);

                posStr=99;
                for (o=99; o>0; o--) if (Global1.SearchData[i].Comment.charAt(o)!=' ') {posStr=o+1;break;}
                Global1.SearchData[i].Comment=Global1.SearchData[i].Comment.substring(0,posStr);

                //Global1.SearchData[i].City=String.valueOf(posStr);

                //CHECK 1 RUN HERE
                //if (cont)
                Contents+="Site:"+Global1.SearchData[i].Site+"\n"+
                        "City:"+Global1.SearchData[i].City+"\n"
                        +"Date:"+Global1.SearchData[i].Date+"\n"+
                        "Temp:"+Global1.SearchData[i].Temperature+" "+ Global1.SearchData[i].Units+"\n"+
                        "Humidity:"+Global1.SearchData[i].Humidity+"%\n"
                        +"Conditions:"+Global1.SearchData[i].WeatherCon+"\n"
                        +"Comment:"+Global1.SearchData[i].Comment+"\n"+
                        "_____________________________\n";

            }

            for (i=1;i<=10;i++) Contents+="\n"; //Help for choosing from top line
            mDataTxt.setText(Contents);
            mDataTxt.setScrollY(ScrollY); //NEW

        }


        if (Period==11)
        {
            //mCommit.setVisibility(View.GONE);
            mCommit.setText("Delete");
            mEditCommit.setVisibility(View.GONE);

            mTitleText.setText("Services");
            for (i=1;i<=15;i++) Contents+="\n"; //Help for choosing from top line
            mDataTxt.setText(Contents);

        }

        if (Period==12) {

            mCommit.setText("Delete");
            mEditCommit.setVisibility(View.GONE);

            mTitleText.setText("Names Coordinates Records");
            for (i=1;i<=15;i++) Contents+="\n"; //Help for choosing from top line
            mDataTxt.setText(Contents);

        }

    }

    public void recRefresh()
    {
        int searchpos;
        int HistoryPos;

        Date DateComp,DateComp2;
        String DateExpSTR,DateExpSTR2;
        boolean siteCont;


        HistoryPos=Global1.ArraySize;

        Contents="Records\n";



        searchpos = 0;

        for (i = 0; i < HistoryPos; i++) {
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss z");
            sdf.setTimeZone(TimeZone.getTimeZone("Europe/Athens")); //NEW
            //String formattedDate = sdf.format(DateExp);
            //DateExpSTR=formattedDate;


            try {

                DateExp=sdf.parse(Global1.HistoryData[i].Date);
            } catch (ParseException e) {
                e.printStackTrace();

            }

            DateComp=Global1.DateFrom;
            DateComp2=Global1.DateTo;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Global1.DateTo);
            calendar.add(Calendar.DAY_OF_MONTH, 1); //CHECK //PREV

            calendar.set(Calendar.HOUR_OF_DAY,23);
            calendar.set(Calendar.MINUTE,59);
            calendar.set(Calendar.SECOND,59);

            calendar.add(Calendar.HOUR_OF_DAY,-2); //GMC +2 Compbatibility

            DateComp=calendar.getTime();

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(Global1.DateFrom);

            //-------------------------------------------------
            calendar2.add(Calendar.DAY_OF_MONTH, 1); //NEW //CHECK !!!
            //-------------------------------------------------

            calendar2.set(Calendar.HOUR_OF_DAY,0);
            calendar2.set(Calendar.MINUTE,0);
            calendar2.set(Calendar.SECOND,0);

            calendar2.add(Calendar.HOUR_OF_DAY,-2); //GMC +2 Compbatibility
            DateComp2=calendar2.getTime();

            String formattedDate = sdf.format(DateComp);
            DateExpSTR=formattedDate;

            String formattedDate2 = sdf.format(DateComp2);
            DateExpSTR2=formattedDate2;

            //Log.d("Date Message P","HistoryData: "+HistoryData[i].Date);
            //Log.d("Date Message P","DateComp2: "+DateExpSTR2);
            //Log.d("Date Message P","DateComp: "+DateExpSTR);


            /*
            siteCont=false;
            if (CheckOpen) if (HistoryData[i].Site.contains("Open Weather Map")) siteCont=true;
            if (CheckAccu) if (HistoryData[i].Site.contains("AccuWeather")) siteCont=true;
            if (CheckStack) if (HistoryData[i].Site.contains("Weather Stacks")) siteCont=true;
            if (CheckDark) if (HistoryData[i].Site.contains("Dark Sky")) siteCont=true;
            if (CheckBit) if (HistoryData[i].Site.contains("Weatherbit.io")) siteCont=true;

            if (CheckService)
            {
                if (HistoryData[i].Site.contains("(s)")) siteCont = true; else siteCont=false;
            }
            */
            siteCont=true; //TMP
            if (Global1.HistoryData[i].City.toLowerCase().contains(Global1.City.toLowerCase()))
                if ((DateExp.after(DateComp2)) && (DateExp.before(DateComp)))
                    if (siteCont)

                    {
                        //Log.d("Date Message","HistoryData: "+HistoryData[i].Date);
                        //Log.d("Date Message","DateComp2: "+DateExpSTR2);
                        //Log.d("Date Message","DateComp: "+DateExpSTR);
                        Global1.SearchData[searchpos]=new FullscreenActivity.HistoryDataClass();
                        Global1.SearchData[searchpos].pos = i;
                        Global1.SearchData[searchpos].City = Global1.HistoryData[i].City;
                        Global1.SearchData[searchpos].Temperature = Global1.HistoryData[i].Temperature;
                        Global1.SearchData[searchpos].Humidity = Global1.HistoryData[i].Humidity;
                        Global1.SearchData[searchpos].Comment = Global1.HistoryData[i].Comment;
                        Global1.SearchData[searchpos].Site = Global1.HistoryData[i].Site;
                        Global1.SearchData[searchpos].WeatherCon = Global1.HistoryData[i].WeatherCon;
                        Global1.SearchData[searchpos].Date = Global1.HistoryData[i].Date;
                        Global1.SearchData[searchpos].Units = Global1.HistoryData[i].Units;

                        Contents+="Site:"+Global1.SearchData[i].Site+"\n"+
                                "City:"+Global1.SearchData[i].City+"\n"
                                +"Date:"+Global1.SearchData[i].Date+"\n"+
                                "Temp:"+Global1.SearchData[i].Temperature+" "+ Global1.SearchData[i].Units+"\n"+
                                "Humidity:"+Global1.SearchData[i].Humidity+"%\n"
                                +"Conditions:"+Global1.SearchData[i].WeatherCon+"\n"
                                +"Comment:"+Global1.SearchData[i].Comment+"\n"+
                                "_____________________________\n";
                        searchpos++;
                    }
        }


    }

    public void SaveNamesAll()
    {



        String datapath;
        datapath=getApplicationInfo().dataDir+"/Coords.txt";
        File fileSc = new File(datapath);
        long filelen;
        OutText="";
        filelen=fileSc.length();



        int strlen;



        try {

            //fileSc.createNewFile();
            //FileWriter fileWriter = new FileWriter(fileSc,true); //Append File
            FileWriter fileWriter = new FileWriter(fileSc); //New File //This Needed Here


            for (o=0;o<=(filelen-1)/80;o++)
                if (o!=Global1.NameSelDel)
                {

                    NameT = Global1.CoordsNamesData[o].Name;
                    LatStrT = String.valueOf(Global1.CoordsNamesData[o].lat);
                    LonStrT = String.valueOf(Global1.CoordsNamesData[o].lon);


                    for (i=NameT.length();i<50;i++) NameT+=" ";
                    for (i=LatStrT.length();i<15;i++) LatStrT+=" ";
                    for (i=LonStrT.length();i<15;i++) LonStrT+=" ";


                    fileWriter.write(NameT, 0, 50);
                    fileWriter.write(LatStrT, 0, 15);
                    fileWriter.write(LonStrT, 0, 15);

                    fileWriter.flush();


                    OutText += "Name:" + FullscreenActivity.Global1.CoordsNamesData[o].Name + "\n" +
                            "Latitude:" + FullscreenActivity.Global1.CoordsNamesData[o].lat + "\n" +
                            "Longitute:" + FullscreenActivity.Global1.CoordsNamesData[o].lon + "\n" +
                            "_____________________________\n";


                }

            fileWriter.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Contents=OutText;
        for (i=1;i<=10;i++) Contents+="\n"; //Help for choosing from top line
        mDataTxt.setText(Contents);

        return;

    }

    public void DeleteName()
    {

        String datapath, strdata;
        char[] scArray = new char[29];
        datapath = getApplicationInfo().dataDir + "/Coords.txt";
        int o;
        int stopPos;

        String TimeT;

        CoordsPos = 0;


        try {
            File fileSc = new File(datapath);
            FileReader fileReader = new FileReader(fileSc);
            StringBuffer stringBuffer = new StringBuffer();
            int numCharsRead = 0; //NEW
            char[] charArrayName = new char[50]; //PREV
            char[] charArray1 = new char[15];
            OutText = "";

            for (i = 0; i <= (fileSc.length() - 1) / 80; i++)
                if (i != Global1.NameSelDel) {
                    NameT = "";
                    LatStrT="";
                    LonStrT="";

                    fileReader.read(charArrayName);
                    stringBuffer.append(charArrayName, 0, 50);
                    NameT = stringBuffer.toString();
                    stringBuffer.delete(0, 50);

                    fileReader.read(charArray1);
                    stringBuffer.append(charArray1, 0, 15);
                    LatStrT = stringBuffer.toString();
                    stringBuffer.delete(0, 15);

                    fileReader.read(charArray1);
                    stringBuffer.append(charArray1, 0, 15);
                    LonStrT= stringBuffer.toString();
                    stringBuffer.delete(0, 15);

                    FullscreenActivity.Global1.CoordsNamesData[CoordsPos]=new FullscreenActivity.CoordsNames();
                    FullscreenActivity.Global1.CoordsNamesData[CoordsPos].Name=NameT;
                    FullscreenActivity.Global1.CoordsNamesData[CoordsPos].lat=Double.valueOf(LatStrT);
                    FullscreenActivity.Global1.CoordsNamesData[CoordsPos].lon=Double.valueOf(LonStrT);

                    posStr=49;
                    for (o=49; o>0; o--) if (NameT.charAt(o)!=' ') {posStr=o+1;break;}
                    NameT=NameT.substring(0,posStr);
                    FullscreenActivity.Global1.CoordsNamesData[CoordsPos].Name=NameT;



                    //-----------------
                    //PREV //CHECK
                    CoordsPos++;
                    //-----------------

                    FullscreenActivity.Global1.CoordsRecs=CoordsPos;

                    OutText+="Name:"+ FullscreenActivity.Global1.CoordsNamesData[CoordsPos-1].Name+"\n"+
                            "Latitude:"+FullscreenActivity.Global1.CoordsNamesData[CoordsPos-1].lat+"\n"+
                            "Longitute:"+FullscreenActivity.Global1.CoordsNamesData[CoordsPos-1].lon+"\n"+
                            "_____________________________\n";


                }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //if (CoordsPos>0) CoordsPos--; //MAYBE NOT NEEDED HERE
        SaveNamesAll(); //PREV

        return;
    }


}
