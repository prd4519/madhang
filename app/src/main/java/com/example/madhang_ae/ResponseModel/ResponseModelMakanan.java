package com.example.madhang_ae.ResponseModel;

import com.example.madhang_ae.Model.ModelMakanan;


import java.util.List;

public class ResponseModelMakanan {
    private String message;
    private List<ModelMakanan> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModelMakanan> getData() {
        return data;
    }

    public void setData(List<ModelMakanan> data) {
        this.data = data;
    }
}
