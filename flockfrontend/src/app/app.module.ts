import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserModule } from './user/user.module';
import { NavComponent } from './nav/nav.component';
import { AngularFireModule } from '@angular/fire/compat'
import { environment } from 'src/environments/environment';
import { AngularFireAuthModule } from '@angular/fire/compat/auth'
import { AngularFirestoreModule } from '@angular/fire/compat/firestore';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CreateTweetComponent} from "./create-tweet/create-tweet.component";
import {TweetHistoryComponent} from "./tweet-history/tweet-history.component";
import {SearchTweetsComponent} from "./search-tweets/search-tweets.component";
import {OrderPipe} from "./pipes/order-pipe";
import {ReplyComponent} from "./reply/reply.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {MatDialogModule} from "@angular/material/dialog";
import {TweetService} from "./services/tweet.service";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {HomeComponent} from "./home/home.component";
import {Routes} from "@angular/router";

const routes: Routes = [
  { path: '', component: HomeComponent }, // Add the HomeComponent route
  { path: 'search', component: SearchTweetsComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    CreateTweetComponent,
    TweetHistoryComponent,
    ReplyComponent,
    OrderPipe,
    SearchTweetsComponent,
    HomeComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    UserModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireAuthModule,
    AngularFirestoreModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    BrowserAnimationsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule,],
  providers: [TweetService],
  bootstrap: [AppComponent],

})
export class AppModule { }
