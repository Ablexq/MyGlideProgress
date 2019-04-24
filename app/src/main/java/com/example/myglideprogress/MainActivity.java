package com.example.myglideprogress;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myglideprogress.progressglide.GlideImageView;
import com.example.myglideprogress.progressglide.GlidePhotoView;
import com.example.myglideprogress.progressglide.progress.CircleProgressView;
import com.example.myglideprogress.progressglide.progress.OnProgressListener;
import com.github.chrisbanes.photoview.OnOutsidePhotoTapListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;

public class MainActivity extends AppCompatActivity implements OnPhotoTapListener, OnOutsidePhotoTapListener {

    public static final String cat = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/resources/cat.jpg";
    public static final String cat_thumbnail = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/resources/cat_thumbnail.jpg";

    public static final String girl = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/resources/girl.jpg";
    public static final String girl_thumbnail = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/resources/girl_thumbnail.jpg";

    public static final String url1 = "https://i1.mifile.cn/f/i/2019/mi9/index/index1.jpg";
    public static final String url2 = "https://res.vmallres.com/pimages/detailImg/2019/04/23/201904231942587808067.jpg";

    private CircleProgressView progressView1;
    private CircleProgressView progressView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlideImageView glideImageView = ((GlideImageView) this.findViewById(R.id.image1));
        GlidePhotoView glidePhotoView = ((GlidePhotoView) this.findViewById(R.id.image2));
        glidePhotoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        glidePhotoView.setOnPhotoTapListener(this);
        glidePhotoView.setOnOutsidePhotoTapListener(this);
        progressView1 = ((CircleProgressView) this.findViewById(R.id.progressView1));
        progressView2 = ((CircleProgressView) this.findViewById(R.id.progressView2));

        glideImageView.centerCrop()
                .error(R.mipmap.image_load_err)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .load(url1, R.color.placeholder, new OnProgressListener() {
                    @Override
                    public void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes) {
                        Log.d("1--->", "load percentage: " + percentage + " totalBytes: " + totalBytes + " bytesRead: " + bytesRead);
                        if (isComplete) {
                            progressView1.setVisibility(View.GONE);
                        } else {
                            progressView1.setVisibility(View.VISIBLE);
                            progressView1.setProgress(percentage);
                        }
                    }
                });

        glidePhotoView.centerCrop()
                .error(R.mipmap.image_load_err)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .load(url2, R.color.placeholder, new OnProgressListener() {
                    @Override
                    public void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes) {
                        Log.d("2--->", "load percentage: " + percentage + " totalBytes: " + totalBytes + " bytesRead: " + bytesRead);
                        if (isComplete) {
                            progressView2.setVisibility(View.GONE);
                        } else {
                            progressView2.setVisibility(View.VISIBLE);
                            progressView2.setProgress(percentage);
                        }
                    }
                });
    }

    @Override
    public void onOutsidePhotoTap(ImageView imageView) {

    }

    @Override
    public void onPhotoTap(ImageView view, float x, float y) {

    }
}
