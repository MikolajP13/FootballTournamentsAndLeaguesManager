import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamAboutComponent } from './team-about.component';

describe('TeamAboutComponent', () => {
  let component: TeamAboutComponent;
  let fixture: ComponentFixture<TeamAboutComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TeamAboutComponent]
    });
    fixture = TestBed.createComponent(TeamAboutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
