package com.andack.youdu.Fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andack.youdu.Fragment.BaseFragmnent;
import com.andack.youdu.R;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/13
 * 邮箱：    1160083806@qq.com
 * 描述：    Home的Fragment
 */

public class HomeFragment extends BaseFragmnent {
    private Context mHomeContext;
    private View mHomeView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHomeContext=getActivity();
        mHomeView=inflater.inflate(R.layout.fragment_home_layout,container,false);
        return mHomeView;
    }
}
