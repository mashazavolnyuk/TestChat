package com.mashazavolnyuk.testchat.mvp.view.interfaces;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by mashka on 27.07.17.
 */

public interface IBaseView {

    String getStringById(int id);

    void showDialog(String mess, int ProgressStyle, boolean cancel);

    void showToast(String mess, int TYPE);

    void showToast(String mess);

    Context getContext();
     void hideDialog();


}
