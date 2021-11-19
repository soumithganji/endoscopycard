package com.example.endoscopycard.Ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.endoscopycard.Api.ApiClient;
import com.example.endoscopycard.Api.ApiInterface;
import com.example.endoscopycard.R;
import com.example.endoscopycard.Response.AddPatientResponse;
import com.example.endoscopycard.Response.GetPatientDetails;
import com.example.endoscopycard.Ui.activities.TapToWrite;
import com.example.endoscopycard.Utils.PrefKeys;
import com.example.endoscopycard.databinding.ActivityEmptyCardPageBinding;
import com.example.endoscopycard.databinding.FragmentDetailsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_details extends Fragment {

    private static fragment_details instance;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    TextView hospno,nameinput,dobinput,genderinput,addressinput,mobileinput,emailinput,ptname;



    public fragment_details() {
        // Required empty public constructor
    }

    public static synchronized fragment_details getInstance() {
        if (instance == null)
            instance = new fragment_details();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_details, container, false);
        sharedPreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        ptname=itemView.findViewById(R.id.ptname);
        hospno=itemView.findViewById(R.id.hospno);
        nameinput=itemView.findViewById(R.id.nameinput);
        dobinput=itemView.findViewById(R.id.dobinput);
        genderinput=itemView.findViewById(R.id.genderinput);
        addressinput=itemView.findViewById(R.id.addressinput);
        mobileinput=itemView.findViewById(R.id.mobileinput);
        emailinput=itemView.findViewById(R.id.emailinput);


        ApiInterface apiSeitemViewice = ApiClient.getClient().create(ApiInterface.class);
        Call<GetPatientDetails> call;
        call = apiSeitemViewice.getpatientDetails(sharedPreferences.getString(PrefKeys.TOKEN,""),sharedPreferences.getString(PrefKeys.HOSPITAL_NO,""));
        call.enqueue(new Callback<GetPatientDetails>() {
            @Override
            public void onResponse(Call<GetPatientDetails> call, Response<GetPatientDetails> response) {

                GetPatientDetails data=response.body();

                hospno.setText(data.getHospitalNo());
                nameinput.setText(data.getName());
                ptname.setText(data.getName());
                dobinput.setText(data.getDob());
                genderinput.setText(data.getSex());
                addressinput.setText(data.getAddress());
                mobileinput.setText(data.getMobile());
                emailinput.setText(data.getEmail());

            }

            @Override
            public void onFailure(Call<GetPatientDetails> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }

        });


        // Inflate the layout for this fragment
        return itemView;
    }
}