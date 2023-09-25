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

    @GetMapping("/all/not-in-tournament/{userId}")
    public ResponseEntity<List<TeamDTO>> findAllTeamsNotInTournament(@PathVariable Long userId){
        List<TeamDTO> availableTeams = teamService.findAllTeamsNotInTournament(userId);
        return ResponseEntity.ok(availableTeams);
    }

    @GetMapping("/all/not-in-league/{userId}")
    public ResponseEntity<List<TeamDTO>> findAllTeamsNotInLeague(@PathVariable Long userId){
        List<TeamDTO> availableTeams = teamService.findAllTeamsNotInLeague(userId);
        return ResponseEntity.ok(availableTeams);
    }

    @PutMapping("/updateIsInTournament/{teamId}")
    public ResponseEntity<TeamDTO> updateIsInTournament(@PathVariable Long teamId, @RequestBody boolean isInTournament){
        Team updatedTeam = teamService.updateIsInTournament(teamId, isInTournament);
        TeamDTO updatedTeamDTO = teamService.convertToDTO(updatedTeam);
        return ResponseEntity.ok(updatedTeamDTO);
    }

    @PutMapping("/updateIsInLeague/{teamId}")
    public ResponseEntity<TeamDTO> updateIsInLeague(@PathVariable Long teamId, @RequestBody boolean isInLeague){
        Team updatedTeam = teamService.updateIsInLeague(teamId, isInLeague);
        TeamDTO updatedTeamDTO = teamService.convertToDTO(updatedTeam);
        return ResponseEntity.ok(updatedTeamDTO);
    }

    @GetMapping("/all/tournament/{tournamentId}")
    public ResponseEntity<List<TeamDTO>> findAllTeamsInTournamentByTournamentId(@PathVariable Long tournamentId){
        List<TeamDTO> tournamentTeams = teamService.findAllTeamsInTournamentByTournamentId(tournamentId);
        return  ResponseEntity.ok(tournamentTeams);
    }

    @GetMapping("/all/league/{leagueId}")
    public ResponseEntity<List<TeamDTO>> findAllTeamsInLeagueByLeagueId(@PathVariable Long leagueId){
        List<TeamDTO> leagueTeams = teamService.findAllTeamsInLeagueByLeagueId(leagueId);
        return  ResponseEntity.ok(leagueTeams);
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
