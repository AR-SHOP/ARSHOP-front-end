package com.arthe100.arshop.scripts.network.Request;

import com.arthe100.arshop.scripts.network.Model.Picture;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface PictureApi
{
    @GET("3DPicture")
    Call<List<Picture>> getPicUrl(
            @Query("Url") String url
    );

    @POST("3DPicture")
    Call<Picture> addPic(
            @Body Picture picture
    );

    @PUT("3DPicture")
    Call<Picture> putUrl(
            String url
    );
}