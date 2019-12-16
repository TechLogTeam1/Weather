package com.example.weather;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weather.FullscreenActivity.Global1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
//import com.example.weather.

/**
 * Created by Belal on 8/29/2017.
 */


//class extending the Broadcast Receiver
public class AlarmService extends BroadcastReceiver { //PREV
    //public class AlarmService extends AppCompatActivity {
    private String Units;
    private String UnitsTxt;
    private String Contents, OutText;
    private String CallUrl;
    private String City;
    private String Code, CodeTxt;
    private String CallCode;
    private String Coords;

    private String TempT, HumidityT, UnitsT, SiteT, CommentT;
    private String CurrentDateT;
    private String Temp1;
    private String Humidity1;
    private String Site1;
    private String Comment1;
    private String WeatherCon;
    private String WeatherCon1;
    private String WeatherConT;
    private String Temp;

    private int i,o;
    private int HistoryPos;


    private float Temperature;
    private float Humidity;
    private String HumidityTxt;

    private String APIOpen = "400b0e4928077be78efaf4523cd3a3b5";
    private String APIAccu = "GXj9XbCK7EOk5cVRnAOVN62PdDGJaTD6";
    private String APIDark = "e478e283b61f95dc70771be89db8ce1c";
    private String APIBit = "307702986d074885b6bdf41d74768e0c";
    private String APIStack = "516a194fae7c7db19dc99de1bfce0c6e\n";
    private String CurrentDate;
    private int CurrentHour;
    private int CurrentMinute;

    private String Longitude,Latitude,CityKey;
    private int ServicePos2;

    ProgressDialog progressDialog;

    int siteId;
    int siteIdArray[]=new int[1000];
    int daysnum;
    int daysnumArray[]=new int[1000];
    boolean hasdone;
    boolean hasdoneArray[]=new boolean[1000];
    int ServiceIdPos;
    boolean isOpen,isAccu,isStack,isDark,isBit;
    int isOpen1,isAccu1,isStack1,isDark1,isBit1;
    //private int year,month,day,hours,minutes;
    //private long rechour;
    //private long recminute;
    //private Calendar calendar;

