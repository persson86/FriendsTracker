package com.mob.schneiderpersson.friendstracker;

public class UsersIndex {
    public String userNode, username;

    public UsersIndex(){}

    public UsersIndex(String userNode, String username)
    {
        userNode = userNode;
        username = username;
    }

    public String getUserNode() {
        return userNode;
    }

    public void setUserNode(String userNode) {
        this.userNode = userNode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
