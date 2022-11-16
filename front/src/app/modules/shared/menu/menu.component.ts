import { Component, OnInit } from '@angular/core';
import { last } from 'rxjs/operators';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  items: {
    name: string,
    value: string
  }[];

  firstName: string;
  lastName: string;

  baseUri: string;
  userType: number;

  constructor(private readonly userService: UserService) {
    this.items = [];
    this.firstName = "";
    this.lastName = "";
    this.baseUri = "";
    this.userType = -1;
  }

  ngOnInit(): void {
    this.userService.getUserInfos().subscribe(user => {
      this.firstName = user.firstName;
      this.lastName = user.lastName;
    });

    this.userType = this.userService.getUserType();

    this.baseUri = this.userType === 0 ? "/patient" : "/doctor";

    this.items = [
      {
        name: "Mes rendez-vous",
        value: "/appointment"
      }
    ];
    if (this.userService.getUserType() === 0) {
      this.items.unshift({
        name: "Acceuil",
        value: "/patient/search"
      })
    } else {
      this.items.unshift({
        name: "Agenda",
        value: "/doctor"
      })
    }
  }

  onLogout(): void {
    this.userService.logout();
  }

}
