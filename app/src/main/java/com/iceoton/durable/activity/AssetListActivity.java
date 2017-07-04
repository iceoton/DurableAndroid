package com.iceoton.durable.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.iceoton.durable.R;
import com.iceoton.durable.fragment.AssetListFragment;


public class AssetListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_list);

        if(savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentContainer, AssetListFragment.newInstance(bundle))
                    .commit();
        }

    }
    
}
