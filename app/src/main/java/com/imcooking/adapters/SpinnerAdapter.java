package com.imcooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imcooking.R;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {

    Context context;
    int flags[];
    ArrayList<String> countryNames;
    LayoutInflater inflter;

    public SpinnerAdapter(Context applicationContext,ArrayList<String> countryNames) {
        this.context = applicationContext;
        this.countryNames = countryNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return countryNames.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_row, null);
//        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.text1);
//        icon.setImageResource(flags[i]);
        names.setText(countryNames.get(i));
        return view;
    }

}
