import { stringify } from '@angular/compiler/src/util';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Subscription } from 'rxjs';
import { DoctorDTO } from 'src/app/dto/doctorDTO';
import { SlotDTO } from 'src/app/dto/slot';
import { DoctorService } from 'src/app/services/doctor.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {
  @Input()
  userId: number;

  slots: SlotDTO[];
  slotsSubscription: Subscription;
  step: number;
  dates: string[];

  private slotsByDay: Map<string,SlotDTO[]>;

  private readonly daysOfWeek: string[];
  private readonly monthOfYear: string[];

  private showed: number = 0;
  private maxShowed: number = 0;

  isPatient: boolean;

  constructor(private readonly doctorService: DoctorService,
    private readonly userService: UserService) {
    this.step = 0;
    this.dates = [];
    this.daysOfWeek = ["dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi"]
    this.monthOfYear = ["janv", "fevr", "mars", "avri", "mai", "juin", "juil", "août", "sept", "octo", "nov", "dec"];
    this.slotsByDay = new Map();
    this.slots = [];
    this.isPatient = true;
    this.userId = 0;
    this.slotsSubscription = doctorService.slotsSubject.subscribe((slots) => {
      this.slots = slots;
      this.makeCurrentWeek();
    })
   }

  ngOnInit(): void {
    this.doctorService.getSlotsBetween(this.userId, this.jsDateToSHDate(new Date()), "").subscribe((slots: SlotDTO[]) => {
      this.slots = slots;
      this.makeCurrentWeek();
    });
    this.isPatient = this.userService.getUserType() === 0;
  }

  makeCurrentWeek() {
    const today = new Date();
    this.dates.splice(0);
    for (let i=0; i<5; i++) {
      let currentDate: Date;
      currentDate = new Date();
      currentDate.setDate(today.getDate() + i + (this.step * 5));
      let help = "";
      const sep1 = currentDate.getMonth() + 1 < 10 ? "-0" : "-";
      help = currentDate.getFullYear() + sep1 + (currentDate.getMonth() + 1) + "-" + currentDate.getDate();
      this.dates.push(help);
    }
  }

  getDay(date: string): string {
    return this.daysOfWeek[this.getJSDate(date).getDay()];
  }

  getMonth(date: string): string {
    return this.monthOfYear[this.getJSDate(date).getMonth()];
  }

  getJSDate(date: string): Date {
    let jsdate: Date;
    jsdate = new Date();
    const token = date.split("-");
    jsdate.setDate(+token[2]);
    jsdate.setMonth((+token[1])-1),
    jsdate.setFullYear(+token[0]);
    return jsdate;
  }

  help(): boolean {
    if (this.showed < this.maxShowed) {
      return true;
    }
    this.maxShowed = this.showed;
    return false;
  }

  makeSlotByDay(): void {
    /*
    let maxLength = 0;
    for (const slot of this.slots) {
      if (!this.slotsByDay?.has(slot.date)) {
        this.slotsByDay?.set(slot.date, [slot]);
      } else {
        this.slotsByDay.get(slot.date)?.push(slot);
      }
      let currentLength = this.slotsByDay.get(slot.date)?.length;
      if (currentLength !== undefined && currentLength > maxLength) {
        maxLength = currentLength;
      }
    }

    for (let entry of this.slotsByDay.entries()) {
      while (entry[1].length < maxLength) {
        entry[1].push(undefined);
      }
    }
  */
  }
  
  // TODO replace by Util.jsDateTo...
  private jsDateToSHDate(date: Date): string {
    let shDate = date.getFullYear() + '-';
    shDate += (date.getMonth() < 10) ? ('0' + date.getMonth()) : date.getMonth() + '';    
    shDate += '-';
    shDate += (date.getDate() < 10) ? ('0' + date.getDate()) : date.getDate() + '';
    return shDate;
  }

  next(): void {
    this.step += 1;
    this.makeCurrentWeek();
  }

  back(): void {
    if (this.step > 0) {
      this.step -= 1;
      this.makeCurrentWeek();
    }
  }
}
