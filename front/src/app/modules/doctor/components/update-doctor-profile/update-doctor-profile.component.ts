import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { last } from 'rxjs/operators';
import { UpdateDoctorRequestDTO } from 'src/app/dto/UpdateDoctorRequestDTO';
import { DoctorService } from 'src/app/services/doctor.service';
import { MessageService } from 'src/app/services/Message.service';

@Component({
  selector: 'app-update-doctor-profile',
  templateUrl: './update-doctor-profile.component.html',
  styleUrls: ['./update-doctor-profile.component.css']
})
export class UpdateDoctorProfileComponent implements OnInit {
  doctorForm: FormGroup;
  constructor(private readonly fb: FormBuilder,
    private dialogRef: MatDialogRef<UpdateDoctorProfileComponent>,
    private readonly doctorService: DoctorService,
    private readonly messageService: MessageService) {
      this.doctorForm = fb.group({
        firstName: fb.control('', [Validators.required]),
        lastName: fb.control('', [Validators.required]),
        address: fb.group({
          road: fb.control(''),
          city: fb.control(''),
          postalCode: fb.control('')
        }),
        presentation: fb.control(''),
        speciality: fb.control(''),
        meanOfPayment: fb.control(''),
        diplomas: fb.array([
          fb.group({
            title: fb.control(''),
            university: fb.control('')
          })
       ]),
        prices: fb.array([
          fb.group({
            description: fb.control(''),
            number: fb.control('')
          })
       ])

      });
   }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  updateDoctor(): void {
    let input: UpdateDoctorRequestDTO;
    input = {
      firstName: this.doctorForm.get("firstName")?.value,
      lastName: this.doctorForm.get("lastName")?.value,
      presentation: this.doctorForm.get("presentation")?.value,
      speciality: this.doctorForm.get("speciality")?.value
    };
    this.doctorService.update(input).subscribe(() => {
      this.messageService.showMessage("profile modifié !", 0);
      this.onNoClick();
    })
  }

  addDiplomas(): void {
    this.diplomas.push(this.fb.group({
      title: this.fb.control(''),
      university: this.fb.control('')
    }))
  }

  addPrice(): void {
    this.prices.push(this.fb.group({
      description: this.fb.control(''),
      number: this.fb.control('')
    }))
  }


  help(x: any): FormGroup {
    return x as FormGroup;
  }

  get firstName() {return this.doctorForm.get("firstName")}
  get lastName() {return this.doctorForm.get("lastName")}
  get diplomas() {return this.doctorForm.get("diplomas") as FormArray}
  get prices() {return this.doctorForm.get("prices") as FormArray}
}
