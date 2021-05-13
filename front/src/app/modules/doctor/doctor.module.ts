import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DoctorComponent } from './components/doctor/doctor.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {path: '', component: DoctorComponent}
]

@NgModule({
  declarations: [DoctorComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ]
})
export class DoctorModule { }
