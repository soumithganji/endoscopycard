package com.example.endoscopycard.Response;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

public class GetPatientScans {
    @SerializedName("scans")
    @Expose
    private JsonArray scans;

    public JsonArray getScans() {
        return scans;
    }

    public void setScans(JsonArray scans) {
        this.scans = scans;
    }
}
