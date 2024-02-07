package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueStandingDTO;
import com.mp.footballtournamentsandleaguesmanager.model.LeagueStanding;
import com.mp.footballtournamentsandleaguesmanager.repository.LeagueStandingRepository;
import com.mp.footballtournamentsandleaguesmanager.service.LeagueStandingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/leagueStanding")
public class LeagueStandingController {
    private final LeagueStandingService leagueStandingService;

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

    @PutMapping("/update/league/{leagueId}/team/{teamId}")
    public ResponseEntity<LeagueStandingDTO> updateLeagueStanding(@PathVariable Long leagueId,
                                                                  @PathVariable Long teamId,
                                                                  @RequestBody LeagueStandingDTO leagueStandingDTO){
        LeagueStanding updatedLeagueStanding = leagueStandingService.updateLeagueStanding(leagueId, teamId, leagueStandingDTO);
        LeagueStandingDTO updatedLeagueStandingDTO = leagueStandingService.convertToDTO(updatedLeagueStanding);
        return ResponseEntity.ok(updatedLeagueStandingDTO);
    }

    @GetMapping("/getTeamsGoalsFor/{leagueId}")
    public ResponseEntity<List<LeagueStandingDTO>> getLeagueStandingsByLeagueIdOrderByGoalsForDesc(@PathVariable Long leagueId){
        List<LeagueStandingDTO> leagueStandingDTOList = this.leagueStandingService.getLeagueStandingsByLeagueIdOrderByGoalsForDesc(leagueId);
        return ResponseEntity.ok(leagueStandingDTOList);
    }

    @GetMapping("/getTeamsWins/{leagueId}")
    public ResponseEntity<List<LeagueStandingDTO>> getLeagueStandingsByLeagueIdOrderByWinsDesc(@PathVariable Long leagueId){
        List<LeagueStandingDTO>leagueStandingDTOList = this.leagueStandingService.getLeagueStandingsByLeagueIdOrderByWinsDesc(leagueId);
        return ResponseEntity.ok(leagueStandingDTOList);
    }

    @GetMapping("/getTeamsLosses/{leagueId}")
    public ResponseEntity<List<LeagueStandingDTO>> getLeagueStandingsByLeagueIdOrderByLossesDesc(@PathVariable Long leagueId){
        List<LeagueStandingDTO> leagueStandingDTOList = this.leagueStandingService.getLeagueStandingsByLeagueIdOrderByLossesDesc(leagueId);
        return ResponseEntity.ok(leagueStandingDTOList);
    }
}
