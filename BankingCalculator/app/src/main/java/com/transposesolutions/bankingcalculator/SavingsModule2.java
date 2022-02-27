package com.transposesolutions.bankingcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class SavingsModule2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    // declare instance variables required for the navigation drawer
    private ActionBarDrawerToggle mToggle;
    private AdView adView;
    // declare TextView, and Spinner Widgets. Import the required class
    TextView result1, result2, result3, result4, result5, result6, netReturn, initialDeposit;
    Button mCompare, mAmortization;
    // declare package prefix , SAVINGS_MESSAGE constant, key values passed to Savings Module 3 - Amortization Schedule
    public final static String SAVINGS_MESSAGE12 = "com.transposesolutions.bankingcalculator.DEPOSIT";
    public final static String SAVINGS_MESSAGE13 = "com.transposesolutions.bankingcalculator.MONTHLY";
    public final static String SAVINGS_MESSAGE14 = "com.transposesolutions.bankingcalculator.SAVINGS_TERM";
    public final static String SAVINGS_MESSAGE15 = "com.transposesolutions.bankingcalculator.ROI";
    // declare package prefix , SAVINGS_MESSAGE constant, key values passed to Savings Module 5 - Compare Activity
    public final static String SAVINGS_RESULTS1 = "com.transposesolutions.bankingcalculator.MATURITY";
    public final static String SAVINGS_RESULTS2 = "com.transposesolutions.bankingcalculator.INTEREST";
    public final static String SAVINGS_RESULTS3 = "com.transposesolutions.bankingcalculator.TOTAL_DEPOSITS";
    public final static String SAVINGS_RESULTS4 = "com.transposesolutions.bankingcalculator.YIELD";
    public final static String SAVINGS_RESULTS5 = "com.transposesolutions.bankingcalculator.TAX";
    public final static String SAVINGS_RESULTS6 = "com.transposesolutions.bankingcalculator.INTEREST_TAX";
    public final static String SAVINGS_RESULTS7 = "com.transposesolutions.bankingcalculator.NET";
    public final static String SAVINGS_RESULTS8 = "com.transposesolutions.bankingcalculator.DEPOSIT";
    public final static String SAVINGS_RESULTS9 = "com.transposesolutions.bankingcalculator.MONTHLY";
    public final static String SAVINGS_RESULTS10 = "com.transposesolutions.bankingcalculator.SAVINGS_TERM";
    public final static String SAVINGS_RESULTS11 = "com.transposesolutions.bankingcalculator.ROI";
    public final static String SAVINGS_RESULTS12 = "com.transposesolutions.bankingcalculator.INCOME_TAX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings_module2);

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
        // Capture the layout's TextView Widget
        result1 = findViewById(R.id.result_1);
        result2 = findViewById(R.id.result_2);
        result3 = findViewById(R.id.result_3);
        result4 = findViewById(R.id.result_4);
        result5 = findViewById(R.id.result_5);
        result6 = findViewById(R.id.result_6);
        mCompare = findViewById(R.id.button_compare);
        mAmortization = findViewById(R.id.button_schedule);
        netReturn = findViewById(R.id.returnTextResult);
        initialDeposit = findViewById(R.id.investedTextResult);
        ProgressBar pieChart = findViewById(R.id.stats_progressbar);

        // get the data values from SavingsModule1.java
        Bundle bundle = getIntent().getExtras();
        String MATURITY = bundle.getString(SavingsModule1.SAVINGS_MESSAGE1);
        String INTEREST = bundle.getString(SavingsModule1.SAVINGS_MESSAGE2);
        String TOTAL_DEPOSIT =  bundle.getString(SavingsModule1.SAVINGS_MESSAGE2A);
        String YIELD = bundle.getString(SavingsModule1.SAVINGS_MESSAGE3);
        String TAX = bundle.getString(SavingsModule1.SAVINGS_MESSAGE4);
        String INTEREST_TAX = bundle.getString(SavingsModule1.SAVINGS_MESSAGE5);
        String NET = bundle.getString(SavingsModule1.SAVINGS_MESSAGE6);
        String DEPOSIT = bundle.getString(SavingsModule1.SAVINGS_MESSAGE7);
        String MONTHLY = bundle.getString(SavingsModule1.SAVINGS_MESSAGE8);
        String TERM = bundle.getString(SavingsModule1.SAVINGS_MESSAGE9);
        String ROI = bundle.getString(SavingsModule1.SAVINGS_MESSAGE10);
        String INCOME_TAX = bundle.getString(SavingsModule1.SAVINGS_MESSAGE11);
        // print the value to TextView
        // convert the string to double then format the string to print the value to TextView
        double _maturityValue = Double.parseDouble(MATURITY);
        result1.setText(String.format(Locale.getDefault(),"%,.2f", _maturityValue));
        double _interestEarned = Double.parseDouble(INTEREST);
        result2.setText(String.format(Locale.getDefault(),"%,.2f", _interestEarned));
        double _APY = Double.parseDouble(YIELD);
        result3.setText(String.format(Locale.getDefault(),"%,.2f",_APY));
        double _tax = Double.parseDouble(TAX);
        result4.setText(String.format(Locale.getDefault(),"%,.2f",_tax));
        double _interestAfterTax = Double.parseDouble(INTEREST_TAX);
        result5.setText(String.format(Locale.getDefault(),"%,.2f",_interestAfterTax));
        double _netMaturityValue = Double.parseDouble(NET);
        result6.setText(String.format(Locale.getDefault(),"%,.2f",_netMaturityValue));

        // onClick Handler called when the user taps the button - compare
        mCompare.setOnClickListener(v -> {
            // using Intent send the use to Module 4 -  Compare Activity
            Intent intent = new Intent(SavingsModule2.this, SavingsModule4.class);
            // using shared preferences to store the credentials */
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("RESULT_SAVINGS_PREFERENCES", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SAVINGS_RESULTS1, MATURITY);
            editor.putString(SAVINGS_RESULTS2, INTEREST);
            editor.putString(SAVINGS_RESULTS3, TOTAL_DEPOSIT);
            editor.putString(SAVINGS_RESULTS4, YIELD);
            editor.putString(SAVINGS_RESULTS5, TAX);
            editor.putString(SAVINGS_RESULTS6, INTEREST_TAX);
            editor.putString(SAVINGS_RESULTS7, NET);
            editor.putString(SAVINGS_RESULTS8, DEPOSIT);
            editor.putString(SAVINGS_RESULTS9, MONTHLY);
            editor.putString(SAVINGS_RESULTS10, TERM);
            editor.putString(SAVINGS_RESULTS11, ROI);
            editor.putString(SAVINGS_RESULTS12, INCOME_TAX);
            /* Commits the changes and adds them to the file */
            editor.apply();
            startActivity(intent);
        });



        // onClick Handler called when the user taps the button - Amortization
        mAmortization.setOnClickListener(v -> {
            // The Intent constructor takes two parameters, a Context parameter and a subclass of Context the activity to start
            Intent intent = new Intent(SavingsModule2.this, SavingsModule3.class);
            //Android Bundle object and putExtra() Method is used to pass data between activities.
            Bundle bundle2 = new Bundle();
            // store and pass data values from one activity to another
            bundle2.putString(SAVINGS_MESSAGE12, DEPOSIT);
            bundle2.putString(SAVINGS_MESSAGE13, MONTHLY);
            bundle2.putString(SAVINGS_MESSAGE14, TERM);
            bundle2.putString(SAVINGS_MESSAGE15, ROI);
            intent.putExtras(bundle2);
            startActivity(intent);
        });

        // Initiate and Populate Pie Chart
        double mInterest = Double.parseDouble(INTEREST) ;
        double mInitialDeposit = Double.parseDouble(DEPOSIT);
        double mReturnPercentage = (mInterest /(mInitialDeposit+mInterest))*100;
        double mDepositPercentage = (mInitialDeposit / (mInitialDeposit+ mInterest))*100;
        double chartData = mReturnPercentage / mDepositPercentage;
        int progress = (int) (chartData * 100);
        pieChart.setProgress(Integer.parseInt(String.valueOf(progress)));
        String deposit  =String.format(Locale.getDefault(),"%,.2f", mDepositPercentage)+"%";
        initialDeposit.setText(deposit);
        String returns =String.format(Locale.getDefault(),"%,.2f", mReturnPercentage)+"%";
        netReturn.setText(returns);


    }//End of onCreate

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
    // When user taps the share button, carry the values from screen and share
    private void share() {
        // get the data values from SavingsModule1.java
        Bundle bundle = getIntent().getExtras();
        String MATURITY = bundle.getString(SavingsModule1.SAVINGS_MESSAGE1);
        String INTEREST = bundle.getString(SavingsModule1.SAVINGS_MESSAGE2);
        String YIELD = bundle.getString(SavingsModule1.SAVINGS_MESSAGE3);
        String TAX = bundle.getString(SavingsModule1.SAVINGS_MESSAGE4);
        String INTEREST_TAX = bundle.getString(SavingsModule1.SAVINGS_MESSAGE5);
        String NET = bundle.getString(SavingsModule1.SAVINGS_MESSAGE6);
        String DEPOSIT = bundle.getString(SavingsModule1.SAVINGS_MESSAGE7);
        String MONTHLY = bundle.getString(SavingsModule1.SAVINGS_MESSAGE8);
        String TERM = bundle.getString(SavingsModule1.SAVINGS_MESSAGE9);
        String ROI = bundle.getString(SavingsModule1.SAVINGS_MESSAGE10);
        String INCOME_TAX = bundle.getString(SavingsModule1.SAVINGS_MESSAGE11);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Your Estimated Savings Balance:" + MATURITY + ",\n" + "Total Interest Earned:" + INTEREST + ",\n" + "APY:" + YIELD + "%" + ",\n"
                + "Tax on Interest Earned:" + TAX + ",\n" + "Interest Earned after Tax:" + INTEREST_TAX + ",\n" + "Net Savings Value:" + NET + ",\n"
                +  "\n" + "Above result is calculated based on your input:"
                +  "\n" + "Initial Deposit:"+ DEPOSIT + ",\n" + "Monthly Deposit:" + MONTHLY +  ",\n"
                + "Savings Term:" + TERM + "months" +",\n" + "Annual Interest (Compounded):" + ROI + "%" + ",\n" + "Your Tax Rate:" + INCOME_TAX + "%" + ".\n";
        String shareSub = "Your Estimated Savings Balance Calculated by Transpose Solutions: Android App - Banking Calculator";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    // inflate custom toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tool,menu);
        return true;
    }
    // override method to responsible for responding correctly to the items specified in the menu resource file.
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        if(item.getItemId() == R.id.action_share){
            share();
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