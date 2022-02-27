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

import java.text.MessageFormat;
import java.util.Locale;

public class LoanModule2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    // declare instance variables required for the navigation drawer
    private ActionBarDrawerToggle mToggle;
    private AdView adView;
    TextView result1, result2, result3, result4, result5, result6;
    String mLoan, mTerm, mRate;
    Button mCompare,mAmortization;
    // declare package prefix , AMORTIZATION_MESSAGE constant, key values to pass data to Loan Module 3 - Amortization Activity
    public final static String AMORTIZATION_MESSAGE1 = "com.transposesolutions.bankingcalculator.mLoan_Amount";
    public final static String AMORTIZATION_MESSAGE2 = "com.transposesolutions.bankingcalculator.mLoan_Term";
    public final static String AMORTIZATION_MESSAGE3 = "com.transposesolutions.bankingcalculator.mLoan_Rate";
    // declare package prefix , LOAN_RESULT constant, key values to pass data to Loan Module 5 - Compare Activity
    public final static String LOAN_RESULT1 = "com.transposesolutions.bankingcalculator.mPayment";
    public final static String LOAN_RESULT2 = "com.transposesolutions.bankingcalculator.mPrincipal";
    public final static String LOAN_RESULT3 = "com.transposesolutions.bankingcalculator.mInterest";
    public final static String LOAN_RESULT4 = "com.transposesolutions.bankingcalculator.mPayments";
    public final static String LOAN_RESULT5 = "com.transposesolutions.bankingcalculator.mLoan";
    public final static String LOAN_RESULT6 = "com.transposesolutions.bankingcalculator.mTerm";
    public final static String LOAN_RESULT7 = "com.transposesolutions.bankingcalculator.mRate";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_module2);

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
        result1 = findViewById(R.id.result_1);
        result2 = findViewById(R.id.result_2);
        result3 = findViewById(R.id.result_3);
        result4 = findViewById(R.id.result_4);
        result5 = findViewById(R.id.result_5);
        result6 = findViewById(R.id.result_6);
        mCompare = findViewById(R.id.button_compare);
        mAmortization = findViewById(R.id.button_schedule);

        // get the text from card Activity
        Bundle bundle = getIntent().getExtras();
        String mPayment = bundle.getString(LoanModule1.LOAN_MESSAGE1);
        double _Payment = Double.parseDouble(mPayment);
        System.out.println(_Payment);
        String _MonthlyPayment = String.format(Locale.getDefault(),"%,.2f", _Payment);
        String mPrincipal = bundle.getString(LoanModule1.LOAN_MESSAGE2);
        double _Principal = Double.parseDouble(mPrincipal);
        System.out.println(_Principal);
        String _LoanPrincipal = String.format(Locale.getDefault(),"%,.2f", _Principal);
        String mInterest = bundle.getString(LoanModule1.LOAN_MESSAGE3);
        double _Interest = Double.parseDouble(mInterest);
        System.out.println(_Interest);
        String _LoanInterest = String.format(Locale.getDefault(),"%,.2f", _Interest);
        String mPayments = bundle.getString(LoanModule1.LOAN_MESSAGE4);
        double _LoanPayment = Double.parseDouble(mPayments);
        System.out.println(_LoanPayment);
        String _TotalPayment = String.format(Locale.getDefault(),"%,.2f", _LoanPayment);
        mLoan = bundle.getString(LoanModule1.LOAN_MESSAGE5);
        mTerm = bundle.getString(LoanModule1.LOAN_MESSAGE6);
        mRate = bundle.getString(LoanModule1.LOAN_MESSAGE7);
        // use the text in a TextView
        result1.setText(_MonthlyPayment);
        result2.setText(_LoanPrincipal);
        result3.setText(_LoanInterest);
        result4.setText(_TotalPayment);

        // onClick Handler called when the user taps the button - compare
        mCompare.setOnClickListener(v -> {
            // using Intent send the use to Module 4 -  Compare Activity
            Intent intent = new Intent(LoanModule2.this, LoanModule4.class);
            // using shared preferences to store the credentials */
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("RESULT_PERSONAL_PREFERENCES", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(LOAN_RESULT1, mPayment);
            editor.putString(LOAN_RESULT2, mLoan);
            editor.putString(LOAN_RESULT3, mInterest);
            editor.putString(LOAN_RESULT4, mPayments);
            editor.putString(LOAN_RESULT5, mLoan);
            editor.putString(LOAN_RESULT6, mTerm);
            editor.putString(LOAN_RESULT7, mRate);
            /* Commits the changes and adds them to the file */
            editor.apply();
            startActivity(intent);
        });



        // onClick Handler called when the user taps the button - Amortization
        mAmortization.setOnClickListener(v -> {
            // The Intent constructor takes two parameters, a Context parameter and a subclass of Context the activity to start
            Intent intent = new Intent(LoanModule2.this, LoanModule3.class);
            //Android Bundle object and putExtra() Method is used to pass data between activities.
            Bundle bundle1 = new Bundle();
            // pass data values from one activity to another
            bundle1.putString(AMORTIZATION_MESSAGE1, mLoan);
            bundle1.putString(AMORTIZATION_MESSAGE2, mTerm);
            bundle1.putString(AMORTIZATION_MESSAGE3, mRate);
            intent.putExtras(bundle1);
            startActivity(intent);
        });



        // Populate the data to graph activity
        double percentage = _Principal + _Interest;
        double _loanPrincipal = Math.round((_Principal/percentage)*100);
        String printPrincipal = String.valueOf(_loanPrincipal);
        result5.setText(MessageFormat.format("{0}%", printPrincipal));
        double _loanInterest =  Math.round((_Interest/percentage)*100);
        String printInterest = String.valueOf(_loanInterest);
        result6.setText(MessageFormat.format("{0}%", printInterest));

        // Calculate the slice size and update the pie chart:
        ProgressBar pieChart = findViewById(R.id.stats_progressbar);
        double chartData =  _Interest /  _Principal;
        int progress = (int) (chartData * 100);
        pieChart.setProgress(progress);



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




    // onClick Handler called when the user taps the button - share
    private void share() {
        String EMI;
        EMI = result1.getText().toString();
        String Principal;
        Principal = result2.getText().toString();
        String Interest;
        Interest = result3.getText().toString();
        String Payments;
        Payments = result4.getText().toString();

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Your Estimated Loan Payment Details:" +  "\n" +
                "Your Monthly Payment:" + EMI + ",\n" + "Your Total Principal Payment:" + Principal + ",\n" + "Your Total Interest Payment:" + Interest + ",\n" +"Your Total Loan Payments:"+ Payments + ",\n"
                +  "\n"+ "Above result is calculated based on your input:"
                +  "\n"+"Your Loan Amount:" + mLoan + ",\n" + "Your Loan Term:" + mTerm + "Months" + ",\n" + "Your Rate of Interest:" + mRate + "%" +  ".\n";
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