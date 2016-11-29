package com.iceoton.durable.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iceoton.durable.R;
import com.iceoton.durable.model.Asset;

public class AssetDetailFragment extends Fragment {
    private Asset asset;

    public AssetDetailFragment() {
        // Required empty public constructor
    }

    public static AssetDetailFragment newInstance(String json) {
        AssetDetailFragment fragment = new AssetDetailFragment();
        Bundle args = new Bundle();
        args.putString("json", json);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String json = getArguments().getString("json");
            asset = new Gson().fromJson(json, Asset.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_asset_detail, container, false);
        setupView(rootView);

        return rootView;
    }

    private void setupView(View rootView) {
        TextView tvJson = (TextView) rootView.findViewById(R.id.textJson);
        tvJson.setText(new Gson().toJson(asset));
    }

}
