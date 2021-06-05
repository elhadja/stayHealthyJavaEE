import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { UpdatePatientRequestDTO } from "../dto/UpdatePatientRequestDTO";
import { API } from "./api";
import { UserService } from "./user.service";

@Injectable()
export class PatientService {
    private readonly id: number;
    private readonly baseUri: string;
    constructor(private api: API, private userService: UserService) {
        this.baseUri = "/patients";
        this.id = userService.getUserId();
    }

    update(input: UpdatePatientRequestDTO): Observable<any> {
        return this.api.put(this.baseUri + "/" + this.id, input);
    }
}