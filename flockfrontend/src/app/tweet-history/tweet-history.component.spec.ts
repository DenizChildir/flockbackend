import {ComponentFixture, TestBed} from '@angular/core/testing';

import {TweetHistoryComponent} from './tweet-history.component';

describe('TweetHistoryComponent', () => {
  let component: TweetHistoryComponent;
  let fixture: ComponentFixture<TweetHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TweetHistoryComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(TweetHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
