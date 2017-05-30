package controllers;

import beans.Profile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ProfileEditController {

    private boolean isEdit;

    public ProfileEditController() {}

    public String startEdit() {
        isEdit = true;
        return "profile?faces-redirect=true&includeViewParams=true";
    }

    public String cancelEdit() {
        isEdit = false;
        return "profile?faces-redirect=true&includeViewParams=true";
    }

    public String saveEdit(Profile profile) {
        isEdit = false;
        profile.updateInfo();
        return "profile?faces-redirect=true&includeViewParams=true";
    }

    public boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean edit) {
        isEdit = edit;
    }
}
