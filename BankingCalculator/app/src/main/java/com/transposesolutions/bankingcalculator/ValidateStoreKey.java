package com.transposesolutions.bankingcalculator;
import android.content.Context;
import android.content.SharedPreferences;
public class ValidateStoreKey {

    final Context context;
    ValidateStoreKey(Context context){
        this.context = context;
    }


    public Boolean getPurchaseKey(Context context, String prefName, String constantName) {
        SharedPreferences pref = context.getSharedPreferences(prefName, 0); // 0 - for private mode

        return pref.getBoolean(constantName, false);

    }

    public void setPurchaseKey(Context context,String prefName, String constantName, Boolean val) {
        SharedPreferences pref = context.getSharedPreferences(prefName, 0); // 0 - for private mode

        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(constantName, val);
        editor.apply();
    }
}

