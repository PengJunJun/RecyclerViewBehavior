package com.mockitotest;

import java.util.List;

/**
 * Created by bykj003 on 2017/7/28.
 */

interface MainContact {

    interface MainView {
        void setData(List<User> users);

        void showSuccess();
    }

    interface MainPresenter {
        void getUsers();

        void attach(MainView view);

        void setUserApi(UserApi userApi);
    }
}
