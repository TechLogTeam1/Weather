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
import android.text.method.DialerKeyListener;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

public class WeatherDetails extends AppCompatActivity {

    private View mContentView;

    private TextView mTitleText;
    private TextView mDataTxt;
    private Button mCommit;
    private EditText mEditCommit;

    private int ScrollY,ScrollPos,ScrollId;

    private int i,o;
    private String Contents,ContentsTmp;

    private String TempT,HumidityT,UnitsT,SiteT,CommentT,CityT;
    private String SiteIdT,DaysT;
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

    //Μετατροπή milliseconds σε String ημερομηνία/ώρα
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

    //Mετατρέπει την θερμοκρασία απο Celsius σε Fahrenheit η Kelvin,
    //εάν είναι Celsius η τιμή επιστρέφει όπως δόθηκε.
    public float ShowTemp(float TempF)
    {
        if (Global1.Units2=="C") return TempF;
        if (Global1.Units2=="F") return (TempF*(float)1.8+32);
        if (Global1.Units2=="K") return TempF+(float)273.15;
        return 0;
    }

    //Εισαγωγή σχολίου σε μια εγγραφή
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

        Global1.ArraySize=(int)(fileSc.length()-1)/320;

        //Έλενχος αν η εγγραφή του ιστορικού είναι άδεια
        if (fileSc.length()<320)
        {
            Toast.makeText(WeatherDetails.this, "Services Records Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        //Καταχώρηση σχολίου στην εγγραφή αφου δεν είναι άδεια
        int hpos;
        hpos = Global1.SearchData[ScrollId].pos;
        Global1.HistoryData[hpos].Comment = mEditCommit.getText().toString();
        Global1.SearchData[hpos].Comment = mEditCommit.getText().toString(); //NEW //CHECK

        try {

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
        mDataTxt.setMovementMethod(new ScrollingMovementMethod());

        //NEW
        Period = Global1.Period;
        Contents=Global1.Contents;

        //Επιλογή button σχολίου
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Services:","Period:"+String.valueOf(Period));

                //Περίπτωση εγγραφής ιστορικού
                if ((Period!=11) && (Period!=12))
                {
                    ScrollY = mDataTxt.getScrollY();
                    ScrollPos = ScrollY / mDataTxt.getLineHeight();
                    ScrollId = ScrollPos / 8; //ADDED _____________________
                    SaveHistory2(); //Εισαγωγή σχολίου σε μια εγγραφή
                    for (i=1;i<=15;i++) Contents+="\n"; //Help for choosing from top line
                    mDataTxt.setText(Contents);
                    mDataTxt.setScrollY(ScrollY);

                }

                //Διαγραφή εγγραφής υπηρεσιών (Services)
                if (Period==11) {
                    ScrollY = mDataTxt.getScrollY();
                    ScrollPos = ScrollY / mDataTxt.getLineHeight();
                    ScrollId=ScrollPos/3;
                    Global1.ServiceSelDel=ScrollId;
                    DeleteService();
                    ReadService();
                    mDataTxt.setScrollY(ScrollY);
                }

                //Διαγραφή εγγραφής Συντεταγμένων (Coordinates)
                if (Period==12) {

                    ScrollY = mDataTxt.getScrollY();
                    ScrollPos = ScrollY / mDataTxt.getLineHeight();
                    ScrollId=ScrollPos/3;
                    Global1.NameSelDel=ScrollId;
                    DeleteName();
                    mDataTxt.setScrollY(ScrollY);
                }


            }
        });

        //Περίπτωση εγγραφής καιρού 24ώρου
        if (Period==2) {

            mCommit.setVisibility(View.GONE);
            mEditCommit.setVisibility(View.GONE);

            mTitleText.setText("Next 24 hours");
            mDataTxt.setText(Contents);

        }

        //Περιπτωσή εγγραφής 5ήμερου/εβδομάδας
        if (Period==3)
        {
            mCommit.setVisibility(View.GONE);
            mEditCommit.setVisibility(View.GONE);

            mTitleText.setText("Next 5 days");
            mDataTxt.setText(Contents);

        }

