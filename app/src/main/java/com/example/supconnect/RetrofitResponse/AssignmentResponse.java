package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.Assignment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignmentResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("assignments")
    @Expose
    private List<Assignment> assignments = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }
}
