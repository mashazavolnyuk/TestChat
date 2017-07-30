package com.mashazavolnyuk.testchat.di.module;

import android.app.Activity;

import com.mashazavolnyuk.testchat.mvp.model.ModelChat;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mashka on 29.07.17.
 */
@Module
public class ModuleModelChat {

    public ModuleModelChat() {

    }

    @Provides
    ModelChat getModelChatModel(){
        return new ModelChat();
    }
}
