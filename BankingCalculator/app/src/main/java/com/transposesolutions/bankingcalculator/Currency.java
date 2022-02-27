package com.transposesolutions.bankingcalculator;

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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Currency extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener  {
    // declare instance variables required for the navigation drawer
    private ActionBarDrawerToggle mToggle;
    private AdView adView;
    TextView conversionRate;
    TextView result1, result2, result3, result4, result5, result6, result7, result8,result9,result10;
    Spinner spinnerFrom, spinnerTo;
    EditText amountToConvertEditText;
   //Declare spinnerFrom Drop Down List items
    String convertFromValue="USD",convertToValue="EUR",conversionValue;
    String[] fromCountries ={"USD", "AED", "AFN", "ALL", "AMD", "AOA", "ARS", "AUD", "AWG", "AZN",
            "BAM", "BBD", "BDT", "BGN", "BHD", "BND", "BOB", "BRL", "BTC", "BWP",
            "BYN", "CAD", "CHF", "CLP", "CNY", "COP", "CRC", "CUP", "CZK", "DKK",
            "DOP", "DZD", "EGP", "ETB", "EUR", "FJD", "GBP", "GEL", "GHS", "GMD",
            "GTQ", "GYD","HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS","INR",
            "IQD", "IRR", "ISK", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KRW",
            "KWD", "KZT", "LAK", "LBP", "LKR", "LYD", "MAD","MDL", "MGA", "MKD",
            "MMK", "MNT", "MOP", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD",
            "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PLN",
            "PHP","PKR", "PYG", "QAR", "RON", "RSD", "RUB", "SAR", "SCR", "SEK", "SGD",
            "SYP", "THB", "TJS", "TND", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX",
            "UYU", "UZS", "VEF","VND", "XAF", "XAU", "XCD", "XOF", "XPF", "YER",
            "ZAR", "ZMW"};
    // Declare spinnerFrom drop down images
    int[] fromImages = { R.drawable.usd,R.drawable.united_arab_emirates_uaee_aed,R.drawable.afghanistan, R.drawable.albania_all, R.drawable.armenian_amd,R.drawable.angola_aoa,R.drawable.argentina_ars,R.drawable.australia, R.drawable.aruba_awg, R.drawable.azerbaijan_azn,
            R.drawable.bosnia_h_mark_bam,R.drawable.barbados_bbd,R.drawable.bangladesh_bdt, R.drawable.bulgaria_bgn, R.drawable.bahrain_bhd, R.drawable.brunei_bnd,R.drawable.bolivia_bob,R.drawable.brazil_brl, R.drawable.bitcoin_btc, R.drawable.botswana_bwp,
            R.drawable.belarus_byn,R.drawable.canada,R.drawable.chf, R.drawable.chilean_clp, R.drawable.china_cny, R.drawable.colombia_cop,R.drawable.costa_rica_crc,R.drawable.cuban_cup, R.drawable.czech_czk, R.drawable.denmark_dkk,
            R.drawable.dominican_dop,R.drawable.algeria_dzd,R.drawable.egypt_egp, R.drawable.ethiopia_etb, R.drawable.euro, R.drawable.fijian_fjd,R.drawable.gbp,R.drawable.georgia_gel, R.drawable.ghana_ghs, R.drawable.gambia_gmd,
            R.drawable.guatemala_gtq,R.drawable.guyana_gyd,R.drawable.hongkong_hkd, R.drawable.honduras_hnl, R.drawable.croatia_hrk, R.drawable.haiti_htg,R.drawable.hungary_huf,R.drawable.indonesia_idr, R.drawable.israel_ils, R.drawable.india,
            R.drawable.iraq_iqd,R.drawable.iran_irr,R.drawable.iceland_isk, R.drawable.jamaica_jmd, R.drawable.jordan_jod, R.drawable.japan,R.drawable.kenya_kes,R.drawable.kyrgyzstan_kgs, R.drawable.cambodia_khr, R.drawable.south_korea_krw,
            R.drawable.kuwait_kwd,R.drawable.kazakhstan_kzt,R.drawable.laos_lak, R.drawable.lebanon_lbp, R.drawable.sri_lanka_lkr, R.drawable.libya_lyd,R.drawable.morocco_mad,R.drawable.moldova_mdl, R.drawable.mga, R.drawable.macedonia_mkd,
            R.drawable.myanmar_mmk,R.drawable.mongolia_mnt,R.drawable.macau_mop, R.drawable.mauritius_mur, R.drawable.maldives_mvr, R.drawable.malawi_mwk,R.drawable.mxn,R.drawable.malaysia_myr, R.drawable.mozambique_mzn, R.drawable.namibia_nad,
            R.drawable.nigeria_ngn,R.drawable.nicaragua_nio,R.drawable.norway_nok, R.drawable.nepal_npr, R.drawable.new_zealand_nzd, R.drawable.oman_omr,R.drawable.panama_pab,R.drawable.peru_pen, R.drawable.papua_new_guinea_pgk, R.drawable.poland_pln,
            R.drawable.philippines_php, R.drawable.pakistan_pkr,R.drawable.paraguay_pyg,R.drawable.qatar_qar, R.drawable.romania_ron, R.drawable.serbia_rsd, R.drawable.rub_russia,R.drawable.saudi_arabia_sar,R.drawable.seychelles_scr, R.drawable.sweden_sek, R.drawable.singapore_sgd,
            R.drawable.syria_syp,R.drawable.thailand_thb,R.drawable.tajikistan_tjs, R.drawable.tunisia_tnd, R.drawable.turkey_try, R.drawable.trinidad_ttd,R.drawable.taiwan_twd,R.drawable.tanzania_tzs, R.drawable.ukraine_uah, R.drawable.uganda_ugx,
            R.drawable.uruguay_uyu,R.drawable.uzbekistan_uzs,R.drawable.venezuela_vef, R.drawable.vietnam_vnd, R.drawable.central_african_xaf, R.drawable.gold_xau,R.drawable.xcd,R.drawable.xof, R.drawable.xpf, R.drawable.yemen_yer,
            R.drawable.south_africa_zar, R.drawable.zambia_zmw,
    };

    //Declare spinnerFrom Drop Down List items
    String[] fromCountriesName = {"United State Dollar","UAE Dirham","Afghan Afghani","Albanian Lek","Armenian Dram",
            "Angolan Kwanza", "Argentine Peso","Australian Dollar", "Aruban Guilder","Azerbaijani New Manat",
            "Bosnia-H mark","Barbadian Dollar","Bangladeshi Taka","Bulgarian Lev","Bahraini Dinar",
            "Brunei Dollar", "Bolivian Boliviano","Brazilian Real","Bitcoin","Botswana Pula",
            "New Belarusian Ruble","Canadian Dollar","Swiss Franc","Chilean Peso", "Chinese Yuan",
            "Colombian Peso","Costa Rican Colon","Cuban Peso","Czezh Koruna","Danish Krone",
            "Dominican Peso","Algarian Dinar","Engyptian Pound", "Ethiopian Birr","Euro",
            "Fijian Dollar", "British Pound Sterling","Georgian Lari","Ghana Cedi","Gambian Dalasi",
            "Guatemalan Quetzal", "Guyanese Dollar", "Hong Kong Dollar","Honduran Lempira","Croatian Kura",
            "Haitian Gourde", "Hungarian Rupiah","Indonesian Rupiah","Israeli Shekel","Indian Rupee",
            "Iraqi Dinar", "Iranian Rial","Icelandic Krona","Jamaican Dollar","Jordanian Dinar",
            "Japanes Yen", "Kenyan Shilling", "Kyrgyzstani Som","Combidian Riel","South Korean Won" ,
            "Kuwaiti Dinar", "Kazakhstani Tenge", "Lao Kip","Lebanese Pound","Sri Lankan Rupees",
            "Libyan Diner", "Moroccan Dirham", "Moldovan Leu","Malagasy Ariary","Macedonian Denar",
            "Myanmar Kyat", "Mongolian Tugrik", "Macanese Pataca","Mauritian Rupee", "Maldivian Rufiyaa","Malawian Kwacha" ,
            "Mexican Peso", "Malaysian Ringgit", "Mozambican metical","Namibian Dollar","Nigerian Naira",
            "Nicaraguan Cordoba", "Norwegian Krone", "Nepalese Rupee","New Zealand Dollar", "Omani Rial",
            "Panamanian Balboa", "Peruvian Nuevo Sol", "Papua New Guinea Kina","Polish Zloty","Philippine Peso","Pakistani Rupee",
            "Paraguayan Guarani", "Qatari Riyal","Romanian Leu","Serbian Diner",
            "Russian Ruble", "Saudi Riyal", "Seychellois Rupee","Swedish Krona","Singapore Dollar",
            "Syrian Pound", "Thai Baht", "Tajikistani Somoni","Tunisian Diner","Turkish Lira",
            "Trinidadian Dollar", "Taiwanese Dollar", "Tanzanian shilling","Ukrainian Hryvnia","Ugandan Shilling",
            "Uruguayan Peso", "Uzbekistani Som","Venezuelan Bolivar","Vietnamese Dong","Central Africa Franc",
            "Gold Ounce", "East Caribbean Dollar","West African Franc","CFP Franc","Yemeni Rial",
            "South african Rand", "Zambian Kwacha" };


    //Declare spinnerTo Drop Down List items
    String[] toCountries ={
            "EUR", "AED", "AFN", "ALL", "AMD", "AOA", "ARS", "AUD", "AWG", "AZN",
            "BAM", "BBD", "BDT", "BGN", "BHD", "BND", "BOB", "BRL", "BTC", "BWP",
            "BYN", "CAD", "CHF", "CLP", "CNY", "COP", "CRC", "CUP", "CZK", "DKK",
            "DOP", "DZD", "EGP", "ETB", "FJD", "GBP", "GEL", "GHS", "GMD", "GTQ",
            "GYD","HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS","INR", "IQD",
            "IRR", "ISK", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KRW", "KWD",
            "KZT", "LAK", "LBP", "LKR", "LYD", "MAD","MDL", "MGA", "MKD", "MMK",
            "MNT", "MOP", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD", "NGN",
            "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PLN","PHP", "PKR",
            "PYG", "QAR", "RON", "RSD", "RUB", "SAR", "SCR", "SEK", "SGD", "SYP",
            "THB", "TJS", "TND", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX","USD",
            "UYU", "UZS", "VEF","VND", "XAF", "XAU", "XCD", "XOF", "XPF", "YER",
            "ZAR", "ZMW"};

    //Declare spinnerTo Drop Down images
    int[] toImages = { R.drawable.euro,R.drawable.united_arab_emirates_uaee_aed,R.drawable.afghanistan, R.drawable.albania_all, R.drawable.armenian_amd,
            R.drawable.angola_aoa,R.drawable.argentina_ars,R.drawable.australia, R.drawable.aruba_awg, R.drawable.azerbaijan_azn,
            R.drawable.bosnia_h_mark_bam,R.drawable.barbados_bbd,R.drawable.bangladesh_bdt, R.drawable.bulgaria_bgn, R.drawable.bahrain_bhd,
            R.drawable.brunei_bnd,R.drawable.bolivia_bob,R.drawable.brazil_brl, R.drawable.bitcoin_btc, R.drawable.botswana_bwp,
            R.drawable.belarus_byn,R.drawable.canada,R.drawable.chf, R.drawable.chilean_clp, R.drawable.china_cny,
            R.drawable.colombia_cop,R.drawable.costa_rica_crc,R.drawable.cuban_cup, R.drawable.czech_czk, R.drawable.denmark_dkk,
            R.drawable.dominican_dop,R.drawable.algeria_dzd,R.drawable.egypt_egp, R.drawable.ethiopia_etb, R.drawable.fijian_fjd,
            R.drawable.gbp,R.drawable.georgia_gel, R.drawable.ghana_ghs, R.drawable.gambia_gmd, R.drawable.guatemala_gtq,
            R.drawable.guyana_gyd,R.drawable.hongkong_hkd, R.drawable.honduras_hnl, R.drawable.croatia_hrk, R.drawable.haiti_htg,
            R.drawable.hungary_huf,R.drawable.indonesia_idr, R.drawable.israel_ils, R.drawable.india, R.drawable.iraq_iqd,
            R.drawable.iran_irr,R.drawable.iceland_isk, R.drawable.jamaica_jmd, R.drawable.jordan_jod, R.drawable.japan,
            R.drawable.kenya_kes,R.drawable.kyrgyzstan_kgs, R.drawable.cambodia_khr, R.drawable.south_korea_krw, R.drawable.kuwait_kwd,
            R.drawable.kazakhstan_kzt,R.drawable.laos_lak, R.drawable.lebanon_lbp, R.drawable.sri_lanka_lkr, R.drawable.libya_lyd,
            R.drawable.morocco_mad,R.drawable.moldova_mdl, R.drawable.mga, R.drawable.macedonia_mkd, R.drawable.myanmar_mmk,
            R.drawable.mongolia_mnt,R.drawable.macau_mop, R.drawable.mauritius_mur, R.drawable.maldives_mvr, R.drawable.malawi_mwk,
            R.drawable.mxn,R.drawable.malaysia_myr, R.drawable.mozambique_mzn, R.drawable.namibia_nad, R.drawable.nigeria_ngn,
            R.drawable.nicaragua_nio,R.drawable.norway_nok, R.drawable.nepal_npr, R.drawable.new_zealand_nzd, R.drawable.oman_omr,
            R.drawable.panama_pab,R.drawable.peru_pen, R.drawable.papua_new_guinea_pgk, R.drawable.poland_pln,R.drawable.philippines_php, R.drawable.pakistan_pkr,
            R.drawable.paraguay_pyg, R.drawable.qatar_qar, R.drawable.romania_ron, R.drawable.serbia_rsd, R.drawable.rub_russia,
            R.drawable.saudi_arabia_sar,R.drawable.seychelles_scr, R.drawable.sweden_sek, R.drawable.singapore_sgd, R.drawable.syria_syp,
            R.drawable.thailand_thb,R.drawable.tajikistan_tjs, R.drawable.tunisia_tnd, R.drawable.turkey_try, R.drawable.trinidad_ttd,
            R.drawable.taiwan_twd,R.drawable.tanzania_tzs, R.drawable.ukraine_uah, R.drawable.uganda_ugx,R.drawable.usd,
            R.drawable.uruguay_uyu, R.drawable.uzbekistan_uzs,R.drawable.venezuela_vef, R.drawable.vietnam_vnd, R.drawable.central_african_xaf,
            R.drawable.gold_xau, R.drawable.xcd,R.drawable.xof, R.drawable.xpf, R.drawable.yemen_yer,
            R.drawable.south_africa_zar, R.drawable.zambia_zmw,
    };

    //Declare spinnerTo Drop Down List items
    String[] toCountriesName = {"Euro","UAE Dirham","Afghan Afghani","Albanian Lek","Armenian Dram",
            "Angolan Kwanza", "Argentine Peso","Australian Dollar", "Aruban Guilder","Azerbaijani New Manat",
            "Bosnia-H mark","Barbadian Dollar","Bangladeshi Taka","Bulgarian Lev","Bahraini Dinar",
            "Brunei Dollar", "Bolivian Boliviano","Brazilian Real","Bitcoin","Botswana Pula",
            "New Belarusian Ruble","Canadian Dollar","Swiss Franc","Chilean Peso", "Chinese Yuan",
            "Colombian Peso","Costa Rican Colon","Cuban Peso","Czezh Koruna","Danish Krone",
            "Dominican Peso","Algarian Dinar","Engyptian Pound", "Ethiopian Birr","Fijian Dollar",
            "British Pound Sterling","Georgian Lari","Ghana Cedi","Gambian Dalasi", "Guatemalan Quetzal",
            "Guyanese Dollar", "Hong Kong Dollar","Honduran Lempira","Croatian Kura","Haitian Gourde",
            "Hungarian Forint","Indonesian Rupiah","Israeli Shekel","Indian Rupee", "Iraqi Dinar",
            "Iranian Rial","Icelandic Krona","Jamaican Dollar","Jordanian Dinar","Japanes Yen",
            "Kenyan Shilling", "Kyrgyzstani Som","Combidian Riel","South Korean Won" ,"Kuwaiti Dinar",
            "Kazakhstani Tenge", "Lao Kip","Lebanese Pound","Sri Lankan Rupees","Libyan Diner",
            "Moroccan Dirham", "Moldovan Leu","Malagasy Ariary","Macedonian Denar","Myanmar Kyat",
            "Mongolian Tugrik", "Macanese Pataca","Mauritian Rupee", "Maldivian Rufiyaa","Malawian Kwacha","Mexican Peso",
            "Malaysian Ringgit", "Mozambican metical","Namibian Dollar","Nigerian Naira","Nicaraguan Cordoba",
            "Norwegian Krone", "Nepalese Rupee","New Zealand Dollar", "Omani Rial","Panamanian Balboa",
            "Peruvian Nuevo Sol", "Papua New Guinea Kina","Polish Zloty","Philippine Peso","Pakistani Rupee",
            "Paraguayan Guarani", "Qatari Riyal","Romanian Leu","Serbian Diner","Russian Ruble",
            "Saudi Riyal", "Seychellois Rupee","Swedish Krona","Singapore Dollar","Syrian Pound",
            "Thai Baht", "Tajikistani Somoni","Tunisian Diner","Turkish Lira","Trinidadian Dollar",
            "Taiwanese Dollar", "Tanzanian shilling","Ukrainian Hryvnia","Ugandan Shilling","United State Dollar",
            "Uruguayan Peso", "Uzbekistani Som","Venezuelan Bolivar","Vietnamese Dong","Central Africa Franc",
            "Gold Ounce", "East Caribbean Dollar","West African Franc","CFP Franc","Yemeni Rial",
            "South african Rand", "Zambian Kwacha" };

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
            if (editable.length() == 0) {
                conversionRate.setText("");


            } else {
                OnLoad();
//                OnMajorCurrencyUpdate();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

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
       //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spinnerFrom =  findViewById(R.id.convert_from_dropdown_menu);
        spinnerTo =  findViewById(R.id.convert_to_dropdown_menu);
      //conversionButton = findViewById(R.id.conversionButton);
        conversionRate = findViewById(R.id.conversionResult);
        amountToConvertEditText = findViewById(R.id.amountConvertEditText);
        result1 = findViewById(R.id.Result_1);
        result2 = findViewById(R.id.Result_2);
        result3 = findViewById(R.id.Result_3);
        result4 = findViewById(R.id.Result_4);
        result5 = findViewById(R.id.Result_5);
        result6 = findViewById(R.id.Result_6);
        result7 = findViewById(R.id.Result_7);
        result8 = findViewById(R.id.Result_8);
        result9 = findViewById(R.id.Result_9);
        result10 = findViewById(R.id.Result_10);
        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),fromImages,fromCountries,fromCountriesName);
        CustomAdapter customAdapter1=new CustomAdapter(getApplicationContext(),toImages,toCountries,toCountriesName);
        spinnerFrom.setAdapter(customAdapter);
        spinnerTo.setAdapter(customAdapter1);

        // to perform the default logic on create
        int defaultValue1 = 100;
        String displayValue1 = String.valueOf(defaultValue1);
        amountToConvertEditText.setText(displayValue1);
        amountToConvertEditText.addTextChangedListener(mTextWatcher);
        OnLoad();
        OnMajorCurrencyUpdate();
        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertFromValue = fromCountries[position];
                OnLoad();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertToValue = toCountries[position];
                OnLoad();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    private void OnMajorCurrencyUpdate() {
        try {
            int amountToConvert = 1;
            String convertFrom = "USD";
            String convertTO ="EUR";
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://free.currconv.com/api/v7/convert?q="+convertFrom+"_"+convertTO+"&compact=ultra&apiKey=c344be763a9c4cd858df";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double ConversionRateValue = round(((Double) jsonObject.get(convertFrom + "_" + convertTO)), 2);
                    conversionValue = "" + round((ConversionRateValue * amountToConvert),  2);
                    result1.setText(conversionValue);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
                if (error instanceof NetworkError) {


                    Toast.makeText(Currency.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }

                else if (error instanceof ServerError) {
                    Toast.makeText(Currency.this, "Our server is busy please try again later", Toast.LENGTH_SHORT).show();
                }

                else if (error instanceof AuthFailureError) {
                    Toast.makeText(Currency.this, "AuthFailure Error please try again later", Toast.LENGTH_SHORT).show();
                }

                else if (error instanceof ParseError) {
                    Toast.makeText(Currency.this, "Parse Error please try again later", Toast.LENGTH_SHORT).show();
                }

                else if (error instanceof NoConnectionError) {
                    Toast.makeText(Currency.this, "No connection", Toast.LENGTH_SHORT).show();
                }

                else if (error instanceof TimeoutError) {
                    Toast.makeText(Currency.this, "Server time out please try again later", Toast.LENGTH_SHORT).show();
                }error.printStackTrace();

            });
            queue.add(stringRequest);
            String convertToJpy ="JPY";
            RequestQueue queue1 = Volley.newRequestQueue(this);
            String url1 = "https://free.currconv.com/api/v7/convert?q="+convertFrom+"_"+convertToJpy+"&compact=ultra&apiKey=c344be763a9c4cd858df";
            StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1, response -> {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double ConversionRateValue = round(((Double) jsonObject.get(convertFrom + "_" + convertToJpy)), 2);
                    conversionValue = "" + round((ConversionRateValue * amountToConvert),  2);
                    result2.setText(conversionValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {

            });
            queue1.add(stringRequest1);
            String convertToGbp ="GBP";
            RequestQueue queue2 = Volley.newRequestQueue(this);
            String url2 = "https://free.currconv.com/api/v7/convert?q="+convertFrom+"_"+convertToGbp+"&compact=ultra&apiKey=c344be763a9c4cd858df";
            StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, response -> {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double ConversionRateValue = round(((Double) jsonObject.get(convertFrom + "_" + convertToGbp)), 2);
                    conversionValue = "" + round((ConversionRateValue * amountToConvert),  2);
                    result3.setText(conversionValue);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {

            });
            queue2.add(stringRequest2);
            String convertToCad ="CAD";
            RequestQueue queue3 = Volley.newRequestQueue(this);
            String url3 = "https://free.currconv.com/api/v7/convert?q="+convertFrom+"_"+convertToCad+"&compact=ultra&apiKey=c344be763a9c4cd858df";
            StringRequest stringRequest3 = new StringRequest(Request.Method.GET, url3, response -> {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double ConversionRateValue = round(((Double) jsonObject.get(convertFrom + "_" + convertToCad)), 2);
                    conversionValue = "" + round((ConversionRateValue * amountToConvert),  2);
                    result4.setText(conversionValue);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {

            });
            queue3.add(stringRequest3);
            String convertToCny ="CNY";
            RequestQueue queue4 = Volley.newRequestQueue(this);
            String url4 = "https://free.currconv.com/api/v7/convert?q="+convertFrom+"_"+convertToCny+"&compact=ultra&apiKey=c344be763a9c4cd858df";
            StringRequest stringRequest4 = new StringRequest(Request.Method.GET, url4, response -> {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double ConversionRateValue = round(((Double) jsonObject.get(convertFrom + "_" + convertToCny)), 2);
                    conversionValue = "" + round((ConversionRateValue * amountToConvert),  2);
                    result5.setText(conversionValue);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
                // As of f605da3 the following should work


            });
            queue4.add(stringRequest4);
            String aud ="AUD";
            RequestQueue queue5 = Volley.newRequestQueue(this);
            String url5 = "https://free.currconv.com/api/v7/convert?q="+convertFrom+"_"+aud+"&compact=ultra&apiKey=c344be763a9c4cd858df";
            StringRequest stringRequest5 = new StringRequest(Request.Method.GET, url5, response -> {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double ConversionRateValue = round(((Double) jsonObject.get(convertFrom + "_" + aud)), 2);
                    conversionValue = "" + round((ConversionRateValue * amountToConvert),  2);
                    result6.setText(conversionValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {

            });
            queue5.add(stringRequest5);
            String convertToHkd ="HKD";
            RequestQueue queue6 = Volley.newRequestQueue(this);
            String url6 = "https://free.currconv.com/api/v7/convert?q="+convertFrom+"_"+convertToHkd+"&compact=ultra&apiKey=c344be763a9c4cd858df";
            StringRequest stringRequest6 = new StringRequest(Request.Method.GET, url6, response -> {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double ConversionRateValue = round(((Double) jsonObject.get(convertFrom + "_" + convertToHkd)), 2);
                    conversionValue = "" + round((ConversionRateValue * amountToConvert),  2);
                    result7.setText(conversionValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {

            });
            queue6.add(stringRequest6);
            String convertToInd ="INR";
            RequestQueue queue7 = Volley.newRequestQueue(this);
            String url7 = "https://free.currconv.com/api/v7/convert?q="+convertFrom+"_"+convertToInd+"&compact=ultra&apiKey=c344be763a9c4cd858df";
            StringRequest stringRequest7 = new StringRequest(Request.Method.GET, url7, response -> {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double ConversionRateValue = round(((Double) jsonObject.get(convertFrom + "_" + convertToInd)), 2);
                    conversionValue = "" + round((ConversionRateValue * amountToConvert),  2);
                    result8.setText(conversionValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {

            });
            queue7.add(stringRequest7);
            String convertToRub ="RUB";
            RequestQueue queue8 = Volley.newRequestQueue(this);
            String url8 = "https://free.currconv.com/api/v7/convert?q="+convertFrom+"_"+convertToRub+"&compact=ultra&apiKey=c344be763a9c4cd858df";
            StringRequest stringRequest8 = new StringRequest(Request.Method.GET, url8, response -> {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double ConversionRateValue = round(((Double) jsonObject.get(convertFrom + "_" + convertToRub)), 2);
                    conversionValue = "" + round((ConversionRateValue * amountToConvert),  2);
                    result9.setText(conversionValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {

            });
            queue8.add(stringRequest8);
            String convertToKrw ="KRW";
            RequestQueue queue9 = Volley.newRequestQueue(this);
            String url9 = "https://free.currconv.com/api/v7/convert?q="+convertFrom+"_"+convertToKrw+"&compact=ultra&apiKey=c344be763a9c4cd858df";
            StringRequest stringRequest9 = new StringRequest(Request.Method.GET, url9, response -> {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double ConversionRateValue = round(((Double) jsonObject.get(convertFrom + "_" + convertToKrw)), 2);
                    conversionValue = "" + round((ConversionRateValue * amountToConvert),  2);
                    result10.setText(conversionValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {

            });
            queue9.add(stringRequest9);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void OnLoad() {
        try{
            Double amountToConvert = Double.valueOf(Currency.this.amountToConvertEditText.getText().toString());
            getConversionRate(convertFromValue,convertToValue,amountToConvert);

        }catch (Exception e){
        }
    }
    private void getConversionRate(String convertFromValue, String convertToValue, Double amountToConvert) {

        RequestQueue queue = Volley.newRequestQueue(this);
        if (convertFromValue.equals(convertToValue)) {
            String value = String.valueOf(amountToConvert);
            conversionRate.setText(value);
        } else {
            String url = "https://free.currconv.com/api/v7/convert?q=" + convertFromValue + "_" + convertToValue + "&compact=ultra&apiKey=c344be763a9c4cd858df";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double ConversionRateValue = round(((Double) jsonObject.get(convertFromValue + "_" + convertToValue)), 2);
                    conversionValue = "" + round((ConversionRateValue * amountToConvert), 2);
                    conversionRate.setText(conversionValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {

                if (error instanceof NetworkError) {
                    Toast.makeText(Currency.this, "No internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Currency.this, "Our server is busy please try again later", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Currency.this, "AuthFailure Error please try again later", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Currency.this, "Parse Error please try again later", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Currency.this, "No connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(Currency.this, "Server time out please try again later", Toast.LENGTH_SHORT).show();
                }
                error.printStackTrace();

//                Log.i("VolleyError ", "Error: " + error.networkResponse.statusCode);
            });
            queue.add(stringRequest);
        }
    }

    private Double round(Double aDouble, int i) {
        if(i<0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(aDouble);
        bd = bd.setScale(i, RoundingMode.HALF_UP);
        return bd.doubleValue();
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