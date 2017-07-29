package com.mashazavolnyuk.testchat.mvp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mashazavolnyuk.testchat.App;
import com.mashazavolnyuk.testchat.Constants;
import com.mashazavolnyuk.testchat.R;
import com.mashazavolnyuk.testchat.adapters.MessageAdapter;
import com.mashazavolnyuk.testchat.adapters.interfaces.IDataMessage;
import com.mashazavolnyuk.testchat.di.component.DaggerMainComponent;
import com.mashazavolnyuk.testchat.di.component.DaggerPresenterComponent;
import com.mashazavolnyuk.testchat.di.module.ActivityModule;
import com.mashazavolnyuk.testchat.di.module.PresenterChatModule;
import com.mashazavolnyuk.testchat.di.module.PresenterMessModule;
import com.mashazavolnyuk.testchat.mvp.model.MessageHeader.MessageHeader;
import com.mashazavolnyuk.testchat.mvp.model.interfaces.ICallBackRes;
import com.mashazavolnyuk.testchat.mvp.model.message.Message;
import com.mashazavolnyuk.testchat.mvp.presenter.PresenterMessages;
import com.mashazavolnyuk.testchat.mvp.view.interfaces.IViewMessages;
import com.mashazavolnyuk.testchat.util.DataFormatter;
import com.mashazavolnyuk.testchat.util.TimeChecker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mashazavolnyuk.testchat.R.id.imgCamera;
import static com.mashazavolnyuk.testchat.R.id.rcvMess;

/**
 * Created by mashka on 27.07.17.
 */

public class FragmentMessage extends BaseFragment implements IViewMessages {
    
    @BindView(rcvMess)
    RecyclerView viewMess;
    @BindView(imgCamera)
    ImageView imgGallery;

    MessageAdapter messageAdapter;
    @Inject
    PresenterMessages presenterMessages;
    String title;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        DaggerPresenterComponent.builder().
                appComponent(App.getComponent()).
                activityModule(new ActivityModule(getActivity()))
                .presenterMessModule(new PresenterMessModule(this)).
                build().inject(this);
        Bundle bundle =getArguments();
        title = bundle.getString(Constants.BUNDLE_FOR_MESS,"");
        unbinder = ButterKnife.bind(this, view);
        start();
        return view;
    }

    private void start() {
        messageAdapter = new MessageAdapter(getActivity(),null);
        viewMess.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewMess.setAdapter(messageAdapter);
        loadData();

    }

    private void loadData() {
        showDialog("Please,wait", ProgressDialog.STYLE_SPINNER,false);
        presenterMessages.getData(new ICallBackRes() {
            @Override
            public void setData(List<? extends Object> data) {
                if (data != null)
                    messageAdapter.setNewData((List<IDataMessage>) data);
                    hideDialog();
            }

            @Override
            public void setErrorMess(String errorMess) {
                showToast(errorMess);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_mess, menu);
        return;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(title);
    }
}
