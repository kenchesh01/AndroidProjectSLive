package com.transposesolutions.loantrackerproject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;
import com.transposesolutions.loantrackerproject.db.LoanTrackerDatabase;

public class LoanTrackerModule3 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    public final static String Loan_Name2 = "com.transposesolutions.loancalculator.mLoan_Name1";
    public final static String Loan_Term2 = "com.transposesolutions.loancalculator.mLoan_Term1";
    public final static String Loan_Installment_Amt2 = "com.transposesolutions.loancalculator.mLoan_Installment_Amt";
    public final static String Loan_InstallmentDate2 = "com.transposesolutions.loancalculator.mLoan_InstallmentDate1";
    public final static String  Loan_Amt2 = "com.transposesolutions.loancalculator.mLoan_Amt";
    public final static String  Loan_Rate2 = "com.transposesolutions.loancalculator.mLoan_Rate";
    public final static String  Loan_Day2 = "com.transposesolutions.loancalculator.mLoan_Day";
    public final static String  Loan_Month2 = "com.transposesolutions.loancalculator.mLoan_Month";
    public final static String  Loan_Year2 = "com.transposesolutions.loancalculator.mLoan_Year";

    private AdView adView;
    private ActionBarDrawerToggle mToggle;
    TextView input1, input2, input3, input4,input5,input6;
    String day2,month2,year2;
    public  static LoanTrackerDatabase myAppDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_tracker_module3);
        //  Database connection
        myAppDatabase=LoanTrackerDatabase.getDBinstance(this.getApplicationContext());

        // Load Navigation View.
        DrawerLayout mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        // Load an ad into the AdMob banner view.
        FrameLayout adContainerView = findViewById(R.id.ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
        adContainerView.addView(adView);
        loadBanner();

        // initiate the Edit text and date picker
        input1 = findViewById(R.id.user_input1);
        input2 = findViewById(R.id.user_input2);
        input3 = findViewById(R.id.user_input3);
        input4 = findViewById(R.id.user_input4);
        input5 = findViewById(R.id.user_input5);
        input6 =  findViewById(R.id.user_input6);



        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Loan_View", MODE_PRIVATE);
        String Name = sharedPreferences.getString(LoanTrackerModule1.Loan_Name1, "");
        String Loan_Amount = sharedPreferences.getString(LoanTrackerModule1.Loan_Amt, "0");
        String Term  = sharedPreferences.getString(LoanTrackerModule1.Loan_Term1, "");
        String Rate = sharedPreferences.getString(LoanTrackerModule1.Loan_Rate, "");
        String Monthly_Payment = sharedPreferences.getString(LoanTrackerModule1.Loan_Installment_Amt, "");
        String InstallmentDate = sharedPreferences.getString(LoanTrackerModule1.Loan_InstallmentDate1, "");
        String day = sharedPreferences.getString(LoanTrackerModule1.Loan_Day, "");
        String month = sharedPreferences.getString(LoanTrackerModule1.Loan_Month, "");
        String year = sharedPreferences.getString(LoanTrackerModule1.Loan_Year, "");

        day2 = day;
        month2 = month;
        year2 = year;

        input1.setText(Name);
        input2.setText(Loan_Amount);
        input3.setText(Term);
        input4.setText(Rate);
        input5.setText(Monthly_Payment);
        input6.setText(InstallmentDate);
        // fire base data



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

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_mortgage) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_auto) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_personal) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_card) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_rate) {
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
            String shareBody = "Check out this great Loan Calculator app. This app helps you to estimate loan interest and payments" +  "\n"+  "\n"
                    + "Google Play store:" +  "\n" +"https://play.google.com/store/apps/details?id=com.transposesolutions.loancalculator&hl=en" + "\n";
            String shareSub = "Check out this great Loan Calculator app from Transpose Solutions";
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

    public void Amortization(View view) {


        Intent intent = new Intent(this, LoanTrackerModule4.class);
        SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences("Loan_View2", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences2.edit();
        editor.putString(Loan_Name2, input1.getText().toString());
        editor.putString(Loan_Installment_Amt2, input5.getText().toString());
        editor.putString(Loan_Term2, input3.getText().toString());
        editor.putString(Loan_InstallmentDate2, input6.getText().toString());
        editor.putString(Loan_Amt2, input2.getText().toString());
        editor.putString(Loan_Rate2, input4.getText().toString());
        editor.putString(Loan_Day2, day2);
        editor.putString(Loan_Month2, month2);
        editor.putString(Loan_Year2, year2);
        editor.apply();
        startActivity(intent);
    }
}