package com.mashazavolnyuk.testchat.mvp.model;

import com.mashazavolnyuk.testchat.api.ServiceGeneratorCollege;
import com.mashazavolnyuk.testchat.api.requests.IData;
import com.mashazavolnyuk.testchat.mvp.model.chanels.ChanelsSet;
import com.mashazavolnyuk.testchat.mvp.model.chanels.Channel;
import com.mashazavolnyuk.testchat.mvp.model.interfaces.ICallBackRes;
import com.mashazavolnyuk.testchat.mvp.model.interfaces.IModelChat;
import com.mashazavolnyuk.testchat.mvp.presenter.intefaces.IBasePresenter;
import com.mashazavolnyuk.testchat.mvp.presenter.intefaces.IPresenterChat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mashka on 27.07.17.
 */

public class ModelChat implements IModelChat {

    private String testLogin = "iostest";
    private String testPass = "iostest2k17!";
    List<Channel> data  ;
    IPresenterChat presenter;

    public ModelChat() {

    }

    @Override
    public void load(ICallBackRes res) {
        IData chanelcReq = ServiceGeneratorCollege.createService(IData.class,testLogin,testPass);
        Call<ChanelsSet> call = chanelcReq.channels();
        call.enqueue(new Callback<ChanelsSet>() {
            @Override
            public void onResponse(Call<ChanelsSet> call, Response<ChanelsSet> response) {
                if(response!=null)
                    data = response.body().getChannels();
                res.setData(data);

            }

            @Override
            public void onFailure(Call<ChanelsSet> call, Throwable t) {

            }
        });
    }
}
