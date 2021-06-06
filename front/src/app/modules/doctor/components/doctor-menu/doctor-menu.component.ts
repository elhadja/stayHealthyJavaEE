import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-doctor-menu',
  templateUrl: './doctor-menu.component.html',
  styleUrls: ['./doctor-menu.component.css']
})
export class DoctorMenuComponent implements OnInit {

  constructor(private readonly userService: UserService) { }

  ngOnInit(): void {
  }

  logout(): void {
    this.userService.logout();
  }

}
