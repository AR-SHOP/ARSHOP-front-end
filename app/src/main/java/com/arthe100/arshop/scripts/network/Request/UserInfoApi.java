package com.arthe100.arshop.scripts.network.Request;

import com.arthe100.arshop.scripts.network.Model.UserInfo;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserInfoApi {
    @GET("UserInformation")
    Call<List<UserInfo>> getUserInfo(
            @Query("Username") String username,
            @Query("E-Mail") String email
    );

    @POST("UserInformation")
    Call<UserInfo> createUserInfo(
            @Body UserInfo userInfo
    );

    @PUT("UserInformation/{Username}")
    Call<UserInfo> putUsername(
            @Path("Username") String username
    );

    @PUT("UserInformation/{Username}")
    Call<UserInfo> putPassword(
            @Path("Username") String password
    );

    @PUT("UserInformation/{E_Mail}")
    Call<UserInfo> putEmail(
            @Path("E-mail") String email
    );
}