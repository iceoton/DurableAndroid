package com.iceoton.durable.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.iceoton.durable.R;
import com.iceoton.durable.adapter.ListLocationAdapter;
import com.iceoton.durable.adapter.ListStatusAdapter;
import com.iceoton.durable.model.AssetDetail;
import com.iceoton.durable.model.AssetDetailResponse;
import com.iceoton.durable.model.ListLocationResponse;
import com.iceoton.durable.model.ListStatusResponse;
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

public class AssetDetailFragment extends Fragment implements View.OnClickListener {
    final static String TAG = AssetDetailFragment.class.getSimpleName();
    @BindView(R.id.textAssetCode)
    TextView tvAssetCode;
    @BindView(R.id.textAssetName)
    TextView tvAssetName;
    @BindView(R.id.textAssetDescription)
    TextView tvAssetDescription;
    @BindView(R.id.textAssetCategory)
    TextView tvAssetCategory;
    @BindView(R.id.textAssetComeDate)
    TextView tvAssetComeDate;
    @BindView(R.id.textAssetLocation)
    TextView tvAssetLocation;
    @BindView(R.id.textAssetSource)
    TextView tvAssetSource;
    @BindView(R.id.textAssetStatus)
    TextView tvAssetStatus;
    @BindView(R.id.textAssetQuantity)
    TextView tvAssetQuantity;
    @BindView(R.id.button_manage)
    Button btnManage;

    private String assetCode;
    AssetDetail assetDetail;
    private ArrayList<AssetDetail.Location> listLocations;
    private ArrayList<AssetDetail.Status> listStatus;

    public AssetDetailFragment() {
        // Required empty public constructor
    }

