package com.iceoton.durable.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.iceoton.durable.R;
import com.iceoton.durable.util.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;


public class ForgetPasswordFragment extends Fragment {
    EditText etEmail;
    Button btnForgetPassword;
    private ProgressDialog progressBar;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    public static ForgetPasswordFragment newInstance() {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forget_password, container, false);
        initialView(rootView);
        return rootView;
    }

    private void initialView(View rootView) {
        etEmail = (EditText) rootView.findViewById(R.id.edit_email);
        btnForgetPassword = (Button) rootView.findViewById(R.id.button_forget_password);
        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.show();
                sendForgetPasswordToServer(etEmail.getText().toString().trim());
            }
        });

        progressBar = new ProgressDialog(getActivity());
        progressBar.setCancelable(false);
        progressBar.setMessage(" กำลังทำงาน...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void sendForgetPasswordToServer(String email) {
        JSONObject data = new JSONObject();
        try {
            data.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppPreference preference = new AppPreference(getActivity());
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(preference.getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CanomCakeService canomCakeService = retrofit.create(CanomCakeService.class);
        Call callApiService = canomCakeService.sendForgetPassword("forgetPassword", data.toString());
        callApiService.enqueue(new Callback<ForgetPasswordResponse>() {

            @Override
            public void onResponse(Call<ForgetPasswordResponse> callApiService, Response<ForgetPasswordResponse> response) {
                progressBar.hide();
                if (response.body().getSuccessValue() == 1) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("ลืมรหัสผ่าน");
                    alertDialog.setMessage("รหัสผ่านใหม่ได้ส่งไปที่ email ของคุณแล้ว");
                    alertDialog.setIcon(R.mipmap.ic_launcher);
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().onBackPressed();
                        }
                    });
                    alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            getActivity().onBackPressed();
                        }
                    });
                    alertDialog.show();

                } else {
                    Log.d("DEBUG", "Login error: " + response.body().getErrorMessage());
                    Toast.makeText(getActivity(), "ไม่พบอีเมลนี้ ลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ForgetPasswordResponse> callApiService, Throwable t) {
                Log.d("DEBUG", "Call CanomCake-API failure." + "\n" + t.getMessage());
                progressBar.hide();
            }
        });*/
    }

}
