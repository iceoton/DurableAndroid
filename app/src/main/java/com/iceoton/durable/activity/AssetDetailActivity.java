package com.iceoton.durable.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.iceoton.durable.R;
import com.iceoton.durable.fragment.AssetDetailFragment;

public class AssetDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_detail);

        if(savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, AssetDetailFragment.newInstance(bundle))
                    .commit();
        }

    }
}
