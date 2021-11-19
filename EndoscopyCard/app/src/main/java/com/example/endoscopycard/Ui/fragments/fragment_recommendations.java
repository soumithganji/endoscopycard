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
import com.example.endoscopycard.Response.GetPatientRecommendations;
import com.example.endoscopycard.Response.GetPatientReport;
import com.example.endoscopycard.Utils.PrefKeys;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_recommendations extends Fragment {

    private static fragment_recommendations instance;
    katex.hourglass.in.mathlib.MathView recommendations;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    TextView ptname;


    public fragment_recommendations() {
        // Required empty public constructor
    }

    public static synchronized fragment_recommendations getInstance() {
        if (instance == null)
            instance = new fragment_recommendations();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_recommendations, container, false);
        sharedPreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        recommendations=itemView.findViewById(R.id.recommendations);
        ptname=itemView.findViewById(R.id.ptname);

        ApiInterface apiSeitemViewice = ApiClient.getClient().create(ApiInterface.class);
        Call<GetPatientRecommendations> call;
        call = apiSeitemViewice.getpatientRecommendations(sharedPreferences.getString(PrefKeys.TOKEN,""),sharedPreferences.getString(PrefKeys.HOSPITAL_NO,""));
        call.enqueue(new Callback<GetPatientRecommendations>() {
            @Override
            public void onResponse(Call<GetPatientRecommendations> call, Response<GetPatientRecommendations> response) {

                GetPatientRecommendations data=response.body();
                recommendations.setDisplayText(data.getData());

            }

            @Override
            public void onFailure(Call<GetPatientRecommendations> call, Throwable t) {
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