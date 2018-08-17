package com.imcooking.fragment.chef.chefprofile;

import android.app.Dialog;
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

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefDishRequestData;
import com.imcooking.Model.api.response.ConversationData;
import com.imcooking.R;
import com.imcooking.adapters.AdapterChat;
import com.imcooking.adapters.AdatperChefMyRequestList;
import com.imcooking.adapters.DishReqChefChatAdatper;
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


public class ChefMyRequestFragment extends Fragment implements AdatperChefMyRequestList.ChefMyrequestInterface{

    TinyDB tinyDB;
    private RecyclerView requestRecyclerView;
    private LinearLayout no_recordLayout;
    private LinearLayout layoutToolbar;
    private String strtext;
    private List<ChefDishRequestData.ChefDishDetailsBean>chefDishDetailsBeans;
    private String sender_id, receiver_id, request_id, msg="",status, offer_price="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chef_request_list, container, false);
        if (getArguments()!=null){
            strtext=getArguments().getString("message");
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {

        getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        no_recordLayout = getView().findViewById(R.id.fragment_my_request_chef_no_record_image);
        requestRecyclerView = getView().findViewById(R.id.fragment_chef_request_list_recycler);
        layoutToolbar = getView().findViewById(R.id.chefrequest_txtname);
        tinyDB=new TinyDB(getContext());
        CustomLayoutManager manager = new CustomLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

        };
        if (strtext!=null){
            layoutToolbar.setVisibility(View.VISIBLE);
        }
        requestRecyclerView.setLayoutManager(manager);
        getorderList();

    }

    public void getorderList(){

        String login = tinyDB.getString("login_data");
        ApiResponse.UserDataBean apiResponse = new ApiResponse.UserDataBean();
        apiResponse = new Gson().fromJson(login,ApiResponse.UserDataBean.class);
        String user_id=apiResponse.getUser_id()+"";
        String s = "{\"chef_id\":" + user_id + "}";
        sender_id = user_id;

        Log.d("MyRequest", s);

        try {
            JSONObject job = new JSONObject(s);
            new GetData(getContext(), getActivity()).sendMyData(job, GetData.DISH_REQUEST_LIST, getActivity(),
                    new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {

                            ChefDishRequestData chefMyorderList = new ChefDishRequestData();
                            chefMyorderList = new Gson().fromJson(result, ChefDishRequestData.class);
                            chefDishDetailsBeans = new ArrayList<>();
                             if(chefMyorderList.isStatus()){
                                if(chefMyorderList.getChef_dish_details()!=null && chefMyorderList.getChef_dish_details().size()>0){
                                    requestRecyclerView.setVisibility(View.VISIBLE);
                                    no_recordLayout.setVisibility(View.GONE);
                                    chefDishDetailsBeans.addAll(chefMyorderList.getChef_dish_details());
                                    setDishAdapter(chefDishDetailsBeans);
                                }
                                else {
                                    if (strtext!=null){
                                        layoutToolbar.setVisibility(View.VISIBLE);
                                    }
                                    requestRecyclerView.setVisibility(View.GONE);
                                    no_recordLayout.setVisibility(View.VISIBLE);
                                }
                            }
                            else {
                                BaseClass.showToast(getContext(),getResources().getString(R.string.error));
                            }
                        }
                    });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private AdatperChefMyRequestList chefMyRequestsAdatper;
    private void setDishAdapter(List<ChefDishRequestData.ChefDishDetailsBean> list) {
        chefMyRequestsAdatper = new AdatperChefMyRequestList(getContext(),list, this);
        requestRecyclerView.setAdapter(chefMyRequestsAdatper);

    }

    private Dialog dialog;
    private DishReqChefChatAdatper dishReqChatAdatper;

    @Override
    public void setresponse(int pos, String TAG) {
        if (TAG.equals("offer")){
            createOfferDialog(pos);
        } else if(TAG.equals("chat")){
            createChatDialog(pos);
        } else if (TAG.equals("decline")){
            decline_request(chefDishDetailsBeans.get(pos).getRequest_id() + "", pos);
//            BaseClass.showToast(getContext(), "Declined");
        } else {}
    }

    private void decline_request(String req_id, final int pos){

        String s = "{\"request_id\":\"" + req_id + "\"}";
        try {
            JSONObject jsonObject = new JSONObject(s);
            new GetData(getContext(), getActivity()).sendMyData(
                    jsonObject, GetData.CHEF_DECLINE, getActivity(), new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {

                            ApiResponse apiResponse = new ApiResponse();
                            apiResponse = new Gson().fromJson(result, ApiResponse.class);

                            if(apiResponse.isStatus()){
                                if(apiResponse.getMsg().equals("decline successfully")){
                                    BaseClass.showToast(getContext(), "Request has been declined successfully and removed from your list.");
                                    chefDishDetailsBeans.remove(pos);
                                    chefMyRequestsAdatper.notifyDataSetChanged();

                                    if(chefDishDetailsBeans.size() == 0){
                                        requestRecyclerView.setVisibility(View.GONE);
                                        no_recordLayout.setVisibility(View.VISIBLE);
                                    } else {
                                        requestRecyclerView.setVisibility(View.VISIBLE);
                                        no_recordLayout.setVisibility(View.GONE);
                                    }

                                } else {
                                    BaseClass.showToast(getContext(), "Something Went Wrong");
                                }
                            } else{
                                if(apiResponse.getMsg().equals("decline record found")){
                                    BaseClass.showToast(getContext(), "Request Not Found.");
                                } else {
                                    BaseClass.showToast(getContext(), "Something Went Wrong");
                                }
                            }
                        }
                    }
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private EditText dialog_offer_price_edt;
    private Dialog dialog_offer;

    private void createOfferDialog(final int pos){
        dialog_offer = new Dialog(getContext());
        dialog_offer.setContentView(R.layout.dialog_offer_price);
        dialog_offer.setCancelable(true);
        dialog_offer.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog_offer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_offer.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView tv_send_offer = dialog_offer.findViewById(R.id.dialog_offer_price_btn);
        dialog_offer_price_edt = dialog_offer.findViewById(R.id.dialog_offer_price_edt);

        tv_send_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_offer(pos);

            }
        });
        dialog_offer.show();

    }

