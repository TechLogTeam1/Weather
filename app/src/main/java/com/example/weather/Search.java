package com.example.weather;

import com.example.weather.FullscreenActivity.Global1;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.example.weather.FullscreenActivity.Global1.ArraySize;
import static com.example.weather.FullscreenActivity.Global1.HistoryData;
import static com.example.weather.FullscreenActivity.Global1.Period;

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
    private Button mButtonAdvSearch;

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
    private int i,o,searchpos,PrevPeriod;
    private boolean SearchCityOn,ContinueRec;
    private String Date;

    private Date DateExp,DateExp2;
    private Date DateExpFrom,DateExpTo;
    private Date DateComp,DateComp2;
    private String DateExpSTR,DateExpSTR2;

    private boolean CheckOpen=true;
    private boolean CheckAccu=true;
    private boolean  CheckStack=true;
    private boolean  CheckDark=true;
    private boolean  CheckBit=true;
    private boolean  CheckService=false;
    private boolean siteCont,siteContW,siteContS,siteContSt;
    private int posStr;
    private String CityT,DateT;
    private long days,daysFrom,daysTo;

    public float ShowTemp(float TempF)
    {
        if (Global1.Units2=="C") return TempF;
        if (Global1.Units2=="F") return (TempF*(float)1.8+32);
        if (Global1.Units2=="K") return TempF+(float)273.15;
        return 0;
    }

    //Μετατροπή Unix ημεροηνία σε ημερομηνία/ώρα
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

    //Μετατροπή Unix ημεροηνία σε πλήρης ημερομηνία
    public String ConvertLongtoDateFull(long unixSeconds) {


        Date date;

        date=new Date();
        date=new Date(unixSeconds); //Not X 1000
        String strDateFormat = "dd-MM-yyyy HH:mm:ss z";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }


     //Διαβάζει και φιλτράρει το ιστορικό
    public void ReadHistory()

    {
        String datapath,strdata;
        char[] scArray = new char[29];
        datapath=getApplicationInfo().dataDir+"/Weatherdat.txt";
        int o;
        int stopPos;
        float tempf,humf;
        int year,month,day;
        int yearfrom,monthfrom,dayfrom;
        int yearto,monthto,dayto;
        String dateSTR;

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
                CityT="";DateT="";

                fileReader.read(charArray6);
                stringBuffer.append(charArray6, 0, 20);
                SiteT = stringBuffer.toString();
                stringBuffer.delete(0, 20);

                fileReader.read(charArrayCity);
                stringBuffer.append(charArrayCity, 0, 50);
                CityT = stringBuffer.toString();
                stringBuffer.delete(0, 50);

                fileReader.read(charArray);
                stringBuffer.append(charArray, 0, 29);
                DateT = stringBuffer.toString();
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



                posStr=49;
                for (o=49; o>0; o--) if (CityT.charAt(o)!=' ') {posStr=o+1;break;}
                CityT=CityT.substring(0,posStr);

                posStr=99;
                for (o=99; o>0; o--) if (WeatherConT.charAt(o)!=' ') {posStr=o+1;break;}
                WeatherConT=WeatherConT.substring(0,posStr);

                posStr=99;
                for (o=99; o>0; o--) if (CommentT.charAt(o)!=' ') {posStr=o+1;break;}
                CommentT=CommentT.substring(0,posStr);

                //NEW //CHECK
                posStr=9;
                for (o=9; o>0; o--) if (TempT.charAt(o)!=' ') {posStr=o+1;break;}
                TempT=TempT.substring(0,posStr);


                City=CityT;Date=DateT;

                Global1.HistoryData[HistoryPos]=new FullscreenActivity.HistoryDataClass();
                Global1.HistoryData[HistoryPos].Site=SiteT;
                Global1.HistoryData[HistoryPos].City=City;
                Global1.HistoryData[HistoryPos].Date=Date;
                Global1.HistoryData[HistoryPos].Temperature=ShowTemp(Float.valueOf(TempT));
                Global1.HistoryData[HistoryPos].Humidity=Float.valueOf(HumidityT);
                Global1.HistoryData[HistoryPos].WeatherCon=WeatherConT; //CHECK
                //Global1.HistoryData[HistoryPos].Units=UnitsT;
                Global1.HistoryData[HistoryPos].Units=Global1.Units2;
                Global1.HistoryData[HistoryPos].Comment=CommentT;
                Global1.HistoryData[HistoryPos].pos=HistoryPos;

                HistoryPos++;
                SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss z");
                sdf.setTimeZone(TimeZone.getTimeZone("Europe/Athens")); //NEW
                dateSTR=Global1.HistoryData[i].Date;

                Log.d("WeatherSearch:","Record DateSTR:"+dateSTR);
                day=Integer.valueOf(dateSTR.substring(0,2));
                month=Integer.valueOf(dateSTR.substring(3,5));
                year=Integer.valueOf(dateSTR.substring(6,10));
                Log.d("WeatherSearch:","Date1:"+day+"/"+month+"/"+year);

                dateSTR=ConvertLongtoDateFull(Global1.DateFromM);

                dayfrom=Integer.valueOf(dateSTR.substring(0,2));
                monthfrom=Integer.valueOf(dateSTR.substring(3,5));
                yearfrom=Integer.valueOf(dateSTR.substring(6,10));

                //dateSTR=ConvertLongtoDateFull(Global1.DateTo.getTime());
                dateSTR=ConvertLongtoDateFull(Global1.DateToM);

                dayto=Integer.valueOf(dateSTR.substring(0,2));
                monthto=Integer.valueOf(dateSTR.substring(3,5));
                yearto=Integer.valueOf(dateSTR.substring(6,10));


                  days=(long)(year-1970)*356+(month*30)+day;
                  daysFrom=Global1.DateFromM;
                  daysTo=Global1.DateToM;


                siteContSt=false;
                if (CheckOpen) if (HistoryData[i].Site.contains("Open Weather Map")) siteContSt=true;
                if (CheckAccu) if (HistoryData[i].Site.contains("AccuWeather")) siteContSt=true;
                if (CheckStack) if (HistoryData[i].Site.contains("Weather Stacks")) siteContSt=true;
                if (CheckDark) if (HistoryData[i].Site.contains("Dark Sky")) siteContSt=true;
                if (CheckBit) if (HistoryData[i].Site.contains("Weatherbit.io")) siteContSt=true;

                if (CheckService)
                {
                    if (HistoryData[i].Site.contains("(s)")) siteCont = true; else siteCont=false;
                }

                siteContW=false;

                if ((Global1.Clear) &&(Global1.Clouds)&&(Global1.Rain)&& (Global1.Snow) &&(Global1.Thunder))
                    siteContW=true; //All cases

                if (Global1.Clear)
                {
                    if (HistoryData[i].WeatherCon.toLowerCase().contains("clear")) siteContW = true;
                    if (HistoryData[i].WeatherCon.toLowerCase().contains("sunny")) siteContW = true;
                }

                if (Global1.Clouds)
                {
                    if (HistoryData[i].WeatherCon.toLowerCase().contains("overcast")) siteContW = true;
                    if (HistoryData[i].WeatherCon.toLowerCase().contains("cloud")) siteContW = true;
                    if (HistoryData[i].WeatherCon.toLowerCase().contains("mist")) siteContW=true;
                    if (HistoryData[i].WeatherCon.toLowerCase().contains("haze")) siteContW = true;
                }
                if (Global1.Rain)
                {
                    if (HistoryData[i].WeatherCon.toLowerCase().contains("rain")) siteContW = true;
                    if (HistoryData[i].WeatherCon.toLowerCase().contains("drizzle")) siteContW = true;
                }
                if (Global1.Snow)
                    if (HistoryData[i].WeatherCon.toLowerCase().contains("snow")) siteContW=true;

                if (Global1.Thunder)
                    if (HistoryData[i].WeatherCon.toLowerCase().contains("thunder")) siteContW=true;

                if (!CheckService) siteContS=true;

                if (CheckService)
                {
                    if (HistoryData[i].Site.contains("(s)")) siteContS = true; else siteContS=false;
                }

                //History 1 New
                if ((HistoryData[i].Temperature>=Global1.FromT) && (HistoryData[i].Temperature<=Global1.ToT)) siteCont=true;
                else siteCont=false;

                if (Global1.HistoryData[i].City.toLowerCase().contains(Global1.City.toLowerCase())) //Έλεγχος για την πόλη
                if ((days>=daysFrom) && (days<=daysTo)) //Ελενχος για εύρος ημερομηνίων
                if (siteCont)
                if (siteContW)  //Έλενχος για καιρικές συνθήκεςε
                if (siteContS)  //Έλενγχος αν είναι μονο για service
                    if (siteContSt) //Έλενχος για τα επιλεγμένα site προβολής
                OutText+="Site:"+Global1.HistoryData[HistoryPos-1].Site+"\n"+
                        "City:"+Global1.HistoryData[HistoryPos-1].City+"\n"
                        +"Date:"+Global1.HistoryData[HistoryPos-1].Date+"\n"+
                        "Temp:"+Global1.HistoryData[HistoryPos-1].Temperature+" "+ Global1.HistoryData[HistoryPos-1].Units+"\n"+
                        "Humidity:"+Global1.HistoryData[HistoryPos-1].Humidity+"%\n"
                        +"Conditions:"+Global1.HistoryData[HistoryPos-1].WeatherCon+"\n"
                        +"Comment:"+Global1.HistoryData[HistoryPos-1].Comment+"\n"+
                        "_____________________________\n";

            }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        PrevPeriod=Period;
        Period=10;
        Contents="Records\n";

        searchpos = 0; //CHECK

        Period=Period;
        Global1.SearchArraySize=searchpos;
        Global1.Contents=OutText;
        Period=PrevPeriod;

        return;
    }

    //Έλενχος για στωστή ημερομηνία
    //public boolean checkdate(String DateS)
    public static boolean checkdate(String DateS)
    {

        int digits;
        int pavles;
        int i;
        int day, month, year;
        boolean cont;

        digits=0;
        pavles=0;

        if (DateS.length()!=10) return false;

        for (i=0;i<10;i++)
            if ((DateS.charAt(i)>='0') && (DateS.charAt(i)<='9')) digits++;
        if (digits!=8) return false;


        if (DateS.charAt(2)=='-') pavles++;
        if (DateS.charAt(5)=='-') pavles++;

        if (pavles!=2) return false;


        day=Integer.valueOf(DateS.substring(0,2));
        month=Integer.valueOf(DateS.substring(3,5));
        year=Integer.valueOf(DateS.substring(6,10));

        cont=false;
        if ((day>=1) && (day<=31))
        if ((month>=1) && (month<=12))
        cont=true;

        if (!cont) return false;

        return true;
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

        //Διάβασμα της τωρινής ημερομηνίας
        Date date1 = new Date();
        String strDateFormat = "dd-MM-yyyy";
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
        mButtonAdvSearch= (Button) findViewById(R.id.buttonSearch2);

        mCheckOpen=(CheckBox)findViewById(R.id.checkBox);
        mCheckAccu=(CheckBox)findViewById(R.id.checkBox2);
        mCheckStack=(CheckBox)findViewById(R.id.checkBox3);
        mCheckDark=(CheckBox)findViewById(R.id.checkBox4);
        mCheckBit=(CheckBox)findViewById(R.id.checkBox5);
        mCheckService=(CheckBox)findViewById(R.id.checkBox6);

        //Μηδενίζει την πόλη για να ψαχνει αρχικά για όλες τις περιπτώσεις
        mCity.setText("");

        //Μαρκάρει όλα τα checkboxes
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

        //Ξεμαρκάρει όλα τα checkboxes
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

        //Καθαρίζει την φόρμα
        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCity.setText("");
                mFrom.setText("");
                mTo.setText("");
            }
        });

        //Ημερήσια ημερομηνία
        mButtonDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = new Date();
                String strDateFormat = "dd-MM-yyyy";
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

        //Εβδομαδιαία ημερομηνία
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

        //Μηνιαία ημερομηνία
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

                calendar2 = Calendar.getInstance();
                calendar2.setTime(date);
                calendar2.set(Calendar.DAY_OF_MONTH,1);
                date=calendar2.getTime();

                dateFormat.format(date);
                formattedDate= dateFormat.format(date);
                DateFrom=formattedDate;

                //DateTo
                calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

                date2=calendar.getTime();
                formattedDate= dateFormat.format(date2);

                DateTo=formattedDate;

                Global1.DateFrom=date;
                Global1.DateTo=date2;

                mFrom.setText(DateFrom);
                mTo.setText(DateTo);

            }
        });


        //Εκκίνηση έρευνας ιστορικού
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = new Date();
                Date date2 = new Date();

                int year,month,day;
                int yearfrom,monthfrom,dayfrom;
                int yearto,monthto,dayto;
                String dateSTR;

                Period=10;

                Global1.City=mCity.getText().toString();


                dateSTR=mFrom.getText().toString();

                //Έλενχος για ημερομηνία απο
                if (!checkdate(dateSTR))
                {
                    Toast.makeText(Search.this, "Wrong date from", Toast.LENGTH_SHORT).show();
                return;
                }

                dayfrom=Integer.valueOf(dateSTR.substring(0,2));
                monthfrom=Integer.valueOf(dateSTR.substring(3,5));
                yearfrom=Integer.valueOf(dateSTR.substring(6,10));

                dateSTR=mTo.getText().toString();

                //Έλενχος για ημερομηνία εώς
                if (!checkdate(dateSTR))
                {
                    Toast.makeText(Search.this, "Wrong date to", Toast.LENGTH_SHORT).show();
                    return;
                }


                dayto=Integer.valueOf(dateSTR.substring(0,2));
                monthto=Integer.valueOf(dateSTR.substring(3,5));
                yearto=Integer.valueOf(dateSTR.substring(6,10));

                daysFrom=(long)(yearfrom-1970)*356+(monthfrom*30)+dayfrom;
                daysTo=(long)(yearto-1970)*356+(monthto*30)+dayto;

                Global1.DateFromM=daysFrom;
                Global1.DateToM=daysTo;

                Log.d("WeatherSearch","From1:"+dayfrom+"/"+monthfrom+"/"+yearfrom);
                Log.d("WeatherSearch","To1:"+dayto+"/"+monthto+"/"+yearto);

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

        //Ρυθμίσεις ενισχυμένης αναζήτησης
        mButtonAdvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Period=12;

                Global1.DateFrom=DateFrom1;
                Global1.DateTo=DateTo1;


                if (mCheckOpen.isChecked()) CheckOpen=true; else CheckOpen=false;
                if (mCheckAccu.isChecked()) CheckAccu=true; else CheckAccu=false;
                if (mCheckStack.isChecked()) CheckStack=true; else CheckStack=false;
                if (mCheckDark.isChecked()) CheckDark=true; else CheckDark=false;
                if (mCheckBit.isChecked()) CheckBit=true; else CheckBit=false;
                if (mCheckService.isChecked()) CheckService=true; else CheckService=false;


                //ReadHistory();
                Intent intent = new Intent(Search.this, AdvSearch.class);
                startActivity(intent);

            }
        });



    }

}

