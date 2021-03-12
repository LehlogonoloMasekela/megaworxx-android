package com.doosy.megaworxx.request;

import android.content.Context;

import com.doosy.megaworxx.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class SocketBuilder {

    private static SocketBuilder instance;
    private Context mContext;

    public static SocketBuilder getInstance(Context context){
        if(instance == null){
            instance = new SocketBuilder(context);
        }
        return instance;
    }

    public SocketBuilder(Context mContext) {
        this.mContext = mContext;
    }

    public void build(Context context) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream cert = context.getResources().openRawResource(R.raw.megaworxx_cer);
        Certificate ca = null;
        try {
            ca = cf.generateCertificate(cert);
        } catch (CertificateException e) {
            e.printStackTrace();
        } finally { cert.close(); }

        // creating a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // creating a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // creating an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

    }

    private SSLContext getSSLContext() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream instream = mContext.getResources().openRawResource(R.raw.megaworxx_cer);
        Certificate ca = null;
        try {
            ca = cf.generateCertificate(instream);
        } catch (CertificateException e) {
            e.printStackTrace();
        } finally {
            try {
                instream.close();
            }catch (Exception e){}

        }

        KeyStore kStore = KeyStore.getInstance(KeyStore.getDefaultType());
        kStore.load(null, null);
        kStore.setCertificateEntry("ca", ca);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(kStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        return sslContext;
    }
}
