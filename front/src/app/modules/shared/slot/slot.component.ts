import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { SlotDTO } from 'src/app/dto/slot';
import { SlotResponseDTO } from 'src/app/dto/SlotResponseDTO';
import { PatientService } from 'src/app/services/patient.service';
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


  constructor(private readonly matDialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  onClick(): void {
   this.matDialog.open(CreateAppointmentDialogComponent, {
     data: this.slot
   });
  }
}
