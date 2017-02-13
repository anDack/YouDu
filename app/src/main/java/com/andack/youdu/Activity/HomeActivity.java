package com.andack.youdu.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andack.youdu.Activity.base.BaseActivity;
import com.andack.youdu.R;




/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/13
 * 邮箱：    1160083806@qq.com
 * 描述：    主页Activity
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout homeLinearlayout;
    private LinearLayout pondLinearlayout;
    private LinearLayout messageLinearlayout;
    private LinearLayout mineLinearlayout;

    private TextView homeView;
    private TextView pondView;
    private TextView messageView;
    private TextView mineView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        initView();
    }

    private void initView() {
        homeLinearlayout= (LinearLayout) findViewById(R.id.home_linearlayout);
        homeLinearlayout.setOnClickListener(this);
        pondLinearlayout= (LinearLayout) findViewById(R.id.pond_linearlayout);
        pondLinearlayout.setOnClickListener(this);
        messageLinearlayout= (LinearLayout) findViewById(R.id.message_linearlayout);
        messageLinearlayout.setOnClickListener(this);
        mineLinearlayout= (LinearLayout) findViewById(R.id.mine_linearlayout);
        mineLinearlayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_linearlayout:
                break;
            case R.id.pond_linearlayout:
                break;
            case R.id.message_linearlayout:
                break;
            case R.id.mine_linearlayout:
                break;
        }

    }
}
