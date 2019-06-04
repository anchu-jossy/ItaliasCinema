package com.example.ajit.italiascinema.Activity.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ajit.italiascinema.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClearDialogFragment extends DialogFragment {
    @BindView(R.id.btn_no)
    Button btnNo;
    @BindView(R.id.btn_yes)
    Button btnYes;
    boolean isClickedNo = false;
    boolean isClickedYes = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.clear_dialog, container, false);
        ButterKnife.bind(this, v);
        // Do all the stuff to initialize your custom view

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.btn_no, R.id.btn_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_no:
                isClickedNo = true;
                if (isClickedYes == false) {
                    btnYes.setBackgroundResource(R.drawable.dim_button_border);
                }
                if (isClickedNo == true) {
                    btnNo.setBackgroundResource(R.drawable.button_border);
                    isClickedYes = false;
                }
                isClickedNo = false;
                break;
            case R.id.btn_yes:
                isClickedYes = true;
                if (isClickedNo == false) {
                    btnNo.setBackgroundResource(R.drawable.dim_button_border);
                }
                if (isClickedYes == true)

                {
                    btnYes.setBackgroundResource(R.drawable.button_border);
                    isClickedNo = false;
                }
                isClickedYes = false;
                break;
        }
    }


}