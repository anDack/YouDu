package com.youdu.UIL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.andack.mysdk.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/14
 * 邮箱：    1160083806@qq.com
 * 描述：    好处代码量下降，换成其他的框架会很快
 */

public class ImageLoaderManager {
    private static final int THREAD_CONUT=4;//表明我们的ImageLoader能够启用的最多线程数量
    private static final int PROPRITY=2;//表明我们图片加载的优先级
    private static final int DISK_CACHE_SIZE=50*1024;//表明UIL可以最多缓存多少图片
    private static final int CONNECTION_TIME_OUT=5*1000;//链接超时
    private static final int READ_TIME_OUT=30*1000;//读取超时时间
    private static ImageLoader mImageLoader=null;
    private static ImageLoaderManager mIntances=null;
    public static ImageLoaderManager getIntances(Context context)
    {
        if (mIntances==null) {
            //这里是保障多个线程调度
            synchronized (ImageLoaderManager.class) {
                if (mIntances==null) {
                     mIntances=new ImageLoaderManager(context);
                }
            }
        }
       return mIntances;
    }

    /**
     *
     * @param context 因为ImageLoader需要上下文对象
     */
    private ImageLoaderManager(Context context){
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(THREAD_CONUT)//配置图片下载线程的最大数量
                .threadPriority(Thread.NORM_PRIORITY-PROPRITY)
                .denyCacheImageMultipleSizesInMemory()//防止缓存多套尺寸图片
                .memoryCache(new WeakMemoryCache())//使用弱引用，在内存不足的时候会自动回收内存
                .diskCacheSize(DISK_CACHE_SIZE)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//为了安全，使用了MD5来命名我们的文件名字
                .tasksProcessingOrder(QueueProcessingType.LIFO)//图片下载顺序
                .defaultDisplayImageOptions(getDefultOptions())//默认的图片设置
                .imageDownloader(new BaseImageDownloader(context,CONNECTION_TIME_OUT
                        ,READ_TIME_OUT))
                .writeDebugLogs()//debug模式输出日志
                .build();
        ImageLoader.getInstance().init(configuration);//初始化配置参数
        mImageLoader=ImageLoader.getInstance();
    }

    /**
     * 实现默认的图片显示设置
     * 别人大牛的经验，套用即可，逼逼不得
     * @return
     */
    private DisplayImageOptions getDefultOptions() {
            DisplayImageOptions options=new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.xadsdk_img_error)
                    .showImageOnFail(R.drawable.xadsdk_img_error)//下载图片异常，或者下载图片错误显示默认的错误图片
                    .cacheInMemory(true)//设置图片缓存在内存中
                    .cacheOnDisk(true)//设置图片可以缓存在硬盘
                    .bitmapConfig(Bitmap.Config.RGB_565)//使用图片解码
                    .decodingOptions(new BitmapFactory.Options())//图片解码配置
                    .build();
            return options;
    }

    /**
     * 显示图片的函数
     * @param imageView
     * @param url
     * @param options
     * @param listener
     */
    public void displayImage(ImageView imageView, String url,
                             DisplayImageOptions options,
                             ImageLoadingListener listener)
    {
        if (mImageLoader!=null) {
            mImageLoader.displayImage(url,imageView,options,listener);
        }

    }
    public void displayImage(ImageView imageView,String url,ImageLoadingListener listener)
    {
        displayImage(imageView,url,null,listener);
    }
    public void displayImage(ImageView imageView,String url)
    {
        displayImage(imageView,url,null);
    }
}
