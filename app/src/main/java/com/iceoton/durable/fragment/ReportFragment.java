package com.iceoton.durable.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iceoton.durable.R;

/**
 * Created by jaturon on 10/31/2016 AD.
 */

public class ReportFragment extends Fragment {

    public static ReportFragment newInstance() {
        ReportFragment fragment = new ReportFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);
        setupView(rootView);

        return rootView;
    }

    private void setupView(View rootView) {

    }
}
