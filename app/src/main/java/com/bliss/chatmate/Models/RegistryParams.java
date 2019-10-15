package com.bliss.chatmate.Models;

import android.content.Context;
import android.net.Uri;

public class RegistryParams {
    private Context context;
    private String password, email, path;
    private Uri uri;

    public RegistryParams() {

    }

    public RegistryParams(Context context, String password, String email, String path, Uri uri) {
        this.context = context;
        this.password = password;
        this.email = email;
        this.path = path;
        this.uri = uri;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
