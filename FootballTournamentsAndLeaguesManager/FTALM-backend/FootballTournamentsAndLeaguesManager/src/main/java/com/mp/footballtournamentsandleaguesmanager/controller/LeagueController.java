package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueTeamDTO;
import com.mp.footballtournamentsandleaguesmanager.model.League;
import com.mp.footballtournamentsandleaguesmanager.model.TournamentLeagueBase;
import com.mp.footballtournamentsandleaguesmanager.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/league")
public class LeagueController {
    private final LeagueService leagueService;

    @Autowired
    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping("find/{leagueId}")
    public ResponseEntity<LeagueDTO> getLeagueById(@PathVariable Long leagueId){
        LeagueDTO league = leagueService.getLeagueById(leagueId);
        return new ResponseEntity<>(league, HttpStatus.OK); //TODO handle missing ID!
    }

    @GetMapping("active/{teamId}")
    public ResponseEntity<LeagueDTO> findActiveLeagueForTeam(@PathVariable Long teamId){
        LeagueDTO league = leagueService.findActiveLeagueForTeam(teamId);
        return new ResponseEntity<>(league, HttpStatus.OK);
    }

//    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("all/{userId}")
    public ResponseEntity<List<LeagueDTO>> getAllUserLeagues(@PathVariable Long userId) {
        List<LeagueDTO> userLeagues = leagueService.getAllByUserId(userId);
        return ResponseEntity.ok(userLeagues);
    }

    @GetMapping("/getStatus/{leagueId}")
    public ResponseEntity<TournamentLeagueBase.Status> getLeagueStatusByLeagueId(@PathVariable Long leagueId){
        return new ResponseEntity<>(leagueService.getLeagueStatusByLeagueId(leagueId), HttpStatus.OK);
    }

    @GetMapping("/getNumberOfTeams/{leagueId}")
    public ResponseEntity<Integer> getNumberOfTeamsByLeagueId(@PathVariable Long leagueId){
        return ResponseEntity.ok(leagueService.getNumberOfTeamsByLeagueId(leagueId));
    }

    @GetMapping("/getType/{leagueId}")
    public ResponseEntity<League.LeagueType> getLeagueTypeByLeagueId(@PathVariable Long leagueId){
        return new ResponseEntity<>(leagueService.getLeagueTypeByLeagueId(leagueId), HttpStatus.OK);
    }

    @PutMapping("/updateStatus/{leagueId}")
    public ResponseEntity<LeagueDTO> updateLeagueStatusByLeagueId(@PathVariable Long leagueId, @RequestBody League league){
        League updatedLeague = leagueService.updateLeagueStatusByLeagueId(leagueId, league.getStatus());
        LeagueDTO updatedLeagueDTO = leagueService.convertToDTO(updatedLeague);
        return ResponseEntity.ok(updatedLeagueDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<League> addLeague(@RequestBody League league){
        League newLeague = leagueService.addLeague(league);
        return new ResponseEntity<>(newLeague, HttpStatus.CREATED);
    }

    @PostMapping("/addTeam")
    public ResponseEntity<String> addTeamToLeagueByIds(@RequestBody LeagueTeamDTO leagueTeamDTO){
        return leagueService.addTeamToLeagueByIds(leagueTeamDTO.getTeamId(), leagueTeamDTO.getLeagueId()) ?
                ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{leagueId}")
    public ResponseEntity<String> deleteLeagueById(@PathVariable Long leagueId){
        return leagueService.deleteLeagueById(leagueId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
