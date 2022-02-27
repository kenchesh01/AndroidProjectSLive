package com.transposesolutions.loantrackerproject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.navigation.NavigationView;
import com.transposesolutions.loantrackerproject.db.AmortizationEntity;
import com.transposesolutions.loantrackerproject.db.LoanTrackerDatabase;
import com.transposesolutions.loantrackerproject.db.LoanTrackerEntity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class LoanTrackerModule4 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    // declare value "zero" to the following double variable
    private AdView adView;
    private ActionBarDrawerToggle mToggle;
    // declare variable - mInterstitialAd" - this to support method display Interstitial used outside the onCreate
    private InterstitialAd mInterstitialAd;

    int tableRows = 0;
    short AmortizationTerm = 0;
    double LoanInterestRate = 0;
    double MonthlyPrincipal = 0;
    double MonthlyInterest = 0;
     double OpenBalance;
    double CurrentBalance;

    double InstallmentNumber;

    int loanIdReference;
    TextView loanName;
    double payment;

    public  static LoanTrackerDatabase myAppDatabase;
    AmortizationEntity amortizationEntity =new AmortizationEntity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_tracker_module4);
        loanName = findViewById(R.id.activity_label);
       //Database
        //  Database connection
        myAppDatabase=LoanTrackerDatabase.getDBinstance(this.getApplicationContext());
//       LoanTracker loanTracker = (LoanTracker) getApplicationContext();
        // Alert message
        AlertDialog alertDialog = new AlertDialog.Builder(LoanTrackerModule4.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("View on Landscape mode for better compatibility");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
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
        // Load an ad into the AdMob banner view.
        FrameLayout adContainerView = findViewById(R.id.ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
        adContainerView.addView(adView);
        loadBanner();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // TODO: Your application init goes here.
                Intent mInHome = new Intent(LoanTrackerModule4.this, LoanTrackerModule1.class);
                startActivity(mInHome);
                finish();
            }
        }, 10000); //10 seconds wait

        // Prepare the Interstitial Ad
        // Prepare the Interstitial Ad
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, String.valueOf(R.string.interstitial_id),
                adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
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

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        //Generate the columns and column heading
        //Make the columns
        String[] columns = new String[6];
        columns[0] = "Payment#";
        columns[1] = "Date";
        columns[2] = "Payment";
        columns[3] = "Interest";
        columns[4] = "Principal";
        columns[5] = "Balance";
        this.addTableRow(tableLayout, columns);



    // Fetching the Data from the database

       List<LoanTrackerEntity> users = myAppDatabase.loanTrackerDao().getAllLoans();
        for (LoanTrackerEntity usr : users) {
            loanIdReference = usr.getUid();
        }
        amortizationEntity.setLoanId(loanIdReference);
