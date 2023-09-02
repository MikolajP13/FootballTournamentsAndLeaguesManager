import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-team-details',
  templateUrl: './team-details.component.html',
  styleUrls: ['./team-details.component.css']
})
export class TeamDetailsComponent {
  teamId!: number;
  teamName: any;
  establishmentDate!: Date;
  totalPlayers: any;
  positions: any;
  playersByPosition: any;
  currentCompetitions: any;

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.teamId = params['id'];
    });
  }
}
