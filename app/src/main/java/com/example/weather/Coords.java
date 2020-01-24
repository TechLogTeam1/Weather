package com.example.weather;

import com.example.weather.FullscreenActivity.Global1;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Coords extends AppCompatActivity {

    private Button mButtonSave;
    private Button mButtonRecs;
    private EditText mCoords,mName;
    private String Name1;
    //private int i;
    private static int i;
    double lat,lon;
    private int CoordsPos;
    String OutText;

    String NameT;
    String CoordsStr;
    String LatStr,LonStr;
    String LatStrT,LonStrT;
    private int posStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_coords);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mCoords= (EditText) findViewById(R.id.editTextCoords);
        mName= (EditText) findViewById(R.id.editTextName);

        mButtonSave= (Button) findViewById(R.id.buttonSave);
        mButtonRecs= (Button) findViewById(R.id.buttonRecs);

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SaveName();
            }
        });

        mButtonRecs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ReadCoords();
                FullscreenActivity.Global1.Period=12;
                FullscreenActivity.Global1.Contents=OutText;
                Intent intent = new Intent(Coords.this, WeatherDetails.class);
                startActivity(intent);

            }
        });


    }

    //Διαβάζει την λίστα των ονομάτων τοποθεσιών συντεταγμένων
    public void ReadCoords()

    {
        String datapath,strdata;
        char[] scArray = new char[29];
        datapath=getApplicationInfo().dataDir+"/Coords.txt";
        int o;
        int stopPos;

        String SiteT;
        String CityT;
        String DaysT;
        String SideIdT;
        String TimeT;

        CoordsPos=0;


        try {
            File fileSc = new File(datapath);
            //fileSc.delete(); //TMP
            //fileSc.createNewFile();//TMP
            FileReader fileReader = new FileReader(fileSc);
            StringBuffer stringBuffer = new StringBuffer();
            int numCharsRead = 0; //NEW
            char[] charArrayName = new char[50]; //PREV
            char[] charArray1=new char[15];
            OutText="";



            if (fileSc.length()!=0) //NEW NEEDED HERE
                for (i=0;i<=(fileSc.length()-1)/80;i++)
                {
                    SiteT="";CityT="";DaysT="";SideIdT="";TimeT="";


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

                    OutText+="Name:"+ FullscreenActivity.Global1.CoordsNamesData[CoordsPos-1].Name+"\n"+
                            "Latitude:"+FullscreenActivity.Global1.CoordsNamesData[CoordsPos-1].lat+"\n"+
                            "Longitute:"+FullscreenActivity.Global1.CoordsNamesData[CoordsPos-1].lon+"\n"+
                            "_____________________________\n";

                }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        Global1.CoordsRecs=CoordsPos;
        return;
    }

    //Έλενχος για στωστές συντεταγμένες
    //public boolean checkcoords(String CoordsS)
    public static boolean checkcoords(String CoordsS)
    {

        int digits;
        int commasnum,commapos;
        int minusnum;
        int dotsnum;
        int dot1,dot2;
        int zerosnum;

        boolean acceptvalue,minusAccept,Dotcont;

        digits=0;
        commasnum=0;
        minusnum=0;
        commapos=0;
        dotsnum=0;
        dot1=0;
        dot2=0;
        zerosnum=0;

        acceptvalue=false;
        minusAccept=false;
        Dotcont=false;
        if (CoordsS=="") return false;

        for (i=0;i<CoordsS.length();i++)
        {

            acceptvalue=false;
            if (((CoordsS.charAt(i) >= '0') && (CoordsS.charAt(i) <= '9')) ||
                    ((CoordsS.charAt(i) == '-') || (CoordsS.charAt(i) == '.') || (CoordsS.charAt(i) == ',')))
            {
                if (CoordsS.charAt(i)==',') {commasnum++;commapos=i;}

                if (CoordsS.charAt(i)=='-') minusnum++;
                if (CoordsS.charAt(i)=='.')
                {
                    if (i==0) return false;

                    if ((CoordsS.charAt(i-1)>='0') && (CoordsS.charAt(i-1)<='9')) Dotcont=true; else Dotcont=false;

                if (!Dotcont) return false;

                    dotsnum++;
                }


                if (i==1)
                    if (CoordsS.charAt(i) == '0')
                    if (CoordsS.charAt(i-1) == '0') return false;
                if (i==commapos+2)
                    if (CoordsS.charAt(i) == '0')
                    if (CoordsS.charAt(i-1) == '0') return false;


                if (commasnum==0) if (CoordsS.charAt(i)=='.') dot1++;
                if (commasnum==1) if (CoordsS.charAt(i)=='.') dot2++;

                acceptvalue = true;
                digits++;
            }

            if (!acceptvalue) return false;
        }

        if (minusnum==1)
        {
            if ((CoordsS.charAt(0)=='-') || (CoordsS.charAt(commapos+1)=='-')) minusAccept=true;
        }

        if (minusnum==2)
        {
            if ((CoordsS.charAt(0)=='-') && (CoordsS.charAt(commapos+1)=='-')) minusAccept=true;
        }


        if ((minusnum>0) && (!minusAccept)) return false;
        if (commasnum>1) return false;
        if (minusnum>2) return false;
        if (dotsnum>2) return false;
        if ((dot1>1) || (dot2>1)) return false;
        if (commapos==0) return false;
        if (commapos==CoordsS.length()-1) return false;
        if (digits!=CoordsS.length()) return false;

        return true;
    }



    //Σώζει μια καταχώρηση ονόματος συντεταγμένων
    public void SaveName()
    {
        int commapos;
        boolean samename;

        Name1=mName.getText().toString();

        ReadCoords(); //NEW //CHECK

        //Έλενχος αν είναι το όνομα κενό
        if (Name1.isEmpty())
        {
            Toast.makeText(this, "The name is empty !", Toast.LENGTH_SHORT).show();
            return;
        }

        //Έλεγχος αν υπάρχει ήδη εγγραφή με αυτό το όνομα
        samename=false;
        for (i=0;i<CoordsPos;i++)
            if (FullscreenActivity.Global1.CoordsNamesData[i].Name.toLowerCase().equalsIgnoreCase(Name1)) samename=true;

        if (samename) {
            Toast.makeText(this, "This name already exists !", Toast.LENGTH_SHORT).show();
            return;
        }

        CoordsStr=mCoords.getText().toString();


        //Έλενχος των συντεταγμένων
        if (!checkcoords(CoordsStr))
        {
            Toast.makeText(Coords.this, "Wrong Coordinates", Toast.LENGTH_SHORT).show();
            return;
        }


        //Δίαβσσμα των συντεταγμένων και τοποθέτηση τους se double μεταβλητές
        commapos=0;

        for (i = 0; i <= CoordsStr.length(); i++)
            if (CoordsStr.charAt(i) == ',')
            {
                commapos = i;
                break;
            }

        LatStr = CoordsStr.substring(0, commapos);
        LonStr = CoordsStr.substring(commapos + 1, CoordsStr.length());
        lat = Double.valueOf(LatStr);
        lon = Double.valueOf(LonStr);



        NameT=Name1;
        LatStrT=LatStr;
        LonStrT=LonStr;

        for (i=NameT.length();i<50;i++) NameT+=" ";
        for (i=LatStrT.length();i<15;i++) LatStrT+=" ";
        for (i=LonStrT.length();i<15;i++) LonStrT+=" ";

        //Καταχώρηση καινούργιας εγγραφής
        FullscreenActivity.Global1.CoordsNamesData[CoordsPos]=new FullscreenActivity.CoordsNames();
        FullscreenActivity.Global1.CoordsNamesData[CoordsPos].Name=Name1;
        FullscreenActivity.Global1.CoordsNamesData[CoordsPos].lat=lat;
        FullscreenActivity.Global1.CoordsNamesData[CoordsPos].lon=lon;

        int strlen;

        String datapath;
        datapath=getApplicationInfo().dataDir+"/Coords.txt";

        File fileSc = new File(datapath);

        try {

            //fileSc.createNewFile();
            FileWriter fileWriter = new FileWriter(fileSc,true); //Append File
            //FileWriter fileWriter = new FileWriter(fileSc); //New File

            fileWriter.write(NameT, 0, 50);
            fileWriter.write(LatStrT, 0, 15);
            fileWriter.write(LonStrT, 0, 15);

            fileWriter.flush();

            fileWriter.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        CoordsPos++;

        Toast.makeText(this, "New Coordinates Name Saved", Toast.LENGTH_SHORT).show();
        return;
    }



}