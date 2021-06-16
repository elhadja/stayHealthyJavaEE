import { Injectable } from "@angular/core";
import { Observable, Subject } from "rxjs";
import { SlotDTO } from "../dto/slot";
import { UpdateDoctorRequestDTO } from "../dto/UpdateDoctorRequestDTO";
import { API } from "./api";
import { UserService } from "./user.service";

@Injectable()
export class DoctorService {
    readonly baseUri: string;
    readonly slotUri: string;
    public updateSlotSubject: Subject<SlotDTO>;

    constructor(private api: API, private readonly userService: UserService) {
        this.baseUri = "/doctors";
        this.slotUri = "/slots";
        this.updateSlotSubject = new Subject<SlotDTO>();
    }

    update(input: UpdateDoctorRequestDTO): Observable<any> {
        return this.api.put(this.baseUri + "/" + this.userService.getUserId(), input);
    }

    getById(): Observable<any> {
        return this.api.get(this.baseUri + "/" + this.userService.getUserId());
    }

    public getSlotsBetween(doctorId: number, startDate: string, endDate: string): Observable<any> {
        return this.api.get(this.baseUri + "/" + doctorId + this.slotUri
                           + "?date=" + startDate);
    }

    public addSlot(input: SlotDTO): Observable<any> {
        return this.api.post(this.baseUri + "/" + this.userService.getUserId() + "/slots", input);
    }

    public deleteSlot(id: number): Observable<any> {
        return this.api.delete(this.baseUri + "/" + this.userService.getUserId() + this.slotUri + "/" + id);
    }

    public emitUpdateSlot(slotIdtoUpdate: SlotDTO) {
        this.updateSlotSubject.next(slotIdtoUpdate);
    }
}