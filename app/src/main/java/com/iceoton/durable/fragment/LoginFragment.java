package com.iceoton.durable.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iceoton.durable.R;
import com.iceoton.durable.activity.MainActivity;
import com.iceoton.durable.util.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;

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
                startMainActivity();
                //loginToServer(etUsername.getText().toString().trim(), etPassword.getText().toString());
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

    private void loginToServer(String username, String password) {
        JSONObject data = new JSONObject();
        try {
            data.put("username", username);
            data.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppPreference preference = new AppPreference(getActivity());
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(preference.getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CanomCakeService canomCakeService = retrofit.create(CanomCakeService.class);
        Call call = canomCakeService.loginToServer("loginCustomer", data.toString());
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {

                if (response.body().getUser() != null) {
                    User user = response.body().getUser();
                    Log.d("DEBUG", "id = " + user.getId());
                    AppPreference appPreference = new AppPreference(getActivity());
                    appPreference.saveUserId(user.getId());
                    appPreference.saveUserName(user.getEmail());
                    appPreference.saveLoginStatus(true);

                    startMainActivity();
                } else {
                    Log.d("DEBUG", "Login error: " + response.body().getErrorMessage());
                    Toast.makeText(getActivity(), "ไม่พบอีเมลนี้หรือรหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Log.d("DEBUG", "Call CanomCake-API failure." + "\n" + t.getMessage());
            }
        });*/
    }


    private void startMainActivity() {
        Intent intentToMain = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intentToMain);
        getActivity().finish();
    }

}
