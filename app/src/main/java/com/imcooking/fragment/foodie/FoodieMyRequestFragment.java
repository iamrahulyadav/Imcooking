package com.imcooking.fragment.foodie;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ConversationData;
import com.imcooking.Model.api.response.FoodieMyRequest;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.CartActivity;
import com.imcooking.activity.Sub.Foodie.ChefILove;
import com.imcooking.adapters.AdapterChat;
import com.imcooking.adapters.AdapterFoodieMyRequest;
import com.imcooking.adapters.DishReqChatAdatper;
import com.imcooking.utils.BaseClass;
import com.imcooking.utils.CustomLayoutManager;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class FoodieMyRequestFragment extends Fragment implements AdapterFoodieMyRequest.FoodieMyRequestInterface{

    TinyDB tinyDB;
    private LinearLayout no_recordLayout;
    private TextView txtShop;
    private String sender_id, receiver_id, request_id, msg, offer_price="", status;
    private List<FoodieMyRequest.FoodieRequestDishChefDetailsBean> list = new ArrayList();

    public FoodieMyRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_foodie_my_request, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tinyDB=new TinyDB(getContext());

        String login = tinyDB.getString("login_data");
        ApiResponse.UserDataBean apiResponse = new ApiResponse.UserDataBean();
        apiResponse = new Gson().fromJson(login,ApiResponse.UserDataBean.class);
        sender_id =apiResponse.getUser_id()+"";

        getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        init();
    }

    private RecyclerView rv;
    private ImageView iv_cart;
    private TextView tv_count;

    private void init(){
        tv_count = getView().findViewById(R.id.foodie_my_request_cart_count);

        iv_cart = getView().findViewById(R.id.fragment_my_request_img_cart);
        txtShop = getView().findViewById(R.id.fragment_my_request_shop_now);
        no_recordLayout = getView().findViewById(R.id.fragment_my_request_foodie_no_record_image);
        rv = getView().findViewById(R.id.recycler_foodie_my_requests);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        myorderRequest();

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CartActivity.class).putExtra("foodie_id",
                        sender_id));
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });

        txtShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ChefILove.class).putExtra("extra", "extra"));
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
                //BaseClass.callFragment(new HomeFragment(), HomeFragment.class.getName(), getFragmentManager());
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        String cart_count = tinyDB.getString("cart_count");
        tv_count.setText(cart_count);

    }

    private List<FoodieMyRequest.FoodieRequestDishChefDetailsBean>requestDishChefDetailsBeans ;
    public void myorderRequest(){
       String user_id = sender_id;
        String s = "{\"foodie_id\":" + user_id + "}";
        try {
            JSONObject job = new JSONObject(s);
            new GetData(getContext(), getActivity()).sendMyData(job, "foodie_myrequestdish_chefdetails",
                    getActivity(), new GetData.MyCallback() {
                @Override
                public void onSuccess(final String result) {
                    if (getActivity()!=null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                FoodieMyRequest foodieMyRequest = new FoodieMyRequest();
                                requestDishChefDetailsBeans = new ArrayList<>();
                                foodieMyRequest = new Gson().fromJson(result, FoodieMyRequest.class);
                                if(foodieMyRequest.isStatus()){
                                    if(!foodieMyRequest.getFoodie_request_dish_chef_details().isEmpty()){
                                        rv.setVisibility(View.VISIBLE);
                                        no_recordLayout.setVisibility(View.GONE);
                                        requestDishChefDetailsBeans.addAll(foodieMyRequest.getFoodie_request_dish_chef_details());
                                        setMyAdapter(requestDishChefDetailsBeans);
                                    }
                                    else {
                                        rv.setVisibility(View.GONE);
                                        no_recordLayout.setVisibility(View.VISIBLE);
                                    }
                                }
                                else {
                                    BaseClass.showToast(getContext(), getResources().getString(R.string.error));
                                }
                            }
                        });
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private AdapterFoodieMyRequest adatperFoodieMyRequest;

    private void setMyAdapter(List<FoodieMyRequest.FoodieRequestDishChefDetailsBean> list ){
        adatperFoodieMyRequest = new AdapterFoodieMyRequest(getContext(),list, this);
        rv.setAdapter(adatperFoodieMyRequest);
    }

    private RecyclerView dialog_chat_rv;
    private EditText dialog_chat_edt;

    private void createChatDialog(final int pos){
        final Dialog dialog_chat = new Dialog(getContext());
        dialog_chat.setContentView(R.layout.dialog_chef_chat);
        dialog_chat.setCancelable(true);
        dialog_chat.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog_chat.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_chat.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        dialog_chat_edt = dialog_chat.findViewById(R.id.dialog_chef_chat_edittext);

        ImageView iv_send = dialog_chat.findViewById(R.id.dialog_chef_chat_send_icon);
        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!dialog_chat_edt.getText().toString().isEmpty()) {
                    sendMessage(pos);
                } else {
                    BaseClass.showToast(getContext(), "You can not send an empty message.");
                }
            }
        });

        dialog_chat_rv = dialog_chat.findViewById(R.id.dialog_chat_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        dialog_chat_rv.setLayoutManager(layoutManager);

        getChat(pos);

        dialog_chat.show();
    }

    private void sendMessage(final int pos){

        String s = "{\n" +
                "  \"sender_id\":\"" + sender_id + "\",\n" +
                "  \"receiver_id\":" + requestDishChefDetailsBeans.get(pos).getChef_id() + ",\n" +
                "  \"request_id\":" + requestDishChefDetailsBeans.get(pos).getRequest_id() + ",\n" +
                "  \"message\":\"" + dialog_chat_edt.getText().toString().trim() + "\",\n" +
                "  \"offer_price\":\"" + requestDishChefDetailsBeans.get(pos).getOffered_price() + "\",\n" +
                "\"status\":\"" + "reply" + "\"\n" +
                "}";
        try {
            JSONObject jsonObject = new JSONObject(s);
            new GetData(getContext(), getActivity()).sendMyData(jsonObject, GetData.CONVERSATION_CHAT,
                    getActivity(), new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {

                            try {
                                JSONObject jsonObject1 = new JSONObject(result);
                                if(jsonObject1.getBoolean("status")){
                                    ConversationData.ConversationMsgBean conversationMsgBean = new ConversationData.ConversationMsgBean();
                                    conversationMsgBean.setConversation_date(""); // 1
                                    conversationMsgBean.setConversation_message(dialog_chat_edt.getText()
                                            .toString().trim()); // 2
                                    conversationMsgBean.setConversation_reciver_id(requestDishChefDetailsBeans.get(pos)
                                            .getChef_id() + ""); // 3
                                    conversationMsgBean.setConversation_sender_id(sender_id); // 4
                                    conversationMsgBean.setConversation_staus("reply"); // 5
                                    conversationMsgBean.setConversation_request_id(requestDishChefDetailsBeans
                                    .get(pos).getRequest_id() + ""); // 6

                                    if(conversationData.getConversation_msg()!=null) {
                                        conversationData.getConversation_msg().add(conversationMsgBean);
                                    } else {
                                        List<ConversationData.ConversationMsgBean> conversationMsgBeans = new ArrayList<>();
                                        conversationMsgBeans.add(conversationMsgBean);
                                        conversationData.setConversation_msg(conversationMsgBeans);
                                    }

                                    if(adapterChat!=null) {
                                        adapterChat.notifyDataSetChanged();
                                    } else{
                                        showChat(pos);
                                    }
                                } else {
                                    BaseClass.showToast(getContext(), "Something Went Wrong.");
                                }
                                dialog_chat_edt.setText("");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ConversationData conversationData;

    private void getChat(final int pos){
        String s = "{\"request_id\":\"" + requestDishChefDetailsBeans.get(pos).getRequest_id() + "\"}";
        try {
            JSONObject jsonObject = new JSONObject(s);
            new GetData(getContext(), getActivity()).sendMyData(jsonObject, GetData.CHAT_DISPLAY, getActivity(),
                    new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject job = new JSONObject(result);
                                conversationData = new ConversationData();
                                if(job.getBoolean("status")){
                                   conversationData = new Gson().fromJson(result, ConversationData.class);

                                    if(conversationData.isStatus()){
                                        showChat(pos);
                                    } else{
                                        BaseClass.showToast(getContext(), "Something Went Wrong.");
                                    }

                                } else{
                                    if(job.getString("conversation_msg").equals("not found records")) {
                                        BaseClass.showToast(getContext(), "No chat yet.");
                                    } else {
                                        BaseClass.showToast(getContext(), "Something Went Wrong.");
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private AdapterChat adapterChat;

    private void showChat(int pos){

        adapterChat = new AdapterChat(conversationData,
                sender_id,
                requestDishChefDetailsBeans.get(pos).getChef_id() + "");
        dialog_chat_rv.setAdapter(adapterChat);
    }

    private DishReqChatAdatper dishReqChatAdatper;

    @Override
    public void method_AdapterFoodieMyRequest(int position, String tag) {

        if(tag.equals("reply")){
//            BaseClass.showToast(getContext(), "Reply");
            createChatDialog(position);
        } else if(tag.equals("accept")){
            accept_request(position);
//            BaseClass.showToast(getContext(), "Accepted");
        } else if(tag.equals("decline")){
            cancel_request(position);
        } else if(tag.equals("cancel")){
            cancel_request(position);
        } else {

        }
    }

    private void accept_request(final int position){
        String s = "{\"request_id\":\"" + requestDishChefDetailsBeans.get(position).getRequest_id()
                + "\",\"foodie_id\":\"" + sender_id + " \"}";
        try {
            JSONObject jsonObject = new JSONObject(s);
            new GetData(getContext(), getActivity()).sendMyData(jsonObject, GetData.FOODIE_ACCEPT, getActivity(),
                    new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
//                            {"status":false,"msg":"foodie accept not successfully"}
//                            {"status":true,"msg":"foodie accept successfully"}
                            ApiResponse apiResponse = new ApiResponse();
                            apiResponse = new Gson().fromJson(result, ApiResponse.class);
                            if(apiResponse.isStatus()){
                                if(apiResponse.getMsg().equals("foodie accept successfully")){
                                    requestDishChefDetailsBeans.get(position).setFoodie_response("2");
                                    BaseClass.showToast(getContext(), "Offer has been accepted sucessfully.");
                                    adatperFoodieMyRequest.notifyDataSetChanged();
                                } else {
                                    BaseClass.showToast(getContext(), "Something Went Wrong.");
                                }
                            } else{
                                if(apiResponse.getMsg().equals("foodie accept not successfully")){
                                    BaseClass.showToast(getContext(), "Offer Accept Failed");
                                } else if(apiResponse.getMsg().equals("not found this request")){
                                    BaseClass.showToast(getContext(), "Request Not Found");
                                } else if(apiResponse.getMsg().equals("not empty request_id,foodie_id")){
                                    BaseClass.showToast(getContext(), "Foodie id or Request id is Empty");
                                } else {
                                    BaseClass.showToast(getContext(), "Something Went Wrong.");
                                }
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
//a
    }

    private void cancel_request(final int position){

        String s = "{\"request_id\":\"" + requestDishChefDetailsBeans.get(position).getRequest_id() + "\"}";
        try {
            JSONObject jsonObject = new JSONObject(s);

            new GetData(getContext(), getActivity()).sendMyData(jsonObject, GetData.FOODIE_DECLINE, getActivity(),
                    new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {

                            ApiResponse apiResponse = new ApiResponse();
                            apiResponse = new Gson().fromJson(result, ApiResponse.class);

                            if(apiResponse.isStatus()){
                                if(apiResponse.getMsg().equals("Delete successfully")){
                                    BaseClass.showToast(getContext(), "Request has been removed successfully");
                                    requestDishChefDetailsBeans.remove(position);
                                    adatperFoodieMyRequest.notifyDataSetChanged();
                                    if(requestDishChefDetailsBeans.size() == 0){
                                        rv.setVisibility(View.GONE);
                                        no_recordLayout.setVisibility(View.VISIBLE);
                                    } else {
                                        rv.setVisibility(View.VISIBLE);
                                        no_recordLayout.setVisibility(View.GONE);
                                    }

                                } else {
                                    BaseClass.showToast(getContext(), "Something Went Wrong.");
                                }
                            } else {
                                if(apiResponse.getMsg().equals("not fount this request")){
                                    BaseClass.showToast(getContext(), "Request Not Found.");
                                } else{
                                    BaseClass.showToast(getContext(), "Something Went Wrong.");
                                }
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}