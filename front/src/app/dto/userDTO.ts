import { AdressDTO } from "./AdressDTO";

export interface UserDTO {
    id: number,
    firstName: string,
    lastName: string,
    email: string,
    tel: string,
    address: AdressDTO,
    speciality: string // just for doctor
}