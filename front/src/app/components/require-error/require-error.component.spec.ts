import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequireErrorComponent } from './require-error.component';

describe('RequireErrorComponent', () => {
  let component: RequireErrorComponent;
  let fixture: ComponentFixture<RequireErrorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RequireErrorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RequireErrorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
