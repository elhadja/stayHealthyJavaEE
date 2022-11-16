import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuDropdownUserComponent } from './menu-dropdown-user.component';

describe('MenuDropdownUserComponent', () => {
  let component: MenuDropdownUserComponent;
  let fixture: ComponentFixture<MenuDropdownUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MenuDropdownUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuDropdownUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
