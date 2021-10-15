import { Component, Input } from '@angular/core';
import { Params, ActivatedRoute } from '@angular/router';
import * as CryptoJS from 'crypto-js';
import { NGXLogger } from 'ngx-logger';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'web';

  @Input() conv_id!: string;
  @Input() userId!: string;

  private currentConversationId = '';
  private currentConversationTitle = '';
  private currentSessionId = '';
  private encryptedData = '';
  private projectId = '';
  private projectName = '';

  constructor(private route: ActivatedRoute, private logger: NGXLogger) { }

  ngOnInit() {
    this.currentConversationTitle = '';
    this.currentConversationId = '';
    this.currentSessionId = '';
    this.route.queryParams.forEach((params: Params) => {
      this.encryptedData = params['data'];
      if (this.encryptedData != undefined) {
        var temp = this.encryptedData.replace(/ /g, '+');
        this.encryptedData = temp;
        var decr = CryptoJS.AES.decrypt(this.encryptedData.trim(), "Cefriel").toString(CryptoJS.enc.Utf8);
        var decrArray = decr.split("*&*");
        if (decrArray.length == 4) {
          this.userId = decrArray[0];
          this.projectId = decrArray[1];
          this.projectName = decrArray[2].replace("%26", "&");
          this.conv_id = decrArray[3];
          this.logger.info('user=' + this.userId + ' projectId=' + this.projectId + ' projectName=' + this.projectName + ' conv_id=' + this.conv_id);
        }
      }
    });
  }
}