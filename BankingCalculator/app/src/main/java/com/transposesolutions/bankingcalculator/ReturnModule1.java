package com.transposesolutions.bankingcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;

public class ReturnModule1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    // declare instance variables required for the navigation drawer
    private ActionBarDrawerToggle mToggle;
    private AdView adView;
    // declare package prefix , INVESTMENT_MESSAGE constant and key values
    public final static String INVESTMENT_MESSAGE1 = "com.transposesolutions.bankingcalculator.ROI";
    public final static String INVESTMENT_MESSAGE2 = "com.transposesolutions.bankingcalculator.ANNUALIZED_ROI";
    public final static String INVESTMENT_MESSAGE3 = "com.transposesolutions.bankingcalculator.RETURNS";
    public final static String INVESTMENT_MESSAGE4 = "com.transposesolutions.bankingcalculator.INVESTMENT";
    public final static String INVESTMENT_MESSAGE5 = "com.transposesolutions.bankingcalculator.TERM";
    public final static String INVESTMENT_MESSAGE6 = "com.transposesolutions.bankingcalculator.EXPECTED _RETURN";

    // declare EditText, and Spinner Widgets. Import the required class
    EditText input1, input2, input3, input4, input5;
    private TextView label3, label4;


    //  create a textWatcher member
    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };
    private void checkFieldsForEmptyValues() {
        Button button = findViewById(R.id.button_calculate);
        String s1 = input1.getText().toString();
        String s2 = input2.getText().toString();
        String s3 = input3.getText().toString();
        String s4 = input4.getText().toString();
        String s5 = input5.getText().toString();
        button.setEnabled(!s1.equals("") && !s2.equals("") && !s3.equals("") && !s4.equals("") && !s5.equals(""));
    }
