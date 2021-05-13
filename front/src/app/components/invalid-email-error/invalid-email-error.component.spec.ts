import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InvalidEmailErrorComponent } from './invalid-email-error.component';

describe('InvalidEmailErrorComponent', () => {
  let component: InvalidEmailErrorComponent;
  let fixture: ComponentFixture<InvalidEmailErrorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InvalidEmailErrorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InvalidEmailErrorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
