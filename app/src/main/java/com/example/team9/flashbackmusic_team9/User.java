package com.example.team9.flashbackmusic_team9;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cyrusdeng on 09/03/2018.
 */

public class User {
    private String name;
    private String email;
//    private ArrayList<String> friends;
    private HashMap<String, String> friends;

    public User(String email, String name) {
        this.email = email;
        this.name = name;
//        this.friends = new ArrayList<>();
        this.friends = new HashMap<>();
    }

//    public void setFriends(ArrayList<String> friends) {
//        this.friends = friends;
//    }

    public HashMap<String, String> getFriends() {
        return friends;
    }

    public void addFriend(String email, String name) {
//        friends.add(email);
        friends.put(email, name);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {this.name = name;}

    public String getName() {return name;}
}
