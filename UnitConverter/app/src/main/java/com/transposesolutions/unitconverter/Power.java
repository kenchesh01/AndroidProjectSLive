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

public class Power extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Declare the instance variable Ad view
    private AdView adView;
    private ActionBarDrawerToggle mToggle;
    EditText UserInput;
    TextView result1,result2,result3,result4,result5,result6,result7,result8,result9;
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

            }else{
                checkForChanges1();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);

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
                R.array.spinner_power_items, R.layout.spinner_selector_items);
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
                    watt();
                }else if(position==1){
                    kiloWatt();
                }else if(position==2){
                    megaWatt();
                }else if(position==3){
                    KiloCalorieSecond();
                }else if (position==4){
                    KiloCaloriHour();
                }else if(position==5){
                    HorsePower();
                } else if(position==6){
                    BritishThermalUnitHour();
                }else if(position==7){
                    TonOfRefrigeration();
                }else if(position==8){
                    CalorieSecond();
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
            case "Watt (W)":
                x = 1;
                break;
            case "Kilowatt (kW)":
                x = 2;
                break;
            case "Megawatt (MW)":
                x = 3;
                break;
            case "Kilocalorie/second (Kcal/s)":
                x = 4;
                break;
            case "Kilocalorie/hour (Kcal/h)":
                x = 5;
                break;
            case "Horsepower (hp)":
                x = 6;
                break;
            case "British thermal unit/hour (Btu/h)":
                x = 7;
                break;
            case "Ton of refrigeration (TR)":
                x = 8;
                break;
            case "Calorie/second (cal/s)":
                x = 9;
                break;
            default:
                break;
        }
        if(x==1){
            watt();
        }else if(x==2){
            kiloWatt();
        }else if(x==3){
            megaWatt();
        }else if(x==4){
            KiloCalorieSecond();
        }else if(x==5){
            KiloCaloriHour();
        }else if (x==6){
            HorsePower();
        }else if (x==8){
            BritishThermalUnitHour();
        }else if(x==9){
            TonOfRefrigeration();
        }else if(x==10){
            CalorieSecond();
        }
    }

    private void watt() {
        // watt to Watt (W)
        // example watt=1; Watt= Watt*1; Result=1
        double valueSelector1 =  (userValue1 * 1);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // watt to Kilowatt (kW)
        // example watt=1 , Kilowatt= Watt * 0.001; Result= 0.001
        double valueSelector2 =  (userValue1 /1000);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //watt to Megawatt (MW)
        // example watt=1 , Megawatt= Watt *  0.000001; Result= 0.000001
        double valueSelector3 =  (userValue1 /1e+6);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //watt to Kilocalorie/second (Kcal/s)
        // example watt=1 ,Kilocalorie/second (Kcal/s)= Watt *0.0002388459; Result=0.0002388459
        double valueSelector4 = (userValue1 * 0.0002390057);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //watt to Kilocalorie/hour (Kcal/h)
        // example watt=1 ,  Kilocalorie/hour (Kcal/h) = Watt *0.8604206501; Result=0.8604206501
        double valueSelector5 =  (userValue1 * 0.8604206501);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //watt to Horsepower (hp)
        // example watt=1 ,  Horsepower= Watt * 0.0013404053; Result=0.0013404053
        double valueSelector6 = (userValue1 * 0.0013596216);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);

        //watt to  British thermal unit/hour (Btu/h)
        // example watt=1 ,   British thermal unit/hour (Btu/h) = Watt *3.4144259497; Result=3.4144259497
        double valueSelector7 =  (userValue1 *3.4144259497);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //watt to Ton of refrigeration (TR)
        // example watt=1 ,  Ton of refrigeration (TR) = Watt *0.0002843451; Result=0.0002843451
        double valueSelector8 =  (userValue1 * 0.0002843451);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //watt to  Calorie/second (cal/s)
        // example watt=1 ,   Calorie/second (cal/s) = Watt *0.2390057361;  Result= 0.2390057361
        double valueSelector9 =  (userValue1 * 0.2390057361);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
    }

    private void kiloWatt() {
        // kiloWatt to Watt (W)
        // example kiloWatt=1; Watt= kiloWatt*1000; Result=1000
        int valueSelector1 = (int) (userValue1 * 1000);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // kiloWatt to Kilowatt (kW)
        // example kiloWatt=1 , Kilowatt= kiloWatt * 1; Result= 1
        double valueSelector2 =  (userValue1 *1);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //kiloWatt to Megawatt (MW)
        // example kiloWatt=1 , Megawatt= kiloWatt * 0.001; Result=  0.001
        double valueSelector3 =  (userValue1 * 0.001 );
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //kiloWatt to Kilocalorie/second (Kcal/s)
        // example kiloWatt=1 ,Kilocalorie/second (Kcal/s)= kiloWatt *0.2390057361; Result=0.2390057361
        double valueSelector4 = (userValue1 * 0.2390057361);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //kiloWatt to Kilocalorie/hour (Kcal/h)
        // example kiloWatt=1 ,  Kilocalorie/hour (Kcal/h) = kiloWatt * 860.4206501; Result=860.4206501
        double valueSelector5 =  (userValue1 * 860.4206501 );
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //kiloWatt to Horsepower (hp)
        // example kiloWatt=1 ,  Horsepower= kiloWatt * 1.3404053118; Result=1.3404053118
        double valueSelector6 = (userValue1 * 1.3596216173);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //kiloWatt to  British thermal unit/hour (Btu/h)
        // example kiloWatt=1 ,   British thermal unit/hour (Btu/h) = kiloWatt *3414.425949; Result=3414.425949
        double valueSelector7 =  (userValue1 * 3414.4259497);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //kiloWatt to Ton of refrigeration (TR)
        // example kiloWatt=1 ,  Ton of refrigeration (TR) = kiloWatt *0.2843451361; Result=0.2843451361
        double valueSelector8 =  (userValue1 * 0.2843451361);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //kiloWatt to  Calorie/second (cal/s)
        // example kiloWatt=1 ,   Calorie/second (cal/s) = kiloWatt *239.00573614;  Result=239.00573614
        double valueSelector9 =  (userValue1 *239.00573614);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
    }

    private void megaWatt() {
        // megaWatt to Watt (W)
        // example megaWatt=1; Watt= megaWatt*1000000; Result=1000000
        int valueSelector1 = (int) (userValue1 * 1000000);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // megaWatt to Kilowatt (kW)
        // example megaWatt=1 , Kilowatt= megaWatt * 1000; Result= 1000
        int valueSelector2 = (int) (userValue1 * 1000);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //watt to Megawatt (MW)
        // example megaWatt=1 , Megawatt= megaWatt *1; Result= *1
        double valueSelector3 =  (userValue1 *1 );
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //megaWatt to Kilocalorie/second (Kcal/s)
        // example megaWatt=1 ,Kilocalorie/second (Kcal/s)= megaWatt * 238.85 ; Result= 238.85
        double valueSelector4 = (userValue1 *  238.84589663 );
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //megaWatt to Kilocalorie/hour (Kcal/h)
        // example megaWatt=1 ,  Kilocalorie/hour (Kcal/h) = megaWatt * 859845.22; Result=859845.22
        double valueSelector5 =  (userValue1 * 859845.22786);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //megaWatt to Horsepower (hp)
        // example megaWatt=1 ,  Horsepower= megaWatt * 1341.0220; Result=1341.0220
        double valueSelector6 = (userValue1 * 1359.6216173);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //megaWatt to  British thermal unit/hour (Btu/h)
        // example megaWatt=1 ,   British thermal unit/hour (Btu/h) = megaWatt *3414425.949; Result=3414425.949
        double valueSelector7 =  (userValue1 * 3414425.9497);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //megaWatt to Ton of refrigeration (TR)
        // example megaWatt=1 ,  Ton of refrigeration (TR) = megaWatt *284.34513; Result=284.34513
        double valueSelector8 =  (userValue1 * 284.34513609);
        String _valueSelector8 =decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //megaWatt to  Calorie/second (cal/s)
        // example megaWatt=1 ,   Calorie/second (cal/s) = megaWatt *238845.9;  Result= 238845.9
        double valueSelector9 =  (userValue1 * 238845.89663);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
    }

    private void KiloCalorieSecond() {
        // KiloCalorieSecond to Watt (W)
        // example KiloCalorieSecond=1; Watt= KiloCalorieSecond*4186.8; Result=4186.8
        int valueSelector1 = (int) (userValue1 * 4184);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // KiloCalorieSecond to Kilowatt (kW)
        // example KiloCalorieSecond=1 , Kilowatt= KiloCalorieSecond * 418.68; Result= 418.68
        double valueSelector2 =  (userValue1 * 4.184);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //KiloCalorieSecond to Megawatt (MW)
        // example KiloCalorieSecond=1 , Megawatt= KiloCalorieSecond * 0.0041868; Result= * 0.0041868
        double valueSelector3 =  (userValue1 *0.004184);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //KiloCalorieSecond to Kilocalorie/second (Kcal/s)
        // example KiloCalorieSecond =1 ,Kilocalorie/second (Kcal/s)= KiloCalorieSecond *1; Result=1
        int valueSelector4 = (int) (userValue1 * 1);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //KiloCalorieSecond to Kilocalorie/hour (Kcal/h)
        // example KiloCalorieSecond=1 ,  Kilocalorie/hour (Kcal/h) = KiloCalorieSecond * 3600; Result=3600
        int valueSelector5 = (int) (userValue1 * 3600);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //KiloCalorieSecond to Horsepower (hp)
        // example KiloCalorieSecond=1 ,  Horsepower= KiloCalorieSecond * 5.61459; Result= 5.61459
        double valueSelector6 = (userValue1 * 5.6886568468);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //KiloCalorieSecond to  British thermal unit/hour (Btu/h)
        // example KiloCalorieSecond=1 ,   British thermal unit/hour (Btu/h) = KiloCalorieSecond *3.9686; Result=3.968
        double valueSelector7 =  (userValue1 *14285.958174);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //KiloCalorieSecond to Ton of refrigeration (TR)
        // example KiloCalorieSecond=1 ,  Ton of refrigeration (TR) = KiloCalorieSecond *1.19049; Result=1.19049
        double valueSelector8 =  (userValue1 * 1.1897000494);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //KiloCalorieSecond to  Calorie/second (cal/s)
        // example KiloCalorieSecond=1 ,   Calorie/second (cal/s) = KiloCalorieSecond *1000;  Result= 1000
        int valueSelector9 = (int) (userValue1 * 1000);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
    }

    private void KiloCaloriHour() {
        // KiloCaloriHour to Watt (W)
        // example KiloCaloriHour =1; Watt= KiloCaloriHour *1.1622222222; Result=1.1622222222
        double valueSelector1 =  (userValue1 * 1.1622222222);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // KiloCaloriHour to Kilowatt (kW)
        // example KiloCaloriHour=1 , Kilowatt= KiloCaloriHour * 0.001162222; Result= 0.001162222
        double valueSelector2 =  (userValue1 * 0.0011622222);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //KiloCaloriHour to Megawatt (MW)
        // example KiloCaloriHour=1 , Megawatt= KiloCaloriHour * 0.0000011622; Result= 0.0000011622
        double valueSelector3 =  (userValue1 * 0.0000011622);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //KiloCaloriHour to Kilocalorie/second (Kcal/s)
        // example KiloCaloriHour=1 ,Kilocalorie/second (Kcal/s)= KiloCaloriHour *0.0002777778  Result=0.0002777778
        double valueSelector4 = (userValue1 * 0.0002777778);
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //KiloCaloriHour to Kilocalorie/hour (Kcal/h)
        // example KiloCaloriHour=1 ,  Kilocalorie/hour (Kcal/h) = KiloCaloriHour * 1; Result=1
        int  valueSelector5 = (int) (userValue1 * 1);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //KiloCaloriHour to Horsepower (hp)
        // example KiloCaloriHour=1 ,  Horsepower= KiloCaloriHour * 0.0015578488; Result=0.0015578488
        double valueSelector6 = (userValue1 * 0.0015801825);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //KiloCaloriHour to  British thermal unit/hour (Btu/h)
        // example KiloCaloriHour=1 ,   British thermal unit/hour (Btu/h) = KiloCaloriHour *3.9683217149; Result=3.9683217149
        double valueSelector7 =  (userValue1 *3.9683217149);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //KiloCaloriHour to Ton of refrigeration (TR)
        // example KiloCaloriHour=1 ,  Ton of refrigeration (TR) = KiloCaloriHour *0.0003304722; Result=0.0003304722
        double valueSelector8 =  (userValue1 * 0.0003304722);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //KiloCaloriHour to  Calorie/second (cal/s)
        // example KiloCaloriHour=1 ,   Calorie/second (cal/s) = KiloCaloriHour *0.2777777778;  Result= 0.2777777778
        double valueSelector9 =  (userValue1 * 0.2777777778);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
    }

    private void HorsePower() {
        // HorsePower to Watt (W)
        // example HorsePower=1; Watt= HorsePower*746.043; Result=746.043
        double valueSelector1 =  (userValue1 * 746.043);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // HorsePower to Kilowatt (kW)
        // example HorsePower=1 , Kilowatt= HorsePower *0.746043; Result= 0.746043
        double valueSelector2 =  (userValue1 * 0.746043);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //HorsePower to Megawatt (MW)
        // example HorsePower=1 , Megawatt= HorsePower * 0.000746043; Result= 0.000746043
        double valueSelector3 =  (userValue1 * 0.000746043);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //HorsePower to Kilocalorie/second (Kcal/s)
        // example HorsePower=1 ,Kilocalorie/second (Kcal/s)= HorsePower *0.1783085564; Result=0.1783085564
        double valueSelector4 = (userValue1 * 0.1783085564);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //HorsePower to Kilocalorie/hour (Kcal/h)
        // example HorsePower=1 ,  Kilocalorie/hour (Kcal/h) = HorsePower *641.91080306; Result=641.91080306
        double valueSelector5 =  (userValue1 * 641.91080306);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //HorsePower to Horsepower (hp)
        // example HorsePower=1 ,  Horsepower= HorsePower * 1; Result=1
        int  valueSelector6 = (int) (userValue1 * 1);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //HorsePower to  British thermal unit/hour (Btu/h)
        // example HorsePower=1 ,   British thermal unit/hour (Btu/h) = HorsePower *2544.4335; Result=2544.4335
        double valueSelector7 =  (userValue1 *2547.3085788);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //HorsePower to Ton of refrigeration (TR)
        // example HorsePower=1 ,  Ton of refrigeration (TR) = HorsePower *0.212036; Result=0.212036
        double valueSelector8 =  (userValue1 * 0.2121336984);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //HorsePower to  Calorie/second (cal/s)
        // example HorsePower=1 ,   Calorie/second (cal/s) = HorsePower *178.10735;  Result= 178.10735
        double valueSelector9 =  (userValue1 * 178.30855641);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
    }

    private void BritishThermalUnitHour() {
        // BritishThermalUnitHour to Watt (W)
        // example BritishThermalUnitHour=1; BritishThermalUnitHour= Watt* 0.29307; Result= 0.29307
        double valueSelector1 =  (userValue1 * 0.292875);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // BritishThermalUnitHour to Kilowatt (kW)
        // example BritishThermalUnitHour=1 , Kilowatt= BritishThermalUnitHour * 0.00023; Result= 0.00023
        double valueSelector2 =  (userValue1 * 0.000292875);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //BritishThermalUnitHour to Megawatt (MW)
        // example BritishThermalUnitHour=1 , Megawatt= BritishThermalUnitHour * 2.9307e-7; Result= 2.9307e-7
        double valueSelector3 =  (userValue1 * 2.928749999E-7);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //BritishThermalUnitHour to Kilocalorie/second (Kcal/s)
        // example BritishThermalUnitHour=1 ,Kilocalorie/second (Kcal/s)= BritishThermalUnitHour *0.0000699; Result=0.0000699
        double valueSelector4 = (userValue1 * 0.0000699988);
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //BritishThermalUnitHour to Kilocalorie/hour (Kcal/h)
        // example BritishThermalUnitHour=1 ,  Kilocalorie/hour (Kcal/h) = BritishThermalUnitHour *0.25199; Result=0.25199
        double valueSelector5 =  (userValue1 * 0.2519956979);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //BritishThermalUnitHour to Horsepower (hp)
        // example BritishThermalUnitHour=1 ,  Horsepower= BritishThermalUnitHour * 0.00039 ; Result=0.00039
        double valueSelector6 = (userValue1 *0.0003981992);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //BritishThermalUnitHour to  British thermal unit/hour (Btu/h)
        // example BritishThermalUnitHour=1 ,   British thermal unit/hour (Btu/h) = BritishThermalUnitHour *1; Result=1
        int valueSelector7 = (int) (userValue1 *1);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //BritishThermalUnitHour to Ton of refrigeration (TR)
        // example BritishThermalUnitHour=1 ,  Ton of refrigeration (TR) = BritishThermalUnitHour * 0.000083; Result=0.000083
        double valueSelector8 =  (userValue1 * 0.0000832776);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //BritishThermalUnitHour to  Calorie/second (cal/s)
        // example BritishThermalUnitHour=1 ,   Calorie/second (cal/s) = BritishThermalUnitHour *0.069998;  Result= 0.069998
        double valueSelector9 =  (userValue1 * 0.069998805);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
    }

    private void TonOfRefrigeration() {
        // TonOfRefrigeration to Watt (W)
        // example TonOfRefrigeration=1; Watt= TonOfRefrigeration*3516.8528421; Result=3516.8528421
        double valueSelector1 =  (userValue1 * 3516.8528421);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // TonOfRefrigeration to Kilowatt (kW)
        // example TonOfRefrigeration=1 , Kilowatt= TonOfRefrigeration * 3.5168528421; Result= 3.5168528421
        double valueSelector2 =  (userValue1 * 3.5168528421);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //TonOfRefrigeration to Megawatt (MW)
        // example TonOfRefrigeration=1 , Megawatt= TonOfRefrigeration *0.0035168528  Result= 0.0035168528
        double valueSelector3 =  (userValue1 * 0.0035168528);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //TonOfRefrigeration to Kilocalorie/second (Kcal/s)
        // example TonOfRefrigeration=1 ,Kilocalorie/second (Kcal/s)= TonOfRefrigeration * 0.8405480024; Result=0.8405480024
        double valueSelector4 = (userValue1 * 0.8405480024);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //TonOfRefrigeration to Kilocalorie/hour (Kcal/h)
        // example TonOfRefrigeration=1 ,  Kilocalorie/hour (Kcal/h) = TonOfRefrigeration * 3025.9728087; Result=3023.9491
        double valueSelector5 =  (userValue1 * 3025.9728087);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //TonOfRefrigeration to Horsepower (hp)
        // example TonOfRefrigeration=1 ,  Horsepower= TonOfRefrigeration * 4.7140082302; Result= 4.7140082302
        double valueSelector6 = (userValue1 * 4.781589149);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //TonOfRefrigeration to  British thermal unit/hour (Btu/h)
        // example TonOfRefrigeration=1 ,   British thermal unit/hour (Btu/h) = TonOfRefrigeration *12008.03360; Result=12008.03360
        double valueSelector7 =  (userValue1 *12008.033605);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //TonOfRefrigeration to Ton of refrigeration (TR)
        // example TonOfRefrigeration=1 ,  Ton of refrigeration (TR) = TonOfRefrigeration *1; Result=1
        int valueSelector8 = (int) (userValue1 * 1);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //TonOfRefrigeration to  Calorie/second (cal/s)
        // example TonOfRefrigeration=1 ,   Calorie/second (cal/s) = TonOfRefrigeration *840.54800241;  Result=840.54800241
        double valueSelector9 =  (userValue1 * 840.54800241);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
    }

    private void CalorieSecond() {
        // CalorieSecond to Watt (W)
        // example CalorieSecond=1; Watt= CalorieSecond*4.184; Result= 4.184
        double valueSelector1 =  (userValue1 * 4.184);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // CalorieSecond to Kilowatt (kW)
        // example CalorieSecond=1 , Kilowatt= CalorieSecond * 0.004184; Result=0.004184
        double valueSelector2 =  (userValue1 * 0.004184);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //CalorieSecond to Megawatt (MW)
        // example CalorieSecond=1 , Megawatt= CalorieSecond * 0.000004184; Result= 0.000004184
        double valueSelector3 =  (userValue1 * 0.000004184);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //CalorieSecond to Kilocalorie/second (Kcal/s)
        // example CalorieSecond=1 ,Kilocalorie/second (Kcal/s)= CalorieSecond *0.001; Result=0.001
        double valueSelector4 = (userValue1 * 0.001);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //CalorieSecond to Kilocalorie/hour (Kcal/h)
        // example CalorieSecond=1 ,  Kilocalorie/hour (Kcal/h) = CalorieSecond * 3.6 ; Result=3.6
        double valueSelector5 =  (userValue1 * 3.6 );
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //CalorieSecond to Horsepower (hp)
        // example CalorieSecond=1 ,  Horsepower= CalorieSecond * 0.0056082558; Result=0.0056082558
        double valueSelector6 = (userValue1 * 0.0056886568);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //CalorieSecond to  British thermal unit/hour (Btu/h)
        // example CalorieSecond=1 ,   British thermal unit/hour (Btu/h) = CalorieSecond * 14.28595817; Result= 14.28595817
        double valueSelector7 =  (userValue1 *14.285958174);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //CalorieSecond to Ton of refrigeration (TR)
        // example CalorieSecond=1 ,  Ton of refrigeration (TR) = CalorieSecond *0.0011897; Result=0.0011897
        double valueSelector8 =  (userValue1 *0.0011897);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //CalorieSecond to  Calorie/second (cal/s)
        // example CalorieSecond=1 ,   Calorie/second (cal/s) = CalorieSecond *1;  Result= 1
        int valueSelector9 = (int) (userValue1 * 1);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
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

        String User_enter_data;
        User_enter_data = UserInput.getText().toString();

        // above declared string are exported to share activity
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody =  "Results based on your input for "+ User_enter_data +" "+selectedItem + "\n"
                + "Watt (W) : " + activity_result + "\n"
                + "Kilowatt (kW) " + activity_result2 + "\n"
                + "Megawatt (MW):" + activity_result3+ "\n"
                + "Kilocalorie/second (Kcal/s): " + activity_result4 + "\n"
                + "Kilocalorie/hour (Kcal/h): " + activity_result5  + "\n"
                + " Horsepower (hp) :" + activity_result6+ "\n"
                + " British thermal unit/hour (Btu/h)  : " + activity_result7  + "\n"
                + "Ton of refrigeration (TR) :" + activity_result8+ "\n"
                + "Calorie/second (cal/s) : " + activity_result9 + "\n";


        String shareSub = "Power Converter Results Powered by Transpose Solutions Android App - Unit Converter ";
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