package com.ItaliasCinemas.ajit.Italiascinema.Activity.fragments;

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
import android.widget.TextView;
import android.widget.Toast;


import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.ItaliaApi;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.EditProfileResponse;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.savedata.SaveDataClass;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.RetrofitClientInstance;


import com.ItaliasCinemas.ajit.Italiascinema.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditprofileFragment extends Fragment {


    @BindView(R.id.imgview_leftarrow)
    ImageView imgviewLeftarrow;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rll_profile_image)
    RelativeLayout rllProfileImage;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.tv_email_address)
    TextView tvEmailAddress;
    @BindView(R.id.edt_emailid)
    EditText edtEmailid;
    @BindView(R.id.rl_text)
    RelativeLayout rlText;
    @BindView(R.id.btn_done)
    Button btnDone;
    String userprofilename, emailid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit Profile");


        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        unbinder = ButterKnife.bind(this, view);
        edtUsername.setText(SaveDataClass.getUserName(getContext()));
        edtEmailid.setText(SaveDataClass.getEmailId(getContext()));
        return view;

    }

    private void doneButtonClick() {

        userprofilename = edtUsername.getText().toString();
        emailid = edtEmailid.getText().toString();

        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);


        Call<EditProfileResponse> call = italiaApi.editProfile(userprofilename, emailid, SaveDataClass.getUserID(getContext()));
        call.enqueue(new Callback<EditProfileResponse>() {
            @Override
            public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {
                EditProfileResponse editProfileResponse = response.body();
                if (editProfileResponse.getStatus() == 1) {
                    Toast.makeText(getContext(), editProfileResponse.getMessage(), Toast.LENGTH_SHORT).show();


                    SaveDataClass.setEmailId(getContext(),editProfileResponse.getEmailid());
                    SaveDataClass.setUserName(getContext(),editProfileResponse.getUsername());

                } else if (editProfileResponse.getStatus() == 0) {
                    Toast.makeText(getContext(), editProfileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EditProfileResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.imgview_leftarrow, R.id.btn_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgview_leftarrow:
                getActivity().onBackPressed();
                break;
            case R.id.btn_done:
                doneButtonClick();
                break;
        }
    }
}
