import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DoctorComponent } from './components/doctor/doctor.component';
import { RouterModule, Routes } from '@angular/router';
import { UpdateDoctorProfileComponent } from './components/update-doctor-profile/update-doctor-profile.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { DoctorMenuComponent } from './components/doctor-menu/doctor-menu.component';
import { ProfileComponentComponent } from './components/profile-component/profile-component.component';

const routes: Routes = [
  {path: '', component: DoctorComponent},
  {path: 'profile', component: ProfileComponentComponent}
]

@NgModule({
  declarations: [DoctorComponent, UpdateDoctorProfileComponent, DoctorMenuComponent, ProfileComponentComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SharedModule,
    RouterModule.forChild(routes)
  ],
})
export class DoctorModule { }
