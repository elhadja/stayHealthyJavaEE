import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { SlotResponseDTO } from "../dto/SlotResponseDTO";
import { UpdatePatientRequestDTO } from "../dto/UpdatePatientRequestDTO";
import { API } from "./api";
import { UserService } from "./user.service";

@Injectable()
export class PatientService {
    private readonly id: number;
    private readonly baseUri: string;
    private readonly appointmentUri: string;
    constructor(private api: API, private userService: UserService) {
        this.baseUri = "/patients";
        this.appointmentUri = "/appointments";
        this.id = userService.getUserId();
    }

    update(input: UpdatePatientRequestDTO): Observable<any> {
        return this.api.put(this.baseUri + "/" + this.id, input);
    }

    getDoctorsByCriteria(name: string, speciality: string, city: string): Observable<any> {
        return this.api.get("/doctors?name=" + name + "&speciality=" + speciality + "&city=" + city);
    }

    takeAppointment(input: SlotResponseDTO): Observable<any> {
        return this.api.post(this.baseUri + "/" + this.userService.getUserId() + this.appointmentUri, input);
    }

    cancelAppointment(appointmentId: number): Observable<any> {
        return this.api.delete(this.baseUri + "/" + this.userService.getUserId() + this.appointmentUri + "/" + appointmentId);
    }
}