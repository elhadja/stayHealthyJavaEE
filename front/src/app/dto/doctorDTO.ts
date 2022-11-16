import { AdressDTO } from "./AdressDTO";

export interface DoctorDTO {
    id: number,
    firstName: string,
    lastName: string,
    email: string,
    tel: string,
    address: AdressDTO,
    speciality: string,
    presentation: string,
    meanOfPayment: string[],
    diplomas: string[],
    prices: string[]
}