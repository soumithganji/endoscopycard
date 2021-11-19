package com.example.endoscopycard.Ui.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.endoscopycard.Api.ApiClient;
import com.example.endoscopycard.Api.ApiInterface;
import com.example.endoscopycard.R;
import com.example.endoscopycard.Response.AddPatientResponse;
import com.example.endoscopycard.Utils.PrefKeys;
import com.example.endoscopycard.databinding.ActivityEmptyCardPageBinding;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmptyCardPage extends AppCompatActivity {

    ActivityEmptyCardPageBinding activityEmptyCardPageBinding;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_card_page);

        activityEmptyCardPageBinding = DataBindingUtil.setContentView((Activity)this, R.layout.activity_empty_card_page);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));

        activityEmptyCardPageBinding.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpatient();
            }
        });

    }

    public void addpatient() {
        JsonObject postData = new JsonObject();
        postData.addProperty("hospital_no",activityEmptyCardPageBinding.hospno.getText().toString());
        postData.addProperty("patient_name",activityEmptyCardPageBinding.nameinput.getText().toString());
        postData.addProperty("date_of_birth",activityEmptyCardPageBinding.dobinput.getText().toString());
        postData.addProperty("sex",activityEmptyCardPageBinding.genderinput.getText().toString());
        postData.addProperty("address",activityEmptyCardPageBinding.addressinput.getText().toString());
        postData.addProperty("mobile_no",activityEmptyCardPageBinding.mobileinput.getText().toString());
        postData.addProperty("email",activityEmptyCardPageBinding.emailinput.getText().toString());
        postData.addProperty("country","India");
        Log.d("onResponse",postData.toString());

        ApiInterface apiSeitemViewice = ApiClient.getClient().create(ApiInterface.class);
        Call<AddPatientResponse> call;
        call = apiSeitemViewice.addPatient(sharedPreferences.getString(PrefKeys.TOKEN,""),postData);
        call.enqueue(new Callback<AddPatientResponse>() {
            @Override
            public void onResponse(Call<AddPatientResponse> call, Response<AddPatientResponse> response) {
                Log.d("onResponse",response.toString());

                if(response.code()==409){
                    Toast.makeText(getApplicationContext(),"Patient Exists",Toast.LENGTH_SHORT).show();
                }

                else if(response.code()==201){
                    sharedPreferences.edit().putString(PrefKeys.HOSPITAL_NO,response.body().getData().getHospitalNo()).apply();
                    startActivity(new Intent(getApplicationContext(),TapToWrite.class));
                    finish();
                }

            }

            @Override
            public void onFailure(Call<AddPatientResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }

        });
    }
}