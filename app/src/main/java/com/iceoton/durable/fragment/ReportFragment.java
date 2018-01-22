package com.iceoton.durable.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iceoton.durable.R;
import com.iceoton.durable.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportFragment extends BaseFragment {
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

    private void setupView(View rootView) {
        webViewReport.getSettings().setJavaScriptEnabled(true);
        webViewReport.loadUrl("http://139.59.255.225/durable/dashboard/mobile/report.php");
        WebViewClient webViewClient= new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView  view, String  url){
                if (Uri.parse(url).getHost().equals("139.59.255.225")) {
                    // This is my web site, so do not override; let my WebView load the page
                    setBackArrowToolbar();
                    return false;
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

    private void setBackArrowToolbar() {
        if(getActivity() instanceof MainActivity) {
            (( MainActivity)getActivity()).setBackArrowIndicator();
        }
    }
}
