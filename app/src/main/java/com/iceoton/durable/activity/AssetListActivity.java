package com.iceoton.durable.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.iceoton.durable.R;
import com.iceoton.durable.fragment.AssetListFragment;
import com.iceoton.durable.model.IntentParams;

/**
 * Activity แสดงหน้ารายการครุภัณฑ์ (list of asset)
 */
public class AssetListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_list);
        initialActionBar();

        if(savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            if(bundle.getInt(IntentParams.TYPE_ID) == 1) setTitle(R.string.title_durable_property);
            else if(bundle.getInt(IntentParams.TYPE_ID) == 2) setTitle(R.string.title_durable_material);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentContainer, AssetListFragment.newInstance(bundle))
                    .commit();
        }

    }

    /**
     * ตั้งค่า bar ด้านบนของหน้านี้ให้เป็น icon ตรงเมนูให้เป็นลูกศรกดย้อนกลับ
     * และเมื่อกดแอพจะพาย้อนกลับไปหน้าก่อนหน้านี้
     */
    private void initialActionBar(){
        // Initializing Toolbar and setting it as the actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.drawable.arrow_back);
        if (getSupportActionBar() != null) {
            /*getSupportActionBar().setTitle(strTitle);
            getSupportActionBar().setDisplayShowTitleEnabled(true);*/
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_indicator);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }
    
}
