package com.example.huanghuishun.myschedule.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huanghuishun.myschedule.R;


public class WalletFragment extends BaseFragment {
    private View view;

    public static WalletFragment newInstance(int index) {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wallet, container, false);
        return view;
    }


}
