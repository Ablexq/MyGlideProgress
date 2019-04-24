package com.example.myglideprogress.progressglide.progress;

import android.text.TextUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author by sunfusheng on 2017/6/14.
 */
public class ProgressManager {

    private static Map<String, OnProgressListener> listenersMap = Collections.synchronizedMap(new HashMap<String, OnProgressListener>());
    private static OkHttpClient okHttpClient;

    private ProgressManager() {
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request.Builder builder = chain.request().newBuilder();
                            builder.addHeader("Accept-Encoding", "identity");//强迫服务器不走压缩
                            return chain.proceed(builder.build());
                        }
                    })
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            Response response = chain.proceed(request);
                            return response.newBuilder()
                                    .body(new ProgressResponseBody(request.url().toString(), LISTENER, response.body()))
                                    .build();
                        }
                    })
                    .build();
        }
        return okHttpClient;
    }

    private static final ProgressResponseBody.InternalProgressListener LISTENER = new ProgressResponseBody.InternalProgressListener() {
        @Override
        public void onProgress(String url, long bytesRead, long totalBytes) {
            System.out.println("bytesRead=========================" + bytesRead);
            System.out.println("totalBytes=========================" + totalBytes);
            OnProgressListener onProgressListener = getProgressListener(url);
            if (onProgressListener != null) {
                int percentage = (int) ((bytesRead * 1f / totalBytes) * 100f);
                boolean isComplete = percentage >= 100;
                onProgressListener.onProgress(isComplete, percentage, bytesRead, totalBytes);
                if (isComplete) {
                    removeListener(url);
                }
            }
        }
    };

    public static void addListener(String url, OnProgressListener listener) {
        if (!TextUtils.isEmpty(url) && listener != null) {
            listenersMap.put(url, listener);
            listener.onProgress(false, 1, 0, 0);
        }
    }

    public static void removeListener(String url) {
        if (!TextUtils.isEmpty(url)) {
            listenersMap.remove(url);
        }
    }

    public static OnProgressListener getProgressListener(String url) {
        if (TextUtils.isEmpty(url) || listenersMap == null || listenersMap.size() == 0) {
            return null;
        }

        OnProgressListener listenerWeakReference = listenersMap.get(url);
        if (listenerWeakReference != null) {
            return listenerWeakReference;
        }
        return null;
    }
}
