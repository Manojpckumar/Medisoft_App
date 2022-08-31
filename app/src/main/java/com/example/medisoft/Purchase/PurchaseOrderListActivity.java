package com.example.medisoft.Purchase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.medisoft.Adapter.PurchaseOrderListAdapter;
import com.example.medisoft.BaseActivity;
import com.example.medisoft.BottomDialogue.AddProductBottomDialogue;
import com.example.medisoft.BottomDialogue.SearchBottomDialogFragment;
import com.example.medisoft.MainActivity;
import com.example.medisoft.Model.Example;
import com.example.medisoft.Model.PurchaseOrder;
import com.example.medisoft.PlacedOrders.ViewPlacedOrders;
import com.example.medisoft.R;
import com.example.medisoft.Retrofit.APIClient;
import com.example.medisoft.Retrofit.GetResult;
import com.example.medisoft.Utils.AppPreference;
import com.example.medisoft.Utils.AppUtils;
import com.example.medisoft.Utils.MyApplication;
import com.example.medisoft.Utils.RecyclerViewClickInterface;
import com.example.medisoft.databinding.ActivityPurchaseOrderListBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class PurchaseOrderListActivity extends BaseActivity implements View.OnClickListener,GetResult.MyListener, RecyclerViewClickInterface, SearchBottomDialogFragment.ItemClickListener, AddProductBottomDialogue.ItemClickListener {

    ActivityPurchaseOrderListBinding binding;
    List<PurchaseOrder> purchaseOrders;
    PurchaseOrderListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurchaseOrderListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AppUtils.setSystemBarColor(this, R.color.white);
        AppUtils.setSystemBarLight(this);

        getPurchaseOrderList(getIntent().getStringExtra("sp_name"),
                getIntent().getStringExtra("dt_from"), getIntent().getStringExtra("dt_to"));

        initViews(binding);

    }

    private void initViews(ActivityPurchaseOrderListBinding binding) {
        binding.ibAddproduct.setOnClickListener(this);
        binding.ibFilter.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
    }

    private void getPurchaseOrderList(String sp_names, String dt_froms, String dt_tos) {

        binding.animationView.setVisibility(View.VISIBLE);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Client_ID", AppPreference.getInstance(MyApplication.mContext).getString(AppPreference.CLIENTID));
            jsonObject.put("Supplier_Name", sp_names);
            jsonObject.put("B_From_Date", dt_froms);
            jsonObject.put("B_To_Date", dt_tos);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getPurchaseOrderList((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "purchaselist");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void showAddProductBottomSheet() {
        AddProductBottomDialogue addPhotoBottomDialogFragment =
                AddProductBottomDialogue.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                AddProductBottomDialogue.TAG);
    }

    @Override
    public void onItemClick(String item) {

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if (callNo.equalsIgnoreCase("purchaselist")) {
            Gson gson = new Gson();
            Example example = gson.fromJson(result.toString(), Example.class);
            purchaseOrders = new ArrayList<>();
            if (example.getResultData().getPurchaseOrder() != null) {
                binding.animationView.setVisibility(View.GONE);
                binding.bar.setVisibility(View.VISIBLE);
                binding.btnSave.setVisibility(View.VISIBLE);
                purchaseOrders = example.getResultData().getPurchaseOrder();
                 adapter = new PurchaseOrderListAdapter(PurchaseOrderListActivity.this,purchaseOrders,this);
                binding.rcvOrderList.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvOrderList.setAdapter(adapter);
            } else {
                binding.animationView.setVisibility(View.GONE);
                AppUtils.showToast(this, example.getResult());
            }


        }

    }

    @Override
    public void onItemClick(int position, String chk) {

        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        Double sum = Double.valueOf(chk);
        binding.totalSum.setText(df.format(sum) + "");
        adapter.setItems(purchaseOrders);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.ib_addproduct:
                showAddProductBottomSheet();
                break;

            case R.id.ib_filter:
                startActivity(new Intent(this, PurchaseOrderActivity.class));
                break;

            case R.id.btn_save:
                Toast.makeText(this, "Server encountered a problem", Toast.LENGTH_SHORT).show();
                break;


        }

    }
}