import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateLeaguePopupComponent } from './create-league-popup.component';

describe('CreateLeaguePopupComponent', () => {
  let component: CreateLeaguePopupComponent;
  let fixture: ComponentFixture<CreateLeaguePopupComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateLeaguePopupComponent]
    });
    fixture = TestBed.createComponent(CreateLeaguePopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
