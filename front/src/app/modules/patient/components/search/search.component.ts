import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
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

  constructor(private readonly fb: FormBuilder, private readonly patientService: PatientService) {
    this.searchForm = fb.group({
      name: fb.control(''),
      city: fb.control(''),
      speciality: fb.control('')
    });
    this.doctors = [];
  }

  ngOnInit(): void {
  }

  onSearch(): void {
    this.patientService.getDoctorsByCriteria(this.searchForm.get("name")?.value,
                                            this.searchForm.get("speciality")?.value,
                                            this.searchForm.get("city")?.value)
        .subscribe((doctors: DoctorDTO[]) => {
          this.doctors = doctors;
        });
  }
}