        //Εμφάνιση δεδομένων ιστορικού
        if (Period==10) //Search
        {

            mCommit.setVisibility(View.VISIBLE);
            mEditCommit.setVisibility(View.VISIBLE);
            mTitleText.setText("History");
            Contents="";
            Contents=Global1.Contents;

            for (i=0;i<Global1.SearchArraySize;i++)
            {
                cont=false;
                searchcnt=0;

                posStr=49;
                for (o=49; o>0; o--) if (Global1.SearchData[i].City.charAt(o)!=' ') {posStr=o+1;break;}
                Global1.SearchData[i].City=Global1.SearchData[i].City.substring(0,posStr);

                posStr=99;
                for (o=99; o>0; o--) if (Global1.SearchData[i].WeatherCon.charAt(o)!=' ') {posStr=o+1;break;}
                Global1.SearchData[i].WeatherCon=Global1.SearchData[i].WeatherCon.substring(0,posStr);

                posStr=99;
                for (o=99; o>0; o--) if (Global1.SearchData[i].Comment.charAt(o)!=' ') {posStr=o+1;break;}
                Global1.SearchData[i].Comment=Global1.SearchData[i].Comment.substring(0,posStr);

                Contents+="Site:"+Global1.SearchData[i].Site+"\n"+
                        "City:"+Global1.SearchData[i].City+"\n"
                        +"Date:"+Global1.SearchData[i].Date+"\n"+
                        "Temp:"+ShowTemp(Global1.SearchData[i].Temperature)+" "+ Global1.SearchData[i].Units+"\n"+
                        "Humidity:"+Global1.SearchData[i].Humidity+"%\n"
                        +"Conditions:"+Global1.SearchData[i].WeatherCon+"\n"
                        +"Comment:"+Global1.SearchData[i].Comment+"\n"+
                        "_____________________________\n";

            }

            for (i=1;i<=15;i++) Contents+="\n"; //Help for choosing from top line
            mDataTxt.setText(Contents);
            mDataTxt.setScrollY(ScrollY); //NEW

        }

        //Μετανομασία του button commit σε delete για χρήση σε ρουτίνα service
        if (Period==11)
        {
            mCommit.setText("Delete");
            mEditCommit.setVisibility(View.GONE);

            mTitleText.setText("Services");
            for (i=1;i<=15;i++) Contents+="\n"; //Help for choosing from top line
            mDataTxt.setText(Contents);

        }

