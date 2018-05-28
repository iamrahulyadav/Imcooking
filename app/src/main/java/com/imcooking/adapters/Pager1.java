package com.imcooking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imcooking.R;

import java.util.ArrayList;

public class Pager1 extends PagerAdapter{

    LayoutInflater mLayoutInflater;
    Context context;
    ArrayList<String>name ;
    public Pager1(Context context, ArrayList<String>name ) {
        this.context = context;
        this.name = name;
        mLayoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mLayoutInflater.inflate(R.layout.tabstyle, container, false);

        TextView txtName = view.findViewById(R.id.txtName);
        txtName.setText(name.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
