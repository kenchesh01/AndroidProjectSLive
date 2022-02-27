package com.transposesolutions.bankingcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;

public class GoalModule4 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    // declare instance variables required for the navigation drawer
    private ActionBarDrawerToggle mToggle;
    private AdView adView;
    // declare package prefix , GOAL_MESSAGE constant and key values
    public final static String GOAL_COMPARE1 = "com.transposesolutions.bankingcalculator.CONTRIBUTION";
    public final static String GOAL_COMPARE2 = "com.transposesolutions.bankingcalculator.INTEREST";
    public final static String GOAL_COMPARE3 = "com.transposesolutions.bankingcalculator.TOTAL_DEPOSIT";
    public final static String GOAL_COMPARE4 = "com.transposesolutions.bankingcalculator.SAVINGS_GOAL";
    public final static String GOAL_COMPARE5 = "com.transposesolutions.bankingcalculator.INITIAL_INVESTMENT";
    public final static String GOAL_COMPARE6 = "com.transposesolutions.bankingcalculator.TIME_SAVE";
    public final static String GOAL_COMPARE7 = "com.transposesolutions.bankingcalculator.INTEREST_RATE";
    // declare EditText Widgets. Import the required class
    EditText input1, input2, input3, input4;
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
            checkFieldsForEmptyValues();
        }
    };
    private void checkFieldsForEmptyValues() {
        Button button = findViewById(R.id.button_calculate);
        String s1 = input1.getText().toString();
        String s2 = input2.getText().toString();
        String s3 = input3.getText().toString();
        String s4 = input4.getText().toString();

        button.setEnabled(!s1.equals("") && !s2.equals("") && !s3.equals("") && !s4.equals(""));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_module4);

        //Get the value from the ValidateStoreKey class using getValue method. if the result is true - remove the Ads from activity and if it is false display Ads
        //checking for the paid user or free user
        // Define a boolean value and assign a value by obtaining it from ValidateStoreKey class using getValue
        String productKey = "banking_1010";
        Boolean validate = new ValidateStoreKey(this).getPurchaseKey(this,"myPref", productKey);
        if (validate){
            // Bind the XML views to Java Code Elements
            ScrollView scrollView = findViewById(R.id.scroll);
            DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams)scrollView.getLayoutParams();
            // Set/Reset Margin for the Scroll View
            layoutParams.topMargin = 10;
            scrollView.setLayoutParams(layoutParams);
        } else {
            // Load an ad into the AdMob banner view.
            FrameLayout adContainerView = findViewById(R.id.ad_view_container);
            // Step 1 - Create an AdView and set the ad unit ID on it.
            adView = new AdView(this);
            adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
            adContainerView.addView(adView);
            loadBanner();
        }

        //Load Navigation view, add toggle button to the drawer layout,
        // setNavigationItemSelectedListener and onOptionsItemSelected.
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
        // Capture the layout's EditText and Spinner Widget
        input1 = findViewById(R.id.user_input1);
        input2 = findViewById(R.id.user_input2);
        input3 = findViewById(R.id.user_input3);
        input4 = findViewById(R.id.user_input4);
        Button calculateButton = findViewById(R.id.button_calculate);
        Button activityReset = findViewById(R.id.reset);
        // OnClick listener to handle the event when the user taps the Button - Reset
        activityReset.setOnClickListener(v -> {
            Intent intent = new Intent(this, GoalModule4.class);
            startActivity(intent);
        });
        // set listeners
        input1.addTextChangedListener(mTextWatcher);
        input2.addTextChangedListener(mTextWatcher);
        input3.addTextChangedListener(mTextWatcher);
        input4.addTextChangedListener(mTextWatcher);
        // run once to disable if empty
        checkFieldsForEmptyValues();
        // Check for minimum and maximum required values and alert user with message to fix the required values
        //check for required minimum value for Savings Goal (user_input1) Field
        input1.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                // Retrieve Savings Goal Value from the activity
                float _savingsGoal = Float.parseFloat(input1.getText().toString());
                if (_savingsGoal < 100) {
                    input1.setError("Please fill out this field! Enter a minimum value of 100 or greater value");
                }} catch (NumberFormatException e) {
                e.printStackTrace();
            }});
        // check for required minimum value for initial deposit not equal or greater than Savings Goal (user_input1) Field
        input1.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                // Retrieve Savings Goal Value from the activity
                float _savingsGoal = Float.parseFloat(input1.getText().toString());
                // Retrieve Initial Deposit Value from the activity
                float _initialDeposit = Float.parseFloat(input2.getText().toString());
                if (_initialDeposit >= _savingsGoal) {
                    input1.setError("Savings Goal must be greater than first deposit");
                }} catch (NumberFormatException e) {
                e.printStackTrace();
            }});


        //check for required minimum value for time to save (user_input3) Field
        input3.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                try {
                    // Retrieve Time to Save (n) from the activity
                    float _timeToSave = Float.parseFloat(input3.getText().toString());
                    if (_timeToSave < 1) {
                        input3.setError("Please fill out this field! Enter a minimum value of 1");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }}});
        //check for required minimum value for annual interest rate (user_input4) Field
        input4.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                // Retrieve Annual Rate of Interest (r) from the activity
                float _annualInterestRate = Float.parseFloat(input4.getText().toString());
                if (_annualInterestRate < 0.1) {
                    input4.setError("Please fill out this field! Enter a minimum value of 0.01 or greater");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }});
        //check for required maximum value for annual interest rate (user_input4) Field
        input4.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                float _annualInterestRate = Float.parseFloat(input4.getText().toString());
                if (_annualInterestRate > 50){
                    input4.setError("Enter a value in the range of 0.01 to 50");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }});


        // OnClick listener to handle the event when the user taps the Button - Calculate
        calculateButton.setOnClickListener(view -> {
            // Savings Goal formula (Excel): Using the function PMT(rate,NPER,PV,FV)
            // The rate argument is annual interest rate (%).
            // The NPER argument is time to save.
            // The PV (present value) is initial deposit.
            // The FV (future value) is Savings Goal.
            // Our logic to generate result based on the  above formula:
            // Retrieve Savings Goal Value from the activity
            float _savingsGoal = Float.parseFloat(input1.getText().toString());
            // Retrieve Initial Deposit Value from the activity
            float _firstDeposit = Float.parseFloat(input2.getText().toString());
            // Retrieve Time to Save (n) from the activity
            float _timeToSave = Float.parseFloat(input3.getText().toString());
            // Retrieve Annual Rate of Interest (r) from the activity
            float _annualInterestRate = Float.parseFloat(input4.getText().toString());
            // Check for minimum and maximum required values and alert user with message to fix the required values
            if(_savingsGoal < 100){
                requiredCheck1();
            }
            //check for required minimum value for savings goal is greater than initial deposit
            else if(_firstDeposit >= _savingsGoal){
                requiredCheck2();
            }
            //check for required minimum value for time to save
            else if(_timeToSave< 1){
                requiredCheck3();
            }
            //check for required minimum value for annual interest rate
            else if(_annualInterestRate< 0.1){
                requiredCheck4();
            }
            //check for required maximum value for annual interest rate
            else if(_annualInterestRate > 50){
                requiredCheck5();
            }
            // Steps to calculate - Monthly deposit  & Interest earned
            else {
                double _step1 = _savingsGoal-_firstDeposit;
                double _step2 = (_annualInterestRate/100)/12;
                double numerator = (_step1*_step2);
                double _step3 = 1 + _step2;
                double _step4= _firstDeposit*_step2;
                double denominator = Math.pow(_step3,_timeToSave) - 1;
                double _monthlyDeposit = (numerator/denominator)-_step4;
                double _step5 = _monthlyDeposit*_timeToSave;
                double _step6 = _savingsGoal-_step5;
                double _InterestEarned = _step6-_firstDeposit;
                _InterestEarned = Math.round(_InterestEarned * 100.0)/100.0;
                _monthlyDeposit = Math.round(_monthlyDeposit *100.0)/100.0;
                double _totalDeposit = _savingsGoal - _InterestEarned;
                _totalDeposit = Math.round(_totalDeposit *100.0)/100.0;
                // The Intent constructor takes two parameters, a Context parameter and a subclass of Context the activity to start
                Intent intent = new Intent(GoalModule4.this, GoalModule5.class);
                // using shared preferences to store the credentials */
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SHARED_PREFERENCES", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String CONTRIBUTION = String.valueOf(_monthlyDeposit);
                String INTEREST = String.valueOf(_InterestEarned);
                String DEPOSITS = String.valueOf(_totalDeposit);
                String GOAL = input1.getText().toString();
                String INVESTMENT = input2.getText().toString();
                String TIME = input3.getText().toString();
                String ROI =  input4.getText().toString();
                editor.putString(GOAL_COMPARE1, CONTRIBUTION);
                editor.putString(GOAL_COMPARE2, INTEREST);
                editor.putString(GOAL_COMPARE3, DEPOSITS);
                editor.putString(GOAL_COMPARE4, GOAL);
                editor.putString(GOAL_COMPARE5, INVESTMENT);
                editor.putString(GOAL_COMPARE6, TIME);
                editor.putString(GOAL_COMPARE7, ROI);
                /* Commits the changes and adds them to the file */
                editor.apply();
                startActivity(intent);
            }});

    }//end of onCreate

    //check for required minimum value for Savings Goal
    private void requiredCheck1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GoalModule4.this);
        builder.setMessage("Savings Goal must be at least 100")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //check for required minimum value for Initial deposit is not equal or greater than saving goal
    private void requiredCheck2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GoalModule4.this);
        builder.setMessage("Savings Goal must be greater than first deposit")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //check for required minimum value for time to save
    private void requiredCheck3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GoalModule4.this);
        builder.setMessage("Time to save must be above zero")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //check for required minimum value for annual interest rate
    private void requiredCheck4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GoalModule4.this);
        builder.setMessage("Annual Interest must be at least 0.01")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //check for required maximum value for annual interest rate
    private void requiredCheck5() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GoalModule4.this);
        builder.setMessage("Annual Interest must be in the range of 0.01 to 50")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
    public  boolean onOptionsItemSelected(MenuItem item){
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
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

        } else if (id == R.id.CD) {
            Intent intent = new Intent(this, DepositModule1.class);
            startActivity(intent);
        } else if (id == R.id.savings) {
//            new ValidateStoreKey(this).setPurchaseKey(this,"myPref",productKey, false );
            Intent intent = new Intent(this, SavingsModule1.class);
            startActivity(intent);
        } else if (id == R.id.loan_Calculator) {
            Intent intent = new Intent(this, LoanModule1.class);
            startActivity(intent);
        }else if (id == R.id.savings_goal) {
            Intent intent = new Intent(this, GoalModule1.class);
            startActivity(intent);
        }else if (id == R.id.investment_Calculator) {
            Intent intent = new Intent(this, ReturnModule1.class);
            startActivity(intent);
        }else if (id == R.id.currency_Calculator) {
            Intent intent = new Intent(this, Currency.class);
            startActivity(intent);
        } else if (id == R.id.nav_rate) {
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
            String shareBody = "Check out this great Banking Calculator app. This app helps you to simulate your future savings based on the compound interest formula " +  "\n"+  "\n"
                    + "Google Play store:" +  "\n" +"https://play.google.com/store/apps/details?id=com.transposesolutions.bankingcalculator&hl=en" + "\n";
            String shareSub = "Check out this great Banking Calculator app from Transpose Solutions";
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