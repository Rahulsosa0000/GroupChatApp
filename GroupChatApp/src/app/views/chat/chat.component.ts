import { Component, OnInit, OnDestroy, signal } from '@angular/core';
import { NgIf, NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatService } from '../../ser/chat.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [NgIf, NgFor, FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css',
})
export class ChatComponent implements OnInit, OnDestroy {
  username = signal('');
  message = signal('');
  messages = signal<any[]>([]);
  isConnected = signal(false);

  constructor(private chatService: ChatService) {}

  ngOnInit() {
    this.chatService.messages$.subscribe((msgs) => {
      this.messages.set(msgs);
    });
  }

  connect() {
    if (this.username().trim()) {
      this.chatService.connect(this.username());
      this.isConnected.set(true);
    }
  }

  sendMessage() {
    if (this.message().trim()) {
      this.chatService.sendMessage({
        sender: this.username(),
        content: this.message(),
        type: 'CHAT',
      });
      this.message.set('');
    }
  }

  disconnect() {
    this.chatService.disconnect();
    this.isConnected.set(false);
  }

  ngOnDestroy() {
    this.disconnect();
  }
}
