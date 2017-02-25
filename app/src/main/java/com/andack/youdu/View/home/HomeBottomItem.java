package com.andack.youdu.View.home;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andack.youdu.R;
import com.andack.youdu.module.recommend.RecommandFooterValue;
import com.youdu.UIL.ImageLoaderManager;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/25
 * 邮箱：    1160083806@qq.com
 * 描述：    ListView头部的底部数据
 */

public class HomeBottomItem extends RelativeLayout{
    private Context context;
    private LayoutInflater inflater;

    /**
     * UI
     */
    private RelativeLayout mRootView;
    private TextView mTitleView;
    private TextView mInfoView;
    private TextView mInterestingView;
    private ImageView mImageOneView;
    private ImageView mImageTwoView;
    /**
     * Data
     */
    private RecommandFooterValue mData;
    private ImageLoaderManager imageLoaderManager;
    public HomeBottomItem(Context context,RecommandFooterValue mData)
    {
        this(context,null,mData);
    }

    public HomeBottomItem(Context context, AttributeSet attrs,RecommandFooterValue mData) {
        super(context, attrs);
        this.context=context;
        this.mData=mData;

        imageLoaderManager=ImageLoaderManager.getIntances(context);
        initView();
    }

    private void initView() {
        Log.i("Home", "initView: HomeBottomItem run");
        inflater=LayoutInflater.from(context);
        mRootView= (RelativeLayout) inflater.inflate(R.layout.item_home_recommand_layout,null);
        mTitleView= (TextView) mRootView.findViewById(R.id.title_view);
        mInfoView= (TextView) mRootView.findViewById(R.id.info_view);
        mInterestingView= (TextView) mRootView.findViewById(R.id.interesting_view);
        mImageOneView= (ImageView) mRootView.findViewById(R.id.icon_1);
        mImageTwoView= (ImageView) mRootView.findViewById(R.id.icon_2);
        mTitleView.setText(mData.title);
        mInfoView.setText(mData.info);
        mInterestingView.setText(mData.from);
        imageLoaderManager.displayImage(mImageOneView,mData.imageOne);

        imageLoaderManager.displayImage(mImageTwoView,mData.imageTwo);
    }
}
