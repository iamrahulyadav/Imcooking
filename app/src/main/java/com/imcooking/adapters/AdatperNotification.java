package com.imcooking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imcooking.Model.api.response.NotificationList;
import com.imcooking.Model.api.response.SavedCardData;
import com.imcooking.R;
import com.imcooking.utils.BaseClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by rakhi.
 * Contact Number : +91 9958187463
 * Date : 27/08/2018
 */
public class AdatperNotification extends RecyclerView.Adapter<AdatperNotification.MyViewHolder> {

    private Context context;
    private List<NotificationList.NotificationBean>notificationBeans ;
    private AdapterNotfiInterface notfiInterface;


    public AdatperNotification(Context context, List<NotificationList.NotificationBean> notificationBeans,
                               AdapterNotfiInterface notfiInterface) {
        this.context = context;
        this.notificationBeans = notificationBeans;
        this.notfiInterface = notfiInterface;
    }

    public interface AdapterNotfiInterface {
        void clickNoti(String edit, int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

    private TextView tv_title, tv_heading, tv_time;
    private LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            tv_time = view.findViewById(R.id.item_notification_time);
            tv_title = view.findViewById(R.id.item_notification_title);
            tv_heading = view.findViewById(R.id.item_notification_txt_heading);
            layout = view.findViewById(R.id.item_notification_layout);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notfiInterface.clickNoti("click", getAdapterPosition());
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_heading.setText(notificationBeans.get(position).getNotification_description());
        holder.tv_title.setText(notificationBeans.get(position).getNotification_title());


        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date myDate = null;
        try {
            myDate = timeFormat.parse(notificationBeans.get(position).getNotification_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MMM dd, yyyy HH:mm a");
        String finalDate = dateFormat.format(myDate);

        holder.tv_time.setText(finalDate);
        if (notificationBeans.get(position).getNotification_status().equals("0")){
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.colorLightGrey));
        } else {
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }
    }

    @Override
    public int getItemCount() {
        return notificationBeans.size();
    }

}