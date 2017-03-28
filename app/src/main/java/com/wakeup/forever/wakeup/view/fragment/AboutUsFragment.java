package com.wakeup.forever.wakeup.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.presenter.fragmentPresenter.AboutUsFragmentPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(AboutUsFragmentPresenter.class)
public class AboutUsFragment extends BeamFragment<AboutUsFragmentPresenter> {

    @Bind(R.id.wv_aboutUs)
    WebView wvAboutUs;
    @Bind(R.id.pb_load)
    ProgressBar pbLoad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        getPresenter().loadUrl(wvAboutUs);
    }

    public void setLoadingProgress(int progress, boolean finsh) {
        pbLoad.setProgress(progress);
        if(finsh){
            pbLoad.setVisibility(ProgressBar.GONE);
        }
    }
}
