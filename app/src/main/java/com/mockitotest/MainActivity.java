package com.mockitotest;

import android.Manifest;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContact.MainView {

    private MainContact.MainPresenter mMainPresenter;
    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;
    private List<String> mLanguageList;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainPresenter = new MainPresenterImpl();
        mMainPresenter.attach(this);
        mMainPresenter.getUsers();

        mLanguageList = Arrays.asList("Java", "Android", "Kotlin", "Linux", "Gradle", "C++"
                , "C#", "Python1", "Shell1", "Unix1", "Go1", "C###", "JavaScript1");
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CustomAdapter(this, mLanguageList);
        mRecyclerView.setAdapter(mAdapter);

        mImageView = (ImageView) findViewById(R.id.imageView);

        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe();
    }

    @Override
    public void setData(List<User> users) {
    }

    @Override
    public void showSuccess() {
    }
}
