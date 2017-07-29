package com.mashazavolnyuk.testchat.api.requests;

import com.mashazavolnyuk.testchat.mvp.model.chanels.ChanelsSet;
import com.mashazavolnyuk.testchat.mvp.model.message.MessSet;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mashka on 27.07.17.
 */

public interface IData {

    @GET("channels/")
    Call<ChanelsSet> channels();

    @GET("channels/1/messages/")
    Call<MessSet> messages();
}
