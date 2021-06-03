import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { MessageService } from 'src/app/services/Message.service';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit, OnDestroy {
  message: string | undefined;
  messageType: number;
  messageSubscription: Subscription;

  constructor(public readonly messageService: MessageService) {
    this.message = undefined;
    this.messageType = 0;
    this.messageSubscription = this.messageService.messageSubject.subscribe((message => {
      this.message = message;
      this.messageType = messageService.getCurrentMessageType();
      setTimeout(() => {
        this.message = undefined;
      }, 5000);
    }))
   }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.messageSubscription.unsubscribe();
  }

  public hide(): void {
    this.message = undefined;
    console.log("hided");
  }

}
