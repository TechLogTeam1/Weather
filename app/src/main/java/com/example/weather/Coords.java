package com.example.weather;

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

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Coords extends AppCompatActivity {

    private Button mButtonSave;
    private Button mButtonRecs;
    private EditText mCoords,mName;
    private String Name1;
    private int i;
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


        return;
    }



    public void SaveName()
    {
        int commapos;
        boolean samename;

        Name1=mName.getText().toString();

        ReadCoords(); //NEW //CHECK


        samename=false;
        for (i=0;i<CoordsPos;i++)
            if (FullscreenActivity.Global1.CoordsNamesData[i].Name.toLowerCase().equalsIgnoreCase(Name1)) samename=true;

        if (samename) {
            Toast.makeText(this, "This name already exists !", Toast.LENGTH_SHORT).show();
            return;
        }

        CoordsStr=mCoords.getText().toString();

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