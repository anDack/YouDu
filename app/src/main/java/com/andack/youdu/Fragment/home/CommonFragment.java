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
 * 描述：    TODO
 */

public class CommonFragment extends BaseFragmnent {
    @Nullable
    private View mCommonView;
    private Context mCommonContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCommonContext=getActivity();
        mCommonView=inflater.inflate(R.layout.fragment_pond_layout,container,false);
        return mCommonView;
    }
}
