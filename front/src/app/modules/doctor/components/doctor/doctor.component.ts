import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { SlotDTO } from 'src/app/dto/slot';
import { DoctorService } from 'src/app/services/doctor.service';
import { MessageService } from 'src/app/services/Message.service';

@Component({
  selector: 'app-doctor',
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.css']
})
export class DoctorComponent implements OnInit {
  startTime: FormControl;
  date: FormControl;

  constructor(private readonly doctorService: DoctorService,
    private readonly messageService: MessageService) {
    this.startTime = new FormControl('', [Validators.required]);
    this.date = new FormControl('', [Validators.required]);
   }

  ngOnInit(): void {
  }

  onAddSlot(): void {
    let input: SlotDTO;
    input = {
      startTime: this.startTime.value,
      date: this.date.value
    }
    this.doctorService.addSlot(input).subscribe(() => {
      this.messageService.showMessage("Créneaux ajouté avec succès", 0);
    })
  }

}
