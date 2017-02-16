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
        int type=getItemViewType(position);
        switch (type) {
            case CARD_TYPE_ONE:
                if (convertView==null) {
                    viewHolder=new ViewHolder();
//                    convertView=minflater.inflate()
                }
                break;
        }

        return null;
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
