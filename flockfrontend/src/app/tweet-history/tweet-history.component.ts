import {Component, OnInit, ViewChild} from '@angular/core';
import {Tweet} from '../models/tweet';
import {TweetService} from '../services/tweet.service';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {ReplyComponent} from '../reply/reply.component';


@Component({
  selector: 'app-tweet-history',
  templateUrl: './tweet-history.component.html',
  styleUrls: ['./tweet-history.component.scss'],
})
export class TweetHistoryComponent implements OnInit {
  tweets: Tweet[] = [];
  editTweetForm!: FormGroup;
  selectedFile: File | null = null;
  selectedTweetId: number | null = null;
  showCreateTweetForm = false;



  @ViewChild(ReplyComponent) replyComponent!: ReplyComponent;
  public showReplyForm: boolean | undefined;

  constructor(private tweetService: TweetService, private fb: FormBuilder) {
  }

  ngOnInit(): void {
    console.log('TweetHistoryComponent ngOnInit called');
    this.loadTweets();
    this.initEditTweetForm();
  }

  deleteTweet(tweetId: number): void {
    this.tweetService.deleteTweetById(tweetId).subscribe(() => {
      this.tweets = this.tweets.filter((tweet) => tweet.id !== tweetId);
    });
  }

  loadTweets() {
    console.log('Loading tweets...');
    this.tweetService.getAllTweets().subscribe((tweets) => {
      this.tweets = tweets;

      //added
      console.log('Received tweets:', tweets);

    });
  }

  initEditTweetForm(): void {
    this.editTweetForm = this.fb.group({
      title: ['', Validators.required],
      tweet: ['', Validators.required],
    });
  }

  toggleEditMode(tweet: Tweet): void {
    tweet.editMode = !tweet.editMode;
  }

  async saveChanges(tweet: Tweet): Promise <void> {
    if (this.selectedFile) {
      try {
        tweet.img = await this.readFileAsDataURL(this.selectedFile);
      } catch (error) {
        console.error('Error reading the file:', error);
      }
    }
    this.tweetService.editTweetById(tweet.id!, tweet).subscribe(() => {
      // Refresh the tweets list after editing
      this.loadTweets();
      this.selectedFile = null;
    });
    tweet.editMode = false;
  }

  // toggleReplyForm(tweetId: number): void {
  //   this.selectedTweetId = this.selectedTweetId === tweetId ? null : tweetId;
  // }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  toggleCreateTweetForm(): void {
    this.showCreateTweetForm = !this.showCreateTweetForm;
  }

  private async readFileAsDataURL(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();

      reader.onload = () => {
        resolve(reader.result as string);
      };

      reader.onerror = (error) => {
        reject(error);
      };

      reader.readAsDataURL(file);
    });
  }
  toggleReplyForm(tweetId: number) {
    this.selectedTweetId = this.selectedTweetId === tweetId ? null : tweetId;
    this.showReplyForm = !this.showReplyForm;
  }


}
