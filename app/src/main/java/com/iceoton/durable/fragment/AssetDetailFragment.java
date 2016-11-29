package com.iceoton.durable.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iceoton.durable.R;

public class AssetDetailFragment extends Fragment {

    public AssetDetailFragment() {
        // Required empty public constructor
    }

    public static AssetDetailFragment newInstance() {
        AssetDetailFragment fragment = new AssetDetailFragment();

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
        View rootView = inflater.inflate(R.layout.fragment_asset_detail, container, false);
        setupView(rootView);

        return rootView;
    }

    private void setupView(View rootView){


    }

}
