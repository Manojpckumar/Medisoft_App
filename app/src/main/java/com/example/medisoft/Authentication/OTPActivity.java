package com.example.medisoft.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.medisoft.BaseActivity;
import com.example.medisoft.Model.Example;
import com.example.medisoft.Model.ResponseCommon;
import com.example.medisoft.R;
import com.example.medisoft.Retrofit.APIClient;
import com.example.medisoft.Retrofit.GetResult;
import com.example.medisoft.Utils.AppUtils;
import com.example.medisoft.Utils.CustPrograssbar;
import com.example.medisoft.databinding.ActivityLoginBinding;
import com.example.medisoft.databinding.ActivityOtpactivityBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import retrofit2.Call;

public class OTPActivity extends BaseActivity implements GetResult.MyListener, View.OnClickListener {

    ActivityOtpactivityBinding binding;
    CustPrograssbar custPrograssbar;

    Random ran ;
    String nxt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AppUtils.setSystemBarColor(this, R.color.tcolor);
        AppUtils.setSystemBarLight(this);
        custPrograssbar = new CustPrograssbar();
        initViews(binding);
        ran = new Random();

        nxt = String.format("%04d",ran.nextInt(10000));
        sendOTP(getIntent().getStringExtra("inputMobile"),nxt);
    }

    private void sendOTP(String inputMobile,String otp) {

        custPrograssbar.progressCreate(this);
        custPrograssbar.setCancel(false);
        JSONObject jsonObject = new JSONObject();
        Log.d("otp4Send", String.valueOf(nxt));

        try {
            jsonObject.put("mobile", inputMobile);
            jsonObject.put("random_otp", otp);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().verifyMobile((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "verifyMobile");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initViews(ActivityOtpactivityBinding binding) {

        binding.tvResendOtp.setOnClickListener(this);
        binding.tvVerifyotp.setOnClickListener(this);

    }

    @Override
    public void callback(JsonObject result, String callNo) {


        if (callNo.equalsIgnoreCase("verifyMobile")) {
            custPrograssbar.close();
            Gson gson = new Gson();

            ResponseCommon common = gson.fromJson(result.toString(), ResponseCommon.class);

            if (common.getResult().equalsIgnoreCase("true")) {
//                Toast.makeText(this, String.valueOf(nxt), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "OTP send succesfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }


        }
        else  if (callNo.equalsIgnoreCase("register")) {

            custPrograssbar.close();
            Gson gson = new Gson();

            ResponseCommon common = gson.fromJson(result.toString(), ResponseCommon.class);

            if (common.getResult().equalsIgnoreCase("true")) {

                Toast.makeText(this, common.getResponseMsg(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(this,LoginActivity.class));
                finish();

            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.tv_resendOtp:
                nxt = String.format("%04d",ran.nextInt(10000));
                sendOTP(getIntent().getStringExtra("inputMobile"),nxt);
                break;

            case R.id.tv_verifyotp:

                String OTP = binding.inputOtp.getText().toString();

                if(OTP.equalsIgnoreCase(""))
                {
                    Toast.makeText(this, "Enter otp", Toast.LENGTH_SHORT).show();
                }
                else if(!binding.inputOtp.getText().toString().equals(nxt))
                {
                    Toast.makeText(this, "Enter valid otp", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "OTP verified ", Toast.LENGTH_SHORT).show();

                    registerUser();
                }
                break;

        }



    }

    private void registerUser() {

        String name = getIntent().getStringExtra("inputName");
        String email = getIntent().getStringExtra("inputEmail");
        String password = getIntent().getStringExtra("inputPassword");
        String mobile = getIntent().getStringExtra("inputMobile");
        String shopid = getIntent().getStringExtra("inputShopid");
        String role = "1";

        custPrograssbar.progressCreate(this);
        custPrograssbar.setCancel(false);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", name);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("role", role);
            jsonObject.put("mobile", mobile);
            jsonObject.put("shop_id", shopid);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().register((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "register");
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}