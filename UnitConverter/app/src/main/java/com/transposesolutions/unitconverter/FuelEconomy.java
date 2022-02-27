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

public class FuelEconomy extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Declare the instance variable Ad view
    private AdView adView;
    private ActionBarDrawerToggle mToggle;
    EditText UserInput;
    TextView result1,result2,result3,result4;
    Spinner selector_1;
    double x=0;
    double userValue1=0;
    String selectedItem;
    DecimalFormat decimalFormat = new DecimalFormat("#.#####");

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

            }else{
                checkForChanges1();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_economy);
        //UI elements
        UserInput = findViewById(R.id.user_input);
        selector_1 = findViewById(R.id.selector_1);
        result1=findViewById(R.id.Result_1);
        result2=findViewById(R.id.Result_2);
        result3=findViewById(R.id.Result_3);
        result4=findViewById(R.id.Result_4);

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
                R.array.spinner_fuel_items, R.layout.spinner_selector_items);
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
                    update_Km_liter();
                }else if(position==1){
                    update_Liter_kilometers();
                }else if(position==2){
                    update_mi_gal_us();
                }else if(position==3){
                    update_mi_gal_uk();
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
            case "Kilometer per liter (km/L)":
                x = 1;
                break;
            case "Liter per 100 kilometers (L/km)":
                x = 2;
                break;
            case "Miles per gallon (mi/gal - US)":
                x = 3;
                break;
            case "Miles per gallon (mi/gal - UK)":
                x = 4;
                break;
            default:
                break;
        }
        if(x==1){
            update_Km_liter();
        }else if(x==2){
            update_Liter_kilometers();
        }else if(x==3){
            update_mi_gal_us();
        }else if(x==4){
            update_mi_gal_uk();
        }
    }

    private void update_mi_gal_uk() {
        //  Miles per gallon (mi/gal - UK) to Kilometer per liter (km/L)
        //  example  (mi/gal - Uk) =1  (km/L)=(mi/gal - Uk) * 0.35400619  result = 0.35400619
        double valueSelector1 =  (userValue1 / 2.825);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //  Miles per gallon (mi/gal - UK) to Liter per 100 kilometers (L/km)
        //  example  (mi/gal - Uk) =1  (L/km)=282.48093628*(mi/gal - Uk)  result = 282.48093628
        double valueSelector2 =  (282.481/ userValue1);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //  Miles per gallon (mi/gal - UK) to Miles per gallon (mi/gal - US)
        //  example  (mi/gal - Uk) =1  (mi/gal - US)=(mi/gal - Uk) * 0.8326741846  result =0.8326741846
        double valueSelector3 =  (userValue1 / 1.201);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        //  Miles per gallon (mi/gal - UK) to Miles per gallon (mi/gal - UK)
        //  example  (mi/gal - Uk) =1  (mi/gal - Uk)=(mi/gal - Uk) * 1  result = 1
        int valueSelector4 = (int) (userValue1 * 1);
        String _valueSelector4 = String.valueOf(valueSelector4);
        result4.setText(_valueSelector4);
    }

    private void update_mi_gal_us() {
        // Miles per gallon (mi/gal - US) to Kilometer per liter (km/L)
        //  example  (mi/gal - US) =1  (km/L)=(mi/gal - US) * 0.425144  result = 0.425144;
        double valueSelector1 =  (userValue1 / 2.352 );
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //Miles per gallon (mi/gal - US) to Liter per 100 kilometers (L/km)
        //  example  (mi/gal - US) =1    (L/km)=235.21458329*(mi/gal - US)   result = 235.215;
        double valueSelector2 =  (235.215/userValue1);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        //  Miles per gallon (mi/gal - US) to Miles per gallon (mi/gal - US)
        //  example  (mi/gal - US) =1   (mi/gal - US)=(mi/gal - US) * 1  result = 1;
        int valueSelector3 = (int) (userValue1 * 1);
        String _valueSelector3 = String.valueOf(valueSelector3);
        result3.setText(_valueSelector3);
        // Miles per gallon (mi/gal - US) to Miles per gallon (mi/gal - UK)
        //  example  (mi/gal - US) =1   (mi/gal - Uk)=(mi/gal - US) * 1.2009499255  result = 1.2009499255
        double valueSelector4 = (userValue1 * 1.201);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
    }

    private void update_Liter_kilometers() {
        //  Liter per 100 kilometers (L/km) to Kilometer per liter (km/L)
        // example  (L/km) =1   (km/L)=100*(L/km) result = 100;
        int valueSelector1 = (int) (100/userValue1);
        String _valueSelector1 = decimalFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        //  Liter per 100 kilometers (L/km) to Liter per 100 kilometers (L/km)
        // example  (L/km) =1   (L/km)=(L/km)*1  result = 1;
        int valueSelector2 = (int) (userValue1* 1);
        String _valueSelector2 = String.valueOf(valueSelector2);
        result2.setText(_valueSelector2);
        // Liter per 100 kilometers (L/km) to Miles per gallon (mi/gal - US)
        // example  (L/km) =1   (mi/gal - US)=235.21458329*(L/km) result =235.21458329;
        double valueSelector3 =  ( 235.215 /((100*userValue1)/100));
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // example  (L/km) =1   (mi/gal - UK)=282.48093628*(L/km)  result = 282.48093628;
        //  Liter per 100 kilometers (L/km) to Miles per gallon (mi/gal - UK)
        double valueSelector4 = (282.481/((100*userValue1)/100));
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
    }


    private void update_Km_liter() {
        //  Kilometer per liter (km/L) to Kilometer per liter (km/L)
        // example (km\L) = 1 (km\L ) = (km\L) * 1 result = 1;
        int valueSelector1 = (int) (userValue1 * 1);
        String _valueSelector1 = String.valueOf(valueSelector1);
        result1.setText(_valueSelector1);
        //  Kilometer per liter (km/L) to Liter per 100 kilometers (L/km)
        // example (km\L) = 1 (L/km) = 1(km\L) *100 result = 100;
        int valueSelector2 = (int) (100/userValue1);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Kilometer per liter (km/L) to Miles per gallon (mi/gal - US)
        // example (km\L) = 1 (mi/gal - US) = (km\L) * 2.3521458329  result = 2.3521458329;
        double valueSelector3 =  (userValue1 * 2.352);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Kilometer per liter (km/L) to Miles per gallon (mi/gal - UK)
        // example (km\L) = 1 (mi/gal - UK) = (km\L) * 2.8248093628 result = 2.8248093628;
        double valueSelector4 = (userValue1 * 2.8241);
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
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
        String User_enter_data;
        User_enter_data = UserInput.getText().toString();
        // above declared string are exported to share activity
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody =   "Results based on your input for "+ User_enter_data + " "+ selectedItem + "\n"
                + "Kilometer per liter (km/L) : " + activity_result + "\n"
                + "Liter per 100 kilometers (L/km) :" + activity_result2 + "\n"
                + " Miles per gallon (mi/gal - US) :" + activity_result3+ "\n"
                + "Miles per gallon (mi/gal - UK) : " + activity_result4 + ".\n";
        String shareSub = "Fuel Economy Converter Results Powered by Transpose Solutions Android App - Unit Converter ";
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