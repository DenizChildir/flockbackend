import {Component, EventEmitter, Output, Input, ViewChild} from '@angular/core';
import {Tweet} from '../models/tweet';
import {TweetService} from '../services/tweet.service';
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-create-tweet',
  templateUrl: './create-tweet.component.html',
  styleUrls: ['./create-tweet.component.scss'],
})
export class CreateTweetComponent {
  tweet: Tweet = new Tweet();
  selectedFile: File | null = null;
  @Output() tweetCreated = new EventEmitter<void>();
  @Input() showTweetForm = false;
  @Output() tweetCanceled = new EventEmitter<void>();
  imageData: string = '';
  @ViewChild('tweetFormRef') tweetFormRef!: NgForm;




  constructor(private tweetService: TweetService) {
  }

  onCancel() {
    this.tweet = new Tweet();
    this.selectedFile = null;
    this.imageData = '';
    this.tweetCanceled.emit();
    this.tweetFormRef.resetForm();
  }

  onFileSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.onload = () => {
        this.imageData = reader.result?.toString() ?? '';
      };
      reader.readAsDataURL(file);
    }
  }
  /*
  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }*/

  async createTweet() {
    if (this.selectedFile) {
      try {
        this.tweet.img = await this.readFileAsDataURL(this.selectedFile);
      } catch (error) {
        console.error('Error reading the file:', error);
      }
    }
    console.log('Sending tweet data:', this.tweet);
    this.tweetService.createTweet(this.tweet).subscribe((createdTweet) => {
      this.tweet = new Tweet();
      this.selectedFile = null;
      this.selectedFile = null;
      this.imageData = '';
      this.tweetCreated.emit();
      this.tweetCanceled.emit();
    });
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
  resetForm() {
    this.tweetFormRef.resetForm();
  }
}

