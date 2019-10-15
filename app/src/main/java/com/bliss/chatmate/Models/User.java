package com.bliss.chatmate.Models;

public class User {
    String email, image, user_id;

    public User() {

    }

    public User(String email, String image, String user_id) {
        this.email = email;
        this.image = image;
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
