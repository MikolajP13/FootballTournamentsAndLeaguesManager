import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeagueTableComponent } from './league-table.component';

describe('LeagueTableComponent', () => {
  let component: LeagueTableComponent;
  let fixture: ComponentFixture<LeagueTableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LeagueTableComponent]
    });
    fixture = TestBed.createComponent(LeagueTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
