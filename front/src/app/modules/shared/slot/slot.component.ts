import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { SlotDTO } from 'src/app/dto/slot';
import { SlotResponseDTO } from 'src/app/dto/SlotResponseDTO';
import { DoctorService } from 'src/app/services/doctor.service';
import { PatientService } from 'src/app/services/patient.service';
import { UserService } from 'src/app/services/user.service';
import { CreateAppointmentDialogComponent } from '../../patient/components/create-appointment-dialog/create-appointment-dialog.component';
import { UpdatePasswordComponent } from '../update-password/update-password.component';

@Component({
  selector: 'app-slot',
  templateUrl: './slot.component.html',
  styleUrls: ['./slot.component.css']
})
export class SlotComponent implements OnInit {
  @Input()
  slot: SlotDTO | undefined;

  constructor(private readonly matDialog: MatDialog,
    private readonly userService: UserService,
    private readonly doctorService: DoctorService) {
  }

  ngOnInit(): void {
  }

  onClick(): void {
    if(this.userService.getUserType() === 0) {
      this.matDialog.open(CreateAppointmentDialogComponent, {
        data: this.slot
      });
    } else {
      if(this.slot?.id !== undefined) {
        this.doctorService.emitUpdateSlot(this.slot);
      }
    }
  }
}
