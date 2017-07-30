package com.mashazavolnyuk.testchat.di.component;

import com.mashazavolnyuk.testchat.di.module.ModuleModelChat;
import com.mashazavolnyuk.testchat.di.module.PresenterChatModule;
import com.mashazavolnyuk.testchat.mvp.model.ModelChat;
import com.mashazavolnyuk.testchat.mvp.presenter.PresenterChat;
import com.mashazavolnyuk.testchat.mvp.view.FragmentChat;

import dagger.Component;

/**
 * Created by mashka on 29.07.17.
 */
@Component(modules = {PresenterChatModule.class, ModuleModelChat.class},dependencies = {AppComponent.class})
public interface MainComponent {
    void inject(FragmentChat fragment);
    void inject(PresenterChat presenterChat);
    void inject(ModelChat modelChat);
}
