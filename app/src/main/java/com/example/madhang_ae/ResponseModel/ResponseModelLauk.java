package com.example.madhang_ae.ResponseModel;

import com.example.madhang_ae.Model.ModelLauk;

import java.util.List;

public class ResponseModelLauk {
    private String message;
    private List<ModelLauk> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModelLauk> getData() {
        return data;
    }

    public void setData(List<ModelLauk> data) {
        this.data = data;
    }
}
