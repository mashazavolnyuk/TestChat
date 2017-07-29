package com.mashazavolnyuk.testchat.mvp.presenter;

import com.mashazavolnyuk.testchat.R;
import com.mashazavolnyuk.testchat.adapters.interfaces.IDataMessage;
import com.mashazavolnyuk.testchat.mvp.model.MessageHeader.MessageHeader;
import com.mashazavolnyuk.testchat.mvp.model.ModelMess;
import com.mashazavolnyuk.testchat.mvp.model.interfaces.ICallBackRes;
import com.mashazavolnyuk.testchat.mvp.model.message.Message;
import com.mashazavolnyuk.testchat.mvp.presenter.intefaces.IPresenterMessage;
import com.mashazavolnyuk.testchat.mvp.view.interfaces.IViewMessages;
import com.mashazavolnyuk.testchat.util.DataFormatter;
import com.mashazavolnyuk.testchat.util.TimeChecker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mashka on 28.07.17.
 */

public class PresenterMessages implements IPresenterMessage {


    IViewMessages iViewMessages;
    ModelMess modelMess;
    String header;
    Date prevDate;


    public PresenterMessages(IViewMessages iViewChat) {
        this.iViewMessages = iViewChat;
        modelMess = new ModelMess(this);

    }


    @Override
    public void cleanData() {

    }

    @Override
    public void setMess(String mess) {
        iViewMessages.showToast(mess);
    }

    @Override
    public void getData(ICallBackRes res) {
        modelMess.load(new ICallBackRes() {
            @Override
            public void setData(List<? extends Object> data) {
                res.setData( convert((List<? extends IDataMessage>) data));


            }

            @Override
            public void setErrorMess(String errorMess) {
                iViewMessages.showToast(errorMess);
            }
        });
    }

    private List<IDataMessage> convert(List<? extends IDataMessage> messages) {

        List<IDataMessage> converList = new ArrayList<>();
        Message message;


        for (int i = 0; i < messages.size(); i++) {
            message = (Message) messages.get(i);
            if (i == 0){
                converList.add(getFirstHeader(DataFormatter.formatDate(message.getCreateDate())));
            converList.add(messages.get(i));}
            else {
                MessageHeader messageHeader = getHeader(DataFormatter.formatDate(message.getCreateDate()));
                if (messageHeader != null)
                    converList.add(messageHeader);
                    converList.add(messages.get(i));
            }
        }
        return converList;
    }


    private MessageHeader getFirstHeader(Date date) {
        MessageHeader messageHeader;
        if (TimeChecker.isYesterday(date))
            header = iViewMessages.getContext().getResources().getString(R.string.time_yestarday);
        if (TimeChecker.isToday(date))
            header = iViewMessages.getContext().getResources().getString(R.string.time_today);
        header = date.toString();

        messageHeader = new MessageHeader(header);
        prevDate = date;
        return messageHeader;
    }

    private MessageHeader getHeader(Date date) {
        MessageHeader messageHeader;
        if (TimeChecker.isAtThisDay(prevDate, date)) return null;
        else if (TimeChecker.isYesterday(date))
            messageHeader = new MessageHeader(iViewMessages.getContext().getResources().getString(R.string.time_yestarday));
        if (TimeChecker.isToday(date))
            messageHeader = new MessageHeader(iViewMessages.getContext().getResources().getString(R.string.time_today));
        else
            messageHeader = new MessageHeader(date.toString());

        prevDate = date;
        return messageHeader;

    }

}