package com.example.medisoft.PlacedOrders;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.medisoft.BaseActivity;
import com.example.medisoft.R;
import com.example.medisoft.Retrofit.APIClient;
import com.example.medisoft.Utils.AppPreference;
import com.example.medisoft.Utils.AppUtils;
import com.example.medisoft.Utils.CustPrograssbar;
import com.example.medisoft.Utils.MyApplication;
import com.example.medisoft.databinding.ActivityPdfViewBinding;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class PdfViewActivity extends BaseActivity implements DownloadFile.Listener, View.OnClickListener {

    ActivityPdfViewBinding binding;
    RemotePDFViewPager remotePDFViewPager;
    PDFPagerAdapter adapter;
    CustPrograssbar custPrograssbar;

    String baseurl = APIClient.baseUrl + "pdftest/generate-user-pdf.php?payee_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        custPrograssbar = new CustPrograssbar();
        setContentView(view);
        AppUtils.setSystemBarColor(this, R.color.white);
        AppUtils.setSystemBarLight(this);
        binding.tbCmn.ivLogout.setVisibility(View.GONE);
        binding.tbCmn.tbHead.setText("Purchase Order Activity");
        binding.tbCmn.ivMenu.setBackgroundResource(R.drawable.ic_baseline_arrow_back_24);
        binding.tbCmn.ivMenu.setOnClickListener(this);
        setDownloadButtonListener();
    }

    private void setDownloadButtonListener() {
        final Context ctx = this;
        final DownloadFile.Listener listener = this;
        remotePDFViewPager = new RemotePDFViewPager(ctx, baseurl + AppPreference.getInstance(MyApplication.mContext).getString(AppPreference.CLIENTID), listener);
        remotePDFViewPager.setId(R.id.pdfViewPager);
        custPrograssbar.progressCreate(this);
    }


    @Override
    public void onSuccess(String url, String destinationPath) {
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        updateLayout();
        custPrograssbar.close();
    }

    private void updateLayout() {
        binding.rootLt.removeAllViewsInLayout();
        binding.rootLt.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onFailure(Exception e) {
        Toast.makeText(this, "Please try again later", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adapter != null) {
            adapter.close();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.iv_menu:
                finish();

                break;
        }

    }
}