import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
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
  _meanOfPayment: {name: string, value: string}[];
  specialityOptions: {name: string, value: string}[];
  constructor(private readonly fb: FormBuilder,
    private dialogRef: MatDialogRef<UpdateDoctorProfileComponent>,
    private readonly doctorService: DoctorService,
    private readonly messageService: MessageService) {
      this._meanOfPayment = [
        {name: 'cheque', value: 'cheque'},
        {name: 'espèces', value: 'espèces'},
        {name: 'carte bancaire', value: 'carte bancaire'},
      ]
      this.specialityOptions = [
        {name: 'chirurgien dentiste', value: 'chirurgien dentiste'},
        {name: 'medecin generaliste', value: 'medecin generaliste'},
        {name: 'psychiatre', value: 'psychiatre'},
      ]
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
        meanOfPayment: fb.control([]),
        diplomas: fb.array([
          fb.group({
            name: fb.control('', [Validators.required]),
            university: fb.control('', [Validators.required])
          })
       ]),
        prices: fb.array([
          fb.group({
            description: fb.control('', [Validators.required]),
            number: fb.control('', [Validators.required])
          })
       ])

      });
   }

  ngOnInit(): void {
    this.doctorService.getById().subscribe((doctor: UpdateDoctorRequestDTO) => {
      this.firstName?.setValue(doctor.firstName);
      this.lastName?.setValue(doctor.lastName);
      this.presentation?.setValue(doctor.presentation);
      this.speciality?.setValue(doctor.speciality);
      this.address?.get('road')?.setValue(doctor.address?.road)
      this.address?.get('city')?.setValue(doctor.address?.city)
      this.address?.get('postalCode')?.setValue(doctor.address?.postalCode)
      this.meanOfPayment?.setValue(doctor.meanOfPayment)
      if (doctor.diplomas !== undefined) {
        this.diplomas.reset();
        let i=0;
        for (let d of doctor.diplomas) {
          this.diplomas.insert(i, new FormGroup({
            name: new FormControl(d.name),
            university: new FormControl(d.university)
          }))
          i++;
        }
      }
      if (doctor.prices != undefined) {
        this.prices.reset();
        let i=0;
        for (const p of doctor.prices) {
          this.prices.insert(i, new FormGroup({
            description: new FormControl(p.description),
            number: new FormControl(p.number)
          }))
          i++;
        }
      }
    })
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
      speciality: this.doctorForm.get("speciality")?.value,
      address: this.isAddressSetted() ? this.doctorForm.get("address")?.value : undefined,
      diplomas: this.doctorForm.get("diplomas")?.value,
      prices: this.prices.value,
      meanOfPayment: this.meanOfPayment?.value
    };
    this.doctorService.update(input).subscribe(() => {
      this.messageService.showMessage("profile modifié !", 0);
      this.onNoClick();
    })
    console.log("==> " + this.meanOfPayment?.value)
  }

  addDiplomas(): void {
    this.diplomas.push(this.fb.group({
      name: this.fb.control(''),
      university: this.fb.control('')
    }))
  }

  removeDiplomas(i: number): void {
    this.diplomas.removeAt(i);
  }

  addPrice(): void {
    this.prices.push(this.fb.group({
      description: this.fb.control(''),
      number: this.fb.control('')
    }))
  }

  removePrice(i: number) {
    this.prices.removeAt(i);
  }


  help(x: any): FormGroup {
    return x as FormGroup;
  }

  private isAddressSetted(): boolean {
    return this.doctorForm.get("address")?.get("road")?.value != ""
          || this.doctorForm.get("address")?.get("postalCode")?.value != ""
          || this.doctorForm.get("address")?.get("city")?.value != "";
  }


  get firstName() {return this.doctorForm.get("firstName")}
  get lastName() {return this.doctorForm.get("lastName")}
  get presentation() { return this.doctorForm.get("presentation") }
  get speciality() { return this.doctorForm.get("speciality") }
  get diplomas() {return this.doctorForm.get("diplomas") as FormArray}
  get prices() {return this.doctorForm.get("prices") as FormArray}
  get address() {return this.doctorForm.get("address")}
  get meanOfPayment() {return this.doctorForm.get("meanOfPayment")}
}
