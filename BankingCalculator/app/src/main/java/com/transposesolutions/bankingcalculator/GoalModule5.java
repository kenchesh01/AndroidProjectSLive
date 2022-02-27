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
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class GoalModule5 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    // declare instance variables required for the navigation drawer
    private ActionBarDrawerToggle mToggle;
    private AdView adView;
    // declare variable - mInterstitialAd" - this to support method display Interstitial used outside the onCreate
    private InterstitialAd mInterstitialAd;
    //Declare text view for column - result#1
    TextView Deposit, TotalDeposit, Interest, Savings, Goal, Initial, Term, Rate;
    //Declare text view for column - result#2
    TextView RequiredDeposit, TotalDeposits, EarnedInterest, TotalSavings, SavingsGoal, InitialDeposit, TimeToSave, AnnualInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_module5);

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
            // Prepare the Interstitial Ad
            AdRequest adRequest = new AdRequest.Builder().build();
            com.google.android.gms.ads.interstitial.InterstitialAd.load(this,getString(R.string.interstitial_id), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd;
                    Log.i("TAG", "onAdLoaded");
                    displayInterstitial();
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    // Handle the error
                    Log.i("TAG", loadAdError.getMessage());
                    mInterstitialAd = null;
                }
            });
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

        // TextView for the #Result1
        Goal = findViewById(R.id.savings_result1);
        Initial = findViewById(R.id.initial_deposit_result1);
        Term= findViewById(R.id.term_result1);
        Rate = findViewById(R.id.rate_result1);
        Deposit = findViewById(R.id.required_deposit_result1);
        TotalDeposit = findViewById(R.id.total_payments_result1);
        Interest = findViewById(R.id.total_interest_result1);
        Savings  = findViewById(R.id.total_savings_result1);
        //#Result1
        // get the data values from GoalModule2.java
        SharedPreferences sharedPreference = getApplicationContext().getSharedPreferences("SHARED_PREFERENCE", MODE_PRIVATE);
        String _monthlyDeposit = sharedPreference.getString(GoalModule2.DISPLAY_MESSAGE1,"");
        double _totalDeposit = Double.parseDouble(sharedPreference.getString(GoalModule2.DISPLAY_MESSAGE1A,""));
        String _interestEarned = sharedPreference.getString(GoalModule2.DISPLAY_MESSAGE2,"");
        double _savingsGoal = Double.parseDouble(sharedPreference.getString(GoalModule2.DISPLAY_MESSAGE3,""));
        double _initialDeposit = Double.parseDouble(sharedPreference.getString(GoalModule2.DISPLAY_MESSAGE4,""));
        String _timeToSave = sharedPreference.getString(GoalModule2.DISPLAY_MESSAGE5,"");
        double _annualInterest = Double.parseDouble(sharedPreference.getString(GoalModule2.DISPLAY_MESSAGE6,""));
        //setting the data values in the text view
        Deposit.setText(_monthlyDeposit);
        Interest.setText(_interestEarned);
        TotalDeposit.setText(String.format(Locale.getDefault(),"%,.2f",_totalDeposit));
        Savings.setText(String.format(Locale.getDefault(),"%,.2f",_savingsGoal));
        Goal.setText(String.format(Locale.getDefault(),"%,.2f",_savingsGoal));
        Initial.setText(String.format(Locale.getDefault(),"%,.2f",_initialDeposit));
        Term.setText(_timeToSave);
        Rate.setText(String.format(Locale.getDefault(),"%,.2f",_annualInterest));

        // TextView for the #Result2
        SavingsGoal = findViewById(R.id.savings_result2);
        InitialDeposit = findViewById(R.id.initial_deposit_result2);
        TimeToSave= findViewById(R.id.term_result2);
        AnnualInterest = findViewById(R.id.rate_result2);
        RequiredDeposit = findViewById(R.id.required_deposit_result2);
        TotalDeposits = findViewById(R.id.total_payments_result2);
        EarnedInterest = findViewById(R.id.total_interest_result2);
        TotalSavings  = findViewById(R.id.total_savings_result2);
        //#Result2
        // get the data values from GoalModule4.java
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("SHARED_PREFERENCES", MODE_PRIVATE);
        double _MONTHLY_DEPOSIT = Double.parseDouble(sharedPreferences1.getString(GoalModule4.GOAL_COMPARE1,""));
        double _INTEREST_EARNED = Double.parseDouble(sharedPreferences1.getString(GoalModule4.GOAL_COMPARE2,""));
        double _TOTAL_DEPOSITS = Double.parseDouble(sharedPreferences1.getString(GoalModule4.GOAL_COMPARE3,""));
        double _SAVINGS_GOAL = Double.parseDouble(sharedPreferences1.getString(GoalModule4.GOAL_COMPARE4,""));
        double  _INITIAL_DEPOSIT = Double.parseDouble(sharedPreferences1.getString(GoalModule4.GOAL_COMPARE5,""));
        String _TIME_TO_SAVE = (sharedPreferences1.getString(GoalModule4.GOAL_COMPARE6,""));
        double _ANNUAL_INTEREST = Double.parseDouble(sharedPreferences1.getString(GoalModule4.GOAL_COMPARE7,""));
        //setting the data values in the text view
        RequiredDeposit.setText(String.format(Locale.getDefault(),"%,.2f",_MONTHLY_DEPOSIT));
        EarnedInterest.setText(String.format(Locale.getDefault(),"%,.2f",_INTEREST_EARNED));
        TotalDeposits.setText(String.format(Locale.getDefault(),"%,.2f",_TOTAL_DEPOSITS));
        TotalSavings.setText(String.format(Locale.getDefault(),"%,.2f",_SAVINGS_GOAL));
        SavingsGoal.setText(String.format(Locale.getDefault(),"%,.2f",_SAVINGS_GOAL));
        InitialDeposit.setText(String.format(Locale.getDefault(),"%,.2f",_INITIAL_DEPOSIT));
        TimeToSave.setText(_TIME_TO_SAVE);
        AnnualInterest.setText(String.format(Locale.getDefault(),"%,.2f",_ANNUAL_INTEREST));
    }//end of onCreate

    // Called to display interstitial ad
    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when fullscreen content is dismissed.
                Log.d("TAG", "The ad was dismissed.");
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                // Called when fullscreen content failed to show.
                Log.d("TAG", "The ad failed to show.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when fullscreen content is shown.
                // Make sure to set your reference to null so you don't
                // show it a second time.
                mInterstitialAd = null;
                Log.d("TAG", "The ad was shown.");
            }
        });
        if (mInterstitialAd != null) {
            mInterstitialAd.show(GoalModule5.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
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

    // When user taps the share button, carry the values from screen and share
    private void share() {
        //Result#1 Data to share
        String result1_required_deposit;
        result1_required_deposit = Deposit.getText().toString();
        String result1_interest_earned;
        result1_interest_earned = Interest.getText().toString();
        String result1_total_deposit;
        result1_total_deposit = TotalDeposit.getText().toString();
        String result1_total_savings;
        result1_total_savings = Savings.getText().toString();
        String result1_Goal;
        result1_Goal = Goal.getText().toString();
        String result1_Investment;
        result1_Investment = Initial.getText().toString();
        String result1_Time;
        result1_Time = Term.getText().toString();
        String result1_Interest;
        result1_Interest = Rate.getText().toString();

        //Result#2 Data to share
        String result2_required_deposit;
        result2_required_deposit = RequiredDeposit.getText().toString();
        String result2_interest_earned;
        result2_interest_earned = EarnedInterest.getText().toString();
        String result2_total_deposit;
        result2_total_deposit = TotalDeposits.getText().toString();
        String result2_total_savings;
        result2_total_savings = TotalSavings.getText().toString();
        String result2_Goal;
        result2_Goal = SavingsGoal.getText().toString();
        String result2_Investment;
        result2_Investment = InitialDeposit.getText().toString();
        String result2_Time;
        result2_Time = TimeToSave.getText().toString();
        String result2_Interest;
        result2_Interest = AnnualInterest.getText().toString();

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Result# 1" + ",\n" +
                "To reach your savings goal, you need a estimated monthly deposit of :" + result1_required_deposit + ",\n" + "Total Interest Earned:" + result1_interest_earned +
                ",\n" + "Total Deposits:" + result1_total_deposit +  ",\n" + "Total Savings:" + result1_total_savings + ",\n" +
                "\n" + "Above result is calculated based on your input:" +
                "\n"+ "Savings Goal:"+ result1_Goal + ",\n" + "Initial Deposit:" + result1_Investment + ",\n"
                + "Time to Save (Months):" + result1_Time + ",\n" + "Annual Interest:" + result1_Interest + "%" +
                "\n" + "\n" + "Result# 2" + ",\n" +
                "To reach your savings goal, you need a estimated monthly deposit of :" + result2_required_deposit + ",\n" + "Total Interest Earned:" + result2_interest_earned +
                ",\n" + "Total Deposits:" + result2_total_deposit + ",\n" + "Total Savings:" + result2_total_savings + ",\n" +
                "\n" + "Above result is calculated based on your input:" +
                "\n" + "Savings Goal:" + result2_Goal + ",\n" + "Initial Deposit:" + result2_Investment + ",\n"
                + "Time to Save (Months):" + result2_Time + ",\n" + "Annual Interest:" + result2_Interest + "%" + ".\n";
        String shareSub = "Your estimated contribution required to meet your savings goal is Calculated by Transpose Solutions: Android App - Banking Calculator";
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