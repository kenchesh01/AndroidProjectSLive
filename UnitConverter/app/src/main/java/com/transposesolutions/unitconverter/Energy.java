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

public class Energy extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Declare the instance variable Ad view
    private AdView adView;
    private ActionBarDrawerToggle mToggle;
    EditText UserInput;
    TextView result1,result2,result3,result4,result5,result6,result7,result8,result9,result10,result11,result12;
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
                result12.setText("");
            }else{
                checkForChanges1();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy);
        //UI elements
        UserInput = findViewById(R.id.user_input);
        selector_1 = findViewById(R.id.selector_1);
        result1=findViewById(R.id.Result_1);
        result2=findViewById(R.id.Result_2);
        result3=findViewById(R.id.Result_3);
        result4=findViewById(R.id.Result_4);
        result5=findViewById(R.id.Result_5);
        result6=findViewById(R.id.Result_6);
        result7=findViewById(R.id.Result_7);
        result8=findViewById(R.id.Result_8);
        result9=findViewById(R.id.Result_9);
        result10=findViewById(R.id.Result_10);
        result11=findViewById(R.id.Result_11);
        result12=findViewById(R.id.Result_12);

        // to perform the default logic on create
        int defaultValue1 = 1;
        String displayValue1 = String.valueOf(defaultValue1);
        UserInput.setText(displayValue1);
        // set listeners
        UserInput.addTextChangedListener(mTextWatcher);
        checkForChanges1();

        // Load an ad into the AdMob banner view.
        FrameLayout adContainerView = findViewById(R.id.ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
        adContainerView.addView(adView);
        loadBanner();
        // Load Navigation View.
        DrawerLayout mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_Energy_items, R.layout.spinner_selector_items);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        // Apply the adapter to the spinner
        selector_1.setAdapter( adapter);
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
                if(position==0){
                    update_Joule();
                }else if(position==1){
                    update_KiloJoule();
                }else if(position==2){
                    update_Calorie();
                }else if(position==3){
                    update_KiloCalorie();
                }else if(position==4){
                    update_Watt_hour();
                }else if(position==5){
                    update_Kilowatt_hour();
                }else if(position==6){
                    update_Electron_volt();
                }else if(position==7){
                    update_Erg();
                }else if(position==8){
                    update_British_thermal_unit();
                }else if(position==9){
                    update_US_Therm();
                }else if(position==10){
                    update_Inch_pound();
                }else if(position==11){
                    update_Foot_pound();
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
            case "Joule (J)":
                x = 1;
                break;
            case "Kilo joule (kJ)":
                x = 2;
                break;
            case "Calorie (cal)":
                x = 3;
                break;
            case "Kilo calorie (Kcal)":
                x = 4;
                break;
            case "Watt hour (KW-h)":
                x = 5;
                break;
            case "Kilowatt hour (KW-h)":
                x = 6;
                break;
            case "Electron volt (eV)":
                x = 7;
                break;
            case "Erg (erg)":
                x = 8;
                break;
            case "British thermal unit (Btu)":
                x = 9;
                break;
            case "US Therm (therm)":
                x = 10;
                break;
            case "Inch-pound (in-lb)":
                x = 11;
                break;
            case "Foot-pound (ft-lb)":
                x = 12;
                break;
            default:
                break;
        }
        if (x==1){
            update_Joule();
        }else if (x==2){
            update_KiloJoule();
        }else if (x==3){
            update_Calorie();
        }else if (x==4){
            update_KiloCalorie();
        }else if (x==5){
            update_Watt_hour();
        }else if (x==6){
            update_Kilowatt_hour();
        }else if (x==7){
            update_Electron_volt();
        } else if (x==8){
            update_Erg();
        }else if (x==9){
            update_British_thermal_unit();
        }else if (x==10){
            update_US_Therm();
        }else if (x==11){
            update_Inch_pound();
        }else if (x==12){
            update_Foot_pound();
        }
    }

    private void update_Foot_pound() {
        // Foot-pound to Joule
        // example Foot-pound = 1  Joule = Foot-pound * 1.3558179483; Result = 1.3558179483
        double valueSelector1 =  (userValue1 * 1.35582);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Foot-pound to KiloJoule
        // example Foot-pound = 1  KiloJoule = Foot-pound *  0.0013558179; Result = 0.0013558179
        double valueSelector2 = (userValue1 /738);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Foot-pound to Calorie
        // example Foot-pound = 1  Calorie = Foot-pound *  0.3240482668; Result =  0.3240482668
        double valueSelector3 = (userValue1/ 3.086);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Foot-pound to KiloCalorie
        // example Foot-pound = 1  KiloCalorie = Foot-pound * 0.0003240483; Result = 0.0003240483
        double valueSelector4 = (userValue1 / 3086);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Foot-pound to Watt_hour
        // example Foot-pound = 1  Watt_hour = Foot-pound *0.0003766161; Result = 0.0003766161
        double valueSelector5 = (userValue1 /2655);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Foot-pound to Kilowatt_hour
        // example Foot-pound = 1   Kilowatt_hour = Foot-pound * 3.766160967E-7; Result =3.766160967E-7
        double valueSelector6 = (userValue1 * 3.7662e-7);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Foot-pound to Electron_volt
        // example Foot-pound = 1  Electron_volt = Foot-pound * 1.356e+7; Result = 1.356e+7
        double valueSelector7 = (userValue1 * 8.462e+18);
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // Foot-pound to Erg
        // example Foot-pound = 1  Erg = Foot-pound * 13558179.483; Result =13558179.483
        double valueSelector8 = (userValue1 * 1.356e+7);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // Foot-pound to British_thermal_unit
        // example Foot-pound = 1  British_thermal_unit = Foot-pound * 0.0012859278; Result =0.0012859278
        double valueSelector9 =  (userValue1 /778);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Foot-pound to US_Therm
        // example Foot-pound = 1  US_Therm = Foot-pound *1.2854e-8; Result =1.2854e-8
        double valueSelector10 =  (userValue1 *  1.2854e-8);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Foot-pound to Inch-pound
        // example Foot-pound = 1  Inch-pound = Foot-pound * 12; Result = 12
        int valueSelector11 = (int) (userValue1 * 12);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Foot-pound to Foot-pound
        // example Foot-pound = 1  Foot-pound = Foot-pound * 1; Result = 1
        int valueSelector12 = (int) (userValue1 * 1);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
    }

    private void update_Inch_pound() {
        // Inch-pound to Joule
        // example Inch-pound = 1  Joule =Inch-pound * 0.112984829; Result = 0.112984829
        double valueSelector1 =  (userValue1 * 0.112984829);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Inch-pound to KiloJoule
        // example Inch-pound = 1  KiloJoule  =Inch-pound *  0.0001129848; Result =  0.0001129848
        double valueSelector2 = (userValue1 * 0.0001129848);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Inch-pound to Calorie
        // example Inch-pound = 1  Calorie =Inch-pound * 0.0270040222; Result = 0.0270040222
        double valueSelector3 = (userValue1* 0.0270040222);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Inch-pound to KiloCalorie
        // example Inch-pound = 1  KiloCalorie =Inch-pound * 0.000027004; Result =0.000027004
        double valueSelector4 = (userValue1 * 0.000027004);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Inch-pound to Watt_hour
        // example Inch-pound = 1   Watt_hour =Inch-pound *  0.0000313847; Result = 0.0000313847
        double valueSelector5 = (userValue1 *  0.0000313847);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Inch-pound to Kilowatt_hour
        // example Inch-pound = 1  Kilowatt_hour =Inch-pound * 3.138467472E-8; Result =3.138467472E-8
        double valueSelector6 = (userValue1 * 3.1385E-8);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Inch-pound to Electron_volt
        // example Inch-pound = 1  Electron_volt =Inch-pound *705195529287220400; Result = 7.051955293e+17
        double valueSelector7 = (userValue1 * 7.051955292872200e17);
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // Inch-pound to Erg
        // example Inch-pound = 1  Erg =Inch-pound * 1129848.2902; Result = 1129848.2902
        double valueSelector8 = (userValue1 * 1129848.2902);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // Inch-pound to British_thermal_unit
        // example Inch-pound = 1  British_thermal_unit =Inch-pound *  0.0001071606; Result = 0.0001071606
        double valueSelector9 =  (userValue1 * 0.0001071606);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Inch-pound to US_Therm
        // example Inch-pound = 1  US_Therm =Inch-pound *  1.071145246E-9; Result = 1.071145246E-9
        double valueSelector10 =  (userValue1 * 1.071145246E-9);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Inch-pound to Inch-pound
        // example Inch-pound = 1  Inch-pound=Inch-pound * 1; Result = 1
        int valueSelector11 = (int) (userValue1 * 1);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Inch-pound to Foot-pound
        // example Inch-pound = 1  Foot-pound =Inch-pound * 0.08333333; Result = 0.08333333
        double valueSelector12 = (userValue1 * 0.0833333333);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
    }

    private void update_US_Therm() {
        // US_Therm to Joule
        // example US_Therm = 1  Joule = US_Therm * 105480400; Result =105480400
        double valueSelector1 =  (userValue1 * 1.055e+8);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // US_Therm to KiloJoule
        // example US_Therm = 1  KiloJoule = US_Therm * 105480.4; Result = 105480.4
        double valueSelector2 = (userValue1 * 105480);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // US_Therm to Calorie
        // example US_Therm = 1  Calorie = US_Therm * 25210420.65; Result =25210420.65
        double valueSelector3 = (userValue1* 2.521e+7);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // US_Therm to KiloCalorie
        // example US_Therm = 1  KiloCalorie = US_Therm *25193.560715; Result =25193.560715
        double valueSelector4 = (userValue1 *25210.4);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // US_Therm to Watt_hour
        // example US_Therm = 1  Watt_hour = US_Therm * 29.300111111; Result =29.300111111
        double valueSelector5 = (userValue1 *29300.1);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // US_Therm to Kilowatt_hour
        // example US_Therm = 1  Kilowatt_hour = US_Therm *29300.111111; Result =29300.111111
        double valueSelector6 = (userValue1 *29.3001);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // US_Therm to Electron_volt
        // example US_Therm = 1  Electron_volt = US_Therm * 6.583565877E+26; Result =6.583565877E+26
        double valueSelector7 = (userValue1 * 6.584e+26);
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // US_Therm to Erg
        // example US_Therm = 1  Erg = US_Therm * 1054803999999987; Result =1.054804e+15
        double valueSelector8 = (userValue1 * 1.055e+15);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // US_Therm to British_thermal_unit
        // example US_Therm = 1  British_thermal_unit = US_Therm * 99976.1; Result = 99976.1
        double valueSelector9 =  (userValue1 * 99976.1);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // US_Therm to US_Therm
        // example US_Therm = 1  US_Therm = US_Therm * 1; Result =1
        int valueSelector10 = (int) (userValue1 * 1);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // US_Therm to Inch-pound
        // example US_Therm = 1 Inch-pound = US_Therm *  933580206.4; Result = 933580206.4
        double valueSelector11 = (userValue1 * 933580206.4);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // US_Therm to Foot-pound
        // example US_Therm = 1   Foot-pound = US_Therm * 77798350.533; Result =77798350.533
        double valueSelector12 = (userValue1 * 7.78e+7);
        String _valueSelector12 = decimalScientificFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
    }

    private void update_British_thermal_unit() {
        // British_thermal_unit to Joule
        // example British_thermal_unit = 1  Joule = British_thermal_unit * 1054.35; Result = 1054.35
        double valueSelector1 =  (userValue1 * 1055.06);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // British_thermal_unit to KiloJoule
        // example British_thermal_unit = 1  KiloJoule = British_thermal_unit *  1.05435; Result =  1.05435
        double valueSelector2 = (userValue1 * 1.05506);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // British_thermal_unit to Calorie
        // example British_thermal_unit = 1  Calorie = British_thermal_unit *251.99569789; Result = 251.99569789
        double valueSelector3 = (userValue1* 252.164);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // British_thermal_unit to KiloCalorie
        // example British_thermal_unit = 1  KiloCalorie = British_thermal_unit * 0.2519956979; Result = 0.2519956979
        double valueSelector4 = (userValue1 * 0.252164);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // British_thermal_unit to Watt_hour
        // example British_thermal_unit = 1  Watt_hour = British_thermal_unit * 0.292875; Result = 0.292875
        double valueSelector5 = (userValue1 /3.412);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // British_thermal_unit to Kilowatt_hour
        // example British_thermal_unit = 1  Kilowatt_hour = British_thermal_unit * 0.000292875; Result =0.000292875
        double valueSelector6 = (userValue1 / 3412);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // British_thermal_unit to Electron_volt
        // example British_thermal_unit = 1  Electron_volt = British_thermal_unit * 6.580732233E+21; Result = 6.580732233E+21
        double valueSelector7 = (userValue1 * 6.585e+21);
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // British_thermal_unit to Erg
        // example British_thermal_unit = 1  Erg = British_thermal_unit *10543500000; Result = 10543500000
        double valueSelector8 = (userValue1 * 1.055e+10);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // British_thermal_unit to British_thermal_unit
        // example British_thermal_unit = 1  British_thermal_unit = British_thermal_unit * 1; Result = 1
        int valueSelector9 = (int) (userValue1 * 1);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // British_thermal_unit to US_Therm
        // example British_thermal_unit = 1  US_Therm = British_thermal_unit * 0.0000099957; Result = 0.0000099957
        double valueSelector10 = (userValue1 * 1.0002e-5);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // British_thermal_unit to Inch-pound
        // example British_thermal_unit = 1  Inch-pound = British_thermal_unit *9331.7838251; Result =9331.7838251
        double valueSelector11 = (userValue1 * 9331.7838251);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // British_thermal_unit to Foot-pound
        // example British_thermal_unit = 1   Foot-pound = British_thermal_unit *  777.6486521; Result =  777.6486521
        double valueSelector12 = (userValue1 * 778.169);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
    }

    private void update_Erg() {
        // Erg to Joule
        // example Erg = 1   Joule = Erg * 1E-7; Result =  1E-7
        double valueSelector1 =  (userValue1 * 1E-7);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Erg to KiloJoule
        // example Erg = 1    KiloJoule = Erg * 1E-10; Result = 1E-10
        double valueSelector2 = (userValue1 * 1e-10);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Erg to Calorie
        // example Erg = 1   Calorie = Erg *2.390057361E-8; Result =2.390057361E-8
        double valueSelector3 = (userValue1* 2.3901E-8);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Erg to KiloCalorie
        // example Erg = 1   KiloCalorie = Erg * 2.390057361E-11; Result = 2.390057361E-11
        double valueSelector4 = (userValue1 * 2.3901e-11);
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Erg to Watt_hour
        // example Erg = 1   Watt_hour = Erg * 2.777777777E-11; Result =  2.777777777E-11
        double valueSelector5 = (userValue1 * 2.7778e-11);
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Erg to Kilowatt_hour
        // example Erg = 1   Kilowatt_hour = Erg *  2.777777777E-14; Result =   2.777777777E-14
        double valueSelector6 = (userValue1 * 2.7778e-14);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Erg to Electron_volt
        // example Erg = 1   Electron_volt = Erg *624150636309; Result =  6.2415E+11
        double valueSelector7 = (userValue1 * 6.242e+11);
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // Erg to Erg
        // example Erg = 1   Erg = Erg * 1; Result =  1
        int valueSelector8 = (int) (userValue1 * 1);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // Erg to British_thermal_unit
        // example Erg = 1   British_thermal_unit = Erg * 9.484516527E-11; Result =  9.484516527E-11
        double valueSelector9 = (userValue1 * 9.4782e-11);
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Erg to US_Therm *  9.480434279E-16; Result =  9.480434279E-16
        double valueSelector10 = (userValue1 * 9.4804e-16);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Erg to Inch-pound
        // example Erg = 1   Inch-pound = Erg *8.850745791E-7; Result =  8.850745791E-7
        double valueSelector11 = (userValue1 * 8.850745791E-7);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Erg to Foot-pound
        // example Erg = 1   Foot-pound = Erg * 7.375621493E-88; Result = 7.375621493E-8
        double valueSelector12 = (userValue1 * 7.3756e-8);
        String _valueSelector12 = decimalScientificFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
    }

    private void update_Electron_volt() {
        // Electron_volt to Joule
        // example Electron_volt = 1    Joule = Electron_volt * 1.60217733E-19; Result =  1.60217733E-19
        double valueSelector1 =  (userValue1 * 1.6022e-19);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Electron_volt to KiloJoule
        // example Electron_volt = 1    KiloJoule = Electron_volt * 1.60217733E-22; Result = 1.60217733E-22
        double valueSelector2 = (userValue1 * 1.6022e-22);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Electron_volt to Calorie
        // example Electron_volt = 1    Calorie = Electron_volt * 3.829295721E-20; Result = 3.829295721E-20
        double valueSelector3 = (userValue1* 3.8293e-20);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Electron_volt to KiloCalorie
        // example Electron_volt = 1    KiloCalorie = Electron_volt *  3.829295721E-23; Result =  33.829295721E-23
        double valueSelector4 = (userValue1 * 3.8293E-23);
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Electron_volt to Watt_hour
        // example Electron_volt = 1    Watt_hour = Electron_volt *4.450492583E-23; Result =  4.450492583E-23
        double valueSelector5 = (userValue1 * 4.4505E-23);
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Electron_volt to Kilowatt_hour
        // example Electron_volt = 1    Kilowatt_hour = Electron_volt * 4.450492583E-26; Result =  4.450492583E-26
        double valueSelector6 = (userValue1 * 4.45045E-26);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Electron_volt to Electron_volt
        // example Electron_volt = 1    Electron_volt = Electron_volt * 1; Result =  1
        int valueSelector7 = (int) (userValue1 * 1);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // Electron_volt to Erg
        // example Electron_volt = 1    Erg = Electron_volt * 1.60217733E-12; Result = 1.60217733E-12
        double valueSelector8 = (userValue1 * 1.6022E-12);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // Electron_volt to British_thermal_unit
        // example Electron_volt = 1    British_thermal_unit = Electron_volt *  1.519587736E-22; Result =  1.519587736E-22
        double valueSelector9 = (userValue1 * 1.5186E-22);
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Electron_volt to US_Therm
        // example Electron_volt = 1    US_Therm = Electron_volt *1.518933688E-27; Result =1.518933688E-27
        double valueSelector10 = (userValue1 * 1.5189e-27);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Electron_volt to Inch-pound
        // example Electron_volt = 1    Inch-pound = Electron_volt *1.418046426E-18; Result = 1.418046426E-18
        double valueSelector11 = (userValue1 * 1.418046426E-18);
        String _valueSelector11 = decimalScientificFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Electron_volt to Foot-pound
        // example Electron_volt = 1    Foot-pound = Electron_volt * 1.181705355E-19; Result =  1.181705355E-19
        double valueSelector12 = (userValue1 * 1.1817e-19);
        String _valueSelector12 = decimalScientificFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
    }

    private void update_Kilowatt_hour() {
        // _Kilowatt to Joule
        // example _Kilowatt = 1    Joule = _Kilowatt * 3600000; Result = 3600000
        double valueSelector1 =  (userValue1 * 3.6e+6);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // _Kilowatt to KiloJoule
        // example _Kilowatt = 1    KiloJoule = _Kilowatt * 3600; Result = 3600
        int valueSelector2 = (int) (userValue1 * 3600);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Kilowatt to Calorie
        // example _Kilowatt = 1    Calorie = _Kilowatt * 860420.6501; Result = 860420.65
        double valueSelector3 = (userValue1 * 860421);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // _Kilowatt to KiloCalorie
        // example _Kilowatt = 1    KiloCalorie = _Kilowatt *  860.4206501; Result =  860.42065
        double valueSelector4 = (userValue1 * 860.421);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // _Kilowatt to Watt_hour
        // example _Kilowatt = 1    Watt_hour = _Kilowatt * 1000; Result = 1000
        int valueSelector5 = (int) (userValue1 * 1000);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Kilowatt to Kilowatt_hour
        // example _Kilowatt = 1    Kilowatt_hour = _Kilowatt * 1; Result = 1
        int valueSelector6 = (int) (userValue1 * 1);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Kilowatt to Electron_volt
        // example _Kilowatt = 1    Electron_volt = _Kilowatt *  2.24694229E+25; Result =  2.2469E+25
        double valueSelector7 = (userValue1 * 2.247e+25);
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // _Kilowatt to Erg
        // example _Kilowatt = 1    Erg = _Kilowatt * 36000000000000; Result = 3.6000E+13
        double valueSelector8 = (userValue1 * 3.6e+13);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // _Kilowatt to British_thermal_unit
        // example _Kilowatt = 1     British_thermal_unit = _Kilowatt * 3414.4259497; Result = 3412.14164
        double valueSelector9 = (userValue1 * 3412.14);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // _Kilowatt to US_Therm
        // example _Kilowatt = 1    US_Therm = _Kilowatt * 0.0341295634; Result = 0.0341295634
        double valueSelector10 = (userValue1 /29.3);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // _Kilowatt to Inch-pound
        // example _Kilowatt = 1     Inch-pound = _Kilowatt * 31862684.85; Result =31862684.84976
        double valueSelector11 = (userValue1 * 31862684.85);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // _Kilowatt to Foot-pound
        // example _Kilowatt = 1    Foot-pound = _Kilowatt * 2655223.7375; Result =2655223.7375
        double valueSelector12 = (userValue1 * 2.655e+6);
        String _valueSelector12 = decimalScientificFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
    }

    private void update_Watt_hour() {
        // Watt_hour to Joule
        // example Watt_hour = 1   Joule = Watt_hour * 3600; Result =3600
        int valueSelector1 = (int) (userValue1 * 3600);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Watt_hour to KiloJoule
        // example Watt_hour = 1   KiloJoule = Watt_hour * 3.6; Result =3.6
        double valueSelector2 = (userValue1 * 3.6 );
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Watt_hour to Calorie
        // example Watt_hour = 1   Calorie = Watt_hour * 860.4206501; Result =860.4206501
        double valueSelector3 = (userValue1 * 860.421);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Watt_hour to KiloCalorie
        // example Watt_hour = 1   KiloCalorie = Watt_hour *0.8604206501; Result =0.8604206501
        double valueSelector4 = (userValue1 * 0.860421 );
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Watt_hour to Watt_hour
        // example Watt_hour = 1   Watt_hour = Watt_hour * 1; Result =1
        int valueSelector5 = (int) (userValue1 * 1);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Watt_hour to Kilowatt_hour
        // example Watt_hour = 1   Kilowatt_hour = Watt_hour * 0.001; Result =0.001
        double  valueSelector6 =  (userValue1 /1000);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Watt_hour to Electron_volt
        // example Watt_hour = 1   Electron_volt = Watt_hour * 2.24694229E+22; Result =2.24694229E+22
        double valueSelector7 = (userValue1 * 2.247E+22);
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // Watt_hour to Erg
        // example Watt_hour = 1   Erg = Watt_hour * 360000000; Result =360000000
        double valueSelector8 = (userValue1 * 3.6e+10);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // Watt_hour to British_thermal_unit
        // example Watt_hour = 1   British_thermal_unit = Watt_hour * 3.4144259497; Result =3.4144259497
        double valueSelector9 = (userValue1 * 3.412114);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Watt_hour to US_Therm
        // example Watt_hour = 1   US_Therm = Watt_hour * 0.0000341296; Result =0.0000341296
        double valueSelector10 = (userValue1 * 3.413e-5);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Watt_hour to Inch-pound
        // example Watt_hour = 1   Inch-pound = Watt_hour * 31862.68485; Result =31862.68485
        double valueSelector11 = (userValue1 * 31862.68485 );
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Watt_hour to Foot-pound
        // example Watt_hour = 1   Foot-pound = Watt_hour * 2655.2237375; Result =2655.2237375
        double valueSelector12 = (userValue1 * 2655.22);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
    }

    private void update_KiloCalorie() {
        // KiloCalorie to Joule
        // example KiloCalorie = 1  Joule = KiloCalorie * 4184; Result =4184
        int valueSelector1 = (int) (userValue1 * 4184);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // KiloCalorie to KiloJoule
        // example KiloCalorie = 1  KiloJoule = KiloCalorie * 4.184; Result =4.184
        double valueSelector2 = (userValue1 * 4.184);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // KiloCalorie to Calorie
        // example KiloCalorie = 1  Calorie = KiloCalorie * 1000; Result =1000
        int valueSelector3 = (int) (userValue1 * 1000);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // KiloCalorie to KiloCalorie
        // example KiloCalorie = 1  KiloCalorie = KiloCalorie * 1; Result =1
        int valueSelector4 = (int) (userValue1 * 1);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // KiloCalorie to Watt_hour
        // example KiloCalorie = 1  Watt_hour = KiloCalorie * 1.1622222; Result =1.1622222
        double valueSelector5 = (userValue1 * 1.16222);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // KiloCalorie to Kilowatt_hour
        // example KiloCalorie = 1  Kilowatt_hour = KiloCalorie * 0.0011622222; Result =0.0011622222
        double valueSelector6 = (userValue1 /860);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // KiloCalorie to Electron_volt
        // example KiloCalorie = 1  Electron_volt = KiloCalorie * 2.611446262E+22; Result =2.611446262E+22
        double valueSelector7 = (userValue1 * 2.611E+22);
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // KiloCalorie to Erg
        // example KiloCalorie = 1  Erg = KiloCalorie *41840000000; Result =4.1840E+10
        double valueSelector8 = (userValue1 *  4.184e+10);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // KiloCalorie to British_thermal_unit
        // example KiloCalorie = 1  British_thermal_unit = KiloCalorie * 3.9683217149; Result =3.9683217149
        double valueSelector9 = (userValue1 * 3.96567);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // KiloCalorie to US_Therm
        // example KiloCalorie = 1  US_Therm = KiloCalorie * 0.0000396661; Result =4E-5
        double valueSelector10 = (userValue1 * 3.9666e-5);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // KiloCalorie to Inch-pound
        // example KiloCalorie = 1  Inch-pound = KiloCalorie *  37031.520392; Result = 37031.520392
        double valueSelector11 = (userValue1 * 37031.520392);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // KiloCalorie to Foot-pound
        // example KiloCalorie = 1  Foot-pound = KiloCalorie * 3085.9600327; Result =3085.9600327
        double valueSelector12 = (userValue1 * 3085.96);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
    }

    private void update_Calorie() {
        // Calorie to Joule
        // example Calorie = 1  Joule = Calorie *4.184; Result =4.184
        double valueSelector1 =  (userValue1 * 4.184);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Calorie to KiloJoule
        // example Calorie = 1  KiloJoule = Calorie * 0.004184; Result =0.004184
        double valueSelector2 = (userValue1 /  239);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Calorie to Calorie
        // example Calorie = 1  Joule = Calorie *1; Result =1
        double valueSelector3 = (userValue1 * 1 );
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Calorie to KiloCalorie
        // example Calorie = 1  KiloCalorie = Calorie * 0.001; Result =0.001
        double valueSelector4 = (userValue1 /1000);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Calorie to Watt_hour
        // example Calorie = 1  Watt_hour = Calorie *0.0011622222; Result =0.0011622222
        double valueSelector5 = (userValue1 /860);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Calorie to Kilowatt_hour
        // example Calorie = 1  Kilowatt_hour = Calorie * 0.0000011622; Result =0.0000011622
        double valueSelector6 = (userValue1 * 1.1622e-6);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Calorie to Electron_volt
        // example Calorie = 1  Electron_volt = Calorie * 26114462623185002000; Result = 2.6114E+19
        double valueSelector7 =  (userValue1*(2.611 * Math.pow(10, 19)));
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // Calorie to Erg
        // example Calorie = 1  Erg = Calorie *41840000; Result =41840000
        double valueSelector8 = (userValue1 * 4.184e+7);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // Calorie to British_thermal_unit
        // example Calorie = 1  British_thermal_unit = Calorie *  0.0039683217; Result =0.00396567
        double valueSelector9 = (userValue1 /252);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Calorie to US_Therm
        // example Calorie = 1  US_Therm = Calorie * 3.966613702E-8; Result = 3.966613702E-8
        double valueSelector10 = (userValue1 * 3.9666e-8);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Calorie to Inch-pound
        // example Calorie = 1  Inch-pound = Calorie *37.031520392; Result =37.031520392
        double valueSelector11 = (userValue1 * 37.031520392);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Calorie to Foot-pound
        // example Calorie = 1   Foot-pound = Calorie *3.0859600327; Result =3.0859600327
        double valueSelector12 = (userValue1 * 3.08596);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
    }

    private void update_KiloJoule() {
        // KiloJoule to Joule
        // example KiloJoule= 1 Joule= KiloJoule * 1000;  result =1000
        int valueSelector1 = (int) (userValue1 * 1000);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // KiloJoule to KiloJoule
        // example KiloJoule= 1  KiloJoule= KiloJoule * 1;  result = 1
        int valueSelector2 = (int) (userValue1 * 1);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // KiloJouleKiloJoule to Calorie
        // example KiloJoule= 1  Calorie= KiloJoule *  239.005736;  result =  239.005736
        double valueSelector3 = (userValue1 * 239.006);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // KiloJoule to KiloCalorie
        // example KiloJoule= 1  KiloCalorie= KiloJoule * 0.2390057361;  result =0.2390057361
        double valueSelector4 = (userValue1 / 4.184);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // KiloJoule to Watt_hour
        // example KiloJoule= 1 Watt_hour= KiloJoule * 0.277777778;  result = 0.277777778
        double valueSelector5 = (userValue1 / 3.6);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // KiloJoule to Kilowatt_hour
        // example KiloJoule= 1  Kilowatt_hour = KiloJoule * 0.0002777778;  result =0.0002777778
        double valueSelector6 = (userValue1 / 3600);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // KiloJoule to Electron_volt
        // example KiloJoule= 1 Electron_volt = KiloJoule *  6.241506363E+21;  result = 6.241506363E+21
        double valueSelector7 = (userValue1 * 6.242E+21);
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // KiloJoule to Erg
        // example KiloJoule= 1  Erg= KiloJoule * 10000000000;  result = 10000000000
        double valueSelector8 = (userValue1 * 1e+10);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // KiloJoule to British_thermal_unit
        // example KiloJoule= 1  Foot-pound= KiloJoule * 0.9484516527;  result =0.9484516527
        double valueSelector9 = (userValue1 /  1.055);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // KiloJoule to US_Therm
        // example KiloJoule= 1  US_Therm= KiloJoule * 0.0000094804;  result = 737.562148
        double valueSelector10 = (userValue1 * 9.4804e-6);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // KiloJoule to Inch-pound
        // example KiloJoule= 1 Inch-pound = KiloJoule * 8850.7457916;  result = 8850.7457916
        double valueSelector11 = (userValue1 * 8850.7457916);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // KiloJoule to Foot-pound
        // example KiloJoule= 1  Foot-pound= KiloJoule * 737.5621493;  result = 737.5621493
        double valueSelector12 = (userValue1 * 737.562);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
    }

    private void update_Joule() {
        // Joule to Joule
        // example Joule= 1 Joule=Joule * 1;  result = 1
        int valueSelector1 = (int) (userValue1 * 1);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Joule to KiloJoule
        // example Joule= 1 KiloJoule=Joule * 0.001;  result = 0.001
        double valueSelector2 = (userValue1 / 1000);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Joule to Calorie
        // example Joule= 1 KiloJoule=Joule * 0.2390057361;  result =0.2390057361
        double valueSelector3 = (userValue1 /4.184);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Joule to KiloCalorie
        // example Joule= 1 KiloJoule=Joule * 0.0002390057;  result =0.0002390057
        double valueSelector4 = (userValue1/  4184);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Joule to Watt_hour
        // example Joule= 1 KiloJoule=Joule * 0.0002777778;  result =0.0002777778
        double valueSelector5 = (userValue1 / 3600);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Joule to Kilowatt_hour
        // example Joule= 1 KiloJoule=Joule * 2.777777777E-7;  result =2.777777777E-7
        double valueSelector6 = (userValue1 / 3.6e+6);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Joule to Electron_volt
        // example Joule= 1 KiloJoule=Joule * 6241506363094000000;  result =6.2415E+18
        double valueSelector7 = (userValue1 *  6.242e+18);
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // Joule to Erg
        // example Joule= 1 KiloJoule=Joule * 10000000;  result =10000000
        double valueSelector8 = (userValue1 * 1e+7);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // Joule to British_thermal_unit
        // example Joule= 1 KiloJoule=Joule *  0.0009484517;  result = 0.0009484517
        double valueSelector9 = (userValue1 /1055);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Joule to US_Therm
        // example Joule= 1 KiloJoule=Joule * 0.0009484517;  result = 9.480434279E-9
        double valueSelector10 = (userValue1 /1.055e+8);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Joule to Inch-pound
        // example Joule= 1 KiloJoule=Joule * 8.8507457916;  result =8.8507457916
        double valueSelector11 = (userValue1 * 8.8507457916);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Joule to Foot-pound
        // example Joule= 1 Foot-pound=Joule* 0.7375621493;  result = 0.7375621493
        double valueSelector12 = (userValue1 / 1.356);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
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

    // override method to responsible for responding correctly to the items specified in the menu resource file.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_menu,menu);
        return true;
    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){

        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        // toolbar onOptionItemSelected
        if (item.getItemId() == R.id.action_share) {
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
        String activity_result12;
        activity_result12 = result12.getText().toString();
        String User_enter_data;
        User_enter_data = UserInput.getText().toString();

        // above declared string are exported to share activity
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody =    "Results based on your input for "+ User_enter_data +" "+selectedItem + "\n"
                + "Joule (J) : " + activity_result  + "\n"
                + "Kilo joule (kJ): " + activity_result2 + "\n"
                + "Calorie (cal) : " + activity_result3 + "\n"
                + "Kilo calorie (Kcal):" + activity_result4+ "\n"
                + "Watt hour (KW-h): " + activity_result5 + "\n"
                + "Kilowatt hour (KW-h):" + activity_result6+ "\n"
                + "Electron volt (eV): " + activity_result7 + "\n"
                + "Erg (erg) : " + activity_result8 + "\n"
                + "British thermal unit (Btu):" + activity_result9+ "\n"
                + "US Therm (therm): " + activity_result10+ "\n"
                + "Inch-pound (in-lb): " + activity_result11 + ".\n"
                + "Foot-pound (ft-lb): " + activity_result12+ "\n";

        String shareSub = "Energy Converter Results Powered by Transpose Solutions Android App - Unit Converter ";
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