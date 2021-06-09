import { AdressDTO } from "./AdressDTO";

export interface UpdateDoctorRequestDTO {
    firstName: string,
    lastName: string,
    address?: AdressDTO,
    presentation?: string,
    speciality?: string,
    meanOfPayment?: string[],
    diplomas?: {
        name?: string,
        university?: string
    }[],
    prices: {
        description: string,
        number: number
    }[]
}