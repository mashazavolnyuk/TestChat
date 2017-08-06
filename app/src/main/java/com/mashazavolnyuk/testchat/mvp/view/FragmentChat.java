package com.mashazavolnyuk.testchat.mvp.view;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mashazavolnyuk.testchat.App;
import com.mashazavolnyuk.testchat.ICountObserver;
import com.mashazavolnyuk.testchat.R;
import com.mashazavolnyuk.testchat.adapters.ChatAdapter;
import com.mashazavolnyuk.testchat.adapters.interfaces.IObserverClick;
import com.mashazavolnyuk.testchat.di.component.DaggerMainComponent;
import com.mashazavolnyuk.testchat.di.module.ModuleModelChat;
import com.mashazavolnyuk.testchat.di.module.PresenterChatModule;
import com.mashazavolnyuk.testchat.mvp.model.ModelChat;
import com.mashazavolnyuk.testchat.mvp.model.chanels.Channel;
import com.mashazavolnyuk.testchat.mvp.model.interfaces.ICallBackRes;
import com.mashazavolnyuk.testchat.mvp.presenter.PresenterChat;
import com.mashazavolnyuk.testchat.mvp.view.interfaces.IViewChat;
import com.mashazavolnyuk.testchat.util.BottomNavigationViewHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mashazavolnyuk.testchat.R.id.bnvBar;
import static com.mashazavolnyuk.testchat.R.id.rcvChat;

/**
 * Created by mashka on 27.07.17.
 */

public class FragmentChat extends BaseFragment implements IViewChat, IObserverClick {


    @BindView(rcvChat)
    RecyclerView viewChats;
    @BindView(bnvBar)
    BottomNavigationView bottomNavigationView;
    ChatAdapter chatAdapter;
    @Inject
    PresenterChat presenterChat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerMainComponent.builder().
                appComponent(App.getComponent())
                .moduleModelChat(new ModuleModelChat())
                .presenterChatModule(new PresenterChatModule(this, new ModelChat())).
                build().inject(this);
        start();
        return view;
    }

    private void start() {
        chatAdapter = new ChatAdapter(getActivity(), this, (ICountObserver) getActivity());
        viewChats.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewChats.setAdapter(chatAdapter);
        //  presenterChat = new PresenterChat(this);
        setUpItemTouchHelper();
        setUpAnimationDecoratorHelper();
        setListeners();
        loadData();

    }

    private void loadData() {
        showDialog("Please,wait", ProgressDialog.STYLE_SPINNER, false);
        presenterChat.getData(new ICallBackRes() {
            @Override
            public void setData(List<? extends Object> data) {
                if (data != null)
                    chatAdapter.setNewData((List<Channel>) data);
                hideDialog();
            }

            @Override
            public void setErrorMess(String errorMess) {
                showToast(errorMess);
                hideDialog();
            }
        });
    }

    private void setListeners() {
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_kurse:
                    case R.id.action_comunity:
                    case R.id.action_help:
                    case R.id.action_prof:
                        showToast("Jon Snow know nothing");
                }
                return true;
            }
        });
    }


    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ChatItemTouchHelper(viewChats, 0, ItemTouchHelper.RIGHT);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(viewChats);
    }

    private void setUpAnimationDecoratorHelper() {
        viewChats.addItemDecoration(new ChatItemDecoration(getActivity()));
    }

    @Override
    public void click(String mess) {
        iNavigation.toMessages(mess);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        return;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Chat");
    }

}
