import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MenuListItemComponent } from './features/menu-list-item/menu-list-item.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FeaturesComponent } from './features/features.component';

import { LoggerModule, NgxLoggerLevel } from 'ngx-logger';

import { BackendService } from './services/backend.service';

@NgModule({
  declarations: [
    AppComponent,
    MenuListItemComponent,
    FeaturesComponent
  ],
  imports: [
    BrowserModule,
    FlexLayoutModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    LoggerModule.forRoot({
      level: NgxLoggerLevel.INFO,
      disableConsoleLogging: false
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
