package com.iceoton.durable.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.iceoton.durable.R;
import com.iceoton.durable.fragment.ReportAssetListFragment;
import com.iceoton.durable.model.IntentParams;


public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initialActionBar();

        if(savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            int manageType = bundle.getInt(IntentParams.MANAGE_TYPE);
            if(manageType == 1) setTitle(R.string.title_report_pick_up);
            else if(manageType == 2) setTitle(R.string.title_report_borrow);
            else if(manageType == 3) setTitle(R.string.title_report_return);
            else if(manageType == 4) setTitle(R.string.title_report_repair);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentContainer, ReportAssetListFragment.newInstance(bundle))
                    .commit();
        }

    }

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
