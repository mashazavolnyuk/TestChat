package com.mashazavolnyuk.testchat.mvp.model;

import com.mashazavolnyuk.testchat.api.ServiceGeneratorCollege;
import com.mashazavolnyuk.testchat.api.requests.IData;
import com.mashazavolnyuk.testchat.mvp.model.interfaces.ICallBackRes;
import com.mashazavolnyuk.testchat.mvp.model.interfaces.IModelMess;
import com.mashazavolnyuk.testchat.mvp.model.message.MessSet;
import com.mashazavolnyuk.testchat.mvp.model.message.Message;
import com.mashazavolnyuk.testchat.mvp.presenter.intefaces.IPresenterMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mashka on 28.07.17.
 */

public class ModelMess implements IModelMess {

    private String testLogin = "iostest";
    private String testPass = "iostest2k17!";
    List<Message> data;
    IPresenterMessage presenter;


    public ModelMess(IPresenterMessage iPresenterChat) {
        this.presenter = iPresenterChat;
    }

    @Override
    public void load(ICallBackRes res) {

        IData chanelcReq = ServiceGeneratorCollege.createService(IData.class, testLogin, testPass);
        Call<MessSet> call = chanelcReq.messages();
        call.enqueue(new Callback<MessSet>() {
            @Override
            public void onResponse(Call<MessSet> call, Response<MessSet> response) {
                data = response.body().getMessages();
                res.setData(data);
            }

            @Override
            public void onFailure(Call<MessSet> call, Throwable t) {
                res.setErrorMess("Ошибка загрузки данных");
            }
        });
    }
}
