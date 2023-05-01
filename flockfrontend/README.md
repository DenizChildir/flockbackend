# README for Flock Front End

The front end of flock is built using Angular. It demonstrates basic features of a social media platform where users can post tweets, reply to tweets, and search for tweets by username. This document provides an overview of the backend file structure, as well as descriptions of the individual code files.

|Team Members| 
|----------------|
|Andrew Wang|
|Nicholas Tan|
|Jonathan Del Vecchio|
|Deniz Childir|

## Features
- Create and post tweets
- Reply to tweets
- Edit and delete tweets and replies
- Upload and display images in tweets and replies
- Search for tweets and replies by username

## Project Structure

### Basic File Structure:
    src
    -app
    --app.component.css
    --app.component.ts
    --app.module.ts
    --app-routing.module.ts
    --create-tweet
    create-tweet.component.html
    create-tweet.component.ts
    --models
    reply.ts
    tweet.ts
    --pipes
    order-pipe.ts
    --reply
    reply.component.html
    reply.component.ts
    --search-tweets
    search-tweets.component.html
    search-tweets.component.ts
    --services
    tweet.service.ts
    tweet-history
    tweet-history.component.html
    tweet-history.component.ts


The project is organized into different components and services, each handling a specific functionality.

Components
- app.component: The root component that hosts other components.
- create-tweet: Handles the tweet creation functionality. Users can input a title, content, and optionally upload an image for the tweet.
- tweet-history: Displays a list of all tweets, including options to edit or delete a tweet, and reply to a tweet.
- reply: Manages the functionality of creating a reply to a tweet, as well as listing, editing, and deleting replies for a specific tweet.
- search-tweets: Provides a search feature that allows users to search for tweets and replies based on a username.
Services
- tweet.service: Contains all the API calls and logic related to tweets and replies, such as creating, retrieving, updating, and deleting tweets and replies, as well as uploading images.

Models
- tweet: Defines the structure of a tweet object.
- reply: Defines the structure of a reply object.

Dependencies:
- Angular CLI
- Node.js
