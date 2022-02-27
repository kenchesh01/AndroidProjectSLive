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

public class Time extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Declare the instance variable Ad view
    private AdView adView;
    private ActionBarDrawerToggle mToggle;
    EditText UserInput;
    TextView result1,result2,result3,result4,result5,result6,result7,result8,result9,result10;
    Spinner selector_1;
    double x=0;
    double userValue1=0;
    String selectedItem;
    DecimalFormat decimalFormat = new DecimalFormat("#.######");
    DecimalFormat decimalScientificFormat = new DecimalFormat("#.#####E0");

    //  create a textWatcher member
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

            }else{
                checkForChanges1();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

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
                R.array.spinner_time_items, R.layout.spinner_selector_items);
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
                    update_Nanosecond();
                }else if(position==1){
                    update_Microsecond();
                }else if(position==2){
                    update_Millisecond();
                }else if(position==3){
                    update_Second();
                }else if (position==4){
                    update_Minute();
                }else if(position==5){
                    update_Hour();
                }else if(position==6){
                    update_Day();
                }else if(position==7){
                    update_Week();
                }else if(position==8){
                    update_Month();
                }else if(position==9){
                    update_Year();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void checkForChanges1() {
        // First get the edit text value from the activity
        String activityTextValue = UserInput.getText().toString();
        try {
            userValue1 = Double.parseDouble(activityTextValue);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        // code inside the button click event handler
        String spinnerValue = String.valueOf(selector_1.getSelectedItem());
        switch (spinnerValue) {
            case "Nanosecond":
                x = 1;
                break;
            case "Microsecond":
                x = 2;
                break;
            case "Millisecond (ms)":
                x = 3;
                break;
            case "Second (s)":
                x = 4;
                break;
            case "Minute (min)":
                x = 5;
                break;
            case "Hour (h)":
                x = 6;
                break;
            case "Day (d)":
                x = 7;
                break;
            case "Week (wk)":
                x = 8;
                break;
            case "Month (m)":
                x = 9;
                break;
            case "Year (y)":
                x = 10;
                break;
            default:
                break;
        }
        if(x==1){
            update_Nanosecond();
        }else if(x==2){
            update_Microsecond();
        }else if(x==3){
            update_Millisecond();
        }else if(x==4){
            update_Second();
        }else if(x==5){
            update_Minute();
        }else if(x==6){
            update_Hour();
        }else if(x==7){
            update_Day();
        }else if(x==8){
            update_Week();
        }else if(x==9){
            update_Month();
        }else if(x==10){
            update_Year();
        }
    }

    private void update_Nanosecond() {
        // Nanosecond to Nanosecond
        int  valueSelector1 = (int) (userValue1 * 1);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Nanosecond to Microsecond
        double valueSelector2 =  (userValue1 /1000);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Nanosecond to  Millisecond
        double valueSelector3 =  (userValue1 /1e+6);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Nanosecond to Second
        double valueSelector4 =  (userValue1 * 1e-9);
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Nanosecond to Minute
        double valueSelector5 =  (userValue1 *  1.6667e-11);
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Nanosecond to Hour
        double valueSelector6 =  (userValue1 *  2.7778e-13);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Nanosecond to Day
        double valueSelector7 = userValue1 * 1.1574e-14;
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Nanosecond to Week
        double valueSelector8 = userValue1 * 1.6534e-15;
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Nanosecond to Month
        double valueSelector9 =  (userValue1 * 3.8052e-16);
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Nanosecond to  Year
        double valueSelector10 =  (userValue1 * 3.171e-17);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
    }

    private void update_Microsecond() {
        // Microsecond to Nanosecond
        int  valueSelector1 = (int) (userValue1 * 1000);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Microsecond to Microsecond
        int valueSelector2 = (int) (userValue1 *  1);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Microsecond to  Millisecond
        double valueSelector3 =  (userValue1 /1000);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Microsecond to Second
        double valueSelector4 =  (userValue1 /1e+6);
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Microsecond to Minute
        double valueSelector5 =  (userValue1 *  1.6667e-8);
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Microsecond to Hour
        double valueSelector6 =  (userValue1 *  2.7778e-10);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Microsecond to Day
        double valueSelector7 = userValue1 * 1.1574e-11;
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Microsecond to Week
        double valueSelector8 = userValue1 * 1.6534e-12;
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Microsecond to Month
        double valueSelector9 =  (userValue1 * 3.8052e-13);
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Microsecond to  Year
        double valueSelector10 =  (userValue1 * 3.171e-14);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
    }

    private void update_Millisecond() {
        // Millisecond to Nanosecond
        double  valueSelector1 =  (userValue1 * 1e+6);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Millisecond to Microsecond
        int valueSelector2 = (int) (userValue1 *  1000);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Millisecond to  Millisecond
        int valueSelector3 = (int) (userValue1 * 1);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Millisecond to Second
        double valueSelector4 =  (userValue1 /1000);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Millisecond to Minute
        double valueSelector5 =  (userValue1 *  1.6667e-5);
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Millisecond to Hour
        double valueSelector6 =  (userValue1 *  2.7778e-7);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Millisecond to Day
        double valueSelector7 = userValue1 * 1.1574e-8;
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Millisecond to Week
        double valueSelector8 = userValue1 * 1.6534e-9;
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Millisecond to Month
        double valueSelector9 =  (userValue1 * 3.8052e-10);
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Millisecond to  Year
        double valueSelector10 =  (userValue1 * 3.171e-11);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
    }

    private void update_Second() {
        // Second  to Nanosecond
        double  valueSelector1 =  (userValue1 * 1e+9);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Second  to Microsecond
        double valueSelector2 =  (userValue1 *  1e+6);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Second  to  Millisecond
        int valueSelector3 = (int) (userValue1 * 1000);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Second  to Second
        int valueSelector4 = (int) (userValue1 * 1);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Second  to Minute
        double valueSelector5 =  (userValue1 /60);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Second  to Hour
        double valueSelector6 =  (userValue1 /3600);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Second  to Day
        double valueSelector7 = userValue1 * 1.1574e-5;
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Second  to Week
        double valueSelector8 = userValue1 * 1.6534e-6;
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Second  to Month
        double valueSelector9 =  (userValue1 * 3.8052e-7);
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Second  to  Year
        double valueSelector10 =  (userValue1 * 3.171e-8);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
    }

    private void update_Minute() {
        //  Minute to Nanosecond
        double valueSelector1 = (userValue1 *6e+10);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Minute to Microsecond
        double valueSelector2 = (userValue1 *  6e+7);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Minute to  Millisecond
        int valueSelector3 = (int) (userValue1 * 60000);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Minute to Second
        int valueSelector4 = (int) (userValue1 * 60);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Minute to Minute
        int valueSelector5 = (int) (userValue1 *  1);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Minute to Hour
        double valueSelector6 =  (userValue1 /60);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Minute to Day
        double valueSelector7 = userValue1 /1440;
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // Minute to Week
        double valueSelector8 = userValue1 * 9.9206e-5;
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // Minute to Month
        double valueSelector9 =  (userValue1 * 2.2831e-5);
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Minute to  Year
        double valueSelector10 =  (userValue1 * 1.9026e-6);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
    }

    private void update_Hour() {
        // Hour to Nanosecond
        double  valueSelector1 =  (userValue1 * 3.6e+12);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Hour to Microsecond
        double valueSelector2 =  (userValue1 *   3.6e+9);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Hour to  Millisecond
        double valueSelector3 = (userValue1 *  3.6e+6);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Hour to Second
        int valueSelector4 = (int) (userValue1 * 3600);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Hour to Minute
        int valueSelector5 = (int) (userValue1 *  60);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Hour to Hour
        int valueSelector6 = (int) (userValue1 *  1);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Hour to Day
        double valueSelector7 = userValue1 /24;
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Hour to Week
        double valueSelector8 = userValue1 /168;
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Hour to Month
        double valueSelector9 =  (userValue1 /730);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Hour to  Year
        double valueSelector10 =  (userValue1 / 8760);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
    }

    private void update_Day() {
        // Day to Nanosecond
        double  valueSelector1 =  (userValue1 * 8.64e+13);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Day to Microsecond
        double valueSelector2 =  (userValue1 *  8.64e+10);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Day to  Millisecond
        double valueSelector3 =  (userValue1 * 8.64e+7);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Day to Second
        int valueSelector4 = (int) (userValue1 * 86400);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Day to Minute
        int valueSelector5 = (int) (userValue1 *  1440);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Day to Hour
        int valueSelector6 = (int) (userValue1 *  24);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Day to Day
        int valueSelector7 = (int) (userValue1 * 1);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Day to Week
        double valueSelector8 = userValue1 /7;
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Day to Month
        double valueSelector9 =  (userValue1 /30.417);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Day to  Year
        double valueSelector10 =  (userValue1 / 365);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
    }

    private void update_Week() {
        // Week to Nanosecond
        double  valueSelector1 =  (userValue1 *  6.048e+14);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Week to Microsecond
        double valueSelector2 =  (userValue1 *   6.048e+11);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Week to  Millisecond
        double valueSelector3 =  (userValue1 *  6.048e+8);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Week to Second
        double valueSelector4 =  (userValue1 * 604800);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Week to Minute
        double valueSelector5 =  (userValue1 *  10080);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Week to Hour
        int valueSelector6 = (int) (userValue1 *  168);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Week to Day
        int valueSelector7 = (int) (userValue1 * 7);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Week to Week
        int valueSelector8 = (int) (userValue1 * 1);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Week to Month
        double valueSelector9 =  (userValue1 /4.345);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Week to  Year
        double valueSelector10 =  (userValue1 /52.143);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
    }

    private void update_Month() {
        // Month to Nanosecond
        double  valueSelector1 =  (userValue1 * 2.628e+15);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Month to Microsecond
        double valueSelector2 =  (userValue1 * 2.628e+12);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Month to  Millisecond
        double valueSelector3 =  (userValue1 * 2.628e+9);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Month to Second
        double valueSelector4 =  (userValue1 * 2.628e+6);
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Month to Minute
        int valueSelector5 = (int) (userValue1 *  43800);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Month to Hour
        int valueSelector6 = (int) (userValue1 *  730);
        String _valueSelector6 = String.valueOf(valueSelector6);
        result6.setText(_valueSelector6);
        //Month to Day
        double valueSelector7 = userValue1 * 30.417;
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Month to Week
        double valueSelector8 = userValue1 * 4.345;
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Month to Month
        int valueSelector9 = (int) (userValue1 * 1);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Month to  Year
        double valueSelector10 =  (userValue1 /12);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
    }

    private void update_Year() {
        // Year to Nanosecond
        double  valueSelector1 =  (userValue1 * 3.154e+16);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Year to Microsecond
        double valueSelector2 =  (userValue1 *  3.154e+13);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Year to  Millisecond
        double valueSelector3 =  (userValue1 * 3.154e+10);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Year to Second
        double valueSelector4 =  (userValue1 * 3.154e+7);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Year to Minute
        int valueSelector5 = (int) (userValue1 *  525600);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Year to Hour
        int valueSelector6 = (int) (userValue1 *  8760);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Year to Day
        int valueSelector7 = (int) (userValue1 * 365);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Year to Week
        double valueSelector8 = userValue1 * 52.143;
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Year to Month
        int valueSelector9 = (int) (userValue1 * 12);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Year to  Year
        int valueSelector10 = (int) (userValue1 * 1);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
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
        String User_enter_data;
        User_enter_data = UserInput.getText().toString();

        // above declared string are exported to share activity
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody =   "Results based on your input for "+ User_enter_data +" "+selectedItem + "\n"
                + "MNanosecond : " + activity_result + "\n"
                + "Microsecond : " + activity_result2 + "\n"
                + "Millisecond (ms) :" + activity_result3+ "\n"
                + "Second (s) : " + activity_result4 + "\n"
                + "Minute (min) : " + activity_result5  + "\n"
                + "Hour (h)  :" + activity_result6+ "\n"
                + "Day (d): " + activity_result7 + "\n"
                + "Week (wk) : " + activity_result8  + "\n"
                + "Month (m) :" + activity_result9+ "\n"
                + "Year (y) : " + activity_result10 + "\n";

        String shareSub = "Time Converter Results Powered by Transpose Solutions Android App - Unit Converter ";
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