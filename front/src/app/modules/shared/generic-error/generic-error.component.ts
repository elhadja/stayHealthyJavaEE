import { Component, Input, OnInit } from '@angular/core';
import { AbstractControl, FormControl } from '@angular/forms';

@Component({
  selector: 'app-generic-error',
  templateUrl: './generic-error.component.html',
  styleUrls: ['./generic-error.component.css']
})
export class GenericErrorComponent implements OnInit {
  @Input()
  fc: AbstractControl;

  @Input()
  fieldName: string;

  @Input()
  required: boolean;

  @Input()
  maxLength: number;

  @Input()
  minLength: number;

  @Input()
  email: boolean;

  constructor() { 
    this.fc = new FormControl();
    this.fieldName = 'champs';
    this.required = false;
    this.email = false;
    this.maxLength = 0;
    this.minLength = 0;
  }

  ngOnInit(): void {
  }
}
