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
import { TeamDetailsComponent } from './pages/team-details/team-details.component';

const routes: Routes = [
  {path: "", component: LoginComponent},
  {path: "home", component: HomeComponent, canActivate: [authGuard]},
  {path: "tournaments", component: TournamentsComponent, canActivate: [authGuard]},
  // {path: "tournament/:id", component: TournamentDetailsComponent, canActivate: [authGuard]},
  {path: "leagues", component: LeaguesComponent, canActivate: [authGuard]},
  // {path: "league/:id", component: LeagueDetailsComponent, canActivate: [authGuard]},
  {path: "teams", component: TeamsComponent, canActivate: [authGuard]},
  {path: "team/:id", component: TeamDetailsComponent, canActivate: [authGuard]},
  {path: "register", component: RegistrationComponent},
  {path: "register-success", component: RegistrationSuccessComponent},
  {path: "**", component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }