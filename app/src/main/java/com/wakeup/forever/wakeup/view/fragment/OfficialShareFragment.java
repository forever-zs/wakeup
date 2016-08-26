package com.wakeup.forever.wakeup.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.presenter.fragmentPresenter.OfficialShareFragmentPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
@RequiresPresenter(OfficialShareFragmentPresenter.class)
public class OfficialShareFragment extends BeamFragment<OfficialShareFragmentPresenter> {


    public OfficialShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_official_share, container, false);
    }

}
