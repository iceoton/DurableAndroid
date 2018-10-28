package com.iceoton.durable.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.iceoton.durable.R;
import com.iceoton.durable.fragment.ReportAssetDetailFragment;

/**
 * Activity แสดงหน้ารายงานที่เป็นรายละเอียด
 */
public class ReportAssetDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_detail);
        initialActionBar();
        if(savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, ReportAssetDetailFragment.newInstance(bundle))
                    .commit();
        }

    }

    /**
     * แทนที่ fragment ด้วย fragment ที่ส่งมา
     * @param fragment fragment ที่ต้องการแทนที่
     */
    public void placeFragmentToContrainer(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * ตั้งค่า bar ด้านบนของหน้านี้ให้เป็น icon ตรงเมนูให้เป็นลูกศรกดย้อนกลับ
     * และเมื่อกดแอพจะพาย้อนกลับไปหน้าก่อนหน้านี้
     */
    private void initialActionBar(){
        // Initializing Toolbar and setting it as the actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.title_asset_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }
}
