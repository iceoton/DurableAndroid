package com.iceoton.durable.rest;

import android.content.Context;
import android.graphics.Color;

import com.iceoton.durable.R;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * สำหรับสร้าง client ด้วย Retrofit Library เพื่อติดต่อไปที่ API
 */
public class ApiClient {
    public static final String BASE_URL = "http://10.0.2.2:8888/durable/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * สร้าง loading dialog สำหรับแสดงเวลาแอพประมวลผลอะไรที่ต้องใช้เวลารอ
     * เช่น การติดต่อไปที่ API
     * @param context Context ของหน้าที่จะแสดง loading
     * @return loading dialog
     */
    public static SweetAlertDialog getProgressDialog(Context context) {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(context.getString(R.string.loading));
        pDialog.setCancelable(false);

        return pDialog;
    }
}
