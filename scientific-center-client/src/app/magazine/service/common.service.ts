import { Injectable } from '@angular/core';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor(private snackBar: MatSnackBar) { }

  somethingWentWrong(){
    const config = new MatSnackBarConfig();
    config.panelClass = ['background-snack'];
    config.duration = 2000;

    this.snackBar.open('Something went wrong, please try again.', null, config);
  }

  showMessage(message: string){
    const config = new MatSnackBarConfig();
    config.panelClass = ['background-snack'];
    config.duration = 2000;

    this.snackBar.open(message, null, config);
  }
}
