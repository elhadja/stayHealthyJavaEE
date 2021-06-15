import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { SlotDTO } from "../dto/slot";
import { UpdateDoctorRequestDTO } from "../dto/UpdateDoctorRequestDTO";
import { API } from "./api";
import { UserService } from "./user.service";

@Injectable()
export class DoctorService {
    readonly baseUri: string;
    readonly slotUri: string;

    constructor(private api: API, private readonly userService: UserService) {
        this.baseUri = "/doctors";
        this.slotUri = "/slots";
    }

    update(input: UpdateDoctorRequestDTO): Observable<any> {
        return this.api.put(this.baseUri + "/" + this.userService.getUserId(), input);
    }

    getById(): Observable<any> {
        return this.api.get(this.baseUri + "/" + this.userService.getUserId());
    }

    public getSlotsBetween(doctorId: string, startDate: string, endDate: string): Observable<any> {
        return this.api.get(this.baseUri + "/" + doctorId + this.slotUri
                           + "?date=" + startDate);
    }

    public addSlot(input: SlotDTO): Observable<any> {
        return this.api.post(this.baseUri + "/" + this.userService.getUserId() + "/slots", input);
    }
}