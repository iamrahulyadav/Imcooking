package com.imcooking.fragment.foodie;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.FoodieMyorderList;
import com.imcooking.Model.api.response.NotificationList;
import com.imcooking.R;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.adapters.AdapterFoodieMyOrderList;
import com.imcooking.adapters.AdatperNotification;
import com.imcooking.utils.CustomLayoutManager;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment implements AdatperNotification.AdapterNotfiInterface{
    private RecyclerView recyclerList;
    private AdatperNotification adatperNotification;
    private List<NotificationList.NotificationBean>notificationBeans;
    private String userid;
    private TinyDB tinyDB;
    private ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tinyDB = new TinyDB(getContext());
        userDataBean = new Gson().fromJson(tinyDB.getString("login_data"), ApiResponse.UserDataBean.class);
        userid = userDataBean.getUser_id()+"";

        getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        recyclerList = getView().findViewById(R.id.fragment_notification_recycler);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).setBottomColor();
        ((MainActivity) getActivity()).tv_notification.setTextColor(getResources().getColor(R.color.theme_color));
        ((MainActivity) getActivity()).iv_notification.setImageResource(R.drawable.ic_ring_1);
        getNotification(userid);
    }

    private void getNotification(String userid){
        String request ="{\"user_id\":\""+userid+"\"}";
        Log.d("TAG", "MyRequest: "+request);
        notificationBeans = new ArrayList<>();
        new GetData(getContext(), getActivity()).getResponse(request, GetData.NOTIFICATION, new GetData.MyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("status")){
                        NotificationList notificationList = new NotificationList();
                        notificationList = new Gson().fromJson(result, NotificationList.class);
                        notificationBeans.addAll(notificationList.getNotification());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setMyAdapter(notificationBeans);
                            }
                        });
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void setMyAdapter(List<NotificationList.NotificationBean>notificationBeanList){
        if (notificationBeanList.size()>0){
            adatperNotification = new AdatperNotification(getContext(), notificationBeanList, this);
            recyclerList.setAdapter(adatperNotification);
        }
    }


    @Override
    public void clickNoti(String edit, int pos) {
     //   readNotif(notificationBeans.get(pos).getNotification_id()+"");
    }

    private String TAG = NotificationFragment.class.getName();
    private void readNotif(String notification_id){
        String readRequest = "{\"notification_id\":" + notification_id + ",\"user_id\":" + userid +"}";
        Log.d(TAG, "MyRequest: "+readRequest);
        new GetData(getContext(), getActivity()).getResponse(readRequest, GetData.READ_NOTIFI, new GetData.MyCallback() {
            @Override
            public void onSuccess(String result) {
                adatperNotification.notifyDataSetChanged();
            }
        });
    }


}
