package com.mob.schneiderpersson.friendstracker;

import java.util.HashMap;
import java.util.Map;

public class User {
    public String email, username, id;

    public User(){}

    public User(String email, String username, String id)
    {
        email = email;
        username = username;
        id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}