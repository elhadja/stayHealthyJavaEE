import { HttpHeaders } from '@angular/common/http';
import { tokenize } from '@angular/compiler/src/ml_parser/lexer';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginDTO } from 'src/app/api/dto/loginDTO';
import { updateCredentialsOutputDTO } from 'src/app/api/dto/UpdateCredentialsOutputDTO';
import { UserApiService } from 'src/app/api/user-api.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  accountForm: FormGroup;
  error: string;

  constructor(private readonly fb: FormBuilder, private readonly useApiService: UserApiService) { 
    this.accountForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      tel: ['', [Validators.required]]
    });

    this.error = '';
  }

  ngOnInit(): void {
  }

  // TODO préremplir le formulaire une fois que get users sera implémenté côté back

  onSubmit(): void {
    const input: updateCredentialsOutputDTO = {
      email: this.accountForm.get('email')?.value,
      tel: this.accountForm.get('tel')?.value
    };
    this.useApiService.updateCredentials(input, 185).subscribe(
      (res) => {},
      (error) => {
        this.error = error;
      });
  }

  get email() {return this.accountForm.get('email')}
  get tel() {return this.accountForm.get('tel')}
}
