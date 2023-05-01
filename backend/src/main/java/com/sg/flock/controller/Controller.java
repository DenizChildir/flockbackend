/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sg.flock.dao.Dao;
import com.sg.flock.dao.DataPersistenceException;
import com.sg.flock.dto.Reply;
import com.sg.flock.dto.Tweet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

;
import com.sg.flock.service.InvalidTweetIdException;
import com.sg.flock.service.ReplyValidationException;
import com.sg.flock.service.TweetValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author nicho
 */

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class Controller {

    Dao sl =new Dao();





    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody Tweet tweet) throws DataPersistenceException, TweetValidationException {
        // Insert the tweet into the database
        sl.insertTweet(tweet);
    }

    @PostMapping("/replies")
    public void createReply(@RequestBody Reply reply) throws DataPersistenceException, ReplyValidationException {
        // Perform validation checks on the tweet object
//        if (reply.getPost() == null || reply.getPost().isEmpty()) {
//            throw new ReplyValidationException("reply message cannot be empty");
//        }
//        if (reply.getPost().length() > 280) {
//            throw new ReplyValidationException("reply message length cannot exceed 280 characters");
//        }

        // Insert the tweet into the database
        sl.insertReply(reply);
    }

    @GetMapping("/posts")
    public List<Tweet> getAllPosts() throws DataPersistenceException {
        List<Tweet> ret = sl.getAllTweets();
        Collections.reverse(ret);
        return ret;
    }

    @GetMapping("/replies/{tweetId}")
    public List<Reply> getAllReplies(@PathVariable("tweetId") int tweetId) throws DataPersistenceException {
        return sl.getRepliesForTweetId(tweetId);
    }

    @GetMapping("/posts/{tweetId}")
    public Tweet getTweetById(@PathVariable("tweetId") int tweetId) throws DataPersistenceException {

        return sl.getTweetById(tweetId);
    }

    @GetMapping("/posts/name/{user_name}")
    public List<Tweet> getTweetByUserName(@PathVariable ("user_name") String user_name) throws DataPersistenceException {
        return sl.getTweetByUserName(user_name);
    }

    @GetMapping("/replies/name/{user_name}")
    public List<Reply> getReplyByUserName(@PathVariable ("user_name") String user_name) throws DataPersistenceException {
        return sl.getReplyByUserName(user_name);
    }

    @DeleteMapping("/posts/{tweetId}")
    public void deleteTweetById(@PathVariable("tweetId") int tweetId) throws DataPersistenceException {
        sl.deleteTweetById(tweetId);
    }

    @DeleteMapping("/replies/{tweetId}/{replyId}")
    public void deleteReplyById(@PathVariable("tweetId") int tweetId, @PathVariable("replyId") int replyId) throws DataPersistenceException {
        sl.deleteReplyById(tweetId, replyId);
    }

    @PutMapping("/posts/{tweetId}")
    public void editTweetById(@PathVariable("tweetId") int tweetId, @RequestBody Tweet tweet) throws DataPersistenceException {
        sl.editTweetById(tweetId, tweet);
    }

    @PutMapping("/replies/{tweetId}/{replyId}")
    public void editReplyById(@PathVariable("tweetId") int tweetId, @PathVariable("replyId") int replyId, @RequestBody Reply reply) throws DataPersistenceException {
        sl.editReplyById(tweetId, replyId, reply);
    }
}
