package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TournamentStandings")
public class TournamentStanding extends Standing{
    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;
    private int groupId; // 1 - A, 2 - B, 3 - C, ...

    public TournamentStanding() {
    }

    public TournamentStanding(Team team, int matches, int points, int goalsFor, int goalsAgainst, int wins, int draws,
                              int losses, Tournament tournament, int groupId) {
        super(team, matches, points, goalsFor, goalsAgainst, wins, draws, losses);
        this.tournament = tournament;
        this.groupId = groupId;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

}
