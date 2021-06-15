import { AdressDTO } from "./AdressDTO";

export interface DoctorDTO {
    id: string,
    firstName: string,
    lastName: string,
    email: string,
    tel: string,
    address: AdressDTO,
    speciality: string,
    presentation: string
}