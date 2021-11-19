package com.example.endoscopycard.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddPatientResponse {
    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private Data data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

   public class Data{
        @SerializedName("hospitalNo")
        @Expose
        private String hospitalNo;

        public String getHospitalNo() {
            return hospitalNo;
        }

        public void setHospitalNo(String hospitalNo) {
            this.hospitalNo = hospitalNo;
        }
    }
}

