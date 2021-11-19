package com.example.endoscopycard.Ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.endoscopycard.Api.ApiClient;
import com.example.endoscopycard.Api.ApiInterface;
import com.example.endoscopycard.R;
import com.example.endoscopycard.Response.GetPatientDetails;
import com.example.endoscopycard.Response.GetPatientReport;
import com.example.endoscopycard.Utils.PrefKeys;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_report extends Fragment {

    private static fragment_report instance;
    katex.hourglass.in.mathlib.MathView report;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    TextView ptname;

    public fragment_report() {
        // Required empty public constructor
    }

    public static synchronized fragment_report getInstance() {
        if (instance == null)
            instance = new fragment_report();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_report, container, false);
        sharedPreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        report=itemView.findViewById(R.id.report);
        ptname=itemView.findViewById(R.id.ptname);


        ApiInterface apiSeitemViewice = ApiClient.getClient().create(ApiInterface.class);
        Call<GetPatientReport> call;
        call = apiSeitemViewice.getpatientReport(sharedPreferences.getString(PrefKeys.TOKEN,""),sharedPreferences.getString(PrefKeys.HOSPITAL_NO,""));
        call.enqueue(new Callback<GetPatientReport>() {
            @Override
            public void onResponse(Call<GetPatientReport> call, Response<GetPatientReport> response) {

                GetPatientReport data=response.body();
                report.setDisplayText(data.getData());

            }

            @Override
            public void onFailure(Call<GetPatientReport> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }

        });

        apiSeitemViewice = ApiClient.getClient().create(ApiInterface.class);
        Call<GetPatientDetails> call_1;
        call_1 = apiSeitemViewice.getpatientDetails(sharedPreferences.getString(PrefKeys.TOKEN,""),sharedPreferences.getString(PrefKeys.HOSPITAL_NO,""));
        call_1.enqueue(new Callback<GetPatientDetails>() {
            @Override
            public void onResponse(Call<GetPatientDetails> call, Response<GetPatientDetails> response) {

                GetPatientDetails data=response.body();
                ptname.setText(data.getName());

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