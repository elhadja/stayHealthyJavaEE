import { HttpHeaders } from '@angular/common/http';
import { tokenize } from '@angular/compiler/src/ml_parser/lexer';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { LoginDTO } from 'src/app/dto/loginDTO';
import { updateCredentialsOutputDTO } from 'src/app/dto/UpdateCredentialsOutputDTO';
import { MessageService } from 'src/app/services/Message.service';
import { UserService } from 'src/app/services/user.service';
import { UpdatePasswordComponent } from '../update-password/update-password.component';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  accountForm: FormGroup;
  error: string;

  constructor(private readonly fb: FormBuilder,
     private readonly userService: UserService,
     private readonly messageService: MessageService,
     private readonly matDialog: MatDialog) { 
    this.accountForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      tel: [''] // TODO check phone number validity using regex
    });

    this.error = '';
  }

  ngOnInit(): void {
    this.userService.getById(this.userService.getUserId()).subscribe((user) => {
      this.accountForm.get('email')?.setValue(user.email);
      this.accountForm.get('tel')?.setValue(user.tel);
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

  get email() {return this.accountForm.get('email')}
  get tel() {return this.accountForm.get('tel')}
}
