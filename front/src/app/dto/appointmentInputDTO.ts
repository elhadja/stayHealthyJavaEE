import { doctorInfosDTO } from "./doctorInfosDTO";
import { SlotDTO } from "./slot";
import { UserDTO } from "./userDTO";

export interface AppointmentInputDTO {
    id: number,
    raison: string,
    slot: SlotDTO,
    user: UserDTO
    doctorInfos: doctorInfosDTO
}