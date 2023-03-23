/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sg.flock.dao.Dao;
import com.sg.flock.dto.Reply;
import com.sg.flock.dto.Tweet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

;
import com.sg.flock.service.TweetValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nicho
 */

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/posts")
public class Controller {
    /*
      @Autowired
      FlockDao dao;
      */
    Dao dao =new Dao();

//    @PostMapping("/posts")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createPost(@RequestBody String tweet) throws TweetValidationException, JsonProcessingException {
//        System.out.println(tweet);
//    }



    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody String input) throws TweetValidationException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        // Use the readValue() method to parse the input string into a Map object
        Map<String, Object> map = mapper.readValue(input, new TypeReference<Map<String, Object>>() {});

        // Extract the values of all properties from the Map and add them to a List

        List<String> values = new ArrayList<>();
        for (Object value : map.values()) {
            values.add(String.valueOf(value));
        }
        int id= Integer.parseInt(values.get(0));String name=values.get(1),title=values.get(2),
                body=values.get(3),img=values.get(4),date= String.valueOf(LocalDate.now());
        Tweet tweet=new Tweet(id,name,title,body,img,date,null);
        Dao dao=new Dao();
        dao.insertTweet(tweet);
    }



//    @GetMapping("")
//    public List<Tweet> getAllPosts(){
//        System.out.println(sl.getAllTweets());
//       // return sl.convertTweetsToStrings(sl.getAllTweets());
//        List ret =sl.getAllTweets();
//        Collections.reverse(ret);
//        return ret;
//    }

    @GetMapping("")
    public List getAllPosts(){
        /*
        System.out.println(sl.getAllTweets());
        // return sl.convertTweetsToStrings(sl.getAllTweets());
        List ret =sl.getAllTweets();
        Collections.reverse(ret);
        //System.out.println(ret.toString().toString());
        final Logger logger = LoggerFactory.getLogger(Controller.class);
        logger.debug("Sending myObject: {}", ret);
         */
        return dao.getAllTweets2();
    }

    @PostMapping("/{postId}/replies")
    public void createReply(@PathVariable int postId, @RequestBody Reply reply) {
        dao.insertReply(reply);
    }

    private void printReplyToConsole(Reply reply) {
        System.out.println("Reply details:");
        System.out.println("ID: " + reply.getId());
        System.out.println("Post ID: " + reply.getPostId());
        System.out.println("User Name: " + reply.getUserName());
        System.out.println("Title: " + reply.getTitle());
        System.out.println("Post Text: " + reply.getPostText());
        System.out.println("Image: " + reply.getImage());
        System.out.println("Date: " + reply.getDate());
    }


}
