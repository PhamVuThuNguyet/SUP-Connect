package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.PersonalTargetItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TargetListResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("targets")
    @Expose
    private List<PersonalTargetItem> targets = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<PersonalTargetItem> getTargets() {
        return targets;
    }

    public void setTargets(List<PersonalTargetItem> targets) {
        this.targets = targets;
    }
}
