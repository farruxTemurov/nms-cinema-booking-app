import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { MoviesComponent } from './pages/movies/movies.component';
import { TheatersComponent } from './pages/theaters/theaters.component';
import { BookingsComponent } from './pages/bookings/bookings.component';

const routes: Routes = [
  { path: '', component: HomeComponent },           // Default route
  { path: 'login', component: LoginComponent },     // Login page
  { path: 'register', component: RegisterComponent }, // Register page
  { path: 'movies', component: MoviesComponent },
  { path: 'theaters', component: TheatersComponent },
  { path: 'add-movie', component: MoviesComponent },
  { path: 'add-theater', component: TheatersComponent },
  { path: 'bookings', component: BookingsComponent },

  // Wildcard fallback
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
