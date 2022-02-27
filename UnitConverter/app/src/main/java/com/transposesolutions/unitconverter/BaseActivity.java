package com.transposesolutions.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

public class BaseActivity extends AppCompatActivity {
    private final static int THEME_BLUE = 2;
    private final static int THEME_GREEN = 1;
    private final static int THEME_DARK = 3;
    private final static int THEME_BROWN = 4;
    private final static int THEME_GRAY = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateTheme();
    }
    public void updateTheme() {
        if (Utility.getTheme(getApplicationContext()) <= THEME_GREEN) {
            setTheme(R.style.Theme_green);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(getResources().getColor(R.color.dark_green));
            }
        } else if (Utility.getTheme(getApplicationContext()) == THEME_BLUE) {
            setTheme(R.style.AppTheme_Blue);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.primaryColorDark_blue));
        }else if (Utility.getTheme(getApplicationContext()) == THEME_DARK) {
            setTheme(R.style.AppTheme_Black);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.lightDark));
        }else if (Utility.getTheme(getApplicationContext()) == THEME_BROWN) {
            setTheme(R.style.AppTheme_Brown);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.brown));
        }else if (Utility.getTheme(getApplicationContext()) == THEME_GRAY) {
            setTheme(R.style.AppTheme_Gray);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.grey));
        }
    }
}