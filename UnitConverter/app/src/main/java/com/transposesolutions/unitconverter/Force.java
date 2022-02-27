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

public class Force extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Declare the instance variable Ad view
    private AdView adView;
    private ActionBarDrawerToggle mToggle;
    EditText UserInput;
    TextView result1,result2,result3,result4,result5,result6,result7,result8;
    Spinner selector_1;
    double x=0;
    double userValue1=0;
    String selectedItem;
    DecimalFormat decimalFormat = new DecimalFormat("#.#####");
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
            }else{
                checkForChanges1();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force);

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
                R.array.spinner_force_items, R.layout.spinner_selector_items);
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
                    update_Dyne();
                }else if(position==1){
                    update_Newton();
                }else if(position==2){
                    update_DeKaNewton();
                }else if(position==3){
                    update_KiloNewton();
                }else if (position==4){
                    update_Gram_force();
                }else if(position==5){
                    update_Kilogram_force();
                }else if(position==6){
                    update_Pound_force();
                }else if(position==7) {
                    update_Kip_force();
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
            case "Dyne (dyn)":
                x = 1;
                break;
            case "Newton (N)":
                x = 2;
                break;
            case "DeKaNewton (daN)":
                x = 3;
                break;
            case "KiloNewton (kN)":
                x = 4;
                break;
            case "Gram-force (gf)":
                x = 5;
                break;
            case "Kilogram-force (kgf)":
                x = 6;
                break;
            case "Pound-force (ibf)":
                x = 7;
                break;
            case "Kip-force (kipf)":
                x = 8;
                break;
            default:
                break;
        }
        if (x==1) {
            update_Dyne();
        }else if (x==2){
            update_Newton();
        }else if (x==3){
            update_DeKaNewton();
        }else if (x==4){
            update_KiloNewton();
        }else if (x==5){
            update_Gram_force();
        }else if (x==6){
            update_Kilogram_force();
        }else if (x==7){
            update_Pound_force();
        }else if (x==8){
            update_Kip_force();
        }
    }

    private void update_Dyne() {
        // Dyne to Dyne
        //example Dyne =1 Dyne = Dyne*1 result =1
        int valueSelector1 = (int) (userValue1 * 1);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Dyne to Newton
//example Dyne =1 Newton = Dyne*0.00001 result =0.00001
        double valueSelector2 =  (userValue1 * 1e-5);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Dyne to DeKaNewton
//example Dyne =1 DeKaNewton = Dyne*0.000001 result =0.000001
        double valueSelector3 =  (userValue1 * 1e-6);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Dyne to KiloNewton
//example Dyne =1 KiloNewton = Dyne*1.E-8 result =1.E-8
        double valueSelector4 = (userValue1 * 1e-8);
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Dyne to  Gram-force
//example Dyne =1 Gram-force = Dyne*0.0010197162 result =0.0010197162
        double valueSelector5 =  (userValue1 * 0.0010197162);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Dyne to Kilogram-force
//example Dyne =1 Kilogram-force = Dyne*0.0000010197 result =0.0000010197
        double valueSelector6 =  (userValue1 * 1.01972e-6);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Dyne  to Pound-force
//example Dyne =1 Pound-force = Dyne*0.0000022481 result =0.0000022481
        double valueSelector7 =  (userValue1 * 2.24809e-6);
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Dyne  to  Kip-force
//example Dyne =1 Kip-force = Dyne*2.248089431E-9 result =2.248089431E-9
        double valueSelector8 =  (userValue1 * 2.248089431E-9);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);

    }

    private void update_Newton() {
        // Newton to Dyne
        //example Newton =1 Dyne = Newton*100000 result =100000
        double valueSelector1 =  (userValue1 * 100000);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Newton to Newton
        //example Newton =1 Newton = Newton*1 result =1
        int valueSelector2 = (int) (userValue1 * 1);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Newton to DeKaNewton
        //example Newton =1 DeKaNewton = Newton*0.1 result =0.1
        double valueSelector3 =  (userValue1 * 0.1);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Newton to KiloNewton
        //example Newton =1 KiloNewton= Newton*0.001 result =0.001
        double valueSelector4 = (userValue1 * 0.001);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Newton to  Gram-force
        //example Newton =1 Gram-force = Newton*101.9716213 result =101.9716213
        double valueSelector5 =  (userValue1 * 101.9716213);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Newton to Kilogram-force
        //example Newton =1 Kilogram-force = Newton*0.1019716213 result =0.1019716213
        double valueSelector6 =  (userValue1 /9.807);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Newton  to Pound-force
        //example Newton =1 Pound-force = Newton*0.2248089431 result =0.2248089431
        double valueSelector7 =  (userValue1 / 4.448);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Newton  to  Kip-force
        //example Newton =1  Kip-force = Newton*0.0002248089 result =0.0002248089
        double valueSelector8 =  (userValue1 * 0.0002248089);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
    }

    private void update_DeKaNewton() {
        // DeKaNewton to Dyne
        //example DeKaNewton =1 Dyne = DeKaNewton*1000000 result =1000000
        double valueSelector1 =  (userValue1 *  1e+6);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // DeKaNewton to Newton
        //example DeKaNewton =1 Newton = DeKaNewton*10 result =10
        int valueSelector2 = (int) (userValue1 * 10);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //DeKaNewton to DeKaNewton
        // example DeKaNewton =1 DeKaNewton = DeKaNewton*1000000 result =1000000
        int valueSelector3 = (int) (userValue1 * 1);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //DeKaNewton to KiloNewton
//example DeKaNewton =1 KiloNewton = DeKaNewton*0.01 result =0.01
        double valueSelector4 = (userValue1 /100);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //DeKaNewton to  Gram-force
//example DeKaNewton =1 Gram-force = DeKaNewton*1019.716213 result =1019.716213
        double valueSelector5 =  (userValue1 * 1019.716213);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //DeKaNewton to Kilogram-force
//example DeKaNewton =1 Kilogram-force = DeKaNewton*1.019716213 result =1.019716213
        double valueSelector6 =  (userValue1 * 1.01972);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //DeKaNewton  to Pound-force
//example DeKaNewton =1 Pound-force = DeKaNewton*2.248089431 result =2.248089431
        double valueSelector7 =  (userValue1 * 2.24809);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //DeKaNewton  to  Kip-force
//example DeKaNewton =1 Kip-force = DeKaNewton*0.0022480894 result =0.0022480894
        double valueSelector8 =  (userValue1 * 0.0022480894);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
    }

    private void update_KiloNewton() {
        // KiloNewton to Dyne
        //example KiloNewton =1 Dyne = KiloNewton*100000000 result =100000000
        double valueSelector1 =  (userValue1 * 1e+8);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // KiloNewton to Newton
//example KiloNewton =1 Newton = KiloNewton*1000 result =1000
        int valueSelector2 = (int) (userValue1 * 1000);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //KiloNewton to DeKaNewton
//example KiloNewton =1 DeKaNewton = KiloNewton*100 result =100
        int valueSelector3 = (int) (userValue1 * 100);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //KiloNewton to KiloNewton
//example KiloNewton =1 KiloNewton = KiloNewton*1000000 result =1000000
        int valueSelector4 = (int) (userValue1 * 1);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //KiloNewton to  Gram-force
//example KiloNewton =1 Gram-force = KiloNewton*101971.6213 result =101971.6213
        double valueSelector5 =  (userValue1 * 101971.6213);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //KiloNewton to Kilogram-force
//example KiloNewton =1 Kilogram-force = KiloNewton*101.9716213 result =101.9716213
        double valueSelector6 =  (userValue1 * 101.972);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //KiloNewton  to Pound-force
//example KiloNewton =1 Pound-force = KiloNewton*224.8089431 result =224.8089431
        double valueSelector7 =  (userValue1 * 224.809);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //KiloNewton  to  Kip-force
//example KiloNewton =1 Kip-force = KiloNewton*0.2248089431 result =0.2248089431
        double valueSelector8 =  (userValue1 * 0.2248089431);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
    }

    private void update_Gram_force() {
        // Gram-force to Dyne
        //example  Gram-force =1 Dyne =  Gram-force*100000000 result =100000000

        double valueSelector1 =  (userValue1 * 980.665);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Gram-force to Newton
        //example  Gram-force =1 Newton =  Gram-force*0.00980665 result =0.00980665

        double valueSelector2 =  (userValue1 * 0.00980665);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Gram-force to DeKaNewton
        //example  Gram-force =1 DeKaNewton =  Gram-force*0.000980665 result =0.000980665

        double valueSelector3 =  (userValue1 * 0.000980665);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Gram-force to KiloNewton
        //example  Gram-force =1 KiloNewton =  Gram-force*0.0000098067 result =0.0000098067

        double valueSelector4 = (userValue1 * 0.0000098067);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Gram-force to  Gram-force
        //example  Gram-force =1 Gram-force =  Gram-force*1 result =1

        int valueSelector5 = (int) (userValue1 * 1);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Gram-force to Kilogram-force
        //example  Kilogram-force =1 Kilogram-force =  Gram-force*0.001 result =0.001

        double valueSelector6 =  (userValue1 * 0.001);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Gram-force  to Pound-force
        //example  Gram-force =1  Pound-force =  Gram-force*0.0022046226 result =0.0022046226

        double valueSelector7 =  (userValue1 * 0.0022046226);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Gram-force  to  Kip-force
        //example  Gram-force =1  Kip-force =  Gram-force*0.0000022046 result =0.0000022046
        double valueSelector8 =  (userValue1 * 0.0000022046);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
    }

    private void update_Kilogram_force() {
        // Kilogram-force to Dyne

        double valueSelector1 =  (userValue1 * 980665);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Kilogram-force to Newton

        double valueSelector2 =  (userValue1 * 9.80665);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Kilogram-force to DecaNewton

        double valueSelector3 =  (userValue1 * 0.980665);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Kilogram-force to KiloNewton

        double valueSelector4 = (userValue1 /102);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Kilogram-force to  Gram-force

        int valueSelector5 = (int) (userValue1 * 1000);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Kilogram-force to Kilogram-force

        int valueSelector6 = (int) (userValue1 * 1);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Kilogram-force  to Pound-force

        double valueSelector7 =  (userValue1 * 2.20462);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Kilogram-force  to  Kip-force

        double valueSelector8 =  (userValue1 * 0.0022046226);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
    }

    private void update_Pound_force() {
        // Pound-force to Dyne

        double valueSelector1 =  (userValue1 * 444822);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Pound-force to Newton

        double valueSelector2 =  (userValue1 * 4.44822);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Pound-force to DeKaNewton

        double valueSelector3 =  (userValue1 /2.248);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Pound-force to KiloNewton

        double valueSelector4 = (userValue1 /225);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Pound-force to  Gram-force

        double valueSelector5 =  (userValue1 * 453.59237);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Pound-force to Kilogram-force

        double valueSelector6 =  (userValue1 /2.205);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Pound-force  to Pound-force

        int valueSelector7 = (int) (userValue1 * 1);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Pound-force  to  Kip-force

        double valueSelector8 =  (userValue1 * 0.001);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
    }

    private void update_Kip_force() {
        // Kip-force to Dyne

        double valueSelector1 =  (userValue1 * 444822161.53);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Kip-force to Newton

        double valueSelector2 =  (userValue1 * 4448.2216153);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //Kip-force to DeKaNewton

        double valueSelector3 =  (userValue1 * 444.82216153);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //Kip-force to KiloNewton

        double valueSelector4 = (userValue1 * 4.4482216153);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        //Kip-force to  Gram-force

        double valueSelector5 =  (userValue1 * 453592.37);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        //Kip-force to Kilogram-force

        double valueSelector6 =  (userValue1 * 453.59237);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        //Kip-force  to Pound-force

        int valueSelector7 = (int) (userValue1 * 1000);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        //Kip-force  to  Kip-force

        int valueSelector8 = (int) (userValue1 * 1);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
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
    //
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
        String User_enter_data;
        User_enter_data = UserInput.getText().toString();

        // above declared string are exported to share activity
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody =  "Results based on your input for "+ User_enter_data +" "+selectedItem + "\n"
                + "Dyne (dyn) : " + activity_result + "\n"
                + "Newton (N) " + activity_result2 + "\n"
                + "DeKaNewton (daN) :" + activity_result3+ "\n"
                + "KiloNewton (kN): " + activity_result4 + "\n"
                + "Gram-force (gf): " + activity_result5  + "\n"
                + "Kilogram-force (kgf) :" + activity_result6+ "\n"
                + "Pound-force (ibf): " + activity_result7 + "\n"
                + "Kip-force (kipf) : " + activity_result8  + "\n";

        String shareSub = "Force Converter Results Powered by Transpose Solutions Android App - Unit Converter ";
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