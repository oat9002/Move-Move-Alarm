package com.fatel.mamtv1.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Group implements Serializable{
    private int id;
    private String name;
    private String status;
    private int score;
    private User admin;
    private GroupProgress progress;
    private List<User> members = new ArrayList<>();

    public Group(String name, User admin)
    {
        this.name = name;
        this.admin = admin;
        addMember(admin);
    }

    public void addMember(User user)
    {
        members.add(user);
    }

    public void addScore(int score) { this.score += score; }
}