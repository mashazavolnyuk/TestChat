package com.mashazavolnyuk.testchat.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mashazavolnyuk.testchat.R;
import com.mashazavolnyuk.testchat.adapters.interfaces.IObserverClick;
import com.mashazavolnyuk.testchat.mvp.model.chanels.Channel;
import com.mashazavolnyuk.testchat.mvp.model.chanels.LastMessage;
import com.mashazavolnyuk.testchat.util.DataFormatter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.mashazavolnyuk.testchat.R.id.cardChanel;
import static com.mashazavolnyuk.testchat.R.id.imgLogo;
import static com.mashazavolnyuk.testchat.R.id.tvCountMess;
import static com.mashazavolnyuk.testchat.R.id.tvDate;
import static com.mashazavolnyuk.testchat.R.id.tvMess;
import static com.mashazavolnyuk.testchat.R.id.tvName;

/**
 * Created by mashka on 27.07.17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    Context context;
    List<Channel> data;
    IObserverClick iObserverClick;

    public ChatAdapter(Context context) {
        this.context = context;

    }

    public ChatAdapter(Context context, IObserverClick iObserverClick) {
        this.context = context;
        this.iObserverClick = iObserverClick;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channels, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(data.get(position).getLastMessage().getSender().getPhoto()).into(holder.logo);
        LastMessage lastMessage= data.get(position).getLastMessage();
        String title = lastMessage.getSender().getFirstName()+"  "+lastMessage.getSender().getLastName();
        holder.name.setText(title);
        holder.mess.setText(data.get(position).getLastMessage().getText());
        holder.date.setText(DataFormatter.formatString(data.get(position).getLastMessage().getCreateDate()));

        int countMess = data.get(position).getUnreadMessagesCount();
        if (countMess != 0) {
            holder.count.setVisibility(View.VISIBLE);
            holder.count.setText(String.valueOf(countMess));
        }
        holder.card.setOnClickListener(v -> {
            if (iObserverClick != null) iObserverClick.click(title);
        });
    }


    public void remove(int position) {
        data.remove(position);
        setNewData(data);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        else
            return data.size();
    }

    public void setNewData(List<Channel> newData) {
        this.data = newData;
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(cardChanel)
        CardView card;

        @BindView(imgLogo)
        CircleImageView logo;

        @BindView(tvName)
        TextView name;

        @BindView(tvMess)
        TextView mess;

        @BindView(tvDate)
        TextView date;

        @BindView(tvCountMess)
        TextView count;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }
    }

}
