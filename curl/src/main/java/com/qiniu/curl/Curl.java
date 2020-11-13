package com.qiniu.curl;


import com.qiniu.library.CurlAPI.ICurl;
import com.qiniu.library.CurlAPI.ICurlConfiguration;
import com.qiniu.library.CurlAPI.ICurlHandler;
import com.qiniu.library.CurlAPI.ICurlRequest;

public class Curl implements ICurl {

    private boolean isCancel = false;

    static {
        System.loadLibrary("qn-curl");
    }

    public boolean isCancel() {
        return isCancel;
    }

    public native long globalInit();

    public void request(final ICurlRequest request,
                        final ICurlConfiguration curlConfiguration,
                        final ICurlHandler curlHandler){

        CurlThreadPool.run(new Runnable() {
            @Override
            public void run() {
                CurlHandler curlHandlerReal = new CurlHandler(curlHandler);
                requestNative(request, curlConfiguration, curlHandlerReal);
            }
        });
    }

    public native void requestNative(final ICurlRequest request,
                                     final ICurlConfiguration curlConfiguration,
                                     final CurlHandler curlHandler);

    public void cancel(){
        isCancel = true;
    }

}
