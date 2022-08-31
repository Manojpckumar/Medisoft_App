package com.example.medisoft.Purchase;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.medisoft.BaseActivity;
import com.example.medisoft.MainActivity;
import com.example.medisoft.Model.Example;
import com.example.medisoft.Model.SupplierByClient;
import com.example.medisoft.PlacedOrders.ViewPlacedOrders;
import com.example.medisoft.R;
import com.example.medisoft.Retrofit.APIClient;
import com.example.medisoft.Retrofit.GetResult;
import com.example.medisoft.Utils.AppPreference;
import com.example.medisoft.Utils.AppUtils;
import com.example.medisoft.Utils.MyApplication;
import com.example.medisoft.databinding.ActivityPurchaseOrderBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

public class PurchaseOrderActivity extends BaseActivity implements View.OnClickListener, GetResult.MyListener {

    ActivityPurchaseOrderBinding binding;
    ArrayList<String> ar = new ArrayList<String>();
    List<SupplierByClient> supplier;
    final Calendar myCalendar = Calendar.getInstance();
    Date date;
    SimpleDateFormat formatter;
    String current_date;
    DatePickerDialog datePicker;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurchaseOrderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getAllSupplierbyClient();
        AppUtils.setSystemBarColor(this, R.color.white);
        AppUtils.setSystemBarLight(this);
        datePicker = new DatePickerDialog(this);
        initViews(binding);
    }

    private void initViews(ActivityPurchaseOrderBinding binding) {

        date = new Date();
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        current_date = formatter.format(date);
        binding.dateTo.setText(current_date);
        binding.dateFrom.setOnClickListener(this);
        binding.dateTo.setOnClickListener(this);
        binding.porderSearch.setOnClickListener(this);
        binding.SupplierAuto.setTextColor(Color.BLACK);
        binding.tbCommon.ivLogout.setVisibility(View.GONE);
        binding.tbCommon.tbHead.setText("Purchase Order Activity");

        binding.tbCommon.ivMenu.setBackgroundResource(R.drawable.ic_baseline_arrow_back_24);

        binding.tbCommon.ivMenu.setOnClickListener(this);
        binding.tvViewplacedorders.setOnClickListener(this);

    }

    private void getAllSupplierbyClient() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("ClientID", AppPreference.getInstance(MyApplication.mContext).getString(AppPreference.CLIENTID));
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllSupplierbyClient((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "login");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_menu:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;

            case R.id.date_From:

                showDatepopupFrom();
                break;

            case R.id.date_To:

                showDatepopupFrom();
                break;


            case R.id.porder_search:

                String sp_name = binding.SupplierAuto.getText().toString();
                String pd_days = binding.spnPeriod.getSelectedItem().toString();
                String sp_sort = binding.spSort.getSelectedItem().toString();
                String date_from = binding.dateFrom.getText().toString();
                String date_to = binding.dateTo.getText().toString();

                if (sp_name.isEmpty()) {

                    AppUtils.showToast(this, "Please select a supplier name");
                } else if (date_from.isEmpty() || date_to.isEmpty()) {

                    AppUtils.showToast(this, "Please choose a date range");
                } else {

                    startActivity(new Intent(this, PurchaseOrderListActivity.class)
                            .putExtra("sp_name", sp_name)
                            .putExtra("dt_from", date_from)
                            .putExtra("dt_to", date_to));

                }


                break;

            case R.id.tv_viewplacedorders:

                startActivity(new Intent(this, ViewPlacedOrders.class));
                break;


        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {
        Gson gson = new Gson();
        Example example = gson.fromJson(result.toString(), Example.class);
        supplier = new ArrayList<>();
        if (example.getResultData().getSuppliern() != null) {
            supplier = example.getResultData().getSuppliern();
            for (SupplierByClient sn : supplier) {
                ar.add(sn.getSupplierName());
                Log.d("==", sn.getSupplierName());

            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_dropdown_item_1line, ar);
            binding.SupplierAuto.setThreshold(1);
            binding.SupplierAuto.setAdapter(adapter);
        }

    }


    private void showDatepopupFrom() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };


        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();



    }

    private void updateLabel() {

        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (binding.dateFrom.getText().toString().isEmpty()) {
            binding.dateFrom.setText(sdf.format(myCalendar.getTime()));
        } else {
            binding.dateTo.setText(sdf.format(myCalendar.getTime()));


        }
    }


}
