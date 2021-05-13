import { Component, Input, OnInit } from '@angular/core';
import { AbstractControl, FormControl } from '@angular/forms';

@Component({
  selector: 'app-invalid-email-error',
  templateUrl: './invalid-email-error.component.html',
  styleUrls: ['./invalid-email-error.component.css']
})
export class InvalidEmailErrorComponent implements OnInit {
  @Input()
  email: AbstractControl;

  @Input()
  fieldName: string;

  constructor() { 
    this.email = new FormControl();
    this.fieldName = '';
  }

  ngOnInit(): void {
  }

}
