package com.fatel.mamtv1.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Group implements Serializable{
    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String status;
    @Expose
    private int score;
    @SerializedName("member")
    private int amountMember;
    @Expose
    private User admin;
    @Expose
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
        amountMember++;
    }
}
