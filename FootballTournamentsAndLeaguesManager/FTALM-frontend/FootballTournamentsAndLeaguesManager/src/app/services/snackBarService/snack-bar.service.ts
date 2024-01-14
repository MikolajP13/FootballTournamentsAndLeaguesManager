import { Injectable } from '@angular/core'; 
import { MatSnackBar } from '@angular/material/snack-bar'; 
import { SnackBarComponent } from '../../shared-components/snack-bar/snack-bar.component';
  
@Injectable({ 
  providedIn: 'root'
}) 

export class SnackBarService { 

  constructor(private snackBar: MatSnackBar) { } 
  
  openSnackBar(message: string, action: string) { 
    this.snackBar.openFromComponent(SnackBarComponent, { 
       duration: 1500,
       horizontalPosition: 'end',
       verticalPosition: 'bottom',
       panelClass: 'match-event-snackbar',
       data: { message }
    });
  }
} 
