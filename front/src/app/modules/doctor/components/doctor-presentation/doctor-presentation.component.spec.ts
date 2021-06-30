import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorPresentationComponent } from './doctor-presentation.component';

describe('DoctorPresentationComponent', () => {
  let component: DoctorPresentationComponent;
  let fixture: ComponentFixture<DoctorPresentationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorPresentationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DoctorPresentationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
