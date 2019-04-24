
# Androidx和Android support库共存问题

PhotoView推荐版本2.1.4

```
implementation 'com.github.chrisbanes:PhotoView:2.1.4'
```
原因：

[Androidx和Android support库共存问题解决](https://www.jianshu.com/p/f7a7a8765294)


# responsebody.contentLength()获取到的值为-1

最近在使用OKhttp下载文件的时候出现了一个奇怪的现象，responsebody.contentLength()获取到的值为-1
经常抓包分析，发现服务器会随机的对下发的资源做GZip操作，而此时就没有相应的content-length，解决方法很简单，在Header中加入：Request.Builder().addHeader("Accept-Encoding", "identity")
这样强迫服务器不走压缩，问题就得到了解决。

[responsebody.contentLength()获取到的值为-1](https://blog.csdn.net/z_sawyer/article/details/78668790)
















