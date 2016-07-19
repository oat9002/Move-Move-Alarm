package com.fatel.mamtv1.Model;

import android.content.Context;
import android.provider.BaseColumns;

import com.fatel.mamtv1.Helper.PostureHelper;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 23/10/2558.
 */
@Getter
@Setter
public class Posture {

    private int id;
    private int idPosture;
    private int image;
    private String name;
    private String description;
    private int mode;

    private PostureHelper helper;

    public static final String TABLE = "postureTB";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String IDPOSTURE = "idPosture";
        public static final String IMAGE = "image";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String MODE = "mode";
    }

    public Posture(int id, int idPosture, int image, String name, String description, int mode) {
        this.id = id;
        this.idPosture = idPosture;
        this.image = image;
        this.name = name;
        this.description = description;
        this.mode = mode;
    }

    public Posture(int idPosture, int image, String name, String description, int mode) {
        this.id = -1;
        this.idPosture = idPosture;
        this.image = image;
        this.name = name;
        this.description = description;
        this.mode = mode;
    }

    public void save(Context context) {
        PostureHelper postureHelper = new PostureHelper(context);
        if (this.id == -1) {
            this.id = postureHelper.addPosture(this);
        } else {
            postureHelper.updatePosture(this);
        }
    }

    public static Posture find(int idPosture, Context context) {
        PostureHelper postureHelper = new PostureHelper(context);
        if (postureHelper.getPosture(idPosture) == null) {
            return null;
        } else
            return postureHelper.getPosture(idPosture);
    }

    public static ArrayList<Posture> findMode(int mode, Context context) {
        PostureHelper postureHelper = new PostureHelper(context);
        if (postureHelper.getPostureMode(mode) == null) {
            return null;
        } else
            return postureHelper.getPostureMode(mode);
    }

    public static int getPostureCount(Context context) {
        PostureHelper postureHelper = new PostureHelper(context);
        return postureHelper.postureCount();
    }
}