//        amortizationEntity.setPayment(payment);
    SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("Loan_View", MODE_PRIVATE);
    String Name1 = sharedPreferences1.getString(LoanTrackerModule2.Loan_Name, "");
    loanName.setText(Name1);
    String mLoan_Amount  = sharedPreferences1.getString(LoanTrackerModule2.Loan_Amt, "0");
    String mTerm  = sharedPreferences1.getString(LoanTrackerModule2.Loan_Terms, "");
        String mRate  = sharedPreferences1.getString(LoanTrackerModule2.Loan_Rates, "");
    String day = sharedPreferences1.getString(LoanTrackerModule2.Loan_Day, "");
    String month = sharedPreferences1.getString(LoanTrackerModule2.Loan_Month, "");
    String year = sharedPreferences1.getString(LoanTrackerModule2.Loan_Year, "");



    // get user input - Loan Amount as a String value
    double mMortgageAmount = Float.parseFloat(mLoan_Amount);
    // get user input - Loan Term as a String value
    double mTermMonths = Float.parseFloat(mTerm);
    // get user input - Loan Interest Rate as a String value
    double mLoanInterest = Float.parseFloat(mRate );


    // Steps to calculate - Monthly Payment / EMI
    double mInterestRate = (mLoanInterest / 100) / 12;
    int mPaymentPeriods = (int) mTermMonths;
    double mPart1 = (mMortgageAmount * mInterestRate);
    double mNumirator = (Math.pow((1 + mInterestRate), mPaymentPeriods));
    double mDenomitor = (mNumirator - 1);
    double mPart2 = mPart1 * mNumirator;
    double MonthlyPayment = mPart2 / mDenomitor;


    AmortizationTerm = (short) mPaymentPeriods;
    CurrentBalance = mMortgageAmount;
    LoanInterestRate = mLoanInterest;

    // Loop for amortization term (number of monthly payments)
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, Integer.parseInt(year));
    calendar.set(Calendar.MONTH, Integer.parseInt(month));
    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
    for (int j = 0; j < AmortizationTerm; j++) {
        // Calculate monthly cycle
        InstallmentNumber = j + 1;
        OpenBalance = mMortgageAmount;
        //OpenBalance += CurrentBalance - MonthlyPrincipal;
        MonthlyInterest = ((CurrentBalance * LoanInterestRate  )/12)/100;
        //  MonthlyInterest = Math.round(MonthlyInterest);
        MonthlyPrincipal = MonthlyPayment - MonthlyInterest;
        //  MonthlyPrincipal = Math.round(MonthlyPrincipal);
        CurrentBalance = CurrentBalance - MonthlyPrincipal;
        // CurrentBalance = Math.round(CurrentBalance);
        //Payment = MonthlyPayment;

        if (j == AmortizationTerm - 1 && CurrentBalance != MonthlyPayment) {
            // Adjust the last payment to make sure the final balance is 0
            MonthlyPayment += CurrentBalance;
            CurrentBalance = 0;
        }
        //Insert the locations hardcoded (for now).

       payment = Math.round(MonthlyPayment * 100.0) / 100.0;
//        System.out.println(_Payment);
        amortizationEntity.setPayment(payment);
        double _Interest = Math.round(MonthlyInterest * 100.0) / 100.0;
//        System.out.println(_Interest);

        double _Principal = Math.round(MonthlyPrincipal * 100.0) / 100.0;
//        System.out.println(_Principal);

        double _Balance = Math.round(CurrentBalance * 100.0) / 100.0;
//        System.out.println(_Balance);

        //incrementing the month by 1
        calendar.add(Calendar.MONTH, 1);
        String currentDate = DateFormat.getDateInstance(DateFormat.MONTH_FIELD).format(calendar.getTime());
        String[] _DisplayData = new String[6];
        //Print/Populate the Month value to the Array
        _DisplayData[0] = String.valueOf((int)InstallmentNumber);
        amortizationEntity.setTermsId((int) InstallmentNumber);
        _DisplayData[1] = currentDate;
        amortizationEntity.setInstallmentDates(currentDate);
        _DisplayData[2] = String.valueOf(payment);
        _DisplayData[3] = String.valueOf(_Interest);
        amortizationEntity.setInterest(_Interest);
        _DisplayData[4] = String.valueOf(_Principal);
        amortizationEntity.setPrincipal(_Principal);
        _DisplayData[5] = String.valueOf(_Balance);
        amortizationEntity.setBalance(_Balance);
        myAppDatabase.loanTrackerDao().addAmortizationData(amortizationEntity);
        addTableRow(tableLayout, _DisplayData);
    }

}



    private void addTableRow(TableLayout tableLayout, String[] text){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            // Adding border to the Table
            tableRow.setBackgroundResource(R.drawable.row_border);
            //Add padding to the table
            tableRow.setPadding(5, 15, 5, 15);
            // code to set alternative colors to the table rows
            if (tableRows % 2 == 1)
                tableRow.setBackgroundColor(tableRow.getContext().getResources().getColor(R.color.colorTable));
            for (int i = 0; i < text.length; i++) {
                TextView column = new TextView(this);
                column.setId(tableRows);
                column.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                column.setTextColor(Color.BLACK);
                column.setText(text[i]);
                column.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
                column.setGravity(Gravity.CENTER);
                column.setWidth(60);
                column.setPadding(5, 5, 5, 5);
                tableRow.addView(column);
            }

            //tableRow.setLayoutParams();
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
            tableRows++;

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
    // Called to display interstitial ad
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
            public void onAdFailedToShowFullScreenContent( AdError adError) {
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
            mInterstitialAd.show(LoanTrackerModule4.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }

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
        }  else if (id == R.id.nav_mortgage) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mortgage_payment) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_rental) {
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
}

