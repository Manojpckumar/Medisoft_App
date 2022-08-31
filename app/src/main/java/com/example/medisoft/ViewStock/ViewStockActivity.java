package com.example.medisoft.ViewStock;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.medisoft.Adapter.StockSearchAdapter;
import com.example.medisoft.Adapter.ViewStockAdapter;
import com.example.medisoft.BaseActivity;
import com.example.medisoft.Model.AllStock;
import com.example.medisoft.Model.Example;
import com.example.medisoft.R;
import com.example.medisoft.Retrofit.APIClient;
import com.example.medisoft.Retrofit.GetResult;
import com.example.medisoft.Utils.AppPreference;
import com.example.medisoft.Utils.AppUtils;
import com.example.medisoft.Utils.CustPrograssbar;
import com.example.medisoft.Utils.MyApplication;
import com.example.medisoft.Utils.RecyclerViewClickInterface;
import com.example.medisoft.databinding.ActivityViewStockBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

public class ViewStockActivity extends BaseActivity implements GetResult.MyListener, RecyclerViewClickInterface {

    ActivityViewStockBinding binding;
    CustPrograssbar custPrograssbar;
    private SearchView searchView;
    private ViewStockAdapter stockAdapter;
    StockSearchAdapter stockSearchAdapter;
    List<AllStock> allStocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewStockBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        AppUtils.setSystemBarColor(this, R.color.tcolor);
        AppUtils.setSystemBarLight(this);
//        binding.tbCommonNew.tbHead.setText("Stock List Activity");
//        binding.tbCommonNew.ivLogout.setVisibility(View.GONE);
//        binding.tbCommonNew.ivMenu.setBackgroundResource(R.drawable.ic_baseline_arrow_back_24);
//        binding.tbCommonNew.ivMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        initViews(binding);

        binding.searchToolbar.searchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.searchToolbar.edSearch.setVisibility(View.VISIBLE);
                binding.searchToolbar.tvPageTitle.setVisibility(View.GONE);
                binding.searchToolbar.edSearch.requestFocus();
                binding.searchToolbar.edSearch.setText("");
                binding.searchToolbar.searchItem.setVisibility(View.GONE);
                binding.searchToolbar.closeItem.setVisibility(View.VISIBLE);

            }
        });


        binding.searchToolbar.closeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.searchToolbar.edSearch.setVisibility(View.INVISIBLE);
                binding.searchToolbar.tvPageTitle.setVisibility(View.VISIBLE);
                binding.searchToolbar.searchItem.setVisibility(View.VISIBLE);
                binding.searchToolbar.closeItem.setVisibility(View.GONE);

            }
        });














        binding.searchToolbar.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });


    }

    void ItemFound() {
        binding.rcvViewStock.setVisibility(View.VISIBLE);
        binding.rcvNotfound.setVisibility(View.GONE);
    }

    void ItemNotFound() {
        binding.rcvViewStock.setVisibility(View.GONE);
        binding.rcvNotfound.setVisibility(View.VISIBLE);
    }


    private void initViews(ActivityViewStockBinding binding) {
        custPrograssbar = new CustPrograssbar();
       /* if (AppPreference.getInstance(MyApplication.mContext).getString(AppPreference.IS_ADMIN).equals("1")) {

            getStockByID("0");
        }*/
        getStockByID(AppPreference.getInstance(MyApplication.mContext).getString(AppPreference.CLIENTID));


    }

    private void getStockByID(String pref) {
        custPrograssbar.progressCreate(this);
        custPrograssbar.setCancel(false);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("ClientID", pref);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllStockByClient((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAllStockByClient");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void filter(String text){
        List<AllStock> temp = new ArrayList();
        for(AllStock d: allStocks){

            if(d.getProductName().toLowerCase().contains(text)){
                temp.add(d);
            }
        }
        stockSearchAdapter.updateList(temp);
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        custPrograssbar.close();
        if (callNo.equalsIgnoreCase("getAllStockByClient")) {

            Gson gson = new Gson();
            Example example = gson.fromJson(result.toString(), Example.class);
            allStocks = new ArrayList<>();
            if (example.getResultData().getAllStock() != null) {
                allStocks = example.getResultData().getAllStock();

               /* for(AllStock allStock : allStocks){

                    Toast.makeText(this, allStock.getProductName(), Toast.LENGTH_SHORT).show();
                }*/

                stockSearchAdapter = new StockSearchAdapter(this,allStocks);
                binding.rcvViewStock.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvViewStock.setAdapter(stockSearchAdapter);


            } else {

                Toast.makeText(this, "kittunnilla", Toast.LENGTH_SHORT).show();
//                ItemNotFound();

            }


        }

    }

    @Override
    public void onItemClick(int position, String chk) {

    }
}