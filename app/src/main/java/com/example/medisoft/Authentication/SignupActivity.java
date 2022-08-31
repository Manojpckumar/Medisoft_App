package com.example.medisoft.Authentication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medisoft.BaseActivity;
import com.example.medisoft.MainActivity;
import com.example.medisoft.Model.ClientDetails;
import com.example.medisoft.Model.Example;
import com.example.medisoft.Model.ResponseCommon;
import com.example.medisoft.Model.ResultData;
import com.example.medisoft.R;
import com.example.medisoft.Retrofit.APIClient;
import com.example.medisoft.Retrofit.GetResult;
import com.example.medisoft.Utils.AppPreference;
import com.example.medisoft.Utils.AppUtils;
import com.example.medisoft.Utils.CustPrograssbar;
import com.example.medisoft.Utils.MyApplication;
import com.example.medisoft.databinding.ActivityLoginBinding;
import com.example.medisoft.databinding.ActivitySignupBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.BasePDFPagerAdapter;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;
import retrofit2.Call;

public class SignupActivity extends BaseActivity implements View.OnClickListener , GetResult.MyListener{
    BasePDFPagerAdapter adapter;
    RemotePDFViewPager remotePDFViewPager;
    EditText etPdfUrl;
    Button downlod;
    LinearLayout root;
    CustPrograssbar custPrograssbar;
    ActivitySignupBinding binding;

    String securityCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        AppUtils.setSystemBarColor(this, R.color.tcolor);
        AppUtils.setSystemBarLight(this);
        custPrograssbar = new CustPrograssbar();

        initViews(binding);

        if (getIntent() != null && getIntent().getData() != null) {
            Uri data = getIntent().getData();
            securityCode = data.getQueryParameter("shopId");

           getShopNameFromApi(securityCode);

        }

    }

    private void getShopNameFromApi(String securityCode) {

        custPrograssbar.progressCreate(this);
        custPrograssbar.setCancel(false);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("shop_id", securityCode);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getShopbyId((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getShopbyId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initViews(ActivitySignupBinding binding) {
        binding.btnLogin.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.btn_login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;

            case R.id.btn_signup:

                String name = binding.inputName.getText().toString();
                String email = binding.inputEmail.getText().toString();
                String phone = binding.inputPhonenumber.getText().toString();
                String password = binding.inputPassword.getText().toString();

                if(name.equalsIgnoreCase(""))
                {
                    Toast.makeText(this, "Enter valid name", Toast.LENGTH_SHORT).show();
                }
                else if(email.equalsIgnoreCase(""))
                {
                    Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show();
                }
                else if(phone.equalsIgnoreCase(""))
                {
                    Toast.makeText(this, "Enter valid phone", Toast.LENGTH_SHORT).show();
                }
                else if(password.equalsIgnoreCase(""))
                {
                    Toast.makeText(this, "Enter valid password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    startActivity(new Intent(this,OTPActivity.class)
                            .putExtra("inputName",binding.inputName.getText().toString())
                            .putExtra("inputEmail",binding.inputEmail.getText().toString())
                            .putExtra("inputPassword",binding.inputPassword.getText().toString())
                            .putExtra("inputMobile",binding.inputPhonenumber.getText().toString())
                            .putExtra("inputShopid",securityCode)
                    );
                    finish();
                }
        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if (callNo.equalsIgnoreCase("getShopbyId")) {
            custPrograssbar.close();
            Gson gson = new Gson();
            Example am = gson.fromJson(result.toString(), Example.class);
            String shopName = am.getResultData().getClientDetails().getClientName();
            binding.inputShopname.setText("Refferal "+shopName);
        }

    }
}