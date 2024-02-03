package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TournamentStandings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TournamentStanding extends Standing{
    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;
    private int groupId; // 1 - A, 2 - B, 3 - C, ...
}