    public void SaveHistory() {


        //Get Current Day
        String CurrentDate;
        String Path;
        float TempF, HumF;
        String PrevTemp, PrevHum, PrevCon, PrevSite;

        Date date = new Date();
        String strDateFormat = "dd-MM-yyyy HH:mm:ss z";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
        String formattedDate = dateFormat.format(date);
        CurrentDate = formattedDate;

        PrevTemp = TempT;
        PrevHum = HumidityT;
        PrevCon = WeatherConT;
        PrevSite = SiteT;

        TempT = Temp1;
        HumidityT = Humidity1;
        WeatherConT = WeatherCon1;
        SiteT = Site1;
        CommentT = Comment1;
        CurrentDateT = CurrentDate;

        for (i = SiteT.length(); i < 20; i++) SiteT += " ";
        for (i = City.length(); i < 50; i++) City += " ";
        for (i = TempT.length(); i < 10; i++) TempT += " ";
        for (i = HumidityT.length(); i < 10; i++) HumidityT += " ";
        for (i = WeatherConT.length(); i < 100; i++) WeatherConT += " ";
        for (i = CommentT.length(); i < 100; i++) CommentT += " ";
        for (i = CurrentDateT.length(); i < 29; i++) CurrentDateT += " ";

        Global1.HistoryData[HistoryPos] = new FullscreenActivity.HistoryDataClass();
        Global1.HistoryData[HistoryPos].Site = Site1;
        Global1.HistoryData[HistoryPos].City = City;
        Global1.HistoryData[HistoryPos].Date = CurrentDate;
        Global1.HistoryData[HistoryPos].Temperature = Float.valueOf(Temp1);
        Global1.HistoryData[HistoryPos].Humidity = Float.valueOf(Humidity1);
        Global1.HistoryData[HistoryPos].WeatherCon = WeatherCon1;
        Global1.HistoryData[HistoryPos].Units = Units;
        Global1.HistoryData[HistoryPos].Comment = Comment1;

        UnitsT = Units;

        int strlen;

        String datapath;
        //datapath=getApplicationInfo().dataDir+"/Weatherdat.txt";
        datapath = Global1.datapath;
        File fileSc = new File(datapath);

        try {


            //fileSc.createNewFile();

            FileWriter fileWriter = new FileWriter(fileSc, true); //Append File
            //FileWriter fileWriter = new FileWriter(fileSc); //New File

            fileWriter.write(SiteT, 0, 20);
            fileWriter.write(City, 0, 50);
            //fileWriter.write(CurrentDate, 0, 29); //HERE'S PROBLEM WITH MOBILE, DON'T RUN!!!
            fileWriter.write(CurrentDateT, 0, 29);
            fileWriter.write(TempT, 0, 10);
            fileWriter.write(HumidityT, 0, 10);
            fileWriter.write(WeatherConT, 0, 100);

            fileWriter.write(UnitsT, 0, 1);
            fileWriter.write(CommentT, 0, 100);


            fileWriter.flush();

            fileWriter.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        HistoryPos++;

        TempT = PrevTemp;
        HumidityT = PrevHum;
        WeatherConT = PrevCon;

        return;
    }

    /*
    public void GetCurTime()

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
        hours=date.getHours();
        minutes=date.getMinutes();

    }
*/


    @Override
    public void onReceive(Context context, Intent intent)
    {
        Global1.ServiceRun = true;
        //Units = "C";
        Units=Global1.Units;
        Code = "";
        String Action;
        Content content;
        boolean onetime;

        Log.d("AlarmService", "Alarm just fired"); //PREV

        intent=Global1.i; //NEW //CHECK

        Action=intent.getAction();

        //GetCurTime();

        //NEW
        //calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        //calendar.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));

        //for (i=0;i<=Global1.ServiceRecs-1;i++) //PREV
        for (i=0;i<=Global1.ServiceRecs;i++)
              if (Action.equals(String.valueOf(i))) {
                City = intent.getStringExtra("City");
                ServiceIdPos = intent.getIntExtra("ServicePos", 0);
                siteId=intent.getIntExtra("Site", 0);

                  //rechour=TimeUnit.MILLISECONDS.toHours(Global1.ServiceData[i].time);
                  //recminute=TimeUnit.MILLISECONDS.toMinutes(Global1.ServiceData[i].time);

                  /*
                  calendar.setTimeInMillis(Global1.ServiceData[i].time); //Check i here

                  //rechour=calendar.get(Calendar.HOUR); //12 hours format
                  rechour=calendar.get(Calendar.HOUR_OF_DAY); //24 hours format
                  recminute=calendar.get(Calendar.MINUTE);


                  //GMT +2:00 Athens
                  rechour-=2;
                  if (rechour==-1) rechour=23;
                  if (rechour==-2) rechour=22;

                  Log.d("AlarmService", "CurHours:" +hours);
                  Log.d("AlarmService", "Curminutes:" +minutes);

                  Log.d("AlarmService", "recHours:" +rechour);
                  Log.d("AlarmService", "recHinutes:" +recminute);

                if (hours==rechour)*/
                {
                    Log.d("AlarmService", "City:" + City);
                    Log.d("AlarmService", "Service Pos:" + String.valueOf(ServiceIdPos));
                    content = new Content(); //NEW POS
                    content.execute();
                }



            }

    }

    private class Content extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {


            if (siteId==1) ReadFromOpen();
            if (siteId==2) ReadFromAccu();
            if (siteId==3) ReadFromStack();
            if (siteId==4) ReadFromDark();
            if (siteId==5) ReadFromBit();

            Log.d("AlarmService", "Alarm just fired 2");


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //------------------------------------
            //Not Need to write nothing here
            //------------------------------------
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (OutText == "") {
                return;
            }
        }



        void ReadFromOpen() //PREV
        //public void ReadFromOpen()

        {
            if (Units == "C") UnitsTxt = "&units=metric";     //Celsius
            if (Units == "F") UnitsTxt = "&units=imperial";   //Fahrenheit
            if (Units == "K") UnitsTxt = "";                  //Kelvin

            if (Code.length() < 2) CodeTxt = "";
            else CodeTxt = "," + Code;

            //if (Period==1)
            try {
                CallUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + City + CodeTxt + UnitsTxt + "&APPID=" + APIOpen;
                //doc = Jsoup.connect(CallUrl).ignoreContentType(true).get();

                //CallUrl=""; //TMP

                Contents = "";
                Contents = Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();

                try {

                    JSONObject jsonObj = new JSONObject(Contents);
                    JSONObject obj2 = jsonObj.getJSONObject("coord");
                    JSONObject obj3 = jsonObj.getJSONObject("main");

                    JSONArray WeatherArray = jsonObj.getJSONArray("weather");
                    JSONObject json2 = WeatherArray.getJSONObject(0);

                    CallCode = jsonObj.getString("cod"); //404 = City not found
                    Coords = "Latitude:" + obj2.getString("lat");
                    Coords += "\n" + "Longitude:" + obj2.getString("lon");

                    //Save History
                    TempT = "Temperature:" + obj3.getString("temp") + " " + Units;
                    HumidityT = "Humidity:" + obj3.getString("humidity") + "%";
                    Temp1 = obj3.getString("temp");
                    Humidity1 = obj3.getString("humidity");
                    WeatherCon1 = json2.getString("main");
                    Site1 = "Open Weather Map(s)";
                    Comment1 = "None";
                    SaveHistory(); //PREV


                    Temp = "Temperature:" + obj3.getString("temp") + " " + Units;
                    HumidityTxt = "Humidity:" + obj3.getString("humidity") + "%";

                    Temperature = Float.valueOf(obj3.getString("temp"));
                    Humidity = Float.valueOf(obj3.getString("humidity"));
                    WeatherCon = json2.getString("main");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //OutText=Contents;
                OutText = "City:" + City + CodeTxt + "\n" + "---------------------------------------------" + "\n"
                        + Coords + "\n" + Temp + "\n" + HumidityTxt;
                //OutText=CallUrl;

            } catch (IOException e) {
                e.printStackTrace();
            }


            return;
        }

        void ReadFromAccu() {
            if (Units == "C") UnitsTxt = "&units=metric";     //Celsius
            if (Units == "F") UnitsTxt = "&units=imperial";   //Fahrenheit
            if (Units == "K") UnitsTxt = "";                  //Kelvin

            if (Code.length() < 2) CodeTxt = "";
            else CodeTxt = "," + Code;

            //CityKey="328328"; //London
            //CallUrl="http://dataservice.accuweather.com/locations/v1/cities/search?q=London&apikey=GXj9XbCK7EOk5cVRnAOVN62PdDGJaTD6";
            //CallUrl="http://dataservice.accuweather.com/locations/v1/cities/search?q="+City+"&apikey="+APIAccu;

            //First Call
            try {
                CallUrl = "http://dataservice.accuweather.com/locations/v1/cities/search?q=" + City + "&apikey=" + APIAccu;
                Contents = "";
                Contents = Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();
                Contents = Contents.substring(1, Contents.length());

                try {

                    JSONObject jsonObj = new JSONObject(Contents);
                    JSONObject obj2 = jsonObj.getJSONObject("GeoPosition");
                    CityKey = jsonObj.getString("Key");
                    Coords = "Latitude:" + obj2.getString("Latitude");
                    Coords += "\n" + "Longitude:" + obj2.getString("Longitude");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            //Second Call

            try {
                //CallUrl="http://api.openweathermap.org/data/2.5/weather?q="+City+CodeTxt+UnitsTxt+"&APPID="+API;
                //CallUrl="http://dataservice.accuweather.com/locations/v1/cities/search?q="+City+"&apikey="+APIAccu;
                CallUrl = "http://dataservice.accuweather.com/currentconditions/v1/" + CityKey + "?apikey=" + APIAccu + "&details=true";

                Contents = Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();
                Contents = Contents.substring(1, Contents.length());
                try {
                    JSONObject jsonObj = new JSONObject(Contents);
                    JSONObject obj2 = jsonObj.getJSONObject("Temperature");
                    JSONObject obj3;

                    if (Units == "C")
                        obj3 = obj2.getJSONObject("Metric");
                    else if (Units == "F")
                        obj3 = obj2.getJSONObject("Imperial");
                    else
                        obj3 = obj2.getJSONObject("Metric");

                    Temp = "Temperature:" + obj3.getString("Value") + " " + Units;
                    HumidityTxt = "Humidity:" + jsonObj.getString("RelativeHumidity") + "%";

                    Temperature = Float.valueOf(obj3.getString("Value"));
                    Humidity = Float.valueOf(jsonObj.getString("RelativeHumidity"));

                    WeatherCon = jsonObj.getString("WeatherText");

                    Temp1 = obj3.getString("Value");
                    Humidity1 = jsonObj.getString("RelativeHumidity");
                    WeatherCon1 = jsonObj.getString("WeatherText");
                    Site1 = "AccuWeather(s)";
                    Comment1 = "None";
                    SaveHistory();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //"WeatherText"
                //OutText=WeatherText;
                //OutText=Contents;

                OutText = "City:" + City + CodeTxt + "\n" + "---------------------------------------------" + "\n"
                        + Coords + "\n" + Temp + "\n" + HumidityTxt;

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
                OutText=Latitude+","+Longitude;
                //OutText="City:"+City+CodeTxt+"\n"+"---------------------------------------------"+"\n"
                //      +Coords+"\n"+Temp+"\n"+Humidity;
                //OutText=CallUrl;

            } catch (IOException e) {
                e.printStackTrace();
            }


            //Second call (Call DarkSky)
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
                    //WeatherConIcon=obj2.getString("icon"); //Maybe not needed here

                    Temp1=obj2.getString("temperature");
                    Humidity1=String.valueOf(Float.valueOf(obj2.getString("humidity"))*100);
                    WeatherCon1=obj2.getString("summary");
                    Site1="Dark Sky(s)";
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





            return;
        }



        void ReadFromBit()
        {
            //if (Units=="C") UnitsTxt="&units=M";   //Celsius
            if (Units=="C") UnitsTxt="";   //Celsius (Default)
            if (Units=="F") UnitsTxt="&units=I";   //Fahrenheit
            if (Units=="K") UnitsTxt="&units=S";   //Kelvin

            if (Code.length()<2) CodeTxt=""; else CodeTxt=","+Code;

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

                    Temp1=json2.getString("temp");
                    Humidity1=json2.getString("rh");
                    WeatherCon1=obj2.getString("description");
                    Site1="Weatherbit.io(s)";
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



        void ReadFromStack() {
            if (Units == "C") UnitsTxt = "&units=m";   //Celsius
            if (Units == "F") UnitsTxt = "&units=f";   //Fahrenheit
            if (Units == "K") UnitsTxt = "&units=s";   //Kelvin

            if (Code.length() < 2) CodeTxt = "";
            else CodeTxt = "," + Code;
            //http://api.weatherstack.com/current?access_key=e4c1390e4110c5a78e89c99f56b94b08&query=London

            try {
                //CallUrl="http://api.openweathermap.org/data/2.5/weather?q="+City+CodeTxt+UnitsTxt+"&APPID="+APIOpen;
                //CallUrl="http://api.weatherstack.com/current?access_key=e4c1390e4110c5a78e89c99f56b94b08&query=London";


                CallUrl = "http://api.weatherstack.com/current?access_key=" + APIStack + "&query=" + City + UnitsTxt;


                //doc = Jsoup.connect(CallUrl).ignoreContentType(true).get();
                Contents = "";
                Contents = Jsoup.connect(CallUrl).ignoreContentType(true).execute().body();

                try {
                    JSONObject jsonObj = new JSONObject(Contents);

                    JSONObject obj2 = jsonObj.getJSONObject("location");
                    JSONObject obj3 = jsonObj.getJSONObject("current");


                    //CallCode=jsonObj.getString("cod"); //404 = City not found
                    Coords = "Latitude:" + obj2.getString("lat");
                    Coords += "\n" + "Longitude:" + obj2.getString("lon");
                    Temp = "Temperature:" + obj3.getString("temperature") + " " + Units;
                    HumidityTxt = "Humidity:" + obj3.getString("humidity") + "%";

                    Temperature = Float.valueOf(obj3.getString("temperature"));
                    Humidity = Float.valueOf(obj3.getString("humidity"));
                    WeatherCon = obj3.getString("weather_descriptions");
                    WeatherCon = WeatherCon.substring(2, WeatherCon.length() - 2);

                    Temp1 = obj3.getString("temperature");
                    Humidity1 = obj3.getString("humidity");
                    WeatherCon1 = obj3.getString("weather_descriptions");
                    WeatherCon1 = WeatherCon1.substring(2, WeatherCon1.length() - 2);
                    Site1 = "Weather Stacks(s)";
                    Comment1 = "None";
                    SaveHistory();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //OutText=Contents;
                //OutText=Humidity;

                OutText = "City:" + City + CodeTxt + "\n" + "---------------------------------------------" + "\n"
                        + Coords + "\n" + Temp + "\n" + Humidity;
                //OutText=CallUrl;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }



    } //NEW
} //NEW