package com.example.team9.flashbackmusic_team9;

import java.util.ArrayList;

/**
 * Created by cyrusdeng on 09/03/2018.
 */

public class User {
    private String email;
    private ArrayList<String> friends;

    public User(String email) {
        this.email = email;
        this.friends = new ArrayList<>();
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void addFriend(String email) {
        friends.add(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
