package com.g12.faunalencyclopedia.AI;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * @author UID:u7630167 Name: Yihang Zhu
 */
// Reference:
public class ViewModel extends androidx.lifecycle.ViewModel{
    private MutableLiveData<String> currentCharacter = new MutableLiveData<>();
    public void fetchResponse(String authorization, String prompt){
        Api apiService = Client.getInstance();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                "{\"messages\":[{\"role\":\"user\",\"content\":\"" + prompt + "\"}], \"model\":\"gpt-3.5-turbo\",  \"temperature\": 0.5}");
        Call<ResponseBody> call = apiService.getResponse(authorization, requestBody);
        call.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String content = jsonObject.getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content");
                        simulateRealtimeTyping(content);
                    } catch (JSONException e) {
                        currentCharacter.setValue("JSON Parsing error: " + e.getMessage());
                    } catch (Exception e) {
                        currentCharacter.setValue("Other error: " + e.getMessage());
                    }
            }else {
                    try {
                        currentCharacter.setValue("API call failed with status code: " + response.code() + ", message: " + response.message() + ", response body: " + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                currentCharacter.setValue("API call failed: " + t.getMessage());
            }
        });
    }
    // Imitate typing
    private void simulateRealtimeTyping(String chatGptResponse) {
        final char[] chars = chatGptResponse.toCharArray();
        final Handler handler = new Handler(Looper.getMainLooper());
        final int delay = 25;
        handler.postDelayed(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                if (i < chars.length) {
                    currentCharacter.setValue(String.valueOf(chars[i]));
                    i++;
                    handler.postDelayed(this, delay);
                }
            }
        }, delay);
    }

    public LiveData<String> getCurrentCharacter() {
        return currentCharacter;
    }
}
