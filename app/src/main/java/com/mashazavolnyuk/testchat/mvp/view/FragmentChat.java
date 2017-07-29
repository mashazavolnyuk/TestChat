package com.mashazavolnyuk.testchat.mvp.view;

import android.app.ProgressDialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
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
import com.mashazavolnyuk.testchat.R;
import com.mashazavolnyuk.testchat.adapters.ChatAdapter;
import com.mashazavolnyuk.testchat.adapters.interfaces.IObserverClick;
import com.mashazavolnyuk.testchat.di.component.DaggerMainComponent;
import com.mashazavolnyuk.testchat.di.module.ActivityModule;
import com.mashazavolnyuk.testchat.di.module.PresenterChatModule;
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
    public static final float ALPHA_FULL = 1.0f;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerMainComponent.builder().
                appComponent(App.getComponent()).
                activityModule(new ActivityModule(getActivity()))
                .presenterChatModule(new PresenterChatModule(this)).
                build().inject(this);
        start();
        return view;
    }

    private void start() {
        chatAdapter = new ChatAdapter(getActivity(), this);
        viewChats.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewChats.setAdapter(chatAdapter);
      //  presenterChat = new PresenterChat(this);
        setUpItemTouchHelper();
        setUpAnimationDecoratorHelper();
        setListeners();
        loadData();

    }

    private void loadData() {
        showDialog("Please,wait", ProgressDialog.STYLE_SPINNER,false);
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

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.BLUE);
                xMark = ContextCompat.getDrawable(getActivity(), R.drawable.ic_clear_24dp);
                xMark.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) getActivity().getResources().getDimension(R.dimen.ic_clear_margin);
                initiated = true;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPosition = viewHolder.getAdapterPosition();
                ChatAdapter adapter = (ChatAdapter) viewChats.getAdapter();
                adapter.remove(swipedPosition);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;
                Paint p = new Paint();
                p.setColor(getActivity().getResources().getColor(R.color.colorPrimary));
                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if (!initiated) {
                    init();
                }

                // draw red background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);
                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                        (float) itemView.getBottom(), p);
                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setTextSize(50);
                paint.setTextAlign(Paint.Align.CENTER);
                String inbox = itemView.getContext().getResources().getString(R.string.swipe_text);
                c.drawText(inbox, itemView.getRight() - 1200, itemView.getHeight() / 2, paint);
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                xMark.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(viewChats);
    }

    private void setUpAnimationDecoratorHelper() {
        viewChats.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(getActivity().getResources().getColor(R.color.colorPrimary));
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!initiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
            }

        });
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
