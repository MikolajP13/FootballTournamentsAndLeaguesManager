import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { authGuard } from './security/guards/auth.guard';
import { RegistrationSuccessComponent } from './pages/registration-success/registration-success.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { TournamentsComponent } from './pages/tournaments/tournaments.component';
import { LeaguesComponent } from './pages/leagues/leagues.component';
import { TeamsComponent } from './pages/teams/teams.component';
import { TeamDetailsComponent } from './pages/details/team-details/team-details.component';
import { TeamPlayersComponent } from './pages/details/team-players/team-players.component';
import { TeamMatchesComponent } from './pages/details/team-matches/team-matches.component';
import { TeamStatisticsComponent } from './pages/details/team-statistics/team-statistics.component';
import { TeamAboutComponent } from './pages/details/team-about/team-about.component';
import { TournamentAboutComponent } from './pages/details/tournament-about/tournament-about.component';
import { LeagueAboutComponent } from './pages/details/league-about/league-about.component';
import { TournamentTeamsComponent } from './pages/details/tournament-teams/tournament-teams.component';
import { LeagueTeamsComponent } from './pages/details/league-teams/league-teams.component';
import { LeagueTableComponent } from './pages/details/league-table/league-table.component';
import { LeagueMatchesComponent } from './pages/details/league-matches/league-matches.component';
import { LeagueStatisticsComponent } from './pages/details/league-statistics/league-statistics.component';
import { LeagueMatchComponent } from './pages/details/league-match/league-match.component';
import { LeagueMatchDetailsComponent } from './pages/details/league-match-details/league-match-details.component';
import { TournamentBracketComponent } from './pages/details/tournament-bracket/tournament-bracket.component';
import { TournamentMatchesComponent } from './pages/details/tournament-matches/tournament-matches.component';
import { TournamentMatchDetailsComponent } from './pages/details/tournament-match-details/tournament-match-details.component';
import { TournamentMatchComponent } from './pages/details/tournament-match/tournament-match.component';
import { TournamentStatisticsComponent } from './pages/details/tournament-statistics/tournament-statistics.component';

const routes: Routes = [
  {path: "", component: LoginComponent},
  {path: "home", component: HomeComponent, canActivate: [authGuard]},
  {path: "tournaments", component: TournamentsComponent, canActivate: [authGuard]},
  {path: "tournament/:id/about", component: TournamentAboutComponent, canActivate: [authGuard]},
  {path: "tournament/:id/teams", component: TournamentTeamsComponent, canActivate: [authGuard]},
  {path: "tournament/:id/bracket", component: TournamentBracketComponent, canActivate: [authGuard]},
  {path: "tournament/:id/matches", component: TournamentMatchesComponent, canActivate: [authGuard]},
  {path: "tournament/:tournamentId/match/:id", component: TournamentMatchComponent, canActivate: [authGuard]},
  {path: "tournament/:tournamentId/match-details/:id", component: TournamentMatchDetailsComponent, canActivate: [authGuard]},
  {path: "tournament/:id/statistics", component: TournamentStatisticsComponent, canActivate: [authGuard]},
  {path: "leagues", component: LeaguesComponent, canActivate: [authGuard]},
  {path: "league/:id/about", component: LeagueAboutComponent, canActivate: [authGuard]},
  {path: "league/:id/teams", component: LeagueTeamsComponent, canActivate: [authGuard]},
  {path: "league/:id/table", component: LeagueTableComponent, canActivate: [authGuard]},
  {path: "league/:id/matches", component: LeagueMatchesComponent, canActivate: [authGuard]},
  {path: "league/:leagueId/match/:id", component: LeagueMatchComponent, canActivate: [authGuard]},
  {path: "league/:leagueId/match-details/:id", component: LeagueMatchDetailsComponent, canActivate: [authGuard]},
  {path: "league/:id/statistics", component: LeagueStatisticsComponent, canActivate: [authGuard]},
  {path: "teams", component: TeamsComponent, canActivate: [authGuard]},
  {path: "team/:id", component: TeamDetailsComponent, canActivate: [authGuard]},
  {path: "team/:id/matches", component: TeamMatchesComponent, canActivate: [authGuard]},
  {path: "team/:id/players", component: TeamPlayersComponent, canActivate: [authGuard]},
  {path: "team/:id/statistics", component: TeamStatisticsComponent, canActivate: [authGuard]},
  {path: "team/:id/about", component: TeamAboutComponent, canActivate: [authGuard]},
  {path: "register", component: RegistrationComponent},
  {path: "register-success", component: RegistrationSuccessComponent},
  {path: "**", component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }