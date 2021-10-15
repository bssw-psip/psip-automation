import { Component, OnDestroy } from '@angular/core';
import { NavItem } from '../models/nav-item';
import { MediaChange, MediaObserver } from "@angular/flex-layout";
import { Subscription } from 'rxjs';
import { menu } from '../models/menu';

@Component({
  selector: 'app-features',
  templateUrl: './features.component.html',
  styleUrls: ['./features.component.css']
})
export class FeaturesComponent implements OnDestroy {
  public opened: boolean = true;
  private mediaWatcher: Subscription;
  public menu: NavItem[] = menu;

  constructor(private media: MediaObserver) {
      this.mediaWatcher = this.media.media$.subscribe((mediaChange: MediaChange) => {
          this.handleMediaChange(mediaChange);
      })
  }

  private handleMediaChange(mediaChange: MediaChange) {
    if (this.media.isActive('lt-md')) {
        this.opened = false;
    } else {
        this.opened = true;
    }
  }

  ngOnDestroy() {
      this.mediaWatcher.unsubscribe();
  }

}