        //Μετανομασία του button commit σε delete για χρήση σε ρουτίνα coordinates
        if (Period==12) {

            mCommit.setText("Delete");
            mEditCommit.setVisibility(View.GONE);

            mTitleText.setText("Names Coordinates Records");
            for (i=1;i<=15;i++) Contents+="\n"; //Help for choosing from top line
            mDataTxt.setText(Contents);

        }

    }

    //Επανεγγραφή της λίστας των υπηρεσιών χωρίς την διαγραφόμενη υπηρεσία
    public void SaveServicesAll()
    {
        String datapath;
        datapath = getApplicationInfo().dataDir + "/Servicedat.txt";
        File fileSc = new File(datapath);
        long filelen;
        String AlarmT;

        OutText="";
        filelen=fileSc.length();


        int strlen;



        try {

            FileWriter fileWriter = new FileWriter(fileSc); //New File //This needed here

                    for (o=0;o<=(filelen-1)/112;o++)
                        if (o!=Global1.ServiceSelDel)
                        {


                    SiteIdT=String.valueOf(Global1.ServiceData[o].siteId);
                    SiteT=Global1.ServiceData[o].site;
                    CityT=Global1.ServiceData[o].City;
                    DaysT=String.valueOf(Global1.ServiceData[o].daysnum);
                    TimeT=String.valueOf(Global1.ServiceData[o].time);
                    AlarmT=String.valueOf(Global1.ServiceData[o].AlarmId);

                    for (i=SiteT.length();i<20;i++) SiteT+=" ";
                    for (i=CityT.length();i<50;i++) CityT+=" ";
                    for (i=TimeT.length();i<20;i++) TimeT+=" ";
                    for (i=AlarmT.length();i<20;i++) AlarmT+=" ";

                    fileWriter.write(SiteT, 0, 20);
                    fileWriter.write(CityT, 0, 50);
                    fileWriter.write(DaysT, 0, 1);
                    fileWriter.write(SiteIdT, 0, 1);
                    fileWriter.write(TimeT, 0, 20);
                    fileWriter.write(AlarmT, 0, 20);

                    fileWriter.flush();

                       OutText += "Site:" + Global1.ServiceData[o].site + "\n" +
                               "City:" + Global1.ServiceData[o].City + "\n" +
                               "Time:"+ ConvertLongtoDate(Global1.ServiceData[o].time)+"\n"+
                                    "_____________________________\n";

                }

            fileWriter.close();
        }

        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        Contents=OutText;
        for (i=1;i<=15;i++) Contents+="\n"; //Help for choosing from top line
        mDataTxt.setText(Contents);

        return;

    }


    //Κατήργηση μίας ειδοποίησης service (alarm)
    public void DisableAlarm(long time)

    {
        AlarmManager am=Global1.am;
        Intent i=Global1.i;
        int AlarmIdSel;

        AlarmIdSel=Global1.ServiceData[Global1.ServiceSelDel].AlarmId;;
        PendingIntent pi = PendingIntent.getBroadcast(this, AlarmIdSel, i, PendingIntent.FLAG_CANCEL_CURRENT); //NEW
        Log.d("AlarmService","Service Num Off:"+String.valueOf(AlarmIdSel));
        am.cancel(pi);

    }

    //Δίαβασμα εγγραφής υπηρεσιών
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
        String AlarmT;

        ServicePos=0;


        try {
            File fileSc = new File(datapath);
            //fileSc.delete(); //TMP
            //fileSc.createNewFile();//TMP
            FileReader fileReader = new FileReader(fileSc);
            StringBuffer stringBuffer = new StringBuffer();
            int numCharsRead = 0;
            char[] charArrayCity = new char[50];
            char[] charArray1=new char[20];
            char[] charArray2=new char[1];
            OutText="";

            if (fileSc.length()!=0) //NEW NEEDED HERE
                for (i=0;i<=(fileSc.length()-1)/112;i++)
                {
                    SiteT="";CityT="";DaysT="";SideIdT="";TimeT="";AlarmT="";


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

                    fileReader.read(charArray1);
                    stringBuffer.append(charArray1, 0, 20);
                    AlarmT = stringBuffer.toString();
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

                    posStr=49;
                    for (o=49; o>0; o--) if (CityT.charAt(o)!=' ') {posStr=o+1;break;}
                    CityT=CityT.substring(0,posStr);
                    Global1.ServiceData[ServicePos].City=CityT;

                    posStr=19;
                    for (o=19; o>=0; o--) if (AlarmT.charAt(o)!=' ') {posStr=o+1;break;} //CHECK >=0 NEEDED HERE
                    AlarmT=AlarmT.substring(0,posStr);
                    Global1.ServiceData[ServicePos].AlarmId=Integer.valueOf(AlarmT);

                    ServicePos++;

                    Global1.ServiceRecs=ServicePos;

                    OutText+="Site:"+ Global1.ServiceData[ServicePos-1].site+"\n"+
                            "City:"+ Global1.ServiceData[ServicePos-1].City+"\n"+
                            "Time:"+ ConvertLongtoDate(Global1.ServiceData[ServicePos-1].time)+"\n"+
                            "_____________________________\n";

                }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return;
    }


    //Διαγραφή υπηρεσίας απο την λίστα εγγραφών και κατάργηση απο την μνήμη (background service)
    public void DeleteService() {
        String datapath, strdata;
        char[] scArray = new char[29];
        datapath = getApplicationInfo().dataDir + "/Servicedat.txt";
        int o;

        String SiteT;
        String CityT;
        String DaysT;
        String SiteIdT;
        String AlarmT;

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

            //Έλενχος αν η εγγραφή των υπηρεσιών (services) είναι άδεια
            if ((Global1.ServiceRecs==0) || (fileSc.length()<112)) {
                Toast.makeText(this, "Services Records Empty", Toast.LENGTH_SHORT).show();
                return;
            }

            //Κατάργηση ειδοποίησης απο την μνήμη
            DisableAlarm(Global1.ServiceData[Global1.ServiceSelDel].time);

            for (i = 0; i <= (fileSc.length() - 1) / 112; i++)
                if (i != Global1.ServiceSelDel) {
                    SiteT = "";
                    CityT = "";
                    DaysT = "";
                    TimeT="";
                    SiteIdT="";
                    AlarmT="";

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

                    fileReader.read(charArray1);
                    stringBuffer.append(charArray1, 0, 20);
                    TimeT = stringBuffer.toString();
                    stringBuffer.delete(0, 20);

                    fileReader.read(charArray1);
                    stringBuffer.append(charArray1, 0, 20);
                    AlarmT = stringBuffer.toString();
                    stringBuffer.delete(0, 20);


                    posStr=19;
                    for (o=19; o>0; o--) if (TimeT.charAt(o)!=' ') {posStr=o+1;break;}
                    TimeT=TimeT.substring(0,posStr);
                    //Global1.ServiceData[ServicePos].time=Long.valueOf(TimeT);

                    posStr=19;
                    for (o=19; o>=0; o--) if (AlarmT.charAt(o)!=' ') {posStr=o+1;break;}
                    AlarmT=AlarmT.substring(0,posStr);

                    //NEW
                    posStr=49;
                    for (o=49; o>0; o--) if (CityT.charAt(o)!=' ') {posStr=o+1;break;}
                    CityT=CityT.substring(0,posStr);
                    //Global1.ServiceData[ServicePos].City=CityT;


                    Global1.ServiceData[ServicePos] = new FullscreenActivity.ServicesDataClass();
                    Global1.ServiceData[ServicePos].site = SiteT;
                    Global1.ServiceData[ServicePos].City = CityT;
                    Global1.ServiceData[ServicePos].daysnum = Integer.valueOf(DaysT);
                    Global1.ServiceData[ServicePos].siteId = Integer.valueOf(SiteIdT);
                    Global1.ServiceData[ServicePos].time=Long.valueOf(TimeT); //CHECK for error
                    Global1.ServiceData[ServicePos].AlarmId=Integer.valueOf(AlarmT);
                    Global1.ServiceData[ServicePos].hasdone=false; //NEW //Maybe not needed

                    ServicePos++;


                    OutText += "Site:" + Global1.ServiceData[ServicePos - 1].site + "\n" +
                            "City:" + Global1.ServiceData[ServicePos - 1].City + "\n" +
                            "Time:"+ ConvertLongtoDate(Global1.ServiceData[ServicePos-1].time)+"\n"+
                            "_____________________________\n";



                }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Επανεγγραφή της λίστας των υπηρεσιών χωρίς την διαγραφόμενη υπηρεσία
        SaveServicesAll();

        return;
    }


    //Επανεγγραφή της λίστας χωρίς την διαγραφόμενη καταχώρηση
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

                    NameT="";

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
        for (i=1;i<=15;i++) Contents+="\n"; //Help for choosing from top line
        mDataTxt.setText(Contents);

        return;

    }

    //Διαγραφή μιας εγγραφής ονομασίας συντεταγμένων (coordinates)
    public void DeleteName()
    {

        String datapath, strdata;
        char[] scArray = new char[29];
        datapath = getApplicationInfo().dataDir + "/Coords.txt";
        int o;
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


            //Έλενχος αν η εγγραφή λίστας είναι άδεια, εμφανίζοντας το κατάλληλο μύνημα
            if ((Global1.CoordsRecs==0) || (fileSc.length()<80)) {
                Toast.makeText(this, "Coordinates Records Empty", Toast.LENGTH_SHORT).show();
                return;
            }


            //For 1 entry only delete, clean memory
                Global1.CoordsNamesData[0].Name="";

                //Clear Memory of previous Name
               Global1.CoordsNamesData[Global1.NameSelDel].Name=""; //PREV //CHECK


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

                    CoordsPos++;
                    Global1.CoordsRecs=CoordsPos;

                    OutText+="Name:"+ FullscreenActivity.Global1.CoordsNamesData[CoordsPos-1].Name+"\n"+
                            "Latitude:"+FullscreenActivity.Global1.CoordsNamesData[CoordsPos-1].lat+"\n"+
                            "Longitute:"+FullscreenActivity.Global1.CoordsNamesData[CoordsPos-1].lon+"\n"+
                            "_____________________________\n";


                }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Επανεγγραφή της λίστας χωρίς την διαγραφόμενη καταχώρησης
        SaveNamesAll();

        return;
    }


}
