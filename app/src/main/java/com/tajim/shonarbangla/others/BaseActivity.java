package com.tajim.shonarbangla.others;

import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class BaseActivity extends AppCompatActivity {
    public void jsonArrayReq(Boolean loading ,String url , JSONArray jsonArray, JsonArrayCallBack jsonArrayCallBack){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        if (loading) startLoading();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                endLoading();
                jsonArrayCallBack.onSuccess(jsonArray);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                endLoading();
                Log.e("appLog", volleyError.toString());
                Toast.makeText(BaseActivity.this, "Unknown Error - Volley", Toast.LENGTH_SHORT).show();
                jsonArrayCallBack.onFailed();

            }
        });
        requestQueue.add(request);

    }


    
    
    
    
    
    AlertDialog loadingAlert;

    protected void startLoading() {
        ProgressBar progressBar = new ProgressBar(this);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(progressBar)
                .setCancelable(false)
                .create();

        dialog.show();

        dialog.getWindow().setLayout(175, 175);

        loadingAlert = dialog;
    }

    protected void endLoading() {
        if (loadingAlert != null && loadingAlert.isShowing()) {
            loadingAlert.dismiss();
        }
    }

    public interface JsonArrayCallBack {
        void onSuccess(JSONArray result);
        void onFailed();

    }
}
