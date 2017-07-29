package com.mashazavolnyuk.testchat.mvp.model.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mashka on 27.07.17.
 */

public class MessSet {

    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}


