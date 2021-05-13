import { Component, Input, OnInit } from '@angular/core';
import { AbstractControl, FormControl } from '@angular/forms';

@Component({
  selector: 'app-require-error',
  templateUrl: './require-error.component.html',
  styleUrls: ['./require-error.component.css']
})
export class RequireErrorComponent implements OnInit {
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
