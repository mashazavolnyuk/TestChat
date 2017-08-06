package com.mashazavolnyuk.testchat.mvp.presenter.intefaces;

import com.mashazavolnyuk.testchat.mvp.model.interfaces.ICallBackRes;

/**
 * Created by mashka on 27.07.17.
 */

public interface IBasePresenter {
    void setMess(String mess);
    void getData(ICallBackRes res);
}
