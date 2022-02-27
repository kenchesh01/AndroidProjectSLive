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

public class GoalModule2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    // declare instance variables required for the navigation drawer
    private ActionBarDrawerToggle mToggle;
    private AdView adView;
    // declare package prefix , GOAL_MESSAGE constant, key values passed to GoalModule3 -Amortization Schedule
    public final static String GOAL_MESSAGE7 = "com.transposesolutions.bankingcalculator.SAVINGS_GOAL";
    public final static String GOAL_MESSAGE8 = "com.transposesolutions.bankingcalculator.INITIAL_INVESTMENT";
    public final static String GOAL_MESSAGE9 = "com.transposesolutions.bankingcalculator.TIME_SAVE";
    public final static String GOAL_MESSAGE10 = "com.transposesolutions.bankingcalculator.INTEREST_RATE";
    // Key values for storing and passing the values GoalModule4 - Compare Activity
    public final static String DISPLAY_MESSAGE1 = "com.transposesolutions.bankingcalculator.CONTRIBUTION";
    public final static String DISPLAY_MESSAGE1A = "com.transposesolutions.bankingcalculator.TOTAL_DEPOSIT";
    public final static String DISPLAY_MESSAGE2 = "com.transposesolutions.bankingcalculator.EARNED_INTEREST";
    public final static String DISPLAY_MESSAGE3 = "com.transposesolutions.bankingcalculator.GOAL";
    public final static String DISPLAY_MESSAGE4 = "com.transposesolutions.bankingcalculator.DEPOSIT";
    public final static String DISPLAY_MESSAGE5 = "com.transposesolutions.bankingcalculator.TERM";
    public final static String DISPLAY_MESSAGE6 = "com.transposesolutions.bankingcalculator.RATE";
    // declare TextView Widgets. Import the required class
    TextView result1, result2, savingsGoal, netReturn;
    Button mCompare, mAmortization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_module2);

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
        result1 = findViewById(R.id.result_1);
        result2 = findViewById(R.id.result_2);
        mCompare = findViewById(R.id.button_compare);
        mAmortization = findViewById(R.id.button_schedule);
        netReturn = findViewById(R.id.returnTextResult);
        savingsGoal = findViewById(R.id.investedTextResult);
        ProgressBar pieChart = findViewById(R.id.stats_progressbar);

        // get the data values from GoalModule1.java
        Bundle bun = getIntent().getExtras();
        String CONTRIBUTION = bun.getString(GoalModule1.GOAL_MESSAGE1);
        String ACTUAL_DEPOSIT = bun.getString(GoalModule1.GOAL_MESSAGE1A);
        String INTEREST = bun.getString(GoalModule1.GOAL_MESSAGE2);
        String GOAL = bun.getString(GoalModule1.GOAL_MESSAGE3);
        String INVESTMENT = bun.getString(GoalModule1.GOAL_MESSAGE4);
        String TIME = bun.getString(GoalModule1.GOAL_MESSAGE5);
        String ROI = bun.getString(GoalModule1.GOAL_MESSAGE6);
        // convert the string to double then format the string to print the value to TextView
        double _depositRequired = Double.parseDouble(CONTRIBUTION);
        result1.setText(String.format(Locale.getDefault(),"%,.2f",_depositRequired));
        double _interestEarned = Double.parseDouble(INTEREST);
        result2.setText(String.format(Locale.getDefault(),"%,.2f",_interestEarned));


        // onClick Handler called when the user taps the button - compare
        mCompare.setOnClickListener(v -> {
            // using Intent send the use to Module 4 -  Compare Activity
            Intent intent = new Intent(GoalModule2.this, GoalModule4.class);
            // using shared preferences to store the credentials */
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SHARED_PREFERENCE", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(DISPLAY_MESSAGE1, CONTRIBUTION);
            editor.putString(DISPLAY_MESSAGE1A, ACTUAL_DEPOSIT);
            editor.putString(DISPLAY_MESSAGE2, INTEREST);
            editor.putString(DISPLAY_MESSAGE3, GOAL);
            editor.putString(DISPLAY_MESSAGE4, INVESTMENT);
            editor.putString(DISPLAY_MESSAGE5, TIME);
            editor.putString(DISPLAY_MESSAGE6, ROI);
            /* Commits the changes and adds them to the file */
            editor.apply();
            startActivity(intent);
        });

        // onClick Handler called when the user taps the button - Amortization
        mAmortization.setOnClickListener(v -> {
            // The Intent constructor takes two parameters, a Context parameter and a subclass of Context the activity to start
            Intent intent = new Intent(GoalModule2.this, GoalModule3.class);
            //Android Bundle object and putExtra() Method is used to pass data between activities.
            Bundle bundle2 = new Bundle();
            // pass data values from one activity to another
            bundle2.putString(GOAL_MESSAGE7, GOAL);
            bundle2.putString(GOAL_MESSAGE8, INVESTMENT);
            bundle2.putString(GOAL_MESSAGE9, TIME);
            bundle2.putString(GOAL_MESSAGE10, ROI);
            intent.putExtras(bundle2);
            startActivity(intent);
        });

        // Initiate and Populate Pie Chart
        double mMonthlyDeposit = Double.parseDouble(CONTRIBUTION) ;
        double mInitialDeposit =  Double.parseDouble(INVESTMENT) ;
        double mTerm = Double.parseDouble(TIME) ;
        double mTotalDeposit = mMonthlyDeposit* mTerm + mInitialDeposit;
        double mInterest = Double.parseDouble(INTEREST);
        double mReturnPercentage = (mInterest /(mTotalDeposit+mInterest))*100;
        double mDepositPercentage = (mTotalDeposit / (mTotalDeposit+ mInterest))*100;
        double chartData = mReturnPercentage / mDepositPercentage;
        int progress = (int) (chartData * 100);
        pieChart.setProgress(Integer.parseInt(String.valueOf(progress)));
        String deposit  =String.format(Locale.getDefault(),"%,.2f", mDepositPercentage)+"%";
        savingsGoal.setText(deposit);
        String returns =String.format(Locale.getDefault(),"%,.2f", mReturnPercentage)+"%";
        netReturn.setText(returns);

    }//end of OnCreate

    // onClick Handler called when the user taps the button - Share
    private void share() {
        Bundle bun = getIntent().getExtras();
        String CONTRIBUTION = bun.getString(GoalModule1.GOAL_MESSAGE1);
        String INTEREST = bun.getString(GoalModule1.GOAL_MESSAGE2);
        String GOAL = bun.getString(GoalModule1.GOAL_MESSAGE3);
        String INVESTMENT = bun.getString(GoalModule1.GOAL_MESSAGE4);
        String TIME = bun.getString(GoalModule1.GOAL_MESSAGE5);
        String ROI = bun.getString(GoalModule1.GOAL_MESSAGE6);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "To reach your savings goal, you need a estimated monthly deposit of :" + CONTRIBUTION + ",\n" + "Total Interest Earned:" + INTEREST + ",\n"
                + "\n" + "Above result is calculated based on your input:"
                + "\n"+ "Savings Goal:"+ GOAL + ",\n" + "Initial Deposit:" + INVESTMENT + ",\n"
                + "Time to Save (Months):" + TIME + ",\n" + "Annual Interest:" + ROI+ "%" + ".\n";
        String shareSub = "Your estimated contribution required to meet your savings goal is Calculated by Transpose Solutions: Android App - Banking Calculator";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
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