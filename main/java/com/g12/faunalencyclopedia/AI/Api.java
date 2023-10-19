package com.g12.faunalencyclopedia.AI;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
/**
 * @author UID:u7630167 Name: Yihang Zhu
 */
public interface Api {
    @POST("https://api.openai.com/v1/chat/completions")
    Call<ResponseBody> getResponse(@Header("Authorization") String authorization, @Body RequestBody requestBody);
}
