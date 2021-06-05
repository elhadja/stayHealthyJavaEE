import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-update-patient-dialog',
  templateUrl: './update-patient-dialog.component.html',
  styleUrls: ['./update-patient-dialog.component.css']
})
export class UpdatePatientDialogComponent implements OnInit {
  patientForm: FormGroup;
  constructor(private dialogRef: MatDialogRef<UpdatePatientDialogComponent>,
    private readonly fb: FormBuilder) {
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

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  updatePatient(): void {

  }

  get firstName() {return this.patientForm.get("firstName")}
  get lastName() {return this.patientForm.get("lastName")}
}
