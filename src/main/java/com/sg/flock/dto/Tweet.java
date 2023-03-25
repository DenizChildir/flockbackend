package com.sg.flock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sg.flock.dto.Reply;

import java.util.LinkedList;

public class Tweet {

    @JsonProperty("id")
    int id;
    @JsonProperty("user_name")
    String user_name;
    @JsonProperty("title")
    String title;
    @JsonProperty("post")
    String post;
    @JsonProperty("img")
    String img;
    @JsonProperty("date")
    String date;



    LinkedList<Reply> replies=new LinkedList<>();

    public LinkedList<Reply> getReplies() {
        return replies;
    }

    public void setReplies(LinkedList<Reply> replies) {
        this.replies = replies;
    }


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getImage() {
        return img;
    }

    public void setImage(String image) {
        this.img = image;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}