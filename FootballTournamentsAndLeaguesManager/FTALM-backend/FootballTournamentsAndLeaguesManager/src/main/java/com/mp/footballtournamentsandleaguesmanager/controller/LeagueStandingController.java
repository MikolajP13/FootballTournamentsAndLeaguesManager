package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueStandingDTO;
import com.mp.footballtournamentsandleaguesmanager.model.LeagueStanding;
import com.mp.footballtournamentsandleaguesmanager.service.LeagueStandingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leagueStanding")
public class LeagueStandingController {
    private final LeagueStandingService leagueStandingService;

    @Autowired
    public LeagueStandingController(LeagueStandingService leagueStandingService) {
        this.leagueStandingService = leagueStandingService;
    }

    @PostMapping("/add")
    public ResponseEntity<List<LeagueStanding>> addLeagueStanding(@RequestBody List<LeagueStanding> leagueStanding){
        List<LeagueStanding> newLeagueStanding = this.leagueStandingService.addLeagueStanding(leagueStanding);
        return new ResponseEntity<>(newLeagueStanding, HttpStatus.OK);
    }

    @GetMapping("/all/{leagueId}")
    public ResponseEntity<List<LeagueStandingDTO>> getLeagueStandingByLeagueId(@PathVariable Long leagueId){
        List<LeagueStandingDTO> leagueStandingDTOList = this.leagueStandingService.getLeagueStandingByLeagueId(leagueId);
        return ResponseEntity.ok(leagueStandingDTOList);
    }

    @GetMapping("/getTeamsGoalsFor/{leagueId}")
    public ResponseEntity<List<LeagueStandingDTO>> getLeagueStandingsByLeagueIdOrderByGoalsFor(@PathVariable Long leagueId){
        List<LeagueStandingDTO> leagueStandingDTOList = this.leagueStandingService.getLeagueStandingsByLeagueIdOrderByGoalsFor(leagueId);
        return ResponseEntity.ok(leagueStandingDTOList);
    }

    @GetMapping("/getTeamsWins/{leagueId}")
    public ResponseEntity<List<LeagueStandingDTO>> getLeagueStandingsByLeagueIdOrderByWins(@PathVariable Long leagueId){
        List<LeagueStandingDTO>leagueStandingDTOList = this.leagueStandingService.getLeagueStandingsByLeagueIdOrderByWins(leagueId);
        return ResponseEntity.ok(leagueStandingDTOList);
    }

    @GetMapping("/getTeamsLosses/{leagueId}")
    public ResponseEntity<List<LeagueStandingDTO>> getLeagueStandingsByLeagueIdOrderByLosses(@PathVariable Long leagueId){
        List<LeagueStandingDTO> leagueStandingDTOList = this.leagueStandingService.getLeagueStandingsByLeagueIdOrderByLosses(leagueId);
        return ResponseEntity.ok(leagueStandingDTOList);
    }
}
