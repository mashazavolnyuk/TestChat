package com.mashazavolnyuk.testchat.mvp.presenter;

import com.mashazavolnyuk.testchat.mvp.model.interfaces.ICallBackRes;
import com.mashazavolnyuk.testchat.mvp.model.ModelChat;
import com.mashazavolnyuk.testchat.mvp.model.chanels.Channel;
import com.mashazavolnyuk.testchat.mvp.presenter.intefaces.IPresenterChat;
import com.mashazavolnyuk.testchat.mvp.view.interfaces.IViewChat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by mashka on 27.07.17.
 */

public class PresenterChat implements IPresenterChat {

    IViewChat iViewChat;
    ModelChat modelChat;
    List<Channel> channels;

    @Inject
    public PresenterChat(IViewChat iViewChat,ModelChat modelChat) {
        this.iViewChat = iViewChat;
        this.modelChat =modelChat;
      //  modelChat = new ModelChat();

        channels = new ArrayList<>();
    }

    @Override
    public void cleanData() {

    }

    @Override
    public void setMess(String mess) {
        iViewChat.showToast(mess);
    }

    @Override
    public void getData(ICallBackRes res) {
        modelChat.load(new ICallBackRes() {
            @Override
            public void setData(List<? extends Object> data) {
                res.setData(data);
            }

            @Override
            public void setErrorMess(String errorMess) {
                iViewChat.showToast(errorMess);
            }
        });
    }
}
