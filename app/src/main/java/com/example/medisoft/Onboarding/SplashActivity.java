package com.example.medisoft.Onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.medisoft.Authentication.LoginActivity;
import com.example.medisoft.BaseActivity;
import com.example.medisoft.R;
import com.example.medisoft.Utils.AppUtils;
import com.example.medisoft.Utils.CustPrograssbar;
import com.example.medisoft.databinding.ActivitySplashBinding;

public class SplashActivity extends BaseActivity {

    ActivitySplashBinding binding;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
//        showProgressWheel();
        AppUtils.setSystemBarColor(this, R.color.tcolor);
        AppUtils.setSystemBarLight(this);
        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
//                hideProgressWheel(true);
            }
        }, 3000);
    }
}