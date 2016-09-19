package com.example.huanghuishun.myschedule.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.huanghuishun.myschedule.R;

/**
 * Created by huanghuishun on 2016/9/19.
 */
public class AddWalletItemActivity extends BaseActivityWithToolbar {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet_item);
        initView();
    }
    private void initView(){
        isBackButtonEnabled(true);
        getToolbar().setTitle(R.string.activity_add_wallet_item);
    }
}
