package com.transposesolutions.loantrackerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.transposesolutions.loantrackerproject.db.AmortizationEntity;
import com.transposesolutions.loantrackerproject.db.LoanTrackerDatabase;
import com.transposesolutions.loantrackerproject.db.LoanTrackerEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class View extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    // declare value "zero" to the following double variable
    private AdView adView;
    private ActionBarDrawerToggle mToggle;
    // declare variable - mInterstitialAd" - this to support method display Interstitial used outside the onCreate
    private InterstitialAd mInterstitialAd;
    int tableRows = 0;
    TextView currentDate,currentBalance,currentTerms, loanName;
    // current date and balance required data types
    int refId, termsID;
    String installmentDate, currentDates;
    double loanAmount;
    public  static LoanTrackerDatabase myAppDatabase;
   String referenceLoanID,LoanName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        currentDate =findViewById(R.id.currentDate);
        currentBalance = findViewById(R.id.currentBalance);
        currentTerms =findViewById(R.id.currentTerms);
        loanName = findViewById(R.id.activity_label);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Loan_View", MODE_PRIVATE);
        referenceLoanID= sharedPreferences.getString(LoanTrackerModule1.Loan_ids, "");
        LoanName= sharedPreferences.getString(LoanTrackerModule1.Loan_Name1, "");
        installmentDate= sharedPreferences.getString(LoanTrackerModule1.Loan_InstallmentDate1, "");

        refId = Integer.parseInt(referenceLoanID);
        loanName.setText(LoanName);
        // current date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        currentDates = simpleDateFormat.format(c);
        currentDate.setText(currentDates);

        //  Database connection
        myAppDatabase = LoanTrackerDatabase.getDBinstance(this.getApplicationContext());
        List<AmortizationEntity> termsCount = myAppDatabase.loanTrackerDao().getAllAmortizationList(refId);
        int totalTerms=termsCount.size();

//        List<LoanTrackerEntity> user = myAppDatabase.loanTrackerDao().getAllLoans();
//        for (LoanTrackerEntity loanTrackers : user) {
//            installmentDate = loanTrackers.getLoanStartDate();
//        }
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = simpleDateFormat.parse(currentDates);
            d2 = simpleDateFormat.parse(installmentDate);
            long diff = d1.getTime() - d2.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000); // count the days
            termsID = (int) (diffDays / 30); //   find the ids ex: diffDays(60)/30=2
            totalTerms = totalTerms - termsID;

            double getCurrentBalance = myAppDatabase.loanTrackerDao().getBalance(termsID,refId);
            currentBalance.setText(String.valueOf(getCurrentBalance));
            currentTerms.setText(String.valueOf(totalTerms));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Alert message
        AlertDialog alertDialog = new AlertDialog.Builder(View.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("View on Landscape mode for better compatibility");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
        // Load Navigation View.
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
        // Load an ad into the AdMob banner view.
        FrameLayout adContainerView = findViewById(R.id.ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
        adContainerView.addView(adView);
        loadBanner();
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
        // Populate data to the table rows from database
        List<AmortizationEntity> users = myAppDatabase.loanTrackerDao().getAllAmortizationList(refId);
        int size = users.size();
        System.out.println("Size "+String.valueOf(size));
        for (AmortizationEntity tracker : users) {
            String[] _DisplayData = new String[6];
            // data for column-1
            _DisplayData[0] = String.valueOf(tracker.getTermsId());

            // data for column-2
            _DisplayData[1] = String.valueOf(tracker.getInstallmentDates());

            // data for column-3
            _DisplayData[2] = String.valueOf(tracker.getPayment());

            // data for column-4
            _DisplayData[3] = String.valueOf(tracker.getInterest());

            // data for column-5
            _DisplayData[4] = String.valueOf(tracker.getPrincipal());
            // data for column-6
            _DisplayData[5] = String.valueOf(tracker.getBalance());

            addTableRow(tableLayout, _DisplayData);
        }
    }

    // Called to table row based on Array value "_Goal Result"
    private void addTableRow(TableLayout tableLayout, String[] text) {
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        // Adding border to the Table
        tableRow.setBackgroundResource(R.drawable.row_border);
        //Add padding to the table
        tableRow.setPadding(5,15,5,15);
        // code to set alternative colors to the table rows
        if (tableRows % 2 == 1)
            tableRow.setBackgroundColor(tableRow.getContext().getResources().getColor(R.color.colorTable));
        for (int i = 0; i < text.length; i++) {
            TextView column = new TextView(this);
            column.setId(tableRows);
            column.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            column.setTextColor(Color.BLACK);
            column.setText(text[i]);
            column.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size));
            column.setGravity(Gravity.CENTER);
            column.setWidth(60);
            column.setPadding(5,5,5,5);
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
            mInterstitialAd.show(View.this);
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