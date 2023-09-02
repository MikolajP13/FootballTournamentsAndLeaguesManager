import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationPopupComponent } from '../../popups/confirmation-popup/confirmation-popup.component';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-team-about',
  templateUrl: './team-about.component.html',
  styleUrls: ['./team-about.component.css']
})
export class TeamAboutComponent {
  teamId!: number;
  objectName: string = 'team';

  constructor(private route: ActivatedRoute, private dialog: MatDialog) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.teamId = params['id'];
    });
  }

  openConfirmationPopup(){
    this.dialog.open(ConfirmationPopupComponent, {data: {
      id: this.teamId,
      objectName: this.objectName,
    }
    });
  }
}
