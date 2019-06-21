package com.ItaliasCinemas.ajit.Italiascinema.Activity.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.interfaces.CommonInterface;
import com.ItaliasCinemas.ajit.Italiascinema.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment {
    @BindView(R.id.ll_continue)
    LinearLayout llContinue;
    @BindView(R.id.RelativeLayoutSheet)
    LinearLayout RelativeLayoutSheet;
    Unbinder unbinder;
    CommonInterface commonInterface;
static  int resultCode=1;
    @SuppressLint("ValidFragment")



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        unbinder = ButterKnife.bind(this, v);

     if(getActivity() instanceof CommonInterface)   {
       commonInterface =  (CommonInterface)getActivity();
     }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ll_continue)
    public void onViewClicked() {


        commonInterface.onClickContinueInLogin();




    }
}








