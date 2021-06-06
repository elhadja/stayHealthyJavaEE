import { HttpHeaders } from '@angular/common/http';
import { tokenize } from '@angular/compiler/src/ml_parser/lexer';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { LoginDTO } from 'src/app/dto/loginDTO';
import { updateCredentialsOutputDTO } from 'src/app/dto/UpdateCredentialsOutputDTO';
import { UpdatePatientDialogComponent } from 'src/app/modules/patient/components/update-patient-dialog/update-patient-dialog.component';
import { MessageService } from 'src/app/services/Message.service';
import { UserService } from 'src/app/services/user.service';
import { UpdateDoctorProfileComponent } from '../../doctor/components/update-doctor-profile/update-doctor-profile.component';
import { UpdatePasswordComponent } from '../update-password/update-password.component';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  accountForm: FormGroup;
  error: string;
  username: string;
  userType: number;

  constructor(private readonly fb: FormBuilder,
     private readonly userService: UserService,
     private readonly messageService: MessageService,
     private readonly matDialog: MatDialog) { 
    this.accountForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      tel: [''] // TODO check phone number validity using regex
    });

    this.error = '';
    this.username = '';
    this.userType = 0;
  }

  ngOnInit(): void {
    this.userService.getById(this.userService.getUserId()).subscribe((user) => {
      this.accountForm.get('email')?.setValue(user.email);
      this.accountForm.get('tel')?.setValue(user.tel);
      this.username = user.firstName + " " + user.lastName;
      this.userType = user.userType;
    })
  }

  onSubmit(): void {
    const input: updateCredentialsOutputDTO = {
      email: this.accountForm.get('email')?.value,
      tel: this.accountForm.get('tel')?.value
    };
    this.userService.updateCredentials(input, this.userService.getUserId()).subscribe(
      () => { 
        this.messageService.showMessage("informations modifées", 0)
        // TODO set token
      });
  }

  showDialog(): void {
    this.matDialog.open(UpdatePasswordComponent);
  }

  updateProfile(): void {
    if (this.userType == 0) {
      this.matDialog.open(UpdatePatientDialogComponent);
    } else {
      this.matDialog.open(UpdateDoctorProfileComponent);
    }
  }

  deleteAccount(): void {
    this.userService.delete().subscribe(() => {
      this.userService.logout();
    });
  }

  get email() {return this.accountForm.get('email')}
  get tel() {return this.accountForm.get('tel')}
}
