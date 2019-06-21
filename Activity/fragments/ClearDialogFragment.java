package com.ItaliasCinemas.ajit.Italiascinema.Activity.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.ItaliaApi;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.RetrofitClientInstance;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.ClearHistoryResponse;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.savedata.SaveDataClass;
import com.ItaliasCinemas.ajit.Italiascinema.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private void clearHistory() {
        Log.d("notificationerror12345", SaveDataClass.getUserID(getContext()) + "");
        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<ClearHistoryResponse> call = italiaApi.getClearHistory(SaveDataClass.getUserID(getContext()));
        call.enqueue(new Callback<ClearHistoryResponse>() {
            @Override
            public void onResponse(Call<ClearHistoryResponse> call, Response<ClearHistoryResponse> response) {
                ClearHistoryResponse clearHistoryResponse = response.body();
                if (clearHistoryResponse.getStatus() == 1) {
                    Toast.makeText(getActivity(),clearHistoryResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClearHistoryResponse> call, Throwable t) {

            }
        });

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
                    clearHistory();
                    this.dismiss();
                }
                isClickedYes = false;
                break;
        }
    }


}