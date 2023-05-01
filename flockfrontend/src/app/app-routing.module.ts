import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {SearchTweetsComponent} from "./search-tweets/search-tweets.component";



const routes: Routes = [
  { path: '', component: HomeComponent }, // Add the HomeComponent route
  { path: 'search', component: SearchTweetsComponent },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
