package com.imcooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.AddDelRequest;
import com.imcooking.Model.api.response.AddressListData;
import com.imcooking.Model.api.response.ChefIloveData;
import com.imcooking.R;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.webservices.GetData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakhi.
 * Contact Number : +91 9958187463
 */

public class AddListAdatper extends RecyclerView.Adapter<AddListAdatper.MyViewHolder> {
    AddInterfaceMethod addInterfaceMethod;
    private Context context;
    private List<AddressListData.AddressBean>addressBeanList = new ArrayList<>();

    public AddListAdatper(Context context, List<AddressListData.AddressBean> addressBeanList) {
        this.context = context;
        this.addressBeanList = addressBeanList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, address, txtEdit, txtDelete;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.manage_address_title);
            address = view.findViewById(R.id.manage_address_address);
            txtEdit = view.findViewById(R.id.manage_address_txtEdit);
            txtDelete = view.findViewById(R.id.manage_address_txtDelete);
            txtEdit.setOnClickListener(this);
            txtDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.manage_address_txtEdit:
                    if (addInterfaceMethod != null) {
                        addInterfaceMethod.AddressInterfaceMethod(view, getPosition(), "edit");
                    }
                    break;
                case R.id.manage_address_txtDelete:
                    if (addInterfaceMethod != null) {
                        addInterfaceMethod.AddressInterfaceMethod(view, getPosition(), "delete");
                    }
                    break;
            }
        }
    }
    public interface AddInterfaceMethod {
        void AddressInterfaceMethod(View view, int position, String tag);
    }
    public void AddInterfaceMethod(AddListAdatper.AddInterfaceMethod quoteInterface) {
        this.addInterfaceMethod = quoteInterface;
    }
    int row_index=-1;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address_list_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.title.setText(addressBeanList.get(position).getAddress_title());
        holder.address.setText(addressBeanList.get(position).getAddress_address());

      /*  holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
                deleteAdd(HomeFragment.foodie_id+"", addressBeanList.get(position).getAddress_id()+"");
            }
        });*/

        /*holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return addressBeanList.size();
    }

}