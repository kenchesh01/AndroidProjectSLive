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

public class Pressure extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Declare the instance variable Ad view
    private AdView adView;
    private ActionBarDrawerToggle mToggle;
    EditText UserInput;
    TextView result1,result2,result3,result4,result5,result6,result7,result8,result9, result10;
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
        setContentView(R.layout.activity_pressure);

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
                R.array.spinner_pressure_items, R.layout.spinner_selector_items);
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
                    update_Bar();
                }else if(position==1){
                    update_Pascal();
                }else if(position==2){
                    update_HectoPascal();
                }else if(position==3){
                    update_KiloPascal();
                }else if (position==4){
                    update_MegaPascal();
                }else if(position==5){
                    update_PSI();
                }else if(position==6){
                    update_KSI();
                }else if(position==7){
                    update_Pound_force_per_SquareFoot();
                }else if(position==8){
                    update_Standard_atmosphere();
                }else if(position==9){
                    update_Torr();
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
            case "Bar":
                x = 1;
                break;
            case "Pascal (Pa)":
                x = 2;
                break;
            case "HectoPascal (hPa)":
                x = 3;
                break;
            case "KiloPascal (kPa)":
                x = 4;
                break;
            case "MegaPascal (MPa)":
                x = 5;
                break;
            case "PSI (psi)":
                x = 6;
                break;
            case "KSI (ksi)":
                x = 7;
                break;
            case "Pound-force per SquareFoot":
                x = 8;
                break;
            case "Standard atmosphere (atm)":
                x = 9;
                break;
            case "Torr (Torr)":
                x = 10;
                break;
            default:
                break;
        }
        if(x==1){
            update_Bar();
        }else if(x==2){
            update_Pascal();
        }else if(x==3){
            update_HectoPascal();
        }else if(x==4){
            update_KiloPascal();
        }else if(x==5){
            update_MegaPascal();
        }else if(x==6){
            update_PSI();
        }else if(x==7){
            update_KSI();
        }else if(x==8){
            update_Pound_force_per_SquareFoot();
        }else if(x==9){
            update_Standard_atmosphere();
        }else if(x==10){
            update_Torr();
        }
    }

    private void update_Bar() {
        //Bar to Bar
        int  valueSelector1 = (int) (userValue1 * 1);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Bar to Pascal
        double valueSelector2 =  (userValue1 * 100000);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Bar to HectoPascal
        int valueSelector3 = (int) (userValue1 * 1000);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Bar to KiloPascal
        int valueSelector4 = (int) (userValue1 * 100);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Bar to MegaPascal
        double valueSelector5 = userValue1 /10;
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Bar to PSI
        double valueSelector6 =  (userValue1 *  14.5038);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Bar to KSI
        double valueSelector7 = userValue1 /68.948;
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);

        //Bar to Pound-force_perSquareFoot
        double valueSelector9 =  (userValue1 * 2088.54);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result8.setText(_valueSelector9);
        //Bar to Standard atmosphere
        double valueSelector10 =  (userValue1 /1.013);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result9.setText(_valueSelector10);
        //Bar to Torr
        double valueSelector11 = userValue1 * 750.062;
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result10.setText(_valueSelector11);
    }

    private void update_Pascal() {
        //Pascal  to Bar
        double  valueSelector1 =  (userValue1 /100000);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Pascal  to Pascal
        int valueSelector2 = (int) (userValue1 * 1);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Pascal  to HectoPascal
        double valueSelector3 =  (userValue1 /100);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Pascal  to KiloPascal
        double valueSelector4 =  (userValue1 /1000);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Pascal  to MegaPascal
        double valueSelector5 = userValue1 /1e+6;
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Pascal  to PSI
        double valueSelector6 =  (userValue1 /6895);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Pascal  to KSI
        double valueSelector7 = userValue1 * 1.45038e-7;
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);

        //Pascal  to Pound-force_perSquareFoot
        double valueSelector9 =  (userValue1 /47.88);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result8.setText(_valueSelector9);
        //Pascal  to Standard atmosphere
        double valueSelector10 =  (userValue1 * 9.86923e-6);
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result9.setText(_valueSelector10);
        //Pascal  to Torr
        double valueSelector11 = userValue1 /133;
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result10.setText(_valueSelector11);
    }

    private void update_HectoPascal() {
        //HectoPascal to Bar
        double  valueSelector1 =  (userValue1 /100);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //HectoPascal to Pascal
        int valueSelector2 = (int) (userValue1 * 100);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //HectoPascal to HectoPascal
        int valueSelector3 = (int) (userValue1 * 1);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //HectoPascal to KiloPascal
        double valueSelector4 =  (userValue1 /10);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //HectoPascal to MegaPascal
        double valueSelector5 = userValue1 /10000;
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //HectoPascal to PSI
        double valueSelector6 =  (userValue1 /68.948);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //HectoPascal to KSI
        double valueSelector7 = userValue1 * 1.45038e-5;
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);

        //HectoPascal to Pound-force_perSquareFoot
        double valueSelector9 =  (userValue1 * 2.08854);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result8.setText(_valueSelector9);
        //HectoPascal to Standard atmosphere
        double valueSelector10 =  (userValue1 /1013);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result9.setText(_valueSelector10);
        //HectoPascal to Torr
        double valueSelector11 = userValue1 /1.333;
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result10.setText(_valueSelector11);
    }

    private void update_KiloPascal() {
        //KiloPascal to Bar
        double valueSelector1 =  (userValue1 /100);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //KiloPascal to Pascal
        int valueSelector2 = (int) (userValue1 * 1000);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //KiloPascal to HectoPascal
        int valueSelector3 = (int) (userValue1 * 10);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //KiloPascal to KiloPascal
        int valueSelector4 = (int) (userValue1 * 1);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //KiloPascal to MegaPascal
        double valueSelector5 = userValue1 /1000;
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //KiloPascal to PSI
        double valueSelector6 =  (userValue1 /6.895);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //KiloPascal to KSI
        double valueSelector7 = userValue1 /6895;
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);

        //KiloPascal to Pound-force_perSquareFoot
        double valueSelector9 =  (userValue1 * 20.8854);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result8.setText(_valueSelector9);
        //KiloPascal to Standard atmosphere
        double valueSelector10 =  (userValue1 /101);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result9.setText(_valueSelector10);
        //KiloPascal  to Torr
        double valueSelector11 = userValue1 * 7.50062;
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result10.setText(_valueSelector11);
    }

    private void update_MegaPascal() {
        //MegaPascal to Bar
        int  valueSelector1 = (int) (userValue1 * 10);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //MegaPascal to Pascal
        double valueSelector2 =  (userValue1 * 1e+6);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //MegaPascal to HectoPascal
        double valueSelector3 =  (userValue1 * 10000);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //MegaPascal to KiloPascal
        int valueSelector4 = (int) (userValue1 * 1000);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //MegaPascal to MegaPascal
        int valueSelector5 = (int) (userValue1 *  1);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //MegaPascal to PSI
        double valueSelector6 =  (userValue1 *  145.038);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //MegaPascal to KSI
        double valueSelector7 = userValue1 /6.895;
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);

        //MegaPascal to Pound-force_perSquareFoot
        double valueSelector9 =  (userValue1 * 20885.4);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result8.setText(_valueSelector9);
        //MegaPascal to Standard atmosphere
        double valueSelector10 =  (userValue1 * 9.86923);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result9.setText(_valueSelector10);
        //MegaPascal to Torr
        double valueSelector11 = userValue1 * 7500.62;
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result10.setText(_valueSelector11);
    }

    private void update_PSI() {
        //PSI to Bar
        double  valueSelector1 =  (userValue1 /14.504);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //PSI to Pascal
        double valueSelector2 =  (userValue1 * 6894.76);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //PSI to HectoPascal
        double valueSelector3 =  (userValue1 * 68.9476);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //PSI to KiloPascal
        double valueSelector4 =  (userValue1 * 6.89476);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //PSI to MegaPascal
        double valueSelector5 = userValue1 /145;
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //PSI to PSI
        int valueSelector6 = (int) (userValue1 *  1);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //PSI to KSI
        double valueSelector7 = userValue1 /1000;
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);

        //PSI to Pound-force_perSquareFoot
        int valueSelector9 = (int) (userValue1 * 144);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result8.setText(_valueSelector9);
        //PSI to Standard atmosphere
        double valueSelector10 =  (userValue1 /14.696);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result9.setText(_valueSelector10);
        //PSI to Torr
        double valueSelector11 = userValue1 * 51.7149;
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result10.setText(_valueSelector11);
    }

    private void update_KSI() {
        //KSI to Bar
        double  valueSelector1 = (userValue1 * 68.9476);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //KSI to Pascal
        double valueSelector2 = (userValue1 * 6.895e+6);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //KSI to HectoPascal
        double valueSelector3 =  (userValue1 * 68947.6);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //KSI to KiloPascal
        double valueSelector4 =  (userValue1 * 6894.76);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //KSI to MegaPascal
        double valueSelector5 = userValue1 *  6.89476;
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //KSI to PSI
        int valueSelector6 = (int) (userValue1 *  1000);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //KSI to KSI
        int valueSelector7 = (int) (userValue1 * 1);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);

        //KSI to Pound-force_perSquareFoot
        int valueSelector9 = (int) (userValue1 * 144000);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result8.setText(_valueSelector9);
        //KSI to Standard atmosphere
        double valueSelector10 =  (userValue1 * 68.046);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result9.setText(_valueSelector10);
        //KSI to Torr
        double valueSelector11 = userValue1 * 51714.9;
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result10.setText(_valueSelector11);
    }

    private void update_Pound_force_per_SquareFoot() {
        //Pound-force_perSquareFoot to Bar
        double  valueSelector1 =  (userValue1 /2089);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Pound-force_perSquareFoot to Pascal
        double valueSelector2 =  (userValue1 * 47.8803);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Pound-force_perSquareFoot to HectoPascal
        double valueSelector3 =  (userValue1 * 0.4788);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Pound-force_perSquareFoot to KiloPascal
        double valueSelector4 =  (userValue1 /20.885);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Pound-force_perSquareFoot to MegaPascal
        double valueSelector5 = userValue1 *  4.78803e-5;
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Pound-force_perSquareFoot to PSI
        double valueSelector6 =  (userValue1 /144);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Pound-force_perSquareFoot to KSI
        double valueSelector7 = userValue1 /144000;
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);

        //Pound-force_perSquareFoot to Pound-force_perSquareFoot
        int valueSelector9 = (int) (userValue1 * 1);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result8.setText(_valueSelector9);
        //Pound-force_perSquareFoot to Standard atmosphere
        double valueSelector10 =  (userValue1 /2116);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result9.setText(_valueSelector10);
        //Pound-force_perSquareFoot to Torr
        double valueSelector11 = userValue1 /2.784;
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result10.setText(_valueSelector11);
    }


    private void update_Standard_atmosphere() {
        //Standard atmosphere to Bar
        double  valueSelector1 =  (userValue1 * 1.01325);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Standard atmosphere to Pascal
        int valueSelector2 = (int) (userValue1 * 101325);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Standard atmosphere to HectoPascal
        double valueSelector3 =  (userValue1 * 1013.25);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Standard atmosphere to KiloPascal
        double valueSelector4 =  (userValue1 * 101.325);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Standard atmosphere to MegaPascal
        double valueSelector5 = userValue1 / 9.869;
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Standard atmosphere to PSI
        double valueSelector6 =  (userValue1 *  14.6959);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Standard atmosphere to KSI
        double valueSelector7 = userValue1 /68.046;
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);

        //Standard atmosphere to Pound-force_perSquareFoot
        double valueSelector9 =  (userValue1 * 2116.22);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result8.setText(_valueSelector9);
        //Standard atmosphere  to Standard atmosphere
        int valueSelector10 = (int) (userValue1 * 1);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result9.setText(_valueSelector10);
        //Standard atmosphere to Torr
        int valueSelector11 = (int) (userValue1 * 760);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result10.setText(_valueSelector11);
    }

    private void update_Torr() {
        //Torr to Bar
        double  valueSelector1 =  (userValue1 /750);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Torr to Pascal
        double valueSelector2 =  (userValue1 * 133.322);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Torr to HectoPascal
        double valueSelector3 = (userValue1 * 1.33322);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Torr to KiloPascal
        double valueSelector4 =  (userValue1 /7.501);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Torr to MegaPascal
        double valueSelector5 = userValue1 /7501;
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Torr to PSI
        double valueSelector6 =  (userValue1 / 51.715);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Torr to KSI
        double valueSelector7 = userValue1 * 1.93368e-5;
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);

        //Torr to Pound-force_perSquareFoot
        double valueSelector9 =  (userValue1 * 2.7845);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result8.setText(_valueSelector9);
        //Torr to Standard atmosphere
        double valueSelector10 =  (userValue1  /760);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result9.setText(_valueSelector10);
        //Torr to Torr
        int valueSelector11 = (int) (userValue1 * 1);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result10.setText(_valueSelector11);
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
        String shareBody =   "Results based on your input for "+ User_enter_data + " "+selectedItem + "\n"
                + "Bar : " + activity_result + "\n"
                + "Pascal (Pa) " + activity_result2 + "\n"
                + "HectoPascal (hPa):" + activity_result3+ "\n"
                + "KiloPascal: " + activity_result4 + "\n"
                + "MegaPascal : " + activity_result5  + "\n"
                + " PSI :" + activity_result6+ "\n"
                + "KSI: " + activity_result7 + "\n"
                + "Pound-force per squareFoot:" + activity_result8+ "\n"
                + "Standard atmosphere: " + activity_result9 + "\n"
                + "Torr : " + activity_result10  + ".\n";

        String shareSub = "Pressure Converter Results Powered by Transpose Solutions Android App - Unit Converter ";
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