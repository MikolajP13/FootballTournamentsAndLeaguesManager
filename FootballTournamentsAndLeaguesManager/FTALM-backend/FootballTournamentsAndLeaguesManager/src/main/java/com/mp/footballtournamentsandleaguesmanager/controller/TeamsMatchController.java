package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.TeamsMatchDTO;
import com.mp.footballtournamentsandleaguesmanager.service.TeamsMatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/teamsMatch")
public class TeamsMatchController {
    private final TeamsMatchService teamsMatchService;

    @PostMapping("/addLeagueMatch")
    public ResponseEntity<String> addTeamsToMatchInLeague(@RequestBody TeamsMatchDTO teamsMatchDTO){
        return teamsMatchService.addTeamsToMatchInCompetition(
                teamsMatchDTO.getMatchId(), teamsMatchDTO.getHomeTeamId(), teamsMatchDTO.getAwayTeamId(),
                null, teamsMatchDTO.getLeagueId(), true
        ) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/addTournamentMatch")
    public ResponseEntity<String> addTeamsToMatchInTournament(@RequestBody TeamsMatchDTO teamsMatchDTO){
        return teamsMatchService.addTeamsToMatchInCompetition(
                teamsMatchDTO.getMatchId(), teamsMatchDTO.getHomeTeamId(), teamsMatchDTO.getAwayTeamId(),
                teamsMatchDTO.getTournamentId(), null, false
        ) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
