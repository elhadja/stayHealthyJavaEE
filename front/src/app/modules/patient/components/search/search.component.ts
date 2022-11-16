import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { DoctorDTO } from 'src/app/dto/doctorDTO';
import { PatientService } from 'src/app/services/patient.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  searchForm: FormGroup;
  doctors: DoctorDTO[];
  doctorsOptions: DoctorDTO[];
  doctorsOptions2: DoctorDTO[];
  doctorsOptions3: DoctorDTO[];
  doctorsToShow: DoctorDTO[];
  doctorsByPage: number;
  currentPage: number;
  numberOfDoctorsByPage: number;
  numberOfPages: number;
  pages: number[];

  constructor(private readonly fb: FormBuilder,
               private readonly patientService: PatientService,
               private readonly router: Router) {
    this.searchForm = fb.group({
      name: fb.control(''),
      city: fb.control(''),
      speciality: fb.control('')
    });
    this.doctors = [];
    this.doctorsOptions = [];
    this.doctorsOptions2 = [];
    this.doctorsOptions3 = [];
    this.doctorsToShow = [];
    this.pages = [];

    this.doctorsByPage = 2;
    this.currentPage = 1;
    this.numberOfDoctorsByPage = 2;
    this.numberOfPages = 0;
  }

  ngOnInit(): void {
    this.searchForm.get("name")?.valueChanges.subscribe(() => {
      this.doctorsOptions = [];
      if (this.searchForm.get("name")?.value !== "") {
        this.patientService.getDoctorsByCriteria(this.searchForm.get("name")?.value,
                                              this.searchForm.get("speciality")?.value,
                                              this.searchForm.get("city")?.value)
          .subscribe((doctors: DoctorDTO[]) => {
            this.doctorsOptions = doctors;
          });
      }
     
    });
    this.searchForm.get("speciality")?.valueChanges.subscribe(() => {
      this.doctorsOptions2 = [];
      if(this.searchForm.get("speciality")?.value !== "") {
        this.patientService.getDoctorsByCriteria(this.searchForm.get("name")?.value,
                                                this.searchForm.get("speciality")?.value,
                                                this.searchForm.get("city")?.value)
            .subscribe((doctors: DoctorDTO[]) => {
              this.doctorsOptions2 = doctors;
            });
      }
    });
    this.searchForm.get("city")?.valueChanges.subscribe(() => {

      this.doctorsOptions3 = [];
      if(this.searchForm.get("city")?.value !== "") {
        this.patientService.getDoctorsByCriteria(this.searchForm.get("name")?.value,
                                                this.searchForm.get("speciality")?.value,
                                                this.searchForm.get("city")?.value)
            .subscribe((doctors: DoctorDTO[]) => {
              this.doctorsOptions3 = doctors;
            });
      }
    });
 
  }

  onSearch(): void {
    this.patientService.getDoctorsByCriteria(this.searchForm.get("name")?.value,
                                            this.searchForm.get("speciality")?.value,
                                            this.searchForm.get("city")?.value)
        .subscribe((doctors: DoctorDTO[]) => {
          this.doctors = doctors;
          this.numberOfPages = doctors.length / this.numberOfDoctorsByPage;
          this.pages = [];
          for (let i=0; i<this.numberOfPages; i++) {
            this.pages.push(i);
          }
          this.onPage(0);
        });
  }

  onPage(page: number): void {
    this.currentPage = page;
    this.doctorsToShow.splice(0);
    for (let i=(page*this.numberOfDoctorsByPage); (i<(this.numberOfDoctorsByPage*page)+this.numberOfDoctorsByPage); i++) {
      if (i >= this.doctors.length) {
        break;
      }
      this.doctorsToShow.push(this.doctors[i]);
    }
  }

  onNextPage(): void {
    if (this.currentPage < this.numberOfPages-1) {
      this. currentPage += 1;
      this.onPage(this.currentPage);
    }
  }

  onPreviousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage -= 1;
      this.onPage(this.currentPage);
    }
  }

  onDoctorSelected(doctorId: number): void {
    this.router.navigateByUrl('/doctor/' + doctorId);
  }
}
