package com.imcooking.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imcooking.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 4/24/2018.
 */

public class CustomHomeTabAdapter extends PagerAdapter{
    LayoutInflater mLayoutInflater;
    Context context;
    ArrayList<String> cat_names = new ArrayList<>();
    FragmentManager manager;

    public CustomHomeTabAdapter(LayoutInflater mLayoutInflater, Context context,
                                ArrayList<String> cat_names, FragmentManager manager) {
        this.mLayoutInflater = mLayoutInflater;
        this.context = context;
        this.cat_names = cat_names;
        this.manager = manager;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_textview, container, false);

        TextView tv1 = (TextView) itemView.findViewById(R.id.text_1);
        tv1.setText(cat_names.get(0));
        container.addView(itemView);

        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
