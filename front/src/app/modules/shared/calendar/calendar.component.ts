import { stringify } from '@angular/compiler/src/util';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Subscription } from 'rxjs';
import { DoctorDTO } from 'src/app/dto/doctorDTO';
import { SlotDTO } from 'src/app/dto/slot';
import { UtilDate } from 'src/app/services/date.service';
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

  private showed: number = 0;
  private maxShowed: number = 0;

  isPatient: boolean;

  constructor(private readonly doctorService: DoctorService,
    private readonly userService: UserService,
    public readonly utilDate: UtilDate) {
    this.step = 0;
    this.dates = [];
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

  help(): boolean {
    if (this.showed < this.maxShowed) {
      return true;
    }
    this.maxShowed = this.showed;
    return false;
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
