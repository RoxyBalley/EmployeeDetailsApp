package com.example.tvsapp.retrofit;


import com.example.tvsapp.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final String BASE_URL = "http://tvsfit.mytvs.in/reporting/vrm/api/test_new/int/";
    private Retrofit retrofit = null;
    final OkHttpClient client = getHttpClient().build();

    public RetrofitManager() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
    }

    public FetchAPI getUserDetails() {
        return retrofit.create(FetchAPI.class);
    }

    //for debugging the request response
    private OkHttpClient.Builder getHttpClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(120, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Customize the request
                Request request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build();
                Response response = chain.proceed(request);
                return response;
            }
        });

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor mLogging = new HttpLoggingInterceptor();
            mLogging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(mLogging);
        }
        return httpClient;
    }


}
