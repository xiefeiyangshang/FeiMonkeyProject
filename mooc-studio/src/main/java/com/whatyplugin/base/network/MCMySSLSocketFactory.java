package com.whatyplugin.base.network;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpVersion;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class MCMySSLSocketFactory extends SSLSocketFactory {
    SSLContext sslContext;

    public MCMySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        super(truststore);
        this.sslContext = SSLContext.getInstance("TLS");
        this.sslContext.init(null, new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }}, null);
    }

    public Socket createSocket() throws IOException {
        return this.sslContext.getSocketFactory().createSocket();
    }

    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
        return this.sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    public void fixHttpsURLConnection() {
        HttpsURLConnection.setDefaultSSLSocketFactory(this.sslContext.getSocketFactory());
    }

    public static SSLSocketFactory getFixedSocketFactory() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            MCMySSLSocketFactory mcMySSLSocketFactory = new MCMySSLSocketFactory(MCMySSLSocketFactory.getKeystore());
            ((SSLSocketFactory)mcMySSLSocketFactory).setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        }
        catch(Throwable v1) {
            v1.printStackTrace();
            sslSocketFactory = SSLSocketFactory.getSocketFactory();
        }

        return sslSocketFactory;
    }

    public static KeyStore getKeystore() {
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
        }
        catch(Throwable e) {
            e.printStackTrace();
        }

        return keyStore;
    }

    public static KeyStore getKeystoreOfCA(InputStream cert) {
        KeyStore keyStore = null;
        BufferedInputStream bufferedInputStream;
        CertificateFactory certificateFactory;
        BufferedInputStream bufferedInputStream2 = null;
        Certificate certificate = null;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            bufferedInputStream = new BufferedInputStream(cert);
            certificate = certificateFactory.generateCertificate(((InputStream)bufferedInputStream));
            if(bufferedInputStream == null) {
            	 String type = KeyStore.getDefaultType();
                 try {
                     keyStore = KeyStore.getInstance(type);
                     keyStore.load(null, null);
                     keyStore.setCertificateEntry("ca", certificate);
                 }
                 catch(Exception v4_1) {
                     v4_1.printStackTrace();
                 }
            }
        }
        catch(Exception v5) {
        	v5.printStackTrace();
        }

        return keyStore;
    }

    public static DefaultHttpClient getNewHttpClient(KeyStore keyStore) {
        DefaultHttpClient v5;
        try {
            MCMySSLSocketFactory v4 = new MCMySSLSocketFactory(keyStore);
            SchemeRegistry v3 = new SchemeRegistry();
            v3.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            v3.register(new Scheme("https", ((SocketFactory)v4), 443));
            BasicHttpParams v2 = new BasicHttpParams();
            HttpProtocolParams.setVersion(((HttpParams)v2), HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(((HttpParams)v2), "UTF-8");
            v5 = new DefaultHttpClient(new ThreadSafeClientConnManager(((HttpParams)v2), v3), ((HttpParams)v2));
        }
        catch(Exception v1) {
            v5 = new DefaultHttpClient();
        }

        return v5;
    }
}

