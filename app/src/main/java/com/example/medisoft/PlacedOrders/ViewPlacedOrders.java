package com.example.medisoft.PlacedOrders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.medisoft.Adapter.PurchaseOrderListAdapter;
import com.example.medisoft.Adapter.ViewPlacedOrdersAdapter;
import com.example.medisoft.Adapter.ViewStockAdapter;
import com.example.medisoft.BaseActivity;
import com.example.medisoft.Model.Example;
import com.example.medisoft.Model.OrderByClient;
import com.example.medisoft.Purchase.PurchaseOrderListActivity;
import com.example.medisoft.R;
import com.example.medisoft.Retrofit.APIClient;
import com.example.medisoft.Retrofit.GetResult;
import com.example.medisoft.Utils.AppPreference;
import com.example.medisoft.Utils.AppUtils;
import com.example.medisoft.Utils.CustPrograssbar;
import com.example.medisoft.Utils.MyApplication;
import com.example.medisoft.Utils.RecyclerViewClickInterface;
import com.example.medisoft.databinding.ActivityOtpactivityBinding;
import com.example.medisoft.databinding.ActivityViewPlacedOrdersBinding;
import com.example.medisoft.databinding.ViewplacedordersBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ViewPlacedOrders extends BaseActivity implements GetResult.MyListener, RecyclerViewClickInterface {

    ActivityViewPlacedOrdersBinding binding;
    CustPrograssbar custPrograssbar;
    List<OrderByClient> orderByClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewPlacedOrdersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AppUtils.setSystemBarColor(this, R.color.white);
        AppUtils.setSystemBarLight(this);
        custPrograssbar = new CustPrograssbar();
        initViews(binding);


    }

    private void initViews(ActivityViewPlacedOrdersBinding binding) {

        binding.tbcm.ivLogout.setVisibility(View.GONE);
        AppUtils.setSystemBarColor(this, R.color.white);
        AppUtils.setSystemBarLight(this);
         binding.tbcm.tbHead.setText("Previous Order History");

        binding.tbcm.ivMenu.setBackgroundResource(R.drawable.ic_baseline_arrow_back_24);
        binding.tbcm.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        getAllPlacedOrderByClient();

    }

    private void getAllPlacedOrderByClient() {
        custPrograssbar.progressCreate(this);
        custPrograssbar.setCancel(false);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("ClientID", AppPreference.getInstance(MyApplication.mContext).getString(AppPreference.CLIENTID));
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getPurchaseOrderByClient((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getPurchaseOrderByClient");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        custPrograssbar.close();
        if (callNo.equalsIgnoreCase("getPurchaseOrderByClient")) {

            Gson gson = new Gson();
            Example example = gson.fromJson(result.toString(), Example.class);
            orderByClients = new ArrayList<>();
            if (example.getResultData().getOrderByClient() != null) {
                orderByClients = example.getResultData().getOrderByClient();
                ViewPlacedOrdersAdapter adapter = new ViewPlacedOrdersAdapter(ViewPlacedOrders.this, orderByClients, this);
                binding.rcvPlacedorders.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvPlacedorders.setAdapter(adapter);

            } else {
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();

            }


        }

    }

    @Override
    public void onItemClick(int position, String chk) {

        if (chk.equalsIgnoreCase("view")) {

            startActivity(new Intent(this, PdfViewActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Your Download will start automatically", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(APIClient.baseUrl + "pdftest/generate-user-pdf.php?payee_id=" + AppPreference.getInstance(MyApplication.mContext).getString(AppPreference.CLIENTID)));
            startActivity(intent);
        }

    }
}