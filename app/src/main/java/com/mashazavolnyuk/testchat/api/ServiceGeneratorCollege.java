package com.mashazavolnyuk.testchat.api;

import android.text.TextUtils;
import android.util.Base64;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mashka on 27.07.17.
 */

public class ServiceGeneratorCollege {

    public static String PREF_COOKIES = "pref_cookies";
    public static final String BASE_URL = "https://iostest.db2dev.com/api/chat/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static HttpLoggingInterceptor logging;
    private static Retrofit.Builder builder;


    public static <S> S createService(Class<S> serviceClass, String username, String password) {

            String credentials = username + ":" + password;
          String   basic =
                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);



        builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        httpClient = new OkHttpClient.Builder();

        httpClient.
                addNetworkInterceptor(
                        (chain) -> {
                            final Request original = chain.request();
                            // adds "Content-Type" header to all server requests
                            final Request request = original.newBuilder().
                                    header("Content-Type", "application/json")
                                    .header("Authorization", basic).
//                                    header("Accept-Language", Locale.getDefault().getLanguage()).
//                                    header("User-Agent","Android"+ Build.VERSION.RELEASE).
                            method(original.method(), original.body()).
                                    build();
                            return chain.proceed(request);
                        }
                ).
                addInterceptor(logging);
        httpClient.sslSocketFactory(getSSLSocketFactory());
        httpClient.hostnameVerifier((hostname, session) -> true);

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    private static SSLSocketFactory getSSLSocketFactory() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            return sslSocketFactory;
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            return null;
        }

    }
}
