import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { UpdatePatientRequestDTO } from 'src/app/dto/UpdatePatientRequestDTO';
import { MessageService } from 'src/app/services/Message.service';
import { PatientService } from 'src/app/services/patient.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-update-patient-dialog',
  templateUrl: './update-patient-dialog.component.html',
  styleUrls: ['./update-patient-dialog.component.css']
})
export class UpdatePatientDialogComponent implements OnInit {
  patientForm: FormGroup;
  constructor(private dialogRef: MatDialogRef<UpdatePatientDialogComponent>,
    private readonly fb: FormBuilder,
    private readonly patientService: PatientService,
    private readonly messageService: MessageService,
    private readonly userService: UserService) {
      this.patientForm = fb.group({
        firstName: fb.control('', [Validators.required]),
        lastName: fb.control('', [Validators.required]),
        address: fb.group({
          road: fb.control(''),
          city: fb.control(''),
          postalCode: fb.control('')
        })
      });
  }

  ngOnInit(): void {
    this.userService.getById().subscribe((user) => {
      this.patientForm.get("firstName")?.setValue(user.firstName);
      this.patientForm.get("lastName")?.setValue(user.lastName);
      this.patientForm.get("address")?.get("road")?.setValue(user.address.road);
      this.patientForm.get("address")?.get("city")?.setValue(user.address.city);
      this.patientForm.get("address")?.get("postalCode")?.setValue(user.address.postalCode);
    })
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  updatePatient(): void {
    let input: UpdatePatientRequestDTO;
    input = {
      firstName: this.patientForm.get("firstName")?.value,
      lastName: this.patientForm.get("lastName")?.value,
      address: this.isAddressSetted() ? this.patientForm.get("address")?.value : undefined
    };

    this.patientService.update(input).subscribe((res) => {
      this.messageService.showMessage("profile modifié", 0);
    });
  }

  private isAddressSetted(): boolean {
    return this.patientForm.get("address")?.get("road")?.value != ""
          || this.patientForm.get("address")?.get("postalCode")?.value != ""
          || this.patientForm.get("address")?.get("city")?.value != "";
  }


  get firstName() {return this.patientForm.get("firstName")}
  get lastName() {return this.patientForm.get("lastName")}
}
