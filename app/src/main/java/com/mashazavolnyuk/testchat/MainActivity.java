package com.mashazavolnyuk.testchat;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mashazavolnyuk.testchat.mvp.view.FragmentChat;
import com.mashazavolnyuk.testchat.mvp.view.FragmentLiveChat;
import com.mashazavolnyuk.testchat.mvp.view.FragmentMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mashazavolnyuk.testchat.R.id.panelChat;
import static com.mashazavolnyuk.testchat.R.id.panelLiveChat;
import static com.mashazavolnyuk.testchat.R.id.tvChat;
import static com.mashazavolnyuk.testchat.R.id.tvCountChat;
import static com.mashazavolnyuk.testchat.R.id.tvCountLiveChat;
import static com.mashazavolnyuk.testchat.R.id.tvLiveChat;

public class MainActivity extends AppCompatActivity implements INavigation {

    FragmentManager fragmentManager;
    Fragment fragment;

    @BindView(panelLiveChat)
    RelativeLayout layoutLiveChat;
    @BindView(tvLiveChat)
    TextView liveChatText;
    @BindView(tvCountLiveChat)
    TextView liveChatTextCount;

    @BindView(panelChat)
    RelativeLayout layoutChat;
    @BindView(tvChat)
    TextView chatText;
    @BindView(tvCountChat)
    TextView chatTextCount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getFragmentManager();
        setListeners();
        toChannelsScreen();
    }

    @Override
    public void toChannelsScreen() {
        if(fragment instanceof FragmentChat) return;
        fragment = new FragmentChat();
        fragmentManager.beginTransaction().replace(R.id.content, fragment).addToBackStack(fragment.getTag()).commit();
        setStyleForChat();

    }

    @Override
    public void toLiveChatsScreen() {
        if(fragment instanceof FragmentLiveChat)return;

        fragment = new FragmentLiveChat();
        fragmentManager.beginTransaction().replace(R.id.content, fragment).addToBackStack(fragment.getTag()).commit();
        setStyleForLiveChat();
    }

    @Override
    public void toMessages(String title) {
        Bundle bundle =new Bundle();
        bundle.putString(Constants.BUNDLE_FOR_MESS,title);
        fragment = new FragmentMessage();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.content, fragment).addToBackStack(fragment.getTag()).commit();
    }

    private void setListeners(){
        layoutChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toChannelsScreen();
            }
        });
        layoutLiveChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLiveChatsScreen();
            }
        });
    }

    private void setStyleForChat(){
        Drawable drawable = getResources().getDrawable(R.drawable.rec_white);
        layoutChat.setBackground(drawable);
        drawable = getResources().getDrawable(R.drawable.rec);
        layoutLiveChat.setBackground(drawable);

    }

    private void setStyleForLiveChat(){
        Drawable drawable = getResources().getDrawable(R.drawable.rec_white);
        layoutLiveChat.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.rec);
        layoutChat.setBackground(drawable);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 1){
            finish();
        }
        super.onBackPressed();
    }
}
