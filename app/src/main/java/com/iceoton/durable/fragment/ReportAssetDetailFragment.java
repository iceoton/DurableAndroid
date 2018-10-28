package com.iceoton.durable.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iceoton.durable.R;
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

/**
 * Fragment แสดงหน้ารายละเอียดของครุภัณฑ์ ของรายงาน
 */
public class ReportAssetDetailFragment extends Fragment implements View.OnClickListener {
    final static String TAG = ReportAssetDetailFragment.class.getSimpleName();
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

    private String assetCode;
    AssetDetail assetDetail;
    private ArrayList<AssetDetail.Location> listLocations;
    private ArrayList<AssetDetail.Status> listStatus;

    public ReportAssetDetailFragment() {
        // Required empty public constructor
    }

    public static ReportAssetDetailFragment newInstance(Bundle bundle) {
        ReportAssetDetailFragment fragment = new ReportAssetDetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void setupView() {
        postQueryAssetDetail(assetCode);
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
        View rootView = inflater.inflate(R.layout.fragment_report_asset_detail, container, false);
        ButterKnife.bind(this, rootView);
        setupView();

        return rootView;
    }

    @Override
    public void onClick(View v) {

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

    /**
     * ติดต่อไปที่ api เพื่อดึงรายละเอียดของครุภัณฑ์ เมือดึงสำเร็จ ฟังก์ชัน setAssetDetailValue() จะถูกเรียกใช้งานต่อไป
     * สิ่งที่ต้องใช้ในการดึงรายละเอียดของครุภัณฑ์คือ รหัสครุภัณฑ์
     * @param assetCode รหัสครุภัณฑ์ที่ต้องการรายละเอียด
     */
    private void postQueryAssetDetail(final String assetCode) {
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
                            postQueryAssetDetail(assetCode);
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

    /**
     * ติดต่อไปที่ api เพื่อดึงข้อมูลสถานที่เก็บครุภัณฑ์
     * @param loadingDialog ตัวแสดง loading ในขณะที่กำหลังโหลดข้อมูล
     */
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

    /**
     * ติดต่อไปที่ api เพื่อดึงข้อมูลสถานะของครุภัณฑ์
     * @param loadingDialog ตัวแสดง loading ในขณะที่กำหลังโหลดข้อมูล
     */
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

    /**
     * ทำการแสดงค่ารายละเอียดครุภัณฑ์ ลงบน view
     * @param assetDetail รายละเอียดของครุภัณฑ์ที่ต้องการทำการแสดง
     */
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
}
