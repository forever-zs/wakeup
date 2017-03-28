package com.wakeup.forever.wakeup.presenter.fragmentPresenter;

import android.content.pm.PackageManager;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.model.DataManager.VersionDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.VersionUpdate;
import com.wakeup.forever.wakeup.view.fragment.SettingFragment;

import rx.Subscriber;

/**
 * Created by forever on 2016/12/7.
 */

public class SettingFragmentPresenter extends BeamBasePresenter<SettingFragment> {

    public void checkUpdate() {
        try {
            final int versionCode = getView().getActivity().getPackageManager().getPackageInfo(getView().getActivity().getPackageName(), 0).versionCode;
            VersionDataManager.getInstance().getNewVersion(new Subscriber<HttpResult<VersionUpdate>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(HttpResult<VersionUpdate> versionUpdateHttpResult) {
                    if (versionUpdateHttpResult.getResultCode() == 200) {
                        if (versionUpdateHttpResult.getData().getVersionCode() > versionCode) {
                            getView().showUpdateDialog(versionUpdateHttpResult.getData());
                        }
                        else{
                            getView().showAlreadyNewDialog();
                        }
                    }
                }
            });
        } catch (PackageManager.NameNotFoundException e) {

        }


    }
}
