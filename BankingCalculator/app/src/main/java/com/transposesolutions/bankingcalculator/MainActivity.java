package com.transposesolutions.bankingcalculator;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PurchasesUpdatedListener, AcknowledgePurchaseResponseListener {

    // declare instance variables required for the navigation drawer
    private ActionBarDrawerToggle mToggle;
    private AdView adView;
    private BillingClient billingClient;
    private final List<String> skuList = new ArrayList<>();
    private final String productKey = "banking_1010";
    ImageView depositCalculator, savingsCalculator, goalCalculator, investmentCalculator, loanCalculator, currencyConverter;
    TextView deposit, savings, goal, investment, loan, currency;
   Boolean adsVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initiate Mobile Ads SDK
        MobileAds.initialize(this, initializationStatus -> {
        });

        // Bind the XML views to Java code Elements
        //Define UI Elements
        depositCalculator = findViewById(R.id.button_CD);
        savingsCalculator = findViewById(R.id.button_savings);
        goalCalculator = findViewById(R.id.button_goal);
        investmentCalculator = findViewById(R.id.button_return);
        loanCalculator = findViewById(R.id.button_loan);
        currencyConverter = findViewById(R.id.button_currency);
        deposit = findViewById(R.id.label_deposit);
        savings = findViewById(R.id.label_savings);
        goal = findViewById(R.id.label_goal);
        investment = findViewById(R.id.label_return);
        loan = findViewById(R.id.label_loan);
        currency = findViewById(R.id.label_currency);

        // Add Sku
        skuList.add(productKey);

        // Obtain the boolean value from ValidateStoreKey class using getPurchaseKey method.
        // If the value is false - Ads will be enabled and if true (Paid User) Ads will be removed
        Boolean validate = new ValidateStoreKey(this).getPurchaseKey(this, "myPref", productKey);
        if (validate) {
            ScrollView scrollView = findViewById(R.id.scroll);
            DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) scrollView.getLayoutParams();
            // Set/Reset Margin for the Scroll View
            layoutParams.topMargin = 10;
            scrollView.setLayoutParams(layoutParams);
            Toast.makeText(this, "you are a premium user", Toast.LENGTH_SHORT).show();
        } else {
            // Initialize Google Play Billing Client
            setupBillingClient();
            // Load an ad into the AdMob banner view.
            FrameLayout adContainerView = findViewById(R.id.ad_view_container);
            // Step 1 - Create an AdView and set the ad unit ID on it.
            adView = new AdView(this);
            adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
            adContainerView.addView(adView);
            loadBanner();
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


        // When user taps the Text - Deposit, user will be navigated to DepositModule1.java
        depositCalculator.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, DepositModule1.class)));
        deposit.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, DepositModule1.class)));
        // When user taps the Text - Savings, user will be navigated to SavingsModule1.java
        savingsCalculator.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SavingsModule1.class)));
        savings.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SavingsModule1.class)));
        // When user taps the Text - Loan, user will be navigated to LoanModule1.java
        loanCalculator.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LoanModule1.class)));
        loan.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LoanModule1.class)));
        // When user taps the Text - Savings Goal, user will be navigated to GoalModule1.java
        goalCalculator.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, GoalModule1.class)));
        goal.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, GoalModule1.class)));
        // When user taps the Text - Investment, user will be navigated to ReturnModule1.java
        investmentCalculator.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ReturnModule1.class)));
        investment.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ReturnModule1.class)));
        // When user taps the Text - Currency, user will be navigated to Currency.java
        currencyConverter.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Currency.class)));
        currency.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Currency.class)));

    }

    // end of onCreate

    // Initialize Google Play Billing Client
    private void setupBillingClient() {
        billingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is setup successfully
                    //loadAllSKUs();
                    appPurchase();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });
    }
    // override method to responsible for responding correctly to the items specified in the menu resource file.


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            // Obtain the boolean value from ValidateStoreKey class using getPurchaseKey method.
            // If the value is false - Ads will be enabled and if true (Paid User) Ads will be removed
            Boolean validate = new ValidateStoreKey(this).getPurchaseKey(this, "myPref", productKey);
            if (validate) {
        //  if (adsVisible) {
                Toast.makeText(MainActivity.this, "Tool bar set to invisible", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                getMenuInflater().inflate(R.menu.tool_menu, menu);
                return true;

            }
        }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
       // toolbar onOptionItemSelected
        if (item.getItemId() == R.id.purchase) {
                appPurchase();
                Toast.makeText(MainActivity.this, "purchase flow initiated", Toast.LENGTH_SHORT).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // Tool bar - Buy button on click event
    //Show products available to buy and launch the purchase flow
    private void appPurchase() {
        if (billingClient.isReady())
        {
            Toast.makeText(MainActivity.this, "billing client ready", Toast.LENGTH_SHORT).show();
            SkuDetailsParams params = SkuDetailsParams.newBuilder()
                    .setSkusList(skuList)
                    .setType(BillingClient.SkuType.INAPP)
                    .build();

            billingClient.querySkuDetailsAsync(params, (billingResult, skuDetailsList) -> {
                // Toast.makeText(Purchase.this, "inside query" + billingResult.getResponseCode(), Toast.LENGTH_SHORT).show();
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList!=null) {
                    // Retrieve the SKU details
                    for (Object skuDetailsObject : skuDetailsList) {
                        final SkuDetails skuDetails = (SkuDetails) skuDetailsObject;
                        //    Toast.makeText(Purchase.this, "" + skuDetails.getSku(), Toast.LENGTH_SHORT).show();
                        // Find the right SKU
                        if (skuDetails.getSku().equals(productKey)) {
                            // Initialize/ launch the purchase flow using the inbuilt UI
                            BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails).build();
                            billingClient.launchBillingFlow(MainActivity.this, billingFlowParams);
                        }
                    }
                }
            });
        }
        else
            Toast.makeText(MainActivity.this, "billing client not ready", Toast.LENGTH_SHORT).show();

    }
    // To handle possible response codes
    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<com.android.billingclient.api.Purchase> purchases) {
        int responseCode = billingResult.getResponseCode();
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && purchases != null) {
            for (com.android.billingclient.api.Purchase purchase : purchases) {
                handlePurchase(purchase);
            }
        }
        else if (responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
            Log.d(productKey, "User Canceled" + responseCode);
        }
        else if (responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            //If user is a paid customer update the status to the ValidateStoreKey Class
            new ValidateStoreKey(this).setPurchaseKey(this,"myPref",productKey, true );
        }
        else {
            // Handle any other error codes.
            Log.d(productKey, "Other code" + responseCode);
        }
    }
    // If user is a paid customer update the status to the ValidateStoreKey Class
    private void handlePurchase(com.android.billingclient.api.Purchase purchase) {
        // Grant entitlement to the user.
        applyPurchase(purchase);
        // Acknowledge the purchase if it hasn't already been acknowledged.
        if (!purchase.isAcknowledged()) {
            AcknowledgePurchaseParams acknowledgePurchaseParams =
                    AcknowledgePurchaseParams.newBuilder()
                            .setPurchaseToken(purchase.getPurchaseToken())
                            .build();
            billingClient.acknowledgePurchase(acknowledgePurchaseParams, MainActivity.this);
            //If user is a paid customer update the status to the ValidateStoreKey Class
            new ValidateStoreKey(this).setPurchaseKey(this,"myPref",productKey, true );
        }
    }
    // If purchase transaction is successful. App grants permission for the user to get a premium access.
    private void applyPurchase(com.android.billingclient.api.Purchase purchase) {
        // setting the boolean PurchaseKey to true if the app is purchased by the user
        if (purchase.getSkus().equals(productKey)) {
            new ValidateStoreKey(this).setPurchaseKey(this,"myPref",productKey, true );
            // Toast.makeText(this, "Purchase done. you are now a premium member.", Toast.LENGTH_SHORT).show();
        }else
        {
            new ValidateStoreKey(this).setPurchaseKey(this,"myPref",productKey, false );
        }
    }
    // Let the user know, the purchase is acknowledged and they are paid user now.
    @Override
    public void onAcknowledgePurchaseResponse(BillingResult billingResult) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
            Toast.makeText(this, "Purchase done. you are now a premium member." + billingResult.getResponseCode(), Toast.LENGTH_SHORT).show();
        }
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

    // Called when leaving the activity
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    // Called when returning to the activity
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    // Called before the activity is destroyed
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

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





    // called when the user tapped device back button override it with onBackPressed() to ask if they want exit the app or stay?
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure you want to quit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", (dialog, id) -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        });
        builder.setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }





}