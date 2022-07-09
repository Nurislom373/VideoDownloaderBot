package org.khasanof.service.instagram;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.*;
import okhttp3.*;
import org.khasanof.entity.instagram.InstagramEntity;
import org.khasanof.utils.baseUtils.ValidateUtils;

import java.lang.reflect.Type;

public class InstagramService {

    @SneakyThrows
    public static InstagramEntity load(String url) {

        Gson gson = new Gson();
        Type type = new TypeToken<InstagramEntity>() {
        }.getType();

        ValidateUtils.validateURL(url);

        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("url", url)
                .build();

        Request request = new Request.Builder()
                .url("https://instagram-video-or-images-downloader.p.rapidapi.com/")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("X-RapidAPI-Key", "45c4d31191msh5d479e8a37e217ep10c2e5jsn1c502dcfecb7")
                .addHeader("X-RapidAPI-Host", "instagram-video-or-images-downloader.p.rapidapi.com")
                .build();
        Response response = client.newCall(request).execute();
        InstagramEntity instagram = gson.fromJson(response.body().string(), type);
        return instagram;
    }

}
