package com.kmitl.movealarm.Model;

import android.content.Context;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;
import com.kmitl.movealarm.Helper.GroupHelper;
import com.kmitl.movealarm.Service.MyApplication;
import com.kmitl.movealarm.Service.UserManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Group implements Serializable{
    private int internalId;
    @SerializedName("id")
    private int groupId;
    private String name;
    private String status;
    private int score;
    //private int adminId;
    private User admin;
    private GroupProgress progress;
    private List<User> members = new ArrayList<>();

    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "grouptable";

    public class Column{
        public static final String ID = BaseColumns._ID;
        public static final String GROUPID = "groupid";
        public static final String NAME = "name";
        public static final String STATUS = "status";
        public static final String SCORE = "score";
        public static final String ADMINID = "adminid";
    }
    public Group(){

    }
    public Group(String name, User admin)
    {
        this.name = name;
       // this.adminId = admin.getId();
        this.admin = admin;
        addMember(admin);

    }
    public Group(int id,int groupId,String name,String status,int score,int adminId){
        this.internalId= id;
        this.groupId = groupId;
        this.name = name;
        this.status = status;
        this.score = score;
        //this.adminId= adminId;
        this.admin = User.find(adminId, MyApplication.getAppContext());
        this.members = User.getGroupMembers(groupId,MyApplication.getAppContext());
        this.progress = GroupProgress.getProgressByGroupId(MyApplication.getAppContext(),groupId);
    }

    public void save(Context context) {
        GroupHelper groupHelper = new GroupHelper(context);
        Group checkGroup = groupHelper.getGroup(groupId);
        if (checkGroup == null) {
            this.internalId = groupHelper.addGroup(this);
        } else {
            this.internalId = checkGroup.getInternalId();
            groupHelper.updateGroup(this);
        }
        User admin = this.admin;
        if(admin.getId() != UserManage.getCurrentUser().getId()) {
            admin.setLogin(0);
            admin.setStatesw(0);
        }
        else{
            admin.setLogin(UserManage.getCurrentUser().getLogin());
            admin.setStatesw(UserManage.getCurrentUser().getStatesw());
        }
        admin.save(context);
        if(this.members!=null){
            for(int i=0;i<this.members.size();i++){
                User member = this.members.get(i);
                if(member.getId() != UserManage.getCurrentUser().getId()) {
                    member.setLogin(0);
                    member.setStatesw(0);
                }
                else{
                    member.setLogin(UserManage.getCurrentUser().getLogin());
                    member.setStatesw(UserManage.getCurrentUser().getStatesw());
                }
                member.save(context);
            }
        }

    }

    public static Group find(int groupId, Context context) {
        GroupHelper groupHelper = new GroupHelper(context);
        Group group = groupHelper.getGroup(groupId);
        if (group == null) {
            return null;
        }
        else{
            group.progress = GroupProgress.getProgressByGroupId(MyApplication.getAppContext(),group.getGroupId());
            return group;
        }
    }


    public void addMember(User user)
    {
        this.members.add(user);
    }

    public void addScore(int score) { this.score += score; }
}