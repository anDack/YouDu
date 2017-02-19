package com.andack.youdu.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andack.youdu.R;
import com.andack.youdu.module.recommend.RecommandBodyValue;
import com.youdu.UIL.ImageLoaderManager;

import java.util.ArrayList;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/18
 * 邮箱：    1160083806@qq.com
 * 描述：    类型三的ViewPager的适配器
 */

public class HotSalePagerAdapter extends PagerAdapter {
    private ArrayList<RecommandBodyValue> values;
    private LayoutInflater inflater;
    private Context mContext;
    private ImageLoaderManager imageLoaderManager;
    public HotSalePagerAdapter(Context context,ArrayList<RecommandBodyValue>recommandBodyValues){
        this.mContext=context;
        this.values=recommandBodyValues;
        imageLoaderManager=ImageLoaderManager.getIntances(mContext);//单例设计模式
        inflater=LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        /**
         * 因为要无限循环
         */
//        return values.size();
        return Integer.MAX_VALUE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final RecommandBodyValue value=values.get(position%values.size());
        View rootView=inflater.inflate(R.layout.item_hot_product_pager_layout,null);
        TextView titleView= (TextView) rootView.findViewById(R.id.title_view);
        TextView infoView= (TextView) rootView.findViewById(R.id.info_view);
        TextView gonggaoView= (TextView) rootView.findViewById(R.id.gonggao_view);
        TextView saleView= (TextView) rootView.findViewById(R.id.sale_num_view);
        ImageView[] imageViews=new ImageView[3];
        imageViews[0]= (ImageView) rootView.findViewById(R.id.image_one);
        imageViews[1]= (ImageView) rootView.findViewById(R.id.image_two);;
        imageViews[2]= (ImageView) rootView.findViewById(R.id.image_three);
        titleView.setText(value.title);
        infoView.setText(value.price);
        gonggaoView.setText(value.info);
        saleView.setText(value.text);
        for (int i = 0; i < imageViews.length; i++) {
            imageLoaderManager.displayImage(imageViews[i],value.url.get(i));
        }
        container.addView(rootView,0);
        return rootView;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
