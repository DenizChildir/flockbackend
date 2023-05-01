import {Component, Input, OnInit,} from '@angular/core';
import {Reply} from '../models/reply';
import {TweetService} from '../services/tweet.service';
import {MatDialog} from '@angular/material/dialog';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';

@Component({
  selector: 'app-reply',
  templateUrl: './reply.component.html',
  styleUrls: ['./reply.component.scss'],
})
export class ReplyComponent implements OnInit {
  @Input() tweetId!: number;
  replies: Reply[] = [];
  reply: Reply = new Reply();
  selectedFile: File | null = null;
  editReplyForm!: FormGroup;
  showReplyForm = false;
  @Input() selectedTweetId: number | null = null;



  constructor(private tweetService: TweetService, private dialog: MatDialog, private fb: FormBuilder) {
  }


  ngOnInit(): void {
    this.loadReplies();
    this.initEditReplyForm();
  }

  //Dont remove
  loadReplies() {
    this.tweetService.getRepliesForTweet(this.tweetId).subscribe((replies) => {
      this.replies = replies;
      //added
      console.log('Received replies:', replies);
    });
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  async createReply() {
    if (this.selectedFile) {
      try {
        this.reply.img = await this.readFileAsDataURL(this.selectedFile);
      } catch (error) {
        console.error('Error reading the file:', error);
      }
    }
    this.reply.tweet_id = this.tweetId;
    console.log('Reply object before sending to back-end:', this.reply);
    this.tweetService.createReply(this.reply).subscribe((createdReply) => {
      this.loadReplies();
      this.reply = new Reply();
      this.selectedFile = null;
      this.selectedTweetId = null
    });
  }

  deleteReply(replyId: number): void {
    this.tweetService.deleteReplyById(this.tweetId, replyId).subscribe(() => {
      this.replies = this.replies.filter((reply) => reply.id !== replyId);
    });
  }

  initEditReplyForm(): void {
    this.editReplyForm = this.fb.group({
      title: ['', Validators.required],
      reply: ['', Validators.required],
    });
  }
  showReplyFormForTweet(tweetId: number): void {
    this.selectedTweetId = tweetId;
  }

  toggleEditMode(reply: Reply): void {
    reply.editMode = !reply.editMode;
  }

  async saveChanges(reply: Reply): Promise<void> {
    if (this.selectedFile) {
      try {
        reply.img = await this.readFileAsDataURL(this.selectedFile);
      } catch (error) {
        console.error('Error reading the file:', error);
      }
    }
    this.tweetService.editReplyById(this.tweetId, reply.id, reply).subscribe(() => {
      // Refresh the replies list after editing
      this.loadReplies();
      this.selectedFile = null;
    });
    reply.editMode = false;
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
}
