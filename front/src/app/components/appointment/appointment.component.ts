import { Component, OnInit } from '@angular/core';
import { forkJoin } from 'rxjs';
import { AppointmentInputDTO } from 'src/app/dto/appointmentInputDTO';
import { UserDTO } from 'src/app/dto/userDTO';
import { UtilDate } from 'src/app/services/date.service';
import { MessageService } from 'src/app/services/Message.service';
import { PatientService } from 'src/app/services/patient.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent implements OnInit {
  previousAppointments: AppointmentInputDTO[];
  upcomingAppointments: AppointmentInputDTO[];
  currentAppointment: AppointmentInputDTO | undefined;
  currentUser: UserDTO | undefined;

  isPatient: boolean;

  constructor(private readonly userService: UserService,
              public utilDate: UtilDate,
              private readonly patientService: PatientService,
              private readonly messageService: MessageService) {
    this.previousAppointments = [];
    this.upcomingAppointments = [];
    this.isPatient = true;
  }

  ngOnInit(): void {
    forkJoin({
      currentUser: this.userService.getById(),
      previousAppointent: this.userService.getAppointmentsBefore(),
      upcomingAppointments: this.userService.getAppointmentAfter()
    }).subscribe((response) => {
      this.currentUser = response.currentUser
      this.previousAppointments = response.previousAppointent;
      this.upcomingAppointments = response.upcomingAppointments;
      if(this.upcomingAppointments.length > 0) {
        this.currentAppointment = this.upcomingAppointments[0];
      } else if (this.previousAppointments.length > 0) {
        this.currentAppointment = this.previousAppointments[0];
      }
    });
    this.isPatient = this.userService.getUserType() === 0;
  }

  getFormattedDate(date: string | undefined): string {
    if(!!date) {
      const token = date.split("-");
      return this.utilDate.getDay(date) + ' ' + token[2] + ' ' +  this.utilDate.getMonth(date);
    }
    return '';
  }

  onCancelAppointment(): void {
    this.patientService.cancelAppointment(this.currentAppointment?.id ?? 0).subscribe(() => {
      this.messageService.showMessage("Votre rendez-vous a été annulé", 0);
    })
  }

}
