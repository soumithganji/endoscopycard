package com.example.endoscopycard.Ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.endoscopycard.Api.ApiClient;
import com.example.endoscopycard.Api.ApiInterface;
import com.example.endoscopycard.R;
import com.example.endoscopycard.Response.GetPatientDetails;
import com.example.endoscopycard.Response.GetPatientScans;
import com.example.endoscopycard.Utils.Adapter;
import com.example.endoscopycard.Utils.PrefKeys;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_scans extends Fragment {

    private static fragment_scans instance;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    TextView ptname;
    RecyclerView recyclerView;

    public fragment_scans() {
        // Required empty public constructor
    }

    public static synchronized fragment_scans getInstance() {
        if (instance == null)
            instance = new fragment_scans();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_scans, container, false);
        sharedPreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        ptname=itemView.findViewById(R.id.ptname);
        recyclerView=itemView.findViewById(R.id.scans);




        ApiInterface apiSeitemViewice = ApiClient.getClient().create(ApiInterface.class);
        Call<GetPatientScans> call;
        call = apiSeitemViewice.getpatientScans(sharedPreferences.getString(PrefKeys.TOKEN,""),sharedPreferences.getString(PrefKeys.HOSPITAL_NO,""));
        call.enqueue(new Callback<GetPatientScans>() {
            @Override
            public void onResponse(Call<GetPatientScans> call, Response<GetPatientScans> response) {

                GetPatientScans data=response.body();
                Log.d("GetPatientScans",data.getScans().toString());

                JsonArray array=data.getScans();
                Adapter listAdapter = new Adapter(array, getContext());
                recyclerView.setAdapter(listAdapter);
                recyclerView.setLayoutManager( new GridLayoutManager(getContext(), 2));

            }

            @Override
            public void onFailure(Call<GetPatientScans> call, Throwable t) {
                Log.d("GetPatientScans",t.toString());
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