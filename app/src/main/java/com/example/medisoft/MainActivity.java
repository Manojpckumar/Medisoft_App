package com.example.medisoft;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.medisoft.Authentication.LoginActivity;
import com.example.medisoft.Fragment.DashboardFragment;
import com.example.medisoft.Fragment.POrderFragment;
import com.example.medisoft.Fragment.ProfileFragment;
import com.example.medisoft.Fragment.ReportFragment;
import com.example.medisoft.Retrofit.GetResult;
import com.example.medisoft.Utils.AppPreference;
import com.example.medisoft.Utils.AppUtils;
import com.example.medisoft.Utils.MyApplication;
import com.example.medisoft.databinding.ActivityMainBinding;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.gson.JsonObject;

public class MainActivity extends BaseActivity implements OnFragmentInteractionListener, View.OnClickListener, GetResult.MyListener {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);
        AppUtils.setSystemBarColor(this, R.color.tcolor);
        AppUtils.setSystemBarLight(this);
        initViews(binding);

        LoadFragment(new DashboardFragment(), "Dashboard");

    }

    private void initViews(ActivityMainBinding binding) {
//        binding.tbCommon.ivMenu.setBackgroundResource(R.drawable.menu);
//        binding.tbCommon.ivLogout.setOnClickListener(this);
        binding.equalNavigationBar.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch (position) {
                    case 0:
                        LoadFragment(new DashboardFragment(), "Dashboard");
                        break;

                    case 1:
                        LoadFragment(new ReportFragment(), "Reports");
                        break;

                    case 2:
                        LoadFragment(new ProfileFragment(), "Profile");
                        break;


                }

            }
        });

    }

    private void LoadFragment(Fragment fragment, String stackvalue) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.Frame_1, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_logout:
                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.USERNAME, "0");
                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.IS_ADMIN, "0");
                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.CLIENTID, "0");
                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.CLIENTNAME, "0");
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                AppUtils.showToast(this, "Loggged out successfully");

                break;

        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {

    }
}