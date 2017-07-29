package com.mashazavolnyuk.testchat.mvp.model.MessageHeader;

import com.mashazavolnyuk.testchat.adapters.interfaces.IDataMessage;

/**
 * Created by mashka on 29.07.17.
 */

public class MessageHeader implements IDataMessage {

    private String mess;
    public MessageHeader(String mess) {
        this.mess = mess;

    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

}
