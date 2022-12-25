package com.example.seniorcustomview.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/11 23:03
 **/
public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return loadView(inflater, container);
    }

    private View loadView(LayoutInflater inflater, ViewGroup container) {
        int resId = getResourceId();
        View rootView = inflater.inflate(resId, container, false);
        initView(rootView);
        return rootView;
    }

    protected void initView(View rootView) {

    }

    protected abstract int getResourceId();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
