import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GenericErrorComponent } from './generic-error/generic-error.component';
import { AccountComponent } from './account/account.component';
import { UpdatePasswordComponent } from './update-password/update-password.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MenuDropdownUserComponent } from './menu-dropdown-user/menu-dropdown-user.component';
import { SlotComponent } from './slot/slot.component';
import { CalendarComponent } from './calendar/calendar.component';
import {MatIconModule} from '@angular/material/icon';
import { MenuComponent } from './menu/menu.component'; 
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [GenericErrorComponent,
                 AccountComponent,
                 UpdatePasswordComponent,
                 MenuDropdownUserComponent, 
                 SlotComponent, 
                 CalendarComponent, 
                 MenuComponent
                ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatIconModule,
    RouterModule
  ],
  exports: [GenericErrorComponent, 
            AccountComponent, 
            MenuDropdownUserComponent, 
            CalendarComponent, 
            SlotComponent,
            MenuComponent
          ]
})
export class SharedModule { }
