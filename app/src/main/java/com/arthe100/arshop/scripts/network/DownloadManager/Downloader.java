package com.arthe100.arshop.scripts.network.DownloadManager;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
import com.arthe100.arshop.scripts.network.services.FileDownloadClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Downloader //extends AppCompatActivity
{

    private static final int PERMISSION_STORAGE_CODE = 1000;
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadFile();
                } else {
                    Toast.makeText(this, "Permission denied!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }*/

    private void downloadFile() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("//.....");

        Retrofit retrofit = builder.build();

        FileDownloadClient fileDownloadClient = retrofit.create(FileDownloadClient.class);

        Call<ResponseBody> call = fileDownloadClient.downloadFile();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}