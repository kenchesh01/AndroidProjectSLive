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

public class LoanModule5 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    // declare instance variables required for the navigation drawer
    private ActionBarDrawerToggle mToggle;
    private AdView adView;
    // declare variable - mInterstitialAd" - this to support method display Interstitial used outside the onCreate
    private InterstitialAd mInterstitialAd;
    //Declare Text view for Result1
    TextView loan_amount1, term1, rate1, monthly_payment1, principal1, interest1,total_payments1;
    //Declare Text view for Result2
    TextView loan_amount2, term2, rate2, monthly_payment2, principal2, interest2,total_payments2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_module5);

        //Get the value from the ValidateStoreKey class using getValue method. if the result is true - remove the Ads from activity and if it is false display Ads
        //checking for the paid user or free user
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
            com.google.android.gms.ads.interstitial.InterstitialAd.load(this, getString(R.string.interstitial_id), adRequest, new InterstitialAdLoadCallback() {
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

        //#Result1
        // Call the TextView from the XML view for Result#1
        loan_amount1 = findViewById(R.id.loan_amount_result1);
        term1 = findViewById(R.id.loan_term_result1);
        rate1 = findViewById(R.id.rate_result1);
        monthly_payment1 = findViewById(R.id.monthly_payment_result1);
        principal1 = findViewById(R.id.loan_principal_result1);
        interest1 = findViewById(R.id.total_interest_result1);
        total_payments1 = findViewById(R.id.total_payments_result1);

        // get the data values from LoanModule2.java
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("RESULT_PERSONAL_PREFERENCES", MODE_PRIVATE);
        String payment1  = sharedPreferences.getString(LoanModule2.LOAN_RESULT1, "");
        String loan_principal1= sharedPreferences.getString(LoanModule2.LOAN_RESULT2, "");
        String loan_interest1  = sharedPreferences.getString(LoanModule2.LOAN_RESULT3, "");
        String loan_payments1  = sharedPreferences.getString(LoanModule2.LOAN_RESULT4, "");
        double loan1 = Double.parseDouble(sharedPreferences.getString(LoanModule2.LOAN_RESULT5, ""));
        String loan_term1 = sharedPreferences.getString(LoanModule2.LOAN_RESULT6, "");
        String loan_rate1  = sharedPreferences.getString(LoanModule2.LOAN_RESULT7, "");

        //setting the data values in the text view
        loan_amount1.setText(String.format(Locale.getDefault(),"%,.2f",loan1));
        term1.setText(loan_term1);
        rate1.setText(loan_rate1);
        monthly_payment1.setText(payment1);
        principal1.setText(loan_principal1);
        interest1.setText(loan_interest1);
        total_payments1.setText(loan_payments1);

        //#Result2
        // Call the TextView from the XML view for Result#2
        loan_amount2 = findViewById(R.id.loan_amount_result2);
        term2 = findViewById(R.id.loan_term_result2);
        rate2 = findViewById(R.id.rate_result2);
        monthly_payment2 = findViewById(R.id.monthly_payment_result2);
        principal2 = findViewById(R.id.loan_principal_result2);
        interest2 = findViewById(R.id.total_interest_result2);
        total_payments2 = findViewById(R.id.total_payments_result2);

        // get the data values from LoanModule4.java
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("COMPARE_PERSONAL_PREFERENCES", MODE_PRIVATE);
        double payment2  = Double.parseDouble(sharedPreferences1.getString(LoanModule4.LOAN_COMPARE1, ""));
        double loan_principal2= Double.parseDouble(sharedPreferences1.getString(LoanModule4.LOAN_COMPARE2, ""));
        double loan_interest2  = Double.parseDouble(sharedPreferences1.getString(LoanModule4.LOAN_COMPARE3, ""));
        double loan_payments2  = Double.parseDouble(sharedPreferences1.getString(LoanModule4.LOAN_COMPARE4, ""));
        double loan2 = Double.parseDouble(sharedPreferences1.getString(LoanModule4.LOAN_COMPARE5, ""));
        String loan_term2 = sharedPreferences1.getString(LoanModule4.LOAN_COMPARE6, "");
        String loan_rate2  = sharedPreferences1.getString(LoanModule4.LOAN_COMPARE7, "");

        //setting the data values in the text view
        loan_amount2 .setText(String.format(Locale.getDefault(),"%,.2f", loan2 ));
        term2 .setText(loan_term2 );
        rate2.setText(loan_rate2);
        monthly_payment2.setText(String.format(Locale.getDefault(),"%,.2f", payment2));
        principal2.setText(String.format(Locale.getDefault(),"%,.2f", loan_principal2));
        interest2.setText(String.format(Locale.getDefault(),"%,.2f", loan_interest2));
        total_payments2.setText(String.format(Locale.getDefault(),"%,.2f", loan_payments2));


    }

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
            mInterstitialAd.show(LoanModule5.this);
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
        String result1_loan_amount;
        result1_loan_amount = loan_amount1.getText().toString();
        String result1_loan_term;
        result1_loan_term = term1.getText().toString();
        String result1_rate;
        result1_rate = rate1.getText().toString();
        String result1_monthly_payment;
        result1_monthly_payment = monthly_payment1.getText().toString();
        String result1_loan_principal;
        result1_loan_principal = principal1.getText().toString();
        String result1_Total_interest;
        result1_Total_interest = interest1.getText().toString();
        String result1_total_loan;
        result1_total_loan = total_payments1.getText().toString();

        //Result#2 Data to share
        String result2_loan_amount;
        result2_loan_amount = loan_amount2.getText().toString();
        String result2_loan_term;
        result2_loan_term = term2.getText().toString();
        String result2_rate;
        result2_rate = rate2.getText().toString();
        String result2_monthly_payment;
        result2_monthly_payment = monthly_payment2.getText().toString();
        String result2_loan_principal;
        result2_loan_principal = principal2.getText().toString();
        String result2_Total_interest;
        result2_Total_interest = interest2.getText().toString();
        String result2_total_loan;
        result2_total_loan = total_payments2.getText().toString();
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Result# 1" + "\n" +
                "Your Estimated Loan Payment Details:" +  "\n" +
                "Your Total Monthly Payment:" + result1_monthly_payment + ",\n" + "Your Total Principal Payment:" + result1_loan_principal + ",\n" + "Your Total Interest Payment:" + result1_Total_interest
                + ",\n" +"Your Total Loan Payments:"+ result1_total_loan + ",\n"
                +  "\n"+ "Above result is calculated based on your input:"
                +  "\n"+
                "Your loan Amount:" + result1_loan_amount + ",\n" +  "Your Loan Term:" + result1_loan_term  + "Months" + ",\n" + "Your Rate of Interest:" + result1_rate+ "%" +
                "\n" +"\n" + "Result# 2" + "\n" +
                "Your Estimated Personal Loan Payment Details:" +  "\n" +
                "Your Total Monthly Payment:" + result2_monthly_payment + ",\n" + "Your Total Principal Payment:" + result2_loan_principal + ",\n" + "Your Total Interest Payment:" + result2_Total_interest
                + ",\n" +"Your Total Loan Payments:"+ result2_total_loan + ",\n"
                +  "\n"+ "Above result is calculated based on your input:"
                +  "\n"+
                "Your loan Amount:" + result2_loan_amount + ",\n" +  "Your Loan Term:" + result2_loan_term  + "Months" + ",\n" + "Your Rate of Interest:" + result2_rate+ "%" + ".\n";
        String shareSub = "Your Loan Information calculated by Transpose Solutions: Android App - Banking Calculator";
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