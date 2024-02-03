package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.TournamentDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.TournamentTeamDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Tournament;
import com.mp.footballtournamentsandleaguesmanager.model.TournamentLeagueBase;
import com.mp.footballtournamentsandleaguesmanager.service.TournamentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tournament")
public class TournamentController {
    private final TournamentService tournamentService;

    @GetMapping("/find/{tournamentId}")
    public ResponseEntity<TournamentDTO> getTournamentById(@PathVariable Long tournamentId){
        return new ResponseEntity<>(tournamentService.getTournamentById(tournamentId), HttpStatus.OK);
    }

    @GetMapping("/active/{teamId}")
    public ResponseEntity<TournamentDTO> findActiveTournamentForTeam(@PathVariable Long teamId){
        TournamentDTO tournament = tournamentService.findActiveTournamentForTeam(teamId);
        return new ResponseEntity<>(tournament, HttpStatus.OK);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<TournamentDTO>> getAllByUserId(@PathVariable Long userId){
       return ResponseEntity.ok(tournamentService.getAllByUserId(userId));
    }

    @GetMapping("/getStatus/{tournamentId}")
    public ResponseEntity<TournamentLeagueBase.Status> getTournamentStatusByTournamentId(@PathVariable Long tournamentId){
        return new ResponseEntity<>(tournamentService.getTournamentStatusByTournamentId(tournamentId), HttpStatus.OK);
    }

    @GetMapping("/getNumberOfTeams/{tournamentId}")
    public ResponseEntity<Integer> getNumberOfTeamsByTournamentId(@PathVariable Long tournamentId){
        return ResponseEntity.ok(tournamentService.getNumberOfTeamsByTournamentId(tournamentId));
    }

    @GetMapping("/getType/{tournamentId}")
    public ResponseEntity<Tournament.TournamentType> getTournamentTypeByTournamentId(@PathVariable Long tournamentId){
        return new ResponseEntity<>(tournamentService.getTournamentTypeByTournamentId(tournamentId), HttpStatus.OK);
    }

    @PutMapping("/updateStatus/{tournamentId}")
    public ResponseEntity<TournamentDTO> updateLeagueStatusByLeagueId(@PathVariable Long tournamentId, @RequestBody Tournament tournament){
        Tournament updatedLeague = tournamentService.updateTournamentStatusByTournamentId(tournamentId, tournament.getStatus());
        TournamentDTO updatedTournamentDTO = tournamentService.convertToDTO(updatedLeague);
        return ResponseEntity.ok(updatedTournamentDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<Tournament> addTournament(@RequestBody Tournament tournament){
        Tournament newTournament = tournamentService.addTournament(tournament);
        return new ResponseEntity<>(newTournament, HttpStatus.CREATED);
    }

    @PostMapping("/addTeam")
    public ResponseEntity<String> addTeamToTournamentByIds(@RequestBody TournamentTeamDTO tournamentTeamDTO){
        return tournamentService.addTeamToTournamentByIds(tournamentTeamDTO.getTeamId(), tournamentTeamDTO.getTournamentId()) ?
                ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{tournamentId}")
    public ResponseEntity<String> deleteTournamentById(@PathVariable Long tournamentId){
        return tournamentService.deleteTournamentById(tournamentId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
