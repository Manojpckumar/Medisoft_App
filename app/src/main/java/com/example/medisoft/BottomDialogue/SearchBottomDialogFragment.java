package com.example.medisoft.BottomDialogue;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.medisoft.Model.Example;
import com.example.medisoft.Model.SupplierByClient;
import com.example.medisoft.R;
import com.example.medisoft.Retrofit.APIClient;
import com.example.medisoft.Retrofit.GetResult;
import com.example.medisoft.Utils.AppPreference;
import com.example.medisoft.Utils.MyApplication;
import com.example.medisoft.databinding.AddproductdialogueBinding;
import com.example.medisoft.databinding.BottomSheetBinding;
import com.example.medisoft.databinding.FragmentDashboardBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

public class SearchBottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener, GetResult.MyListener {

    public static final String TAG = "ActionBottomDialog";
    private ItemClickListener mListener;
    ArrayList<String> sp_list = new ArrayList<String>();
    List<SupplierByClient> supplier;
    BottomSheetBinding binding;
    final Calendar myCalendar = Calendar.getInstance();

    public static SearchBottomDialogFragment newInstance() {
        return new SearchBottomDialogFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = BottomSheetBinding.inflate(inflater, container, false);
        initViews(binding);
        getAllSupplierbyClient();
        return binding.getRoot();
    }

    private void initViews(BottomSheetBinding binding) {
        binding.dateFrom.setOnClickListener(this);
        binding.dateTo.setOnClickListener(this);
        binding.SupplierAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {

            }
        });

    }


    private void getAllSupplierbyClient() {
        String pattern = "dd-MM-yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        binding.dateTo.setText(dateInString);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("ClientID", AppPreference.getInstance(MyApplication.mContext).getString(AppPreference.CLIENTID));
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllSupplierbyClient((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "supplier_list");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.date_From:

                showDatepopup();
                break;

            case R.id.date_To:
                showDatepopup();

                break;


        }
        TextView tvSelected = (TextView) view;
        mListener.onItemClick(tvSelected.getText().toString());
        dismiss();


    }

    private void showDatepopup() {

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


        new DatePickerDialog(getContext(), date, myCalendar
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

    @Override
    public void callback(JsonObject result, String callNo) {


        if (callNo.equalsIgnoreCase("supplier_list")) {
            Gson gson = new Gson();
            Example example = gson.fromJson(result.toString(), Example.class);
            supplier = new ArrayList<>();
            if (example.getResultData().getSuppliern() != null) {
                supplier = example.getResultData().getSuppliern();
                for (SupplierByClient sn : supplier) {
                    sp_list.add(sn.getSupplierName());
                    Log.d("==", sn.getSupplierName());

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (getContext(), android.R.layout.simple_dropdown_item_1line, sp_list);
                binding.SupplierAuto.setThreshold(1);
                binding.SupplierAuto.setAdapter(adapter);


            }


        }

    }

    public interface ItemClickListener {
        void onItemClick(String item);
    }
}
