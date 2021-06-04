import { AdressDTO } from "./AdressDTO";

export interface signupDTO {
    firstName?: string | undefined;
    lastName?: string | undefined;
    email?: string | undefined;
    tel?: string | undefined;
    password?: string | undefined;
    address?: AdressDTO;
    userType?: number;
}