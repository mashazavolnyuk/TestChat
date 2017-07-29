package com.mashazavolnyuk.testchat.di.module;

import com.mashazavolnyuk.testchat.mvp.presenter.PresenterChat;
import com.mashazavolnyuk.testchat.mvp.presenter.PresenterMessages;
import com.mashazavolnyuk.testchat.mvp.view.interfaces.IViewChat;
import com.mashazavolnyuk.testchat.mvp.view.interfaces.IViewMessages;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mashka on 29.07.17.
 */
@Module
public class PresenterMessModule {

    private IViewMessages iViewMessages;

    @Inject
    public PresenterMessModule(IViewMessages iViewMessages){
        this.iViewMessages = iViewMessages;
    }

    @Provides
    PresenterMessages providePresenterMess() {
        return new PresenterMessages(iViewMessages);
    }

}




