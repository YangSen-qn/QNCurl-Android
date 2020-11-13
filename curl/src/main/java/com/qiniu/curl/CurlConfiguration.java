package com.qiniu.curl;


import android.annotation.SuppressLint;

import com.qiniu.library.CurlAPI.ICurlConfiguration;

import java.util.ArrayList;

public class CurlConfiguration implements ICurlConfiguration {

    private static String defaultCAPath;

    private String[] dnsResolverArray;
    private String proxy;
    private String proxyUserPwd;
    private String caPath;

    public String[] getDnsResolverArray() {
        return dnsResolverArray;
    }

    public String getProxy() {
        return proxy;
    }

    public String getProxyUserPwd() {
        return proxyUserPwd;
    }

    public String getCAPath() {
        return caPath;
    }

    public void setCAPath(String caPath) {
        this.caPath = caPath;
    }

    @Override
    public void init(IBuilder builder) {
        this.dnsResolverArray = builder.getDnsResolverArray();
        this.proxy = builder.getProxy();
        this.proxyUserPwd = builder.getProxyUserPwd();
        this.caPath = builder.getCaPath();
    }


    public static class DnsResolver implements ICurlConfiguration.IDnsResolver {
        private String host;
        private String ip;
        private int port;

        public void init(String host, String ip, int port){
            this.host = host;
            this.ip = ip;
            this.port = port;
        }

        @SuppressLint("DefaultLocale")
        public String toString(){
            return String.format("%s:%d:%s", this.host, this.port, this.ip);
        }
    }

    public static class Builder implements ICurlConfiguration.IBuilder{

        private String[] dnsResolverArray;
        private String proxy;
        private String proxyUserPwd;
        private String caPath;

        public Builder(){
            if (defaultCAPath == null) {
                defaultCAPath = CurlUtils.getCAPath();
            }
            caPath = defaultCAPath;
        }

        public String[] getDnsResolverArray() {
            return dnsResolverArray;
        }

        public String getProxy() {
            return proxy;
        }

        public String getProxyUserPwd() {
            return proxyUserPwd;
        }

        public String getCaPath() {
            return caPath;
        }

        @Override
        public Builder setDnsResolverArray(IDnsResolver[] dnsResolverArray) {
            if (dnsResolverArray == null || dnsResolverArray.length == 0){
                return this;
            }
            ArrayList<String> dnsResolverList = new ArrayList<>();
            for (IDnsResolver resolver : dnsResolverArray){
                dnsResolverList.add(resolver.toString());
            }
            this.dnsResolverArray = dnsResolverList.toArray(new String[0]);
            return this;
        }

        public Builder setProxy(String proxy) {
            this.proxy = proxy;
            return this;
        }

        public Builder setProxyUserPwd(String proxyUserPwd) {
            this.proxyUserPwd = proxyUserPwd;
            return this;
        }

        public Builder setCaPath(String caPath) {
            this.caPath = caPath;
            return this;
        }

        public CurlConfiguration build(){
            CurlConfiguration configuration = new CurlConfiguration();
            configuration.init(this);
            return configuration;
        }
    }
}
