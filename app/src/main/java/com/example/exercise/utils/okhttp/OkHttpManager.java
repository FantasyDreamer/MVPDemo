package com.example.exercise.utils.okhttp;

import com.example.exercise.App;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.android.BuildConfig;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2018/8/1.
 */

public class OkHttpManager {
    //设置缓存目录
    private static File cacheDirectory = new File(App.getInstance().getApplicationContext().getCacheDir().getAbsolutePath(), "MyCache");
    private static Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);
    public static OkHttpClient getHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder()//
                .cache(cache)//设置缓存
                .writeTimeout(100, TimeUnit.SECONDS)//设置写超时 100s
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时 10s
                .readTimeout(10, TimeUnit.SECONDS);//设置读取超时 10s

        try {// Https处理
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()},
                    new java.security.SecureRandom());
            //builder.sslSocketFactory(sc.getSocketFactory());
            builder.sslSocketFactory(sc.getSocketFactory(), new TrustAnyTrustManager());
            builder.hostnameVerifier(new TrustAnyHostnameVerifier());
        } catch (KeyManagementException ignored) {
        } catch (NoSuchAlgorithmException ignored) {
        }

        // 添加拦截器
        List<Interceptor> interceptors = builder.interceptors();
        if (BuildConfig.DEBUG) {//添加日志拦截器
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            interceptors.add(interceptor);
            //builder.addInterceptor(interceptor);
        }

        interceptors.add(0, new Interceptor() {//请求拦截
            @Override
            public Response intercept(Chain chain) throws IOException {
                okhttp3.Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("token", "token")
                        .addHeader("header1", "header1")
                        .addHeader("content-type", "application/json");
                return chain.proceed(builder.build());
            }
        });

        interceptors.add(new Interceptor() {//响应拦截
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                int code = response.code();
                if (code >= 200 && code <= 300) {// 2xx表示http ok
                    ResponseBody peekBody = response.peekBody(Integer.MAX_VALUE);
                    Gson gson = new Gson();
                    ApiResponse o = gson.fromJson(peekBody.string(), TypeToken.get(ApiResponse.class).getType());
                    //由于Reader只能读一次所以不能用下面的方法
                    //JsonReader jsonReader = getGson().newJsonReader(response.body().charStream());
                    //TypeAdapter<?> adapter = getGson().getAdapter(TypeToken.get(ApiResponse.class));
                    //ApiResponse o = (ApiResponse) adapter.read(jsonReader);
                    //Timber.d(response.toString());
                    // todo 可以在这里检测token是否过期进行一些处理
          /*Intent intent = new Intent(app, BaseActivity.class);
          intent.putExtra(BaseActivity.FRAGMENT_CLASS_NAME, BaseFragment.class.getName());
          intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
          Activity activity = app.getTopActivity();
          activity.startActivity(intent);
          activity.finish();*/
                }
                return response;
            }
        });

        return builder.build();
    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}