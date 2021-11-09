package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.Assignment;
import com.example.supconnect.model.LeaveNotice;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaveNoticeResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("leavenotices")
    @Expose
    private List<LeaveNotice> leaveNotices = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<LeaveNotice> getLeaveNotices() {
        return leaveNotices;
    }

    public void setLeaveNotices(List<LeaveNotice> leaveNotices) {
        this.leaveNotices = leaveNotices;
    }
}
