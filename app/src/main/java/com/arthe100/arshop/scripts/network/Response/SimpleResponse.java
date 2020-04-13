package com.arthe100.arshop.scripts.network.Response;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.arthe100.arshop.scripts.network.Model.Picture;
import com.arthe100.arshop.scripts.network.Model.UserInfo;
import com.arthe100.arshop.scripts.network.Request.ApiClient;
import com.arthe100.arshop.scripts.network.Request.PictureApi;
import com.arthe100.arshop.scripts.network.Request.UserInfoApi;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimpleResponse extends AppCompatActivity
{
    private static final String TAG = "tag";


    UserInfoApi userInfoApi;
    PictureApi pictureApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //problem
        userInfoApi = ApiClient.getClient().create(UserInfoApi.class);
        pictureApi = ApiClient.getClient().create(PictureApi.class);

        getPicUrl();
        getUserInfo();
        createUserInfo();
        addPic();
    }

    private void getUserInfo() {
        Call<List<UserInfo>> call = userInfoApi.getUserInfo(null, null);

        call.enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
                Log.e(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void getPicUrl() {
        Call<List<Picture>> call = pictureApi.getPicUrl(null);

        call.enqueue(new Callback<List<Picture>>() {
            @Override
            public void onResponse(Call<List<Picture>> call, Response<List<Picture>> response) {
                Log.e(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<List<Picture>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void createUserInfo() {
        UserInfo userInfo = new UserInfo(null, null, null);
        Call<UserInfo> call = userInfoApi.createUserInfo(userInfo);

        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                Log.e(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void addPic() {
        Picture picture = new Picture(null);
        Call<Picture> call = pictureApi.addPic(picture);

        call.enqueue(new Callback<Picture>() {
            @Override
            public void onResponse(Call<Picture> call, Response<Picture> response) {
                Log.e(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<Picture> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}