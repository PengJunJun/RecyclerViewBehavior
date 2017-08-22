package com.mockitotest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.CapturesArguments;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class MainPresenterImplTest {

    @Mock
    public MainContact.MainView mView;

    @Mock
    public UserApi mUserApi;

    public MainContact.MainPresenter mPresenter;

    public ApiEntity<List<User>> mTestUserList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        RxUnitTestTools.openRxTools();
        mPresenter = new MainPresenterImpl();
        mPresenter.attach(mView);

        List<User> users = new ArrayList<>();
        User user = new User();
        user.avatar = "http://www.baidu.com";
        user.hot = 200L;
        user.id = 2L;
        user.name = "PengJunJun";
        user.sex = "F";
        users.add(user);
        mTestUserList = new ApiEntity<>();
        mTestUserList.status = true;
        mTestUserList.msg = "success";
        mTestUserList.data = users;
    }

    @Test
    public void testGetUser() {
        UserApi userApi = HttpManager.getInstance().getUserApi();

        TestSubscriber<ApiEntity<List<User>>> testSubscriber = new TestSubscriber<>();
        userApi.getUsers(1, 10).subscribe(testSubscriber);

        ApiEntity<List<User>> entity = testSubscriber.getOnNextEvents().get(0);
        assertNull(entity);
    }

    @Test
    public void testLoadingUserSuccess() {
        when(mUserApi.getUsers(1, 10)).thenReturn(Observable.just(mTestUserList));

        mPresenter.setUserApi(mUserApi);
        mPresenter.getUsers();

        verify(mUserApi).getUsers(1, 10);
        verify(mView).setData(mTestUserList.data);
        verify(mView).showSuccess();
    }

    @Test
    public void testLoadingUserError() {
        when(mUserApi.getUsers(1, 10)).thenReturn(Observable.just(mTestUserList));

        mPresenter.setUserApi(mUserApi);
        mPresenter.getUsers();

        verify(mView).showSuccess();
    }

    @Test
    public void testStringIntern() {
        String s = new String("1111");
        s.intern();
        String s2 = "1111";

        System.out.print(s == s2);
        System.out.print("\n");
        System.out.println(s.equals(s2));

        Assert.assertEquals(s, s2);
    }
}