package com.transposesolutions.unitconverter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class Area extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Declare the instance variable Ad view
    private AdView adView;
    private ActionBarDrawerToggle mToggle;
    EditText UserInput;
    TextView result1,result2,result3,result4,result5,result6,result7,result8,result9,result10,result11;
    Spinner selector_1;
    double x=0;
    double userValue1=0;
    String selectedItem;
    DecimalFormat decimalFormat = new DecimalFormat("#.######");
    DecimalFormat decimalScientificFormat = new DecimalFormat("#.#####E0");
    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            if (editable.length() == 0){
                result1.setText("");
                result2.setText("");
                result3.setText("");
                result4.setText("");
                result5.setText("");
                result6.setText("");
                result7.setText("");
                result8.setText("");
                result9.setText("");
                result10.setText("");
                result11.setText("");
            }else{
                checkForChanges1();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        //UI elements
        UserInput = findViewById(R.id.user_input);
        selector_1 = findViewById(R.id.selector_1);
        result1 = findViewById(R.id.Result_1);
        result2 = findViewById(R.id.Result_2);
        result3 = findViewById(R.id.Result_3);
        result4 = findViewById(R.id.Result_4);
        result5 = findViewById(R.id.Result_5);
        result6 = findViewById(R.id.Result_6);
        result7 = findViewById(R.id.Result_7);
        result8 = findViewById(R.id.Result_8);
        result9 = findViewById(R.id.Result_9);
        result10 = findViewById(R.id.Result_10);
        result11 = findViewById(R.id.Result_11);

        // Load an ad into the AdMob banner view.
        FrameLayout adContainerView = findViewById(R.id.ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
        adContainerView.addView(adView);
        loadBanner();
        // Load Navigation View.
        DrawerLayout mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        // to perform the default logic on create
        int defaultValue1 = 1;
        String displayValue1 = String.valueOf(defaultValue1);
        UserInput.setText(displayValue1);
        // set listeners
        UserInput.addTextChangedListener(mTextWatcher);
        checkForChanges1();
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_area_items, R.layout.spinner_selector_items);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        // Apply the adapter to the spinner
        selector_1.setAdapter(adapter);
        selector_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);
                String activityTextValue = UserInput.getText().toString();
                try {
                    userValue1 = Double.parseDouble(activityTextValue);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (position == 0) {
                    acre();
                } else if (position == 1) {
                    Hectare();
                } else if (position == 2) {
                    SquareCentiMeter();
                } else if (position == 3) {
                    SquareDeciMeter();
                } else if (position == 4) {
                    SquareFoot();
                } else if (position == 5) {
                    SquareInch();
                } else if (position == 6) {
                    SquareKilometer();
                } else if (position == 7) {
                    SquareMeter();
                } else if (position == 8) {
                    SquareMile();
                } else if (position == 9) {
                    SquareMilliMeter();
                } else if (position == 10) {
                    SquareYard();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void checkForChanges1() {
        // First get the edit text value from the activity
        String activityTextValue1 = UserInput.getText().toString();
        // convert double
        try {
            userValue1 =  Double.parseDouble(activityTextValue1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String spinnerValue = String.valueOf(selector_1.getSelectedItem());
        switch (spinnerValue) {
            case "Acre (ac)":
                x = 1;
                break;
            case "Hectare (ha)":
                x = 2;
                break;
            case "Square Centimeter (cm2)":
                x = 3;
                break;
            case "Square Decimeters (dm2)":
                x = 4;
                break;
            case "Square Foot (ft2)":
                x = 5;
                break;
            case "Square Inch (in2)":
                x = 6;
                break;
            case "Square Kilometer (km2)":
                x = 7;
                break;
            case "Square Meter (m2)":
                x = 8;
                break;
            case "Square Mile (mi2)":
                x = 9;
                break;
            case "Square Millimeter (mm2)":
                x = 10;
                break;
            case "Square Yard (yd2)":
                x = 11;
                break;
            default:
                break;
        }
        if(x==1){
            acre();
        }else if(x==2){
            Hectare();
        }else if(x==3){
            SquareCentiMeter();
        }else if(x==4){
            SquareDeciMeter();
        }else if(x==5){
            SquareFoot();
        }else if (x==6){
            SquareInch();
        }else if(x==7){
            SquareKilometer();
        }else if (x==8){
            SquareMeter();
        }else if(x==9){
            SquareMile();
        }else if(x==10){
            SquareMilliMeter();
        }else if(x==11){
            SquareYard();
        }
    }

    private void SquareYard() {
        // SquareYard to acre
        // example Yard=1; Acre= Yard*0.0002066116; Result=0.0002066116
        double valueSelector1 =  (userValue1 * 0.000207);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // SquareYard to Hectare
        // example Yard=1 , Hectare= Yard* 0.0000836127; Result= 8.3613e-5
        double valueSelector2 =  (userValue1 *(8.3613* Math.pow(10, -5)));
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //SquareYard to SquareCentiMeter
        // example Yard=1 , CentiMeter= Yard*8361.2736; Result=8361.2736
        double valueSelector3 =  (userValue1 *8361.2736);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //SquareYard to SquareDeciMeter
        // example Yard=1 ,DeciMeter= Yard*83.612736; Result=83.612736
        double valueSelector4 = (userValue1 * 83.612736);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //SquareYard toSquare Foot
        // example Yard=1 ,  Foot= Yard*9; Result=9
        int valueSelector5 = (int) (userValue1 * 9);
        String _valueSelector5 = String.valueOf(valueSelector5);
        result5.setText(_valueSelector5);
        //SquareYard to SquareInch
        // example Yard=1 ,  Inch= Yard*1296; Result=1296
        int valueSelector6 = (int) (userValue1 * 1296);
        String _valueSelector6 = String.valueOf(valueSelector6);
        result6.setText(_valueSelector6);
        //SquareYard to SquareKilo Meter
        // example Yard=1 ,  Kilo_Meter= Yard*8.361273599E-7; Result=8.3613e-7
        double valueSelector7 = (userValue1 *(8.3613* Math.pow(10, -7)));
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //SquareYard to  SquareMeter
        // example Yard=1 ,  Meter= Yard*0.83612736; Result=0.83612736
        double valueSelector8 =  (userValue1 * 0.836127);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //SquareYard toSquare Mile
        // example Yard=1 ,  Mile= Yard*3.228305785E-7; Result=3.228305785E-7
        double valueSelector9 =  (userValue1 *(3.2283* Math.pow(10, -7)));
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //SquareYard to Squaremilli meter
        // example Yard=1 ,  milli_meter= Yard* 836127.36;  Result= 836127.36
        double valueSelector10 =  (userValue1 * 836127.36);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //SquareYard to SquareYard
        // example Yard=1 ,  Yard= Yard*1; Result=1
        int valueSelector11 = (int) (userValue1 *  1);
        String _valueSelector11 = String.valueOf(valueSelector11);
        result11.setText(_valueSelector11);


    }
    private void SquareMilliMeter() {
        // SquareMilliMeter to acre
        // ex :-  MilliMeter = 1  acre= MilliMeter *2.471053814E-10; Result = 2.4711e-10
        double valueSelector1 = (userValue1 * (2.4711*Math.pow(10,-10)));
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Square  MilliMeter to Hectare
        // ex :-  MilliMeter = 1  Hectare= MilliMeter * 1e-10; Result =  1e-10
        double valueSelector2 =  (userValue1 * (1*Math.pow(10,-10)));
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Square MilliMeter to SquareCentiMeter
        // ex :-  MilliMeter = 1  CentiMeter= MilliMeter *  0.01; Result =  0.01
        double valueSelector3 =  (userValue1 *  0.01);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Square MilliMeter toSquare DeciMeter
        // ex :-  MilliMeter = 1  DeciMeter= MilliMeter * 0.0001; Result =  0.0001
        double valueSelector4 = (userValue1 * 0.0001);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Square MilliMeter toSquare  Foot
        // ex :-  MilliMeter = 1  Foot= MilliMeter * 0.0000107639; Result =  0.000010764
        double valueSelector5 =  (userValue1 / 92903);
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Square MilliMeter to Square  Inch
        // ex :-  MilliMeter = 1  Inch= MilliMeter * 0.0015500031; Result = 0.00155
        double valueSelector6 =  (userValue1 * 0.00155);
        String _valueSelector6 = String.valueOf(valueSelector6);
        result6.setText(_valueSelector6);
        //Square MilliMeter to Square Kilo Meter
        // ex :-  MilliMeter = 1  Kilo_Meter= MilliMeter *  1e-12; Result =   1e-12
        double valueSelector7 =  (userValue1 * (1*Math.pow(10,-12)));
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Square MilliMeter to Square Meter
        // ex :-  MilliMeter = 1  Meter= MilliMeter * 0.000001; Result =  1e+6
        double valueSelector8 =  (userValue1 * (1*Math.pow(10,6)));
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Square MilliMeter to  Square Mile
        // ex :-  MilliMeter = 1  Mile= MilliMeter * 3.861021585E-13; Result =3.861e-13
        double valueSelector9 = (userValue1 * (3.861*Math.pow(10,-13)));
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Square MilliMeter to Square MilliMeter
        // ex :-  MilliMeter = 1  MilliMeter= MilliMeter * 1; Result =  1
        int valueSelector10 = (int) (userValue1 * 1);
        String _valueSelector10 = String.valueOf(valueSelector10);
        result10.setText(_valueSelector10);
        //Square MilliMeter to SquareYard
        // ex :-  MilliMeter = 1  Yard= MilliMeter * 0.000001196; Result =  1.196E-6
        double valueSelector11 =  (userValue1 * ( 1.196*Math.pow(10,-6)));
        String _valueSelector11 = decimalScientificFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }
    private void SquareMile() {
        // Square Mile to acre
        // ex :-  Mile = 1  acre= Mile * 640; Result = 640
        int valueSelector1 = (int) (userValue1 * 640);
        String _valueSelector1 = String.valueOf(valueSelector1);
        result1.setText(_valueSelector1);
        // Square Mile to Hectare
        // ex :-  Mile = 1  Hectare= Mile * 258.99881103; Result = 258.99881103
        double valueSelector2 =  (userValue1 *258.998811);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Square Mile toSquare  CentiMeter
        // ex :-  Mile = 1  CentiMeter= Mile * 25899881103; Result = 2.59e+10
        double valueSelector3 = userValue1 * 25899881103.36;
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Square Mile to SquareDeciMeter
        // ex :-  Mile = 1  DeciMeter= Mile * 258998811.03; Result = 2.59e+8
        double valueSelector4 = (userValue1 * 258998811.0336);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Square Mile to SquareFoot
        // ex :-  Mile = 1  Foot= Mile *  27878400; Result =  2.788e+7
        double valueSelector5 =  (userValue1 * 27878400);
        String _valueSelector5 = String.valueOf(valueSelector5);
        result5.setText(_valueSelector5);
        //Square Mile to Square Inch
        // ex :-  Mile = 1  Inch= Mile *4014489600; Result = 4.014e+9
        double valueSelector6 =  (userValue1 *( 4.014*Math.pow(10,9)));
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //SquareMile to Square Kilo Meter
        // ex :-  Mile = 1  Kilo_Meter= Mile *2.5899881103; Result = 2.59
        double valueSelector7 =  (userValue1 * 2.589988);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //SquareMile to  SquareMeter
        // ex :-  Mile = 1  Meter= Mile * 2589988.1103; Result = 2.59e+6
        double valueSelector8 =  (userValue1 * 2.59e+6);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Square Mile toSquare  Mile
        // ex :-  Mile = 1  Mile= Mile * 1; Result = 1
        int valueSelector9 = (int) (userValue1 * 1);
        String _valueSelector9 = String.valueOf(valueSelector9);
        result9.setText(_valueSelector9);
        //SquareMile to Squaremilli meter
        // ex :-  Mile = 1  milli_meter= Mile * 2589988110336; Result = 2.59e+12
        double valueSelector10 =  (userValue1 * (2.5900*Math.pow(10,12)));
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //Square Mile to Square Yard
        // ex :-  Mile = 1  Yard= Mile * 3097600; Result = 3.098e+6
        double valueSelector11 =  (userValue1 * 3097600);
        String _valueSelector11 = decimalScientificFormat.format(valueSelector11);
        result11.setText(_valueSelector11);

    }
    private void SquareMeter() {
        // Square Meter to acre
        // ex :-  Meter = 1  Acre= Meter * 0.0002471054; Result = 0.000247105
        double valueSelector1 =  (userValue1 *0.000247);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Square Meter to Hectare
        // ex :-  Meter = 1  Hectare= Meter * 0.0001; Result = 1e-4
        double valueSelector2 =  (userValue1 * 0.0001);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //SquareMeter to SquareCentiMeter
        // ex :-  Meter = 1  CentiMeter= Meter * 10000; Result = 10000
        int valueSelector3 = (int) (userValue1 * 10000);
        String _valueSelector3 = String.valueOf(valueSelector3);
        result3.setText(_valueSelector3);
        //Square Meter to Square DeciMeter
        // ex :-  Meter = 1  DeciMeter= Meter * 100; Result = 100
        int valueSelector4 = (int) (userValue1 * 100);
        String _valueSelector4 = String.valueOf(valueSelector4);
        result4.setText(_valueSelector4);
        //Square Meter to SquareFoot
        // ex :-  Meter = 1  Foot= Meter * 10.763910417; Result = 10.763910417
        double valueSelector5 =  (userValue1 * 10.76391);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Square Meter toSquare Inch
        // ex :-  Meter = 1  Inch= Meter * 1550.0031; Result = 1550.0031
        double valueSelector6 =  (userValue1 * 1550.0031);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Square Meter toSquare  Kilo Meter
        // ex :-  Meter = 1  Kilo_Meter= Meter * 0.000001; Result = 1e-6
        double valueSelector7 =  (userValue1 * (1*Math.pow(10,-6)));
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Square Meter to Square  Meter
        // ex :-  Meter = 1  Meter= Meter * 1; Result = 1
        int valueSelector8 = (int) (userValue1 * 1);
        String _valueSelector8 = String.valueOf(valueSelector8);
        result8.setText(_valueSelector8);
        //Square Meter to Square mile
        // ex :-  Meter = 1  mile= Meter * 3.861021585E-7; Result = 3.861e-7
        double valueSelector9 =  (userValue1 * (3.861*Math.pow(10,-7)));
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Square Meter to milli meter
        // ex :-  Meter = 1  milli_meter= Meter * 1000000; Result = 1e+6
        int valueSelector10 = (int) (userValue1 * 1000000);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //Square Meter to Square Yard
        // ex :-  Meter = 1  Yard= Meter * 1.1959900463; Result = 1.196
        double valueSelector11 =  (userValue1 * 1.19599);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }
    private void SquareKilometer() {
        // Square Kilo Meter to acre
        // ex :-  Kilo_Meter = 1  Acre= Kilo_Meter * 247.10538147; Result = 247.105
        double valueSelector1 =  (userValue1 * 247.105381);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Square Kilo Meter to Hectare
        // ex :-  Kilo_Meter = 1  Hectare= Kilo_Meter * 100 Result =100
        int valueSelector2 = (int) (userValue1 * 100);
        String _valueSelector2 = String.valueOf(valueSelector2);
        result2.setText(_valueSelector2);
        //Square Kilo Meter to Square CentiMeter
        // ex :-  Kilo_Meter = 1  CentiMeter= Kilo_Meter *  1e+1 Result = 1e+10
        double valueSelector3 =  userValue1 * (1*Math.pow(10,10));
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Square Kilo Meter to Square DeciMeter
        // ex :-  Kilo_Meter = 1  DeciMeter= Kilo_Meter * 1e+8 Result = 1e+8
        int valueSelector4 = (int) (userValue1 * 100000000);
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Square Kilo Meter to Square Foot
        // ex :-  Kilo_Meter = 1  Foot= Kilo_Meter * 10763910.417 10763910.4166 Result = 1.076e+7
        double valueSelector5 =  (userValue1 * 10763910.4166);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Square Kilo Meter to Square  Inch
        // ex :-  Kilo_Meter = 1  Inch= Kilo_Meter * 1550003100 Result = 1.55e+9
        double valueSelector6 = (userValue1 * 1550003100);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Square Kilo Meter to Square Kilo Meter
        // ex :-  Kilo_Meter = 1  Kilo_Meter= Kilo_Meter *  1 Result = 1
        int valueSelector7 = (int) (userValue1 * 1);
        String _valueSelector7 = String.valueOf(valueSelector7);
        result7.setText(_valueSelector7);
        //Square Kilo Meter to  Square Meter
        // ex :-  Kilo_Meter = 1  Meter= Kilo_Meter *  1000000 Result = 1e+6
        int valueSelector8 = (int) (userValue1 * 1000000);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Square Kilo Meter to Square mile
        // ex :-  Kilo_Meter = 1  mile= Kilo_Meter * 0.3861021585 Result = 0.386102
        double valueSelector9 =  (userValue1 * 0.386102);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Square Kilo Meter toSquare  milli meter
        // ex :-  Kilo_Meter = 1  milli_meter= Kilo_Meter * 1e+12; Result =1e+12
        double valueSelector10 =  userValue1 * 1E+12  ;
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //SquareKilo Meter to SquareYard
        // ex :-  Kilo_Meter = 1  Yard= Kilo_Meter * 1195990.0463; Result =  1.196e+6
        double valueSelector11 =  (userValue1 * 1.196e+6);
        String _valueSelector11 = decimalScientificFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }
    private void SquareInch() {
        // SquareInch to acre
        // ex :-  Inch = 1  Acre= Inch * 1.594225079E-7  Result = 1.594225079E-7 //6.273e+6
//        double valueSelector1 =  (userValue1 * (1.5942*Math.pow(10,-7)));
        double valueSelector1 =  (userValue1 / (6.273*Math.pow(10,6)));
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // SquareInch to Hectare
        // ex :-  Inch = 1  Hectare= Inch * 6.4516e-8  Result =6.4516e-8
        double valueSelector2 =  (userValue1 * (6.4516*Math.pow(10,-8)));
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //SquareInch to SquareCentiMeter
        // ex :-  Inch = 1  CentiMeter= Inch * 6.4516  Result =6.4516
        double valueSelector3 =  (userValue1 * 6.4516 );
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //SquareInch to SquareDeciMeter
        // ex :-  Inch = 1  DeciMeter= Inch *0.0645165 Result = 0.064516
        double valueSelector4 =  (userValue1 * 0.064516);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //SquareInch to SquareFoot
        // ex :-  Inch = 1  Foot= Inch * 0.0069444444 Result =0.00694444
        double valueSelector5 =  (userValue1 * 0.006944);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //SquareInch to SquareInch
        // ex :-  Inch = 1  Inch= Inch * 1  Result =1
        int valueSelector6 = (int) (userValue1 * 1);
        String _valueSelector6 = String.valueOf(valueSelector6);
        result6.setText(_valueSelector6);
        //SquareInch to SquareKilo Meter
        // ex :-  Inch = 1  Kilo_Meter= Inch * 6.4516e-10  Result =6.4516e-10
        double valueSelector7 =  (userValue1 * (6.4516*Math.pow(10,-10)));
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //SquareInch to  SquareMeter
        // ex :-  Inch = 1  Meter= Inch * 0.00064516  Result =0.00064516
        double valueSelector8 =  (userValue1 * 0.00064516);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //SquareInch to Square mile
        // ex :-  Inch = 1  mile= Inch * 2.490976686E-10  Result =2.490976686E-10
        double valueSelector9 =  (userValue1 *(2.491*Math.pow(10,-10)));
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //SquareInch to Square milli meter
        // ex :-  Inch = 1  milli_meter= Inch * 645.16  Result =645.16
        double valueSelector10 =  (userValue1 *   645.16);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //SquareInch to Square Yard
        // ex :-  Inch = 1  Acre= Inch * 0.0007716049  Result =0.0007716049
        double valueSelector11 =  (userValue1 * 0.000772);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }
    private void SquareFoot() {
        //Square Foot to acre
        // ex :-  Foot = 1  Acre= Foot *  0.0000229568  Result =2.2957e-5
//        double valueSelector1 =  (userValue1 *( 2.2957*Math.pow(10,-5)));
        double valueSelector1 =  (userValue1 /43560);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // SquareFoot to Hectare
        // ex :-  Foot = 1  Hectare= Foot * 0.0000092903  Result = 9.2903e-6
        double valueSelector2 =  (userValue1 *( 9.2903*Math.pow(10,-6)));
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //SquareFoot to Square CentiMeter
        // ex :-  Foot = 1  CentiMeter= Foot * 929.0304  Result =929.0304
        double valueSelector3 =  (userValue1 * 929.0304);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //SquareFoot to Square DeciMeter
        // ex :-  Foot = 1  DeciMeter= Foot *  9.290304 Result = 9.290304
        double valueSelector4 =  (userValue1 * 9.290304);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //SquareFoot to SquareFoot
        // ex :-  Foot = 1  Foot= Foot * 1 Result =1
        int valueSelector5 = (int) (userValue1 * 1);
        String _valueSelector5 = String.valueOf(valueSelector5);
        result5.setText(_valueSelector5);
        //SquareFoot to Square Inch
        // ex :-  Foot = 1 Inch= Foot * 144  Result =144
        int valueSelector6 = (int) (userValue1 * 144);
        String _valueSelector6 = String.valueOf(valueSelector6);
        result6.setText(_valueSelector6);
        //SquareFoot to Square Kilo Meter
        // ex :-  Foot = 1 Kilo_Meter= Foot * 9.290303999E-8  Result =9.290303999E-8
        double valueSelector7 =  (userValue1 /( 1.076*Math.pow(10,7)));
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Square Foot to Square Meter
        // ex :-  Foot = 1  Meter= Foot * 0.09290304  Result =0.092903043
        double valueSelector8 =  (userValue1 * 0.092903);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Square Foot to Square  mile
        // ex :-  Foot = 1  mile= Foot * 3.587006427E-8  Result =3.587006427E-8
        double valueSelector9 =  (userValue1 *( 3.587*Math.pow(10,-8)));
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Square Foot to Square milli meter
        // ex :-  Foot = 1 milli_meter= Foot * 92903  Result =92903
        double valueSelector10 =  (userValue1 * 92903.04);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //Square Foot to SquareYard
        // ex :-  Foot = 1  Yard= Foot * 0.1111111111  Result =0.111111
        double valueSelector11 =  (userValue1 * 0.111111);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }

    private void SquareDeciMeter() {
        // SquareDeciMeter to acre
        // ex :-   DeciMeter = 1  Acre= DeciMeter * 0.0000024711  Result =2.4711e-6
        double valueSelector1 =  (userValue1 *( 2.4711*Math.pow(10,-6)));
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // SquareDeciMeter to Hectare
        // ex :-   DeciMeter = 1   Hectare= DeciMeter * 0.000001  Result = 1e-6
        double valueSelector2 =  (userValue1 * 0.000001);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //SquareDeciMeter to SquareCentiMeter
        // ex :-  Square DeciMeter = 1   SquareCentiMeter= SquareDeciMeter * 100  Result =100
        int valueSelector3 = (int) (userValue1 * 100);
        String _valueSelector3 = String.valueOf(valueSelector3);
        result3.setText(_valueSelector3);
        //SquareDeciMeter to SquareDeciMeter
        // ex :-   SquareDeciMeter = 1  SquareDeciMeter= SquareDeciMeter * 1  Result = 1
        int valueSelector4 = (int) (userValue1 * 1);
        String _valueSelector4 = String.valueOf(valueSelector4);
        result4.setText(_valueSelector4);
        //SquareDeciMeter to SquareFoot
        // ex :-   DeciMeter = 1  Foot= DeciMeter * 0.1076391042  Result = 0.1076391042
        double valueSelector5 =  (userValue1 * 0.107639);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //SquareDeciMeter to Square Inch
        // ex :-   DeciMeter = 1  Inch= DeciMeter * 15.500031 Result =15.500031
        double valueSelector6 =  (userValue1 *15.500031);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //SquareDeciMeter to Square Kilo Meter
        // ex :-   DeciMeter = 1  Kilo_Meter= DeciMeter *  1e-8  Result =1e-8
        double valueSelector7 =  (userValue1 * (1*Math.pow(10,-8)));
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Square DeciMeter to  Square Meter
        // ex :-   DeciMeter = 1  Meter= DeciMeter * 0.01 Result = 0.01
        double valueSelector8 =  (userValue1 * 0.01);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Square DeciMeter to Square mile
        // ex :-   DeciMeter = 1  mile= DeciMeter * 3.861021585E-9  Result =3.861021585E-9
        double valueSelector9 = (userValue1 * (3.861*Math.pow(10,-9)));
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Square DeciMeter to Square  milli meter
        // ex :-   DeciMeter = 1 milli_meter= DeciMeter * 10000  Result =10000
        int valueSelector10 = (int) (userValue1 * 10000);
        String _valueSelector10 = String.valueOf(valueSelector10);
        result10.setText(_valueSelector10);
        //Square DeciMeter to Square Yard
        // ex :-   DeciMeter = 1  Yard= DeciMeter * 0.0119599005 Result =0.0119599005
        double valueSelector11 =  (userValue1 * 0.01196);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }

    private void SquareCentiMeter() {
        // SquareCentiMeter to acre
        // ex :-   CentiMeter = 1  Acre= CentiMeter * 2.471053814E-8  Result =2.4711e-8
        double valueSelector1 =  (userValue1 *  (2.4711*Math.pow(10,-8)));
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // SquareCentiMeter to Hectare
        // ex :-   CentiMeter = 1  Hectare= CentiMeter*1e-8 Result =1e-8
        double valueSelector2 =  (userValue1 * (1*Math.pow(10,-8)));
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //SquareCentiMeter to SquareCentiMeter
        // ex :-   CentiMeter = 1  CentiMeter= CentiMeter*1  Result =1
        int valueSelector3 = (int) (userValue1 * 1);
        String _valueSelector3 = String.valueOf(valueSelector3);
        result3.setText(_valueSelector3);
        //SquareCentiMeter to SquareDeciMeter
        // ex :-   CentiMeter = 1  DeciMeter= CentiMeter*0.01  Result =0.01
        double valueSelector4 =  (userValue1 * 0.01);
        String _valueSelector4 =decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //SquareCentiMeter toSquare Foot
        // ex :-   CentiMeter = 1  Foot= CentiMeter* 0.001076391 Result = 0.001076391
        double valueSelector5 =  (userValue1 * 0.001076);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //SquareHectare to Square Inch
        // ex :-   CentiMeter = 1  Inch= CentiMeter* 0.15500031  Result =0.15500031
        double valueSelector6 =  (userValue1 * 0.155);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
//        String _valueSelector6 = String.valueOf(valueSelector6);
        result6.setText(_valueSelector6);
        //SquareCentiMeter to  SquareKilo Meter
        // ex :-   CentiMeter = 1  Kilo_Meter= CentiMeter*1e-10  Result =1e-10
        double valueSelector7 =  (userValue1 * (1*Math.pow(10,-10)));
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Square CentiMeter to Square  Meter
        // ex :-   CentiMeter = 1  Meter= CentiMeter* 0.0001 Result = 0.0001
        double valueSelector8 =  (userValue1 * 0.0001);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Square CentiMeter to Square mile
        // ex :-   CentiMeter = 1  mile= CentiMeter*3.861021585E-11  Result =3.861021585E-11
        double valueSelector9 =  (userValue1 * (3.861*Math.pow(10,-11)));
//        String _valueSelector9 =String.valueOf(valueSelector9);
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText( _valueSelector9);
        //Square CentiMeter to Square milli meter
        // ex :-   CentiMeter = 1  milli_meter= CentiMeter*100 Result =100
        int valueSelector10 = (int) (userValue1 *   100);
        String _valueSelector10 = String.valueOf(valueSelector10);
        result10.setText(_valueSelector10);
        //Square CentiMeter to Square  Yard
        // ex :-   CentiMeter = 1  Yard= CentiMeter*0.000119599  Result =0.000119599
        double valueSelector11 =  (userValue1 *  0.00012);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
//        String _valueSelector11 = String.valueOf(valueSelector11);
        result11.setText(_valueSelector11);


    }

    private void Hectare() {
        // Hectare to acre
        // ex :-   Hectare = 1  Acre= Hectare*2.4710538147  Result =2.4710538147
        double valueSelector1 =  (userValue1 * 2.47105);   // or
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Hectare to Hectare
        // ex :-   Hectare = 1  Acre= Hectare*1  Result =1
        int valueSelector2 = (int) (userValue1 * 1);   // or
        String _valueSelector2 = String.valueOf(valueSelector2);
        result2.setText(_valueSelector2);
        //Hectare to  square CentiMeter
        // ex :-   Hectare = 1  Acre= Hectare*100000000 Result =100000000
        int valueSelector3 = (int) (userValue1 * 100000000);
        String _valueSelector3 = String.valueOf(valueSelector3);
        result3.setText(_valueSelector3);
        //Hectare to  square  DeciMeter
        // ex :-   Hectare = 1  DeciMeter= Hectare*1000000 Result =1000000
        int valueSelector4 = (int) (userValue1 * 1000000);
        String _valueSelector4 = String.valueOf(valueSelector4);
        result4.setText(_valueSelector4);
        //Hectare to square  Foot
        // ex :-   Hectare = 1  Foot= Hectare*107639.10417  Result =107639.10417
        int valueSelector5 = (int) (userValue1 * 107639);
        String _valueSelector5 = String.valueOf(valueSelector5);
        result5.setText(_valueSelector5);
        //Hectare to  square Inch
        // ex :-   Hectare = 1   Inch= Hectare*15500031  Result =15500031
        int valueSelector6 = (int) (userValue1 * 15500031);
        String _valueSelector6 = String.valueOf(valueSelector6);
//        String _valueSelector6 = String.valueOf(valueSelector6);
        result6.setText(_valueSelector6);
        //Hectare to  square Kilo Meter
        // ex :-   Hectare = 1  Kilo_Meter= Hectare*0.01 Result =0.01
        double valueSelector7 =  (userValue1 *0.01 );
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Hectare to  square  Meter
        // ex :-   Hectare = 1   Meter= Hectare*10000  Result =10000
        int valueSelector8 = (int) (userValue1 * 10000);
        String _valueSelector8 = String.valueOf(valueSelector8);
        result8.setText(_valueSelector8);
        //Hectare to  square mile
        // ex :-   Hectare = 1   square mile= Hectare * 0.0038610216  Result =0.00386102
        double valueSelector9 =  (userValue1 * 0.003861);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Hectare to  square milli meter
        // ex :-   Hectare = 1   square milli meter=Hectare* 10000000000 Result = 1e+10
        //BigInteger squareMillimeter = new BigInteger("10000000000");
        double valueSelector10 =  (userValue1 *(1 *Math.pow(10,10)));
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //Hectare to square Yard
        // ex :-   Hectare = 1    square Yard= Hectare*11959.9  Result =11959.900463
        double valueSelector11 =  (userValue1 *   11959.9005);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }

    private void acre() {
        // acre to acre
        // ex : Acre = 1 Acre = Acre*1   result = 1
        int valueSelector1 = (int) (userValue1 * 1);   // or
        String _valueSelector1 = String.valueOf(valueSelector1);
        result1.setText(_valueSelector1);
        // acre to Hectare
        // ex :-  Acre = 1  Hectare= Acre*0.4046856422 Result = 0.404686
        double valueSelector2 = (userValue1 /2.471);   // or
        String _valueSelector2 = decimalFormat.format(valueSelector2); // 5 decimal result :0.40469
        result2.setText(_valueSelector2);
        //acre to square CentiMeter
        // ex :-  Acre = 1  square CentiMeter= Acre * 40468564.224  Result = 40468564.224
        double valueSelector3 =  (userValue1 * 40468564.22 );
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //acre to square DeciMeter
        // ex :-  Acre = 1  square DeciMeter= Acre * 404686  Result = 404686
        double valueSelector4 =  (userValue1 * 404685.64);
        valueSelector4 = Math.round(valueSelector4 * 100.0)/100.0;
        String _valueSelector4 = String.valueOf(valueSelector4);
        result4.setText(_valueSelector4);
        //acre to square Foot
        // ex :-  Acre = 1  square Foot= Acre * 43560  Result = 43560
        int valueSelector5 = (int) (userValue1 * 43560);
        valueSelector5 = (int) (Math.round(valueSelector5 * 100.0)/100.0);
        String _valueSelector5 = String.valueOf(valueSelector5);
        result5.setText(_valueSelector5);
        //acre to square Inch
        // ex :-  Acre = 1  square Inch= Acre * 6272640  Result = 6272640
        int valueSelector6 = (int) (userValue1 *6272640);
        valueSelector6 = (int) (Math.round(valueSelector6 * 100.0)/100.0);
        String _valueSelector6 = String.valueOf(valueSelector6);
        result6.setText(_valueSelector6);
        //acre to square Kilo Meter
        // ex :-  Acre = 1  square Kilo_Meter= Acre *0.0040468564  Result = 0.0040468564
        double valueSelector7 =  (userValue1 *0.004047);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //acre to  square Meter
        // ex :-  Acre = 1  square Meter= Acre *  4046.8564224  Result =  4046.8564224
        double valueSelector8 =  (userValue1 * 4046.86);
        valueSelector8 = Math.round(valueSelector8 * 100.0)/100.0;
        String _valueSelector8 = String.valueOf(valueSelector8);
        result8.setText(_valueSelector8);
        //acre to square mile
        // ex :-  Acre = 1  square mile= Acre* 0.0015625  Result =  0.0015625
        double valueSelector9 =  (userValue1 *0.001562);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //acre to square milli meter
        // ex :-  Acre = 1   square Milli_meter= Acre * 4046856422.4  Result = 4046856422.4
//        double valueSelector10 =  (userValue1 * (4.047*Math.pow(10,9)));
        double valueSelector10 =  (userValue1 *  4046856422.4);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //acre to square Yard
        // ex :-  Acre = 1  square Yard= Acre * 4840  Result = 4840
        int valueSelector11 = (int) (userValue1 *   4840);
        valueSelector11 = (int) (Math.round(valueSelector11 * 100.0)/100.0);
        String _valueSelector11 = String.valueOf(valueSelector11);
        result11.setText(_valueSelector11);
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    /** Called to load ad */
    private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID
        AdRequest adRequest = new AdRequest.Builder().build();
        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);
        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }
    // inflate custom toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_menu,menu);
        return true;
    }
    // override method to responsible for responding correctly to the items specified in the menu resource file.
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){

        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        if(item.getItemId() == R.id.action_share){
            shareUpdate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareUpdate() {
        // current activity string are copied to another set of string that are made available for the Share intent
        String activity_result;
        activity_result =result1.getText().toString();
        String activity_result2;
        activity_result2 =result2.getText().toString();
        String activity_result3;
        activity_result3 = result3.getText().toString();
        String activity_result4;
        activity_result4 = result4.getText().toString();
        String activity_result5;
        activity_result5 = result5.getText().toString();
        String activity_result6;
        activity_result6 = result6.getText().toString();
        String activity_result7;
        activity_result7 = result7.getText().toString();
        String activity_result8;
        activity_result8 = result8.getText().toString();
        String activity_result9;
        activity_result9 = result9.getText().toString();
        String activity_result10;
        activity_result10 = result10.getText().toString();
        String activity_result11;
        activity_result11 = result11.getText().toString();
        String User_enter_data;
        User_enter_data = UserInput.getText().toString();

        // above declared string are exported to share activity
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody =  "Results based on your input for "+ User_enter_data +" "+ selectedItem + "\n"
                + "Acre (ac) : " + activity_result + "\n"
                + "Hectare (ha) " + activity_result2 + "\n"
                + "Square Centimeter (cm2):" + activity_result3+ "\n"
                + "Square Decimeters (dm2): " + activity_result4 + "\n"
                + "Square Foot (ft2): " + activity_result5  + "\n"
                + " Square Inch (in2) :" + activity_result6+ "\n"
                + "Square Kilometer (km2): " + activity_result7 + "\n"
                + " Square Meter (m2)  : " + activity_result8  + "\n"
                + "Square Mile (mi2):" + activity_result9+ "\n"
                + "Square Millimeters (mm2) : " + activity_result10 + "\n"
                + " Square Yard (yd2) : " + activity_result11  + ".\n";

        String shareSub = "Area Converter Results Powered by Transpose Solutions Android App - Unit Converter ";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));

    }


    // override method to listen for any click events on selecting a particular item from the drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.main_home) {
            // Handle the camera action
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_area) {
            Intent intent = new Intent(this, Area.class);
            startActivity(intent);
        } else if (id == R.id.menu_currency) {
            Intent intent = new Intent(this, Currency.class);
            startActivity(intent);
        }else if (id == R.id.menu_Digital_Storage) {
            Intent intent = new Intent(this, DigitalStorage.class);
            startActivity(intent);
        } else if (id == R.id.menu_energy) {
            Intent intent = new Intent(this, Energy.class);
            startActivity(intent);
        } else if (id == R.id.menu_force) {
            Intent intent = new Intent(this, Force.class);
            startActivity(intent);
        } else if (id == R.id.menu_Fuel_Economy) {
            Intent intent = new Intent(this, FuelEconomy.class);
            startActivity(intent);
        } else if (id == R.id.menu_frequency) {
            Intent intent = new Intent(this, Frequency.class);
            startActivity(intent);
        } else if (id == R.id.Length) {
            Intent intent = new Intent(this, Length.class);
            startActivity(intent);
        } else if (id == R.id.Mass) {
            Intent intent = new Intent(this, Mass.class);
            startActivity(intent);
        } else if (id == R.id.Plane_Angle) {
            Intent intent = new Intent(this, PlaneAngle.class);
            startActivity(intent);
        } else if (id == R.id.menu_power) {
            Intent intent = new Intent(this, Power.class);
            startActivity(intent);
        } else if (id == R.id.Pressure) {
            Intent intent = new Intent(this, Pressure.class);
            startActivity(intent);
        } else if (id == R.id.Speed) {
            Intent intent = new Intent(this, Speed.class);
            startActivity(intent);
        }
        else if (id == R.id.Temperature) {
            Intent intent = new Intent(this, Temperature.class);
            startActivity(intent);
        } else if (id == R.id.Time) {
            Intent intent = new Intent(this, Time.class);
            startActivity(intent);
        } else if (id == R.id.Volume) {
            Intent intent = new Intent(this, Volume.class);
            startActivity(intent);
        }else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, AppSettings.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_rate) {
            try {
                Uri uri1 = Uri.parse("market://details?id=" + getPackageName());
                Intent gotoMarket1 = new Intent(Intent.ACTION_VIEW, uri1);
                startActivity(gotoMarket1);
            } catch (ActivityNotFoundException e){
                Uri uri1 = Uri.parse("http://play.google.com/store/apps/details/id=" + getPackageName());
                Intent gotoMarket1 = new Intent(Intent.ACTION_VIEW, uri1);
                startActivity(gotoMarket1);
            }
        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Check out this great Unit Converter app. This app helps you to convert common units of measurement" +  "\n"+  "\n"
                    + "Google Play store:" +  "\n" +"https://play.google.com/store/apps/details?id=com.transposesolutions.unitconverter&hl=en" + "\n";
            String shareSub = "Check out this great Unit Converter app from Transpose Solutions";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        } else if (id == R.id.nav_apps) {
            try {
                Uri uri1 = Uri.parse("https://play.google.com/store/apps/dev?id=8903808498078108637&hl=en");
                Intent gotoMarket2 = new Intent(Intent.ACTION_VIEW, uri1);
                startActivity(gotoMarket2);
            } catch (ActivityNotFoundException e) {
                Uri uri1 = Uri.parse("https://play.google.com/store/apps/dev?id=8903808498078108637&hl=en");
                Intent gotoMarket2 = new Intent(Intent.ACTION_VIEW, uri1);
                startActivity(gotoMarket2);
            }

        }
        return true;
    }


}