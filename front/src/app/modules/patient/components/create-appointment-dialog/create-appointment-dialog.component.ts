import { Component, Inject, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SlotDTO } from 'src/app/dto/slot';
import { SlotResponseDTO } from 'src/app/dto/SlotResponseDTO';
import { MessageService } from 'src/app/services/Message.service';
import { PatientService } from 'src/app/services/patient.service';

@Component({
  selector: 'app-create-appointment-dialog',
  templateUrl: './create-appointment-dialog.component.html',
  styleUrls: ['./create-appointment-dialog.component.css']
})
export class CreateAppointmentDialogComponent implements OnInit {
  raisonControl: FormControl;

  constructor(private readonly patientService: PatientService,
    private readonly dialogRef: MatDialogRef<CreateAppointmentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SlotDTO,
    private messageService: MessageService) {
      this.raisonControl = new FormControl('');
     }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    let input: SlotResponseDTO;
    input = {
      slotId: this.data.id,
      raison: this.raisonControl.value
    }
    this.patientService.takeAppointment(input).subscribe(
      () => {
        this.messageService.showMessage("rendez-vous crée", 0);
        this.onNoClick();
      }
    );
  }

}
