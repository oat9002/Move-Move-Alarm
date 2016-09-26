package com.kmitl.movealarm.Model;

/**
 * Created by kid14 on 8/17/2016.
 */
public class PostureType {
    private String title;
    int image;

    public PostureType(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

}
