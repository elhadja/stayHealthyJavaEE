import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { UpdateDoctorRequestDTO } from "../dto/UpdateDoctorRequestDTO";
import { API } from "./api";
import { UserService } from "./user.service";

@Injectable()
export class DoctorService {
    readonly baseUri: string;
    constructor(private api: API, private readonly userService: UserService) {
        this.baseUri = "/doctors";
    }

    update(input: UpdateDoctorRequestDTO): Observable<any> {
        return this.api.put(this.baseUri + "/" + this.userService.getUserId(), input);
    }

    getById(): Observable<any> {
        return this.api.get(this.baseUri + "/" + this.userService.getUserId());
    }
}