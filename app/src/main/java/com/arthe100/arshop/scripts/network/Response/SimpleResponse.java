package com.arthe100.arshop.scripts.network.Response;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.arthe100.arshop.scripts.network.Model.Picture;
import com.arthe100.arshop.scripts.network.Model.UserInfo;
import com.arthe100.arshop.scripts.network.Request.PictureApi;
import com.arthe100.arshop.scripts.network.Request.UserInfoApi;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SimpleResponse extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://...")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

    UserInfoApi userInfoApi = retrofit.create(UserInfoApi.class);
    PictureApi pictureApi = retrofit.create(PictureApi.class);

    private void getUserInfo() {
        Call<List<UserInfo>> call = userInfoApi.getUserInfo(null, null);

        //call.enqueue
    }

    private void getPicUrl() {
        Call<List<Picture>> call = pictureApi.getPicUrl(null);

        //call.enqueue
    }

    private void createUserInfo() {
        UserInfo userInfo = new UserInfo(null, null, null);
        Call<UserInfo> call = userInfoApi.createUserInfo(userInfo);

        //call.enqueue
    }

    private void addPic() {
        Picture picture = new Picture(null);
        Call<Picture> call = pictureApi.addPic(picture);

        //call.enqueue
    }

    /*private void putUsername() {
        Call<UserInfo> call = userInfoApi.putUsername()
    }*/
}