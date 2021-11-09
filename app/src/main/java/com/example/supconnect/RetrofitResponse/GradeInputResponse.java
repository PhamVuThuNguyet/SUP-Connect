package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.NewGrade;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GradeInputResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("new")
    @Expose
    private NewGrade _new;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NewGrade getNew() {
        return _new;
    }

    public void setNew(NewGrade _new) {
        this._new = _new;
    }
}
