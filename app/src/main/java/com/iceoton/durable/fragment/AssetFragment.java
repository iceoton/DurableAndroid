package com.iceoton.durable.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iceoton.durable.R;

public class AssetFragment extends Fragment {

    public AssetFragment() {
        // Required empty public constructor
    }

    public static AssetFragment newInstance() {
        AssetFragment fragment = new AssetFragment();

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
        View rootView = inflater.inflate(R.layout.fragment_asset, container, false);

        return rootView;
    }



}
