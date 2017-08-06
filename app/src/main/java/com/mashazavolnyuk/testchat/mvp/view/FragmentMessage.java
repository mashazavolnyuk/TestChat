package com.mashazavolnyuk.testchat.mvp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.mashazavolnyuk.testchat.di.component.DaggerMessageComponent;
import com.mashazavolnyuk.testchat.di.module.PresenterMessModule;
import com.mashazavolnyuk.testchat.mvp.model.ModelMess;
import com.mashazavolnyuk.testchat.mvp.model.interfaces.ICallBackRes;
import com.mashazavolnyuk.testchat.mvp.presenter.PresenterMessages;
import com.mashazavolnyuk.testchat.mvp.view.interfaces.IViewMessages;

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
        Bundle bundle = getArguments();
        title = bundle.getString(Constants.BUNDLE_FOR_MESS, "");
        unbinder = ButterKnife.bind(this, view);
        DaggerMessageComponent.builder().appComponent(App.getComponent()).presenterMessModule(new PresenterMessModule(this, new ModelMess())).build().inject(this);
        start();
        return view;
    }

    private void start() {
        messageAdapter = new MessageAdapter(getActivity(), null);
        viewMess.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewMess.setAdapter(messageAdapter);
        loadData();

    }

    private void loadData() {
        showDialog("Please,wait", ProgressDialog.STYLE_SPINNER, false);
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
                hideDialog();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_mess, menu);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(title);
    }
}
