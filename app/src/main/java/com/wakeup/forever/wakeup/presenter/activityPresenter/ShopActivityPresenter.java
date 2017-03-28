package com.wakeup.forever.wakeup.presenter.activityPresenter;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.ShopDataManager;
import com.wakeup.forever.wakeup.model.bean.ExchangeRecord;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.activity.ShopActivity;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by forever on 2016/12/1.
 */

public class ShopActivityPresenter extends BeamBasePresenter<ShopActivity> {

    public void exchangeShop(int shopId){
        getView().showProgressDialog();
        ShopDataManager.getInstance().exchangeShop(shopId, new BaseSubscriber<HttpResult<ExchangeRecord>>(getView()) {
            @Override
            public void onSuccess(HttpResult<ExchangeRecord> exchangeRecordHttpResult) {
                getView().dismissProgressDialog();
                if(exchangeRecordHttpResult.getResultCode()==200){
                    getView().onExchangeSuccess(exchangeRecordHttpResult.getData());
                }
                else{
                    ToastUtil.showText(exchangeRecordHttpResult.getMessage());
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().dismissProgressDialog();
                LogUtil.e("onError:"+e.toString());
                LogUtil.e(((HttpException) e).toString());
            }
        });
    }

    public void loadUrl(WebView webView,String url){
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

        webView.loadUrl(url);
    }
}
