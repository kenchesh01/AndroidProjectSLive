package com.transposesolutions.bankingcalculator;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;

public class LoanModule4 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    // declare instance variables required for the navigation drawer
    private ActionBarDrawerToggle mToggle;
    private AdView adView;
    // declare package prefix , LOAN_COMPARE constant and key values
    public final static String LOAN_COMPARE1 = "com.transposesolutions.loancalculator.mPayment";
    public final static String LOAN_COMPARE2 = "com.transposesolutions.loancalculator.mPrincipal";
    public final static String LOAN_COMPARE3 = "com.transposesolutions.loancalculator.mInterest";
    public final static String LOAN_COMPARE4 = "com.transposesolutions.loancalculator.mPayments";
    public final static String LOAN_COMPARE5 = "com.transposesolutions.loancalculator.mLoan";
    public final static String LOAN_COMPARE6 = "com.transposesolutions.loancalculator.mTerm";
    public final static String LOAN_COMPARE7 = "com.transposesolutions.loancalculator.mRate";
    EditText input1, input2, input3;

    //  create a textWatcher member
    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };
    void checkFieldsForEmptyValues() {
        Button b = findViewById(R.id.button_calculate);
        String s1 = input1.getText().toString();
        String s2 = input2.getText().toString();
        String s3 = input3.getText().toString();

        b.setEnabled(!s1.equals("") && !s2.equals("") && !s3.equals(""));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_module4);

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
        // Declare activity UI elements
        input1 = findViewById(R.id.user_input1);
        input2 = findViewById(R.id.user_input2);
        input3 = findViewById(R.id.user_input3);
        Button activityReset = findViewById(R.id.redo);
        // OnClick listener to handle the event when the user taps the Button - Reset
        activityReset.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoanModule4.class);
            startActivity(intent);
        });

        final Button calculateButton = findViewById(R.id.button_calculate);
        // set listeners
        input1.addTextChangedListener(mTextWatcher);
        input2.addTextChangedListener(mTextWatcher);
        input3.addTextChangedListener(mTextWatcher);

        // run once to disable if empty
        checkFieldsForEmptyValues();
        // Check for minimum and maximum required values and alert user with message to fix the required values
        // validation for minimum loan value
        input1.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                // Retrieve Loan Value from the activity
                float _loanValue = Float.parseFloat(input1.getText().toString());
                if (_loanValue < 1000) {
                    input1.setError("Please fill out this field! Enter a minimum value of 1000 or greater value");
                }} catch (NumberFormatException e) {
                e.printStackTrace();
            }});

        //Validation for minimum term limit
        input2.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                // Retrieve Loan Term Value from the activity
                float _term = Float.parseFloat(input2.getText().toString());
                if (_term <= 6) {
                    input2.setError("Please fill out this field! Enter a minimum value of 6");
                }} catch (NumberFormatException e) {
                e.printStackTrace();
            }});
        //check for required minimum value for interest rate (
        input3.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                // Retrieve Annual Rate of Interest (r) from the activity
                float _interest_Rate = Float.parseFloat(input3.getText().toString());
                if (_interest_Rate < 0.1) {
                    input3.setError("Please fill out this field! Enter a minimum value of 0.1 or greater");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }});

        //check for required maximum value for interest rate (
        input3.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                // Retrieve Annual Rate of Interest (r) from the activity
                float _interest_Rate = Float.parseFloat(input3.getText().toString());
                if (_interest_Rate > 50) {
                    input3.setError("Please fill out this field! interest cannot exceed 50");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }});

        calculateButton.setOnClickListener(view -> {
            // get user input - loan value as a String value
            float _loanValue = Float.parseFloat(input1.getText().toString());
            // get user input - term as a String value
            float _term = Float.parseFloat(input2.getText().toString());
            // get user input - interest rate as a String value
            float _loanInterest = Float.parseFloat(input3.getText().toString());


            //check for required minimum value for loan value
            if (_loanValue < 1000) {
                requiredCheck1();
            }
            //check for required minimum value for loan term
            else if (_term < 6) {
                requiredCheck2();
            }
            //check for required minimum value for annual interest rate
            else if(_loanInterest< 0.1){
                requiredCheck3();
            }
            //check for required maximum value for annual interest rate
            else if(_loanInterest > 50){
                requiredCheck4();
            }else {
                // Steps to calculate - Monthly Payment / EMI
                double _interestRate = (_loanInterest / 100) / 12;
                int mPaymentPeriods = (int) _term;
                double mPart1 = (_loanValue * _interestRate);
                double mNumerator = (Math.pow((1 + _interestRate), mPaymentPeriods));
                double mDenominator = (mNumerator - 1);
                double mPart2 = mPart1 * mNumerator;
                double calculatedEMIResult = mPart2 / mDenominator;
                calculatedEMIResult = Math.round(calculatedEMIResult * 100.0) / 100.0;
                System.out.println(calculatedEMIResult);


                // Steps to calculate - Total Principal Paid
                double totalPrincipal = _loanValue;
                totalPrincipal = Math.round(totalPrincipal * 100.0) / 100.0;
                System.out.println(totalPrincipal);


                // Steps to calculate - Total Interest Paid
                double Payments = (mPart2 / mDenominator) * mPaymentPeriods;
                double totalInterest = Payments - _loanValue;
                totalInterest = Math.round(totalInterest * 100.0) / 100.0;
                System.out.println(totalInterest);


                // Steps to calculate - Total Principal Paid
                double totalPayments = totalPrincipal + totalInterest;
                totalPayments = Math.round(totalPayments * 100.0) / 100.0;
                System.out.println(totalPayments);

                //Convert the double value to String value
                String mPayment = String.valueOf(calculatedEMIResult);
                String mPrincipal = String.valueOf(_loanValue);
                String mInterest = String.valueOf(totalInterest);
                String mPayments = String.valueOf(totalPayments);
                String mLoan = input1.getText().toString();
                String mTerm = input2.getText().toString();
                String mRate = input3.getText().toString();

                // using shared preferences to store the credentials */
                SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("COMPARE_PERSONAL_PREFERENCES", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString(LOAN_COMPARE1, mPayment);
                editor.putString(LOAN_COMPARE2, mPrincipal);
                editor.putString(LOAN_COMPARE3, mInterest);
                editor.putString(LOAN_COMPARE4, mPayments);
                editor.putString(LOAN_COMPARE5, mLoan);
                editor.putString(LOAN_COMPARE6, mTerm);
                editor.putString(LOAN_COMPARE7, mRate);
                // Commits the changes and adds them to the file
                editor.apply();
                //Method to export the above calculated values to pass it to Result Page
                Intent intent = new Intent(LoanModule4.this, LoanModule5.class);
                startActivity(intent);
            }
        });
    }
    // check for required minimum value for loan value
    private void requiredCheck1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoanModule4.this);
        builder.setMessage("Loan amount must be at least 1000")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    // check for required minimum value for loan term
    private void requiredCheck2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoanModule4.this);
        builder.setMessage("Term should be at least 6 months")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //check for required minimum value for annual interest rate
    private void requiredCheck3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoanModule4.this);
        builder.setMessage("Annual Interest must be at least 0.1 or greater")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //check for required maximum value for annual interest rate
    private void requiredCheck4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoanModule4.this);
        builder.setMessage("Annual Interest must be in the range of 0.1 to 50")
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

    /// override method to responsible for responding correctly to the items specified in the menu resource file.
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