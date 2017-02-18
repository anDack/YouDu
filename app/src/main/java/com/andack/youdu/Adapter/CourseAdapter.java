package com.andack.youdu.Adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andack.youdu.R;
import com.andack.youdu.Utils.Util;
import com.andack.youdu.Utils.Utils;
import com.andack.youdu.module.recommend.RecommandBodyValue;
import com.youdu.UIL.ImageLoaderManager;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/16
 * 邮箱：    1160083806@qq.com
 * 描述：    课程Adapter的编写
 */

public class CourseAdapter extends BaseAdapter{
    private static final int CARD_COUNT=4;//卡片的种类
    private static final int VIDOE_TYPE=0x00;//视频种类的item
    private static final int CARD_TYPE_ONE=0x01;
    private static final int CARD_TYPE_TWO=0x02;
    private static final int CARD_TYPE_THREE=0x03;
    private Context mContext;
    private LayoutInflater minflater;
    private ImageLoaderManager imageLoaderManager;
    private ArrayList<RecommandBodyValue> bodyValues;
    private RecommandBodyValue bodyValue;
    public CourseAdapter(Context mContext,ArrayList<RecommandBodyValue> bodyValues)
    {
        this.mContext=mContext;
        this.bodyValues=bodyValues;
        minflater=LayoutInflater.from(mContext);
        /**
         * 图片加载引擎初始化
         */
        imageLoaderManager.getIntances(mContext);
    }
    @Override
    public int getCount() {
        return bodyValues.size();
    }

    @Override
    public Object getItem(int position) {
        return bodyValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 得到一共有几种类型的视频
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return CARD_COUNT;
    }

    /**
     * 得到当前视频类型的种类
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        RecommandBodyValue value= (RecommandBodyValue) getItem(position);
        return value.type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        final RecommandBodyValue value= (RecommandBodyValue) getItem(position);
        int type=getItemViewType(position);
        if (convertView==null) {
            switch (type) {
                case CARD_TYPE_ONE:
                    viewHolder=new ViewHolder();
                    convertView=minflater.inflate(R.layout.item_product_card_one_layout,parent,false);
                    viewHolder.mLogoView = (CircleImageView) convertView.findViewById(R.id.item_logo_view);
                    viewHolder.mTitleView = (TextView) convertView.findViewById(R.id.item_title_view);
                    viewHolder.mInfoView = (TextView) convertView.findViewById(R.id.item_info_view);
                    viewHolder.mFooterView = (TextView) convertView.findViewById(R.id.item_footer_view);
                    viewHolder.mFromView = (TextView) convertView.findViewById(R.id.item_from_view);
                    viewHolder.mPriceView = (TextView) convertView.findViewById(R.id.item_price_view);
                    viewHolder.mZanView = (TextView) convertView.findViewById(R.id.item_zan_view);
                    viewHolder.mProductLayout= (LinearLayout) convertView.findViewById(R.id.product_photo_layout);

                    break;
                case CARD_TYPE_TWO:
                    viewHolder = new ViewHolder();
                    convertView = minflater.inflate(R.layout.item_product_card_two_layout,parent, false);
                    viewHolder.mLogoView = (CircleImageView) convertView.findViewById(R.id.item_logo_view);
                    viewHolder.mTitleView = (TextView) convertView.findViewById(R.id.item_title_view);
                    viewHolder.mFromView = (TextView) convertView.findViewById(R.id.item_from_view);
                    viewHolder.mInfoView = (TextView) convertView.findViewById(R.id.item_info_view);
                    viewHolder.mFooterView = (TextView) convertView.findViewById(R.id.item_footer_view);
                    viewHolder.mPriceView = (TextView) convertView.findViewById(R.id.item_price_view);
                    viewHolder.mProductView= (ImageView) convertView.findViewById(R.id.product_photo_view);
                    viewHolder.mZanView = (TextView) convertView.findViewById(R.id.item_zan_view);
                    break;
                case CARD_TYPE_THREE:
                    viewHolder=new ViewHolder();
                    convertView=minflater.inflate(R.layout.item_product_card_three_layout,parent,false);
                    viewHolder.mViewPager= (ViewPager) convertView.findViewById(R.id.pager);
                    ArrayList<RecommandBodyValue> recommandBoyList= Util.handleData(value);
                    viewHolder.mViewPager.setPageMargin(Utils.dip2px(mContext,12));
                    viewHolder.mViewPager.setAdapter(new HotSalePagerAdapter(mContext,recommandBoyList));
                    //左右都可以无限轮播,所以我们设置当前的图片为中间部分的图片
                    viewHolder.mViewPager.setCurrentItem(recommandBoyList.size()*100);
                    break;
            }
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();

        }
        /**
         * 因为有不同的数据类型，所以我们填充数据的时候也应当不同
         */
        switch (type) {
            case CARD_TYPE_ONE:
                imageLoaderManager.displayImage(viewHolder.mLogoView,value.logo);
                viewHolder.mTitleView.setText(value.title);
                viewHolder.mFromView.setText(value.from.concat("天前"));//concat是类似与加号的东西
                viewHolder.mInfoView.setText(value.info);
                viewHolder.mZanView.setText("点赞".concat(value.zan));
                viewHolder.mPriceView.setText(value.price);
                viewHolder.mFooterView.setText(value.text);

                //否则会重叠
                viewHolder.mProductLayout.removeAllViews();
                //动态添加到我们的水平ScrollView

                for (String url : value.url) {
                    viewHolder.mProductLayout.addView(createImageView(url));
                }
//                viewHolder.mProductLayout.setOnClickListener(new );
                break;
            case CARD_TYPE_TWO:
                imageLoaderManager.displayImage(viewHolder.mLogoView,value.logo);
                viewHolder.mTitleView.setText(value.title);
                viewHolder.mFromView.setText(value.from.concat("天前"));//concat是类似与加号的东西
                viewHolder.mInfoView.setText(value.info);
                viewHolder.mZanView.setText("点赞".concat(value.zan));
                viewHolder.mPriceView.setText(value.price);
                viewHolder.mFooterView.setText(value.text);
                imageLoaderManager.displayImage(viewHolder.mProductView,value.url.get(0));
                break;
            case CARD_TYPE_THREE:
                break;
        }

        return null;
    }

    /**
     * 动态创建我们的ImageView
     * @param url
     * @return
     */
    private View createImageView(String url) {
        ImageView photoView=new ImageView(mContext);
        //这里我们设置每个图片显示的参数,与ViewGroup应当相同
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(
                Utils.dip2px(mContext,100),LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.leftMargin=Utils.dip2px(mContext,5);
        photoView.setLayoutParams(layoutParams);
        imageLoaderManager.displayImage(photoView,url);
        return photoView;
    }

    class ViewHolder{
        //添加共有属性
        private CircleImageView mLogoView;
        private TextView mTitleView;
        private TextView mInfoView;
        private TextView mFooterView;
        //video Card 属性
        private RelativeLayout mVideoContentLayout;
        private ImageView mShareView;
        //video card外所有View的属性
        private TextView mPriceView;
        private TextView mFromView;
        private TextView mZanView;
        //Card One具有的属性
        private LinearLayout mProductLayout;
        //Card Two具有的属性
        private ImageView mProductView;
        //Card Three具有的属性
        private ViewPager mViewPager;
    }
}
