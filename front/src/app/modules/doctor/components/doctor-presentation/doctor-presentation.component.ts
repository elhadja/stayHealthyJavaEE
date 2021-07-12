import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DoctorDTO } from 'src/app/dto/doctorDTO';
import { DoctorService } from 'src/app/services/doctor.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-doctor-presentation',
  templateUrl: './doctor-presentation.component.html',
  styleUrls: ['./doctor-presentation.component.css']
})
export class DoctorPresentationComponent implements OnInit {
  doctorId: number;
  doctor: DoctorDTO | undefined;

  constructor(private route: ActivatedRoute,
              private readonly doctorService: DoctorService,
              private readonly userService: UserService) { 
    this.doctorId = 0;
    this.route.params.subscribe(param => this.doctorId = param.id);
  }

  ngOnInit(): void {
    this.doctorService.getById(this.doctorId).subscribe((doctor) => {
      this.doctor = doctor;
    })
  }

}
