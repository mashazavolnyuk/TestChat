package com.mashazavolnyuk.testchat.di.component;

import android.app.Activity;

import com.mashazavolnyuk.testchat.di.module.ActivityModule;
import com.mashazavolnyuk.testchat.di.module.PresenterChatModule;
import com.mashazavolnyuk.testchat.di.module.PresenterMessModule;
import com.mashazavolnyuk.testchat.mvp.presenter.PresenterChat;
import com.mashazavolnyuk.testchat.mvp.presenter.PresenterMessages;
import com.mashazavolnyuk.testchat.mvp.view.FragmentChat;
import com.mashazavolnyuk.testchat.mvp.view.FragmentMessage;

import dagger.Component;

/**
 * Created by mashka on 29.07.17.
 */
@Component(modules = {ActivityModule.class, PresenterChatModule.class},dependencies = {AppComponent.class})
public interface MainComponent {
    void inject(Activity activity);
    void inject(FragmentChat fragment);
    void inject(PresenterChat presenterChat);
}
