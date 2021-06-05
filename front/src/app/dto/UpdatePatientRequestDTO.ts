import { AdressDTO } from "./AdressDTO";

export interface UpdatePatientRequestDTO {
    firstName: string,
    lastName: string,
    address: AdressDTO
}