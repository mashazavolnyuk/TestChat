package com.mashazavolnyuk.testchat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mashazavolnyuk.testchat.R;
import com.mashazavolnyuk.testchat.adapters.interfaces.IDataMessage;
import com.mashazavolnyuk.testchat.mvp.model.MessageHeader.MessageHeader;
import com.mashazavolnyuk.testchat.mvp.model.message.Message;
import com.mashazavolnyuk.testchat.util.DataFormatter;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.mashazavolnyuk.testchat.R.id.imgLogo;
import static com.mashazavolnyuk.testchat.R.id.tvDate;
import static com.mashazavolnyuk.testchat.R.id.tvHeader;
import static com.mashazavolnyuk.testchat.R.id.tvMess;

/**
 * Created by mashka on 27.07.17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<IDataMessage> messages;
    private Context context;

    private static final int ITEM_TYPE_VISITOR = 0;
    private static final int ITEM_TYPE_OWNER = 1;
    private static final int ITEM_TYPE_TIME_HEADER = 2;

    private int myTestId = 1947; //test like I am owner

    public MessageAdapter(Context context, List<IDataMessage> iDataMessages) {
        this.context = context;
        this.messages = iDataMessages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case ITEM_TYPE_VISITOR:
                View normalView = LayoutInflater.from(context).inflate(R.layout.item_message_left, null);
                return new Visitor(normalView);

            case ITEM_TYPE_OWNER:
                View headerRow = LayoutInflater.from(context).inflate(R.layout.item_message_right, null);
                return new OperatorHolder(headerRow);

            case ITEM_TYPE_TIME_HEADER:
                View headerTime = LayoutInflater.from(context).inflate(R.layout.item_message_header, null);
                return new HeaderTime(headerTime);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Message message;

        switch (holder.getItemViewType()) {
            case ITEM_TYPE_TIME_HEADER:
                MessageHeader messageHeader = (MessageHeader) messages.get(position);
                HeaderTime headerTime = (HeaderTime) holder;
                headerTime.time.setText((messageHeader.getMess()));
                break;
            case ITEM_TYPE_VISITOR:
                message = (Message) messages.get(position);
                Visitor visitor = (Visitor) holder;
                visitor.mess.setText((message.getText()));
                break;
            case ITEM_TYPE_OWNER:
                message = (Message) messages.get(position);
                OperatorHolder operatorHolder = (OperatorHolder) holder;
                operatorHolder.mess.setText(message.getText());
                operatorHolder.date.setText(DataFormatter.formatString(message.getCreateDate()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (messages == null)
            return 0;
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        //I don't know my id like owner (its just like test task)
        if (messages.get(position) instanceof MessageHeader)
            return ITEM_TYPE_TIME_HEADER;

        if (messages.get(position) instanceof Message) {
            Message message = (Message) messages.get(position);
            if (message.getSender().getId() == myTestId)
                return ITEM_TYPE_OWNER;
            else
                return ITEM_TYPE_VISITOR;
        }
        return ITEM_TYPE_TIME_HEADER;
    }

    public void setNewData(List<IDataMessage> newData) {
        if (newData == null) return;
        messages = newData;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class OperatorHolder extends ViewHolder {

        TextView date;
        TextView mess;

        OperatorHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.tvDate);
            mess = (TextView) itemView.findViewById(R.id.tvMess);

        }
    }

    class Visitor extends ViewHolder {

        CircleImageView logo;
        TextView mess;

        Visitor(View itemView) {
            super(itemView);
            logo = (CircleImageView) itemView.findViewById(R.id.imgLogo);
            mess = (TextView) itemView.findViewById(R.id.tvMess);
        }
    }

    class HeaderTime extends ViewHolder {

        TextView time;

        HeaderTime(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.tvHeader);
        }
    }


}
