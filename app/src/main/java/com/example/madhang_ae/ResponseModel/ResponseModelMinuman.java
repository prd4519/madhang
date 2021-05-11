package com.example.madhang_ae.ResponseModel;

import com.example.madhang_ae.Model.ModelMinuman;

import java.util.List;

public class ResponseModelMinuman {
    private String message;
    private List<ModelMinuman> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModelMinuman> getData() {
        return data;
    }

    public void setData(List<ModelMinuman> data) {
        this.data = data;
    }
}
