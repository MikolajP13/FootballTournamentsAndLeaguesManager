import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationPopupComponent } from '../../popups/confirmation-popup/confirmation-popup.component';

@Component({
  selector: 'app-league-about',
  templateUrl: './league-about.component.html',
  styleUrls: ['./league-about.component.css']
})
export class LeagueAboutComponent {
  leagueId!: number;
  objectName: string = 'league';

  constructor(private route: ActivatedRoute, private dialog: MatDialog) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.leagueId = params['id'];
    });
  }

  openConfirmationPopup(){
    this.dialog.open(ConfirmationPopupComponent, {data: {
      id: this.leagueId,
      objectName: this.objectName,
    }
    });
  }

  startLeague() {
    console.log("Start league " + this.leagueId);
    //TODO: DB update league status to IN_PROGRESS
    //TODO: league can only be started once!!
    //TODO: logic for creating matches, table etc.
  }
}
