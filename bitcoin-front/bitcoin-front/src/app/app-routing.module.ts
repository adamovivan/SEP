import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SuccessComponent } from './success/success.component';
import { CancelComponent } from './cancel/cancel.component';
import { RegisterComponent } from './register/register.component';
import { MainComponent } from './main/main.component';


const routes: Routes = [
  {
    path : 'success/:token',
    component : SuccessComponent
  },
  {
    path : 'cancel/:token', 
    component : CancelComponent
  },
  {
    path : 'input/:username', 
    component : RegisterComponent
  }, 
  {
    path: 'pay/:username/:total',
    component : MainComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
