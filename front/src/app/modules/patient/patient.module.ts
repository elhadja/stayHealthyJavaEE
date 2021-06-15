import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientComponent } from './components/patient/patient.component';
import { RouterModule, Routes } from '@angular/router';
import { MenuComponent } from './components/menu/menu.component';
import { SearchComponent } from './components/search/search.component';
import { UpdatePatientDialogComponent } from './components/update-patient-dialog/update-patient-dialog.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ProfilComponent } from './components/profil/profil.component';
import { SharedModule } from '../shared/shared.module';
import {MatInputModule} from '@angular/material/input'; 
import {MatAutocompleteModule} from '@angular/material/autocomplete'; 
import {MatFormFieldModule} from '@angular/material/form-field';
import { CreateAppointmentDialogComponent } from './components/create-appointment-dialog/create-appointment-dialog.component'; 

const routes: Routes = [
  {path: '', component: PatientComponent},
  {path: 'search', component: SearchComponent},
  {path: 'profile', component: ProfilComponent},
  {path: 'appointment', component: SearchComponent}, // TODO redirect user to appointment component
]

@NgModule({
  declarations: [PatientComponent, MenuComponent, SearchComponent, UpdatePatientDialogComponent, ProfilComponent, CreateAppointmentDialogComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    SharedModule,
    RouterModule.forChild(routes),
  ],
})
export class PatientModule { }
