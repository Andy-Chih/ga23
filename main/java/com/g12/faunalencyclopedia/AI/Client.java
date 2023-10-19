package com.g12.faunalencyclopedia.AI;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * @author UID:u7630167 Name: Yihang Zhu
 */
public class Client {
    private static final String BASE_URL = "https://api.openai.com/";
    private static Api instance;

    public static com.g12.faunalencyclopedia.AI.Api getInstance() {
        if (instance == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            instance = retrofit.create(Api.class);
        }
        return instance;
    }
}


