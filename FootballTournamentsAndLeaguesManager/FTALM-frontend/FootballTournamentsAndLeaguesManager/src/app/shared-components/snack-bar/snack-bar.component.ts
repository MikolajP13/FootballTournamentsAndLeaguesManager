import { Component, Inject } from '@angular/core';
import { MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';
import { SnackBarService } from 'src/app/services/snackBarService/snack-bar.service';


@Component({
  selector: 'app-snack-bar',
  templateUrl: './snack-bar.component.html',
  styleUrls: ['./snack-bar.component.css']
})

export class SnackBarComponent {
  snackMessage?: string;

  constructor(private snackBarService: SnackBarService, @Inject(MAT_SNACK_BAR_DATA) public data: any) { } 
  
  ngOnInit() {
    this.snackMessage = this.data.message;
  }

  trigger(message: string, action: string) { 
    this.snackMessage = message;
    this.snackBarService.openSnackBar(message, action); 
  } 
}
