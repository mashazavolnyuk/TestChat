package com.mashazavolnyuk.testchat.mvp.model.interfaces;

import com.mashazavolnyuk.testchat.mvp.model.chanels.Channel;

import java.util.List;

/**
 * Created by mashka on 27.07.17.
 */

public interface ICallBackRes {
    void setData(List<? extends Object> data);
    void setErrorMess(String errorMess);
}
