package com.andack.youdu.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andack.youdu.Activity.base.BaseActivity;
import com.andack.youdu.Fragment.home.CommonFragment;
import com.andack.youdu.Fragment.home.HomeFragment;
import com.andack.youdu.Fragment.home.MessageFragment;
import com.andack.youdu.Fragment.home.MineFragment;
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

    private FragmentManager fm;
    private FragmentTransaction fragmentTransaction;

    private HomeFragment homeFragment;
    private CommonFragment pondFragment;
    private MineFragment mineFragment;
    private MessageFragment messageFragment;
    private Fragment mCurrentFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        initView();
        homeFragment=new HomeFragment();
        fm=getFragmentManager();
        //如果是V4的包，就用getSupportFragmentManager
        fragmentTransaction=fm.beginTransaction();
        //replace是相当于add
        fragmentTransaction.replace(R.id.content_layout,homeFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        homeView= (TextView) findViewById(R.id.home_image_view);
        pondView= (TextView) findViewById(R.id.pond_image_view);
        messageView= (TextView) findViewById(R.id.message_image_view);
        mineView= (TextView) findViewById(R.id.mine_image_view);
        changeImagePic(true,false,false,false);
    }

    @Override
    public void onClick(View v) {
        fragmentTransaction=fm.beginTransaction();
        switch (v.getId()) {
            case R.id.home_linearlayout:
                changeImagePic(true,false,false,false);
                hideFragment(fragmentTransaction,pondFragment);
                hideFragment(fragmentTransaction,messageFragment);
                hideFragment(fragmentTransaction,mineFragment);
                if (homeFragment==null)
                {
                    homeFragment=new HomeFragment();
                    fragmentTransaction.add(R.id.content_layout,homeFragment);
                }else {
                    mCurrentFragment=homeFragment;
                    fragmentTransaction.show(homeFragment);
                }
                break;
            case R.id.pond_linearlayout:
                changeImagePic(false,true,false,false);
                hideFragment(fragmentTransaction,homeFragment);
                hideFragment(fragmentTransaction,messageFragment);
                hideFragment(fragmentTransaction,mineFragment);
                if (pondFragment==null)
                {
                    pondFragment=new CommonFragment();
                    fragmentTransaction.add(R.id.content_layout,pondFragment);
                }else {
                    mCurrentFragment=pondFragment;
                    fragmentTransaction.show(pondFragment);
                }
                break;
            case R.id.message_linearlayout:
                changeImagePic(false,false,true,false);
                hideFragment(fragmentTransaction,pondFragment);
                hideFragment(fragmentTransaction,homeFragment);
                hideFragment(fragmentTransaction,mineFragment);
                if (messageFragment==null)
                {
                    messageFragment=new MessageFragment();
                    fragmentTransaction.add(R.id.content_layout,messageFragment);
                }else {
                    mCurrentFragment=messageFragment;
                    fragmentTransaction.show(messageFragment);
                }
                break;
            case R.id.mine_linearlayout:
                changeImagePic(false,false,false,true);
                hideFragment(fragmentTransaction,pondFragment);
                hideFragment(fragmentTransaction,messageFragment);
                hideFragment(fragmentTransaction,homeFragment);
                if (mineFragment==null)
                {
                    mineFragment=new MineFragment();
                    fragmentTransaction.add(R.id.content_layout,mineFragment);
                }else {
                    mCurrentFragment=mineFragment;
                    fragmentTransaction.show(mineFragment);
                }
                break;
        }
        fragmentTransaction.commit();//写的时候要成对的写
    }
    private void hideFragment(FragmentTransaction ft, Fragment hideFragment)
    {
        if (hideFragment!=null) {
            ft.hide(hideFragment);
        }
    }
    /**
     * 改变下标的颜色
     * @param isHomeImage       Home页面
     * @param isPondImage       池塘页面
     * @param isMessageImage    消息页面
     * @param isMineImage       我的页面
     */
    private void changeImagePic(boolean isHomeImage,boolean isPondImage,
                                boolean isMessageImage,boolean isMineImage)
    {
        if (isHomeImage) {
            homeView.setBackgroundResource(R.drawable.comui_tab_home_selected);
            pondView.setBackgroundResource(R.drawable.comui_tab_pond);
            messageView.setBackgroundResource(R.drawable.comui_tab_message);
            mineView.setBackgroundResource(R.drawable.comui_tab_person);
        }else if (isPondImage){
            homeView.setBackgroundResource(R.drawable.comui_tab_home);
            pondView.setBackgroundResource(R.drawable.comui_tab_pond_selected);
            messageView.setBackgroundResource(R.drawable.comui_tab_message);
            mineView.setBackgroundResource(R.drawable.comui_tab_person);
        }else if (isMessageImage){
            homeView.setBackgroundResource(R.drawable.comui_tab_home);
            pondView.setBackgroundResource(R.drawable.comui_tab_pond);
            messageView.setBackgroundResource(R.drawable.comui_tab_message_selected);
            mineView.setBackgroundResource(R.drawable.comui_tab_person);
        }else if (isMineImage)
        {
            homeView.setBackgroundResource(R.drawable.comui_tab_home);
            pondView.setBackgroundResource(R.drawable.comui_tab_pond);
            messageView.setBackgroundResource(R.drawable.comui_tab_message);
            mineView.setBackgroundResource(R.drawable.comui_tab_person_selected);
        }
    }


}
