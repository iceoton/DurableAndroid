package com.iceoton.durable.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iceoton.durable.R;
import com.iceoton.durable.activity.MainActivity;
import com.iceoton.durable.activity.ReportActivity;
import com.iceoton.durable.model.IntentParams;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment แสดงหน้ารายงานครุภัณฑ์
 */
public class ReportFragment extends BaseFragment {
    private static final String TAG = ReportActivity.class.getSimpleName();
    @BindView(R.id.web_view)
    WebView webViewReport;

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
        ButterKnife.bind(this, rootView);
        setupView(rootView);

        return rootView;
    }

    /**
     * ทำการสร้างตัวแปรที่เชื่อมต่อกับ view ต่างๆ และตั้งค่าว่าเมื่อกดที่ view ต่างๆ จะเกิดอะไรขึ้น
     * @param rootView view หลักของหน้านี้
     */
    private void setupView(View rootView) {
        webViewReport.getSettings().setJavaScriptEnabled(true);
        webViewReport.loadUrl("http://139.59.255.225/durable/dashboard/mobile/report.php");
        WebViewClient webViewClient= new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView  view, String  url){
                if (Uri.parse(url).getHost().equals("139.59.255.225")) {

                    /**
                     * เมื่อมีการคลิกที่ link ต่างๆของ report แอพจะพาไปหน้าของ report ตามชนิดการจัดการนั้นๆ
                     */
                    String lastPathSegment = Uri.parse(url).getLastPathSegment();
                    Log.d(TAG, "LastPathSegment = " + lastPathSegment);
                    if(lastPathSegment.equalsIgnoreCase("report_pickup.php")) {
                        openReportAssetList(1);
                    } else if(lastPathSegment.equalsIgnoreCase("report_borrow.php")) {
                        openReportAssetList(2);
                    } else if(lastPathSegment.equalsIgnoreCase("report_return.php")) {
                        openReportAssetList(3);
                    } else if(lastPathSegment.equalsIgnoreCase("report_repair.php")) {
                        openReportAssetList(4);
                    } else {
                        // This is my web site, so do not override; let my WebView load the page
                        setBackArrowToolbar();
                        return false;
                    }

                    return true;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);
                if(!webView.canGoBack()) {
                    if(getActivity() instanceof MainActivity) {
                        (( MainActivity)getActivity()).setDefaultToolbarHamberger();
                    }
                }
            }
        };
        webViewReport.setWebViewClient(webViewClient);
    }

    /**
     * Back pressed send from activity.
     *
     * @return if event is consumed, it will return true.
     */
    public boolean onBackPressed() {
        if (webViewReport.canGoBack()) {
            webViewReport.goBack();
            return true;
        } else {
            return false;
        }
    }

    /**
     * ตั้งค่า icon ตรงเมนูให้เป็นลูกศรกดย้อนกลับ และเมื่อกดแอพจะพาย้อนกลับไปหน้าก่อนหน้านี้
     */
    private void setBackArrowToolbar() {
        if(getActivity() instanceof MainActivity) {
            (( MainActivity)getActivity()).setBackArrowIndicator();
        }
    }

    /**
     * เปิดไปหน้ารายงาน การจัดการครุภัณฑ์
     * @param manageType ชนิดของการจัดการครุภัณฑ์
     */
    private void openReportAssetList(int manageType){
        Intent intentToReport = new Intent(getActivity(), ReportActivity.class);
        intentToReport.putExtra(IntentParams.MANAGE_TYPE, manageType);
        getActivity().startActivity(intentToReport);
    }


}
