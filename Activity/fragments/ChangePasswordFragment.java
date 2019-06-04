package com.example.ajit.italiascinema.Activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.ajit.italiascinema.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChangePasswordFragment extends Fragment {


    @BindView(R.id.imgview_leftarrow)
    ImageView imgviewLeftarrow;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_emailid)
    EditText edtEmailid;
    @BindView(R.id.edt_confirm_password)
    EditText edtConfirmPassword;
    @BindView(R.id.rl_text)
    RelativeLayout rlText;
    @BindView(R.id.btn_done1)
    Button btnDone;
    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        unbinder = ButterKnife.bind(this, view);

        Button btn = getActivity().findViewById(R.id.btn_done);
        if(btn!=null)
        btn.setVisibility(View.GONE);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.imgview_leftarrow)
    public void onViewClicked() {


        getActivity().onBackPressed();
    }
}
