package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.TeamDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Team;
import com.mp.footballtournamentsandleaguesmanager.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/find/{teamId}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long teamId){
        TeamDTO team = teamService.getTeamById(teamId);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<TeamDTO>> getAllUserTeams(@PathVariable Long userId){
        List<TeamDTO> userTeams = teamService.getAllByUserId(userId);
        return ResponseEntity.ok(userTeams);
    }

    @PostMapping("add")
    public ResponseEntity<Team> addTeam(@RequestBody Team team){
        Team newTeam = teamService.addTeam(team);
        return new ResponseEntity<>(newTeam, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{teamId}")
    public ResponseEntity<String> deleteTeamById(@PathVariable Long teamId){
        return teamService.deleteTeamById(teamId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
