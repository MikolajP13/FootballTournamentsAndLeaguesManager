package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.TournamentDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Tournament;
import com.mp.footballtournamentsandleaguesmanager.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournament")
public class TournamentController {
    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping("/find/{tournamentId}")
    public ResponseEntity<TournamentDTO> getTournamentById(@PathVariable Long tournamentId){
        return new ResponseEntity<>(tournamentService.getTournamentById(tournamentId), HttpStatus.OK);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<TournamentDTO>> getAllByUserId(@PathVariable Long userId){
       return ResponseEntity.ok(tournamentService.getAllByUserId(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<Tournament> addTournament(@RequestBody Tournament tournament){
        Tournament newTournament = tournamentService.addTournament(tournament);
        return new ResponseEntity<>(newTournament, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{tournamentId}")
    public ResponseEntity<String> deleteTournamentById(@PathVariable Long tournamentId){
        return tournamentService.deleteTournamentById(tournamentId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
