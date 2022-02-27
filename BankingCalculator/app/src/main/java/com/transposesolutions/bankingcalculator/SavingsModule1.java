package com.transposesolutions.bankingcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
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

public class SavingsModule1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    // declare instance variables required for the navigation drawer
    private ActionBarDrawerToggle mToggle;
    private AdView adView;
    // declare package prefix , SAVINGS_MESSAGE constant and key values
    public final static String SAVINGS_MESSAGE1 = "com.transposesolutions.bankingcalculator.MATURITY";
    public final static String SAVINGS_MESSAGE2 = "com.transposesolutions.bankingcalculator.INTEREST";
    public final static String SAVINGS_MESSAGE2A = "com.transposesolutions.bankingcalculator.TOTAL_DEPOSIT";
    public final static String SAVINGS_MESSAGE3 = "com.transposesolutions.bankingcalculator.YIELD";
    public final static String SAVINGS_MESSAGE4 = "com.transposesolutions.bankingcalculator.TAX";
    public final static String SAVINGS_MESSAGE5 = "com.transposesolutions.bankingcalculator.INTEREST_TAX";
    public final static String SAVINGS_MESSAGE6 = "com.transposesolutions.bankingcalculator.NET";
    public final static String SAVINGS_MESSAGE7 = "com.transposesolutions.bankingcalculator.DEPOSIT";
    public final static String SAVINGS_MESSAGE8 = "com.transposesolutions.bankingcalculator.MONTHLY";
    public final static String SAVINGS_MESSAGE9 = "com.transposesolutions.bankingcalculator.SAVINGS_TERM";
    public final static String SAVINGS_MESSAGE10 = "com.transposesolutions.bankingcalculator.ROI";
    public final static String SAVINGS_MESSAGE11 = "com.transposesolutions.bankingcalculator.INCOME_TAX";

    // declare EditText, and Spinner Widgets. Import the required class
    EditText input1, input2, input3, input4, input5;
    Button calculateButton;
    // declare value "zero" to the following double variable
    short AmortizationTerm = 0;
    double OpenBalance = 0;
    double CurrentBalance = 0;
    double MonthlyDeposit = 0;
    double MonthlyInterest = 0;
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

    // Method called to check for empty fields and check required values
    private void checkFieldsForEmptyValues() {
        Button button = findViewById(R.id.button_calculate);
        String s1 = input1.getText().toString();
        String s2 = input2.getText().toString();
        String s3 = input3.getText().toString();
        String s4 = input4.getText().toString();
        String s5 = input5.getText().toString();
        button.setEnabled(!s1.equals("") && !s2.equals("") && !s3.equals("") && !s4.equals("") && !s5.equals(""));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings_module1);


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

        // Capture the layout's EditText and Button Widget
        input1 = findViewById(R.id.user_input1);
        input2 = findViewById(R.id.user_input2);
        input3 = findViewById(R.id.user_input3);
        input4 = findViewById(R.id.user_input4);
        input5 = findViewById(R.id.user_input5);
        calculateButton = findViewById(R.id.button_calculate);
        Button activityReset = findViewById(R.id.reset);
        // OnClick listener to handle the event when the user taps the Button - Reset
        activityReset.setOnClickListener(v -> {
            Intent intent = new Intent(this, DepositModule1.class);
            startActivity(intent);
        });
        // set listeners
        input1.addTextChangedListener(mTextWatcher);
        input2.addTextChangedListener(mTextWatcher);
        input3.addTextChangedListener(mTextWatcher);
        input4.addTextChangedListener(mTextWatcher);
        input5.addTextChangedListener(mTextWatcher);
        // run once to disable if empty
        checkFieldsForEmptyValues();

        // check for required minimum value - Initial Deposit EditText (user_input1) Field
        input1.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                input1.setError("Please fill out this field! Enter a minimum value of 0 or greater value");
            }});
        // check for required minimum value - Monthly Deposit EditText (user_input2) Field
        input2.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                // Number of Monthly deposit
                float _monthly_deposit = Float.parseFloat(input2.getText().toString());
                if (_monthly_deposit < 1) {
                    input2.setError("Please fill out this field! Enter a minimum value of 1");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }});
        // check for required minimum value - Savings Term EditText (user_input3) Field
        input3.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                try {
                    // Savings term
                    float _savings_term = Float.parseFloat(input3.getText().toString());
                    if (_savings_term < 1) {
                        input3.setError("Please fill out this field! Enter a minimum value of 1");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
        //check for required minimum value for annual interest rate EditText (user_input4) Field
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

        // check for required minimum value - Tax Rate EditText (user_input5) Field
        input5.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                input5.setError("Please fill out this field! Enter a minimum value of 0 or greater value");
            }
        });
        // OnClick listener to handle the event when the user taps the Button - Calculate
        calculateButton.setOnClickListener(view -> {
            // Savings Calculator formula (Excel): FV(rate,nper,pmt,[pv],[type])
            // The rate argument is annual interest rate (%).
            // The NPER argument is time to save.
            // The PV (present value) is initial deposit.
            // The FV (future value) is Savings Goal.
            // Our logic to generate result based on the  above formula:
            // Retrieve Data Value from the activity

            // Initial Amount of Deposit
            float _initial_Deposit = Float.parseFloat(input1.getText().toString());
            // Number of Monthly deposit
            float _monthly_deposit = Float.parseFloat(input2.getText().toString());
            // Savings term
            float _savings_term = Float.parseFloat(input3.getText().toString());
            int mPaymentPeriods = (int) _savings_term;
            // Annual Rate of Interest
            float interest_rate = Float.parseFloat(input4.getText().toString());
            double _annual_interest = interest_rate / 100;
            // Tax Rate
            float tax_rate = Float.parseFloat(input5.getText().toString());
            double _taxRate = tax_rate / 100;

            //Check if Monthly Deposit EditText Field has minimum required value else prompt user to enter the required values
            if(_monthly_deposit < 1){
                requiredCheck1();
            }
            //Check if Savings term EditText Field has minimum required value else prompt user to enter the required values
            else if(_savings_term < 1){
                requiredCheck2();
            }
            //Check if Annual Interest Rate EditText Field has minimum required value else prompt user to enter the required values
            else if(interest_rate < 0.1){
                requiredCheck3();
            }
            //Check if Annual Interest Rate EditText Field has exceeded the maximum value else prompt user to enter the required values
            else if(interest_rate > 50){
                requiredCheck4();
            }

            else {
                AmortizationTerm = (short) mPaymentPeriods;
                OpenBalance = _initial_Deposit;
                MonthlyDeposit = _monthly_deposit;
                // Loop for calculating future value of savings balance
                for (int j = 0; j < AmortizationTerm; j++) {
                    // Calculate monthly cycle
                    if (j == 0) {
                        MonthlyInterest = (OpenBalance * _annual_interest) / 12;
                    }
                    MonthlyInterest = (OpenBalance * _annual_interest) / 12;
                    CurrentBalance = OpenBalance + MonthlyInterest;
                    OpenBalance = CurrentBalance + MonthlyDeposit;
                }
                // end of loop
                double _actual_Payments = _initial_Deposit + (_monthly_deposit * _savings_term);
                double _interest_Earned = OpenBalance - _actual_Payments;
                _interest_Earned = Math.round(_interest_Earned * 100.0) / 100.0;
                OpenBalance = Math.round(OpenBalance * 100.0) / 100.0;

                // Steps to calculate - APY
                double _step3 = 1 + _annual_interest / 12;
                double _step4 = Math.pow(_step3, 12) - 1;
                double _result_APY = Math.round((_step4 * 100.0) * 100) / 100.0;

                // Steps to calculate - Tax on Interest Earned
                double _taxable_Interest = _interest_Earned * _taxRate;
                _taxable_Interest = Math.round(_taxable_Interest * 100.0) / 100.0;

                // Steps to calculate - Net Interest After Tax Expense
                double _net_Interest = _interest_Earned - _taxable_Interest;
                _net_Interest = Math.round(_net_Interest * 100.0) / 100.0;

                // Steps to calculate - Net Maturity Value After Tax Expense
                double _net_Maturity = OpenBalance - _taxable_Interest;
                _net_Maturity = Math.round(_net_Maturity * 100.0) / 100.0;

                // The Intent constructor takes two parameters, a Context parameter and a subclass of Context the activity to start
                Intent intent = new Intent(SavingsModule1.this, SavingsModule2.class);
                //Android Bundle object and putExtra() Method is used to pass data between activities.
                Bundle bundle = new Bundle();
                String MATURITY = String.valueOf(OpenBalance);
                String INTEREST = String.valueOf(_interest_Earned);
                String ACTUAL_DEPOSITS = String.valueOf(_actual_Payments);
                String YIELD = String.valueOf(_result_APY);
                String TAX = String.valueOf(_taxable_Interest);
                String INTEREST_TAX = String.valueOf(_net_Interest);
                String NET = String.valueOf(_net_Maturity);
                String DEPOSIT = input1.getText().toString();
                String MONTHLY = input2.getText().toString();
                String TERM = input3.getText().toString();
                String ROI = input4.getText().toString();
                String INCOME_TAX = input5.getText().toString();
                bundle.putString(SAVINGS_MESSAGE1, MATURITY);
                bundle.putString(SAVINGS_MESSAGE2, INTEREST);
                bundle.putString(SAVINGS_MESSAGE2A, ACTUAL_DEPOSITS);
                bundle.putString(SAVINGS_MESSAGE3, YIELD);
                bundle.putString(SAVINGS_MESSAGE4, TAX);
                bundle.putString(SAVINGS_MESSAGE5, INTEREST_TAX);
                bundle.putString(SAVINGS_MESSAGE6, NET);
                bundle.putString(SAVINGS_MESSAGE7, DEPOSIT);
                bundle.putString(SAVINGS_MESSAGE8, MONTHLY);
                bundle.putString(SAVINGS_MESSAGE9, TERM);
                bundle.putString(SAVINGS_MESSAGE10, ROI);
                bundle.putString(SAVINGS_MESSAGE11, INCOME_TAX);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }//end of onCreate

    //Check if Monthly Deposit EditText Field has minimum required value else prompt user to enter the required values
    private void requiredCheck1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SavingsModule1.this);
        builder.setMessage("Monthly deposit should be equal to 1 or greater")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {

                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //Check if Savings term EditText Field has minimum required value else prompt user to enter the required values
    private void requiredCheck2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SavingsModule1.this);
        builder.setMessage("Savings term should be equal to 1 or greater")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {

                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    //Check if Annual Interest Rate EditText Field has minimum required value else prompt user to enter the required values
    private void requiredCheck3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SavingsModule1.this);
        builder.setMessage("annual interest should be at least 0.01")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {

                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //Check if Annual Interest Rate EditText Field has exceeded the maximum value else prompt user to enter the required values
    private void requiredCheck4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SavingsModule1.this);
        builder.setMessage("annual interest cannot exceed 50")
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