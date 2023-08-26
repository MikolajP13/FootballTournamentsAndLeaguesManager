import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTeamPopupComponent } from './create-team-popup.component';

describe('CreateTeamPopupComponent', () => {
  let component: CreateTeamPopupComponent;
  let fixture: ComponentFixture<CreateTeamPopupComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateTeamPopupComponent]
    });
    fixture = TestBed.createComponent(CreateTeamPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
