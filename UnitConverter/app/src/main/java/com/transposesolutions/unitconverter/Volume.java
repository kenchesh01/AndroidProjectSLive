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

public class Volume extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Declare the instance variable Ad view
    private AdView adView;
    private ActionBarDrawerToggle mToggle;
    EditText UserInput;
    TextView result1,result2,result3,result4,result5,result6,result7,result8,result9,result10,result11,result12,result13,result14,result15,result16,result17;
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
                result13.setText("");
                result14.setText("");
                result15.setText("");
                result16.setText("");
                result17.setText("");
            }else{
                checkForChanges1();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);
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
        result13=findViewById(R.id.Result_13);
        result14=findViewById(R.id.Result_14);
        result15=findViewById(R.id.Result_15);
        result16=findViewById(R.id.Result_16);
        result17=findViewById(R.id.Result_17);

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
                R.array.spinner_volume_items, R.layout.spinner_selector_items);
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
                    Milliliter_update();
                }else if(position==1){
                    Liter_update();
                }else if(position==2){
                    Cubic_meter_update();
                }else if(position==3){
                    Cubic_inch_update();
                }else if(position==4){
                    Cubic_foot_update();
                }else if(position==5){
                    Cubic_yard_update();
                }else if(position==6){
                    Fluid_ounce_US_update();
                }else if(position==7){
                    Fluid_ounce_UK_update();
                }else if(position==8){
                    Cup_US_update();
                }else if(position==9){
                    Cup_update();
                }else if(position==10){
                    Pint_liquid_US_update();
                }else if(position==11){
                    Pint_liquid_UK_update();
                }else if(position==12){
                    Quart_liquid_US_update();
                }else if(position==13){
                    Quart_liquid_UK_update();
                } else if(position==14){
                    Gallon_US_update();
                }else if(position==15){
                    Gallon_UK_update();
                }else if(position==16){
                    Barell_US_update();
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
            userValue1 = Double.parseDouble(activityTextValue1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String spinnerValue = String.valueOf(selector_1.getSelectedItem());
        switch (spinnerValue) {
            case "Milliliter (mL)":
                x = 1;
                break;
            case "Liter (L)":
                x = 2;
                break;
            case "Cubic meter (m3)":
                x = 3;
                break;
            case "Cubic inch (in3)":
                x = 4;
                break;
            case "Cubic foot (ft3)":
                x = 5;
                break;
            case "Cubic yard (yd3)":
                x = 6;
                break;
            case "Fluid ounce - US (fl oz)":
                x = 7;
                break;
            case "Fluid ounce - UK (fl oz)":
                x = 8;
                break;
            case "Cup - US (c)":
                x = 9;
                break;
            case "Cup (c)":
                x = 10;
                break;
            case "Pint, liquid -US (pt)":
                x = 11;
                break;
            case "Pint, liquid -UK (pt)":
                x = 12;
                break;
            case "Quart, liquid - US (qt)":
                x = 13;
                break;
            case "Quart, liquid - UK (qt)":
                x = 14;
                break;
            case "Gallon – US (gal)":
                x = 15;
                break;
            case "Gallon – UK (gal)":
                x = 16;
                break;
            case "Barell – US (bbl)":
                x = 17;
                break;
            default:
                break;
        }
        if(x==1){
            Milliliter_update();
        }else if (x==2) {
            Liter_update();
        }else if (x==3) {
            Cubic_meter_update();
        }else if (x==4) {
            Cubic_inch_update();
        }else if (x==5) {
            Cubic_foot_update();
        }else if (x==6) {
            Cubic_yard_update();
        }else if (x==7) {
            Fluid_ounce_US_update();
        }else if (x==8) {
            Fluid_ounce_UK_update();
        }else if (x==9) {
            Cup_US_update();
        }else if (x==10) {
            Cup_update();
        } else if (x==11) {
            Pint_liquid_US_update();
        }else if (x==12) {
            Pint_liquid_UK_update();
        }else if (x==13) {
            Quart_liquid_US_update();
        }else if (x==14) {
            Quart_liquid_UK_update();
        } else if (x==15) {
            Gallon_US_update();
        }else if (x==16) {
            Gallon_UK_update();
        }else if (x==17) {
            Barell_US_update();
        }
    }

    private void Milliliter_update() {
        //Milliliter to Milliliter

        int valueSelector1 = (int) (userValue1 * 1);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Milliliter to Liter

        double valueSelector2 =  (userValue1 /1000);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Milliliter to Cubic_meter

        double valueSelector3 =  (userValue1 /1e+6);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Milliliter to Cubic_inch

        double valueSelector4 = (userValue1 /16.387);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Milliliter to Cubic_foot

        double valueSelector5 = (userValue1 * 3.5315e-5);
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Milliliter to Cubic_yard

        double valueSelector6 =  (userValue1 * 0.000001308);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Milliliter to Fluid_ounce_US

        double valueSelector7 =  (userValue1 /29.574 );
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Milliliter to Fluid_ounce_UK

        double valueSelector8 =  (userValue1 /28.413);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Milliliter to Cup_US

        double valueSelector9 =  (userValue1 /240);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Milliliter to Cup

        double valueSelector10 =  (userValue1 /284 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //milli liter to   Pint_liquid_US

        double valueSelector11 =  (userValue1 /473);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Milliliter to  Pint_liquid_UK

        double valueSelector12 =  (userValue1/568 );
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Milliliter to  Quart_liquid_US

        double valueSelector13 =  (userValue1 /946 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Milliliter to  Quart_liquid_UK

        double valueSelector14 =  (userValue1 /1137);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Milliliter to   Gallon_US

        double valueSelector15 =  (userValue1 /3785);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Milliliter to  Gallon_UK

        double valueSelector16 =  (userValue1 /4546);
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Milliliter to  Barrel_US

        double valueSelector17 =  (userValue1 * 6.2898e-6);
        String _valueSelector17 = decimalScientificFormat.format(valueSelector17);
        result17.setText(_valueSelector17);


    }

    private void Liter_update() {
        //Liter to Milliliter

        int valueSelector1 = (int) (userValue1 * 1000);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Liter to Liter

        int valueSelector2 = (int) (userValue1 * 1);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Liter to Cubic_meter

        double valueSelector3 =  (userValue1 /1000);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Liter to Cubic_inch

        double valueSelector4 = (userValue1 * 61.0237);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Liter to Cubic_foot

        double valueSelector5 = (userValue1 / 28.317);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Liter to Cubic_yard

        double valueSelector6 =  (userValue1 * 0.0013079506);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Liter to Fluid_ounce_US

        double valueSelector7 =  (userValue1 * 33.814 );
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Liter to Fluid_ounce_UK

        double valueSelector8 =  (userValue1 * 35.1951);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Liter to Cup_US

        double valueSelector9 =  (userValue1 * 4.16667);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Liter to Cup

        double valueSelector10 =  (userValue1 * 3.51951 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Liter to Pint_liquid_US

        double valueSelector11 =  (userValue1 * 2.11338);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Liter to  Pint_liquid_UK

        double valueSelector12 =  (userValue1 * 1.75975);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Liter to  Quart_liquid_US

        double valueSelector13 =  (userValue1 * 1.05669 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Liter to  Quart_liquid_UK

        double valueSelector14 =  (userValue1 /1.137);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Liter to   Gallon_US

        double valueSelector15 =  (userValue1 /3.785);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Liter to  Gallon_UK

        double valueSelector16 =  (userValue1 /4.546 );
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Liter to  Barrel_US

        double valueSelector17 =  (userValue1/159);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);

    }

    private void Cubic_meter_update() {
        //Cubic_meter to Milliliter

        double valueSelector1 =  (userValue1 * 1e+6);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Cubic_meter to Liter

        int valueSelector2 = (int) (userValue1 * 1000);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Cubic_meter to Cubic_meter

        int  valueSelector3 = (int) (userValue1 * 1);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Cubic_meter to Cubic_inch

        double valueSelector4 = (userValue1 * 61023.7);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Cubic_meter to Cubic_foot

        double valueSelector5 = (userValue1 * 35.3147);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Cubic_meter to Cubic_yard

        double valueSelector6 =  (userValue1 * 1.30795);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Cubic_meter to Fluid_ounce_US

        double valueSelector7 =  (userValue1 * 33814 );
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Cubic_meter to Fluid_ounce_UK

        double valueSelector8 =  (userValue1 * 35195.1);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Cubic_meter to Cup_US

        double valueSelector9 =  (userValue1 * 4166.67);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Cubic_meter to Cup

        double valueSelector10 =  (userValue1 * 3519.51 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //Cubic_meter to  Pint_liquid_US

        double valueSelector11 =  (userValue1 * 2113.38);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Cubic_meter to  Pint_liquid_UK

        double valueSelector12 =  (userValue1 * 1759.75);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Cubic_meter to  Quart_liquid_US

        double valueSelector13 =  (userValue1 * 1056.69 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Cubic_meter to  Quart_liquid_UK

        double valueSelector14 =  (userValue1 * 879.877);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Cubic_meter to   Gallon_US

        double valueSelector15 =  (userValue1 *264.172);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Cubic_meter to  Gallon_UK

        double valueSelector16 =  (userValue1 * 219.969 );
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Cubic_meter to  Barrel_US

        double valueSelector17 =  (userValue1 * 6.28981);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);

    }

    private void Cubic_inch_update() {
        //Cubic_inch to Milliliter

        double valueSelector1 =  (userValue1 * 16.3871);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Cubic_inch to Liter

        double valueSelector2 =  (userValue1 /61.024);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Cubic_inch to Cubic_meter

        double valueSelector3 =  (userValue1 * 1.6387e-5);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Cubic_inch to Cubic_inch

        int valueSelector4 = (int) (userValue1 * 1);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Cubic_inch to Cubic_foot

        double valueSelector5 = (userValue1 /1728);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Cubic_inch to Cubic_yard

        double valueSelector6 =  (userValue1 * 0.0000214335);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Cubic_inch to Fluid_ounce_US

        double valueSelector7 =  (userValue1 /1.805 );
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Cubic_inch to Fluid_ounce_UK

        double valueSelector8 =  (userValue1 /1.734);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Cubic_inch to Cup_US

        double valueSelector9 =  (userValue1 /14.646);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Cubic_inch to Cup

        double valueSelector10 =  (userValue1 /17.339 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Cubic_inch to Pint_liquid_US

        double valueSelector11 =  (userValue1 /28.875);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Cubic_inch to  Pint_liquid_UK

        double valueSelector12 =  (userValue1 /34.677);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Cubic_inch to  Quart_liquid_US

        double valueSelector13 =  (userValue1 /57.75 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Cubic_inch to  Quart_liquid_UK

        double valueSelector14 =  (userValue1 /69.355);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Cubic_inch to   Gallon_US

        double valueSelector15 =  (userValue1 /231);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Cubic_inch to  Gallon_UK

        double valueSelector16 =  (userValue1 /277 );
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Cubic_inch to  Barrel_US

        double valueSelector17 =  (userValue1 /9702);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);

    }

    private void Cubic_foot_update() {
        //Cubic_foot to Milliliter

        double valueSelector1 =  (userValue1 * 28316.8);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Cubic_foot to Liter

        double valueSelector2 =  (userValue1 * 28.3168);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Cubic_foot to Cubic_meter

        double valueSelector3 =  (userValue1 /35.315);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Cubic_foot to Cubic_inch

        int valueSelector4 = (int) (userValue1 * 1728);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Cubic_foot to Cubic_foot

        int valueSelector5 = (int) (userValue1 * 1);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Cubic_foot to Cubic_yard

        double valueSelector6 =  (userValue1 * 0.037037037);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Cubic_foot to Fluid_ounce_US

        double valueSelector7 =  (userValue1 * 957.506 );
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Cubic_foot to Fluid_ounce_UK

        double valueSelector8 =  (userValue1 * 996.614);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Cubic_foot to Cup_US

        double valueSelector9 =  (userValue1 * 117.987);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Cubic_foot to Cup

        double valueSelector10 =  (userValue1 * 99.6614 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //Cubic_foot to  Pint_liquid_US

        double valueSelector11 =  (userValue1 * 59.8442);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Cubic_foot to  Pint_liquid_UK

        double valueSelector12 =  (userValue1 * 49.8307);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Cubic_foot to  Quart_liquid_US

        double valueSelector13 =  (userValue1 * 29.9221 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Cubic_foot to  Quart_liquid_UK

        double valueSelector14 =  (userValue1 * 24.9153);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Cubic_foot to   Gallon_US

        double valueSelector15 =  (userValue1 * 7.48052);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Cubic_foot to  Gallon_UK

        double valueSelector16 =  (userValue1 * 6.22884 );
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Cubic_foot to  Barrel_US

        double valueSelector17 =  (userValue1 /5.615);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);

    }

    private void Cubic_yard_update() {
        //Cubic_yard to Milliliter

        double valueSelector1 =  (userValue1 * 764554.85798);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Cubic_yard to Liter

        double valueSelector2 =  (userValue1 * 764.55485798);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Cubic_yard to Cubic_meter

        double valueSelector3 =  (userValue1 * 0.764554858);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Cubic_yard to Cubic_inch

        int  valueSelector4 = (int) (userValue1 * 46656);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Cubic_yard to Cubic_foot

        int valueSelector5 = (int) (userValue1 * 27);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Cubic_yard to Cubic_yard

        int valueSelector6 = (int) (userValue1 * 1);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Cubic_yard to Fluid_ounce_US

        double valueSelector7 =  (userValue1 * 25852.687509 );
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Cubic_yard to Fluid_ounce_UK

        double valueSelector8 =  (userValue1 * 26908.569183);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Cubic_yard to Cup_US

        double valueSelector9 =  (userValue1 * 3231.5859386);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Cubic_yard to Cup

        double valueSelector10 =  (userValue1 * 2690.8569 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Cubic_yard to Pint_liquid_US

        double valueSelector11 =  (userValue1 * 1615.7929693);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Cubic_yard to  Pint_liquid_UK

        double valueSelector12 =  (userValue1 * 1345.4284592);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Cubic_yard to  Quart_liquid_US

        double valueSelector13 =  (userValue1 * 807.89648464 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Cubic_yard to  Quart_liquid_UK

        double valueSelector14 =  (userValue1 * 672.71422958	);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Cubic_yard to   Gallon_US

        double valueSelector15 =  (userValue1 * 201.97412116);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Cubic_yard to  Gallon_UK

        double valueSelector16 =  (userValue1 * 168.17855739 );
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Cubic_yard to  Barrel_US

        double valueSelector17 =  (userValue1 * 6.5153);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);

    }

    private void Fluid_ounce_US_update() {
        //Fluid_ounce_US to Milliliter

        double valueSelector1 =  (userValue1 * 29.5735);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Fluid_ounce_US to Liter

        double valueSelector2 =  (userValue1 /33.814);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Fluid_ounce_US to Cubic_meter

        double valueSelector3 =  (userValue1 * 2.9574e-5);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Fluid_ounce_US to Cubic_inch

        double valueSelector4 = (userValue1 * 1.80469);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Fluid_ounce_US to Cubic_foot

        double valueSelector5 = (userValue1 /958);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Fluid_ounce_US to Cubic_yard

        double valueSelector6 =  (userValue1 * 0.0000386807);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Fluid_ounce_US to Fluid_ounce_US

        int valueSelector7 = (int) (userValue1 * 1);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Fluid_ounce_US to Fluid_ounce_UK

        double valueSelector8 =  (userValue1 * 1.04084);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Fluid_ounce_US to Cup_US

        double valueSelector9 =  (userValue1 /8.115);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Fluid_ounce_US to Cup

        double valueSelector10 =  (userValue1 /9.608 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //Fluid_ounce_US to  Pint_liquid_US

        double valueSelector11 =  (userValue1 /16);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Fluid_ounce_US to  Pint_liquid_UK

        double valueSelector12 =  (userValue1 / 19.215);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Fluid_ounce_US to  Quart_liquid_US

        double valueSelector13 =  (userValue1 /32 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Fluid_ounce_US to  Quart_liquid_UK

        double valueSelector14 =  (userValue1 /38.43);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Fluid_ounce_US to   Gallon_US

        double valueSelector15 =  (userValue1  /128);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Fluid_ounce_US to  Gallon_UK

        double valueSelector16 =  (userValue1 /154 );
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Fluid_ounce_US to  Barrel_US

        double valueSelector17 =  (userValue1 /5376);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);

    }
    private void Fluid_ounce_UK_update() {
        //Fluid_ounce_UK to Milliliter

        double valueSelector1 =  (userValue1 * 28.4131);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Fluid_ounce_UK to Liter

        double valueSelector2 =  (userValue1 /35.195);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Fluid_ounce_UK to Cubic_meter

        double valueSelector3 =  (userValue1  * 2.8413e-5);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Fluid_ounce_UK to Cubic_inch

        double valueSelector4 = (userValue1 *  1.73387);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Fluid_ounce_UK to Cubic_foot

        double valueSelector5 = (userValue1 /997);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Fluid_ounce_UK to Cubic_yard

        double valueSelector6 =  (userValue1 * 0.0000371629);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Fluid_ounce_UK to Fluid_ounce_US

        double valueSelector7 =  (userValue1  /1.041 );
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Fluid_ounce_UK to Fluid_ounce_UK

        int valueSelector8 = (int) (userValue1 * 1);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Fluid_ounce_UK to Cup_US

        double valueSelector9 =  (userValue1 /8.447);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Fluid_ounce_UK to Cup

        double valueSelector10 =  (userValue1 /10 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Fluid_ounce_UK to Pint_liquid_US

        double valueSelector11 =  (userValue1 /16.653);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Fluid_ounce_UK to  Pint_liquid_UK

        double valueSelector12 =  (userValue1 /20);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Fluid_ounce_UK to  Quart_liquid_US

        double valueSelector13 =  (userValue1 /33.307);
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Fluid_ounce_UK to  Quart_liquid_UK

        double valueSelector14 =  (userValue1 /20);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Fluid_ounce_UK to   Gallon_US

        double valueSelector15 =  (userValue1 /133);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Fluid_ounce_UK to  Gallon_UK

        double valueSelector16 =  (userValue1 /160);
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Fluid_ounce_UK to  Barrel_US

        double valueSelector17 =  (userValue1 /5596);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);


    }

    private void Cup_US_update() {
        //Cup_US to Milliliter

        double valueSelector1 =  (userValue1 * 240);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Cup_US to Liter

        double valueSelector2 =  (userValue1 /4.167);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Cup_US to Cubic_meter

        double valueSelector3 =  (userValue1 /4167);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Cup_US to Cubic_inch

        double valueSelector4 = (userValue1 * 14.6457);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Cup_US to Cubic_foot

        double valueSelector5 = (userValue1 /118);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Cup_US to Cubic_yard

        double valueSelector6 =  (userValue1 * 0.0003094456);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Cup_US to Fluid_ounce_US

        double valueSelector7 =  (userValue1 * 8.11537);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Cup_US to Fluid_ounce_UK

        double valueSelector8 =  (userValue1 * 8.44682);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Cup_US to Cup_US

        int valueSelector9 = (int) (userValue1 * 1);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Cup_US to Cup

        double valueSelector10 =  (userValue1 /1.184 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //Cup_US to  Pint_liquid_US

        double valueSelector11 =  (userValue1 /1.972);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Cup_US to  Pint_liquid_UK

        double valueSelector12 =  (userValue1 /2.368);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Cup_US to  Quart_liquid_US

        double valueSelector13 =  (userValue1 /3.943 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Cup_US to  Quart_liquid_UK

        double valueSelector14 =  (userValue1 /4.736);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Cup_US to   Gallon_US

        double valueSelector15 =  (userValue1 /15.773);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Cup_US to  Gallon_UK

        double valueSelector16 =  (userValue1 /18.942 );
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Cup_US to  Barrel_US

        double valueSelector17 =  (userValue1 /662);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);


    }

    private void Cup_update() {
        //Cup to Milliliter

        double valueSelector1 =  (userValue1 * 284.131);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Cup to Liter

        double valueSelector2 =  (userValue1 /3.52);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Cup to Cubic_meter

        double valueSelector3 =  (userValue1 /3520);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Cup to Cubic_inch

        double valueSelector4 = (userValue1 * 17.3387);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Cup to Cubic_foot

        double valueSelector5 = (userValue1 /99.661);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Cup to Cubic_yard

        double valueSelector6 =  (userValue1 * 0.0003716288);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Cup to Fluid_ounce_US

        double valueSelector7 =  (userValue1 * 9.6076 );
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Cup to Fluid_ounce_UK

        int valueSelector8 = (int) (userValue1 * 10);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Cup to Cup_US

        double valueSelector9 =  (userValue1 * 1.18388);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Cup to Cup

        int valueSelector10 = (int) (userValue1 * 1);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Cup to Pint_liquid_US

        double valueSelector11 =  (userValue1 /1.665);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Cup to  Pint_liquid_UK

        double valueSelector12 =  (userValue1 /2 );
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Cup to  Quart_liquid_US

        double valueSelector13 =  (userValue1 /3.331 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Cup to  Quart_liquid_UK

        double valueSelector14 =  (userValue1 /4);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Cup to   Gallon_US

        double valueSelector15 =  (userValue1 /13.323);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Cup to  Gallon_UK

        double valueSelector16 =  (userValue1 /16 );
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Cup to  Barrel_US

        double valueSelector17 =  (userValue1 /560);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);


    }
    private void Pint_liquid_US_update() {
        //Pint_liquid_US to Milliliter

        double valueSelector1 =  (userValue1 * 473.176);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Pint_liquid_US to Liter

        double valueSelector2 =  (userValue1 /2.113);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Pint_liquid_US to Cubic_meter

        double valueSelector3 =  (userValue1 /2113);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Pint_liquid_US to Cubic_inch

        double valueSelector4 = (userValue1 * 28.875);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Pint_liquid_US to Cubic_foot

        double valueSelector5 = (userValue1 /59.844);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Pint_liquid_US to Cubic_yard

        double valueSelector6 =  (userValue1* 0.0006188912);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Pint_liquid_US to Fluid_ounce_US

        int valueSelector7 = (int) (userValue1 * 16);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Pint_liquid_US to Fluid_ounce_UK

        double valueSelector8 =  (userValue1 * 16.6535);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Pint_liquid_US to Cup_US

        double valueSelector9 =  (userValue1 * 1.97157);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Pint_liquid_US to Cup

        double valueSelector10 =  (userValue1 * 1.66535 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Pint_liquid_US to Pint_liquid_US

        int valueSelector11 = (int) (userValue1 * 1);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Pint_liquid_US to  Pint_liquid_UK

        double valueSelector12 =  (userValue1 /1.201);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Pint_liquid_US to  Quart_liquid_US

        double valueSelector13 =  (userValue1 /2 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Pint_liquid_US to  Quart_liquid_UK

        double valueSelector14 =  (userValue1 /2.402);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Pint_liquid_US to   Gallon_US

        double valueSelector15 =  (userValue1 /8);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Pint_liquid_US to  Gallon_UK

        double valueSelector16 =  (userValue1 /9.608 );
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Pint_liquid_US to  Barrel_US

        double valueSelector17 =  (userValue1 /336);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);


    }
    private void Pint_liquid_UK_update() {
        //Pint_liquid_UK to Milliliter

        double valueSelector1 =  (userValue1 * 568.261);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Pint_liquid_UK to Liter

        double valueSelector2 =  (userValue1 /1.76);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Pint_liquid_UK to Cubic_meter

        double valueSelector3 =  (userValue1 /1760);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Pint_liquid_UK to Cubic_inch

        double valueSelector4 = (userValue1 * 34.6774);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Pint_liquid_UK to Cubic_foot

        double valueSelector5 = (userValue1 /49.831);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Pint_liquid_UK to Cubic_yard

        double valueSelector6 =  (userValue1 * 0.0007432577);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Pint_liquid_UK to Fluid_ounce_US

        double valueSelector7 =  (userValue1 * 19.2152 );
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Pint_liquid_UK to Fluid_ounce_UK

        double valueSelector8 =  (userValue1 * 20);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Pint_liquid_UK to Cup_US

        double valueSelector9 =  (userValue1 * 2.36776);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Pint_liquid_UK to Cup

        double valueSelector10 =  (userValue1 * 2 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Pint_liquid_UK to Pint_liquid_US

        double valueSelector11 =  (userValue1 * 1.2009);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Pint_liquid_UK to  Pint_liquid_UK

        int valueSelector12 = (int) (userValue1 * 1);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Pint_liquid_UK to  Quart_liquid_US

        double valueSelector13 =  (userValue1 /1.665 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Pint_liquid_UK to  Quart_liquid_UK

        double valueSelector14 =  (userValue1 /2);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Pint_liquid_UK to   Gallon_US

        double valueSelector15 =  (userValue1 /6.661);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Pint_liquid_UK to  Gallon_UK

        double valueSelector16 =  (userValue1 /8 );
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Pint_liquid_UK to  Barrel_US

        double valueSelector17 =  (userValue1 /280);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);


    }

    private void Quart_liquid_US_update() {
        //Quart_liquid_US to Milliliter

        double valueSelector1 =  (userValue1 * 946.353);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Quart_liquid_US to Liter

        double valueSelector2 =  (userValue1 /1.057);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Quart_liquid_US to Cubic_meter

        double valueSelector3 =  (userValue1 /1057);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Quart_liquid_US to Cubic_inch

        double valueSelector4 = (userValue1 * 57.75);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Quart_liquid_US to Cubic_foot

        double valueSelector5 = (userValue1 /29.922);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Quart_liquid_US to Cubic_yard

        double valueSelector6 =  (userValue1 * 0.0012377823);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Quart_liquid_US to Fluid_ounce_US

        int valueSelector7 = (int) (userValue1 * 32);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Quart_liquid_US to Fluid_ounce_UK

        double valueSelector8 =  (userValue1 * 33.307);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Quart_liquid_US to Cup_US

        double valueSelector9 =  (userValue1 * 3.94314);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Quart_liquid_US to Cup

        double valueSelector10 =  (userValue1 * 3.3307 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //Quart_liquid_US to  Pint_liquid_US

        int valueSelector11 = (int) (userValue1 * 2);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Quart_liquid_US to  Pint_liquid_UK

        double valueSelector12 =  (userValue1 * 1.66535);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Quart_liquid_US to  Quart_liquid_US

        int valueSelector13 = (int) (userValue1 * 1);
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Quart_liquid_US to  Quart_liquid_UK

        double valueSelector14 =  (userValue1 /1.201);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Quart_liquid_US to   Gallon_US

        double valueSelector15 =  (userValue1 /4);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Quart_liquid_US to  Gallon_UK

        double valueSelector16 =  (userValue1 /4.804 );
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Quart_liquid_US to  Barrel_US

        double valueSelector17 =  (userValue1 /168);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);

    }

    private void Quart_liquid_UK_update() {
        //Quart_liquid_UK to Milliliter

        double valueSelector1 =  (userValue1 * 1136.52);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Quart_liquid_UK to Liter

        double valueSelector2 =  (userValue1 * 1.13652);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Quart_liquid_UK to Cubic_meter

        double valueSelector3 =  (userValue1 /880);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Quart_liquid_UK to Cubic_inch

        double valueSelector4 = (userValue1 * 69.3549);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Quart_liquid_UK to Cubic_foot

        double valueSelector5 = (userValue1 /24.915);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Quart_liquid_UK to Cubic_yard

        double valueSelector6 =  (userValue1 * 0.0014865153);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Quart_liquid_UK to Fluid_ounce_US

        double valueSelector7 =  (userValue1 * 38.4304 );
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Quart_liquid_UK to Fluid_ounce_UK

        int valueSelector8 = (int) (userValue1 * 40);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Quart_liquid_UK to Cup_US

        double valueSelector9 =  (userValue1 * 4.73551);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Quart_liquid_UK to Cup

        double valueSelector10 =  (userValue1 * 1);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Quart_liquid_UK to Pint_liquid_US

        double valueSelector11 =  (userValue1 * 2.4019);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Quart_liquid_UK to  Pint_liquid_UK

        double valueSelector12 =  (userValue1 * 2);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Quart_liquid_UK to  Quart_liquid_US

        double valueSelector13 =  (userValue1 * 1.20095 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Quart_liquid_UK to  Quart_liquid_UK

        int valueSelector14 = (int) (userValue1 * 1);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Quart_liquid_UK to   Gallon_US

        double valueSelector15 =  (userValue1 /3.331);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Quart_liquid_UK to  Gallon_UK

        double valueSelector16 =  (userValue1 /4 );
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Quart_liquid_UK to  Barrel_US

        double valueSelector17 =  (userValue1 /140);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);


    }

    private void Gallon_US_update() {
        //Gallon_US to Milliliter

        double valueSelector1 =  (userValue1 * 3785.41);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Gallon_US to Liter

        double valueSelector2 =  (userValue1 * 3.78541);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Gallon_US to Cubic_meter

        double valueSelector3 =  (userValue1 /264);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Gallon_US to Cubic_inch

        double valueSelector4 = (userValue1 * 231);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Gallon_US to Cubic_foot

        double valueSelector5 = (userValue1 /7.481);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Gallon_US to Cubic_yard

        double valueSelector6 =  (userValue1 * 0.0049511294);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Gallon_US to Fluid_ounce_US

        int valueSelector7 = (int) (userValue1 * 128);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Gallon_US to Fluid_ounce_UK

        double valueSelector8 =  (userValue1 * 133.228);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Gallon_US to Cup_US

        double valueSelector9 =  (userValue1 * 15.7725);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Gallon_US to Cup

        double valueSelector10 =  (userValue1 * 13.3228 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Gallon_US to Pint_liquid_US

        int valueSelector11 = (int) (userValue1 * 8);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Gallon_US to  Pint_liquid_UK

        double valueSelector12 =  (userValue1 * 6.66139);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Gallon_US to  Quart_liquid_US

        int valueSelector13 = (int) (userValue1 * 4);
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Gallon_US to  Quart_liquid_UK

        double valueSelector14 =  (userValue1 * 3.3307);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Gallon_US to   Gallon_US

        int valueSelector15 = (int) (userValue1 * 1);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Gallon_US to  Gallon_UK

        double valueSelector16 =  (userValue1 /1.201 );
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Gallon_US to  Barrel_US

        double valueSelector17 =  (userValue1 /42);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);


    }

    private void Gallon_UK_update() {
        //Gallon_UK to Milliliter

        double valueSelector1 =  (userValue1 * 4546.09);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Gallon_UK to Liter

        double valueSelector2 =  (userValue1 * 4.54609);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Gallon_UK to Cubic_meter
        double valueSelector3 =  (userValue1 /220);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Gallon_UK to Cubic_inch

        double valueSelector4 = (userValue1 * 277.419	);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Gallon_UK to Cubic_foot

        double valueSelector5 = (userValue1 /6.229);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Gallon_UK to Cubic_yard

        double valueSelector6 =  (userValue1 * 0.0059460612);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Gallon_UK to Fluid_ounce_US

        double valueSelector7 =  (userValue1 * 153.722 );
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Gallon_UK to Fluid_ounce_UK

        int valueSelector8 = (int) (userValue1 * 160);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Gallon_UK to Cup_US

        double valueSelector9 =  (userValue1 * 19.215207864);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Gallon_UK to Cup

        double valueSelector10 =  (userValue1 * 18.942);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Gallon_UK to Pint_liquid_US

        double valueSelector11 =  (userValue1 * 9.6076);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Gallon_UK to  Pint_liquid_UK

        int valueSelector12 = (int) (userValue1 * 8);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Gallon_UK to  Quart_liquid_US

        double valueSelector13 =  (userValue1 * 4.803801966 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Gallon_UK to  Quart_liquid_UK

        int valueSelector14 = (int) (userValue1 * 4);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Gallon_UK to   Gallon_US

        double valueSelector15 =  (userValue1 * 1.20095);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Gallon_UK to  Gallon_UK

        int  valueSelector16 = (int) (userValue1 * 1);
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Gallon_UK to  Barrel_US

        double valueSelector17 =  (userValue1 /34.972);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);


    }

    private void Barell_US_update() {
        //Barrel_US to Milliliter

        double valueSelector1 =  (userValue1 * 158987	);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Barrel_US to Liter

        double valueSelector2 =  (userValue1 * 158.987);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Barrel_US to Cubic_meter

        double valueSelector3 =  (userValue1 /6.29);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Barrel_US to Cubic_inch

        double valueSelector4 = (userValue1 * 9702);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Barrel_US to Cubic_foot

        double valueSelector5 = (userValue1 * 5.61458);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Barrel_US to Cubic_yard

        double valueSelector6 =  (userValue1 * 0.1535);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Barrel_US to Fluid_ounce_US

        double valueSelector7 =  (userValue1 * 5376 );
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Barrel_US to Fluid_ounce_UK

        double valueSelector8 =  (userValue1 * 5595.57);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Barrel_US to Cup_US

        double valueSelector9 =  (userValue1 * 662.447);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Barrel_US to Cup

        double valueSelector10 =  (userValue1 * 559.557 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //Barrel_US to  Pint_liquid_US

        double valueSelector11 =  (userValue1 * 336);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        //Barrel_US to  Pint_liquid_UK

        double valueSelector12 =  (userValue1 * 279.779);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        //Barrel_US to  Quart_liquid_US

        double valueSelector13 =  (userValue1 * 168 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        //Barrel_US to  Quart_liquid_UK

        double valueSelector14 =  (userValue1 * 139.889);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);
        //Barrel_US to   Gallon_US

        double valueSelector15 =  (userValue1 * 42);
        String _valueSelector15 = decimalFormat.format(valueSelector15);
        result15.setText(_valueSelector15);
        //Barrel_US to  Gallon_UK

        double valueSelector16 =  (userValue1 * 34.9723 );
        String _valueSelector16 = decimalFormat.format(valueSelector16);
        result16.setText(_valueSelector16);
        //Barrel_US to  Barrel_US

        int  valueSelector17 = (int) (userValue1 * 1);
        String _valueSelector17 = decimalFormat.format(valueSelector17);
        result17.setText(_valueSelector17);


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
        String activity_result13;
        activity_result13 = result13.getText().toString();
        String activity_result14;
        activity_result14 = result14.getText().toString();
        String activity_result15;
        activity_result15 = result15.getText().toString();
        String activity_result16;
        activity_result16 = result16.getText().toString();
        String activity_result17;
        activity_result17 = result17.getText().toString();
        String User_enter_data;
        User_enter_data = UserInput.getText().toString();

        // above declared string are exported to share activity
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody =   "Results based on your input for "+ User_enter_data +" "+selectedItem + "\n"
                + "Milliliter (mL) : " + activity_result  + "\n"
                + "Liter (L) : " + activity_result2 + "\n"
                + "Cubic meter (m3) : " + activity_result3 + "\n"
                + "Cubic inch (in3) :" + activity_result4+ "\n"
                + "Cubic foot (ft3) : " + activity_result5 + "\n"
                + "Cubic yard (yd3) : " + activity_result6 + "\n"
                + "Fluid ounce - US (fl oz) :" + activity_result7+ "\n"
                + "Fluid ounce - UK (fl oz) : " + activity_result8 + "\n"
                + "Cup - US (c)  : " + activity_result9 + "\n"
                + "Cup (c) :" + activity_result10+ "\n"
                + "Pint, liquid -US (pt) : " + activity_result11+ "\n"
                + "Pint, liquid -UK (pt) : " + activity_result12 + ".\n"
                + "Quart, liquid - US (qt) : " + activity_result13+ "\n"
                + "Quart, liquid - UK (qt) : " + activity_result14  + "\n"
                + "Gallon – US (gal) :" + activity_result15+ "\n"
                + "Gallon – UK (gal) : " + activity_result16+ "\n"
                + "Barell – US (bbl) : " + activity_result17  + "\n";

        String shareSub = "Volume Converter Results Powered by Transpose Solutions Android App - Unit Converter ";
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
        }else if (id == R.id.menu_currency) {
            Intent intent = new Intent(this, Currency.class);
            startActivity(intent);
        } else if (id == R.id.menu_Digital_Storage) {
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