import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamRemoveInformationComponent } from './team-remove-information.component';

describe('TeamRemoveInformationComponent', () => {
  let component: TeamRemoveInformationComponent;
  let fixture: ComponentFixture<TeamRemoveInformationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TeamRemoveInformationComponent]
    });
    fixture = TestBed.createComponent(TeamRemoveInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
