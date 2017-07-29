package com.mashazavolnyuk.testchat.mvp.model.chanels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mashka on 27.07.17.
 */

public class ChanelsSet {

    @SerializedName("channels")
    @Expose
    private List<Channel> channels = null;

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

}
