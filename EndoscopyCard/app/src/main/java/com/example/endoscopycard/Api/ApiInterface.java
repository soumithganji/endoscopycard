package com.example.endoscopycard.Api;

import com.example.endoscopycard.Response.AddPatientResponse;
import com.example.endoscopycard.Response.DoctorResponse;
import com.example.endoscopycard.Response.GetPatientDetails;
import com.example.endoscopycard.Response.GetPatientRecommendations;
import com.example.endoscopycard.Response.GetPatientReport;
import com.example.endoscopycard.Response.GetPatientScans;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @POST("doctor/login")
    Call<DoctorResponse> doctorLogin(@Body JsonObject object);

    @POST("admin/create/patient")
    Call<AddPatientResponse> addPatient(@Header("x-auth-token") String token, @Body JsonObject object);

    @GET("patient/details/{patientId}")
    Call<GetPatientDetails> getpatientDetails(@Header("x-auth-token") String token, @Path("patientId") String patientId);

    @GET("patient/report/{patientId}")
    Call<GetPatientReport> getpatientReport(@Header("x-auth-token") String token, @Path("patientId") String patientId);

    @GET("patient/recommendation/{patientId}")
    Call<GetPatientRecommendations> getpatientRecommendations(@Header("x-auth-token") String token, @Path("patientId") String patientId);

    @GET("patient/scans/{patientId}")
    Call<GetPatientScans> getpatientScans(@Header("x-auth-token") String token, @Path("patientId") String patientId);

}