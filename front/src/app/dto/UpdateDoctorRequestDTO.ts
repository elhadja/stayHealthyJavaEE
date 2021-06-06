import { AdressDTO } from "./AdressDTO";

export interface UpdateDoctorRequestDTO {
    firstName: string,
    lastName: string,
    address?: AdressDTO,
    presentation?: string,
    speciality?: string,
    diplomas?: {
        title?: string,
        university?: string
    }[]
}