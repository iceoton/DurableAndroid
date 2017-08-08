package com.iceoton.durable.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iceoton.durable.R;
import com.iceoton.durable.activity.MainActivity;
import com.iceoton.durable.model.User;
import com.iceoton.durable.model.UserResponse;
import com.iceoton.durable.rest.ApiClient;
import com.iceoton.durable.rest.ApiInterface;
import com.iceoton.durable.util.AppPreference;
import com.iceoton.durable.util.InternetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    EditText etUsername, etPassword;
    Button btnLogin;
    TextView txtForgetPassword;
    ImageView imgLogo;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initialView(rootView);

        return rootView;
    }

    private void initialView(View rootView) {
        etUsername = (EditText) rootView.findViewById(R.id.username);
        etPassword = (EditText) rootView.findViewById(R.id.password);
        btnLogin = (Button) rootView.findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startMainActivity();
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if(!username.isEmpty() && !password.isEmpty()) {
                    loginToServer(etUsername.getText().toString().trim(), etPassword.getText().toString());
                } else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getActivity().getString(R.string.title_warning))
                            .setContentText(getActivity().getString(R.string.please_enter_data))
                            .show();
                }
            }
        });

        txtForgetPassword = (TextView) rootView.findViewById(R.id.text_forget_password);
        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer, ForgetPasswordFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        imgLogo = (ImageView) rootView.findViewById(R.id.image_logo);
    }

    private void loginToServer(final String username, final String password) {
        if (InternetConnection.isNetworkConnected(getActivity())) {
            final SweetAlertDialog loadingDialog = ApiClient.getProgressDialog(getActivity());
            loadingDialog.show();

            JSONObject data = new JSONObject();
            try {
                data.put("username", username);
                data.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call call = apiService.userLogin("userLogin", data.toString());
            call.enqueue(new Callback<UserResponse>() {

                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    loadingDialog.dismissWithAnimation();
                    if (response.body().getResult() != null) {
                        User user = response.body().getResult();
                        Log.d("DEBUG", "id = " + user.getUserKey());
                        AppPreference appPreference = new AppPreference(getActivity());
                        appPreference.saveUserId(user.getUserKey());
                        appPreference.saveUserName(user.getFirstName() + "  " + user.getLastName());
                        appPreference.saveUserEmail(user.getEmail());
                        appPreference.saveLoginStatus(true);

                        startMainActivity();
                    } else {
                        Log.d("DEBUG", "Login error: " + response.body().getErrorMessage());
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(getActivity().getString(R.string.title_warning))
                                .setContentText(response.body().getErrorMessage())
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    loadingDialog.dismissWithAnimation();
                    Log.d("DEBUG", "Call API failure." + "\n" + t.getMessage());
                }
            });

        } else {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getActivity().getString(R.string.title_warning))
                    .setContentText(getActivity().getString(R.string.internet_connection_fail))
                    .setConfirmText(getActivity().getString(R.string.ok))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            loginToServer(username, password);
                        }
                    })
                    .setCancelText(getActivity().getString(R.string.cancel))
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }
    }


    private void startMainActivity() {
        Intent intentToMain = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intentToMain);
        getActivity().finish();
    }

}
