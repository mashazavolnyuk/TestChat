package com.mashazavolnyuk.testchat.di.component;

import com.mashazavolnyuk.testchat.di.module.ActivityModule;
import com.mashazavolnyuk.testchat.di.module.PresenterChatModule;
import com.mashazavolnyuk.testchat.di.module.PresenterMessModule;
import com.mashazavolnyuk.testchat.mvp.presenter.PresenterMessages;
import com.mashazavolnyuk.testchat.mvp.view.FragmentMessage;

import dagger.Component;

/**
 * Created by mashka on 29.07.17.
 */
@Component(modules = {ActivityModule.class, PresenterMessModule.class},dependencies = {AppComponent.class})
public interface PresenterComponent {
    void inject(FragmentMessage message);
    void inject(PresenterMessages presenterMessages);
}
