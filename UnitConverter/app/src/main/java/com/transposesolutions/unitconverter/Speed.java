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

public class Speed extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
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
        setContentView(R.layout.activity_speed);

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
                R.array.spinner_speed_items, R.layout.spinner_selector_items);
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
                    update_Miles_per_hour();
                }else if(position==1){
                    update_Miles_per_minute();
                }else if(position==2){
                    update_Miles_per_second();
                }else if(position==3){
                    update_Kilometer_per_hour();
                }else if (position==4){
                    update_Kilometer_per_minute();
                }else if(position==5){
                    update_Kilometer_per_second();
                }else if(position==6){
                    update_Foot_per_second();
                }else if(position==7){
                    update_Meter_per_second();
                }else if(position==8){
                    update_Meter_per_minute();
                }else if(position==9){
                    update_Meter_per_hour();
                }else if(position==10){
                    update_knot();
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
            case "Miles per hour (mph)":
                x = 1;
                break;
            case "Mile per minute (mi/min)":
                x = 2;
                break;
            case "Mile per second (mi/s)":
                x = 3;
                break;
            case "Kilometer per hour (kph)":
                x = 4;
                break;
            case "Kilometer per minute (km/min)":
                x = 5;
                break;
            case "Kilometer per second (km/s)":
                x = 6;
                break;
            case "Foot per second (ft/s)":
                x = 7;
                break;
            case "Meter per second (m/s)":
                x = 8;
                break;
            case "Meter per minute (m/min)":
                x = 9;
                break;
            case "Meter per hour (m/h)":
                x = 10;
                break;
            case "Knot":
                x = 11;
                break;
            default:
                break;
        }
        if(x==1){
            update_Miles_per_hour();
        }else if(x==2){
            update_Miles_per_minute();
        }else if(x==3){
            update_Miles_per_second();
        }else if(x==4){
            update_Kilometer_per_hour();
        }else if(x==5){
            update_Kilometer_per_minute();
        }else if (x==6){
            update_Kilometer_per_second();
        }else if(x==7){
            update_Foot_per_second();
        }else if (x==8){
            update_Meter_per_second();
        }else if(x==9){
            update_Meter_per_minute();
        }else if(x==10){
            update_Meter_per_hour();
        }else if(x==11){
            update_knot();
        }

    }

    private void update_Miles_per_hour() {
        // Miles_per_hour to Miles_per_hour

        int valueSelector1 = (int) (userValue1 * 1);
        String _valueSelector1 = String.valueOf(valueSelector1);
        result1.setText(_valueSelector1);
        // Miles_per_hour to Miles_per_minute

        double valueSelector2 =  (userValue1 * 0.0166666667);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Miles_per_hour to Miles_per_second

        double valueSelector3 =  (userValue1 * 0.0002777778);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Miles_per_hour to Kilometer_per_hour

        double valueSelector4 = (userValue1 * 1.609344);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Miles_per_hour to Kilometer_per_minute

        double valueSelector5 =  (userValue1 * 0.026822);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Miles_per_hour to Kilometer_per_second

        double valueSelector6 =  (userValue1 * 0.00044704);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Miles_per_hour to Foot_per_second

        double valueSelector7 =  (userValue1 *  1.4666666667);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Miles_per_hour to  Meter_per_second

        double valueSelector8 =  (userValue1 * 0.44704);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Miles_per_hour to Meter_per_minute

        double valueSelector9 =  (userValue1 * 26.8224);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Miles_per_hour to Meter_per_hour

        double valueSelector10 =  (userValue1 * 1609.344);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //Miles_per_hour to Knot

        double valueSelector11 =  (userValue1 * 0.8689762419);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }

    private void update_Miles_per_minute() {
        // miles_per_minute to Miles_per_hour

        int valueSelector1 = (int) (userValue1 * 60);
        String _valueSelector1 = String.valueOf(valueSelector1);
        result1.setText(_valueSelector1);
        // miles_per_minute to Miles_per_minute

        int valueSelector2 = (int) (userValue1 * 1);
        String _valueSelector2 = String.valueOf(valueSelector2);
        result2.setText(_valueSelector2);
        //miles_per_minute to Miles_per_second

        double valueSelector3 =  (userValue1 * 0.0166666667);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //miles_per_minute to Kilometer_per_hour

        double valueSelector4 = (userValue1 * 96.56064);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //miles_per_minute to Kilometer_per_minute

        double valueSelector5 =  (userValue1 *  1.609344);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //miles_per_minute to Kilometer_per_second

        double valueSelector6 =  (userValue1 * 0.0268224);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //miles_per_minute to Foot_per_second

        int valueSelector7 = (int) (userValue1 * 88);
        String _valueSelector7 = String.valueOf(valueSelector7);
        result7.setText(_valueSelector7);
        //miles_per_minute to  Meter_per_second

        double valueSelector8 =  (userValue1 * 26.8224);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //miles_per_minute to Meter_per_minute

        double valueSelector9 =  (userValue1 * 1609.344);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //miles_per_minute to Meter_per_hour

        double valueSelector10 =  (userValue1 * 96560.64);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //miles_per_minute to Knot

        double valueSelector11 =  (userValue1 *  52.138574514);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }

    private void update_Miles_per_second() {
        // miles_per_second to Miles_per_hour

        int valueSelector1 = (int) (userValue1 * 3600);
        String _valueSelector1 = String.valueOf(valueSelector1);
        result1.setText(_valueSelector1);
        // miles_per_second to Miles_per_minute

        int valueSelector2 = (int) (userValue1 * 60);
        String _valueSelector2 = String.valueOf(valueSelector2);
        result2.setText(_valueSelector2);
        //miles_per_second to Miles_per_second

        int valueSelector3 = (int) (userValue1 * 1);
        String _valueSelector3 = String.valueOf(valueSelector3);
        result3.setText(_valueSelector3);
        //miles_per_second to Kilometer_per_hour

        double valueSelector4 = (userValue1 * 5793.6384);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //miles_per_second to Kilometer_per_minute

        double valueSelector5 =  (userValue1 * 96.56064);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //miles_per_second to Kilometer_per_second

        double valueSelector6 =  (userValue1 * 1.609344);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //miles_per_second to Foot_per_second

        int valueSelector7 = (int) (userValue1 * 5280);
        String _valueSelector7 = String.valueOf(valueSelector7);
        result7.setText(_valueSelector7);
        //miles_per_second to  Meter_per_second

        double valueSelector8 =  (userValue1 * 1609.344);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //miles_per_second to Meter_per_minute

        double valueSelector9 =  (userValue1 * 96560.64);
        String _valueSelector9 = String.valueOf(valueSelector9);
        result9.setText(_valueSelector9);
        //miles_per_second to Meter_per_hour

        double valueSelector10 =  (userValue1 * 5793638.4);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //miles_per_second to Knot

        double valueSelector11 =  (userValue1 *  3128.3144708);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }

    private void update_Kilometer_per_hour() {
        // Kilometer_per_hour to Miles_per_hour

        double valueSelector1 =  (userValue1 * 0.6213711922);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Kilometer_per_hour to Miles_per_minute

        double valueSelector2 =  (userValue1 * 0.0103561865);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Kilometer_per_hour to Miles_per_second

        double valueSelector3 =  (userValue1 * 0.0001726031);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Kilometer_per_hour to Kilometer_per_hour

        int valueSelector4 = (int) (userValue1 * 1);
        String _valueSelector4 = String.valueOf(valueSelector4);
        result4.setText(_valueSelector4);
        //Kilometer_per_hour to Kilometer_per_minute

        double valueSelector5 =  (userValue1 * 0.0166666667);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Kilometer_per_hour to Kilometer_per_second

        double valueSelector6 =  (userValue1 * 0.0002777778);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Kilometer_per_hour to Foot_per_second

        double valueSelector7 =  (userValue1 *  0.9113444153);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Kilometer_per_hour to  Meter_per_second

        double valueSelector8 =  (userValue1 * 0.2777777778);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Kilometer_per_hour to Meter_per_minute

        double valueSelector9 =  (userValue1 * 16.666666667);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Kilometer_per_hour to Meter_per_hour

        int valueSelector10 = (int) (userValue1 * 1000);
        String _valueSelector10 = String.valueOf(valueSelector10);
        result10.setText(_valueSelector10);
        //Kilometer_per_hour to Knot

        double valueSelector11 =  (userValue1 *  0.5399568035);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }

    private void update_Kilometer_per_minute() {
        // Kilometer_per_minute to Miles_per_hour

        double valueSelector1 =  (userValue1 * 37.282271534);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Kilometer_per_minute to Miles_per_minute

        double valueSelector2 =  (userValue1 * 0.6213711922);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Kilometer_per_minute to Miles_per_second

        double valueSelector3 =  (userValue1 * 0.0103561865);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Kilometer_per_minute to Kilometer_per_hour

        int valueSelector4 = (int) (userValue1 * 60);
        String _valueSelector4 = String.valueOf(valueSelector4);
        result4.setText(_valueSelector4);
        //Kilometer_per_minute to Kilometer_per_minute

        int valueSelector5 = (int) (userValue1 * 1);
        String _valueSelector5 = String.valueOf(valueSelector5);
        result5.setText(_valueSelector5);
        //Kilometer_per_minute to Kilometer_per_second

        double valueSelector6 =  (userValue1 * 0.0166666667);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Kilometer_per_minute to Foot_per_second

        double valueSelector7 =  (userValue1 * 54.680664917);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Kilometer_per_minute to  Meter_per_second

        double valueSelector8 =  (userValue1 * 16.666666667);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Kilometer_per_minute to Meter_per_minute

        int valueSelector9 = (int) (userValue1 * 1000);
        String _valueSelector9 = String.valueOf(valueSelector9);
        result9.setText(_valueSelector9);
        //Kilometer_per_minute to Meter_per_hour

        int valueSelector10 = (int) (userValue1 * 60000);
        String _valueSelector10 = String.valueOf(valueSelector10);
        result10.setText(_valueSelector10);
        //Kilometer_per_minute to Knot

        double valueSelector11 =  (userValue1 *  32.397408207);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }

    private void update_Kilometer_per_second() {
        // Kilometer_per_second to Miles_per_hour

        double valueSelector1 =  (userValue1 * 2236.9362921);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Kilometer_per_second to Miles_per_minute

        double valueSelector2 =  (userValue1 * 37.282271534);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Kilometer_per_second to Miles_per_second

        double valueSelector3 =  (userValue1 * 0.6213711922);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Kilometer_per_second to Kilometer_per_hour

        int valueSelector4 = (int) (userValue1 * 3600);
        String _valueSelector4 = String.valueOf(valueSelector4);
        result4.setText(_valueSelector4);
        //Kilometer_per_second to Kilometer_per_minute

        int valueSelector5 = (int) (userValue1 * 60);
        String _valueSelector5 = String.valueOf(valueSelector5);
        result5.setText(_valueSelector5);
        //Kilometer_per_second to Kilometer_per_second

        int valueSelector6 = (int) (userValue1 * 1);
        String _valueSelector6 = String.valueOf(valueSelector6);
        result6.setText(_valueSelector6);
        //Kilometer_per_second to Foot_per_second

        double valueSelector7 =  (userValue1 * 3280.839895);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Kilometer_per_second to  Meter_per_second

        int valueSelector8 = (int) (userValue1 * 1000);
        String _valueSelector8 = String.valueOf(valueSelector8);
        result8.setText(_valueSelector8);
        //Kilometer_per_second to Meter_per_minute

        int valueSelector9 = (int) (userValue1 * 60000);
        String _valueSelector9 = String.valueOf(valueSelector9);
        result9.setText(_valueSelector9);
        //Kilometer_per_second to Meter_per_hour

        int valueSelector10 = (int) (userValue1 * 3600000);
        String _valueSelector10 = String.valueOf(valueSelector10);
        result10.setText(_valueSelector10);
        //Kilometer_per_second to Knot

        double valueSelector11 =  (userValue1 *  1943.8444924);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }

    private void update_Foot_per_second() {
        // Foot_per_second to Miles_per_hour

        double valueSelector1 =  (userValue1 *  0.6818181818);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Foot_per_second to Miles_per_minute

        double valueSelector2 =  (userValue1 *  0.0113636364);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Foot_per_second to Miles_per_second

        double valueSelector3 =  (userValue1 *  0.0001893939);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Foot_per_second to Kilometer_per_hour

        double valueSelector4 = (userValue1 *  1.09728);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Foot_per_second to Kilometer_per_minute

        double valueSelector5 =  (userValue1  * 0.018288);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Foot_per_second to Kilometer_per_second

        double valueSelector6 =  (userValue1 * 0.0003048);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Foot_per_second to Foot_per_second

        int valueSelector7 = (int) (userValue1 * 1);
        String _valueSelector7 = String.valueOf(valueSelector7);
        result7.setText(_valueSelector7);
        //Foot_per_second to  Meter_per_second

        double valueSelector8 =  (userValue1 * 0.3048);
        String _valueSelector8 = String.valueOf(valueSelector8);
        result8.setText(_valueSelector8);
        //Foot_per_second to Meter_per_minute

        double valueSelector9 =  (userValue1 * 18.288);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Foot_per_second to Meter_per_hour

        double valueSelector10 =  (userValue1 * 1097.28);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        //Foot_per_second to Knot

        double valueSelector11 =  (userValue1 *  0.5924838013);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }

    private void update_Meter_per_second() {
        // Meter_per_second to Miles_per_hour

        double valueSelector1 =  (userValue1 * 2.2369362921);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Meter_per_second to Miles_per_minute

        double valueSelector2 =  (userValue1 * 0.0372822715);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Meter_per_second to Miles_per_second

        double valueSelector3 =  (userValue1 * 0.0006213712);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Meter_per_second to Kilometer_per_hour

        double valueSelector4 = (userValue1 * 3.6);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Meter_per_second to Kilometer_per_minute

        double valueSelector5 =  (userValue1 * 0.06);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Meter_per_second to Kilometer_per_second

        double valueSelector6 =  (userValue1 * 0.001);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Meter_per_second to Foot_per_second

        double valueSelector7 =  (userValue1 * 3.280839895);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Meter_per_second to  Meter_per_second

        int valueSelector8 = (int) (userValue1 * 1);
        String _valueSelector8 = String.valueOf(valueSelector8);
        result8.setText(_valueSelector8);
        //Meter_per_second to Meter_per_minute

        int valueSelector9 = (int) (userValue1 * 60);
        String _valueSelector9 = String.valueOf(valueSelector9);
        result9.setText(_valueSelector9);
        //Meter_per_second to Meter_per_hour

        int valueSelector10 = (int) (userValue1 * 3600);
        String _valueSelector10 = String.valueOf(valueSelector10);
        result10.setText(_valueSelector10);
        //Meter_per_second to Knot

        double valueSelector11 =  (userValue1 *  1.9438444924);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }

    private void update_Meter_per_minute() {
        // Meter_per_minute to Miles_per_hour

        double valueSelector1 =  (userValue1 * 0.0372822715);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Meter_per_minute to Miles_per_minute

        double valueSelector2 =  (userValue1 * 0.0006213712);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Meter_per_minute to Miles_per_second

        double valueSelector3 =  (userValue1 * 0.0000103562);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Meter_per_minute to Kilometer_per_hour

        double valueSelector4 = (userValue1 * 0.06);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Meter_per_minute to Kilometer_per_minute

        double valueSelector5 =  (userValue1 * 0.001);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Meter_per_minute to Kilometer_per_second

        double valueSelector6 =  (userValue1 * 0.0000166667);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Meter_per_minute to Foot_per_second

        double valueSelector7 =  (userValue1 * 0.0546806649);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Meter_per_minute to  Meter_per_second

        double valueSelector8 =  (userValue1 *  0.0166666667);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Meter_per_minute to Meter_per_minute

        int valueSelector9 = (int) (userValue1 * 1);
        String _valueSelector9 = String.valueOf(valueSelector9);
        result9.setText(_valueSelector9);
        //Meter_per_minute to Meter_per_hour

        int valueSelector10 = (int) (userValue1 * 60);
        String _valueSelector10 = String.valueOf(valueSelector10);
        result10.setText(_valueSelector10);
        //Meter_per_minute to Knot

        double valueSelector11 =  (userValue1 *  0.0323974082);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }

    private void update_Meter_per_hour() {
        // Meter_per_hour to Miles_per_hour

        double valueSelector1 =  (userValue1 * 0.0006213712);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Meter_per_hour to Miles_per_minute

        double valueSelector2 =  (userValue1 * 0.0000103562);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Meter_per_hour to Miles_per_second

        double valueSelector3 =  (userValue1 * 1.726031089E-7);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Meter_per_hour to Kilometer_per_hour

        double valueSelector4 = (userValue1 * 0.001);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Meter_per_hour to Kilometer_per_minute

        double valueSelector5 =  (userValue1 * 0.0000166667);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Meter_per_hour to Kilometer_per_second

        double valueSelector6 =  (userValue1 * 2.777777777E-7);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Meter_per_hour to Foot_per_second

        double valueSelector7 =  (userValue1 * 0.0009113444);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Meter_per_hour to  Meter_per_second

        double valueSelector8 =  (userValue1 * 0.0002777778);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Meter_per_hour to Meter_per_minute

        double valueSelector9 =  (userValue1 * 0.0166666667);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Meter_per_hour to Meter_per_hour

        int valueSelector10 = (int) (userValue1 * 1);
        String _valueSelector10 = String.valueOf(valueSelector10);
        result10.setText(_valueSelector10);
        //Meter_per_hour to Knot

        double valueSelector11 =  (userValue1 *  0.0005399568);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
    }

    private void update_knot() {
        // Miles_per_hour to Miles_per_hour

        double valueSelector1 =  (userValue1 * 1.150779448);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Knot to Miles_per_minute

        double valueSelector2 =  (userValue1 *  0.0191796575);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Knot to Miles_per_second

        double valueSelector3 =  (userValue1 * 0.000319661);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Knot to Kilometer_per_hour

        double valueSelector4 = (userValue1 * 1.852);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Knot to Kilometer_per_minute

        double valueSelector5 =  (userValue1 * 0.0308666667);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Knot to Kilometer_per_second

        double valueSelector6 =  (userValue1 * 0.0005144444);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Knot to Foot_per_second

        double valueSelector7 =  (userValue1 * 1.6878098571);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Knot to  Meter_per_second

        double valueSelector8 =  (userValue1 * 0.5144444444);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        //Knot to Meter_per_minute

        double valueSelector9 =  (userValue1 * 30.866666667);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        //Knot to Meter_per_hour

        int valueSelector10 = (int) (userValue1 * 1852);
        String _valueSelector10 = String.valueOf(valueSelector10);
        result10.setText(_valueSelector10);
        //Knot to Knot

        int valueSelector11 = (int) (userValue1 *  1);
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
        String User_enter_data;
        User_enter_data = UserInput.getText().toString();

        // above declared string are exported to share activity
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody =  "Results based on your input for "+ User_enter_data +" "+selectedItem + "\n"
                + "Miles per hour (mph) : " + activity_result + "\n"
                + "Mile per minute (mi/min) : " + activity_result2 + "\n"
                + "Mile per second (mi/s) :" + activity_result3+ "\n"
                + "Kilometer per hour (kph) : " + activity_result4 + "\n"
                + "Kilometer per minute (km/min) : " + activity_result5  + "\n"
                + "Kilometer per second (km/s) :" + activity_result6+ "\n"
                + "Foot per second (ft/s) : " + activity_result7 + "\n"
                + "Meter per second (m/s) : " + activity_result8  + "\n"
                + "Meter per minute (m/min) :" + activity_result9+ "\n"
                + "Meter per hour (m/h) : " + activity_result10 + "\n"
                + "Knot : " + activity_result11  + ".\n";

        String shareSub = "Speed Converter Results Powered by Transpose Solutions Android App - Unit Converter ";
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