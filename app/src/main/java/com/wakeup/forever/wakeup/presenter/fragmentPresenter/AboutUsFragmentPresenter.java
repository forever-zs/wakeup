package com.wakeup.forever.wakeup.presenter.fragmentPresenter;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.view.fragment.AboutUsFragment;

/**
 * Created by forever on 2016/9/12.
 */
public class AboutUsFragmentPresenter extends BeamBasePresenter<AboutUsFragment> {

    public void loadUrl(WebView webView){
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    getView().setLoadingProgress(100, true);
                } else {
                    // 加载中
                    getView().setLoadingProgress(newProgress * 5, false);
                }
            }
        });

        webView.loadUrl(GlobalConstant.ABOUT_US_URL);
    }
}
