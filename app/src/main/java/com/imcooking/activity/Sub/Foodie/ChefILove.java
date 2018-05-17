package com.imcooking.activity.Sub.Foodie;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefIloveData;
import com.imcooking.R;
import com.imcooking.adapters.CartAdatper;
import com.imcooking.adapters.ChefILoveAdatper;
import com.imcooking.adapters.CuisionAdatper;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.webservices.GetData;

import java.util.ArrayList;
import java.util.List;

public class ChefILove extends AppBaseActivity {
    RecyclerView recyclerView;
    String foodie_id = HomeFragment.foodie_id+"";
    private List<ChefIloveData.ChefloveBean>chefloveBeanList= new ArrayList<>();
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_ilove);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

//        find id
        recyclerView = findViewById(R.id.activity_chef_ilove_recycler);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getChefLove();
        setChefLove();
    }
    ChefIloveData chefIloveData = new ChefIloveData();

    private void getChefLove(){
        String request = "{\"foodie_id\":\"21\"}";
        new GetData(getApplicationContext(), ChefILove.this).getResponse(request, "cheflove", new GetData.MyCallback() {
            @Override
            public void onSuccess(String result) {
                chefIloveData = gson.fromJson(result, ChefIloveData.class);
                chefloveBeanList.addAll(chefIloveData.getCheflove());
                Log.d("TAG", "onSuccess: "+result);
                setChefLove();
            }
        }
        );
    }

    private void setChefLove(){
         ChefILoveAdatper chefILoveAdatper = new ChefILoveAdatper(getApplicationContext(), chefloveBeanList);
        recyclerView.setAdapter(chefILoveAdatper);

        Log.d("VK", chefloveBeanList.size() + "");
    }

}
