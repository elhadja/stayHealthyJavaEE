import { Component, OnInit, Output } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { SlotDTO } from 'src/app/dto/slot';
import { DoctorService } from 'src/app/services/doctor.service';
import { MessageService } from 'src/app/services/Message.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-doctor',
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.css']
})
export class DoctorComponent implements OnInit {
  startTime: FormControl;
  date: FormControl;

  slotToUpdate: SlotDTO | undefined;
  updateSlotSubscription: Subscription;

  constructor(private readonly doctorService: DoctorService,
    private readonly messageService: MessageService,
    public readonly userService: UserService) {
    this.startTime = new FormControl('', [Validators.required]);
    this.date = new FormControl('', [Validators.required]);
    this.updateSlotSubscription = doctorService.updateSlotSubject.subscribe(slotIdToUpdate => {
      this.slotToUpdate = slotIdToUpdate;
      this.onUpdateDeleteSlot();
    })
   }

  onUpdateDeleteSlot() {
    console.log("update");
    this.date.setValue(this.slotToUpdate?.date);
    this.startTime.setValue(this.slotToUpdate?.startTime);
  }

  ngOnInit(): void {
  }

  onAddSlot(): void {
    this.doctorService.addSlot(this.getInput()).subscribe(() => {
      if (this.slotToUpdate !== undefined) {
        this.messageService.showMessage("Créneaux ajouté avec succès", 0);
      } else {
        this.messageService.showMessage("Créneaux modifié avec succès", 0);
        this.slotToUpdate = undefined;
      }

    })
  }

  onUpdateSlot(): void {
    if(this.slotToUpdate?.id !== undefined) {
      if(!this.slotToUpdate.used) {
        this.doctorService.deleteSlot(this.slotToUpdate?.id).subscribe(() => {
          this.onAddSlot();
        })
      } else {
        this.messageService.showMessage("Ce créneaux ne pas être modifié car occupé par un patient", 2);
        this.slotToUpdate = undefined;
      }
    }

  }

  onDeleteSlot(): void {
    if(this.slotToUpdate?.id !== undefined) {
      if(!this.slotToUpdate.used) {
        this.doctorService.deleteSlot(this.slotToUpdate?.id).subscribe(() => {
          this.messageService.showMessage("Créneaux supprimé avec succès", 0);
          this.slotToUpdate = undefined;
        })
      } else {
        this.messageService.showMessage("Ce créneaux ne pas être supprimé car occupé par un patient", 2);
        this.slotToUpdate = undefined;
      }
    }
  }

  private getInput(): SlotDTO {
    let input: SlotDTO;
    input = {
      startTime: this.startTime.value,
      date: this.date.value
    }
    return input;
  }

}
