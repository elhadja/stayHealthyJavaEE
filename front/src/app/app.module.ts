import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'

import {MatDialog, MatDialogModule} from '@angular/material/dialog'; 

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignupComponent } from './components/signup/signup.component';
import { UserService } from './services/user.service';
import { LoginComponent } from './components/login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { AuthValidatorService } from './services/auth-validator.service';
import { MessageComponent } from './components/message/message.component';
import { MessageService } from './services/Message.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { API } from './services/api';
import { SharedModule } from './modules/shared/shared.module';
import { PatientService } from './services/patient.service';
import { DoctorService } from './services/doctor.service';

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'patient', canActivate: [AuthValidatorService], loadChildren: () => import('./modules/patient/patient.module').then(m => m.PatientModule)},
  {path: 'doctor', canActivate: [AuthValidatorService], loadChildren: () => import('./modules/doctor/doctor.module').then(m => m.DoctorModule)},
]

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    LoginComponent,
    MessageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatDialogModule,
    SharedModule,
    RouterModule.forRoot(routes)
  ],
  providers: [UserService, AuthValidatorService, MessageService, MatDialog, API, PatientService, DoctorService],
  bootstrap: [AppComponent]
})
export class AppModule { }
