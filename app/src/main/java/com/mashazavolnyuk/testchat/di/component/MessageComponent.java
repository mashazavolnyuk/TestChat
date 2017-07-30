package com.mashazavolnyuk.testchat.di.component;

import com.mashazavolnyuk.testchat.di.module.ModuleModelChat;
import com.mashazavolnyuk.testchat.di.module.ModuleModelMess;
import com.mashazavolnyuk.testchat.di.module.PresenterChatModule;
import com.mashazavolnyuk.testchat.di.module.PresenterMessModule;
import com.mashazavolnyuk.testchat.mvp.model.ModelChat;
import com.mashazavolnyuk.testchat.mvp.model.ModelMess;
import com.mashazavolnyuk.testchat.mvp.presenter.PresenterChat;
import com.mashazavolnyuk.testchat.mvp.presenter.PresenterMessages;
import com.mashazavolnyuk.testchat.mvp.view.FragmentChat;
import com.mashazavolnyuk.testchat.mvp.view.FragmentMessage;

import dagger.Component;

/**
 * Created by mashka on 30.07.17.
 */
@Component(modules = {PresenterMessModule.class, ModuleModelMess.class},dependencies = {AppComponent.class})
public interface MessageComponent {
    void inject(FragmentMessage fragment);
    void inject(PresenterMessages presenterMessages);
    void inject(ModelMess modelMess);
}

