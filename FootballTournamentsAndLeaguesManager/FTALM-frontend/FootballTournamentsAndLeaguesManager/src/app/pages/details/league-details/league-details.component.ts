import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-league-details',
  templateUrl: './league-details.component.html',
  styleUrls: ['./league-details.component.css']
})
export class LeagueDetailsComponent {
  leagueId!: number;

  constructor (private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.leagueId = params['id'];
    });
  }
}
