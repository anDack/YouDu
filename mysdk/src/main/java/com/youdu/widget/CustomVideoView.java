package com.youdu.widget;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.andack.mysdk.R;
import com.youdu.Content.SDKConstant;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/3/3
 * 邮箱：    1160083806@qq.com
 * 描述：    视频播放
 * 需要熟悉的内容是MediaPlayer
 */
//使用的接口解释1.OnPreparedListener监听是否准备成功，2，OnErrorListener监听是否播放失败，3，OnCompletionListener是否播放完成
    //全部是生命周期的回调函数
public class CustomVideoView extends RelativeLayout implements
        MediaPlayer.OnPreparedListener,MediaPlayer.OnInfoListener,MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener,MediaPlayer.OnBufferingUpdateListener,
        TextureView.SurfaceTextureListener, View.OnClickListener {
    /**
     * Constant
     */
    private ViewGroup parentContainer;
    private AudioManager audioManager;

    /**
     * View
     */
    private RelativeLayout mPlayerView;
    private TextureView mVideoView;

    /**
     * Data
     */
    private int ScreenWidth,mDestationHeight;//屏幕宽高数据

    public CustomVideoView(Context context, ViewGroup parentContainer) {
        super(context);
        this.parentContainer=parentContainer;
        //获取音频管理类
        audioManager= (AudioManager) getContext().
                getSystemService(Context.AUDIO_SERVICE);
        initView();
        initData();

    }
    //初始化一些数据,屏幕的宽高之类的
    private void initData() {
        DisplayMetrics dm=new DisplayMetrics();
        WindowManager wm= (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        ScreenWidth=dm.widthPixels;
        mDestationHeight= (int) (dm.heightPixels* SDKConstant.VIDEO_HEIGHT_PERCENT);
    }

    private void initView() {
        LayoutInflater layoutInflater= (LayoutInflater) getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //疑问
        mPlayerView= (RelativeLayout) layoutInflater.
                inflate(R.layout.xadsdk_video_player,this);
        mVideoView= (TextureView) mPlayerView.
                findViewById(R.id.xadsdk_player_video_textureView);
        mVideoView.setOnClickListener(this);
        mVideoView.setKeepScreenOn(true);
        mVideoView.setSurfaceTextureListener(this);
        initSmallLayoutMode();//初始化小屏情况
    }

    private void initSmallLayoutMode() {
    }

    /**
     * MediaPlayer相关方法的实现
     */
    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    /**
     * TextureView相关方法的实现
     */
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void onClick(View v) {

    }
}
