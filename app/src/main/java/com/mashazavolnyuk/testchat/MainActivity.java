package com.mashazavolnyuk.testchat;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mashazavolnyuk.testchat.mvp.view.FragmentChat;
import com.mashazavolnyuk.testchat.mvp.view.FragmentLiveChat;
import com.mashazavolnyuk.testchat.mvp.view.FragmentMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mashazavolnyuk.testchat.R.id.layoutInfo;
import static com.mashazavolnyuk.testchat.R.id.panelChat;
import static com.mashazavolnyuk.testchat.R.id.panelLiveChat;
import static com.mashazavolnyuk.testchat.R.id.toolbar;
import static com.mashazavolnyuk.testchat.R.id.tvChat;
import static com.mashazavolnyuk.testchat.R.id.tvCountChat;
import static com.mashazavolnyuk.testchat.R.id.tvCountLiveChat;
import static com.mashazavolnyuk.testchat.R.id.tvLiveChat;

public class MainActivity extends AppCompatActivity implements INavigation, ICountObserver {

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
    @BindView(toolbar)
    Toolbar mytoolbar;
    @BindView(layoutInfo)
    PercentRelativeLayout info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getFragmentManager();
        fragmentManager.addOnBackStackChangedListener(listener);
        setListeners();
        toChannelsScreen();
    }

    @Override
    public void toChannelsScreen() {

        if (fragment instanceof FragmentChat) return;
        showInfo();
        fragment = new FragmentChat();
        fragmentManager.beginTransaction().replace(R.id.content, fragment).addToBackStack(Constants.CHAT).commit();
        setStyleForChat();

    }

    @Override
    public void toLiveChatsScreen() {

        if (fragment instanceof FragmentLiveChat) return;
        showInfo();
        fragment = new FragmentLiveChat();
        fragmentManager.beginTransaction().replace(R.id.content, fragment).addToBackStack(Constants.LIVE_CHAT).commit();
        setStyleForLiveChat();
    }

    @Override
    public void toMessages(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_FOR_MESS, title);
        hideInfo();
        fragment = new FragmentMessage();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.content, fragment).addToBackStack(fragment.getTag()).commit();

    }

    private void setListeners() {
        layoutChat.setOnClickListener(v -> toChannelsScreen());
        layoutLiveChat.setOnClickListener(v -> toLiveChatsScreen());
    }

    private void setStyleForChat() {
        Drawable drawable = getResources().getDrawable(R.drawable.rec_white);
        layoutChat.setBackground(drawable);
        drawable = getResources().getDrawable(R.drawable.rec);
        layoutLiveChat.setBackground(drawable);

    }

    private void setStyleForLiveChat() {
        Drawable drawable = getResources().getDrawable(R.drawable.rec_white);
        layoutLiveChat.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.rec);
        layoutChat.setBackground(drawable);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 1) {
            finish();

        }
        super.onBackPressed();
    }

    FragmentManager.OnBackStackChangedListener listener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            Fragment fr = fragmentManager.findFragmentById(R.id.content);
            if (fr != null) {
                if (fr instanceof FragmentChat || fr instanceof FragmentLiveChat)
                    showInfo();
                Log.e("fragment=", fr.getClass().getSimpleName());
            }
        }
    };

    @Override
    public void setCount(int count) {
        chatTextCount.setText(String.valueOf(count));
    }

    private void hideInfo() {
        mytoolbar.setVisibility(View.GONE);

    }

    private void showInfo() {
        mytoolbar.setVisibility(View.VISIBLE);

    }
}
