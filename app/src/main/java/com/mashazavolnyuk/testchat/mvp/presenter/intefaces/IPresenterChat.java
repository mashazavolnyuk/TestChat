package com.mashazavolnyuk.testchat.mvp.presenter.intefaces;

import com.mashazavolnyuk.testchat.mvp.model.interfaces.ICallBackRes;
import com.mashazavolnyuk.testchat.mvp.model.chanels.Channel;

import java.util.List;

/**
 * Created by mashka on 27.07.17.
 */

public interface IPresenterChat extends IBasePresenter {
     void getData(ICallBackRes res);
}
