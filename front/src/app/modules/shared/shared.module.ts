import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GenericErrorComponent } from './generic-error/generic-error.component';
import { AccountComponent } from './account/account.component';
import { UpdatePasswordComponent } from './update-password/update-password.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MenuDropdownUserComponent } from './menu-dropdown-user/menu-dropdown-user.component';

@NgModule({
  declarations: [GenericErrorComponent, AccountComponent, UpdatePasswordComponent, MenuDropdownUserComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  exports: [GenericErrorComponent, AccountComponent, MenuDropdownUserComponent]
})
export class SharedModule { }
