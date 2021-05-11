package com.example.madhang_ae.ResponseModel;

import com.example.madhang_ae.Model.ModelJajanan;

import java.util.List;

public class ResponseModelJajanan {
    private String message;
    private List<ModelJajanan> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModelJajanan> getData() {
        return data;
    }

    public void setData(List<ModelJajanan> data) {
        this.data = data;
    }
}
