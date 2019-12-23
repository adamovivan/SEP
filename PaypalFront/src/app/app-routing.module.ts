import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CancelComponent } from './cancel/cancel.component';
import { SuccessComponent } from './success/success.component';
import { UserDataComponent } from './user-data/user-data.component';


const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'success',
    component: SuccessComponent
  },
  {
    path: 'cancel',
    component: CancelComponent
  },
  {
    path: 'input/:username',
    component: UserDataComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
