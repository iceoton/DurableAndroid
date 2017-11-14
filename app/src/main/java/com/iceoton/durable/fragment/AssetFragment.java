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
    @BindView(R.id.layoutAssetType1)
    FrameLayout layoutAssetType1;
    @BindView(R.id.layoutAssetType2)
    FrameLayout layoutAssetType2;

    public AssetFragment() {
        // Required empty public constructor
    }

    public static AssetFragment newInstance() {
        AssetFragment fragment = new AssetFragment();

        return fragment;
    }

    private void setupView() {
        layoutAssetType1.setOnClickListener(this);
        layoutAssetType2.setOnClickListener(this);
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
        if(v == layoutAssetType1){
            openAssetListByType(1);
        } else if(v == layoutAssetType2){
            openAssetListByType(2);
        }
    }

    private void openAssetListByType(int typeId){
        Intent intentToAssetList = new Intent(getActivity(), AssetListActivity.class);
        intentToAssetList.putExtra(IntentParams.TYPE_ID, typeId);
        getActivity().startActivity(intentToAssetList);
    }
}
