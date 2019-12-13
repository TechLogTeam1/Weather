package com.example.weather;


import android.annotation.SuppressLint;
import com.example.weather.FullscreenActivity.Global1;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Services extends AppCompatActivity {
    private View mControlsView;

    private RadioButton mRadio1;
    private RadioButton mRadio2;
    private RadioButton mRadio3;
    private RadioButton mRadio4;
    private EditText mCity;
    private EditText mTime;

    private Button mSetService;
    private Button mShowSer;

    private CheckBox mCheckOpen;
    private CheckBox mCheckAccu;
    private CheckBox mCheckStack;
    private CheckBox mCheckDark;
    private CheckBox mCheckBit;

    private boolean CheckOpen=true;
    private boolean CheckAccu=true;
    private boolean  CheckStack=true;
    private boolean  CheckDark=true;
    private boolean  CheckBit=true;

    private int ServiceTimes;
    private String City;
    private int i;
    private String OutText;
    private int ServicePos;
    private int posStr;
    private String TimeSTR;
    private Date DateCustom;
    private String CurrentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_services);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ReadService(); //PREV

        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mRadio1= (RadioButton) findViewById(R.id.radioButtonOne);
        mRadio2= (RadioButton) findViewById(R.id.radioButtonTwo);
        mRadio3= (RadioButton) findViewById(R.id.radioButtonThree);
        mRadio4= (RadioButton) findViewById(R.id.radioButtonFour);
        mSetService=(Button) findViewById(R.id.buttonSetSer);
        mShowSer=(Button) findViewById(R.id.buttonShowSer);
        mCity=(EditText) findViewById(R.id.editText2);
        mTime=(EditText) findViewById(R.id.editCustom);

        mCheckOpen= (CheckBox) findViewById(R.id.checkBoxOpen);
        mCheckAccu= (CheckBox) findViewById(R.id.checkBoxAccu);
        mCheckStack= (CheckBox) findViewById(R.id.checkBoxStack);
        mCheckDark= (CheckBox) findViewById(R.id.checkBoxDark);
        mCheckBit= (CheckBox) findViewById(R.id.checkBoxBit);

        ServiceTimes=3; //Default

        mRadio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio1.setChecked(true);
                mRadio2.setChecked(false);
                mRadio3.setChecked(false);
                mRadio4.setChecked(false);
                ServiceTimes=1;
            }
        });

        mRadio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio1.setChecked(false);
                mRadio2.setChecked(true);
                mRadio3.setChecked(false);
                mRadio4.setChecked(false);
                ServiceTimes=2;
            }
        });

        mRadio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio1.setChecked(false);
                mRadio2.setChecked(false);
                mRadio3.setChecked(true);
                mRadio4.setChecked(false);
                ServiceTimes=3;
            }
        });

        mRadio4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio1.setChecked(false);
                mRadio2.setChecked(false);
                mRadio3.setChecked(false);
                mRadio4.setChecked(true);
                ServiceTimes=10;
            }
        });

        mSetService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                City=mCity.getText().toString();
                TimeSTR=mTime.getText().toString();

                if (mCheckOpen.isChecked()) CheckOpen=true; else CheckOpen=false;
                if (mCheckAccu.isChecked()) CheckAccu=true; else CheckAccu=false;
                if (mCheckStack.isChecked()) CheckStack=true; else CheckStack=false;
                if (mCheckDark.isChecked()) CheckDark=true; else CheckDark=false;
                if (mCheckBit.isChecked()) CheckBit=true; else CheckBit=false;

                SetAlarmService();

            }
        });



        mShowSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                ReadService();
                Global1.Period=11;
                Global1.Contents=OutText;
                Intent intent = new Intent(Services.this, WeatherDetails.class);
                startActivity(intent);

                //Log.d("Service Message",String.valueOf(FullscreenActivity.Global1.ServiceSelDel));
                //if (FullscreenActivity.Global1.ServiceSelDel!=-1) DeleteService();
            }

        });



    }

    public String ConvertLongtoDate(long unixSeconds) {


        Date date;
        int hours;

        /*
        date=new Date();
        date=new Date(unixSeconds); //Not X 1000
        hours=date.getHours();
        hours-=2; //NEW GTM+2:00 Athens
        if (hours==-1) hours=23;
        if (hours==-2) hours=22;

*/


        Calendar calendar;

        //hours+=2; //GMT +2 (Athens)
        //if (hours==25) hours=1;
        //if (hours==26) hours=2;

        //NEW //CHECK
        //TimeZone.setDefault(TimeZone.getTimeZone("GMT"));

        //NEW HERE
        calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Athens")); //NEW
        calendar.setTimeInMillis(unixSeconds);
        //calendar.add(Calendar.HOUR_OF_DAY,-2); //GMT +2 Compbatibility
        date=calendar.getTime();

        String strDateFormat = "HH:mm:ss z";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        //dateFormat.setTimeZone(TimeZone.getTimeZone("GMT")); //NEW
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }

    //MAYBE NOT NEEDED HERE
    public void DisableAlarm(long time)

    {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, AlarmService.class);
        //PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT); //PREV
        PendingIntent pi = PendingIntent.getBroadcast(this, Global1.ServiceSelDel, i, PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent pi = PendingIntent.getBroadcast(this, Global1.ServiceSelDel, i, PendingIntent.FLAG_CANCEL_CURRENT); //NEW
        //PendingIntent pi = PendingIntent.getBroadcast(this, Global1.ServiceSelDel, i, 0);
        Log.d("AlarmService","Service Num Off:"+String.valueOf(ServicePos));
        //am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi); //PREV
        am.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pi);
        am.cancel(pi);

    }

    private void setAlarm(long time) {

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent i = new Intent(this, AlarmService.class); //PREV
        i.setAction(String.valueOf(ServicePos));
        i.putExtra("City",City);
        i.putExtra("ServicePos",ServicePos);
        i.putExtra("Site",Global1.ServiceData[ServicePos].siteId);

        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0); //PREV
        //PendingIntent pi = PendingIntent.getBroadcast(this, ServicePos, i, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("AlarmService","Service Num Set:"+String.valueOf(ServicePos));
        Log.d("AlarmService","Service Time:"+ConvertLongtoDate(time));

        //am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi); //PREV
        am.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pi); //NEW
        //am.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.ELAPSED_REALTIME, pi); //NEW



        //DisableAlarm(time);
    }

    public boolean HasSame(int days,Calendar calendar)

    {

        boolean cont;
        int contcnt;

        if (days==1)
        {


            for (i = 0; i <= ServicePos-1; i++) {
                cont=true;
                contcnt=0;

                if (CheckOpen) if (Global1.ServiceData[i].site.contains("OpenWeather")) cont=false;
                if (CheckAccu) if (Global1.ServiceData[i].site.contains("AccuWeather")) cont=false;
                if (CheckStack) if (Global1.ServiceData[i].site.contains("WeatherStack")) cont=false;
                if (CheckDark) if (Global1.ServiceData[i].site.contains("DarkSky")) cont=false;
                if (CheckBit)if (Global1.ServiceData[i].site.contains("Weatherbit")) cont=false;

                if (Global1.ServiceData[i].City.toLowerCase().contains(City.toLowerCase()))
                    if (ConvertLongtoDate(Global1.ServiceData[i].time).contains(ConvertLongtoDate(calendar.getTimeInMillis()))) //CHECK
                        if (!cont) return true;


            }
        }
        return false;
    }

    public void NewRec(int days,Calendar calendar)

    {


        if (mCheckOpen.isChecked()) CheckOpen=true; else CheckOpen=false;
        if (mCheckAccu.isChecked()) CheckAccu=true; else CheckAccu=false;
        if (mCheckStack.isChecked()) CheckStack=true; else CheckStack=false;
        if (mCheckDark.isChecked()) CheckDark=true; else CheckDark=false;
        if (mCheckBit.isChecked()) CheckBit=true; else CheckBit=false;

        //-----------------------
        //ServicePos++; //CHECK
        //-----------------------

        //City=mCity.getText().toString(); //NEW

        if (CheckOpen) {
            Global1.ServiceData[ServicePos]=new FullscreenActivity.ServicesDataClass();
            Global1.ServiceData[ServicePos].daysnum = days;
            Global1.ServiceData[ServicePos].site="OpenWeather";
            Global1.ServiceData[ServicePos].siteId=1;
            Global1.ServiceData[ServicePos].City=City;
            Global1.ServiceData[ServicePos].hasdone=false;
            Global1.ServiceData[ServicePos].time=calendar.getTimeInMillis();
            setAlarm(calendar.getTimeInMillis());
            SaveService();
        }

        if (CheckAccu) {
            Global1.ServiceData[ServicePos]=new FullscreenActivity.ServicesDataClass();
            Global1.ServiceData[ServicePos].daysnum = days;
            Global1.ServiceData[ServicePos].site="AccuWeather";
            Global1.ServiceData[ServicePos].siteId=2;
            Global1.ServiceData[ServicePos].City=City;
            Global1.ServiceData[ServicePos].hasdone=false;
            Global1.ServiceData[ServicePos].time=calendar.getTimeInMillis();
            setAlarm(calendar.getTimeInMillis());
            SaveService();
        }

        if (CheckStack) {
            Global1.ServiceData[ServicePos]=new FullscreenActivity.ServicesDataClass();
            Global1.ServiceData[ServicePos].daysnum = days;
            Global1.ServiceData[ServicePos].site="WeatherStack";
            Global1.ServiceData[ServicePos].siteId=3;
            Global1.ServiceData[ServicePos].City=City;
            Global1.ServiceData[ServicePos].hasdone=false;
            Global1.ServiceData[ServicePos].time=calendar.getTimeInMillis();
            setAlarm(calendar.getTimeInMillis());
            SaveService();
        }

        if (CheckDark) {
            Global1.ServiceData[ServicePos]=new FullscreenActivity.ServicesDataClass();
            Global1.ServiceData[ServicePos].daysnum = days;
            Global1.ServiceData[ServicePos].site="DarkSky";
            Global1.ServiceData[ServicePos].siteId=4;
            Global1.ServiceData[ServicePos].City=City;
            Global1.ServiceData[ServicePos].hasdone=false;
            Global1.ServiceData[ServicePos].time=calendar.getTimeInMillis();
            setAlarm(calendar.getTimeInMillis());
            SaveService();
        }

        if (CheckBit) {
            Global1.ServiceData[ServicePos]=new FullscreenActivity.ServicesDataClass();
            Global1.ServiceData[ServicePos].daysnum = days;
            Global1.ServiceData[ServicePos].site="Weatherbit";
            Global1.ServiceData[ServicePos].siteId=5;
            Global1.ServiceData[ServicePos].City=City;
            Global1.ServiceData[ServicePos].hasdone=false;
            Global1.ServiceData[ServicePos].time=calendar.getTimeInMillis();
            setAlarm(calendar.getTimeInMillis());
            SaveService();
        }


    }

    public void SetAlarmService()

    {
        boolean sameone;
        int hours,minutes,day,year,month;
        String TimeGMT;
        String DateCustomS;

        //Maybe Not Needed Here
        //FullscreenActivity.Global1.progressDialog=new ProgressDialog(FullscreenActivity.this);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        //Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+2:00")); //NEW
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Athens")); //NEW
        Global1.datapath=getApplicationInfo().dataDir+"/Weatherdat.txt";

        sameone=false;
        if (ServiceTimes==1) {

            Date date = new Date();
            String strDateFormat = "dd-MM-yyyy HH:mm:ss z";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
            String formattedDate= dateFormat.format(date);
            CurrentDate=formattedDate;

            year=date.getYear();
            month=date.getMonth();
            day=date.getDay();

            //Afternoon
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.HOUR_OF_DAY, 14);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);


            if (!HasSame(1,calendar))
            {
                NewRec(1, calendar);
                Toast.makeText(this, "Service is set", Toast.LENGTH_SHORT).show();
            }

            else Toast.makeText(this, "Service is exists", Toast.LENGTH_SHORT).show();

        }


        //Custom
        if (ServiceTimes==10) {

            Date date = new Date();
            String strDateFormat = "dd-MM-yyyy HH:mm:ss z";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
            String formattedDate= dateFormat.format(date);
            CurrentDate=formattedDate;

            year=date.getYear();
            month=date.getMonth();
            day=date.getDay();

            DateCustomS=mTime.getText().toString();
            hours=Integer.valueOf(DateCustomS.substring(0,2));
            minutes=Integer.valueOf(DateCustomS.substring(3,5));
            //Log.d("AlarmService","Hours:"+String.valueOf(hours));
            //Log.d("AlarmService","Minutes:"+String.valueOf(minutes));
            //calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            //calendar.setTimeZone(TimeZone.getTimeZone("Europe/Athens")); //NEW

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, 0);

            if (!HasSame(1,calendar))
            {
                NewRec(1, calendar);
                Toast.makeText(this, "Service is set", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(this, "Service is exists", Toast.LENGTH_SHORT).show();
        }

        if (ServiceTimes==2)
        {

            Date date = new Date();
            String strDateFormat = "dd-MM-yyyy HH:mm:ss z";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
            String formattedDate= dateFormat.format(date);
            CurrentDate=formattedDate;

            year=date.getYear();
            month=date.getMonth();
            day=date.getDay();

            //Moorning
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            if (!HasSame(1,calendar)) NewRec(1,calendar); else sameone=true;

            date = new Date();
            strDateFormat = "dd-MM-yyyy HH:mm:ss z";
            dateFormat = new SimpleDateFormat(strDateFormat);
            dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
            formattedDate= dateFormat.format(date);
            CurrentDate=formattedDate;

            year=date.getYear();
            month=date.getMonth();
            day=date.getDay();

            //Night
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.HOUR_OF_DAY,22 );
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            if (!HasSame(1,calendar)) NewRec(1,calendar); else sameone=true;

            if (!sameone) Toast.makeText(this, "Service is set", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "One or more dates exists", Toast.LENGTH_SHORT).show();

        }

        if (ServiceTimes==3) {

            Date date = new Date();
            String strDateFormat = "dd-MM-yyyy HH:mm:ss z";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
            String formattedDate= dateFormat.format(date);
            CurrentDate=formattedDate;

            year=date.getYear();
            month=date.getMonth();
            day=date.getDay();

            //Moorning
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.HOUR_OF_DAY,8 );
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            if (!HasSame(1,calendar)) NewRec(1,calendar); else sameone=true;

            date = new Date();
            strDateFormat = "dd-MM-yyyy HH:mm:ss z";
            dateFormat = new SimpleDateFormat(strDateFormat);
            dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
            formattedDate= dateFormat.format(date);
            CurrentDate=formattedDate;

            year=date.getYear();
            month=date.getMonth();
            day=date.getDay();

            //Afternoon
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.HOUR_OF_DAY,14 );
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            if (!HasSame(1,calendar)) NewRec(1,calendar); else sameone=true;

            date = new Date();
            strDateFormat = "dd-MM-yyyy HH:mm:ss z";
            dateFormat = new SimpleDateFormat(strDateFormat);
            dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
            formattedDate= dateFormat.format(date);
            CurrentDate=formattedDate;

            year=date.getYear();
            month=date.getMonth();
            day=date.getDay();

            //Night
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.HOUR_OF_DAY,22 );
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            if (!HasSame(1,calendar)) NewRec(1,calendar); else sameone=true;

            if (!sameone) Toast.makeText(this, "Service is set", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "One or more dates exists", Toast.LENGTH_SHORT).show();
        }
    }



    public void ReadService()

    {
        String datapath,strdata;
        char[] scArray = new char[29];
        datapath=getApplicationInfo().dataDir+"/Servicedat.txt";
        int o;
        int stopPos;

        String SiteT;
        String CityT;
        String DaysT;
        String SideIdT;
        String TimeT;

        ServicePos=0;


        try {
            File fileSc = new File(datapath);
            //fileSc.delete(); //TMP
            //fileSc.createNewFile();//TMP
            FileReader fileReader = new FileReader(fileSc);
            StringBuffer stringBuffer = new StringBuffer();
            int numCharsRead = 0; //NEW
            char[] charArrayCity = new char[50]; //PREV
            char[] charArray1=new char[20];
            char[] charArray2=new char[1];
            OutText="";


            //ArraySize=(int)(fileSc.length()-1)/320;
            //FullscreenActivity.Global1.ArraySize=ArraySize;
            if (fileSc.length()!=0) //NEW NEEDED HERE
                for (i=0;i<=(fileSc.length()-1)/92;i++)
                {
                    SiteT="";CityT="";DaysT="";SideIdT="";TimeT="";


                    fileReader.read(charArray1);
                    stringBuffer.append(charArray1, 0, 20);
                    SiteT = stringBuffer.toString();
                    stringBuffer.delete(0, 20);

                    fileReader.read(charArrayCity);
                    stringBuffer.append(charArrayCity, 0, 50);
                    CityT = stringBuffer.toString();
                    stringBuffer.delete(0, 50);

                    fileReader.read(charArray2);
                    stringBuffer.append(charArray2, 0, 1);
                    DaysT = stringBuffer.toString();
                    stringBuffer.delete(0, 1);

                    fileReader.read(charArray2);
                    stringBuffer.append(charArray2, 0, 1);
                    SideIdT = stringBuffer.toString();
                    stringBuffer.delete(0, 1);

                    fileReader.read(charArray1);
                    stringBuffer.append(charArray1, 0, 20);
                    TimeT = stringBuffer.toString();
                    stringBuffer.delete(0, 20);

                    Global1.ServiceData[ServicePos]=new FullscreenActivity.ServicesDataClass();
                    Global1.ServiceData[ServicePos].site=SiteT;
                    Global1.ServiceData[ServicePos].City=CityT;
                    Global1.ServiceData[ServicePos].daysnum=Integer.valueOf(DaysT);
                    Global1.ServiceData[ServicePos].siteId=Integer.valueOf(SideIdT);
                    Global1.ServiceData[ServicePos].hasdone=false; //NEW

                    posStr=19;
                    for (o=19; o>0; o--) if (TimeT.charAt(o)!=' ') {posStr=o+1;break;}
                    TimeT=TimeT.substring(0,posStr);
                    Global1.ServiceData[ServicePos].time=Long.valueOf(TimeT);

                    //NEW
                    posStr=49;
                    for (o=49; o>0; o--) if (CityT.charAt(o)!=' ') {posStr=o+1;break;}
                    CityT=CityT.substring(0,posStr);
                    Global1.ServiceData[ServicePos].City=CityT;


                    //-----------------
                    //PREV //CHECK
                    ServicePos++;
                    //-----------------

                    //NEW ///CCHECK
                    Global1.ServiceRecs=ServicePos;

                    //if (2<1) //PREV
                    OutText+="Site:"+ Global1.ServiceData[ServicePos-1].site+"\n"+
                            "City:"+ Global1.ServiceData[ServicePos-1].City+"\n"+
                            "Time:"+ ConvertLongtoDate(Global1.ServiceData[ServicePos-1].time)+"\n"+
                            "_____________________________\n";

                    if (2<1) //NEW
                        OutText+="Site:"+ Global1.ServiceData[ServicePos].site+"\n"+
                                "City:"+ Global1.ServiceData[ServicePos].City+"\n"+
                                "Time:"+ ConvertLongtoDate(Global1.ServiceData[ServicePos].time)+"\n"+
                                "_____________________________\n";

                }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return;
    }



    //NOT USED HERE, CHECK IN WeatherDetails.java
    public void DeleteService() {
        String datapath, strdata;
        char[] scArray = new char[29];
        datapath = getApplicationInfo().dataDir + "/Servicedat.txt";
        int o;
        int stopPos;

        String SiteT;
        String CityT;
        String DaysT;
        String SiteIdT;

        ServicePos = 0;


        try {
            File fileSc = new File(datapath);
            FileReader fileReader = new FileReader(fileSc);
            StringBuffer stringBuffer = new StringBuffer();
            int numCharsRead = 0; //NEW
            char[] charArrayCity = new char[50]; //PREV
            char[] charArray1 = new char[20];
            char[] charArray2 = new char[1];
            OutText = "";


            //ArraySize=(int)(fileSc.length()-1)/320;
            //FullscreenActivity.Global1.ArraySize=ArraySize;
            for (i = 0; i <= (fileSc.length() - 1) / 92; i++)
                if (i != Global1.ServiceSelDel) {
                    SiteT = "";
                    CityT = "";
                    DaysT = "";


                    fileReader.read(charArray1);
                    stringBuffer.append(charArray1, 0, 20);
                    SiteT = stringBuffer.toString();
                    stringBuffer.delete(0, 20);

                    fileReader.read(charArrayCity);
                    stringBuffer.append(charArrayCity, 0, 50);
                    CityT = stringBuffer.toString();
                    stringBuffer.delete(0, 50);

                    fileReader.read(charArray2);
                    stringBuffer.append(charArray2, 0, 1);
                    DaysT = stringBuffer.toString();
                    stringBuffer.delete(0, 1);

                    fileReader.read(charArray2);
                    stringBuffer.append(charArray2, 0, 1);
                    SiteIdT = stringBuffer.toString();
                    stringBuffer.delete(0, 1);

                    Global1.ServiceData[ServicePos] = new FullscreenActivity.ServicesDataClass();
                    Global1.ServiceData[ServicePos].site = SiteT;
                    Global1.ServiceData[ServicePos].City = CityT;
                    Global1.ServiceData[ServicePos].daysnum = Integer.valueOf(DaysT);
                    Global1.ServiceData[ServicePos].siteId = Integer.valueOf(SiteIdT);
                    Global1.ServiceData[ServicePos].hasdone=false; //NEW //Maybe not needed

                    ServicePos++;


                    OutText += "Site:" + Global1.ServiceData[ServicePos - 1].site + "\n" +
                            "City:" + Global1.ServiceData[ServicePos - 1].City + "\n" +
                            "Times in a Day:" + Global1.ServiceData[ServicePos - 1].daysnum + "\n" +
                            "_____________________________\n";

                }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



        SaveService();
        return;
    }



    public void SaveService()
    {


        String SiteT;
        String CityT;
        String DaysT;
        String SiteIdT;
        String TimeT;

        String datapath;
        datapath=getApplicationInfo().dataDir+"/Servicedat.txt";
        File fileSc = new File(datapath);

        SiteIdT=String.valueOf(Global1.ServiceData[ServicePos].siteId);
        SiteT=Global1.ServiceData[ServicePos].site;
        CityT=Global1.ServiceData[ServicePos].City;
        DaysT=String.valueOf(Global1.ServiceData[ServicePos].daysnum);
        TimeT=String.valueOf(Global1.ServiceData[ServicePos].time);

        for (i=SiteT.length();i<20;i++) SiteT+=" ";
        for (i=CityT.length();i<50;i++) CityT+=" ";
        for (i=TimeT.length();i<20;i++) TimeT+=" ";


        try {
            //fileSc.createNewFile();
            FileWriter fileWriter = new FileWriter(fileSc,true); //Append File
            //FileWriter fileWriter = new FileWriter(fileSc); //New File
            fileWriter.write(SiteT, 0, 20);
            fileWriter.write(CityT, 0, 50);
            fileWriter.write(DaysT, 0, 1);
            fileWriter.write(SiteIdT, 0, 1);
            fileWriter.write(TimeT, 0, 20);

            fileWriter.flush();

            fileWriter.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        ServicePos++;

        Global1.ServiceRecs=ServicePos;

        return;

    }


}