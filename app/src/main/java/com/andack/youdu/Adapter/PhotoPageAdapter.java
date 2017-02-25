package com.andack.youdu.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.youdu.UIL.ImageLoaderManager;

import java.util.ArrayList;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/23
 * 邮箱：    1160083806@qq.com
 * 描述：    头部标题栏的适配器
 */

public class PhotoPageAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> strings;
    private boolean misMatch;
    private static final String TAG="Home";
    private ImageLoaderManager imageLoaderManager;
    public PhotoPageAdapter(Context mContext,ArrayList<String> strings,boolean isMatch)
    {
        this.mContext=mContext;
        this.strings=strings;
        this.misMatch=isMatch;
        imageLoaderManager=ImageLoaderManager.getIntances(mContext);
    }
    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        ImageView imageView;
        if (misMatch)
        {
            imageView=new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        }else {
            imageView=new ImageView(mContext);
        }
        imageLoaderManager.displayImage(imageView,strings.get(position));
        Log.i(TAG, "instantiateItem: run"+strings.get(position));
        container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewPager.LayoutParams.MATCH_PARENT);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
