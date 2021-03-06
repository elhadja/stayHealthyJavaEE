import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DoctorComponent } from './components/doctor/doctor.component';
import { RouterModule, Routes } from '@angular/router';
import { UpdateDoctorProfileComponent } from './components/update-doctor-profile/update-doctor-profile.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { ProfileComponentComponent } from './components/profile-component/profile-component.component';
import { AppointmentComponent } from './components/appointment/appointment.component';
import { DoctorPresentationComponent } from './components/doctor-presentation/doctor-presentation.component';

const routes: Routes = [
  {path: '', component: DoctorComponent},
  {path: ':id', component: DoctorPresentationComponent},
  {path: 'profile', component: ProfileComponentComponent},
]

@NgModule({
  declarations: [DoctorComponent, UpdateDoctorProfileComponent, ProfileComponentComponent, AppointmentComponent, DoctorPresentationComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SharedModule,
    RouterModule.forChild(routes)
  ],
})
export class DoctorModule { }
