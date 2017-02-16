package com.andack.youdu.Fragment.home;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andack.youdu.Fragment.BaseFragmnent;
import com.andack.youdu.R;
import com.andack.youdu.networks.RequestCenter;
import com.youdu.okhttp.listener.DisposeDataListener;

import static android.content.ContentValues.TAG;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/13
 * 邮箱：    1160083806@qq.com
 * 描述：    Home的Fragment
 */

public class HomeFragment extends BaseFragmnent implements View.OnClickListener {
    private Context mHomeContext;
    private View mHomeView;
    private TextView QrcodeView;
    private TextView CategoryView;
    private ImageView loadingImageView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requsetRecommandData();
    }

    private void requsetRecommandData() {
        //进行一个应用层的封装
        RequestCenter.requestRecommandData(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                //完成我们真正的业务逻辑
                Log.i(TAG, "onSuccess: "+responseObj.toString());
            }

            @Override
            public void onFailure(Object reasonObj) {
                //提示网络有问题

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHomeContext=getActivity();
        mHomeView=inflater.inflate(R.layout.fragment_home_layout,container,false);
        initView(mHomeView);
        return mHomeView;
    }

    private void initView(View mHomeView) {
        QrcodeView= (TextView) mHomeView.findViewById(R.id.qrcode_view);
        QrcodeView.setOnClickListener(this);
        CategoryView= (TextView) mHomeView.findViewById(R.id.category_view);
        CategoryView.setOnClickListener(this);
        loadingImageView= (ImageView) mHomeView.findViewById(R.id.loading_view);
        AnimationDrawable anim=(AnimationDrawable) loadingImageView.getDrawable();
        anim.start();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
