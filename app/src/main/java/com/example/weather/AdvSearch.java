package com.example.weather;

import com.example.weather.FullscreenActivity.Global1;
import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AdvSearch extends AppCompatActivity {


    private Button mButtonSel;
    private Button mButtonDis;
    private Button mButtonClear;
    private Button mButtonOK;
    private EditText mFromTemp,mToTemp;

    private CheckBox mCheckClear;
    private CheckBox mCheckClouds;
    private CheckBox mCheckRain;
    private CheckBox mCheckSnow;
    private CheckBox mCheckThunder;

    private RadioButton mRadio1;
    private RadioButton mRadio2;
    private RadioButton mRadio3;
    private String Units;
    private float FromT,ToT;


    //Έλενχος για στωστή θερμοκρασία
    //public boolean checkTemperature(String TempS)
    public static boolean checkTemperature(String TempS)
    {

        int i;
        int digits;
        boolean acceptvalue;
        int minusNum,commasNum,zerosNum;
        int commaPos;
        boolean numGived;


        digits=0;
        minusNum=0;
        commasNum=0;
        zerosNum=0;
        numGived=false;

        acceptvalue=false;
        if (TempS=="") return false;

        for (i=0;i<TempS.length();i++)
        {

            acceptvalue=false;

            if (TempS.charAt(i) == '-') minusNum++;
            if (TempS.charAt(i) == '.') {commaPos=i;commasNum++;}


            if (commasNum==0)
            if ((TempS.charAt(i)>'0') && (TempS.charAt(i)<='9')) numGived=true;

            if (commasNum==0)
                if (TempS.charAt(i) == '0') zerosNum++;


            if (((TempS.charAt(i) >= '0') && (TempS.charAt(i) <= '9')) ||
                    ((TempS.charAt(i) == '-') || (TempS.charAt(i) == '.')))
            {
                acceptvalue = true;
                digits++;
            }

            if (!acceptvalue) return false;
        }

        if (!numGived)
        if (zerosNum>1) return false;

        if (minusNum>1) return false;
        if (commasNum>1) return false;

        if ((TempS.contains("-")) && (TempS.charAt(0)!='-')) return false;
        if (digits!=TempS.length()) return false;

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_adv_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mButtonSel= (Button) findViewById(R.id.buttonSelAll1);
        mButtonDis= (Button) findViewById(R.id.buttonDisAll1);
        mButtonClear= (Button) findViewById(R.id.buttonClear);
        mButtonOK= (Button) findViewById(R.id.buttonOK);

        mFromTemp= (EditText) findViewById(R.id.editTextFromT);
        mToTemp= (EditText) findViewById(R.id.editTextToT);

        mCheckClear=(CheckBox)findViewById(R.id.checkBox7);
        mCheckClouds=(CheckBox)findViewById(R.id.checkBox8);
        mCheckRain=(CheckBox)findViewById(R.id.checkBox9);
        mCheckSnow=(CheckBox)findViewById(R.id.checkBox10);
        mCheckThunder=(CheckBox)findViewById(R.id.checkBox11);

        mRadio1= (RadioButton) findViewById(R.id.radioButton12);
        mRadio2= (RadioButton) findViewById(R.id.radioButton13);
        mRadio3= (RadioButton) findViewById(R.id.radioButton14);

        mFromTemp.setText(String.valueOf(Global1.FromT));
        mToTemp.setText(String.valueOf(Global1.ToT));

        //Θέτει τις τιμές των checkboxes
        if (Global1.Clear) mCheckClear.setChecked(true); else mCheckClear.setChecked(false);
        if (Global1.Clouds) mCheckClouds.setChecked(true); else mCheckClouds.setChecked(false);
        if (Global1.Rain) mCheckRain.setChecked(true); else mCheckRain.setChecked(false);
        if (Global1.Snow) mCheckSnow.setChecked(true); else mCheckSnow.setChecked(false);
        if (Global1.Thunder) mCheckThunder.setChecked(true); else mCheckThunder.setChecked(false);

        mRadio1.setChecked(false); mRadio2.setChecked(false); mRadio3.setChecked(false);

        if (Global1.Units2=="C") mRadio1.setChecked(true);
        if (Global1.Units2=="F") mRadio2.setChecked(true);
        if (Global1.Units2=="K") mRadio3.setChecked(true);

        Units=Global1.Units2;

        //Διαβάζει και περνάει στην μνήμη τις τιμές των checkboxes όταν κάποιο/κάποια απο τα checkboxes αλλάξουν
        mCheckClear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Global1.Clear=isChecked;
            }
        });

        mCheckClouds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Global1.Clouds=isChecked;
            }
        });

        mCheckRain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Global1.Rain=isChecked;
            }
        });

        mCheckSnow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Global1.Snow=isChecked;
            }
        });

        mCheckThunder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Global1.Thunder=isChecked;
            }
        });

        //Ενεργοποιεί όλα τα checkboxes
        mButtonSel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCheckClear.setChecked(true);
                mCheckClouds.setChecked(true);
                mCheckRain.setChecked(true);
                mCheckSnow.setChecked(true);
                mCheckThunder.setChecked(true);
            }
        });

        //Απενεργοποιεί όλα τα checkboxes
        mButtonDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckClear.setChecked(false);
                mCheckClouds.setChecked(false);
                mCheckRain.setChecked(false);
                mCheckSnow.setChecked(false);
                mCheckThunder.setChecked(false);
            }
        });

        //Θετει τις default τιμές
        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCheckClear.setChecked(true);
                mCheckClouds.setChecked(true);
                mCheckRain.setChecked(true);
                mCheckSnow.setChecked(true);
                mCheckThunder.setChecked(true);
                Global1.FromT=(float)-273.15;
                Global1.ToT=400;
                mFromTemp.setText("-273.15");
                mToTemp.setText("400.0");
            }
        });

        //Αφού όλες οι παράμετροι είναι σωστές, βγαίνει απο το παρόν activity
        mButtonOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                //Έλενχος για θερμοκρασία απο
                if (!checkTemperature(mFromTemp.getText().toString()))
                {
                    Toast.makeText(AdvSearch.this, "Wrong Temperature from", Toast.LENGTH_SHORT).show();
                    if (Units=="C") Global1.FromT=FromT;
                    if (Units=="F") Global1.FromT=(FromT-32)/(float)1.8;
                    if (Units=="K") Global1.FromT=FromT-(float) 273.15;
                    return;
                }

                FromT=Float.valueOf(mFromTemp.getText().toString());
                Global1.FromT=FromT;

                //Έλενχος για θερμοκρασία προς
                if (!checkTemperature(mToTemp.getText().toString()))
                {
                    Toast.makeText(AdvSearch.this, "Wrong Temperature To", Toast.LENGTH_SHORT).show();
                    if (Units=="C") Global1.ToT=ToT;
                    if (Units=="F") Global1.ToT=(ToT-32)/(float)1.8;
                    if (Units=="K") Global1.ToT=ToT-(float) 273.15;

                    Global1.ToT=ToT;
                    return;
                }

                ToT=Float.valueOf(mToTemp.getText().toString());
                Global1.ToT=ToT;

               finish(); //Επιστροφή στο προηγούμενο activity

            }
        });


        //Επιλέγει την μονάδα μετρήσης των βαθμών
        mRadio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio1.setChecked(true);
                mRadio2.setChecked(false);
                mRadio3.setChecked(false);
                Units="C";Global1.Units2="C";
            }
        });

        mRadio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio1.setChecked(false);
                mRadio2.setChecked(true);
                mRadio3.setChecked(false);
                Units="F";Global1.Units2="F";
            }
        });
        mRadio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mRadio1.setChecked(false);
                mRadio2.setChecked(false);
                mRadio3.setChecked(true);
                Units="K";Global1.Units2="K";
            }
        });

    }



}
