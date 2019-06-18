package com.example.ajit.italiascinema.Activity.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ajit.italiascinema.Activity.Api.ItaliaApi;
import com.example.ajit.italiascinema.Activity.Api.RetrofitClientInstance;
import com.example.ajit.italiascinema.Activity.activity.HomenavigationActivity;
import com.example.ajit.italiascinema.Activity.activity.LoginActivity;
import com.example.ajit.italiascinema.Activity.model.LoginResponse;
import com.example.ajit.italiascinema.Activity.savedata.SaveDataClass;
import com.example.ajit.italiascinema.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LanguagePreferenceFragment extends Fragment {

    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;
    @BindView(R.id.btn_3)
    Button btn3;
    @BindView(R.id.btn_4)
    Button btn4;
    @BindView(R.id.btn_5)
    Button btn5;
    @BindView(R.id.btn_6)
    Button btn6;
    @BindView(R.id.btn_done)
    Button btnDone;

    @BindView(R.id.imgview_leftarrow)
    ImageView imgviewLeftarrow;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.ll_2)
    LinearLayout ll2;
    @BindView(R.id.rl_language_preference)
    RelativeLayout rlLanguagePreference;
    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_language_preference, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void doneButtonClick(String lang_id ) {



        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);


        Call<LoginResponse> call = italiaApi.getSelectedLanguage(lang_id, SaveDataClass.getUserID(getContext()));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                if(loginResponse.getStatus()==1){
                    Toast.makeText(getContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

                else if(loginResponse.getStatus()==0){
                    Toast.makeText(getContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }



    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_done, R.id.imgview_leftarrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:

                    btn1.setBackgroundResource(R.drawable.laguage_colored_button_border);
                    btn1.setTextColor(Color.WHITE);
                    btn2.setBackgroundResource(R.drawable.laguage_button_border);
                    btn2.setTextColor(Color.GRAY);
                    btn3.setBackgroundResource(R.drawable.laguage_button_border);
                    btn3.setTextColor(Color.GRAY);
                    btn4.setBackgroundResource(R.drawable.laguage_button_border);
                    btn4.setTextColor(Color.GRAY);

                    btn5.setBackgroundResource(R.drawable.laguage_button_border);
                    btn5.setTextColor(Color.GRAY);

                    btn6.setBackgroundResource(R.drawable.laguage_button_border);
                    btn6.setTextColor(Color.GRAY);

                doneButtonClick("1");


                break;
            case R.id.btn_2:


                    btn2.setBackgroundResource(R.drawable.laguage_colored_button_border);
                    btn2.setTextColor(Color.WHITE);
                    btn1.setBackgroundResource(R.drawable.laguage_button_border);
                    btn1.setTextColor(Color.GRAY);
                    btn3.setBackgroundResource(R.drawable.laguage_button_border);
                    btn3.setTextColor(Color.GRAY);
                    btn4.setBackgroundResource(R.drawable.laguage_button_border);
                    btn4.setTextColor(Color.GRAY);
                    btn5.setBackgroundResource(R.drawable.laguage_button_border);
                    btn5.setTextColor(Color.GRAY);
                    btn6.setBackgroundResource(R.drawable.laguage_button_border);
                    btn6.setTextColor(Color.GRAY);
                doneButtonClick("2");

                break;
            case R.id.btn_3:
                btn3.setBackgroundResource(R.drawable.laguage_colored_button_border);
                btn3.setTextColor(Color.WHITE);
                btn1.setBackgroundResource(R.drawable.laguage_button_border);
                btn1.setTextColor(Color.GRAY);
                btn2.setBackgroundResource(R.drawable.laguage_button_border);
                btn2.setTextColor(Color.GRAY);
                btn4.setBackgroundResource(R.drawable.laguage_button_border);
                btn4.setTextColor(Color.GRAY);
                btn5.setBackgroundResource(R.drawable.laguage_button_border);
                btn5.setTextColor(Color.GRAY);
                btn6.setBackgroundResource(R.drawable.laguage_button_border);
                btn6.setTextColor(Color.GRAY);
                doneButtonClick("3");

                break;
            case R.id.btn_4:
                btn4.setBackgroundResource(R.drawable.laguage_colored_button_border);
                btn4.setTextColor(Color.WHITE);
                btn1.setBackgroundResource(R.drawable.laguage_button_border);
                btn1.setTextColor(Color.GRAY);
                btn3.setBackgroundResource(R.drawable.laguage_button_border);
                btn3.setTextColor(Color.GRAY);
                btn2.setBackgroundResource(R.drawable.laguage_button_border);
                btn2.setTextColor(Color.GRAY);
                btn5.setBackgroundResource(R.drawable.laguage_button_border);
                btn5.setTextColor(Color.GRAY);
                btn6.setBackgroundResource(R.drawable.laguage_button_border);
                btn6.setTextColor(Color.GRAY);
                doneButtonClick("4");
                break;
            case R.id.btn_5:
                btn5.setBackgroundResource(R.drawable.laguage_colored_button_border);
                btn5.setTextColor(Color.WHITE);
                btn1.setBackgroundResource(R.drawable.laguage_button_border);
                btn1.setTextColor(Color.GRAY);
                btn3.setBackgroundResource(R.drawable.laguage_button_border);
                btn3.setTextColor(Color.GRAY);
                btn4.setBackgroundResource(R.drawable.laguage_button_border);
                btn4.setTextColor(Color.GRAY);
                btn2.setBackgroundResource(R.drawable.laguage_button_border);
                btn2.setTextColor(Color.GRAY);
                btn6.setBackgroundResource(R.drawable.laguage_button_border);
                btn6.setTextColor(Color.GRAY);
                doneButtonClick("5");
                break;
            case R.id.btn_6:
                btn6.setBackgroundResource(R.drawable.laguage_colored_button_border);
                btn6.setTextColor(Color.WHITE);
                btn1.setBackgroundResource(R.drawable.laguage_button_border);
                btn1.setTextColor(Color.GRAY);
                btn3.setBackgroundResource(R.drawable.laguage_button_border);
                btn3.setTextColor(Color.GRAY);
                btn4.setBackgroundResource(R.drawable.laguage_button_border);
                btn4.setTextColor(Color.GRAY);
                btn5.setBackgroundResource(R.drawable.laguage_button_border);
                btn5.setTextColor(Color.GRAY);
                btn2.setBackgroundResource(R.drawable.laguage_button_border);
                btn2.setTextColor(Color.GRAY);
                doneButtonClick("6");
                break;
            case R.id.btn_done:
                Toast.makeText(getContext(), "Language is selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imgview_leftarrow:
                getActivity().onBackPressed();
                break;
        }
    }


}
