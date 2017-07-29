package com.mashazavolnyuk.testchat.mvp.view;



import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mashazavolnyuk.testchat.INavigation;
import com.mashazavolnyuk.testchat.mvp.view.interfaces.IBaseView;

import butterknife.Unbinder;

/**
 * Created by mashka on 27.07.17.
 */

public class BaseFragment extends Fragment implements IBaseView {

    protected ProgressDialog dialog;
    protected Unbinder unbinder;
    protected  static String TAG;
    protected INavigation iNavigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        if (getActivity() instanceof INavigation) {
            iNavigation = (INavigation) getActivity();

        }
        if(getActivity() instanceof AppCompatActivity)
            (  (AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null)
            unbinder.unbind();
    }

    public String getStringById(int id) {

        return getActivity().getResources().getString(id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialog(String mess, int ProgressStyle,boolean cancel) {

        if (dialog == null) {
            dialog = new ProgressDialog(getActivity());
            dialog.setProgressStyle(ProgressStyle);
            dialog.setCancelable(cancel);
        }
        if (mess != null || mess.isEmpty())
            dialog.setMessage("Please wait");
        else
            dialog.setMessage(mess);
        dialog.show();
    }

    public void hideDialog() {
        if(dialog != null) {
            if(dialog.isShowing()) { //check if dialog is showing.

                //get the Context object that was used to great the dialog
                Context context = ((ContextWrapper)dialog.getContext()).getBaseContext();

                //if the Context used here was an activity AND it hasn't been finished or destroyed
                //then dismiss it
                if(context instanceof Activity) {
                    if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed())
                        dialog.dismiss();
                } else //if the Context used wasnt an Activity, then dismiss it too
                    dialog.dismiss();
            }
            dialog = null;
        }
    }

    public void showToast(String mess,int TYPE) {
        Toast.makeText(getActivity(), mess, TYPE).show();

    }

    public void showToast(String mess) {
        Toast.makeText(getActivity(), mess, Toast.LENGTH_SHORT).show();

    }
}
