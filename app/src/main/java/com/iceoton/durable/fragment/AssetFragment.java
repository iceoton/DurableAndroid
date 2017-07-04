package com.iceoton.durable.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.iceoton.durable.R;
import com.iceoton.durable.activity.AssetListActivity;
import com.iceoton.durable.model.IntentParams;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssetFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.layoutCat1)
    FrameLayout layoutCat1;
    @BindView(R.id.layoutCat2)
    FrameLayout layoutCat2;

    public AssetFragment() {
        // Required empty public constructor
    }

    public static AssetFragment newInstance() {
        AssetFragment fragment = new AssetFragment();

        return fragment;
    }

    private void setupView() {
        layoutCat1.setOnClickListener(this);
        layoutCat2.setOnClickListener(this);
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
        View rootView = inflater.inflate(R.layout.fragment_asset, container, false);
        ButterKnife.bind(this, rootView);
        setupView();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v == layoutCat1){
            openAssetListByCategory(0);
        } else if(v == layoutCat2){
            openAssetListByCategory(0);
        }
    }

    private void openAssetListByCategory(int categoryId){
        Intent intentToAssetList = new Intent(getActivity(), AssetListActivity.class);
        intentToAssetList.putExtra(IntentParams.CATEGORY_ID, categoryId);
        getActivity().startActivity(intentToAssetList);
    }
}
