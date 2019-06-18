package com.example.ajit.italiascinema.Activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ajit.italiascinema.Activity.Api.ItaliaApi;
import com.example.ajit.italiascinema.Activity.Api.RetrofitClientInstance;
import com.example.ajit.italiascinema.Activity.model.ChangePaswordResponse;
import com.example.ajit.italiascinema.Activity.savedata.SaveDataClass;
import com.example.ajit.italiascinema.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.imgview_leftarrow)
    ImageView imgviewLeftarrow;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_existpass)
    EditText edtExistpass;
    @BindView(R.id.edt_newpass)
    EditText edtNewpass;
    @BindView(R.id.edt_confirm_password)
    EditText edtConfirmPassword;
    @BindView(R.id.rl_text)
    RelativeLayout rlText;
    @BindView(R.id.btn_done1)
    Button btnDone1;
    String existpass, newpass, confirmpass;

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
        if (btn != null)
            btn.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        edtExistpass.setText(SaveDataClass.getUserPassword(getContext()));
    }

    private void doneButtonClick() {

        existpass = edtExistpass.getText().toString();
        newpass = edtNewpass.getText().toString();
        confirmpass = edtConfirmPassword.getText().toString();
        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);
        Log.d("changepass123",SaveDataClass.getUserID(getContext()));

        Call<ChangePaswordResponse> call = italiaApi.changePassWord(SaveDataClass.getUserID(getContext()),existpass,newpass,confirmpass);
        call.enqueue(new Callback<ChangePaswordResponse>() {
            @Override
            public void onResponse(Call<ChangePaswordResponse> call, Response<ChangePaswordResponse> response) {

                if (response.body().getStatus()==1) {
                    ChangePaswordResponse changePaswordResponse = response.body();
                    Toast.makeText(getActivity(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    SaveDataClass.setUserPassword(getContext(),response.body().getPassword());
                    Log.d("changepass123err",changePaswordResponse.getMessage()+"");
                }
                else if(response.body().getStatus()==0){
                    Toast.makeText(getActivity(),response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ChangePaswordResponse> call, Throwable t) {
                Log.d("changepass123err",t+"");
            }


        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.imgview_leftarrow, R.id.btn_done1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgview_leftarrow:
                getActivity().onBackPressed();
                break;
            case R.id.btn_done1:
                doneButtonClick();
                break;
        }
    }
}
