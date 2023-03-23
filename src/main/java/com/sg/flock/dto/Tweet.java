package com.sg.flock.dto;

import java.util.LinkedList;
import java.util.List;

public class Tweet {
    public int id;
    public String userName;
    public String title;
    public String postText;
    public String image;
    public String date;
    public LinkedList<Reply> replies;

    public Tweet(int id, String userName, String title, String post, String image, String date, LinkedList<Reply> replies) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.postText = post;
        this.image = image;
        this.date = date;
        this.replies = replies;
    }
    public Tweet(String id, String userName, String title, String post, String image, String date, LinkedList<Reply> replies) {
        this.id = Integer.parseInt(id);
        this.userName = userName;
        this.title = title;
        this.postText = post;
        this.image = image;
        this.date = date;
        this.replies = replies;
    }
    public Tweet(){}

    public LinkedList<Reply> getReplies() {
        return replies;
    }

    public void setReplies(LinkedList<Reply> replies) {
        this.replies = replies;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost() {
        return postText;
    }

    public void setPost(String post) {
        this.postText = post;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}