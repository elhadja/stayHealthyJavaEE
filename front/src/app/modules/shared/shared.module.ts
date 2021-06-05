import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GenericErrorComponent } from './generic-error/generic-error.component';
import { AccountComponent } from './account/account.component';
import { UpdatePasswordComponent } from './update-password/update-password.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [GenericErrorComponent, AccountComponent, UpdatePasswordComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  exports: [GenericErrorComponent, AccountComponent]
})
export class SharedModule { }
