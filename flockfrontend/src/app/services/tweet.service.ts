import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Tweet} from '../models/tweet';
import {Reply} from '../models/reply';


@Injectable({
  providedIn: 'root',
})
export class TweetService {
  private apiUrl = 'http://localhost:3111/api';

  constructor(private http: HttpClient) {
  }

  createTweet(tweet: Tweet): Observable<Tweet> {
    return this.http.post<Tweet>(`${this.apiUrl}/posts`, tweet);
  }

  getAllTweets(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>(`${this.apiUrl}/posts`);
  }

  createReply(reply: Reply): Observable<Reply> {
    return this.http.post<Reply>(`${this.apiUrl}/replies`, reply);
  }


  getRepliesForTweet(tweetId: number): Observable<Reply[]> {
    return this.http.get<Reply[]>(`${this.apiUrl}/replies/${tweetId}`);
  }

  uploadImage(image: File): Observable<string> {
    const formData = new FormData();
    formData.append('image', image);
    return this.http.post(`${this.apiUrl}/upload`, formData, {responseType: 'text'});
  }

  deleteTweetById(tweetId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/posts/${tweetId}`);
  }

  deleteReplyById(tweetId: number, replyId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/replies/${tweetId}/${replyId}`);
  }

  editTweetById(tweetId: number, tweet: Tweet): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/posts/${tweetId}`, tweet);
  }

  editReplyById(tweetId: number, replyId: number, reply: Reply): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/replies/${tweetId}/${replyId}`, reply);
  }

  searchTweetsByUsername(username: string): Observable<Tweet[]> {
    return this.http.get<Tweet[]>(`${this.apiUrl}/posts/name/${username}`);
  }

  searchRepliesByUserName(user_name: string): Observable<Reply[]> {
    return this.http.get<Reply[]>(`${this.apiUrl}/replies/name/${user_name}`);
  }
}
