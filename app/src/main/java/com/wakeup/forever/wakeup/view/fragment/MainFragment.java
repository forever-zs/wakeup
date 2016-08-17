package com.wakeup.forever.wakeup.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.presenter.activityPresenter.MainActivityPresenter;
import com.wakeup.forever.wakeup.presenter.fragmentPresenter.MainPragmentPresenter;

@RequiresPresenter(MainActivityPresenter.class)
public class MainFragment extends BeamFragment<MainPragmentPresenter> {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container);
    }
}
