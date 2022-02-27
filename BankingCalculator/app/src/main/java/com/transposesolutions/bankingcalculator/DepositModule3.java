package com.transposesolutions.bankingcalculator;

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

import java.util.Locale;

public class DepositModule3 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // declare instance variables required for the navigation drawer and Adaptive Banner
    private ActionBarDrawerToggle mToggle;
    private AdView adView;
    // declare variable - mInterstitialAd" - this to support method display Interstitial used outside the onCreate
    private InterstitialAd mInterstitialAd;
    // declare value "zero" to the following double variable
    short AmortizationTerm = 0;
    double OpenBalance = 0;
    double CurrentBalance = 0;
    double MonthlyInterest = 0;
    int tableRows = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_module3);

        // Alert message
        AlertDialog alertDialog = new AlertDialog.Builder(DepositModule3.this).create();
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

        // Load Navigation View, add toggle button to the drawer layout , setNavigationItemSelectedListener
        // and onOptionsItemSelected.
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

        // get the data values from DepositModule2.java
        Bundle bundle = getIntent().getExtras();
        String COMPOUNDING = bundle.getString(DepositModule2.DEPOSIT_MESSAGE15);
        // Switch statement to generate "Amortization Schedule" based on the string value "Compounding"
        switch(COMPOUNDING) {
            case "Daily":
                daily();
                break;
            case "Monthly":
                monthly();
                break;
            case "Quarterly":
                quarterly();
                break;
            case "Semi-Annually":
                semiAnnually();
                break;
            case "Annually":
                annually();
                break;
            default:
                break;
        }
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
            mInterstitialAd.show(DepositModule3.this);
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


    // Generate "Amortization Schedule" based on the "Compounding type = Daily"
    private void daily() {
        // get the data values from DepositModule2.java
        Bundle bundle = getIntent().getExtras();
        String DEPOSIT = bundle.getString(DepositModule2.DEPOSIT_MESSAGE12);
        String TERM = bundle.getString(DepositModule2.DEPOSIT_MESSAGE13);
        String ROI = bundle.getString(DepositModule2.DEPOSIT_MESSAGE14);
        // app logic to calculate the amortization schedule
        // Initial Amount of Deposit
        double _initial_Deposit = Float.parseFloat(DEPOSIT);
        // Number of Months
        double _term =Float.parseFloat(TERM);
        double mPaymentPeriods = _term/12;
        mPaymentPeriods = Math.ceil(mPaymentPeriods);
        // Annual Rate of Interest
        double interest_rate = Float.parseFloat(ROI);
        double _annual_interest = interest_rate / 100;
        // Assign numeric value "365" to variable _timesPerYear
        double _timesPerYear = 365;
        //Get  the table layout object in activity_deposit_module3.xml
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        //Generate the columns and column heading
        String[] columns = new String[4];
        columns[0] = "Year";
        columns[1] = "Open Balance";
        columns[2] = "Total Interest";
        columns[3] = "Closing Balance";
        //Add row to the table with above declared column heading
        this.addTableRow(tableLayout, columns);
        AmortizationTerm = (short) mPaymentPeriods;
        OpenBalance = _initial_Deposit;
        // Loop for amortization schedule (number of deposit months)
        for (int j = 0; j < AmortizationTerm; j++) {
            // Steps to calculate - Annual Savings & Interest
            double _step1 = 1+ (_annual_interest / _timesPerYear);
            double _step2 =  _timesPerYear * 1;
            double _maturity_Value = OpenBalance * Math.pow(_step1,_step2);
            _maturity_Value = Math.round(_maturity_Value*100.0)/100.0;
            double _interest_Earned = _maturity_Value - _initial_Deposit;
            _interest_Earned = Math.round(_interest_Earned * 100.0)/100.0;
            // Calculate monthly cycle
            //MonthlyInterest = (OpenBalance * _annual_interest)/_timesPerYear;
            CurrentBalance = _maturity_Value;
            //OpenBalance + MonthlyInterest;
            //Insert the value for the column heading - Month, Open Balance, Interest Earned and Closing Balance.
            String[] _Amortization = new String[4];
            //Print/Populate the Month value to the Array
            _Amortization[0] = String.valueOf(j + 1);
            //Print/Populate the Monthly Opening Balance Value to the Array
            double _openBalance = Math.round(OpenBalance * 100.0)/100.0;
            _Amortization[1] = String.format(Locale.getDefault(),"%,.2f",_openBalance);
            System.out.println(_openBalance);
            //Print/Populate the Monthly Interest Earned Value to the Array
            double _Interest =  Math.round(_interest_Earned* 100.0)/100.0;
            _Amortization[2] = String.format(Locale.getDefault(),"%,.2f",_Interest);
            System.out.println(_Interest);
            //Print/Populate the Monthly Closing Balance Value to the Array
            double _Balance =  Math.round(CurrentBalance * 100.0)/100.0;
            System.out.println(_Balance);
            _Amortization[3] = String.format(Locale.getDefault(),"%,.2f",_Balance);
            // Add Table row to table layout using method with values from the array "_Amortization"
            addTableRow(tableLayout, _Amortization);
            OpenBalance = _initial_Deposit + _interest_Earned;
            //CurrentBalance;
        }
        // end of Amortization loop
    }
    // Generate "Amortization Schedule" based on the "Compounding type = Monthly"
    private void monthly() {
        // get the data values from DepositModule2.java
        Bundle bundle = getIntent().getExtras();
        String DEPOSIT = bundle.getString(DepositModule2.DEPOSIT_MESSAGE12);
        String TERM = bundle.getString(DepositModule2.DEPOSIT_MESSAGE13);
        String ROI = bundle.getString(DepositModule2.DEPOSIT_MESSAGE14);
        // app logic to calculate the amortization schedule
        // Initial Amount of Deposit
        double _initial_Deposit = Float.parseFloat(DEPOSIT);
        // Number of Months
        double _term =Float.parseFloat(TERM);
        int mPaymentPeriods = (int)_term;
        // Annual Rate of Interest
        double interest_rate = Float.parseFloat(ROI);
        double _annual_interest = interest_rate / 100;
        // Assign numeric value "12" to variable _timesPerYear
        double _timesPerYear = 12;
        //Get  the table layout object in activity_deposit_module3.xml
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        //Generate the columns and column heading
        String[] columns = new String[4];
        columns[0] = "Month";
        columns[1] = "Open Balance";
        columns[2] = "Interest";
        columns[3] = "Closing Balance";
        //Add row to the table with above declared column heading
        this.addTableRow(tableLayout, columns);
        AmortizationTerm = (short) mPaymentPeriods;
        OpenBalance = _initial_Deposit;
        // Loop for amortization schedule (number of deposit months)
        for (int j = 0; j < AmortizationTerm; j++) {
            // Calculate monthly cycle
            MonthlyInterest = (OpenBalance * _annual_interest)/_timesPerYear;
            CurrentBalance = OpenBalance + MonthlyInterest;
            //Insert the value for the column heading - Month, Open Balance, Interest Earned and Closing Balance.
            String[] _Amortization = new String[4];
            //Print/Populate the Month value to the Array
            _Amortization[0] = String.valueOf(j + 1);
            //Print/Populate the Monthly Opening Balance Value to the Array
            double _openBalance = Math.round(OpenBalance * 100.0)/100.0;
            _Amortization[1] = String.format(Locale.getDefault(),"%,.2f",_openBalance);
            System.out.println(_openBalance);
            //Print/Populate the Monthly Interest Earned Value to the Array
            double _Interest =  Math.round(MonthlyInterest * 100.0)/100.0;
            _Amortization[2] = String.format(Locale.getDefault(),"%,.2f",_Interest);
            System.out.println(_Interest);
            //Print/Populate the Monthly Closing Balance Value to the Array
            double _Balance =  Math.round(CurrentBalance * 100.0)/100.0;
            System.out.println(_Balance);
            _Amortization[3] = String.format(Locale.getDefault(),"%,.2f",_Balance);
            // Add Table row to table layout using method with values from the array "_Amortization"
            addTableRow(tableLayout, _Amortization);
            OpenBalance = CurrentBalance;
        }
        // end of Amortization loop
    }
    // Generate "Amortization Schedule" based on the "Compounding type = quarterly"
    private void quarterly() {
        // get the data values from DepositModule2.java
        Bundle bundle = getIntent().getExtras();
        String DEPOSIT = bundle.getString(DepositModule2.DEPOSIT_MESSAGE12);
        String TERM = bundle.getString(DepositModule2.DEPOSIT_MESSAGE13);
        String ROI = bundle.getString(DepositModule2.DEPOSIT_MESSAGE14);
        // app logic to calculate the amortization schedule
        // Initial Amount of Deposit
        double _initial_Deposit = Float.parseFloat(DEPOSIT);
        // Number of Months
        double _term =Float.parseFloat(TERM);
        double mPaymentPeriods = _term/3;
        mPaymentPeriods = Math.ceil(mPaymentPeriods);
        // Annual Rate of Interest
        double interest_rate = Float.parseFloat(ROI);
        double _annual_interest = interest_rate / 100;
        // Assign numeric value "4" to variable _timesPerYear
        double _timesPerYear = 4;
        //Get  the table layout object in activity_deposit_module3.xml
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        //Generate the columns and column heading
        String[] columns = new String[4];
        columns[0] = "Quarter";
        columns[1] = "Open Balance";
        columns[2] = "Interest";
        columns[3] = "Closing Balance";
        //Add row to the table with above declared column heading
        this.addTableRow(tableLayout, columns);
        AmortizationTerm = (short) mPaymentPeriods;
        OpenBalance = _initial_Deposit;
        // Loop for amortization schedule (number of deposit months)
        for (int j = 0; j < AmortizationTerm; j++) {
            // Steps to calculate - Annual Savings & Interest
            MonthlyInterest = (OpenBalance * _annual_interest)/_timesPerYear;
            CurrentBalance = OpenBalance + MonthlyInterest;
            //Insert the value for the column heading - Month, Open Balance, Interest Earned and Closing Balance.
            String[] _Amortization = new String[4];
            //Print/Populate the Month value to the Array
            _Amortization[0] = String.valueOf(j + 1);
            //Print/Populate the Monthly Opening Balance Value to the Array
            double _openBalance = Math.round(OpenBalance * 100.0)/100.0;
            _Amortization[1] = String.format(Locale.getDefault(),"%,.2f",_openBalance);
            System.out.println(_openBalance);
            //Print/Populate the Monthly Interest Earned Value to the Array
            double _Interest =  Math.round(MonthlyInterest* 100.0)/100.0;
            _Amortization[2] = String.format(Locale.getDefault(),"%,.2f",_Interest);
            System.out.println(_Interest);
            //Print/Populate the Monthly Closing Balance Value to the Array
            double _Balance =  Math.round(CurrentBalance * 100.0)/100.0;
            System.out.println(_Balance);
            _Amortization[3] = String.format(Locale.getDefault(),"%,.2f",_Balance);
            // Add Table row to table layout using method with values from the array "_Amortization"
            addTableRow(tableLayout, _Amortization);
            OpenBalance = CurrentBalance ;
        }
        // end of Amortization loop
    }
    // Generate "Amortization Schedule" based on the "Compounding type = Semi-Annually"
    private void semiAnnually() {
        // get the data values from DepositModule2.java
        Bundle bundle = getIntent().getExtras();
        String DEPOSIT = bundle.getString(DepositModule2.DEPOSIT_MESSAGE12);
        String TERM = bundle.getString(DepositModule2.DEPOSIT_MESSAGE13);
        String ROI = bundle.getString(DepositModule2.DEPOSIT_MESSAGE14);
        // app logic to calculate the amortization schedule
        // Initial Amount of Deposit
        double _initial_Deposit = Float.parseFloat(DEPOSIT);
        // Number of Months
        double _term =Float.parseFloat(TERM);
        double mPaymentPeriods = _term/6;
        mPaymentPeriods = Math.ceil(mPaymentPeriods);
        // Annual Rate of Interest
        double interest_rate = Float.parseFloat(ROI);
        double _annual_interest = interest_rate / 100;
        // Assign numeric value "2" to variable _timesPerYear
        double _timesPerYear = 2;
        //Get  the table layout object in activity_deposit_module3.xml
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        //Generate the columns and column heading
        String[] columns = new String[4];
        columns[0] = "Half-year";
        columns[1] = "Open Balance";
        columns[2] = "Interest";
        columns[3] = "Closing Balance";
        //Add row to the table with above declared column heading
        this.addTableRow(tableLayout, columns);
        AmortizationTerm = (short) mPaymentPeriods;
        OpenBalance = _initial_Deposit;
        // Loop for amortization schedule (number of deposit months)
        for (int j = 0; j < AmortizationTerm; j++) {
            // Steps to calculate - Annual Savings & Interest
            MonthlyInterest = (OpenBalance * _annual_interest)/_timesPerYear;
            CurrentBalance = OpenBalance + MonthlyInterest;
            //Insert the value for the column heading - Month, Open Balance, Interest Earned and Closing Balance.
            String[] _Amortization = new String[4];
            //Print/Populate the Month value to the Array
            _Amortization[0] = String.valueOf(j + 1);
            //Print/Populate the Monthly Opening Balance Value to the Array
            double _openBalance = Math.round(OpenBalance * 100.0)/100.0;
            _Amortization[1] = String.format(Locale.getDefault(),"%,.2f",_openBalance);
            System.out.println(_openBalance);
            //Print/Populate the Monthly Interest Earned Value to the Array
            double _Interest =  Math.round(MonthlyInterest* 100.0)/100.0;
            _Amortization[2] = String.format(Locale.getDefault(),"%,.2f",_Interest);
            System.out.println(_Interest);
            //Print/Populate the Monthly Closing Balance Value to the Array
            double _Balance =  Math.round(CurrentBalance * 100.0)/100.0;
            System.out.println(_Balance);
            _Amortization[3] = String.format(Locale.getDefault(),"%,.2f",_Balance);
            // Add Table row to table layout using method with values from the array "_Amortization"
            addTableRow(tableLayout, _Amortization);
            OpenBalance = CurrentBalance ;
        }
        // end of Amortization loop
    }
    // Generate "Amortization Schedule" based on the "Compounding type = Annually"
    private void annually() {
        // get the data values from DepositModule2.java
        Bundle bundle = getIntent().getExtras();
        String DEPOSIT = bundle.getString(DepositModule2.DEPOSIT_MESSAGE12);
        String TERM = bundle.getString(DepositModule2.DEPOSIT_MESSAGE13);
        String ROI = bundle.getString(DepositModule2.DEPOSIT_MESSAGE14);
        // app logic to calculate the amortization schedule
        // Initial Amount of Deposit
        double _initial_Deposit = Float.parseFloat(DEPOSIT);
        // Number of Months
        double _term =Float.parseFloat(TERM);
        double mPaymentPeriods = _term/12;
        mPaymentPeriods = Math.ceil(mPaymentPeriods);
        // Annual Rate of Interest
        double interest_rate = Float.parseFloat(ROI);
        double _annual_interest = interest_rate / 100;
        // Assign numeric value "1" to variable _timesPerYear
        double _timesPerYear = 1;
        //Get  the table layout object in activity_deposit_module3.xml
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        //Generate the columns and column heading
        String[] columns = new String[4];
        columns[0] = "Year";
        columns[1] = "Open Balance";
        columns[2] = "Total Interest";
        columns[3] = "Closing Balance";
        //Add row to the table with above declared column heading
        this.addTableRow(tableLayout, columns);
        AmortizationTerm = (short) mPaymentPeriods;
        OpenBalance = _initial_Deposit;
        // Loop for amortization schedule (number of deposit months)
        for (int j = 0; j < AmortizationTerm; j++) {
            // Steps to calculate - Annual Savings & Interest
            double _step1 = 1+ (_annual_interest / _timesPerYear);
            double _step2 =  _timesPerYear * 1;
            double _maturity_Value = OpenBalance * Math.pow(_step1,_step2);
            _maturity_Value = Math.round(_maturity_Value*100.0)/100.0;
            double _interest_Earned = _maturity_Value - _initial_Deposit;
            _interest_Earned = Math.round(_interest_Earned * 100.0)/100.0;
            // Calculate monthly cycle
            CurrentBalance = _maturity_Value;
            //OpenBalance + MonthlyInterest;
            //Insert the value for the column heading - Month, Open Balance, Interest Earned and Closing Balance.
            String[] _Amortization = new String[4];
            //Print/Populate the Month value to the Array
            _Amortization[0] = String.valueOf(j + 1);
            //Print/Populate the Monthly Opening Balance Value to the Array
            double _openBalance = Math.round(OpenBalance * 100.0)/100.0;
            _Amortization[1] = String.format(Locale.getDefault(),"%,.2f",_openBalance);
            System.out.println(_openBalance);
            //Print/Populate the Monthly Interest Earned Value to the Array
            double _Interest =  Math.round(_interest_Earned* 100.0)/100.0;
            _Amortization[2] = String.format(Locale.getDefault(),"%,.2f",_Interest);
            System.out.println(_Interest);
            //Print/Populate the Monthly Closing Balance Value to the Array
            double _Balance =  Math.round(CurrentBalance * 100.0)/100.0;
            System.out.println(_Balance);
            _Amortization[3] = String.format(Locale.getDefault(),"%,.2f",_Balance);
            // Add Table row to table layout using method with values from the array "_Amortization"
            addTableRow(tableLayout, _Amortization);
            OpenBalance = _initial_Deposit + _interest_Earned;
            //CurrentBalance;
        }
        // end of Amortization loop
    }
    // Called to table row based on Array value "_Amortization"
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
            column.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size));
            column.setGravity(Gravity.CENTER);
            column.setWidth(80);
            column.setPadding(5,5,5,5);
            tableRow.addView(column);
        }
        tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        tableRows++;
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