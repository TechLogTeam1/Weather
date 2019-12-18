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

        //Διαβάζει τις καταχωρήσεις των υπηρεσιών
        ReadService();

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

        //Ελένχει τα radio button για τα services
        //@param ServiceTimes
        //Παίρνει την τιμή για τις υπηρεσίες services
        //Παίρνει 1 για μια φορά στις 14:00
        //Παίρνει 2 για δυό φορες την ημέρα στις 08:00 και 22:00
        //Παίρνει 3 για δυό φορες την ημέρα στις 08:00,14:00 και 22:00
        //Πάιρνει 10 για custom ώρα στην ημέρα

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

        //Προχωραέι στο να δημιουργήσει το alarm
        mSetService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                City=mCity.getText().toString();
                TimeSTR=mTime.getText().toString();

                if (!checktime(TimeSTR))
                {
                    Toast.makeText(Services.this, "Service time is wrong", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (mCheckOpen.isChecked()) CheckOpen=true; else CheckOpen=false;
                if (mCheckAccu.isChecked()) CheckAccu=true; else CheckAccu=false;
                if (mCheckStack.isChecked()) CheckStack=true; else CheckStack=false;
                if (mCheckDark.isChecked()) CheckDark=true; else CheckDark=false;
                if (mCheckBit.isChecked()) CheckBit=true; else CheckBit=false;

                SetAlarmService();

            }
        });


        //Ξεκινάει την εμφάνιση της λίστας των services
        mShowSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                ReadService();
                Global1.Period=11;
                Global1.Contents=OutText;
                Intent intent = new Intent(Services.this, WeatherDetails.class);
                startActivity(intent);

            }

        });



    }

    //Μετατροπή Unix χρόνου σε String ημερομηνία
    public String ConvertLongtoDate(long unixSeconds) {


        Date date;
        int hours;

        Calendar calendar;

        //hours+=2; //GMT +2 (Athens)
        //if (hours==25) hours=1;
        //if (hours==26) hours=2;

        calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Athens")); //NEW
        calendar.setTimeInMillis(unixSeconds);
        //calendar.add(Calendar.HOUR_OF_DAY,-2); //GMT +2 Compbatibility
        date=calendar.getTime();

        String strDateFormat = "HH:mm:ss z";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }

    //Θέτει το alarm service
    private void setAlarm(long time) {

        int o,s;
        int recvalue;
        boolean notcont;

        AlarmManager am=Global1.am;

        notcont=false;

        //Ψάχνει μια αδεία θέση για Id στο alarm
        for (recvalue=0;recvalue<=10000;recvalue++) //PREV
        {
        notcont=false;

            for (o = 0; o <= ServicePos; o++)
                if (o != ServicePos) {
                    if (Global1.ServiceData[o].AlarmId == recvalue) notcont = true;
                }

            if (!notcont) {Global1.ServiceData[ServicePos].AlarmId=recvalue;break;}
        }

        Intent i=Global1.i;
        //Θέτει το id της κλήσης.Είναι ενας αυξοντας string αριθμός π.χ "0","1","2","3"...
        i.setAction(String.valueOf(Global1.ServiceData[ServicePos].AlarmId));

        //Μεταφέριε στο action την πολή,την τοποθεσία service και το site καιρού της κλήσης
        i.putExtra("City",City);
        i.putExtra("ServicePos",Global1.ServiceData[ServicePos].AlarmId);
        i.putExtra("Site",Global1.ServiceData[ServicePos].siteId);

        //NEW
        i.putExtra("Site1",CheckOpen);
        i.putExtra("Site2",CheckAccu);
        i.putExtra("Site3",CheckStack);
        i.putExtra("Site4",CheckDark);
        i.putExtra("Site5",CheckBit);

        //PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        PendingIntent pi = PendingIntent.getBroadcast(this, ServicePos, i, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("AlarmService","Service Num Set:"+String.valueOf(Global1.ServiceData[ServicePos].AlarmId));
        Log.d("AlarmService","Service Time:"+ConvertLongtoDate(time));
        //am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi); //PREV
        am.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pi);

    }

    //Έλενχει αν υπάρχει εγγραφή με την ίδια ώρα και πολή και site
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

    //Καταχωρεί μια νέα υπηρεσία alarm
    public void NewRec(int days,Calendar calendar)

    {
        if (mCheckOpen.isChecked()) CheckOpen=true; else CheckOpen=false;
        if (mCheckAccu.isChecked()) CheckAccu=true; else CheckAccu=false;
        if (mCheckStack.isChecked()) CheckStack=true; else CheckStack=false;
        if (mCheckDark.isChecked()) CheckDark=true; else CheckDark=false;
        if (mCheckBit.isChecked()) CheckBit=true; else CheckBit=false;

        Global1.ServiceData[ServicePos]=new FullscreenActivity.ServicesDataClass();
        Global1.ServiceData[ServicePos].daysnum = days;
        Global1.ServiceData[ServicePos].site="OpenWeather";
        Global1.ServiceData[ServicePos].siteId=1;
        Global1.ServiceData[ServicePos].City=City;
        Global1.ServiceData[ServicePos].hasdone=false;
        Global1.ServiceData[ServicePos].time=calendar.getTimeInMillis();
        setAlarm(calendar.getTimeInMillis());
        Log.d("AlarmService:","Date Service = "+ConvertLongtoDate(calendar.getTimeInMillis()));
        SaveService();
    }

    //Θέτει τις ώρες του alarm service και το ίδιο το sevice μέσο της setAlarm
    public void SetAlarmService()

    {
        boolean sameone;
        int hours,minutes,day,year,month,hoursCur,minutesCur;
        String TimeGMT;
        String DateCustomS;
        boolean plusDay;

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Athens")); //NEW
        Global1.datapath=getApplicationInfo().dataDir+"/Weatherdat.txt";

        sameone=false;

        ReadService(); //NEW

        //Κλήση στις 14:00
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
            hoursCur=date.getHours();
            minutesCur=date.getMinutes();

            hours=14;minutes=0;
            calendar.setTimeInMillis(System.currentTimeMillis()); //NEW

            //Alarm Mεσημέρι 14:00
            calendar.set(Calendar.HOUR_OF_DAY, 14);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            //Eδώ χρησιμοποιείται η plusDay για να την περίωπτωση που η alarm service ειναι πριν απο την δεδομένη ώρα
            //Δηλαδή την επόμενη μέρα
            plusDay=false;
            if (System.currentTimeMillis()>calendar.getTimeInMillis()) plusDay=true;
            if (plusDay) calendar.add(Calendar.DAY_OF_YEAR,1);

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
            hoursCur=date.getHours();
            minutesCur=date.getMinutes();


            DateCustomS=mTime.getText().toString();
            hours=Integer.valueOf(DateCustomS.substring(0,2));
            minutes=Integer.valueOf(DateCustomS.substring(3,5));

            calendar.setTimeInMillis(System.currentTimeMillis()); //NEW

            //Alarm Custom ώρα
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, 0);

            plusDay=false;
            if (System.currentTimeMillis()>calendar.getTimeInMillis()) plusDay=true;
            if (plusDay) calendar.add(Calendar.DAY_OF_YEAR,1);

            if (plusDay) Log.d("AlarmService","PlusDay");  else Log.d("AlarmService","Not PlusDay");

            Log.d("AlarmService","calendar time:"+String.valueOf(calendar.getTime().toString()));


            if (!HasSame(1,calendar))
            {
                NewRec(1, calendar);
                Toast.makeText(this, "Service is set", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(this, "Service is exists", Toast.LENGTH_SHORT).show();
        }

        //Κλήση στις 08:00 και 22:00
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
            hoursCur=date.getHours();
            minutesCur=date.getMinutes();

            hours=8;minutes=0;
            calendar.setTimeInMillis(System.currentTimeMillis()); //NEW


            //Alarm Πρωί 8:00
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            plusDay=false;
            if (System.currentTimeMillis()>calendar.getTimeInMillis()) plusDay=true;
            if (plusDay) calendar.add(Calendar.DAY_OF_YEAR,1);

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
            hoursCur=date.getHours();
            minutesCur=date.getMinutes();

            hours=22;minutes=0;
            calendar.setTimeInMillis(System.currentTimeMillis()); //NEW

            //Alarm βράδυ 22:00
            calendar.set(Calendar.HOUR_OF_DAY,22 );
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            plusDay=false;
            if (System.currentTimeMillis()>calendar.getTimeInMillis()) plusDay=true;
            if (plusDay) calendar.add(Calendar.DAY_OF_YEAR,1);

            if (!HasSame(1,calendar)) NewRec(1,calendar); else sameone=true;

            if (!sameone) Toast.makeText(this, "Service is set", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "One or more dates exists", Toast.LENGTH_SHORT).show();

        }

        //Κλήση στις 08:00,14:00,22:00
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
            hoursCur=date.getHours();
            minutesCur=date.getMinutes();

            hours=8;minutes=0;
            calendar.setTimeInMillis(System.currentTimeMillis()); //NEW

            //Alarm Πρωί 8:00
            calendar.set(Calendar.HOUR_OF_DAY,8 );
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            plusDay=false;
            if (System.currentTimeMillis()>calendar.getTimeInMillis()) plusDay=true;
            if (plusDay) calendar.add(Calendar.DAY_OF_YEAR,1);

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
            hoursCur=date.getHours();
            minutesCur=date.getMinutes();

            hours=14;minutes=0;
            calendar.setTimeInMillis(System.currentTimeMillis()); //NEW

            //Alarm Μεσημέρι 14:00
            calendar.set(Calendar.HOUR_OF_DAY,14 );
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            plusDay=false;
            if (System.currentTimeMillis()>calendar.getTimeInMillis()) plusDay=true;
            if (plusDay) calendar.add(Calendar.DAY_OF_YEAR,1);

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
            hoursCur=date.getHours();
            minutesCur=date.getMinutes();

            hours=22;minutes=0;
            calendar.setTimeInMillis(System.currentTimeMillis()); //NEW

            //Alarm Βράδυ 22:00
            calendar.set(Calendar.HOUR_OF_DAY,22 );
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            plusDay=false;
            if (System.currentTimeMillis()>calendar.getTimeInMillis()) plusDay=true;
            if (plusDay) calendar.add(Calendar.DAY_OF_YEAR,1);

            if (!HasSame(1,calendar)) NewRec(1,calendar); else sameone=true;

            if (!sameone) Toast.makeText(this, "Service is set", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "One or more dates exists", Toast.LENGTH_SHORT).show();
        }
    }


    //Διαβάζει τις εγγραφές υπηρεσιών alarms
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
            int numCharsRead = 0; //NEW
            char[] charArrayCity = new char[50]; //PREV
            char[] charArray1=new char[20];
            char[] charArray2=new char[1];
            OutText="";


            //ArraySize=(int)(fileSc.length()-1)/320;
            //FullscreenActivity.Global1.ArraySize=ArraySize;
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

                    //NEW
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

    //Έλενχος για σωστή ώρα
    public boolean checktime(String TimeS)
    {

        if (TimeS=="") return false;

        if (TimeS.length()==5)
        if ((TimeS.charAt(0) >= '0') && (TimeS.charAt(0) <= '9'))
        if ((TimeS.charAt(1) >= '0') && (TimeS.charAt(1) <= '9'))
        if (TimeS.charAt(2) == ':')
        if ((TimeS.charAt(3) >= '0') && (TimeS.charAt(3) <= '9'))
        if ((TimeS.charAt(4) >= '0') && (TimeS.charAt(4) <= '9'))
        return true;


        return false;
    }


    //Προσθέτει μια εγγραφή alarm service
    public void SaveService()
    {
        String SiteT;
        String CityT;
        String DaysT;
        String SiteIdT;
        String TimeT;
        String AlarmT;

        String datapath;
        datapath=getApplicationInfo().dataDir+"/Servicedat.txt";
        File fileSc = new File(datapath);

        SiteIdT=String.valueOf(Global1.ServiceData[ServicePos].siteId);
        SiteT=Global1.ServiceData[ServicePos].site;
        CityT=Global1.ServiceData[ServicePos].City;
        DaysT=String.valueOf(Global1.ServiceData[ServicePos].daysnum);
        TimeT=String.valueOf(Global1.ServiceData[ServicePos].time);
        AlarmT=String.valueOf(Global1.ServiceData[ServicePos].AlarmId);

        Log.d("AlarmService","Time Long:"+ConvertLongtoDate(Global1.ServiceData[ServicePos].time));

        //Γεμίζει με κενά τις μεταβλητές για να φτάσουν το μέγεθος του string array που χρησιμοποιείται παρακάτω
        for (i=SiteT.length();i<20;i++) SiteT+=" ";
        for (i=CityT.length();i<50;i++) CityT+=" ";
        for (i=TimeT.length();i<20;i++) TimeT+=" ";
        for (i=AlarmT.length();i<20;i++) AlarmT+=" ";


        try {
            //fileSc.createNewFile();
            FileWriter fileWriter = new FileWriter(fileSc,true); //Append File
            //FileWriter fileWriter = new FileWriter(fileSc); //New File
            fileWriter.write(SiteT, 0, 20);
            fileWriter.write(CityT, 0, 50);
            fileWriter.write(DaysT, 0, 1);
            fileWriter.write(SiteIdT, 0, 1);
            fileWriter.write(TimeT, 0, 20);
            fileWriter.write(AlarmT, 0, 20);

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