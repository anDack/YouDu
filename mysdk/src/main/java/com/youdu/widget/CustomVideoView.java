package com.youdu.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
    private static final String TAG="MraidViewView";        //这里的MRAID是 Media Rich AD Interface Definitions的意思
    private static final int TIME_MSG=0x01;                 //进行信息传输MSG
    private static final int TIME_INVAL=1000;               //每隔INVAL的时间发送TIME_MSG
    /**
     * STATE
     */
    private static final int STATE_ERROR=-1;                //状态错误
    private static final int STATE_IDLE=0;                  //idle状态
    private static final int STATE_PLAYING=1;               //正在播放状态
    private static final int STATE_PAUSING=2;               //正在暂停状态
    private static final int LOAD_TOTAL_COUNT=3;            //尝试重新加载的次数
    /**
     * STATE保护机制
     */
    private boolean mIsRealPause;
    private boolean mIsComplete;
    private int mCurrentCount;
    private int playerState=STATE_IDLE;                        //默认的状态为idle
    /**
     * View
     */
    private ViewGroup parentContainer;
    private RelativeLayout mPlayerView;
    private TextureView mVideoView;
    /**
     * UI控件
     */
    private Button mMiniPlayBtn;            //开始按钮
    private ImageView mFullBtn;             //全屏按钮
    private ImageView mLoadBar;             //加载动画
    private ImageView mFrameView;           //猜测可能是当前画面之类的功能
    private Surface videoSurface;

    /**
     * Data
     */
    private String mUrl;
    private String mFrameURI;
    private boolean isMute;                  // 是否静音
    private int ScreenWidth,mDestationHeight;//屏幕宽高数据


    private MediaPlayer mMediaPlayer;
    private ADVideoPlayerListener listener;                     //事件回调接口
    private ScreenEventReceiver mScreenEventReceiver;          //用来监听屏幕点击广播
    private ADFrameImageLoadListener mFrameImageListener;       //暂停图的回调接口
    private AudioManager audioManager;                         //音量控制器


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
        //添加布局属性
        LayoutParams params=new LayoutParams(ScreenWidth,mDestationHeight);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mPlayerView.setLayoutParams(params);
        //布局设置
        mMiniPlayBtn= (Button) mPlayerView.findViewById(R.id.xadsdk_small_play_btn);
        mLoadBar= (ImageView) mPlayerView.findViewById(R.id.loading_bar);
        mFullBtn= (ImageView) mPlayerView.findViewById(R.id.xadsdk_to_full_view);
        mFrameView= (ImageView) mPlayerView.findViewById(R.id.framing_view);
        mFullBtn.setOnClickListener(this);
        mMiniPlayBtn.setOnClickListener(this);

    }

    /**
     * 在View的显示发生改变的时候调用
     * 用来监听View是否在可见范围内,如果不是在可见范围内那么直接就visibility=GONE,反之则反
     * @param changedView
     * @param visibility
     */
    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    /**
     * MediaPlayer相关方法的实现
     */
    /**
     * 播放器准备完成
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    /**
     * 发生异常的时候调用
     * @param mp
     * @param what
     * @param extra
     * @return
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    /**
     * 在视频中播放完成后调用这个方法
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    /**
     * TextureView相关方法的实现
     */
    /**
     * 表明我们的帧数据处于显示状态
     * @param surface
     * @param width
     * @param height
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

    //功能性的方法

    /**
     * 加载我们的视频
     */
    public void load(){

    }

    /**
     * 暂停我们的数据
     */
    public void pause(){

    }

    /**
     * 恢复视频播放
     */
    public void resume(){

    }

    /**
     * 播放完成后的状态
     */
    public void playBack() {

    }

    /**
     * 处于停止状态
     */
    public void stop(){

    }

    /**
     * 销毁
     */
    public void destroy(){

    }

    public void setListener(ADVideoPlayerListener listener)
    {
        this.listener=listener;
    }
    //跳转到指定播放的视频
    public void seekAndResume(int position){

    }
    //跳转到指定地点暂停视频
    public void seekAndPause(int position){

    }
    @Override
    public void onClick(View v) {

    }
    /**
     * 自定义接口
     */
    public interface ADVideoPlayerListener{
        /**
         * 回调
         */
        void onBufferUpdate(int time);  //缓冲回调
        void onClickFullScreenBtn();    //全屏按钮被按下回调
        void onClickVideo();            //视频被点击回调
        void onClickBack();             //Back按钮点击回调
        void onClickPlay();             //Play按钮点击回调
        void onAdVideoLoadSuccess();    //视频加载成功
        void onAdVideoLoadFail();       //视频加载失败
        void onAdVideoLoadComplete();   //视频加载完成回调
    }
    public interface ADFrameImageLoadListener{
        void onStartFrameLoad(String url, ImageLoaderListener listener);
    }
    public interface ImageLoaderListener{
        /**
         * 加载图片的回调，如果加载失败返回null
         * @param loadedImage
         */
        void onLoadComplete(Bitmap loadedImage);
    }
    private class ScreenEventReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    /**
     * 一些工具性的方法
     */
    private synchronized void checkMediaPlayer(){
        if (mMediaPlayer==null){
            mMediaPlayer=createMediaPlayer();//每次都重新创建一个MediaPlayer
        }
    }
    private void setCurrentPlayState(int state){
        playerState=state;
    }
    private MediaPlayer  createMediaPlayer(){
        mMediaPlayer=new MediaPlayer();
        mMediaPlayer.reset();
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnInfoListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        if (videoSurface!=null && videoSurface.isValid()) {
            mMediaPlayer.setSurface(videoSurface);

        }else {
            stop();
        }
        return mMediaPlayer;

    }
    private void showPauseView(boolean show){
        mFullBtn.setVisibility(show?View.VISIBLE:View.GONE);
        mMiniPlayBtn.setVisibility(show?View.VISIBLE:View.GONE);
        mLoadBar.clearAnimation();
        mLoadBar.setVisibility(View.GONE);
        //mFrameView应该是缩略图吧。。。。。。。。。。。。。。。。。。。
        if (!show) {
            mFrameView.setVisibility(View.VISIBLE);
            loadFrameImage();
        }else {
            mFrameView.setVisibility(View.GONE);
        }
    }

    /**
     * 显示加载场景
     */
    private void showLoadingView(){
        mFullBtn.setVisibility(View.GONE);
        mLoadBar.setVisibility(View.VISIBLE);
        AnimationDrawable anim= (AnimationDrawable) mLoadBar.getBackground();
        anim.start();
        mMiniPlayBtn.setVisibility(View.GONE);
        mFrameView.setVisibility(View.GONE);
//        loadFrameImage();
    }

    /**
     * 显示播放按钮GONE掉所有
     */
    private void showPlayView(){
        mLoadBar.clearAnimation();
        mLoadBar.setVisibility(View.GONE);
        mMiniPlayBtn.setVisibility(View.GONE);
        mFrameView.setVisibility(View.GONE);
    }

    private void loadFrameImage() {
        //直接在网络中加载缩略图
        if (mFrameImageListener!=null) {
            mFrameImageListener.onStartFrameLoad(mFrameURI, new ImageLoaderListener() {
                @Override
                public void onLoadComplete(Bitmap loadedImage) {
                    if (loadedImage!=null) {
                        mFrameView.setScaleType(ImageView.ScaleType.FIT_XY);
                        mFrameView.setImageBitmap(loadedImage);
                    }else {
                        mFrameView.setScaleType(ImageView.ScaleType.CENTER);
                        mFrameView.setImageResource(R.drawable.xadsdk_img_error);
                    }
                }
            });
        }
    }
}
