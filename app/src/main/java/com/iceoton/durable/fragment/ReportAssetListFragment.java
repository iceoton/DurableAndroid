package com.iceoton.durable.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.iceoton.durable.R;
import com.iceoton.durable.activity.ReportAssetDetailActivity;
import com.iceoton.durable.adapter.AssetRecyclerAdapter;
import com.iceoton.durable.listener.ClickListener;
import com.iceoton.durable.listener.RecyclerTouchListener;
import com.iceoton.durable.model.Asset;
import com.iceoton.durable.model.AssetResponse;
import com.iceoton.durable.model.IntentParams;
import com.iceoton.durable.rest.ApiClient;
import com.iceoton.durable.rest.ApiInterface;
import com.iceoton.durable.rest.ResultCode;
import com.iceoton.durable.util.InternetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import hugo.weaving.DebugLog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment แสดงหน้ารายการของครุภัณฑ์ในรายงาน
 */
public class ReportAssetListFragment extends Fragment {
    private static final String TAG = ReportAssetListFragment.class.getSimpleName();
    @BindView(R.id.recycler_view)
    RecyclerView rvAssetList;
    @BindView(R.id.editTextSearch)
    EditText editTextSearch;

    Call callApiService;
    ArrayList<Asset> assets = new ArrayList<>();
    AssetRecyclerAdapter assetRecyclerAdapter;

    private int manageType;

    public ReportAssetListFragment() {
        // Required empty public constructor
    }

    @DebugLog
    public static ReportAssetListFragment newInstance(Bundle bundle) {
        ReportAssetListFragment fragment = new ReportAssetListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            manageType = getArguments().getInt(IntentParams.MANAGE_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_asset_list, container, false);
        ButterKnife.bind(this, rootView);
        setupViews();
        postQueryReportAssetList("");

        return rootView;
    }

    /**
     * ทำการสร้างตัวแปรที่เชื่อมต่อกับ view ต่างๆ และตั้งค่าว่าเมื่อกดที่ view ต่างๆ จะเกิดอะไรขึ้น
     */
    private void setupViews() {
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(charSequence.length() >= 2) {
                    postQueryReportAssetList(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        assetRecyclerAdapter = new AssetRecyclerAdapter(assets, getActivity());
        rvAssetList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAssetList.setAdapter(assetRecyclerAdapter);
        ClickListener clickListener = new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(assets.size() > 0) {
                    //Toast.makeText(getActivity(), "item at " + assets.get(position).getCode(), Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("asset_code", assets.get(position).getCode());
                    Intent intentToAssetDetailPage = new Intent(getActivity(), ReportAssetDetailActivity.class);
                    intentToAssetDetailPage.putExtras(bundle);
                    getActivity().startActivity(intentToAssetDetailPage);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        };
        rvAssetList.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rvAssetList, clickListener));
    }

    /**
     * ติดต่อไปที่ api เพื่อดึงรายการครุภัณฑ์ สามารถระบุรหัสครุภัณฑ์เพื่อดึงเฉพาะครุภัณฑ์ที่ต้องการได้
     * @param queryAssetCode รหัสของครุภัณฑ์ที่ต้องการค้นหา สามารถส่งไปเฉพาะบางส่วนได้
     *                       หรือส่งไปเป็นค่าว่างเปล่า "" ก็จะได้รายการครุภัณฑ์ทั้งหมด
     */
    private void postQueryReportAssetList(final String queryAssetCode) {
        if (InternetConnection.isNetworkConnected(getActivity())) {
            if(callApiService != null){
                if(!callApiService.isCanceled()) {
                    callApiService.cancel();
                }
            }

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            JSONObject data = new JSONObject();
            try {
                data.put("manageType", manageType);
                data.put("queryAssetCode", queryAssetCode);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            callApiService = apiService.postGetAssetByType("getReport", data.toString());
            callApiService.enqueue(new Callback<AssetResponse>() {

                @Override
                public void onResponse(Call<AssetResponse> call, Response<AssetResponse> response) {
                    if (response.code() == ResultCode.OK) {
                        if (response.body().getResult() != null) {
                            assets.clear();
                            assets.addAll(response.body().getResult());
                            assetRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("DEBUG", getClass().getName() + " error: " + response.body().getErrorMessage());
                            assets.clear();
                            assetRecyclerAdapter.notifyDataSetChanged();
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getActivity().getString(R.string.title_warning))
                                    .setContentText(response.body().getErrorMessage())
                                    .show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AssetResponse> call, Throwable t) {
                    Log.d("DEBUG", getClass().getName() + " Call API failure." + "\n" + t.getMessage());
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
                            postQueryReportAssetList(queryAssetCode);
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


}
