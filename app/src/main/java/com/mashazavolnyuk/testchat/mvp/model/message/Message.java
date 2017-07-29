package com.mashazavolnyuk.testchat.mvp.model.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mashazavolnyuk.testchat.adapters.interfaces.IDataMessage;

/**
 * Created by mashka on 27.07.17.
 */

public class Message implements IDataMessage {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("sender")
    @Expose
    private Sender sender;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("is_read")
    @Expose
    private Boolean isRead;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}