    public static AssetDetailFragment newInstance(Bundle bundle) {
        AssetDetailFragment fragment = new AssetDetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void setupView() {
        postQueryAssetDetail();
        btnManage.setOnClickListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            assetCode = getArguments().getString("asset_code");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_asset_detail, container, false);
        ButterKnife.bind(this, rootView);
        setupView();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == btnManage) {
            final Dialog dialogManagement = new Dialog(getActivity());
            dialogManagement.setContentView(R.layout.dialog_custom_asset_management);
            Window windowDialogPickTime = dialogManagement.getWindow();
            windowDialogPickTime.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final Spinner spinnerLocation = (Spinner) dialogManagement.findViewById(R.id.spinner_location);
            final Spinner spinnerStatus = (Spinner) dialogManagement.findViewById(R.id.spinner_status);
            final Button btnCancel = (Button) dialogManagement.findViewById(R.id.button_cancel);
            final Button btnSave = (Button) dialogManagement.findViewById(R.id.button_save);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogManagement.dismiss();
                }
            });
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int selectedLocationId = (int) spinnerLocation.getSelectedItemId();
                    int selectedStatusId = (int) spinnerStatus.getSelectedItemId();
                    /*String str = "selected location=" + selectedLocationId + " status=" + selectedStatusId;
                    Toast.makeText(getActivity(),str, Toast.LENGTH_SHORT).show();*/
                    postEditAsset(selectedLocationId, selectedStatusId);
                    dialogManagement.dismiss();
                }
            });

            final SweetAlertDialog loadingDialog = ApiClient.getProgressDialog(getActivity());
            loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if(listLocations != null && listStatus != null) {
                        ListLocationAdapter adapterLocation = new ListLocationAdapter(getActivity(), listLocations);
                        ListStatusAdapter adapterStatus = new ListStatusAdapter(getActivity(), listStatus);
                        spinnerLocation.setAdapter(adapterLocation);
                        spinnerLocation.setSelection(findPositionInLocationList(listLocations, assetDetail.getLocation()));
                        spinnerStatus.setAdapter(adapterStatus);
                        spinnerStatus.setSelection(findPositionInStatusList(listStatus, assetDetail.getStatus()));
                        dialogManagement.show();
                    } else {

                    }
                }
            });

            getAllAssetLocation(loadingDialog);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("asset_code", assetCode);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            assetCode = savedInstanceState.getString("asset_code");
        }
    }

    private void postQueryAssetDetail() {
        if (InternetConnection.isNetworkConnected(getActivity())) {

            final SweetAlertDialog loadingDialog = ApiClient.getProgressDialog(getActivity());
            loadingDialog.show();

            JSONObject data = new JSONObject();
            try {
                data.put("code", assetCode);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call call = apiService.postGetAssetDetail("getAssetDetail", data.toString());
            call.enqueue(new Callback<AssetDetailResponse>() {

                @Override
                public void onResponse(Call<AssetDetailResponse> call, Response<AssetDetailResponse> response) {
                    loadingDialog.dismissWithAnimation();
                    if (response.code() == ResultCode.OK) {
                        if (response.body().getResult() != null) {
                            setAssetDetailValue(response.body().getResult());
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
                public void onFailure(Call<AssetDetailResponse> call, Throwable t) {
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
                            postQueryAssetDetail();
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

    private void getAllAssetLocation(final SweetAlertDialog loadingDialog) {
        if (InternetConnection.isNetworkConnected(getActivity())) {
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call call = apiService.postGetAllAssetLocation("getAllAssetLocation");
            call.enqueue(new Callback<ListLocationResponse>() {

                @Override
                public void onResponse(Call<ListLocationResponse> call, Response<ListLocationResponse> response) {
                    if (response.code() == ResultCode.OK) {
                        if (response.body().getResult() != null) {
                            listLocations = response.body().getResult();
                            getAllAssetStatus(loadingDialog);
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
                public void onFailure(Call<ListLocationResponse> call, Throwable t) {
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
                            getAllAssetLocation(loadingDialog);
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

    private void getAllAssetStatus(final SweetAlertDialog loadingDialog) {
        if (InternetConnection.isNetworkConnected(getActivity())) {
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call call = apiService.postGetAllAssetStatus("getAllAssetStatus");
            call.enqueue(new Callback<ListStatusResponse>() {

                @Override
                public void onResponse(Call<ListStatusResponse> call, Response<ListStatusResponse> response) {
                    if (response.code() == ResultCode.OK) {
                        if (response.body().getResult() != null) {
                            listStatus = response.body().getResult();
                            loadingDialog.dismissWithAnimation();
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
                public void onFailure(Call<ListStatusResponse> call, Throwable t) {
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
                            getAllAssetStatus(loadingDialog);
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

    private void postEditAsset(int newLocationId, int newStatusId) {
        if (InternetConnection.isNetworkConnected(getActivity())) {

            final SweetAlertDialog loadingDialog = ApiClient.getProgressDialog(getActivity());
            loadingDialog.show();

            JSONObject data = new JSONObject();
            try {
                data.put("id", assetDetail.getId());
                data.put("location_id", newLocationId);
                data.put("status_id", newStatusId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call call = apiService.postEditAsset("editAsset", data.toString());
            call.enqueue(new Callback<AssetDetailResponse>() {

                @Override
                public void onResponse(Call<AssetDetailResponse> call, Response<AssetDetailResponse> response) {
                    loadingDialog.dismissWithAnimation();
                    if (response.code() == ResultCode.OK) {
                        if (response.body().getResult() != null) {
                            setAssetDetailValue(response.body().getResult());
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
                public void onFailure(Call<AssetDetailResponse> call, Throwable t) {
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
                            postQueryAssetDetail();
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

    @DebugLog
    private void setAssetDetailValue(AssetDetail assetDetail) {
        if (assetDetail != null) {
            this.assetDetail = assetDetail;
            try {
                tvAssetCode.setText(assetDetail.getCode());
                tvAssetName.setText(assetDetail.getName());
                tvAssetDescription.setText(assetDetail.getDetail());
                tvAssetCategory.setText(assetDetail.getCategory().toString());
                tvAssetComeDate.setText(assetDetail.getComeDate());
                tvAssetLocation.setText(assetDetail.getLocation().toString());
                tvAssetSource.setText(assetDetail.getSource().toString());
                tvAssetStatus.setText(assetDetail.getStatus().toString());
                tvAssetQuantity.setText(String.valueOf(assetDetail.getQuantity()).concat(" ").concat(assetDetail.getUnit().getName()));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        } else {
            Log.d(TAG, "Can't set value to view, because asset detail value is null.");
        }
    }

    private int findPositionInLocationList(ArrayList<AssetDetail.Location> locations, AssetDetail.Location itemToFind){
        int position = 0;
        for (int i = 0; i < locations.size();i++) {
            if(locations.get(i).getCode().equals(itemToFind.getCode())) {
                position = i;
            }
        }

        return position;
    }

    private int findPositionInStatusList(ArrayList<AssetDetail.Status> statuses, AssetDetail.Status itemToFind){
        int position = 0;
        for (int i = 0; i < statuses.size();i++) {
            if(statuses.get(i).getCode().equals(itemToFind.getCode())) {
                position = i;
            }
        }

        return position;
    }
}
