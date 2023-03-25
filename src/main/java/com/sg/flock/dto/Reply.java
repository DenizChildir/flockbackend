package com.sg.flock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Reply {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTweet_id() {
        return tweet_id;
    }

    public void setTweetId(int tweet_id) {
        this.tweet_id = tweet_id;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("id")
    int id;
    @JsonProperty("tweet_id")
    int tweet_id;
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

}