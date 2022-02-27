package com.transposesolutions.unitconverter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class DigitalStorage extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Declare the instance variable Ad view
    private AdView adView;
    private ActionBarDrawerToggle mToggle;
    EditText UserInput;
    TextView result1,result2,result3,result4,result5,result6,result7,result8,result9,result10,result11,result12,result13,result14;
    Spinner selector_1;
    double x=0;
    double userValue1=0;
    String selectedItem;
    DecimalFormat decimalFormat = new DecimalFormat("#.######");
    DecimalFormat decimalScientificFormat = new DecimalFormat("#.#####E0");

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
            if (editable.length() == 0){
                result1.setText("");
                result2.setText("");
                result3.setText("");
                result4.setText("");
                result5.setText("");
                result6.setText("");
                result7.setText("");
                result8.setText("");
                result9.setText("");
                result10.setText("");
                result11.setText("");
                result12.setText("");
                result13.setText("");
                result14.setText("");
            }else{
                checkForChanges1();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_storage);
        //UI elements
        UserInput = findViewById(R.id.user_input);
        selector_1 = findViewById(R.id.selector_1);
        result1=findViewById(R.id.Result_1);
        result2=findViewById(R.id.Result_2);
        result3=findViewById(R.id.Result_3);
        result4=findViewById(R.id.Result_4);
        result5=findViewById(R.id.Result_5);
        result6=findViewById(R.id.Result_6);
        result7=findViewById(R.id.Result_7);
        result8=findViewById(R.id.Result_8);
        result9=findViewById(R.id.Result_9);
        result10=findViewById(R.id.Result_10);
        result11=findViewById(R.id.Result_11);
        result12=findViewById(R.id.Result_12);
        result13=findViewById(R.id.Result_13);
        result14=findViewById(R.id.Result_14);

        // to perform the default logic on create
        int defaultValue1 = 1;
        String displayValue1 = String.valueOf(defaultValue1);
        UserInput.setText(displayValue1);
        // set listeners
        UserInput.addTextChangedListener(mTextWatcher);
        checkForChanges1();
        // Load an ad into the AdMob banner view.
        FrameLayout adContainerView = findViewById(R.id.ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
        adContainerView.addView(adView);
        loadBanner();
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
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_storage_items, R.layout.spinner_selector_items);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        // Apply the adapter to the spinner
        selector_1.setAdapter( adapter);
        selector_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);
                String activityTextValue = UserInput.getText().toString();
                try {
                    userValue1 = Double.parseDouble(activityTextValue);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if(position==0){
                    Bit_update();
                }else if(position==1){
                    Byte_update();
                }else if(position==2){
                    Kilobyte_update();
                }else if(position==3){
                    Kilo_bit_update();
                }else if(position==4){
                    Megabit_update();
                }else if(position==5){
                    Megabyte_update();
                }else if(position==6){
                    Gigabit_update();
                }else if(position==7){
                    Gigabyte_update();
                }else if(position==8){
                    Tera_bit_update();
                }else if(position==9){
                    Terabyte_update();
                }else if(position==10){
                    Petabit_update();
                }else if(position==11){
                    PetaByte_update();
                }else if(position==12){
                    PebiBit_update();
                }else if(position==13){
                    PebiByte_update();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void checkForChanges1() {
        // First get the edit text value from the activity
        String activityTextValue1 = UserInput.getText().toString();
        // convert double
        try {
            userValue1 = Double.parseDouble(activityTextValue1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String spinnerValue = String.valueOf(selector_1.getSelectedItem());
        switch (spinnerValue) {
            case "Bit (b)":
                x = 1;
                break;
            case "Byte (B)":
                x = 2;
                break;
            case "Kilobyte (kB)":
                x = 3;
                break;
            case "Kilo bit (kb)":
                x = 4;
                break;
            case "Megabit (Mb)":
                x = 5;
                break;
            case "Megabyte (MB)":
                x = 6;
                break;
            case "Gigabit (Gb)":
                x = 7;
                break;
            case "Gigabyte (GB)":
                x = 8;
                break;
            case "Tera bit (Tb)":
                x = 9;
                break;
            case "Terabyte (TB)":
                x = 10;
                break;
            case "Peta bit (Pb)":
                x = 11;
                break;
            case "Peta byte (PB)":
                x = 12;
                break;
            case "Pebi bit (Pib)":
                x = 13;
                break;
            case "Pebi byte (PiB)":
                x = 14;
                break;
            default:
                break;
        }
        if(x==1){
            Bit_update();
        }else if (x==2) {
            Byte_update();
        }else if (x==3) {
            Kilobyte_update();
        }else if (x==4) {
            Kilo_bit_update();
        }else if (x==5) {
            Megabit_update();
        }else if (x==6) {
            Megabyte_update();
        }else if (x==7) {
            Gigabit_update();
        }else if (x==8) {
            Gigabyte_update();
        }else if (x==9) {
            Tera_bit_update();
        }else if (x==10) {
            Terabyte_update();
        } else if (x==11) {
            Petabit_update();
        }else if (x==12) {
            PetaByte_update();
        }else if (x==13) {
            PebiBit_update();
        }else if (x==14) {
            PebiByte_update();
        }
    }

    private void PebiByte_update() {
        // PebiByte  to Bit
        // example PeBi_byte= 1 Bit=PeBi_byte * 9.007e+15;  result = 9.007e+15
        double valueSelector1 = (userValue1 * 9.007e+15);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // PebiByte  to Byte
        // example PeBi_byte= 1 Byte=PeBi_byte * 1.126e+15;  result = 1.126e+15
        double valueSelector2 = (userValue1 * 1.126e+15);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // PebiByte  to Kilobyte
        // example PeBi_byte= 1 Kilobyte = PeBi_byte *1125899906842.66 ;  result = 1.126e+12
        double valueSelector3 =  (userValue1 * 1.126E+12 );
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // PebiByte  to Kilo  bit
        // example PeBi_byte= 1 Kilo_bit=PeBi_byte *9007199254741.28;  result = 9.007e+12
        double valueSelector4 =  (userValue1 * 9.007E+12 );
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // PebiByte  to Megabit
        // example PeBi_byte= 1 Megabit=PeBi_byte *9007199254.7413;  result = 9.007e+9
        double valueSelector5 =  (userValue1 * 9.007E+9);
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // PebiByte  to  Megabyte
        // example PeBi_byte= 1 Megabyte=PeBi_byte *1125899906.8427;  result = 1.126e+9
        double valueSelector6 =  (userValue1 * 1.126E+9);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // PebiByte  to Gigabit
        // example PeBi_byte= 1 Gigabit=PeBi_byte *9007199.2547;  result =9.007e+6
        double valueSelector7 =  (userValue1 * 9.007E+6);
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // PebiByte  to Gigabyte
        // example PeBi_byte= 1 Gigabyte=PeBi_byte *1125899.9068;  result = 1.126e+6
        double valueSelector8 = (userValue1 * 1.126E+6);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // PebiByte  to  TeraBit
        // example PeBi_byte= 1 TeraBit=PeBi_byte *  9007;  result =  9007
        double valueSelector9 =  (userValue1 * 9007.2);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // PebiByte  to Terabyte
        // example PeBi_byte= 1 Terabyte=PeBi_byte *1125.9;  result = 1125.9
        double valueSelector10 =  (userValue1 * 1125.9);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // PebiByte  to Peta bit
        // example PeBi_byte= 1 Peta_bit=PeBi_byte *9.0072;  result = 9.0072
        double valueSelector11 =  (userValue1 * 9.0072);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // PebiByte  to  Peta byte
        // example PeBi_byte= 1 Peta_byte=PeBi_byte *1.1259;  result = 1.1259
        double valueSelector12 =  (userValue1 * 1.1259);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        // PebiByte  to Pebi bit
        // example PeBi_byte= 1  Pebi bit=PeBi_byte *8;  result = 8
        int valueSelector13 = (int) (userValue1 * 8);
        String _valueSelector13 = String.valueOf(valueSelector13);
        result13.setText(_valueSelector13);
        // PebiByte  to Pebi byte
        // example PeBi_byte= 1 PeBi_byte=PeBi_byte * 1;  result = 1
        int valueSelector14 = (int) (userValue1 * 1);
        String _valueSelector14 = String.valueOf(valueSelector14);
        result14.setText(_valueSelector14);

    }

    private void PebiBit_update() {
        // Pebi bit   to Bit
        // example PeBi_bit= 1 Bit=PeBi_bit *1.126e+15;  result = 1.126e+15
        double valueSelector1 = (userValue1 * 1.126e+15);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Pebi bit   to Byte
        // example PeBi_bit= 1 Byte=PeBi_bit *1.407e+14;  result = 1.407e+14
        double valueSelector2 = (userValue1 * 1.407e+14);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Pebi bit   to Kilobyte
        // example PeBi_bit= 1 Kilobyte=PeBi_bit *1.407e+11;  result = 1.407e+11
        double valueSelector3 =  (userValue1 * 1.407e+11);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Pebi bit   to Kilo  bit
        // example PeBi_bit= 1 Kilo_bit=PeBi_bit *1.126e+12;  result = 1.126e+12
        double valueSelector4 =  (userValue1 * 1.126e+12 );
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Pebi bit   to Megabit
        // example PeBi_bit= 1 Megabit=PeBi_bit *1.126e+9;  result =1.126e+9
        double valueSelector5 =  (userValue1 * 1.26e+9);
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Pebi bit   to  Megabyte
        // example PeBi_bit= 1 Megabyte=PeBi_bit *1.407e+8;  result = 1.407e+8
        double valueSelector6 =  (userValue1 * 1.407e+8);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Pebi bit   to Gigabit
        // example PeBi_bit= 1 Gigabit=PeBi_bit *1.126e+6;  result = 1.126e+6
        double valueSelector7 = (userValue1 *  1.126e+6);
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // Pebi bit   to Gigabyte
        // example PeBi_bit= 1 Gigabyte=PeBi_bit *140737;  result = 140737
        int valueSelector8 = (int) (userValue1 * 140737);
        String _valueSelector8 = String.valueOf(valueSelector8);
        result8.setText(_valueSelector8);
        // Pebi bit   to  TeraBit
        // example PeBi_bit= 1 TeraBit=PeBi_bit *1125.9;  result =1125.9
        double valueSelector9 = (userValue1 * 1125.9);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Pebi bit   to Terabyte
        // example PeBi_bit= 1 Terabyte=PeBi_bit *140.737;  result = 140.737
        double valueSelector10 =  (userValue1 * 140.737);
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Pebi bit   to Peta bit
        // example PeBi_bit= 1 Peta_bit=PeBi_bit *1.1259  ;  result = 1.126
        double valueSelector11 =  (userValue1 * 1.1259  );
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Pebi bit   to  Peta byte
        // example PeBi_bit= 1  Peta_byte=PeBi_bit / 7.105;  result = 0.140737
        double valueSelector12 =  (userValue1 * 0.1407);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        // Pebi bit   to Pebi_bit
        // example PeBi_bit= 1 Pebi_bit=PeBi_bit *1;  result =1
        int valueSelector13 = (int) (userValue1 * 1);
        String _valueSelector13 = String.valueOf(valueSelector13);
        result13.setText(_valueSelector13);
        // Pebi bit   to Pebibyte
        // example PeBi_bit= 1  Pebibyte=PeBi_bit / 8;  result =0.124
        double valueSelector14 =  (userValue1 *0.125);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);

    }

    private void PetaByte_update() {
        // PetaByte  to Bit
        // example PetaByte= 1 , Bit = PetaByte * 9007199254740640;   result = 9007199254740640
        double valueSelector1 = (userValue1 * 8E+15);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // PetaByte  to Byte
        // example PetaByte= 1 , Byte = PetaByte *1125899906842580;   result = 1125899906842580
        double valueSelector2 = (userValue1 * 1E+15);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // PetaByte  to Kilobyte
        // example PetaByte= 1 , Kilobyte = PetaByte * 1099511627776;   result = 1099511627776
        double valueSelector3 =  (userValue1 * 1E+12);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // PetaByte  to Kilo  bit
        // example PetaByte= 1 , Kilo_bit = PetaByte * 8796093022208;   result = 8796093022208
        double valueSelector4 =  (userValue1 *  8E+12 );
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // PetaByte  to Megabit
        // example PetaByte= 1 , Megabit = PetaByte *  8589934592;   result =  8e+9
        double valueSelector5 =  (userValue1 * 8E+9);
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // PetaByte  to  Megabyte
        // example PetaByte= 1 , Megabyte = PetaByte *1073741824;   result = 1e+9
        double valueSelector6 =  (userValue1 * 1E+9);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // PetaByte  to Gigabit
        // example PetaByte= 1 , Gigabit = PetaByte *8388608;   result =8388608
        double valueSelector7 = (userValue1 * 8E+6);
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // PetaByte  to Gigabyte
        // example PetaByte= 1 , Gigabyte = PetaByte * 1048576;   result = 1048576
        double valueSelector8 = (userValue1 * 1E+6);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // PetaByte  to  TeraBit
        // example PetaByte= 1 , TeraBit = PetaByte *8192;   result = 8192
        int valueSelector9 = (int) (userValue1 *  8000);
        String _valueSelector9 = String.valueOf(valueSelector9);
        result9.setText(_valueSelector9);
        // PetaByte  to Terabyte
        // example PetaByte= 1 , Terabyte = PetaByte * 1024;   result = 1024
        int valueSelector10 = (int) (userValue1 * 1000);
        String _valueSelector10 = String.valueOf(valueSelector10);
        result10.setText(_valueSelector10);
        // PetaByte  to Peta bit
        // example PetaByte= 1 , Peta bit = PetaByte * 8;   result = 8
        int valueSelector11 = (int) (userValue1 * 8);
        String _valueSelector11 = String.valueOf(valueSelector11);
        result11.setText(_valueSelector11);
        // PetaByte  to  Peta byte
        // example PetaByte= 1 , Peta_byte = PetaByte *1;   result = 1
        int valueSelector12 = (int) (userValue1 * 1);
        String _valueSelector12 = String.valueOf(valueSelector12);
        result12.setText(_valueSelector12);
        // PetaByte  to Pebi bit
        // example PetaByte= 1 , Pebi_bit = PetaByte * 7.10543;   result =7.10543
        double valueSelector13 =  (userValue1 *  7.1054 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        // PetaByte  to Pebi byte
        // example PetaByte= 1 , Pebi_byte = PetaByte /  1.126;   result =0.888178
        double valueSelector14 =  (userValue1/ 1.126);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);


    }

    private void Petabit_update() {
        // Petabit  to Bit
        // example PetaBit= 1 , Bit = PetaBit * 1125899906842631;   result = 1e+15
        double valueSelector1 = (userValue1 * 1E+15);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Petabit  to Byte
        // example PetaBit= 1 , Byte = PetaBit * 140737488355329;   result = 140737488355329
        double valueSelector2 = (userValue1 * 1.25E+14);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Petabit  to Kilobyte
        // example PetaBit= 1 , Kilobyte = PetaBit * 1.25e+11;   result = 1.25e+11
        double valueSelector3 =  (userValue1 * 1.25E+11);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Petabit  to Kilo  bit
        // example PetaBit= 1 , Kilo_bit = PetaBit * 1099511627776;   result = 1099511627776
        double valueSelector4 =  (userValue1 * 1e+12);
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Petabit  to Megabit
        // example PetaBit= 1 , Megabit = PetaBit * 1073741824;   result = 1073741824
        double valueSelector5 =  (userValue1 * 1E+9);
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Petabit  to  Megabyte
        // example PetaBit= 1 , Megabyte = PetaBit * 134217728;   result = 134217728
        double valueSelector6 =  (userValue1 * 1.25E+8);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Petabit  to Gigabit
        // example PetaBit= 1 , Gigabit = PetaBit * 1048576;   result = 1048576
        double valueSelector7 = (userValue1 * 1E+6);
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // Petabit  to Gigabyte
        // example PetaBit= 1 , Gigabyte = PetaBit * 131072;   result =131072
        int valueSelector8 = (int) (userValue1 *125000);
        String _valueSelector8 = String.valueOf(valueSelector8);
        result8.setText(_valueSelector8);
        // Petabit  to  TeraBit
        // example PetaBit= 1 , TeraBit = PetaBit * 1024;   result = 1024
        int valueSelector9 = (int) (userValue1 * 1000);
        String _valueSelector9 = String.valueOf(valueSelector9);
        result9.setText(_valueSelector9);
        // Petabit  to Terabyte
        // example PetaBit= 1 , Terabyte = PetaBit * 128;   result = 128
        int valueSelector10 = (int) (userValue1 *  125);
        String _valueSelector10 = String.valueOf(valueSelector10);
        result10.setText(_valueSelector10);
        // Petabit  to Peta bit
        // example PetaBit= 1 , Peta_bit = PetaBit * 1;   result = 1
        int valueSelector11 = (int) (userValue1 * 1);
        String _valueSelector11 = String.valueOf(valueSelector11);
        result11.setText(_valueSelector11);
        // Petabit  to  Peta byte
        // example PetaBit= 1 , Peta_byte = PetaBit * 0.125;   result = 0.125
        double valueSelector12 =  (userValue1 * 0.125);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        // Petabit  to Pebi bit
        // example PetaBit= 1 , Pebi_bit = PetaBit * 8.8818E-31 ;   result = 0.888178
        double valueSelector13 =  (userValue1 /1.126);
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        // PetaBit  to Pebi byte
        // example PetaBit= 1 , Pebi_byte = PetaBit * 1.1102E-31;   result =0.111022
        double valueSelector14 =  (userValue1 /  9.007);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);

    }

    private void Terabyte_update() {
        // Terabyte  to Bit
        // example Terabyte= 1 , Bit = Terabyte * 8796093022208;   result = 8796093022208
        double valueSelector1 = (userValue1 * 8E+12);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Terabyte  to Byte
        // example Terabyte= 1 , Byte = Terabyte * 1099511627776;   result = 1099511627776
        double valueSelector2 = (userValue1 * 1E+12);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Terabyte  to Kilobyte
        // example Terabyte= 1 , Kilobyte = Terabyte * 1073741824;   result = 1073741824
        double valueSelector3 =  (userValue1 * 1E+9);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Terabyte  to Kilo  bit
        // example Terabyte= 1 , Kilo_bit = Terabyte *8589934592;   result = 8589934592
        double valueSelector4 =  (userValue1 * 8E+9 );
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Terabyte  to Megabit
        // example Terabyte= 1 , Megabit = Terabyte * 8388608;   result = 8388608
        double valueSelector5 = (userValue1 * 8E+6);
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Terabyte  to  Megabyte
        // example Terabyte= 1 , Megabyte = Terabyte * 1048576;   result = 1048576
        double valueSelector6 = (userValue1 * 1E+6);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Terabyte  to Gigabit
        // example Terabyte= 1 , Gigabit = Terabyte * 8192;   result = 8192
        int valueSelector7 = (int) (userValue1 * 8000);
        String _valueSelector7 = String.valueOf(valueSelector7);
        result7.setText(_valueSelector7);
        // Terabyte  to Gigabyte
        // example Terabyte= 1 , Gigabyte = Terabyte * 1024;   result = 1024
        int valueSelector8 = (int) (userValue1 * 1000);
        String _valueSelector8 = String.valueOf(valueSelector8);
        result8.setText(_valueSelector8);
        // Terabyte  to  TeraBit
        // example Terabyte= 1 , TeraBit = Terabyte * 8;   result = 8
        int valueSelector9 = (int) (userValue1 * 8);
        String _valueSelector9 = String.valueOf(valueSelector9);
        result9.setText(_valueSelector9);
        // Terabyte  to Terabyte
        // example Terabyte= 1 , Terabyte = Terabyte * 1;   result = 1
        int valueSelector10 = (int) (userValue1 * 1);
        String _valueSelector10 = String.valueOf(valueSelector10);
        result10.setText(_valueSelector10);
        // Terabyte  to Peta bit
        // example Terabyte= 1 ,Peta_bit = Terabyte * 0.0078125;   result = 0.0078125
        double valueSelector11 =  (userValue1 * 0.008);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Terabyte  to  Peta byte
        // example Terabyte= 1 , Peta_byte = Terabyte * 0.0009765625;   result = 0.0009765625
        double valueSelector12 =  (userValue1 * 0.001);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        // Terabyte  to Pebi bit
        // example Terabyte= 1 , Pebi_Bit = Terabyte * 0.0071;   result = 0.0071
        double valueSelector13 =  (userValue1 /141 );
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        // Terabyte  to Pebi byte
        // example Terabyte= 1 , Pebi_byte = Terabyte * 0.0009;   result = 0.0009
        double valueSelector14 =  (userValue1 / 1126);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);

    }

    private void Tera_bit_update() {
        // Tera_bit  to Bit
        // example TeraBit= 1 , Bit = TeraBit * 1099511627776;   result = 1099511627776
        double valueSelector1 =  (userValue1 * 1E+12);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Tera_bit  to Byte
        // example TeraBit= 1 , Byte = TeraBit * 1374389534721;   result = 137438953472
        double valueSelector2 = (userValue1 * 1.25E+11);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Tera_bit  to Kilobyte
        // example TeraBit= 1 , Kilobyte = TeraBit * 134217728;   result =134217728
        double valueSelector3 =  (userValue1 * 1.25E+8);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Tera_bit  to Kilo  bit
        // example TeraBit= 1 , KiloBit = TeraBit * 1073741824;   result = 1073741824
        double valueSelector4 =  (userValue1 * 1E+9);
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Tera_bit  to Megabit
        // example TeraBit= 1 , Megabit = TeraBit * 1048576;   result = 1048576
        double valueSelector5 = (userValue1 * 1E+6);
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Tera_bit  to  Megabyte
        // example TeraBit= 1 , Megabyte = TeraBit * 131072;   result =131072
        int valueSelector6 = (int) (userValue1 * 125000);
        String _valueSelector6 = String.valueOf(valueSelector6);
        result6.setText(_valueSelector6);
        // Tera_bit  to Gigabit
        // example TeraBit= 1 , Gigabit = TeraBit * 1024;   result = 1024
        int valueSelector7 = (int) (userValue1 * 1000);
        String _valueSelector7 = String.valueOf(valueSelector7);
        result7.setText(_valueSelector7);
        // Tera_bit  to Gigabyte
        // example TeraBit= 1 ,Gigabyte = TeraBit * 128;   result = 128
        int valueSelector8 = (int) (userValue1 * 125);
        String _valueSelector8 = String.valueOf(valueSelector8);
        result8.setText(_valueSelector8);
        // Tera_bit  to  TeraBit
        // example TeraBit= 1 , TeraBit = TeraBit * 1;   result = 1
        int valueSelector9 = (int) (userValue1 * 1);
        String _valueSelector9 = String.valueOf(valueSelector9);
        result9.setText(_valueSelector9);
        // Tera_bit  to Terabyte
        // example TeraBit= 1 , Terabyte = TeraBit * 0.125;   result = 0.125
        double valueSelector10 =  (userValue1 * 0.125 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Tera_bit  to Peta bit
        // example TeraBit= 1 , Peta_bit = TeraBit * 0.0009765625;   result = 0.0009765625
        double valueSelector11 =  (userValue1  * 0.001);
        String _valueSelector11 = decimalFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Tera_bit  to  Peta byte
        // example TeraBit= 1 , petaBit = TeraBit * 0.0001220703;   result =0.0001220703
        double valueSelector12 =  (userValue1 / 8000);
        String _valueSelector12 = decimalFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        // Tera_bit  to Pebi bit
        // example TeraBit= 1 , pebi_Bit = TeraBit* 0.0009 ;   result = 0.000888178
        double valueSelector13 =  (userValue1 / 1126);
        String _valueSelector13 = decimalFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        // Tera_bit  to Pebi byte
        // example TeraBit= 1 , pebi_byte = TeraBit * 0.0001;   result = 0.000111022
        double valueSelector14 =  (userValue1 / 9007);
        String _valueSelector14 = decimalFormat.format(valueSelector14);
        result14.setText(_valueSelector14);


    }

    private void Gigabyte_update() {
        // Gigabyte  to Bit
        // example Gigabyte= 1 , Bit = Gigabyte * 8589934592;   result = 8589934592
        double valueSelector1 =  (userValue1 *  8E+9);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Gigabyte  to Byte
        // example Gigabyte= 1 , Byte = Gigabyte * 1073741824;   result = 1073741824
        double valueSelector2 = (userValue1 * 1E+9);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Gigabyte  to Kilobyte
        // example Gigabyte= 1 , Kilobyte = Gigabyte * 1048576;   result = 1e+6
        double valueSelector3 = (userValue1 * 1E+6);
        String _valueSelector3 = decimalScientificFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Gigabyte  to Kilo  bit
        // example Gigabyte= 1 , Kilo_bit = Gigabyte * 8388608;   result = 8388608
        double valueSelector4 = (userValue1 * 8E+6);
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Gigabyte  to Megabit
        // example Gigabyte= 1 , Megabit = Gigabyte * 8192;   result = 8192
        int valueSelector5 = (int) (userValue1 * 8000);
        String _valueSelector5 = String.valueOf(valueSelector5);
        result5.setText(_valueSelector5);
        // Gigabyte  to  Megabyte
        // example Gigabyte= 1 , Megabyte = Gigabyte * 1024;   result = 1024
        int valueSelector6 = (int) (userValue1 * 1000);
        String _valueSelector6 = String.valueOf(valueSelector6);
        result6.setText(_valueSelector6);
        // Gigabyte  to Gigabit
        // example Gigabyte= 1 , Gigabit = Gigabyte * 8;   result = 8
        int valueSelector7 = (int) (userValue1 * 8);
        String _valueSelector7 = String.valueOf(valueSelector7);
        result7.setText(_valueSelector7);
        // Gigabyte  to Gigabyte
        // example Gigabyte= 1 , Gigabyte = Gigabyte * 1;   result = 1
        int valueSelector8 = (int) (userValue1 * 1);
        String _valueSelector8 = String.valueOf(valueSelector8);
        result8.setText(_valueSelector8);
        // Gigabyte  to  TeraBit
        // example Gigabyte= 1 , TeraBit = Gigabyte * 0.0078125;   result =0.0078125
        double valueSelector9 =  (userValue1 * 0.008);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Gigabyte  to Terabyte
        // example Gigabyte= 1 , Terabyte = Gigabyte * 0.0009765625;   result = 0.001
        double valueSelector10 =  (userValue1 * 0.001 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Gigabyte  to Peta bit
        // example Gigabyte= 1 , Peta_bit = Gigabyte * 0.0000076294;   result = 8e-6
        double valueSelector11 =  (userValue1* 8E-6 );
        String _valueSelector11 = decimalScientificFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Gigabyte  to  Peta byte
        // example Gigabyte= 1 , Peta_byte = Gigabyte * 9.536743164E-7;   result = 9.536743164E-7
        double valueSelector12 =  (userValue1 * 1E-6);
        String _valueSelector12 = decimalScientificFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        // Gigabyte  to Pebi bit
        // example Gigabyte= 1 , Pebi_Bit = Gigabyte * 7.1054E-6;   result = 7.1054e-6
        double valueSelector13 =  (userValue1 * 7.1054E-6 );
        String _valueSelector13 = decimalScientificFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        // Gigabyte  to Pebi byte
        // example Gigabyte= 1 , Pebi_byte = Gigabyte * 8.8818E-7;   result =8.8818e-7
        double valueSelector14 =  (userValue1 * 8.8818E-7);
        String _valueSelector14 = decimalScientificFormat.format(valueSelector14);
        result14.setText(_valueSelector14);


    }

    private void Gigabit_update() {
        // Gigabit  to Bit
        // example Gigabit= 1 , Bit = Gigabit * 1073741824;   result = 1073741824
        double valueSelector1 = (userValue1 * 1E+9);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Gigabit  to Byte
        // example Gigabit= 1 , Byte = Gigabit * 134217728;   result = 134217728
        double valueSelector2 =  (userValue1 * 1.25E+8);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Gigabit  to Kilobyte
        // example Gigabit= 1 , Kilobyte = Gigabit * 131072;   result = 131072
        int valueSelector3 = (int) (userValue1 * 125000);
        String _valueSelector3 = String.valueOf(valueSelector3);
        result3.setText(_valueSelector3);
        // Gigabit  to Kilo  bit
        // example Gigabit= 1 , Kilo_bit = Gigabit *1048576;   result = 1048576
        double valueSelector4 = (userValue1 * 1E+6);
        String _valueSelector4 = decimalScientificFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Gigabit  to Megabit
        // example Gigabit= 1 , Megabit = Gigabit * 1024;   result = 1024
        int valueSelector5 = (int) (userValue1 * 1000);
        String _valueSelector5 = String.valueOf(valueSelector5);
        result5.setText(_valueSelector5);
        // Gigabit  to  Megabyte
        // example Gigabit= 1 , Megabyte = Gigabit * 128;   result = 128
        int valueSelector6 = (int) (userValue1 * 125);
        String _valueSelector6 = String.valueOf(valueSelector6);
        result6.setText(_valueSelector6);
        // Gigabit  to Gigabit
        // example Gigabit= 1 , Gigabit = Gigabit * 1;   result = 1
        int valueSelector7 = (int) (userValue1 * 1);
        String _valueSelector7 = String.valueOf(valueSelector7);
        result7.setText(_valueSelector7);
        // Gigabit  to Gigabyte
        // example Gigabit= 1 , Gigabyte = Gigabit* 0.125;   result = 0.125
        double valueSelector8 =  (userValue1* 0.125);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // Gigabit  to  TeraBit
        // example Gigabit= 1 , TeraBit = Gigabit * 0.0009765625;   result = 0.0009765625
        double valueSelector9 =  (userValue1 * 0.001);
        String _valueSelector9 = decimalFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Gigabit  to Terabyte
        // example Gigabit= 1 , Terabyte = Gigabit * 0.0001220703;   result = 0.0001220703
        double valueSelector10 =  (userValue1 * 0.000125 );
        String _valueSelector10 = decimalFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Gigabit  to Peta bit
        // example Gigabit= 1 , Peta_bit = Gigabit * 9.536743164E-7;   result = 9.536743164E-7
        double valueSelector11 =  (userValue1 * 1E-6 );
        String _valueSelector11 = decimalScientificFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Gigabit  to  Peta byte
        // example Gigabit= 1 , Peta_byte = Gigabit *1.192092895E-7;   result = 1.192092895E-7
        double valueSelector12 =  (userValue1 * 1.25E-7);
        String _valueSelector12 = decimalScientificFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        // Gigabit  to Pebi bit
        // example Gigabit= 1 , Pebi_Bit = Gigabit * 8.8818E-7;   result = 8.8818e-7
        double valueSelector13 =  (userValue1 * 8.8818E-7 );
        String _valueSelector13 = decimalScientificFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        // Gigabit  to Pebi byte
        // example Gigabit= 1 , Pebi_byte = Gigabit * 1.1102E-7;   result = 1.1102e-7
        double valueSelector14 =  (userValue1 * 1.1102E-7);
        String _valueSelector14 = decimalScientificFormat.format(valueSelector14);
        result14.setText(_valueSelector14);


    }

    private void Megabyte_update() {
        // Megabyte  to Bit
        // example Megabyte= 1 , Bit = Megabyte * 8388608;   result = 8388608
        double valueSelector1 = (userValue1 * 8E+6);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Megabyte  to Byte
        // example Megabyte= 1 , Byte = Megabyte * 1048576;   result = 1048576
        double valueSelector2 =  (userValue1 * 1E+6);
        String _valueSelector2 = decimalScientificFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Megabyte  to Kilobyte
        // example Megabyte= 1 , Kilobyte = Megabyte * 1024;   result = 1024
        int valueSelector3 = (int) (userValue1 * 1000);
        String _valueSelector3 = String.valueOf(valueSelector3);
        result3.setText(_valueSelector3);
        // Megabyte  to Kilo  bit
        // example Megabyte= 1 ,  Kilo_bit = Megabyte * 8192;   result = 8192
        int valueSelector4 = (int) (userValue1 * 8000);
        String _valueSelector4 = String.valueOf(valueSelector4);
        result4.setText(_valueSelector4);
        // Megabyte  to Megabit
        // example Megabyte= 1 , Megabit = Megabyte * 8;   result = 8
        int valueSelector5 = (int) (userValue1 * 8);
        String _valueSelector5 = String.valueOf(valueSelector5);
        result5.setText(_valueSelector5);
        // Megabyte  to  Megabyte
        // example Megabyte= 1 , Megabyte = Megabyte * 1;   result = 1
        int valueSelector6 = (int) (userValue1 * 1);
        String _valueSelector6 = String.valueOf(valueSelector6);
        result6.setText(_valueSelector6);
        // Megabyte  to Gigabit
        // example Megabyte= 1 , Gigabit = Megabyte * 0.0078125;   result = 0.0078125
        double valueSelector7 =  (userValue1 * 0.008 );
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // Megabyte  to Gigabyte
        // example Megabyte= 1 , Gigabyte = Megabyte * 0.0009765625;   result = 0.0009765625
        double valueSelector8 =  (userValue1 * 0.001);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // Megabyte  to  TeraBit
        // example Megabyte= 1 , TeraBit = Megabyte * 0.0000076294;   result = 0.0000076294
        double valueSelector9 =  (userValue1 * 8E-6);
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Megabyte  to Terabyte
        // example Megabyte= 1 , Terabyte = Megabyte * 9.536743164E-7;   result = 9.536743164E-7
        double valueSelector10 =  (userValue1 * 1E-6 );
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Megabyte  to Peta bit
        // example Megabyte= 1 ,  Peta_bit = Megabyte* 7.450580596E-9;   result = 7.450580596E-9
        double valueSelector11 =  (userValue1 * 8E-9);
        String _valueSelector11 = decimalScientificFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Megabyte  to  Peta byte
        // example Megabyte= 1 , Peta_byte = Megabyte * 9.313225746E-10;   result = 9.313225746E-10
        double valueSelector12 =  (userValue1 * 1E-9);
        String _valueSelector12 = decimalScientificFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        // Megabyte  to Pebi bit
        // example Megabyte= 1 , Pebi_bit = Megabyte * 7.1054E-9;   result = 7.1054e-9
        double valueSelector13 =  (userValue1 * 7.1054E-9 );
        String _valueSelector13 = decimalScientificFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        // Megabyte  to Pebi byte
        // example Megabyte= 1 , Pebi_byte = Megabyte * 8.8818E-10;   result = 8.8818e-10
        double valueSelector14 =  (userValue1 * 8.8818E-10);
        String _valueSelector14 = decimalScientificFormat.format(valueSelector14);
        result14.setText(_valueSelector14);

    }

    private void Megabit_update() {
        // Megabit  to Bit
        // example Megabit= 1 , Bit = Megabit * 1048576;   result = 1048576
        int valueSelector1 = (int) (userValue1 * 1E+6);
        String _valueSelector1 = decimalScientificFormat.format(valueSelector1);
        result1.setText(_valueSelector1);
        // Megabit  to Byte
        // example Megabit= 1 , Byte = Megabit * 131072;   result = 131072
        int valueSelector2 = (int) (userValue1 * 125000);
        String _valueSelector2 = String.valueOf(valueSelector2);
        result2.setText(_valueSelector2);
        // Megabit  to Kilobyte
        // example Megabit= 1 , Kilobyte = Megabit * 125;   result = 125
        int valueSelector3 = (int) (userValue1 * 125);
        String _valueSelector3 = String.valueOf(valueSelector3);
        result3.setText(_valueSelector3);
        // Megabit  to Kilo  bit
        // example Megabit= 1 , Kilo_bit = Megabit * 1024;   result = 1024
        int valueSelector4 = (int) (userValue1 * 1000);
        String _valueSelector4 = String.valueOf(valueSelector4);
        result4.setText(_valueSelector4);
        // Megabit  to Megabit
        // example Megabit= 1 , Megabit = Megabit * 1;   result = 1
        int valueSelector5 = (int) (userValue1 * 1);
        String _valueSelector5 = String.valueOf(valueSelector5);
        result5.setText(_valueSelector5);
        // Megabit  to  Megabyte
        // example Megabit= 1 , Megabyte = Megabit * 0.125;   result = 0.125
        double valueSelector6 =  (userValue1 * 0.125);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Megabit  to Gigabit
        // example Megabit= 1 , Gigabit = Megabit * 0.0009765625;   result =0.0009765625
        double valueSelector7 =  (userValue1 * 0.001 );
        String _valueSelector7 = decimalFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // Megabit  to Gigabyte
        // example Megabit= 1 , Gigabyte = Megabit * 0.0001220703;   result = 0.0001220703
        double valueSelector8 =  (userValue1 * 0.000125);
        String _valueSelector8 = decimalFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // Megabit  to  TeraBit
        // example Megabit= 1 , TeraBit = Megabit * 9.536743164E-7;   result = 9.536743164E-7
        double valueSelector9 =  (userValue1 * 1E-6);
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Megabit  to Terabyte
        // example Megabit= 1 , Terabyte = Megabit * 1.192092895E-7;   result = 1.192092895E-7
        double valueSelector10 =  (userValue1 * 1.25E-7 );
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Megabit  to Peta bit
        // example Megabit= 1 , Peta_bit = Megabit * 9.313225746E-10;   result = 9.313225746E-10
        double valueSelector11 =  (userValue1 * 1E-9);
        String _valueSelector11 = decimalScientificFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Megabit  to  Peta byte
        // example Megabit= 1 , Bit = Megabit * 1.164153218E-10;   result = 1.164153218E-10
        double valueSelector12 =  (userValue1 * 1.25E-10);
        String _valueSelector12 = decimalScientificFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        // Megabit  to Pebi bit
        // example Megabit= 1 , Pebi_bit = Megabit *  8.8818E-10;   result = 8.8818e-10
        double valueSelector13 =  (userValue1 *  8.8818E-10 );
        String _valueSelector13 = decimalScientificFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        // Megabit  to Pebi byte
        // example Megabit= 1 , Bit = Megabit * 1.1102E-10;   result = 1.1102e-10
        double valueSelector14 =  (userValue1 * 1.1102E-10);
        String _valueSelector14 = decimalScientificFormat.format(valueSelector14);
        result14.setText(_valueSelector14);

    }

    private void Kilo_bit_update() {
        // Kilo_bit  to Bit
        // example Kilo_bit= 1 , Bit = Kilo_bit * 1000;   result = 1000
        int valueSelector1 = (int) (userValue1 * 1000);
        String _valueSelector1 = String.valueOf(valueSelector1);
        result1.setText(_valueSelector1);
        // Kilo_bit  to byte
        // example Kilo_bit= 1 , byte = Kilo_bit * 125;   result = 125
        int valueSelector2 = (int) (userValue1 * 125);
        String _valueSelector2 = String.valueOf(valueSelector2);
        result2.setText(_valueSelector2);
        // Kilo_bit  to Kilobyte
        // example Kilo_bit= 1 , Kilobyte = Kilo_bit* 0.125;   result = 0.125
        double valueSelector3 =  (userValue1 * 0.125);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Kilo_bit  to Kilo  bit
        // example Kilo_bit= 1 , Kilo_Bit = Kilo_bit * 1;   result = 1
        int valueSelector4 = (int) (userValue1 * 1);
        String _valueSelector4 = String.valueOf(valueSelector4);
        result4.setText(_valueSelector4);
        // Kilo_bit  to Megabit
        // example Kilo_bit= 1 , Megabit = Kilo_bit * 0.0009765625;   result = 0.0009765625
        double valueSelector5 =  (userValue1 * 0.001);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Kilo_bit  to  Megabyte
        // example Kilo_bit= 1 , Megabyte = Kilo_bit *0.0001220703;   result =0.0001220703
        double valueSelector6 =  (userValue1 * 0.000125);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Kilo_bit  to Gigabit
        // example Kilo_bit= 1 , Gigabit = Kilo_bit * 9.536743164E-7;   result = 9.536743164E-7
        double valueSelector7 =  (userValue1 * 1E-6 );
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // Kilo_bit  to Gigabyte
        // example Kilo_bit= 1 , Gigabyte = Kilo_bit * 1.192092895E-7;   result = 1.192092895E-7
        double valueSelector8 =  (userValue1 * 1.25E-7);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // Kilo_bit  to  TeraBit
        // example Kilo_bit= 1 , TeraBit = Kilo_bit * 9.313225746E-10;   result = 9.313225746E-10
        double valueSelector9 =  (userValue1 * 1e-9);
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Kilo_bit  to Terabyte
        // example Kilo_bit= 1 , Terabyte = Kilo_bit* 1.164153218E-10   result = 1.164153218E-10
        double valueSelector10 =  (userValue1 * 1.25E-10 );
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Kilo_bit  to Peta bit
        // example Kilo_bit= 1 , Peta_bit = Kilo_bit * 9.094947017E-13;   result = 9.094947017E-13
        double valueSelector11 =  (userValue1 * 1E-12);
        String _valueSelector11 = decimalScientificFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Kilo_bit  to  Peta byte
        // example Kilo_bit= 1 , Peta_byte = Kilo_bit * 1.136868377E-13;   result =1.136868377E-13
        double valueSelector12 =  (userValue1 * 1.25E-13);
        String _valueSelector12 = decimalScientificFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        // Kilo_bit  to Pebi bit
        // example Kilo_bit= 1 , Pebi_bit = Kilo_bit * 8.8818E-13;   result = 8.8818E-13;
        double valueSelector13 =  (userValue1 * 8.8818E-13 );
        String _valueSelector13 = decimalScientificFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        // Kilo_bit  to Pebi byte
        // example Kilo_bit= 1 , Pebi_byte = Kilo_bit * 1.1102E-13;   result = 1.1102e-13
        double valueSelector14 =  (userValue1 * 1.1102E-13);
        String _valueSelector14 = decimalScientificFormat.format(valueSelector14);
        result14.setText(_valueSelector14);


    }

    private void Kilobyte_update() {
        // Kilobyte  to Bit
        // example Kilobyte= 1 , Bit = Kilobyte * 8192;   result =8192
        int valueSelector1 = (int) (userValue1 * 8000);
        String _valueSelector1 = String.valueOf(valueSelector1);
        result1.setText(_valueSelector1);
        // Kilobyte  to Byte
        // example Kilobyte= 1 , Bit = Kilobyte *1024;   result = 1024
        int valueSelector2 = (int) (userValue1 * 1000);
        String _valueSelector2 = String.valueOf(valueSelector2);
        result2.setText(_valueSelector2);
        // Kilobyte  to Kilobyte
        // example Kilobyte= 1 , Bit = Kilobyte * 1e+12;   result = 1e+12
        int valueSelector3 = (int) (userValue1 * 1);
        String _valueSelector3 = String.valueOf(valueSelector3);
        result3.setText(_valueSelector3);
        // Kilobyte  to Kilo  bit
        // example Kilobyte= 1 , Bit = Kilobyte * 1e+12;   result = 1e+12
        int valueSelector4 = (int) (userValue1 * 8);
        String _valueSelector4 = String.valueOf(valueSelector4);
        result4.setText(_valueSelector4);
        // Kilobyte  to Megabit
        // example Kilobyte= 1 , Megabit = Kilobyte * 0.0078125;   result = 0.0078125
        double valueSelector5 =  (userValue1 * 0.008);
        String _valueSelector5 = decimalFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Kilobyte  to  Megabyte
        // example Kilobyte= 1 , Megabyte = Kilobyte * 0.0009765625;   result =0.0009765625
        double valueSelector6 =  (userValue1* 0.0001);
        String _valueSelector6 = decimalFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Kilobyte  to Gigabit
        // example Kilobyte= 1 , Gigabit = Kilobyte * 0.0000076294;   result = 0.0000076294
        double valueSelector7 =  (userValue1 * 8E-6 );
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // Kilobyte  to Gigabyte
        // example Kilobyte= 1 , Gigabyte = Kilobyte * 9.536743164E-7;   result =9.536743164E-7
        double valueSelector8 =  (userValue1 * 1E-6);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // Kilobyte  to  TeraBit
        // example Kilobyte= 1 , TeraBit = Kilobyte * 7.450580596E-9;   result = 7.450580596E-9
        double valueSelector9 =  (userValue1 * 8E-9);
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Kilobyte  to Terabyte
        // example Kilobyte= 1 , Terabyte = Kilobyte * 9.313225746E-10;   result = 9.313225746E-10
        double valueSelector10 =  (userValue1 * 1E-9 );
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Kilobyte  to Peta bit
        // example Kilobyte= 1 ,  Peta_bit = Kilobyte * 7.275957614E-12;   result = 7.275957614E-12
        double valueSelector11 =  (userValue1 * 8E-12 );
        String _valueSelector11 = decimalScientificFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Kilobyte  to  Peta byte
        // example Kilobyte= 1 , Peta_byte = Kilobyte* 9.094947017E-13;   result = 9.094947017E-13
        double valueSelector12 =  (userValue1* 1E-12);
        String _valueSelector12 = decimalScientificFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        // Kilobyte  to Pebi bit
        // example Kilobyte= 1 ,  Pebi_bit = Kilobyte *7.1054E-12;   result = 7.1054e-12
        double valueSelector13 =  (userValue1 * 7.1054E-12 );
        String _valueSelector13 = decimalScientificFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        // Kilobyte  to Pebi byte
        // example Kilobyte= 1 ,  Pebi_byte = Kilobyte* 8.8818E-13;   result = 8.8818e-13
        double valueSelector14 =  (userValue1 * 8.8818e-13);
        String _valueSelector14 = decimalScientificFormat.format(valueSelector14);
        result14.setText(_valueSelector14);


    }

    private void Byte_update() {
        // Byte  to Bit
        // example Byte= 1 , Bit = Byte * 8;   result = 8
        int valueSelector1 = (int) (userValue1 * 8);
        String _valueSelector1 = String.valueOf(valueSelector1);
        result1.setText(_valueSelector1);
        // Byte  to Byte
        // example Byte= 1 , Byte = Byte * 1;   result = 1
        int valueSelector2 = (int) (userValue1 * 1);
        String _valueSelector2 = String.valueOf(valueSelector2);
        result2.setText(_valueSelector2);
        // Byte  to Kilobyte
        // example Byte= 1 , Kilobyte = Byte *0.0009765625;   result = 0.0009765625
        double valueSelector3 =  (userValue1 * 0.001);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Byte  to Kilo  bit
        // example Byte= 1 , Kilo_bit = Byte *0.0078125;   result = 0.0078125
        double valueSelector4 =  (userValue1 * 0.008 );
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Byte  to Megabit
        // example Byte= 1 , Megabit = Byte * 0.0.0000076294;   result = 0.0.0000076294
        double valueSelector5 =  (userValue1 *8E-6);
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Byte  to  Megabyte
        // example Byte= 1 , Megabyte = Byte * 9.536743164E-7;   result = 9.536743164E-7
        double valueSelector6 =  (userValue1 * 1E-6);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Byte  to Gigabit
        // example Byte= 1 , Gigabit = Byte * 7.450580596E-9;   result =7.450580596E-9
        double valueSelector7 =  (userValue1 * 8E-9 );
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
        result7.setText(_valueSelector7);
        // Byte  to Gigabyte
        // example Byte= 1 , Gigabyte = Byte * 9.313225746E-10;   result = 9.313225746E-10
        double valueSelector8 =  (userValue1 * 1E-9);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // Byte  to  TeraBit
        // example Byte= 1 , TeraBit = Byte * 7.275957614E-12;   result = 7.275957614E-12
        double valueSelector9 =  (userValue1 * 8E-12);
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Byte  to Terabyte
        // example Byte= 1 , Terabyte = Byte * 9.094947017E-13;   result = 9.094947017E-13
        double valueSelector10 =  (userValue1 * 1E-12 );
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Byte  to Peta bit
        // example Byte= 1 ,  Peta_bit = Byte * 7.105427357E-15;   result = 7.105427357E-15
        double valueSelector11 =  (userValue1 * 8E-15);
        String _valueSelector11 = decimalScientificFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Byte  to  Peta byte
        // example Byte= 1 , Peta_byte = Byte *  8.881784197E-16;   result = 8.881784197E-16
        double valueSelector12 =  (userValue1 *  1E-15);
        String _valueSelector12 = decimalScientificFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        // Byte  to Pebi bit
        // example Byte= 1 ,  Pebi_bit = Byte * 7.1054e-15;   result = 7.1054e-15
        double valueSelector13 =  (userValue1 * 7.1054e-15 );
        String _valueSelector13 = decimalScientificFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        // Byte  to Pebi byte
        // example Byte= 1 , Pebi_byte = Byte * 8.8818e-16;   result =8.8818e-16
        double valueSelector14 =  (userValue1 * 8.8818e-16);
        String _valueSelector14 = decimalScientificFormat.format(valueSelector14);
        result14.setText(_valueSelector14);

    }


    private void  Bit_update() {
        // Bit  to Bit
        // example Bit= 1 , Bit = Bit * 1;   result = 1
        int valueSelector1 = (int) (userValue1 * 1);
        String _valueSelector1 = String.valueOf(valueSelector1);
        result1.setText(_valueSelector1);
        // Bit  to Byte
        // example Bit= 1 , Byte = Bit * 0.125;   result = 0.125
        double valueSelector2 =  (userValue1 * 0.125);
        String _valueSelector2 = decimalFormat.format(valueSelector2);
        result2.setText(_valueSelector2);
        // Bit  to Kilobyte
        // example Bit= 1 , Kilobyte = Bit * 0.0001220703;   result = 0.0001220703
        double valueSelector3 =  (userValue1 / 8000);
        String _valueSelector3 = decimalFormat.format(valueSelector3);
        result3.setText(_valueSelector3);
        // Bit  to Kilo  bit
        // example Bit= 1 , Kilo_bit = Bit * 0.0009765625;   result =0.0009765625
        double valueSelector4 =  (userValue1 * 0.001 );
        String _valueSelector4 = decimalFormat.format(valueSelector4);
        result4.setText(_valueSelector4);
        // Bit  to Megabit
        // example Bit= 1 , Megabit = Bit * 9.536743164E-7;   result =9.536743164E-7
        double valueSelector5 =  (userValue1 / 1E+6);
        String _valueSelector5 = decimalScientificFormat.format(valueSelector5);
        result5.setText(_valueSelector5);
        // Bit  to  Megabyte
        // example Bit= 1 , Megabyte = Bit* 1.192092895E-7;   result = 1.192092895E-7
        double valueSelector6 =  (userValue1 / 8e+6);
        String _valueSelector6 = decimalScientificFormat.format(valueSelector6);
        result6.setText(_valueSelector6);
        // Bit  to Gigabit
        // example Bit= 1 , Gigabit = Bit * 9.313225746E-10;   result = 9.313225746E-10
        double valueSelector7 =  (userValue1 * 1E-9 );
        String _valueSelector7 = decimalScientificFormat.format(valueSelector7);
//        String _valueSelector7 = String.format("%.16f", valueSelector7);
        result7.setText(_valueSelector7);
        // Bit  to Gigabyte
        // example Bit= 1 , Gigabyte = Bit * 1.164153218E-10;   result =1.164153218E-10
        double valueSelector8 =  (userValue1 * 1.25E-10);
        String _valueSelector8 = decimalScientificFormat.format(valueSelector8);
        result8.setText(_valueSelector8);
        // Bit  to  TeraBit
        // example Bit= 1 , TeraBit = Bit *9.094947017E-13;   result = 9.094947017E-13
        double valueSelector9 =  (userValue1 * 1.E-12);
        String _valueSelector9 = decimalScientificFormat.format(valueSelector9);
        result9.setText(_valueSelector9);
        // Bit  to Terabyte
        // example Bit= 1 , Terabyte = Bit * 1.136868377E-13;   result = 1.136868377E-13
        double valueSelector10 =  (userValue1 * 1.25E-13 );
        String _valueSelector10 = decimalScientificFormat.format(valueSelector10);
        result10.setText(_valueSelector10);
        // Bit  to Peta bit
        // example Bit= 1 , Bit = Bit * 8.881784197E-16;   result = 8.881784197E-16
        double valueSelector11 =  (userValue1 / 1E+15);
        String _valueSelector11 = decimalScientificFormat.format(valueSelector11);
        result11.setText(_valueSelector11);
        // Bit  to  Peta byte
        // example Bit= 1 , Bit = Bit *1.110223024E-16;   result = 1.110223024E-16
        double valueSelector12 =  (userValue1 * 1.25E-16);
        String _valueSelector12 = decimalScientificFormat.format(valueSelector12);
        result12.setText(_valueSelector12);
        // Bit  to Pebi bit
        // example Bit= 1 , Pebi_bit = Bit * 8.8818e-16;   result = 8.8818e-16
        double valueSelector13 =  (userValue1 * 8.8818e-16);
        String _valueSelector13 = decimalScientificFormat.format(valueSelector13);
        result13.setText(_valueSelector13);
        // Bit  to Pebi byte
        // example Bit= 1 ,  Pebi_byte = Bit * 1.1102e-16;   result = 1.1102e-16
        double valueSelector14 =  (userValue1 * 1.1102e-16);
        String _valueSelector14 = decimalScientificFormat.format(valueSelector14);
        result14.setText(_valueSelector14);


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_menu,menu);
        return true;
    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){

        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        // toolbar onOptionItemSelected
        if (item.getItemId() == R.id.action_share) {
            shareUpdate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareUpdate() {
        // current activity string are copied to another set of string that are made available for the Share intent

        String activity_result;
        activity_result =result1.getText().toString();
        String activity_result2;
        activity_result2 =result2.getText().toString();
        String activity_result3;
        activity_result3 = result3.getText().toString();
        String activity_result4;
        activity_result4 = result4.getText().toString();
        String activity_result5;
        activity_result5 = result5.getText().toString();
        String activity_result6;
        activity_result6 = result6.getText().toString();
        String activity_result7;
        activity_result7 = result7.getText().toString();
        String activity_result8;
        activity_result8 = result8.getText().toString();
        String activity_result9;
        activity_result9 = result9.getText().toString();
        String activity_result10;
        activity_result10 = result10.getText().toString();
        String activity_result11;
        activity_result11 = result11.getText().toString();
        String activity_result12;
        activity_result12 = result12.getText().toString();
        String activity_result13;
        activity_result13 = result13.getText().toString();
        String activity_result14;
        activity_result14 = result14.getText().toString();
        String User_enter_data;
        User_enter_data = UserInput.getText().toString();

        // above declared string are exported to share activity
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody =   "Results based on your input for "+ User_enter_data +" "+selectedItem + "\n"
                + "Bit (b) : " + activity_result  + "\n"
                + "Byte (B) : " + activity_result2 + "\n"
                + "Kilobyte (kB) : " + activity_result3 + "\n"
                + "Kilo bit (kb):" + activity_result4+ "\n"
                + "Megabit (Mb): " + activity_result5 + "\n"
                + "Megabyte (MB): " + activity_result6 + "\n"
                + "Gigabit (Gb):" + activity_result7+ "\n"
                + "Gigabyte (GB): " + activity_result8 + "\n"
                + "Tera bit (Tb) : " + activity_result9 + "\n"
                + "Terabyte (TB):" + activity_result10+ "\n"
                + "Peta bit (Pb) : " + activity_result11+ "\n"
                + "Peta byte (PB): " + activity_result12 + ".\n"
                + "Pebi bit (Pib): " + activity_result13+ "\n"
                + "Pebi byte (PiB): " + activity_result14  + "\n";

        String shareSub = "Digital Storage Converter Results Powered by Transpose Solutions Android App - Unit Converter ";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
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
        } else if (id == R.id.menu_area) {
            Intent intent = new Intent(this, Area.class);
            startActivity(intent);
        } else if (id == R.id.menu_currency) {
            Intent intent = new Intent(this, Currency.class);
            startActivity(intent);
        }else if (id == R.id.menu_Digital_Storage) {
            Intent intent = new Intent(this, DigitalStorage.class);
            startActivity(intent);
        } else if (id == R.id.menu_energy) {
            Intent intent = new Intent(this, Energy.class);
            startActivity(intent);
        } else if (id == R.id.menu_force) {
            Intent intent = new Intent(this, Force.class);
            startActivity(intent);
        } else if (id == R.id.menu_Fuel_Economy) {
            Intent intent = new Intent(this, FuelEconomy.class);
            startActivity(intent);
        } else if (id == R.id.menu_frequency) {
            Intent intent = new Intent(this, Frequency.class);
            startActivity(intent);
        } else if (id == R.id.Length) {
            Intent intent = new Intent(this, Length.class);
            startActivity(intent);
        } else if (id == R.id.Mass) {
            Intent intent = new Intent(this, Mass.class);
            startActivity(intent);
        } else if (id == R.id.Plane_Angle) {
            Intent intent = new Intent(this, PlaneAngle.class);
            startActivity(intent);
        } else if (id == R.id.menu_power) {
            Intent intent = new Intent(this, Power.class);
            startActivity(intent);
        } else if (id == R.id.Pressure) {
            Intent intent = new Intent(this, Pressure.class);
            startActivity(intent);
        } else if (id == R.id.Speed) {
            Intent intent = new Intent(this, Speed.class);
            startActivity(intent);
        }
        else if (id == R.id.Temperature) {
            Intent intent = new Intent(this, Temperature.class);
            startActivity(intent);
        } else if (id == R.id.Time) {
            Intent intent = new Intent(this, Time.class);
            startActivity(intent);
        } else if (id == R.id.Volume) {
            Intent intent = new Intent(this, Volume.class);
            startActivity(intent);
        }else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, AppSettings.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_rate) {
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
            String shareBody = "Check out this great Unit Converter app. This app helps you to convert common units of measurement" +  "\n"+  "\n"
                    + "Google Play store:" +  "\n" +"https://play.google.com/store/apps/details?id=com.transposesolutions.unitconverter&hl=en" + "\n";
            String shareSub = "Check out this great Unit Converter app from Transpose Solutions";
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