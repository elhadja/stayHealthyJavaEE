import { Injectable } from "@angular/core";
import { Subject } from "rxjs";

@Injectable()
export class MessageService {
    public messageSubject: Subject<string>;
    private messageType: number;

    constructor() {
        this.messageSubject = new Subject<string>();
        this.messageType = -1;
    }
    
    public showMessage(message: string, type: number): void {
        this.messageType = type;
        this.messageSubject.next(message);
    }

    public getCurrentMessageType(): number {
        return this.messageType;
    }
}