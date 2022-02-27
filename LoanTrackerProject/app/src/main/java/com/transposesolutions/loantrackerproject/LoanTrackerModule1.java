package com.transposesolutions.loantrackerproject;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableRow;
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
import com.transposesolutions.loantrackerproject.db.AmortizationEntity;
import com.transposesolutions.loantrackerproject.db.LoanTrackerDatabase;
import com.transposesolutions.loantrackerproject.db.LoanTrackerEntity;

import java.util.List;

public class LoanTrackerModule1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public final static String Loan_id1 = "com.transposesolutions.loancalculator.mId1";
    public final static String Loan_Name1 = "com.transposesolutions.loancalculator.mLoan_Name1";
    public final static String Loan_Amount1 = "com.transposesolutions.loancalculator.mLoan_LoanAmount1";
    public final static String Loan_Term1 = "com.transposesolutions.loancalculator.mLoan_Term1";
    public final static String Loan_Installment_Amt = "com.transposesolutions.loancalculator.mLoan_Installment_Amt";
    public final static String Loan_InstallmentDate1 = "com.transposesolutions.loancalculator.mLoan_InstallmentDate1";
    public final static String  Loan_Amt = "com.transposesolutions.loancalculator.mLoan_Amt";
    public final static String  Loan_Rate = "com.transposesolutions.loancalculator.mLoan_Rate";
    public final static String  Loan_Day = "com.transposesolutions.loancalculator.mLoan_Day";
    public final static String  Loan_Month = "com.transposesolutions.loancalculator.mLoan_Month";
    public final static String  Loan_Year = "com.transposesolutions.loancalculator.mLoan_Year";
    public final static String  Loan_ids = "com.transposesolutions.loancalculator.mLoan_ids";

    //Initialization of Database
    public  static LoanTrackerDatabase myAppDatabase;
    private AdView adView;
    private ActionBarDrawerToggle mToggle;
    ImageView Edit1,Delete1,InstallmentDate1,InstallmentDate2,Edit2,Delete2,InstallmentDate3,
    Edit3,Delete3,InstallmentDate4,Edit4,Delete4,InstallmentDate5,Edit5,Delete5;
    TextView id1,Name1,LoanAmount1,Due_Date1,id2,Name2,LoanAmount2,Due_Date2,
            id3,Name3,LoanAmount3,Due_Date3,id4,Name4,LoanAmount4,Due_Date4,
            id5,Name5,LoanAmount5,Due_Date5;
    TableRow row1,row2,row3,row4,row5;
    String Rate,Loan_Amount,Term,name,Installment_Amount,Due_Date,Rate1,tot_Amt1,term1,Rate2,tot_Amt2,term2,Rate3,tot_Amt3,term3,Rate4,tot_Amt4,term4,Rate5,tot_Amt5,term5,
            day,month,year,day1,month1,year1,day2,month2,year2,day3,month3,year3,day4,month4,year4,day5,month5,year5,Id,FIRST_ID,SECOND_ID,THIRD_ID,FOURTH_ID,FIFTH_ID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_tracker_module1);
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

        //  Database connection
        myAppDatabase= LoanTrackerDatabase.getDBinstance(this.getApplicationContext());

        LoanTracker tracker = (LoanTracker) getApplicationContext();

        //Setting the the row to 0 initially
        tracker.setRow1(0);

        //FIRST ROW
        id1 = findViewById(R.id.id1);
        Name1 = findViewById(R.id.Name1);
        LoanAmount1= findViewById(R.id. LoanAmount1);
        Due_Date1 = findViewById(R.id. LoanTerm1);
        InstallmentDate1 = findViewById(R.id.InstallmentDate1);
        InstallmentDate1.setImageDrawable(getResources().getDrawable(R.drawable.ic_view));
        Edit1 = findViewById(R.id.Edit1);
        Edit1.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
        Delete1 = findViewById(R.id.Delete1);
        Delete1.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete));
        row1 = findViewById(R.id.row_2);
        row2 = findViewById(R.id.row_3);
        row3 = findViewById(R.id.row_4);
        row4 = findViewById(R.id.row_5);
        row5 = findViewById(R.id.row_6);

        //second row
        id2 = findViewById(R.id.id2);
        Name2 = findViewById(R.id.Name2);
        LoanAmount2= findViewById(R.id. LoanAmount2);
        Due_Date2 = findViewById(R.id. LoanTerm2);
        InstallmentDate2 = findViewById(R.id.InstallmentDate2);
        Edit2 = findViewById(R.id.Edit2);
        Delete2 = findViewById(R.id.Delete2);
        InstallmentDate2.setImageDrawable(getResources().getDrawable(R.drawable.ic_view));
        Edit2.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
        Delete2.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete));
        row1 = findViewById(R.id.row_2);
        row2 = findViewById(R.id.row_3);
        //Third row
        id3 = findViewById(R.id.id3);
        Name3 = findViewById(R.id.Name3);
        LoanAmount3= findViewById(R.id. LoanAmount3);
        Due_Date3 = findViewById(R.id. LoanTerm3);
        InstallmentDate3 = findViewById(R.id.InstallmentDate3);
        Edit3 = findViewById(R.id.Edit3);
        Delete3 = findViewById(R.id.Delete3);
        InstallmentDate3.setImageDrawable(getResources().getDrawable(R.drawable.ic_view));
        Edit3.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
        Delete3.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete));
        //fourth row
        id4 = findViewById(R.id.id4);
        Name4 = findViewById(R.id.Name4);
        LoanAmount4= findViewById(R.id. LoanAmount4);
        Due_Date4 = findViewById(R.id. LoanTerm4);
        InstallmentDate4 = findViewById(R.id.InstallmentDate4);
        Edit4 = findViewById(R.id.Edit4);
        Delete4 = findViewById(R.id.Delete4);
        InstallmentDate4.setImageDrawable(getResources().getDrawable(R.drawable.ic_view));
        Edit4.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
        Delete4.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete));
        //fifth row
        id5 = findViewById(R.id.id5);
        Name5 = findViewById(R.id.Name5);
        LoanAmount5= findViewById(R.id. LoanAmount5);
        Due_Date5 = findViewById(R.id. LoanTerm5);
        InstallmentDate5 = findViewById(R.id.InstallmentDate5);
        Edit5 = findViewById(R.id.Edit5);
        Delete5 = findViewById(R.id.Delete5);
        InstallmentDate5.setImageDrawable(getResources().getDrawable(R.drawable.ic_view));
        Edit5.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
        Delete5.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete));

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Loan_View", MODE_PRIVATE);
        day = sharedPreferences.getString(LoanTrackerModule2.Loan_Day, "");
        month = sharedPreferences.getString(LoanTrackerModule2.Loan_Month, "");
        year = sharedPreferences.getString(LoanTrackerModule2.Loan_Year, "");

        // Fetching the Data from the database
        List<LoanTrackerEntity> users = myAppDatabase.loanTrackerDao().getAllLoans();
        int size = users.size();
        if(size>0){
            for (LoanTrackerEntity usr:users){
                Id = String.valueOf(usr.getUid());
                name = usr.getLoanName();
                Due_Date = usr.getLoanStartDate();
                Loan_Amount = String.valueOf(usr.getLoanAmount());
                Term = String.valueOf(usr.getLoanTerms());
                Rate = String.valueOf(usr.getLoanRate());
                int value = tracker.getRow1();

                //code to fill the first row
                if (value == 0) {
                    row1.setVisibility(View.VISIBLE);
                    id1.setText("1");
                    Name1.setText(name);
                    double getPayment = myAppDatabase.loanTrackerDao().getPayment(Integer.parseInt(Id));
                    LoanAmount1.setText(String.valueOf(getPayment));
                    Due_Date1.setText(Due_Date);
                    term1 = Term;
                    Rate1 = Rate;
                    tot_Amt1 = Loan_Amount;
                    day1 = day;
                    month1 = month;
                    year1 = year;
                    FIRST_ID = Id;
                    tracker.setRow1(1);

                    //code to fill the second row
                }else if (value ==1){
                    row1.setVisibility(View.VISIBLE);
                    row2.setVisibility(View.VISIBLE);
                    id2.setText("2");
                    Name2.setText(name);
                    double getPayment = myAppDatabase.loanTrackerDao().getPayment(Integer.parseInt(Id));
                    LoanAmount2.setText(String.valueOf(getPayment));
                    Due_Date2.setText(Due_Date);
                    term2 = Term;
                    Rate2 = Rate;
                    tot_Amt2 = Loan_Amount;
                    day2 = day;
                    month2 = month;
                    year2 = year;
                    SECOND_ID = Id;
                    tracker.setRow1(2);

                    //code to fill the third row
                }else if (value ==2){
                    row1.setVisibility(View.VISIBLE);
                    row2.setVisibility(View.VISIBLE);
                    row3.setVisibility(View.VISIBLE);
                    row1.setVisibility(View.VISIBLE);
                    row2.setVisibility(View.VISIBLE);
                    row3.setVisibility(View.VISIBLE);
                    id3.setText("3");
                    Name3.setText(name);
                    double getPayment = myAppDatabase.loanTrackerDao().getPayment(Integer.parseInt(Id));
                    LoanAmount3.setText(String.valueOf(getPayment));
                    Due_Date3.setText(Due_Date);
                    term3 = Term;
                    Rate3 = Rate;
                    tot_Amt3 = Loan_Amount;
                    day3 = day;
                    month3 = month;
                    year3 = year;
                    THIRD_ID = Id;
                    tracker.setRow1(3);

                    //code to fill the fourth row
                }else if (value ==3){
                    row1.setVisibility(View.VISIBLE);
                    row2.setVisibility(View.VISIBLE);
                    row3.setVisibility(View.VISIBLE);
                    row4.setVisibility(View.VISIBLE);
                    id4.setText("4");
                    Name4.setText(name);
                    double getPayment = myAppDatabase.loanTrackerDao().getPayment(Integer.parseInt(Id));
                    LoanAmount4.setText(String.valueOf(getPayment));
                    Due_Date4.setText(Due_Date);
                    term4 = Term;
                    Rate4 = Rate;
                    tot_Amt4 = Loan_Amount;
                    day4 = day;
                    month4 = month;
                    year4 = year;
                    FOURTH_ID = Id;
                    tracker.setRow1(4);

                    //code to fill the fifth row
                }else if (value ==4){
                    row1.setVisibility(View.VISIBLE);
                    row2.setVisibility(View.VISIBLE);
                    row3.setVisibility(View.VISIBLE);
                    row4.setVisibility(View.VISIBLE);
                    row5.setVisibility(View.VISIBLE);
                    id5.setText("5");
                    Name5.setText(name);
                    double getPayment = myAppDatabase.loanTrackerDao().getPayment(Integer.parseInt(Id));
                    LoanAmount5.setText(String.valueOf(getPayment));
                    Due_Date5.setText(Due_Date);
                    term5 = Term;
                    Rate5 = Rate;
                    tot_Amt5 = Loan_Amount;
                    day5 = day;
                    month5 = month;
                    year5 = year;
                    FIFTH_ID = Id;
                    tracker.setRow1(5);

                    //code if the rows are full
                }else if (value ==5){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoanTrackerModule1.this);
                    builder.setMessage("Db full?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        }else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(LoanTrackerModule1.this);
            dialog.setCancelable(false);
            dialog.setTitle("Dashboard is empty");
            dialog.setMessage("Please enter the loan information..!" );
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    startActivity(new Intent(LoanTrackerModule1.this,LoanTrackerModule2.class));
                    //Action for "Delete".
                }
            });
            AlertDialog alert = dialog.create();
            alert.show();
        }


    }

    // ** Called when leaving the activity */
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

    public void activity_share(View view) {
    }



    public void AddLoan(View view) {
        Intent intent  = new Intent(this, LoanTrackerModule2.class);
        startActivity(intent);
    }

    //On click view options
    public void view(View view) {

        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("Loan_View", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString(Loan_Name1, Name1.getText().toString());
        editor.putString(Loan_Installment_Amt, LoanAmount1.getText().toString());
        editor.putString(Loan_Term1, term1);
        editor.putString(Loan_InstallmentDate1, Due_Date1.getText().toString());
        editor.putString(Loan_Amt, tot_Amt1);
        editor.putString(Loan_Rate, Rate1);
        editor.putString(Loan_Day, day1);
        editor.putString(Loan_Month, month1);
        editor.putString(Loan_Year, year1);
        editor.putString(Loan_ids, FIRST_ID);
        editor.apply();
        Intent intent  = new Intent(this, com.transposesolutions.loantrackerproject.View.class);
        startActivity(intent);





    }


    public void view2(View view) {
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("Loan_View", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString(Loan_Name1, Name2.getText().toString());
        editor.putString(Loan_Installment_Amt, LoanAmount2.getText().toString());
        editor.putString(Loan_Term1, term2);
        editor.putString(Loan_InstallmentDate1, Due_Date2.getText().toString());
        editor.putString(Loan_Amt, tot_Amt2);
        editor.putString(Loan_Rate, Rate2);
        editor.putString(Loan_Day, day2);
        editor.putString(Loan_Month, month2);
        editor.putString(Loan_Year, year2);
        editor.putString(Loan_ids, SECOND_ID);
        editor.apply();
        Intent intent  = new Intent(this, com.transposesolutions.loantrackerproject.View.class);
        startActivity(intent);

    }

    public void view3(View view) {
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("Loan_View", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString(Loan_Name1, Name3.getText().toString());
        editor.putString(Loan_Installment_Amt, LoanAmount3.getText().toString());
        editor.putString(Loan_Term1, term3);
        editor.putString(Loan_InstallmentDate1, Due_Date3.getText().toString());
        editor.putString(Loan_Amt, tot_Amt3);
        editor.putString(Loan_Rate, Rate3);
        editor.putString(Loan_Day, day3);
        editor.putString(Loan_Month, month3);
        editor.putString(Loan_Year, year3);
        editor.putString(Loan_ids, THIRD_ID);
        editor.apply();
        Intent intent  = new Intent(this, com.transposesolutions.loantrackerproject.View.class);
        startActivity(intent);
    }

    public void view4(View view) {
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("Loan_View", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString(Loan_Name1, Name4.getText().toString());
        editor.putString(Loan_Installment_Amt, LoanAmount4.getText().toString());
        editor.putString(Loan_Term1, term4);
        editor.putString(Loan_InstallmentDate1, Due_Date4.getText().toString());
        editor.putString(Loan_Amt, tot_Amt4);
        editor.putString(Loan_Rate, Rate4);
        editor.putString(Loan_Day, day4);
        editor.putString(Loan_Month, month4);
        editor.putString(Loan_Year, year4);
        editor.putString(Loan_ids, FOURTH_ID);
        editor.apply();
        Intent intent  = new Intent(this, com.transposesolutions.loantrackerproject.View.class);
        startActivity(intent);
    }

    public void view5(View view) {
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("Loan_View", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString(Loan_Name1, Name5.getText().toString());
        editor.putString(Loan_Installment_Amt, LoanAmount5.getText().toString());
        editor.putString(Loan_Term1, term5);
        editor.putString(Loan_InstallmentDate1, Due_Date5.getText().toString());
        editor.putString(Loan_Amt, tot_Amt5);
        editor.putString(Loan_Rate, Rate5);
        editor.putString(Loan_Day, day5);
        editor.putString(Loan_Month, month5);
        editor.putString(Loan_Year, year5);
        editor.putString(Loan_ids, FIFTH_ID);
        editor.apply();
        Intent intent  = new Intent(this, com.transposesolutions.loantrackerproject.View.class);
        startActivity(intent);
    }

    // onclick delete options
    public void Delete1(View view) {
        int id = Integer.parseInt(FIRST_ID);
        LoanTrackerEntity tracker = new LoanTrackerEntity();
        tracker.setUid(id);
        myAppDatabase.loanTrackerDao().deleteLoan(tracker);
        Toast.makeText(getApplicationContext(),"user Deleted Successfully",Toast.LENGTH_LONG).show();
        List<AmortizationEntity> amortizationEntitiesData = myAppDatabase.loanTrackerDao().getAllAmortizationList(id);
        for(AmortizationEntity amortizationEntities : amortizationEntitiesData){
            int ids = amortizationEntities.getUid();
            AmortizationEntity amortizationEntity = new AmortizationEntity();
            amortizationEntity.setUid(ids);
            myAppDatabase.loanTrackerDao().deleteAmortizationData((AmortizationEntity) amortizationEntity);
        }

        String loanName= Name1.getText().toString();
        id1.setText(" ");
        Name1.setText("");
        On_Delete();
    }
    public void Delete2(View view) {
        int id = Integer.parseInt(SECOND_ID);

        LoanTrackerEntity tracker = new LoanTrackerEntity();
        tracker.setUid(id);
        myAppDatabase.loanTrackerDao().deleteLoan(tracker);
        Toast.makeText(getApplicationContext(),"user Deleted Successfully",Toast.LENGTH_LONG).show();
        String loanName= Name2.getText().toString();
        List<AmortizationEntity> amortizationEntitiesData = myAppDatabase.loanTrackerDao().getAllAmortizationList(id);
        for(AmortizationEntity amortizationEntities : amortizationEntitiesData){
            int ids = amortizationEntities.getUid();
            AmortizationEntity amortizationEntity = new AmortizationEntity();
            amortizationEntity.setUid(ids);
            myAppDatabase.loanTrackerDao().deleteAmortizationData((AmortizationEntity) amortizationEntity);
        }

        id1.setText(" ");
        Name1.setText("");
        On_Delete();
    }

    public void Delete3(View view) {
        int id = Integer.parseInt(THIRD_ID);
        LoanTrackerEntity tracker = new LoanTrackerEntity();
        tracker.setUid(id);
        myAppDatabase.loanTrackerDao().deleteLoan(tracker);
        List<AmortizationEntity> amortizationEntitiesData = myAppDatabase.loanTrackerDao().getAllAmortizationList(id);
        for(AmortizationEntity amortizationEntities : amortizationEntitiesData){
            int ids = amortizationEntities.getUid();
            AmortizationEntity amortizationEntity = new AmortizationEntity();
            amortizationEntity.setUid(ids);
            myAppDatabase.loanTrackerDao().deleteAmortizationData((AmortizationEntity) amortizationEntity);
        }
        Toast.makeText(getApplicationContext(),"user Deleted Successfully",Toast.LENGTH_LONG).show();
        String loanName= Name3.getText().toString();

        id1.setText(" ");
        Name1.setText("");
        On_Delete();
    }

    public void Delete4(View view) {
        int id = Integer.parseInt(FOURTH_ID);

        LoanTrackerEntity tracker = new LoanTrackerEntity();
        tracker.setUid(id);
        myAppDatabase.loanTrackerDao().deleteLoan(tracker);
        List<AmortizationEntity> amortizationEntitiesData = myAppDatabase.loanTrackerDao().getAllAmortizationList(id);
        for(AmortizationEntity amortizationEntities : amortizationEntitiesData){
            int ids = amortizationEntities.getUid();
            AmortizationEntity amortizationEntity = new AmortizationEntity();
            amortizationEntity.setUid(ids);
            myAppDatabase.loanTrackerDao().deleteAmortizationData((AmortizationEntity) amortizationEntity);
        }
        Toast.makeText(getApplicationContext(),"user Deleted Successfully",Toast.LENGTH_LONG).show();
        id1.setText(" ");
        Name1.setText("");
        On_Delete();
    }

    public void Delete5(View view) {
        int id = Integer.parseInt(FIFTH_ID);
        LoanTrackerEntity tracker = new LoanTrackerEntity();
        tracker.setUid(id);
        myAppDatabase.loanTrackerDao().deleteLoan(tracker);
        List<AmortizationEntity> amortizationEntitiesData = myAppDatabase.loanTrackerDao().getAllAmortizationList(id);
        for(AmortizationEntity amortizationEntities : amortizationEntitiesData){
            int ids = amortizationEntities.getUid();
            AmortizationEntity amortizationEntity = new AmortizationEntity();
            amortizationEntity.setUid(ids);
            myAppDatabase.loanTrackerDao().deleteAmortizationData((AmortizationEntity) amortizationEntity);
        }
        Toast.makeText(getApplicationContext(),"user Deleted Successfully",Toast.LENGTH_LONG).show();
        id1.setText(" ");
        Name1.setText("");
        On_Delete();
    }


    //onclick Edit options
    public void Edit1(View view) {
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("Loan_View_Edit", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString(Loan_Name1, Name1.getText().toString());
        editor.putString(Loan_Installment_Amt, LoanAmount1.getText().toString());
        editor.putString(Loan_Term1, term1);
        editor.putString(Loan_InstallmentDate1, Due_Date1.getText().toString());
        editor.putString(Loan_Amt, tot_Amt1);
        editor.putString(Loan_Rate, Rate1);
        editor.putString(Loan_Day, day1);
        editor.putString(Loan_Month, month1);
        editor.putString(Loan_Year, year1);
        editor.putString(Loan_id1,FIRST_ID);
        editor.apply();
        Intent intent  = new Intent(this, LoanTrackerModule5.class);
        startActivity(intent);

    }
    public void Edit2(View view) {
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("Loan_View_Edit", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString(Loan_Name1, Name2.getText().toString());
        editor.putString(Loan_Installment_Amt, LoanAmount2.getText().toString());
        editor.putString(Loan_Term1, term2);
        editor.putString(Loan_InstallmentDate1, Due_Date2.getText().toString());
        editor.putString(Loan_Amt, tot_Amt2);
        editor.putString(Loan_Rate, Rate2);
        editor.putString(Loan_Day, day2);
        editor.putString(Loan_Month, month2);
        editor.putString(Loan_Year, year2);
        editor.putString(Loan_id1,SECOND_ID);
        editor.apply();
        Intent intent  = new Intent(this, LoanTrackerModule5.class);
        startActivity(intent);
    }

    public void Edit3(View view) {
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("Loan_View_Edit", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString(Loan_Name1, Name3.getText().toString());
        editor.putString(Loan_Installment_Amt, LoanAmount3.getText().toString());
        editor.putString(Loan_Term1, term3);
        editor.putString(Loan_InstallmentDate1, Due_Date3.getText().toString());
        editor.putString(Loan_Amt, tot_Amt3);
        editor.putString(Loan_Rate, Rate3);
        editor.putString(Loan_Day, day3);
        editor.putString(Loan_Month, month3);
        editor.putString(Loan_Year, year3);
        editor.putString(Loan_id1,THIRD_ID);
        editor.apply();
        Intent intent  = new Intent(this, LoanTrackerModule5.class);
        startActivity(intent);
    }

    public void Edit4(View view) {
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("Loan_View_Edit", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString(Loan_Name1, Name4.getText().toString());
        editor.putString(Loan_Installment_Amt, LoanAmount4.getText().toString());
        editor.putString(Loan_Term1, term4);
        editor.putString(Loan_InstallmentDate1, Due_Date4.getText().toString());
        editor.putString(Loan_Amt, tot_Amt4);
        editor.putString(Loan_Rate, Rate4);
        editor.putString(Loan_Day, day4);
        editor.putString(Loan_Month, month4);
        editor.putString(Loan_Year, year4);
        editor.putString(Loan_id1,FOURTH_ID);
        editor.apply();
        Intent intent  = new Intent(this, LoanTrackerModule5.class);
        startActivity(intent);
    }

    public void Edit5(View view) {
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("Loan_View_Edit", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString(Loan_Name1, Name5.getText().toString());
        editor.putString(Loan_Installment_Amt, LoanAmount5.getText().toString());
        editor.putString(Loan_Term1, term5);
        editor.putString(Loan_InstallmentDate1, Due_Date5.getText().toString());
        editor.putString(Loan_Amt, tot_Amt5);
        editor.putString(Loan_Rate, Rate5);
        editor.putString(Loan_Day, day5);
        editor.putString(Loan_Month, month5);
        editor.putString(Loan_Year, year5);
        editor.putString(Loan_id1,FIFTH_ID);
        editor.apply();
        Intent intent  = new Intent(this, LoanTrackerModule5.class);
        startActivity(intent);

    }

    // Navigation After deletion of a row
    private void On_Delete() {
        Intent intent = new Intent(this,LoanTrackerModule1.class);
        startActivity(intent);
    }
}