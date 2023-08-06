import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { authGuard } from './security/guards/auth.guard';

const routes: Routes = [
  {path: "", component: LoginComponent},
  {path: "home", component: HomeComponent, canActivate: [authGuard]},
  {path: "register", component: RegistrationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }