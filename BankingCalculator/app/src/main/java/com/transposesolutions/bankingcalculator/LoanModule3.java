package com.transposesolutions.bankingcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
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

import java.text.DateFormat;
import java.util.Calendar;

public class LoanModule3 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    // declare instance variables required for the navigation drawer
    private ActionBarDrawerToggle mToggle;
    private AdView adView;
    // declare variable - mInterstitialAd" - this to support method display Interstitial used outside the onCreate
    private InterstitialAd mInterstitialAd;
    // declare value "zero" to the following double variable
    double LoanInterestRate = 0;
    double InstallmentNumber;
    short AmortizationTerm = 0;
    double OpenBalance;
    double CurrentBalance;
    double MonthlyPrincipal = 0;
    double MonthlyInterest = 0;
    int tableRows = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_module3);

        // Alert message
        AlertDialog alertDialog = new AlertDialog.Builder(LoanModule3.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("View on Landscape mode for better compatibility");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();

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

        //Get  the table layout
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        //Make the columns
        String[] columns = new String[6];
        columns[0] = "Payment#";
        columns[1] = "Date";
        columns[2] = "Payment";
        columns[3] = "Interest";
        columns[4] = "Principal";
        columns[5] = "Balance";
        this.addTableRow(tableLayout, columns);

        // get the text from Result Activity
        Bundle bundle = getIntent().getExtras();
        String mLoan_Amount = bundle.getString(LoanModule2.AMORTIZATION_MESSAGE1);
        String mTerm = bundle.getString(LoanModule2.AMORTIZATION_MESSAGE2);
        String mRate = bundle.getString(LoanModule2.AMORTIZATION_MESSAGE3);

        // get user input - Loan Amount as a String value
        double mLoanAmount = Float.parseFloat(mLoan_Amount);
        // get user input - Loan Term as a String value
        double mTermMonths = Float.parseFloat(mTerm);
        // get user input - Loan Interest Rate as a String value
        double mLoanInterest = Float.parseFloat(mRate);

        // Steps to calculate - Monthly Payment / EMI
        double mInterestRate = (mLoanInterest / 100) / 12;
        int mPaymentPeriods = (int) mTermMonths;
        double mPart1 = (mLoanAmount * mInterestRate);
        double mNumerator = (Math.pow((1 + mInterestRate), mPaymentPeriods));
        double mDenominator = (mNumerator - 1);
        double mPart2 = mPart1 * mNumerator;
        double MonthlyPayment = mPart2 / mDenominator;
        // MonthlyPayment = Math.round(MonthlyPayment);

        AmortizationTerm = (short) mPaymentPeriods;
        CurrentBalance = mLoanAmount;
        LoanInterestRate = mLoanInterest;
        // Loop for amortization term (number of monthly payments)
        Calendar calendar = Calendar.getInstance();
        for (int j = 0; j < AmortizationTerm; j++) {
            // Calculate monthly cycle
            InstallmentNumber = j + 1;
            OpenBalance = mLoanAmount;
            //OpenBalance += CurrentBalance - MonthlyPrincipal;
            MonthlyInterest = (CurrentBalance * LoanInterestRate / 12) / 100;
            MonthlyPrincipal = MonthlyPayment - MonthlyInterest;
            CurrentBalance = CurrentBalance - MonthlyPrincipal;

            if (j == AmortizationTerm - 1 && CurrentBalance != MonthlyPayment) {
                // Adjust the last payment to make sure the final balance is 0
                MonthlyPayment += CurrentBalance;
                CurrentBalance = 0;
            }
            //Insert the locations.
            String[] _loanAmortization = new String[6];
            //Date Column
            calendar.add(Calendar.MONTH, 1);
            String currentDate = DateFormat.getDateInstance(DateFormat.MONTH_FIELD).format(calendar.getTime());
            // Monthly Payment Column
            double _Payment = Math.round(MonthlyPayment * 100.0)/100.0;
            System.out.println(_Payment);
            //Monthly Interest Column
            double _Interest = Math.round(MonthlyInterest * 100.0)/100.0;
            System.out.println(_Interest);
            // Monthly Principal Column
            double _Principal = Math.round(MonthlyPrincipal * 100.0)/100.0;
            System.out.println(_Principal);
            // Monthly outstanding balance column
            double _Balance = Math.round(CurrentBalance *100.0)/100.0;
            System.out.println(_Balance);

            _loanAmortization[0] = String.valueOf(j + 1);
            _loanAmortization[1] = (currentDate);
            _loanAmortization[2] = String.valueOf(_Payment);
            _loanAmortization[3] = String.valueOf(_Interest);
            _loanAmortization[4] = String.valueOf(_Principal);
            _loanAmortization[5] = String.valueOf(_Balance);
            addTableRow(tableLayout, _loanAmortization);

        }
        // end of Amortization loop
    }
    private void addTableRow(TableLayout tableLayout, String[] text) {
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        // Adding border to the Table
        tableRow.setBackgroundResource(R.drawable.row_border);
        //Add padding to the table
        tableRow.setPadding(5,15,5,15);
        // code to set alternative colors to the table rows
        if (tableRows % 2 == 1)
            tableRow.setBackgroundColor(tableRow.getContext().getResources().getColor(R.color.light_green));
        for (String s : text) {
            TextView column = new TextView(this);
            column.setId(tableRows);
            column.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            column.setTextColor(Color.BLACK);
            column.setText(s);
            column.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
            column.setGravity(Gravity.CENTER);
            column.setWidth(50);
            column.setPadding(5, 5, 5, 5);
            tableRow.addView(column);
        }

        //tableRow.setLayoutParams();
        tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        tableRows++;
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
            mInterstitialAd.show(LoanModule3.this);
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
