package com.example.medisoft.Authentication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.medisoft.BaseActivity;
import com.example.medisoft.MainActivity;
import com.example.medisoft.Model.Example;
import com.example.medisoft.R;
import com.example.medisoft.Retrofit.APIClient;
import com.example.medisoft.Retrofit.GetResult;
import com.example.medisoft.Utils.AppPreference;
import com.example.medisoft.Utils.AppUtils;
import com.example.medisoft.Utils.CustPrograssbar;
import com.example.medisoft.Utils.MyApplication;
import com.example.medisoft.databinding.ActivityLoginBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;


public class LoginActivity extends BaseActivity implements View.OnClickListener, GetResult.MyListener {

    ActivityLoginBinding binding;
    CustPrograssbar custPrograssbar;
    String securityCode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        checkForSession();
        AppUtils.setSystemBarColor(this, R.color.tcolor);
        AppUtils.setSystemBarLight(this);
        custPrograssbar = new CustPrograssbar();
        initViews(binding);

    }

    private void checkForSession() {
        if (!AppPreference.getInstance(MyApplication.mContext).getString(AppPreference.USERNAME).equalsIgnoreCase("0")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void initViews(ActivityLoginBinding binding) {
        binding.btnLogin.setOnClickListener(this);
        binding.btnGosignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_login:

                String username = binding.inputPhone.getText().toString().trim();
                String password = binding.inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(binding.inputPhone.getText().toString().trim())) {

                    binding.inputPhone.setError("Please enter a  username");
                } else if (TextUtils.isEmpty(binding.inputPassword.getText().toString().trim())) {

                    binding.inputPassword.setError("Please enter a password");
                } else {

                    LoginwithCredentials(username, password);
                }


                break;

            case R.id.btn_gosignup:
                startActivity(new Intent(this, SignupActivity.class));
                finish();
                break;
        }
    }

    private void LoginwithCredentials(String username, String password) {

        custPrograssbar.progressCreate(this);
        custPrograssbar.setCancel(false);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().login((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "login");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if (callNo.equalsIgnoreCase("login")) {
            custPrograssbar.close();
            Gson gson = new Gson();
            Example am = gson.fromJson(result.toString(), Example.class);

            if (am.getResult().equalsIgnoreCase("true")) {

                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.USERNAME, am.getResultData().getUserDetails().getUserName());
                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.IS_ADMIN, am.getResultData().getUserDetails().getAdmin().toString());
                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.IS_SHOP, am.getResultData().getUserDetails().getIsShop().toString());
                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.IS_USER, am.getResultData().getUserDetails().getIsUser().toString());
                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.NAME, am.getResultData().getUserDetails().getName().toString());
                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.MOBILE, am.getResultData().getUserDetails().getMobile().toString());
                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.CLIENTID, am.getResultData().getUserDetails().getUserid());
                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.CLIENTNAME, am.getResultData().getUserDetails().getClientName());

                startActivity(new Intent(this, MainActivity.class));
                finish();

            } else {
                Toast.makeText(this, am.getResponseMsg(), Toast.LENGTH_SHORT).show();

            }
        }

    }
}