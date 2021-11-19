package com.example.endoscopycard.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorResponse {

    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
