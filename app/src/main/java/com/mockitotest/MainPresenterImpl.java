package com.mockitotest;

import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bykj003 on 2017/7/28.
 */

public class MainPresenterImpl implements MainContact.MainPresenter {

    private MainContact.MainView mMainView;

    private UserApi mUserApi;

    @Override
    public void setUserApi(UserApi userApi) {
        this.mUserApi = userApi;
    }

    @Override
    public void getUsers() {
        mUserApi.getUsers(1, 10).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<ApiEntity<List<User>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ApiEntity<List<User>> listApiEntity) {
                        if (listApiEntity.status) {
                            mMainView.setData(listApiEntity.data);
                            mMainView.showSuccess();
                        }
                    }
                });
    }

    @Override
    public void attach(MainContact.MainView view) {
        this.mMainView = view;
        this.mUserApi = HttpManager.getInstance().getUserApi();
    }
}