boolean radioButton=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_module1);

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
        // Capture the layout's EditText and TextView Elements
        label3 = findViewById(R.id.label_3);
        label4 = findViewById(R.id.label_4);
        input1 = findViewById(R.id.user_input1);
        input2 = findViewById(R.id.user_input2);
        input3 = findViewById(R.id.user_input3);
        input4 = findViewById(R.id.user_input4);
        input5 = findViewById(R.id.user_input5);
        RadioButton optionSelected = findViewById(R.id.radio_yes);
        RadioButton optionSelected2 = findViewById(R.id.radio_no);
        final Button calculateButton = findViewById(R.id.button_calculate);
        Button activityReset = findViewById(R.id.reset);
        // OnClick listener to handle the event when the user taps the Button - Reset
        activityReset.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReturnModule1.class);
            startActivity(intent);
        });


        //Enable/make it visible - Total Income and Expense TextView and EditText
        optionSelected.setOnClickListener(v -> {
            label3.setVisibility(View.VISIBLE);
            input3.setVisibility(View.VISIBLE);
            label4.setVisibility(View.VISIBLE);
            input4.setVisibility(View.VISIBLE);
           radioButton=false;

        });

        //Disable/make it invisible - Total Income and Expense TextView and EditText
        optionSelected2.setOnClickListener(v -> {
            label3.setVisibility(View.GONE);
            input3.setVisibility(View.GONE);
            label4.setVisibility(View.GONE);
            input4.setVisibility(View.GONE);
            radioButton=true;

        });


        // set listeners
        input1.addTextChangedListener(mTextWatcher);
        input2.addTextChangedListener(mTextWatcher);
        input3.addTextChangedListener(mTextWatcher);
        input4.addTextChangedListener(mTextWatcher);
        input5.addTextChangedListener(mTextWatcher);
        // run once to disable if empty
        checkFieldsForEmptyValues();
        // Check for minimum and maximum required values and alert user with message to fix the required values
        // validation for minimum Investment Amount
        input1.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                // Retrieve mortgage purchase price Value from the activity
                float _investment = Float.parseFloat(input1.getText().toString());
                if (_investment < 100) {
                    input1.setError("Please fill out this field! Enter a minimum value of 100 or greater value");
                }} catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
        //validation for required Returns
        input2.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                input2.setError("Please fill out this field! Enter a value equal or greater than investment amount");

            }});
        //Validation for Total Income
        input3.setOnFocusChangeListener((v, hasFocus) -> {
                    try {
                        // Retrieve mortgage purchase price Value from the activity
                        float _income = Float.parseFloat(input3.getText().toString());
                        if (_income < 1) {
                            input3.setError("Please fill out this field! Enter a minimum value of 1 or greater value");
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                });

        //check for required minimum value for Total Expense (
        input4.setOnFocusChangeListener((v, hasFocus) -> {
            // Retrieve Total Expense Value from the activity
            try {
                // Retrieve mortgage purchase price Value from the activity
                float _expense = Float.parseFloat(input4.getText().toString());
                if (_expense < 1) {
                    input4.setError("Please fill out this field! Enter a minimum value of 1 or greater value");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });

        //check for required minimum value for investment length (
        input5.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                // Retrieve Investment Length value from the activity
                float _term = Float.parseFloat(input5.getText().toString());
                if (_term < 1) {
                    input5.setError("Please fill out this field! Enter a minimum value of 1");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }});


        //Click Event Handler to manage Button Calculate
        calculateButton.setOnClickListener(view -> {
            float _investment = Float.parseFloat(input1.getText().toString());
            float _returns = Float.parseFloat(input2.getText().toString());
            float _income = Float.parseFloat(input3.getText().toString());
            float _expense = Float.parseFloat(input4.getText().toString());
            float _term = Float.parseFloat(input5.getText().toString());
           if(!radioButton) {
                    if (_income < 1) {
                        requiredCheck3();             //check for required minimum value for _income
                      } else if (_expense < 1) {
                      requiredCheck4();              //check for required minimum value for _expense
                      } else if (_investment < 100) {
                      requiredCheck1();   //check for required value for returns
                      } else if(_returns < 1){
                      requiredCheck2();  //check for required investment length
                      } else if (_term<1) {
                     requiredCheck5();      //check for required minimum value for investment
                     }else{
                        update(); }
           } else{
               if (_investment < 100) {
                   requiredCheck1();   //check for required value for returns
               } else if(_returns < 1){
                   requiredCheck2();  //check for required investment length
               } else if (_term<1) {
                   requiredCheck5();      //check for required minimum value for investment
               } else {
                   // calculating for ROI & Net Return
                   update();
               }
           }
        });
    }//End of onCreate

    private void update() {
        float _investment = Float.parseFloat(input1.getText().toString());
        float _returns = Float.parseFloat(input2.getText().toString());
        float _income = Float.parseFloat(input3.getText().toString());
        float _expense = Float.parseFloat(input4.getText().toString());
        float _term = Float.parseFloat(input5.getText().toString());
        // Steps to calculate
        double _netGain = ((_returns - _investment)+_income -_expense);
        double _grossReturn = (_returns + _income ) - _expense; // return amount
        double _ROI = (_netGain) / _investment * 100;
        System.out.println(_ROI);

        //calculation for Annualized ROI
        double _cROI_Annualized1 = Math.pow((_grossReturn / _investment), (1 / _term)) - 1;
        double _annualizedROI = _cROI_Annualized1 * 100;
        System.out.println(_annualizedROI);

        //Method to export the above calculated values to pass it to Result Page
        Intent intent = new Intent(ReturnModule1.this, ReturnModule2.class);
        Bundle bundle = new Bundle();
        String mTerm = input5.getText().toString();
        bundle.putString(INVESTMENT_MESSAGE1, String.valueOf(_ROI));
        bundle.putString(INVESTMENT_MESSAGE2, String.valueOf(_annualizedROI));
        bundle.putString(INVESTMENT_MESSAGE3, String.valueOf(_netGain));
        bundle.putString(INVESTMENT_MESSAGE4, String.valueOf(_investment));
        bundle.putString(INVESTMENT_MESSAGE5, mTerm);
        bundle.putString(INVESTMENT_MESSAGE6, String.valueOf(_returns));
        intent.putExtras(bundle);
        startActivity(intent);
    }


    // check for required minimum required value - Investment
    private void requiredCheck1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReturnModule1.this);
        builder.setMessage("Investment should be greater than 100")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //check for required value - Returns
    private void requiredCheck2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReturnModule1.this);
        builder.setMessage("Please fill out this field! Enter a value equal or greater than investment amount")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //check for required value - Total Income
    private void requiredCheck3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReturnModule1.this);
        builder.setMessage("Please fill out this field (Total Income)! Enter a minimum value of 1 or greater value")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //check for required value - Total Expense
    private void requiredCheck4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReturnModule1.this);
        builder.setMessage("Please fill out this field(Total Expense)! Enter a minimum value of 1 or greater value")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //check for required value - Investment Length
    private void requiredCheck5() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReturnModule1.this);
        builder.setMessage("Please fill out this field! Enter a minimum value of 1")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> {
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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