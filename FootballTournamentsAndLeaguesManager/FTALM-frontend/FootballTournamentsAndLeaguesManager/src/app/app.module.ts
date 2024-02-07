import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './pages/home/home.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { StrengthMeterComponent } from './shared-components/strength-meter/strength-meter.component';
import { RegistrationSuccessComponent } from './pages/registration-success/registration-success.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './shared-components/navbar/navbar.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { TeamsComponent } from './pages/teams/teams.component';
import { TournamentsComponent } from './pages/tournaments/tournaments.component';
import { LeaguesComponent } from './pages/leagues/leagues.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { SidenavComponent } from './shared-components/sidenav/sidenav.component';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { SidenavService } from './services/sidenavService/sidenav.service';
import { TeamDetailsComponent } from './pages/details/team-details/team-details.component';
import { CreateTeamPopupComponent } from './pages/popups/create-team-popup/create-team-popup.component';
import { MatDialogModule } from '@angular/material/dialog';
import { TeamPlayersComponent } from './pages/details/team-players/team-players.component';
import { TeamMatchesComponent } from './pages/details/team-matches/team-matches.component';
import { TeamStatisticsComponent } from './pages/details/team-statistics/team-statistics.component';
import { TeamAboutComponent } from './pages/details/team-about/team-about.component';
import { AddPlayerPopupComponent } from './pages/popups/add-player-popup/add-player-popup.component';
import { MatSelectModule } from '@angular/material/select';
import { ConfirmationPopupComponent } from './pages/popups/confirmation-popup/confirmation-popup.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DATE_LOCALE, MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { PlayerDetailsPopupComponent } from './pages/popups/player-details-popup/player-details-popup.component';
import { CreateTournamentPopupComponent } from './pages/popups/create-tournament-popup/create-tournament-popup.component';
import { TournamentAboutComponent } from './pages/details/tournament-about/tournament-about.component';
import { LeagueAboutComponent } from './pages/details/league-about/league-about.component';
import { CreateLeaguePopupComponent } from './pages/popups/create-league-popup/create-league-popup.component';
import { MatRadioModule } from '@angular/material/radio';
import { TournamentTeamsComponent } from './pages/details/tournament-teams/tournament-teams.component';
import { LeagueTeamsComponent } from './pages/details/league-teams/league-teams.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { LeagueTableComponent } from './pages/details/league-table/league-table.component';
import { LeagueMatchesComponent } from './pages/details/league-matches/league-matches.component';
import { LeagueStatisticsComponent } from './pages/details/league-statistics/league-statistics.component';
import { MatTabsModule } from '@angular/material/tabs';
import { LeagueMatchComponent } from './pages/details/league-match/league-match.component';
import { MatSnackBarModule, MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';
import { SnackBarComponent } from './shared-components/snack-bar/snack-bar.component';
import { MatMenuModule } from '@angular/material/menu';
import { MatCardModule } from '@angular/material/card';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { LeagueMatchDetailsComponent } from './pages/details/league-match-details/league-match-details.component';
import { TournamentStatisticsComponent } from './pages/details/tournament-statistics/tournament-statistics.component';
import { TournamentMatchesComponent } from './pages/details/tournament-matches/tournament-matches.component';
import { TournamentMatchDetailsComponent } from './pages/details/tournament-match-details/tournament-match-details.component';
import { TournamentBracketComponent } from './pages/details/tournament-bracket/tournament-bracket.component';
import { TournamentMatchComponent } from './pages/details/tournament-match/tournament-match.component';
import { MatStepperModule } from '@angular/material/stepper';
import { MatPaginatorModule } from '@angular/material/paginator';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { DeletePlayerPopupComponent } from './pages/popups/delete-player-popup/delete-player-popup.component';
import { EditPlayerPopupComponent } from './pages/popups/edit-player-popup/edit-player-popup.component';
import { TeamRemoveInformationComponent } from './pages/popups/team-remove-information/team-remove-information.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    RegistrationComponent,
    StrengthMeterComponent,
    RegistrationSuccessComponent,
    PageNotFoundComponent,
    NavbarComponent,
    TeamsComponent,
    TournamentsComponent,
    LeaguesComponent,
    SidenavComponent,
    TeamDetailsComponent,
    CreateTeamPopupComponent,
    TeamPlayersComponent,
    TeamMatchesComponent,
    TeamStatisticsComponent,
    TeamAboutComponent,
    AddPlayerPopupComponent,
    ConfirmationPopupComponent,
    PlayerDetailsPopupComponent,
    CreateTournamentPopupComponent,
    TournamentAboutComponent,
    LeagueAboutComponent,
    CreateLeaguePopupComponent,
    TournamentTeamsComponent,
    LeagueTeamsComponent,
    LeagueTableComponent,
    LeagueMatchesComponent,
    LeagueStatisticsComponent,
    LeagueMatchComponent,
    SnackBarComponent,
    LeagueMatchDetailsComponent,
    TournamentStatisticsComponent,
    TournamentMatchesComponent,
    TournamentMatchDetailsComponent,
    TournamentBracketComponent,
    TournamentMatchComponent,
    DeletePlayerPopupComponent,
    EditPlayerPopupComponent,
    TeamRemoveInformationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatTableModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    MatDialogModule,
    MatSelectModule,
    MatTooltipModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    MatRadioModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    MatTabsModule,
    MatSnackBarModule,
    MatMenuModule,
    MatCardModule,
    MatSlideToggleModule,
    MatStepperModule,
    MatPaginatorModule,
    FontAwesomeModule,
    
  ],
  providers: [SidenavService, SnackBarComponent, {provide: MAT_DATE_LOCALE, useValue: 'en-GB'}, {provide: MAT_SNACK_BAR_DATA, useValue: { duration: 1500 }}],
  bootstrap: [AppComponent],
  
})
export class AppModule { }
