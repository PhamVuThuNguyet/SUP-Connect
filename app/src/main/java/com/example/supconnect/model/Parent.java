package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parent {

    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
    @SerializedName("account_id")
    @Expose
    private Integer accountId;
    @SerializedName("first_name_parent")
    @Expose
    private String firstNameParent;
    @SerializedName("last_name_parent")
    @Expose
    private String lastNameParent;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("phone_number_parent")
    @Expose
    private String phoneNumberParent;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getFirstNameParent() {
        return firstNameParent;
    }

    public void setFirstNameParent(String firstNameParent) {
        this.firstNameParent = firstNameParent;
    }

    public String getLastNameParent() {
        return lastNameParent;
    }

    public void setLastNameParent(String lastNameParent) {
        this.lastNameParent = lastNameParent;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumberParent() {
        return phoneNumberParent;
    }

    public void setPhoneNumberParent(String phoneNumberParent) {
        this.phoneNumberParent = phoneNumberParent;
    }

}
