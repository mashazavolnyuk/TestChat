package com.mashazavolnyuk.testchat.di.module;

import com.mashazavolnyuk.testchat.mvp.model.ModelChat;
import com.mashazavolnyuk.testchat.mvp.model.ModelMess;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mashka on 29.07.17.
 */
@Module
public class ModuleModelMess {

    public ModuleModelMess() {

    }

    @Provides
    ModelMess getModelChatModel(){
        return new ModelMess();
    }
}
