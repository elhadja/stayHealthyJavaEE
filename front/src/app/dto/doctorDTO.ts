import { AdressDTO } from "./AdressDTO";

export interface DoctorDTO {
    firstName: string,
    lastName: string,
    email: string,
    tel: string,
    address: AdressDTO
}