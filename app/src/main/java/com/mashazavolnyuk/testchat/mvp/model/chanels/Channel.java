package com.mashazavolnyuk.testchat.mvp.model.chanels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mashazavolnyuk.testchat.mvp.model.interfaces.IDataRes;

import java.util.List;

/**
 * Created by mashka on 27.07.17.
 */

public class Channel implements IDataRes {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("users")
    @Expose
    private List<User> users = null;
    @SerializedName("last_message")
    @Expose
    private LastMessage lastMessage;
    @SerializedName("unread_messages_count")
    @Expose
    private Integer unreadMessagesCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Integer getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(Integer unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
    }
}
