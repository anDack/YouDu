package com.andack.youdu.Fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andack.youdu.Adapter.CourseAdapter;
import com.andack.youdu.Fragment.BaseFragmnent;
import com.andack.youdu.R;
import com.andack.youdu.View.home.HomeHeaderLayout;
import com.andack.youdu.module.recommend.BaseRecommandModel;
import com.andack.youdu.networks.RequestCenter;
import com.andack.youdu.zxing.app.CaptureActivity;
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
    private final static int REQUEST_QCODE=0x01;
    /**
     * UI
     */
    private View mContentView;
    private TextView QrcodeView;
    private TextView CategoryView;
    private ImageView loadingImageView;
    private ListView mListView;
    /**
     * Data
     */
    private CourseAdapter adapter;
    private BaseRecommandModel mRecommandData;

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
                /**
                 * 更新UI
                 */
                mRecommandData= (BaseRecommandModel) responseObj;
                showSuccessView();
            }

            @Override
            public void onFailure(Object reasonObj) {
                //提示网络有问题

            }
        });
    }

    private void showSuccessView() {
        if (mRecommandData.data.list==null && mRecommandData.data.list.size()==0) {
            showErrorView();
        }else {
            loadingImageView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            //为ListView添加表头部
            /**
             * 小贴士：
             * 一般Add可以添加多个
             * set只可以有一个
             */
            mListView.addHeaderView(new HomeHeaderLayout(mContext,mRecommandData.data.head));
            adapter=new CourseAdapter(mContext,mRecommandData.data.list);
            mListView.setAdapter(adapter);
        }
    }

    private void showErrorView() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext=getActivity();
        mContentView=inflater.inflate(R.layout.fragment_home_layout,container,false);
        initView();
        return mContentView;
    }

    private void initView() {
        QrcodeView= (TextView) mContentView.findViewById(R.id.qrcode_view);
        QrcodeView.setOnClickListener(this);
        CategoryView= (TextView) mContentView.findViewById(R.id.category_view);
        CategoryView.setOnClickListener(this);
        loadingImageView= (ImageView) mContentView.findViewById(R.id.loading_view);
        AnimationDrawable anim=(AnimationDrawable) loadingImageView.getDrawable();
        anim.start();
        mListView= (ListView) mContentView.findViewById(R.id.content_listview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qrcode_view:
                Intent intent=new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent,REQUEST_QCODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_QCODE:
                if (resultCode== Activity.RESULT_OK) {
                    String code=data.getStringExtra("SCAN_RESULT");
                    if (code.contains("http")||code.contains("https"))
                    {
                        
                    }else {
                        Toast.makeText(mContext, "no thing", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
