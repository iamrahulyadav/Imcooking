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
import com.imcooking.Model.api.response.FoodieMyRequest;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.CartActivity;
import com.imcooking.activity.Sub.Foodie.ChefILove;
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

    private void setMyAdapter(List<FoodieMyRequest.FoodieRequestDishChefDetailsBean> list ){
        AdapterFoodieMyRequest adatper = new AdapterFoodieMyRequest(getContext(),list, this);
        rv.setAdapter(adatper);
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

    @Override
    public void viewResponse(int position) {
        if(requestDishChefDetailsBeans.get(position).getConversation_details().size()>0) {
            receiver_id = requestDishChefDetailsBeans.get(position).getChef_id() + "";
            request_id = requestDishChefDetailsBeans.get(position).getRequest_id() + "";
            showDialog(position);
        } else{
            BaseClass.showToast(getContext(), "You haven't recieve any reply from the chef yet.");
        }
    }

    private DishReqChatAdatper dishReqChatAdatper;

    private void showDialog(final int position){
        TextView txtMsg, txtDesc, txt_accept,txtOfferPrice, txt_decline, txt_reply;
        final EditText edtReply,txtOfferValue;
        RecyclerView chatrecyclerView;
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_view_response);
        dialog.setCancelable(true);
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
        txtOfferPrice = dialog.findViewById(R.id.dialog_view_response_offer_price);
        txtOfferValue = dialog.findViewById(R.id.dialog_view_response_offer_edt);

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        dialog.findViewById(R.id.view_response_cross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txtDesc.setText(requestDishChefDetailsBeans.get(position).getChef_description());
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
        final List<FoodieMyRequest.FoodieRequestDishChefDetailsBean.ConversationDetailsBean> conversationDetailsBeans
                = new ArrayList<>(requestDishChefDetailsBeans.get(position).getConversation_details());
        final String chef_id = requestDishChefDetailsBeans.get(position).getChef_id()+"";
        final String foodie_id = HomeFragment.foodie_id;

        if (conversationDetailsBeans.size() > 0){
            offer_price = conversationDetailsBeans.get(0).getConversation_offer_price();
            txtOfferValue.setText("£"+conversationDetailsBeans.get(0).getConversation_offer_price());
        }

        dishReqChatAdatper = new DishReqChatAdatper(getContext(),conversationDetailsBeans, chef_id, foodie_id);
        chatrecyclerView.setAdapter(dishReqChatAdatper);

//        if(conversationDetailsBeans.get(position).getConversation_message().)

        txt_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    msg = edtReply.getText().toString().trim();

                    FoodieMyRequest.FoodieRequestDishChefDetailsBean.ConversationDetailsBean detailsBean
                            = new FoodieMyRequest.FoodieRequestDishChefDetailsBean.ConversationDetailsBean();
                    detailsBean.setConversation_message(msg);
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat spf=new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
                    String date = spf.format(currentTime);
                    detailsBean.setConversation_sender_id(Integer.parseInt(foodie_id));
                    detailsBean.setConversation_reciver_id(Integer.parseInt(chef_id));
                    detailsBean.setConversation_date(date);
                    detailsBean.setConversation_staus("1");
                    detailsBean.setConversation_offer_price("");
                    conversationDetailsBeans.add(detailsBean);
                    edtReply.setText("");
                    status = "yes";
                    sendreply();

            }
        });

        txt_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    msg = edtReply.getText().toString().trim();

                    FoodieMyRequest.FoodieRequestDishChefDetailsBean.ConversationDetailsBean detailsBean
                            = new FoodieMyRequest.FoodieRequestDishChefDetailsBean.ConversationDetailsBean();
                    detailsBean.setConversation_message(msg);
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat spf=new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
                    String date = spf.format(currentTime);
                    detailsBean.setConversation_sender_id(Integer.parseInt(foodie_id));
                    detailsBean.setConversation_reciver_id(Integer.parseInt(chef_id));
                    detailsBean.setConversation_date(date);
                    detailsBean.setConversation_staus("1");
                    detailsBean.setConversation_offer_price("");
                    conversationDetailsBeans.add(detailsBean);
                    edtReply.setText("");
                    status = "no";
                    sendreply();

            }
        });

        edtReply.setVisibility(View.VISIBLE);

        txt_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtReply.getText().toString().isEmpty()){
                    msg = edtReply.getText().toString().trim();

                    FoodieMyRequest.FoodieRequestDishChefDetailsBean.ConversationDetailsBean detailsBean
                            = new FoodieMyRequest.FoodieRequestDishChefDetailsBean.ConversationDetailsBean();
                    detailsBean.setConversation_message(msg);
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat spf=new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
                    String date = spf.format(currentTime);
                    detailsBean.setConversation_sender_id(Integer.parseInt(foodie_id));
                    detailsBean.setConversation_reciver_id(Integer.parseInt(chef_id));
                    detailsBean.setConversation_date(date);
                    detailsBean.setConversation_staus("1");
                    detailsBean.setConversation_offer_price("");
                    conversationDetailsBeans.add(detailsBean);
                    edtReply.setText("");
                    status = "reply";
                    sendreply();
                }  else BaseClass.showToast(getContext(), "Please enter message");
            }
        });

    }

    private void sendreply(){
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
        Log.d("TAg", "Myrequest: "+s);
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