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
 * 描述：    我的界面
 */

public class MineFragment extends BaseFragmnent {
    private Context mMineContext;
    private View mMineView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMineContext=getActivity();
        mMineView=inflater.inflate(R.layout.fragment_mine_layout,container,false);
        return mMineView;
    }
}
