package com.arthe100.arshop.scripts.network.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FileDownloadClient
{
    @GET("...")
    Call<ResponseBody> downloadFile();
}
