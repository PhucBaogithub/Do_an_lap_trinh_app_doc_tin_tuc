package com.example.projectreaderapp.model;

import android.graphics.Bitmap;

public class ModelClass {
    private String edttextusernameSignUp, edttextemailSignUp, edttextpasswordSignUp, edttextphonenumberlSignUp, edttextfullnameSignUp;
    private Bitmap btnimgv_user;

    public ModelClass(String edttextusernameSignUp, String edttextfullnameSignUp, String edttextemailSignUp, String edttextphonenumberlSignUp, String edttextpasswordSignUp, Bitmap btnimgv_user) {
        this.edttextusernameSignUp = edttextusernameSignUp;
        this.edttextfullnameSignUp = edttextfullnameSignUp;
        this.edttextemailSignUp = edttextemailSignUp;
        this.edttextphonenumberlSignUp = edttextphonenumberlSignUp;
        this.edttextpasswordSignUp = edttextpasswordSignUp;
        this.btnimgv_user = btnimgv_user;
    }

    public String getEdttextfullnameSignUp() {
        return edttextfullnameSignUp;
    }

    public void setEdttextfullnameSignUp(String edttextfullnameSignUp) {
        this.edttextfullnameSignUp = edttextfullnameSignUp;
    }

    public String getEdttextusernameSignUp() {
        return edttextusernameSignUp;
    }

    public void setEdttextusernameSignUp(String edttextusernameSignUp) {
        this.edttextusernameSignUp = edttextusernameSignUp;
    }

    public String getEdttextemailSignUp() {
        return edttextemailSignUp;
    }

    public void setEdttextemailSignUp(String edttextemailSignUp) {
        this.edttextemailSignUp = edttextemailSignUp;
    }

    public String getEdttextpasswordSignUp() {
        return edttextpasswordSignUp;
    }

    public void setEdttextpasswordSignUp(String edttextpasswordSignUp) {
        this.edttextpasswordSignUp = edttextpasswordSignUp;
    }

    public String getEdttextphonenumberlSignUp() {
        return edttextphonenumberlSignUp;
    }

    public void setEdttextphonenumberlSignUp(String edttextphonenumberlSignUp) {
        this.edttextphonenumberlSignUp = edttextphonenumberlSignUp;
    }

    public Bitmap getBtnimgv_user() {
        return btnimgv_user;
    }

    public void setBtnimgv_user(Bitmap btnimgv_user) {
        this.btnimgv_user = btnimgv_user;
    }
}
