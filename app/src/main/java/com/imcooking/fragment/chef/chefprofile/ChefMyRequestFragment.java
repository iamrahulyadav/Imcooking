package com.imcooking.fragment.chef.chefprofile;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefDishRequestData;
import com.imcooking.R;
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

    private void setDishAdapter(List<ChefDishRequestData.ChefDishDetailsBean> list) {
        AdatperChefMyRequestList chefMyRequestsAdatper = new AdatperChefMyRequestList(getContext(),list, this);
        requestRecyclerView.setAdapter(chefMyRequestsAdatper);

    }

    private Dialog dialog;
    private DishReqChefChatAdatper dishReqChatAdatper;

    private void showDialog(final int position, final String receiver_id, final String request_id){
        TextView txtMsg, txtDesc, txt_accept, txt_decline, txt_reply;
        final EditText edtPrice;
        final EditText edtReply;
        RecyclerView chatrecyclerView;
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_view_response);
        dialog.setCancelable(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();

        txtDesc = dialog.findViewById(R.id.dialog_view_response_desc);
        txtMsg = dialog.findViewById(R.id.dialog_view_response_msg);
        txt_accept = dialog.findViewById(R.id.view_response_accept);
        txt_decline = dialog.findViewById(R.id.view_response_decline);
        txt_reply = dialog.findViewById(R.id.view_response_reply);
        edtReply = dialog.findViewById(R.id.dialog_view_response_edtReply);
        chatrecyclerView = dialog.findViewById(R.id.dialog_view_response_recycler);
        edtPrice = dialog.findViewById(R.id.dialog_view_response_offer_edt);


        dialog.findViewById(R.id.view_response_cross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
//        txtDesc.setText(chefDishDetailsBeans.get(position).getChef_description());



        CustomLayoutManager manager = new CustomLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        chatrecyclerView.setLayoutManager(manager);

        txtDesc.setVisibility(View.VISIBLE);
        txtMsg.setVisibility(View.VISIBLE);
        dialog.findViewById(R.id.layout_accept_in_process).setVisibility(View.GONE);
        dialog.findViewById(R.id.layout_accept_delivered).setVisibility(View.GONE);

        final List<ChefDishRequestData.ChefDishDetailsBean.ConversationDetailsBean> conversationDetailsBeans = new ArrayList<>();
        conversationDetailsBeans.addAll(chefDishDetailsBeans.get(position).getConversation_details());

        final String chef_id = sender_id ;
        final String foodie_id = chefDishDetailsBeans.get(position).getFoodie_id()+"";

        edtPrice.setEnabled(true);

        dishReqChatAdatper = new DishReqChefChatAdatper(getContext(), chef_id, foodie_id,conversationDetailsBeans);
        chatrecyclerView.setAdapter(dishReqChatAdatper);
        if (conversationDetailsBeans!=null && conversationDetailsBeans.size()>0){
            offer_price = conversationDetailsBeans.get(0).getConversation_offer_price();
            edtPrice.setText(offer_price);
        }

        txt_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    offer_price = edtPrice.getText().toString().trim();
                    msg = edtReply.getText().toString().trim();

                    ChefDishRequestData.ChefDishDetailsBean.ConversationDetailsBean detailsBean
                            = new ChefDishRequestData.ChefDishDetailsBean.ConversationDetailsBean();
                    detailsBean.setConversation_message(msg);
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat spf=new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
                    String date = spf.format(currentTime);
                    detailsBean.setConversation_sender_id(Integer.parseInt(chef_id));
                    detailsBean.setConversation_reciver_id(Integer.parseInt(foodie_id));
                    detailsBean.setConversation_date(date);
                    detailsBean.setConversation_staus("1");
                    detailsBean.setConversation_offer_price("");
                    conversationDetailsBeans.add(detailsBean);
                    status = "yes";
                    sendreply(receiver_id,request_id);

            }
        });

        txt_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    offer_price = edtPrice.getText().toString().trim();
                    msg = edtReply.getText().toString().trim();

                    ChefDishRequestData.ChefDishDetailsBean.ConversationDetailsBean detailsBean
                            = new ChefDishRequestData.ChefDishDetailsBean.ConversationDetailsBean();
                    detailsBean.setConversation_message(msg);
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat spf=new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
                    String date = spf.format(currentTime);
                    detailsBean.setConversation_sender_id(Integer.parseInt(chef_id));
                    detailsBean.setConversation_reciver_id(Integer.parseInt(foodie_id));
                    detailsBean.setConversation_date(date);
                    detailsBean.setConversation_staus("1");
                    detailsBean.setConversation_offer_price("");
                    conversationDetailsBeans.add(detailsBean);
                    edtReply.setText("");
                    status = "no";
                    sendreply(receiver_id,request_id);

            }
        });

        edtReply.setVisibility(View.VISIBLE);

        txt_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtReply.getText().toString().isEmpty()){
                    offer_price = edtPrice.getText().toString().trim();
                    msg = edtReply.getText().toString().trim();

                    ChefDishRequestData.ChefDishDetailsBean.ConversationDetailsBean detailsBean
                            = new ChefDishRequestData.ChefDishDetailsBean.ConversationDetailsBean();
                    detailsBean.setConversation_message(msg);
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat spf=new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
                    String date = spf.format(currentTime);
                    detailsBean.setConversation_sender_id(Integer.parseInt(chef_id));
                    detailsBean.setConversation_reciver_id(Integer.parseInt(foodie_id));
                    detailsBean.setConversation_date(date);
                    detailsBean.setConversation_staus("1");
                    detailsBean.setConversation_offer_price("");
                    conversationDetailsBeans.add(detailsBean);
                 //   edtReply.setText("");
                    status = "reply";
                    sendreply(receiver_id,request_id);
                } else {
                    BaseClass.showToast(getContext(), "Please enter message");
                }
            }
        });

    }

    @Override
    public void setresponse(int pos, String TAG) {
        if (TAG.equals("reply")){
            receiver_id =chefDishDetailsBeans.get(pos).getFoodie_id()+"";
            request_id = chefDishDetailsBeans.get(pos).getRequest_id();
            showDialog(pos,receiver_id,request_id);
        } else if (TAG.equals("offer")){
            createOfferDialog();
        } else if(TAG.equals("chat")){
            createChatDialog();
        } else {}
    }

    private void createOfferDialog(){
        Dialog dialog_offer = new Dialog(getContext());
        dialog_offer.setContentView(R.layout.dialog_offer_price);
        dialog_offer.setCancelable(true);
        dialog_offer.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog_offer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_offer.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog_offer.show();

    }

    private void createChatDialog(){
        Dialog dialog_chat = new Dialog(getContext());
        dialog_chat.setContentView(R.layout.dialog_chef_chat);
        dialog_chat.setCancelable(true);
        dialog_chat.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog_chat.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_chat.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog_chat.show();
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

