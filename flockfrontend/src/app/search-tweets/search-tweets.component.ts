import { Component, OnInit } from '@angular/core';
import { TweetService } from '../services/tweet.service';
import { Tweet } from '../models/tweet';
import { Reply } from '../models/reply';


@Component({
  selector: 'app-search-tweets',
  templateUrl: './search-tweets.component.html',
  styleUrls: ['./search-tweets.component.scss']
})
export class SearchTweetsComponent implements OnInit {
  searchQuery = '';
  searchedTweets: Tweet[] = [];
  searchedReplies: Reply[] = [];


  constructor(private tweetService: TweetService) { }

  ngOnInit(): void {
  }

  searchAll(): void {
    this.tweetService.searchTweetsByUsername(this.searchQuery).subscribe((tweets) => {
      this.searchedTweets = tweets;
    });
    this.tweetService.searchRepliesByUserName(this.searchQuery).subscribe((replies) => {
      this.searchedReplies = replies;
    });
  }
  closeSearch() {
    this.searchedTweets = [];
    this.searchedReplies = [];
  }
}
