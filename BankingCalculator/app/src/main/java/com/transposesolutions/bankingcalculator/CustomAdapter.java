package com.transposesolutions.bankingcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int[] images;
    String[] countryCode;
    String[] countryFullName;
    LayoutInflater inflater;

    public CustomAdapter(Context applicationContext, int[] flags, String[] countryCode, String[] countryFullName) {
        this.context = applicationContext;
        this.images = flags;
        this.countryCode = countryCode;
        this.countryFullName = countryFullName;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.spinner_custom_layout, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        TextView countryName = (TextView) view.findViewById(R.id.textView1);
        icon.setImageResource(images[i]);
        names.setText(countryCode[i]);
        countryName.setText(countryFullName[i]);
        return view;
    }
}
