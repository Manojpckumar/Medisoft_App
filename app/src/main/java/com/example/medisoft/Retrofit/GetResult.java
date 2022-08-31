package com.example.medisoft.Retrofit;

import android.util.Log;
import android.widget.Toast;

import com.example.medisoft.Utils.AppUtils;
import com.example.medisoft.Utils.MyApplication;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetResult {
    public static MyListener myListener;

    public void onNCHandle(Call<JsonObject> call, String res) {

        //IS NETWORK AVAILABLE PHASE FOR EACH CALLS IN FIRST PHASE SECURITY
        if(!AppUtils.isNetworkAvailable()){
            AppUtils.showToast(MyApplication.mContext,"Please Check Your Internet Connection");

        }else {

            //ENQUEUE METHOD FOR API HANDLING RETROFIT
            call.enqueue(new Callback<JsonObject>() {
                @Override

                //SUCCESS INTERFACE
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.e("message", " : " + response.message());
                    Log.e("body", " : " + response.body());
                    Log.e("callno", " : " + res);
                    myListener.callback(response.body(), res);
                }

                //ERROR INTERFACE LISTENER
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    Toast.makeText(MyApplication.mContext, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    myListener.callback(null, res);
                    call.cancel();
                    t.printStackTrace();
                }
            });

        }
    }

    public interface MyListener {

        public void callback(JsonObject result, String callNo);
    }
    public void setMyListener(MyListener Listener) {
        myListener = Listener;
    }
}
