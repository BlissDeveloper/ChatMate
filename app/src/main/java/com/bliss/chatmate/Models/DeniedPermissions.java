package com.bliss.chatmate.Models;

import java.util.List;

public class DeniedPermissions {
    boolean isAllGranted;
    List<String> permissions;

    public DeniedPermissions() {

    }

    public boolean isAllGranted() {
        return isAllGranted;
    }

    public void setAllGranted(boolean allGranted) {
        isAllGranted = allGranted;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
