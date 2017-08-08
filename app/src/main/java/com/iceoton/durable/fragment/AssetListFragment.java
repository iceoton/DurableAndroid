package com.iceoton.durable.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iceoton.durable.R;
import com.iceoton.durable.activity.AssetDetailActivity;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import hugo.weaving.DebugLog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssetListFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView rvAssetList;

    private int categoryId;

    public AssetListFragment() {
        // Required empty public constructor
    }

    @DebugLog
    public static AssetListFragment newInstance(Bundle bundle) {
        AssetListFragment fragment = new AssetListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(IntentParams.CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_asset_list, container, false);
        ButterKnife.bind(this, rootView);

        postQueryAssetList();

        return rootView;
    }

    private void postQueryAssetList() {
        if (InternetConnection.isNetworkConnected(getActivity())) {

            final SweetAlertDialog loadingDialog = ApiClient.getProgressDialog(getActivity());
            loadingDialog.show();

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            //TODO change to pos
            Call call = apiService.postGetAssetList("getAllAsset");
            call.enqueue(new Callback<AssetResponse>() {

                @Override
                public void onResponse(Call<AssetResponse> call, Response<AssetResponse> response) {
                    loadingDialog.dismissWithAnimation();
                    if (response.code() == ResultCode.OK) {
                        if (response.body().getResult() != null) {
                            final ArrayList<Asset> assets = response.body().getResult();
                            AssetRecyclerAdapter assetRecyclerAdapter = new AssetRecyclerAdapter(assets, getActivity());
                            rvAssetList.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rvAssetList.setAdapter(assetRecyclerAdapter);
                            ClickListener clickListener = new ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    //Toast.makeText(getActivity(), "item at " + assets.get(position).getCode(), Toast.LENGTH_SHORT).show();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("asset_code", assets.get(position).getCode());
                                    Intent intentToAssetDetailPage = new Intent(getActivity(), AssetDetailActivity.class);
                                    intentToAssetDetailPage.putExtras(bundle);
                                    getActivity().startActivity(intentToAssetDetailPage);
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            };
                            rvAssetList.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),rvAssetList, clickListener));

                        } else {
                            Log.d("DEBUG", getClass().getName() + " error: " + response.body().getErrorMessage());
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getActivity().getString(R.string.title_warning))
                                    .setContentText(response.body().getErrorMessage())
                                    .show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AssetResponse> call, Throwable t) {
                    loadingDialog.dismissWithAnimation();
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
                            postQueryAssetList();
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
