import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { UpdatePasswordRequestDTO } from 'src/app/dto/UpdatePasswordRequestDTO';
import { MessageService } from 'src/app/services/Message.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.css']
})
export class UpdatePasswordComponent implements OnInit {
  passwordFormControl: FormControl;
  newPasswordFormControl: FormControl;

  constructor(public dialogRef: MatDialogRef<UpdatePasswordComponent>,
    private readonly userService: UserService,
    private readonly messageService: MessageService) {
      this.passwordFormControl = new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(16)]);
      this.newPasswordFormControl = new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(16)]);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
  }

  public updatePassword(): void {
    const input: UpdatePasswordRequestDTO = {
      password: this.passwordFormControl.value,
      newPassword: this.newPasswordFormControl.value
    }
    this.userService.updatePassword(input, this.userService.getUserId(), this.passwordFormControl.value)
        .subscribe(
          () => {
            this.dialogRef.close();
            this.messageService.showMessage("Mot de passe modifié", 0);
          }
        );
  }

}
