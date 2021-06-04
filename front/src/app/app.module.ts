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
import { GenericErrorComponent } from './components/generic-error/generic-error.component';
import { AccountComponent } from './components/account/account.component';
import { MessageComponent } from './components/message/message.component';
import { MessageService } from './services/Message.service';
import { UpdatePasswordComponent } from './components/update-password/update-password.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'account', component: AccountComponent},
  {path: 'patient', canActivate: [AuthValidatorService], loadChildren: () => import('./modules/patient/patient.module').then(m => m.PatientModule)},
  {path: 'doctor', canActivate: [AuthValidatorService], loadChildren: () => import('./modules/doctor/doctor.module').then(m => m.DoctorModule)},
]

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    LoginComponent,
    GenericErrorComponent,
    AccountComponent,
    MessageComponent,
    UpdatePasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatDialogModule,
    RouterModule.forRoot(routes)
  ],
  providers: [UserService, AuthValidatorService, MessageService, MatDialog],
  bootstrap: [AppComponent]
})
export class AppModule { }
