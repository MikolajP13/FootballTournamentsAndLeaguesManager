package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.MatchDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Match;
import com.mp.footballtournamentsandleaguesmanager.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {
    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/find/{matchId}")
    public ResponseEntity<MatchDTO> getMatchById(@PathVariable Long matchId){
        return new ResponseEntity<>(matchService.getMatchById(matchId), HttpStatus.OK);
    }

    @GetMapping("/findAll/league/{leagueId}")
    public ResponseEntity<List<MatchDTO>> getAllByLeagueId(@PathVariable Long leagueId){
        return new ResponseEntity<>(matchService.getAllByLeagueId(leagueId), HttpStatus.OK);
    }

    @GetMapping("/findAll/tournament/{tournamentId}")
    public ResponseEntity<List<MatchDTO>> getAllByTournamentId(@PathVariable Long tournamentId){
        return new ResponseEntity<>(matchService.getAllByTournamentId(tournamentId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Match> addMatch(@RequestBody Match match){
        Match newMatch = matchService.createMatch(match);
        return new ResponseEntity<>(newMatch, HttpStatus.CREATED);
    }

}
