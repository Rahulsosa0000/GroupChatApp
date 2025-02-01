
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Client, IMessage } from '@stomp/stompjs';  // Import Client and IMessage correctly
import SockJS from 'sockjs-client';

export interface ChatMessage {
  sender: string;
  content: string;
  type: 'JOIN' | 'CHAT' | 'LEAVE'; // Message types
}

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  private stompClient: Client | null = null; // Use Client instead of Stomp.Client
  private messagesSubject = new BehaviorSubject<ChatMessage[]>([]);
  messages$ = this.messagesSubject.asObservable();

  connect(username: string) {
    const socket = new SockJS('http://localhost:8080/chat');
    this.stompClient = new Client({
      brokerURL: 'ws://localhost:8080/chat',
      connectHeaders: {},
      debug: (str) => console.log(str),
      reconnectDelay: 5000,
      onConnect: () => {
        console.log('Connected to WebSocket');
        this.stompClient?.subscribe('/topic/public', (message: IMessage) => {
          this.handleMessage(JSON.parse(message.body));
        });
        this.sendMessage({ sender: username, type: 'JOIN', content: '' });
      },
      onWebSocketError: (error) => {
        console.error('WebSocket error:', error);
      },
    });

    this.stompClient.activate(); // Use activate() to connect to the WebSocket
  }

  disconnect() {
    if (this.stompClient) {
      this.stompClient.deactivate(); // Use deactivate() to disconnect
      console.log('Disconnected from WebSocket');
    }
  }

  sendMessage(chatMessage: ChatMessage) {
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.publish({
        destination: '/app/chat.sendMessage',
        body: JSON.stringify(chatMessage),
      });
    }
  }

  private handleMessage(message: ChatMessage) {
    const currentMessages = this.messagesSubject.getValue();
    this.messagesSubject.next([...currentMessages, message]);
  }
}
