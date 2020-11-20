package com.shivenderkumar.kitchenbook.model;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String email;
    private String image_url;

    public User() {
    }

    public User(String name, String email, String image_url) {
        this.name = name;
        this.email = email;
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
