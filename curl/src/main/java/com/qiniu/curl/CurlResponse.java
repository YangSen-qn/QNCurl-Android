package com.qiniu.curl;

import com.qiniu.library.CurlAPI.ICurlResponse;

import java.util.Map;

public class CurlResponse implements ICurlResponse {

    public final String url;
    public final int statusCode;
    public final Map<String, String> allHeaderFields;
    public final String mimeType;
    public final long expectedContentLength;
    public final String httpVersion;

    public CurlResponse(String url,
                        int statusCode,
                        Map<String, String> allHeaderFields,
                        String mimeType,
                        long expectedContentLength,
                        String httpVersion) {
        this.url = url;
        this.statusCode = statusCode;
        this.allHeaderFields = allHeaderFields;
        this.mimeType = mimeType;
        this.expectedContentLength = expectedContentLength;
        this.httpVersion = httpVersion;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public Map<String, String> getAllHeaderFields() {
        return allHeaderFields;
    }

    @Override
    public String getMimeType() {
        return null;
    }

    @Override
    public long getExpectedContentLength() {
        return expectedContentLength;
    }

    @Override
    public String getHttpVersion() {
        return httpVersion;
    }
}
