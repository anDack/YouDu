package com.andack.youdu.View.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andack.youdu.R;
import com.andack.youdu.View.viewpagerindictor.CirclePageIndicator;
import com.andack.youdu.module.recommend.RecommandHeadValue;
import com.youdu.UIL.ImageLoaderManager;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/23
 * 邮箱：    1160083806@qq.com
 * 描述：    创建一个头部的布局
 */

public class HomeHeaderLayout extends RelativeLayout {
    private LayoutInflater inflater;
    private Context mContext;
    private RecommandHeadValue recommandHeadValue;
    private ImageLoaderManager imageLoaderManager;
    private RelativeLayout RootView;
    private AutoScrollViewPager pager;
    private CirclePageIndicator pageIndicator;
    private ImageView[] imageViews;
    private TextView hotView;
    private LinearLayout footerView;

    public HomeHeaderLayout(Context context, RecommandHeadValue recommandHeadValue)
    {
        this(context, null,recommandHeadValue);

    }
    public HomeHeaderLayout(Context context, AttributeSet attrs, RecommandHeadValue headerValue) {
        super(context, attrs);
        this.mContext=context;
        this.recommandHeadValue =headerValue;
        imageLoaderManager=ImageLoaderManager.getIntances(mContext);
        initView();
    }

    private void initView() {
        inflater=LayoutInflater.from(mContext);
        RootView= (RelativeLayout) inflater.inflate(R.layout.listview_home_head_layout,null);
        pager= (AutoScrollViewPager) RootView.findViewById(R.id.pager);
        pageIndicator= (CirclePageIndicator) RootView.findViewById(R.id.pager_indictor_view);
        imageViews=new ImageView[4];
        imageViews[0]= (ImageView) RootView.findViewById(R.id.head_image_one);
        imageViews[1]= (ImageView) RootView.findViewById(R.id.head_image_two);
        imageViews[2]= (ImageView) RootView.findViewById(R.id.head_image_three);
        imageViews[3]= (ImageView) RootView.findViewById(R.id.head_image_four);
        hotView= (TextView) RootView.findViewById(R.id.zuixing_view);
        footerView= (LinearLayout) RootView.findViewById(R.id.content_layout);



    }
}
