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
import { TeamMatchesPlayedComponent } from './pages/details/team-matches-played/team-matches-played.component';
import { TeamMatchesUpcomingComponent } from './pages/details/team-matches-upcoming/team-matches-upcoming.component';

const routes: Routes = [
  {path: "", component: LoginComponent},
  {path: "home", component: HomeComponent, canActivate: [authGuard]},
  {path: "tournaments", component: TournamentsComponent, canActivate: [authGuard]},
  {path: "tournament/:tournamentId/about", component: TournamentAboutComponent, canActivate: [authGuard]},
  {path: "tournament/:tournamentId/teams", component: TournamentTeamsComponent, canActivate: [authGuard]},
  {path: "tournament/:tournamentId/bracket", component: TournamentBracketComponent, canActivate: [authGuard]},
  {path: "tournament/:tournamentId/matches", component: TournamentMatchesComponent, canActivate: [authGuard]},
  {path: "tournament/:tournamentId/match/:matchId", component: TournamentMatchComponent, canActivate: [authGuard]},
  {path: "tournament/:tournamentId/match-details/:matchId", component: TournamentMatchDetailsComponent, canActivate: [authGuard]},
  {path: "tournament/:tournamentId/statistics", component: TournamentStatisticsComponent, canActivate: [authGuard]},
  {path: "leagues", component: LeaguesComponent, canActivate: [authGuard]},
  {path: "league/:leagueId/about", component: LeagueAboutComponent, canActivate: [authGuard]},
  {path: "league/:leagueId/teams", component: LeagueTeamsComponent, canActivate: [authGuard]},
  {path: "league/:leagueId/table", component: LeagueTableComponent, canActivate: [authGuard]},
  {path: "league/:leagueId/matches", component: LeagueMatchesComponent, canActivate: [authGuard]},
  {path: "league/:leagueId/match/:matchId", component: LeagueMatchComponent, canActivate: [authGuard]},
  {path: "league/:leagueId/match-details/:matchId", component: LeagueMatchDetailsComponent, canActivate: [authGuard]},
  {path: "league/:leagueId/statistics", component: LeagueStatisticsComponent, canActivate: [authGuard]},
  {path: "teams", component: TeamsComponent, canActivate: [authGuard]},
  {path: "team/:teamId", component: TeamDetailsComponent, canActivate: [authGuard]},
  {path: "team/:teamId/matches/played", component: TeamMatchesPlayedComponent, canActivate: [authGuard]},
  {path: "team/:teamId/matches/upcoming", component: TeamMatchesUpcomingComponent, canActivate: [authGuard]},
  {path: "team/:teamId/players", component: TeamPlayersComponent, canActivate: [authGuard]},
  {path: "team/:teamId/statistics", component: TeamStatisticsComponent, canActivate: [authGuard]},
  {path: "team/:teamId/about", component: TeamAboutComponent, canActivate: [authGuard]},
  {path: "register", component: RegistrationComponent},
  {path: "register-success", component: RegistrationSuccessComponent},
  {path: "**", component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }