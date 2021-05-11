package com.example.madhang_ae.ResponseModel;

import com.example.madhang_ae.Model.ModelPenjual;

import java.util.List;

public class ResponseModelPenjual {
    private String message;
    private List<ModelPenjual> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModelPenjual> getData() {
        return data;
    }

    public void setData(List<ModelPenjual> data) {
        this.data = data;
    }
}
