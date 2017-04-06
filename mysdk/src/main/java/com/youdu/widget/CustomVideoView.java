package com.youdu.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.youdu.Content.AdParameters;
import com.youdu.Content.SDKConstant;
import com.youdu.util.Utils;

import java.io.IOException;


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
    private ViewGroup mParentContainer;

    private MediaPlayer mMediaPlayer;
    private ADVideoPlayerListener listener;                     //事件回调接口
    private ScreenEventReceiver mScreenEventReceiver;          //用来监听屏幕点击广播
    private ADFrameImageLoadListener mFrameImageListener;       //暂停图的回调接口
    private AudioManager audioManager;                         //音量控制器
    /**
     * 在播放时候往，主线程发送播放信息数据
     */
    private Handler mHandler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case TIME_MSG:
                    if (isPlaying()) {
                        listener.onBufferUpdate(getCurrentPosition());
                        sendEmptyMessageDelayed(TIME_MSG,TIME_INVAL);
                    }
                    break;
            }

        }
    };

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
        if (visibility==VISIBLE && playerState == STATE_PAUSING) {
            if (isRealPaused()|| isComplete()){
                pause();
            }else {
                decideCanPlay();
            }
        }else {
            pause();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    /**
     * MediaPlayer相关方法的实现
     *
     */
    /**
     * 播放器准备完成,异步加载视频成功以后,在整个视频播放的生命周期中充当load成功的下一步
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        showPlayView();                                         //改变相关的视图，使其适应播放状态下，应该有的样子
        mMediaPlayer=mp;
        if (mMediaPlayer!=null) {
            mMediaPlayer.setOnBufferingUpdateListener(this);    //监听当前的缓冲状态
            mCurrentCount=0;                                     //将加载次数进行归位
            if (listener!=null) {
                listener.onAdVideoLoadSuccess();                 //向上回调告诉调用者视频加载完毕
            }
            //满足自动播放条件，就会自动播放
//           decideCanPlay();
            if (Utils.canAutoPlay(getContext(), AdParameters.getCurrentSetting()) && Utils.getVisablePercent(parentContainer)>SDKConstant.VIDEO_HEIGHT_PERCENT){
                setCurrentPlayState(STATE_PAUSING);
                resume();
            }else {
                setCurrentPlayState(STATE_PLAYING);
                pause();
            }
        }
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
     * @return If the value of return is TRUE.It mean`s the User deal with the ERROR
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        this.playerState=STATE_ERROR;
        mMediaPlayer = mp;
        if (mMediaPlayer!=null) {
            mMediaPlayer.reset();
        }
        if (mCurrentCount >= LOAD_TOTAL_COUNT){
            showPauseView(false);
            if (this.listener!=null) {
                listener.onAdVideoLoadFail();
            }
        }
        this.stop();        //清空MediaPalyer

        return true;
    }

    /**
     * 在视频中播放完成后调用这个方法
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (listener!=null) {
            listener.onAdVideoLoadComplete();           //告诉调用者视频播放完成
        }
        playBack();                                     //将视频的变成第一帧
        setIsComplete(true);                            //设置播放完成属性
        //设置当前为真正的暂停
        // 暂停情况有两种，
        // 1.滑出屏幕暂停（当用户滑回界面要重新播放）
        // 2,播放完成暂停
        setIsRealPaused(true);
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
        videoSurface=new Surface(surface);
        checkMediaPlayer();
        mMediaPlayer.setSurface(videoSurface);
        load();
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
        //如果当前状态不为空闲状态，那么不需要加载
        if (playerState!=STATE_IDLE) {
            return;
        }
        //如果是空闲状态，那么我们去显示加载动画
        showLoadingView();
        try {
            //设置一些状态
            setCurrentPlayState(STATE_IDLE);            //设置当前状态为IDLE
            checkMediaPlayer();                          //检查MediaPlayer是否存在
            mMediaPlayer.setDataSource(this.mUrl);       //设置当前数据源
            mMediaPlayer.prepareAsync();                 // 设置异步加载
        } catch (IOException e) {
//            e.printStackTrace();
            stop();                                     //并且调用stop方法,进行重新加载等操作
        }

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
        if (playerState!=STATE_PAUSING) {                //如果状态不为PAUSEING则返回
            return;
        }
        if (!isPlaying()){
            //如果不为播放状态，处于暂停状态
            entryResumeState();                           //设置当前的变量为播放需要的变量
            mMediaPlayer.setOnSeekCompleteListener(null); //不进行设置进度条监听，貌似DOME没有提供拖动进度条的功能
            mMediaPlayer.start();                          //视频开启播放
            mHandler.sendEmptyMessage(TIME_MSG);
            showPauseView(true);
        }else {
            showPauseView(false);
        }
    }

    private void entryResumeState() {
        setCurrentPlayState(STATE_PLAYING);
        setIsRealPaused(false);
        setIsComplete(false);
    }

    private void setIsRealPaused(boolean b) {
        this.mIsRealPause=b;
    }

    private void setIsComplete(boolean b) {
        this.mIsComplete=b;
    }
    /**
     * 播放完成后的状态,
     */
    public void playBack() {
        setCurrentPlayState(STATE_PAUSING);
        mHandler.removeCallbacksAndMessages(null);
        if (mMediaPlayer!=null) {
            mMediaPlayer.setOnSeekCompleteListener(null);       //重置状态
            mMediaPlayer.seekTo(0);
            mMediaPlayer.pause();

        }
    }

    /**
     * 处于停止状态
     */
    public void stop(){
        //第一步先去移除所有的MediaPlayer对象
        if (this.mMediaPlayer!=null) {
            mMediaPlayer.reset();                        //只是为了代码健壮性考虑
            mMediaPlayer.setOnSeekCompleteListener(null);//用来重置播放状态
            mMediaPlayer.stop();                         //将MediaPlayer进行暂停
            mMediaPlayer.release();                      //释放MediaPlayer资源
            mMediaPlayer=null;                           //
        }
        mHandler.removeCallbacksAndMessages(null);        //重置
        setCurrentPlayState(STATE_IDLE);                 //重新设置状态为空闲
        if (mCurrentCount<LOAD_TOTAL_COUNT){            //判断当前加载次数，少于额定的加载次数就进行重新加载
            mCurrentCount+=1;
            load();
        }else {
            showPauseView(false);
        }


    }

    /**
     * 销毁
     */
    public void destroy(){
        if (this.mMediaPlayer!=null) {
            this.mMediaPlayer.setOnSeekCompleteListener(null);
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer=null;
        }
        setCurrentPlayState(STATE_IDLE);
        mCurrentCount=0;
        setIsComplete(false);
        setIsRealPaused(false);
        mHandler.removeCallbacksAndMessages(null);
        unRegisterBroadcastReceiver();
        showPauseView(false);
    }

    private void unRegisterBroadcastReceiver() {
        if (mScreenEventReceiver!=null) {
            getContext().unregisterReceiver(mScreenEventReceiver);
        }
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

    public boolean isRealPaused() {
        return mIsRealPause;
    }

    public boolean isComplete() {
        return mIsComplete;
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
    //显示暂停界面stop(true)
    private void showPauseView(boolean show){
        mFullBtn.setVisibility(show?View.VISIBLE : View.GONE);
        mMiniPlayBtn.setVisibility(show?View.GONE : View.VISIBLE);
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
        mLoadBar.setVisibility(View.GONE);      //隐藏加载进度条
        mMiniPlayBtn.setVisibility(View.GONE);  //隐藏播放按钮
        mFrameView.setVisibility(View.GONE);    //隐藏缩略图
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
    public boolean isPlaying(){
        if (mMediaPlayer!=null && mMediaPlayer.isPlaying()) {
            return true;
        }
        return false;
    }
    public int getCurrentPosition(){
        if (mMediaPlayer!=null) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }
    private void decideCanPlay(){

        if (Utils.getVisablePercent(parentContainer)> SDKConstant.VIDEO_HEIGHT_PERCENT) {
            resume();
        }else
            pause();
    }
}
