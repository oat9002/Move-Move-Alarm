package com.fatel.mamtv1.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Group implements Serializable{
    @Expose
    private int id = 0;
    @Expose
    private String name = null;
    @Expose
    private String status = null;
    @Expose
    private int score = 0;
    @SerializedName("member")
    private int amountMember = 0;
    @Expose
    private User admin = null;
    @Expose
    private List<User> members;
}