    private void send_offer(final int pos){
        if (!dialog_offer_price_edt.getText().toString().isEmpty()) {
            final String edt = dialog_offer_price_edt.getText().toString().trim();
            if(!edt.contains("£")){
                String s = "{\"request_id\":\"" + chefDishDetailsBeans.get(pos).getRequest_id() + "\"," +
                        "\"offer_price\":\"" + edt + "\",\"key\":\"1\"}";
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    new GetData(getContext(), getActivity()).sendMyData(jsonObject, GetData.CHEF_OFFER_PRICE,
                            getActivity(),
                            new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {

                            ApiResponse apiResponse = new ApiResponse();
                            apiResponse = new Gson().fromJson(result, ApiResponse.class);

                            if(apiResponse.isStatus()){
                                if(apiResponse.getMsg().equals("offer price update successfully")){
                                    BaseClass.showToast(getContext(), "Offer has been sent successfully.");
                                    dialog_offer.dismiss();
                                    chefDishDetailsBeans.get(pos).setRequest_price(edt);
                                    chefDishDetailsBeans.get(pos).setChef_response("1");
                                    chefMyRequestsAdatper.notifyDataSetChanged();

                                } else{
                                    BaseClass.showToast(getContext(), "Something Went Wrong.");
                                }
                            } else{
                                BaseClass.showToast(getContext(), "Something Went Wrong.");
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else{
                BaseClass.showToast(getContext(), "Prize should be a number.");
            }
        } else{
            BaseClass.showToast(getContext(), "Please Enter a Offer Price");
        }
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
                "  \"receiver_id\":" + chefDishDetailsBeans.get(pos).getFoodie_id() + ",\n" +
                "  \"request_id\":" + chefDishDetailsBeans.get(pos).getRequest_id() + ",\n" +
                "  \"message\":\"" + dialog_chat_edt.getText().toString().trim() + "\",\n" +
                "  \"offer_price\":\"" + chefDishDetailsBeans.get(pos).getRequest_price() + "\",\n" +
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
                                    conversationMsgBean.setConversation_date("");
                                    conversationMsgBean.setConversation_message(dialog_chat_edt.getText()
                                            .toString().trim());
                                    conversationMsgBean.setConversation_reciver_id(chefDishDetailsBeans.get(pos)
                                            .getFoodie_id() + "");
                                    conversationMsgBean.setConversation_sender_id(sender_id);
                                    conversationMsgBean.setConversation_staus("reply");
                                    conversationMsgBean.setConversation_request_id(chefDishDetailsBeans
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
        String s = "{\"request_id\":\"" + chefDishDetailsBeans.get(pos).getRequest_id() + "\"}";
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
                chefDishDetailsBeans.get(pos).getFoodie_id() + "");
        dialog_chat_rv.setAdapter(adapterChat);
    }

    private void sendreply(String receiver_id,String request_id){
        if (msg.isEmpty()){
            msg = " ";
        }
        String s = "{\n" +
                "  \"sender_id\":\""+sender_id+"\",\n" +
                "  \"receiver_id\":"+receiver_id+",\n" +
                "  \"request_id\":"+request_id+",\n" +
                "  \"message\":\""+msg+"\",\n" +
                "  \"offer_price\":\""+offer_price+"\",\n" +
                "\"status\":\""+status+"\"\n"+
                "}";

        Log.d("TAG", "MyRequest: "+s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            new GetData(getContext(), getActivity()).sendMyData(jsonObject, GetData.CONVERSATION_CHAT, getActivity(), new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d("TAG", "Rakhi: "+result);
                    dishReqChatAdatper.notifyDataSetChanged();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}