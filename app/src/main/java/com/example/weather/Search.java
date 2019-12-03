package com.example.weather;

import com.example.weather.FullscreenActivity.Global1;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.example.weather.FullscreenActivity.Global1.ArraySize;
import static com.example.weather.FullscreenActivity.Global1.HistoryData;
import static com.example.weather.FullscreenActivity.Global1.Period;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Search extends AppCompatActivity {
    private View mContentView;
    private View mControlsView;

    private Button mButtonSel;
    private Button mButtonDis;
    private Button mButtonClear;
    private Button mButtonDaily;
    private Button mButtonWeekly;
    private Button mButtonMonthly;
    private Button mButtonSearch;

    private CheckBox mCheckOpen;
    private CheckBox mCheckAccu;
    private CheckBox mCheckStack;
    private CheckBox mCheckDark;
    private CheckBox mCheckBit;
    private CheckBox mCheckService;
    private EditText mCity;
    private EditText mFrom;
    private EditText mTo;

    private String City;
    private String DateFrom;
    private String DateTo;
    private Date DateFrom1;
    private Date DateTo1;


    private String TempT,HumidityT,UnitsT,SiteT,CommentT;
    private String WeatherConT;
    private int HistoryPos;
    private String Contents,OutText;
    private int i,searchpos,PrevPeriod;
    private boolean SearchCityOn,ContinueRec;
    private String Date;

    private Date DateExp,DateExp2;
    private Date DateComp,DateComp2;
    private String DateExpSTR,DateExpSTR2;

    private boolean CheckOpen=true;
    private boolean CheckAccu=true;
    private boolean  CheckStack=true;
    private boolean  CheckDark=true;
    private boolean  CheckBit=true;
    private boolean  CheckService=false;
    private boolean siteCont;

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
            ArraySize=ArraySize;
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

                Global1.HistoryData[HistoryPos]=new FullscreenActivity.HistoryDataClass();
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

                //ContinueRec=false;

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



            searchpos = 0;
            for (i = 0; i < HistoryPos; i++) {
                SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
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
                //calendar.setTime(DateExp);
                calendar.setTime(Global1.DateTo);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY,23);
                calendar.set(Calendar.MINUTE,59);
                calendar.set(Calendar.SECOND,59);
                calendar.add(Calendar.HOUR_OF_DAY,-2); //GMC +2 Compbatibility

                DateComp=calendar.getTime();
                //formattedDate= dateFormat.format(date2);


                Calendar calendar2 = Calendar.getInstance();
                //calendar2.setTime(DateExp);
                calendar2.setTime(Global1.DateFrom);
                //calendar2.add(Calendar.DAY_OF_MONTH, -1); //PREV
                calendar2.add(Calendar.DAY_OF_MONTH, 1); //NEW
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


                siteCont=false;
                if (CheckOpen) if (HistoryData[i].Site.contains("Open Weather Map")) siteCont=true;
                if (CheckAccu) if (HistoryData[i].Site.contains("AccuWeather")) siteCont=true;
                if (CheckStack) if (HistoryData[i].Site.contains("Weather Stacks")) siteCont=true;
                if (CheckDark) if (HistoryData[i].Site.contains("Dark Sky")) siteCont=true;
                if (CheckBit) if (HistoryData[i].Site.contains("Weatherbit.io")) siteCont=true;

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

                    searchpos++;
                }
            }


        Period=Period;
        Global1.SearchArraySize=searchpos;
        Global1.Contents=OutText;

        //PREV RUNNED
        //OpenDetailed();

        Period=PrevPeriod;

        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        mCity= (EditText) findViewById(R.id.editCity);
        mFrom= (EditText) findViewById(R.id.editDateFrom);
        mTo= (EditText) findViewById(R.id.editDateTo);

        //Get Current Date
        Date date1 = new Date();
        String strDateFormat = "dd-MM-yyyy"; //NEW
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
        String formattedDate= dateFormat.format(date1);
        DateFrom=formattedDate;
        DateTo=formattedDate;
        mFrom.setText(DateFrom);
        mTo.setText(DateTo);

        DateFrom1=date1;
        DateTo1=date1;

        mButtonSel= (Button) findViewById(R.id.buttonSel);
        mButtonDis= (Button) findViewById(R.id.buttonDis);
        mButtonClear= (Button) findViewById(R.id.buttonClear);
        mButtonDaily= (Button) findViewById(R.id.buttonDaily);
        mButtonWeekly= (Button) findViewById(R.id.buttonWeekly);
        mButtonMonthly= (Button) findViewById(R.id.buttonMonthly);
        mButtonSearch= (Button) findViewById(R.id.buttonSearch);

        mCheckOpen=(CheckBox)findViewById(R.id.checkBox);
        mCheckAccu=(CheckBox)findViewById(R.id.checkBox2);
        mCheckStack=(CheckBox)findViewById(R.id.checkBox3);
        mCheckDark=(CheckBox)findViewById(R.id.checkBox4);
        mCheckBit=(CheckBox)findViewById(R.id.checkBox5);
        mCheckService=(CheckBox)findViewById(R.id.checkBox6);

        if (!Global1.City.isEmpty()) mCity.setText(Global1.City);
        mButtonSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckOpen.setChecked(true);
                mCheckAccu.setChecked(true);
                mCheckStack.setChecked(true);
                mCheckDark.setChecked(true);
                mCheckBit.setChecked(true);
                mCheckService.setChecked(true);
            }
        });



        mButtonDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckOpen.setChecked(false);
                mCheckAccu.setChecked(false);
                mCheckStack.setChecked(false);
                mCheckDark.setChecked(false);
                mCheckBit.setChecked(false);
                mCheckService.setChecked(false);
            }
        });

        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCity.setText("");
                mFrom.setText("");
                mTo.setText("");
            }
        });

        mButtonDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = new Date();
                //String strDateFormat = "yyyy-MM-dd HH:mm:ss z"; //PREV
                String strDateFormat = "dd-MM-yyyy"; //NEW
                DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
                String formattedDate= dateFormat.format(date);
                DateFrom=formattedDate;
                DateTo=formattedDate;

                mFrom.setText(DateFrom);
                mTo.setText(DateFrom);

                DateFrom1=date;
                DateTo1=date;
            }
        });

        mButtonWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = new Date();
                Date date2 = new Date();


                //DateFrom
                String strDateFormat = "dd-MM-yyyy";
                DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
                try {
                    date=dateFormat.parse(mFrom.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String formattedDate= dateFormat.format(date);
                DateFrom=formattedDate;

                //DateTo
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.WEEK_OF_YEAR, 1);

                date2=calendar.getTime();
                formattedDate= dateFormat.format(date2);

                DateTo=formattedDate;
                mFrom.setText(DateFrom);
                mTo.setText(DateTo);

                Global1.DateFrom=date;
                Global1.DateTo=date2;
            }
        });

        mButtonDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = new Date();
                //String strDateFormat = "yyyy-MM-dd HH:mm:ss z"; //PREV
                String strDateFormat = "dd-MM-yyyy"; //NEW
                DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
                String formattedDate= dateFormat.format(date);
                DateFrom=formattedDate;
                DateTo=formattedDate;

                mFrom.setText(DateFrom);
                mTo.setText(DateFrom);

                Global1.DateFrom=date;
                Global1.DateTo=date;
            }
        });

        mButtonMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = new Date();
                Date date2 = new Date();

                String formattedDate = null;
                Calendar calendar;
                Calendar calendar2;
                DateFormat dateFormat;
                String FromDate;
                int year,month,day;


                //DateFrom
                String strDateFormat = "dd-MM-yyyy";
                dateFormat = new SimpleDateFormat(strDateFormat);
                dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));

                FromDate=mFrom.getText().toString();


                try {

                    date=dateFormat.parse(FromDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //String formattedDate= dateFormat.format(date);
                //formattedDate= dateFormat.format(date);

                calendar2 = Calendar.getInstance();
                calendar2.setTime(date);
                calendar2.set(Calendar.DAY_OF_MONTH,1);
                date=calendar2.getTime();

                dateFormat.format(date); //CHECK
                formattedDate= dateFormat.format(date);
                DateFrom=formattedDate;
                //DateFrom=date.toString(); //TMP //RUNS

                //DateTo
                calendar = Calendar.getInstance();
                calendar.setTime(date);
                //calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                //calendar.set(Calendar.DAY_OF_MONTH,5);

                date2=calendar.getTime();
                formattedDate= dateFormat.format(date2);

                DateTo=formattedDate;

                Global1.DateFrom=date;
                Global1.DateTo=date2;

                mFrom.setText(DateFrom);
                mTo.setText(DateTo);

            }
        });


        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = new Date();
                Date date2 = new Date();


                Period=10;

                Global1.City=mCity.getText().toString(); //CHECK


                //DateFrom
                String strDateFormat = "dd-MM-yyyy";
                DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
                try {
                    date=dateFormat.parse(mFrom.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //String formattedDate= dateFormat.format(date);
                DateFrom1=date;

                //DateTo
                dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
                try {
                    date2=dateFormat.parse(mTo.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //String formattedDate= dateFormat.format(date);
                DateTo1=date2;


                Global1.DateFrom=DateFrom1;
                Global1.DateTo=DateTo1;


                if (mCheckOpen.isChecked()) CheckOpen=true; else CheckOpen=false;
                if (mCheckAccu.isChecked()) CheckAccu=true; else CheckAccu=false;
                if (mCheckStack.isChecked()) CheckStack=true; else CheckStack=false;
                if (mCheckDark.isChecked()) CheckDark=true; else CheckDark=false;
                if (mCheckBit.isChecked()) CheckBit=true; else CheckBit=false;
                if (mCheckService.isChecked()) CheckService=true; else CheckService=false;


                ReadHistory();
                Intent intent = new Intent(Search.this, WeatherDetails.class);
                startActivity(intent);

            }
        });



    }

    }

