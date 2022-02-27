package com.transposesolutions.loantrackerproject;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;
import com.transposesolutions.loantrackerproject.db.LoanTrackerDatabase;
import com.transposesolutions.loantrackerproject.db.LoanTrackerEntity;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

public class LoanTrackerModule5 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public final static String Loan_Name5 = "com.transposesolutions.loancalculator.mLoan_Name1";
    public final static String Loan_Terms5 ="com.transposesolutions.loancalculator.mLoan_Term1";
    public final static String Loan_Installment_Amt5 = "com.transposesolutions.loancalculator.mLoan_Installment_Amt";
    public final static String  Loan_Amt5 = "com.transposesolutions.loancalculator.mLoan_Amt";
    public final static String  Loan_Rates5 = "com.transposesolutions.loancalculator.mLoan_Rate";
    public final static String  Loan_Day5 = "com.transposesolutions.loancalculator.mLoan_Day";
    public final static String  Loan_Month5 = "com.transposesolutions.loancalculator.mLoan_Month";
    public final static String  Loan_Year5 = "com.transposesolutions.loancalculator.mLoan_Year";
    public final static String Loan_Ids5 = "com.transposesolutions.loancalculator.mLoan_ID";
    EditText input1, input2, input3, input4,input6;
    TextView input7;
    DatePickerDialog datePickerDialog;
    Button submit;
    private AdView adView;
    private ActionBarDrawerToggle mToggle;
    LoanTrackerDatabase myAppDatabase;
    String referenceLoanID;

    String date,month,Year,day;
    //  create a textWatcher member
    private TextWatcher mTextWatcher = new TextWatcher() {
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
        submit = findViewById(R.id.button_calculate);
        String s1 = input1.getText().toString();
        String s2 = input2.getText().toString();
        String s3 = input3.getText().toString();
        String s4 = input4.getText().toString();
        String s6 = input6.getText().toString();
        submit.setEnabled(!s1.equals("") && !s2.equals("") && !s3.equals("") && !s4.equals("")  && !s6.equals(""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_tracker_module5);
//  Database connection
        myAppDatabase=LoanTrackerDatabase.getDBinstance(this.getApplicationContext());
        LoanTracker tracker=(LoanTracker) getApplicationContext();
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
        input6 = findViewById(R.id.user_input6);
        input7 = findViewById(R.id.user_inputid);
        submit = findViewById(R.id.button_calculate);


        // set listeners
        input1.addTextChangedListener(mTextWatcher);
        input2.addTextChangedListener(mTextWatcher);
        input3.addTextChangedListener(mTextWatcher);
        input4.addTextChangedListener(mTextWatcher);
        input6.addTextChangedListener(mTextWatcher);
        // run once to disable if empty
        checkFieldsForEmptyValues();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Loan_View_Edit", MODE_PRIVATE);
        String Name = sharedPreferences.getString(LoanTrackerModule1.Loan_Name1, "");
        String Loan_Amount = sharedPreferences.getString(LoanTrackerModule1.Loan_Amt, "0");
        String Term  = sharedPreferences.getString(LoanTrackerModule1.Loan_Term1, "");
        String Rate = sharedPreferences.getString(LoanTrackerModule1.Loan_Rate, "");
        String InstallmentDate = sharedPreferences.getString(LoanTrackerModule1.Loan_InstallmentDate1, "");
        referenceLoanID = sharedPreferences.getString(LoanTrackerModule1.Loan_id1, "");
     // set the user old details


        input7.setText(referenceLoanID);
        input1.setText(Name);

        input2.setText(Loan_Amount);
        input3.setText(Term);
        input4.setText(Rate);
        input6.setText(InstallmentDate);
        date = input6.getText().toString();
        // validation for minimum purchase price
        input1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    // Retrieve Loan name Value from the activity
                    String _loan_name_ = (input1.getText().toString());
                    if (_loan_name_.length() < 2) {
                        input1.setError("Please fill out this field! Enter a minimum value of 2 characters or greater value");
                    }} catch (NumberFormatException e) {
                    e.printStackTrace();
                }}});
        // validation for minimum purchase price
        input2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    // Retrieve Loan amount Value from the activity
                    float _loan_amount = Float.parseFloat(input2.getText().toString());
                    if (_loan_amount < 10000) {
                        input2.setError("Please fill out this field! Enter a minimum value of 10000 or greater value");
                    }} catch (NumberFormatException e) {
                    e.printStackTrace();
                }}});


        //Validation for minimum term limit
        input3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    // Retrieve Loan Term (Months) Value from the activity
                    float _term = Float.parseFloat(input3.getText().toString());
                    if (_term < 6) {
                        input3.setError("Please fill out this field! Enter a minimum value of 6 ");
                    }} catch (NumberFormatException e) {
                    e.printStackTrace();
                }}});
        //check for required minimum value for interest rate (
        input4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    // Retrieve Annual Rate of Interest (r) from the activity
                    float _interest_Rate = Float.parseFloat(input4.getText().toString());
                    if (_interest_Rate < 0.01) {
                        input4.setError("Please fill out this field! Enter a minimum value of 0.01 or greater");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }}});

        //check for required maximum value for interest rate (
        input4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    // Retrieve Annual Rate of Interest (r) from the activity
                    float _interest_Rate = Float.parseFloat(input4.getText().toString());
                    if (_interest_Rate > 50) {
                        input4.setError("Please fill out this field! interest cannot exceed 50");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }}});





        LoanTracker loanTracker=(LoanTracker)getApplicationContext();
     loanTracker.setEditDate(false);

        // perform click event on edit text
        input6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(LoanTrackerModule5.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                input6.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                date = input6.getText().toString();
                                day = String.valueOf(dayOfMonth);
                                month = String.valueOf(monthOfYear);
                                Year = String.valueOf(year);
                                loanTracker.setEditDate(true);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Account_Name_ = String.valueOf(input1.getText().toString());
                // get user input - purchase price as a String value
                float _loanAmount = Float.parseFloat(input2.getText().toString());
                // get user input - term as a String value
                float _term = Float.parseFloat(input3.getText().toString());
                // Convert Term (Year) to Months
                //double _termMonths =  12;
                // get user input - interest rate as a String value
                float _loanInterest = Float.parseFloat(input4.getText().toString());
                //check for required minimum value for purchase price
                if (_loanAmount < 10000) {
                    requiredCheck1();
                } else if (Account_Name_.length() < 2) {
                    requiredCheckName();
                }
                //check for required minimum value for mortgage term
                else if (_term < 6) {
                    requiredCheck3();
                }
                //check for required minimum value for annual interest rate
                else if (_loanInterest < 0.1) {
                    requiredCheck4();
                } else if (_loanInterest > 50) {
                    requiredCheck5();
                } else {


                    int UserId = Integer.parseInt(input7.getText().toString());
                    String Account_Name = String.valueOf(input1.getText().toString());
                    int Loan_Amount = Integer.parseInt(input2.getText().toString());
                    int Loan_Term = Integer.parseInt(input3.getText().toString());
                    int Loan_Rate = Integer.parseInt(input4.getText().toString());
                    //String Installment_Date = input6;
                    // check the loan name register or not

                    LoanTrackerEntity loanTrackerEntity = new LoanTrackerEntity();
                    loanTrackerEntity.setUid(UserId);
                    loanTrackerEntity.setLoanName(Account_Name);
                    loanTrackerEntity.setLoanAmount(Loan_Amount);
                    loanTrackerEntity.setLoanTerms(Loan_Term);
                    loanTrackerEntity.setLoanRate(Loan_Rate);
                    loanTrackerEntity.setLoanStartDate(date);
                    myAppDatabase.loanTrackerDao().updateLoan(loanTrackerEntity);
                    Toast.makeText(getApplicationContext(), "user updated Successfully", Toast.LENGTH_LONG).show();

                    SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("Loan_View_Edit5", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putString(Loan_Name5, input1.getText().toString());
                    editor.putString(Loan_Terms5, input3.getText().toString());
                    editor.putString(Loan_Amt5, input2.getText().toString());
                    editor.putString(Loan_Rates5, input4.getText().toString());
                    editor.putString(Loan_Ids5, referenceLoanID);
                    editor.putString(Loan_Day5, day);
                    editor.putString(Loan_Month5, month);
                    editor.putString(Loan_Year5, Year);
                    editor.apply();

                    Intent intent = new Intent(LoanTrackerModule5.this, AmortizationEntityUpdate.class);
                    startActivity(intent);

                }
            }
        });

    }
    private void requiredCheckName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoanTrackerModule5.this);
        builder.setMessage("Account name  must be at least 2 character or more")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // check for required minimum value for purchase price
    private void requiredCheck1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoanTrackerModule5.this);
        builder.setMessage("Purchase price must be at least 10000")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    // check for required minimum value for mortgage term
    private void requiredCheck3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoanTrackerModule5.this);
        builder.setMessage("Term should be at least 6")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //check for required minimum value for annual interest rate
    private void requiredCheck4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoanTrackerModule5.this);
        builder.setMessage("Annual Interest must be at least 0.01 or greater")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //check for required maximum value for annual interest rate
    private void requiredCheck5() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoanTrackerModule5.this);
        builder.setMessage(" Interest must be in the range of 0.01 to 50")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    }
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
}