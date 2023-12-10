package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.TournamentStandingDTO;
import com.mp.footballtournamentsandleaguesmanager.model.TournamentStanding;
import com.mp.footballtournamentsandleaguesmanager.service.TournamentStandingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournamentStanding")
public class TournamentStandingController {
    private final TournamentStandingService tournamentStandingService;

    @Autowired
    public TournamentStandingController(TournamentStandingService tournamentStandingService) {
        this.tournamentStandingService = tournamentStandingService;
    }

    @PostMapping("/add")
    public ResponseEntity<List<TournamentStanding>> addTournamentStanding(@RequestBody List<TournamentStanding> tournamentStanding) {
        List<TournamentStanding> newTournamentStanding = this.tournamentStandingService.addTournamentStanding(tournamentStanding);
        return new ResponseEntity<>(newTournamentStanding, HttpStatus.OK);
    }

    @GetMapping("/all/tournament/{tournamentId}/group/{groupId}")
    public ResponseEntity<List<TournamentStandingDTO>> getTournamentStandingByTournamentIdAndGroupId(@PathVariable Long tournamentId,
                                                                                                     @PathVariable int groupId){
        List<TournamentStandingDTO> tournamentStandingDTOList = this.tournamentStandingService.getTournamentStandingByTournamentIdAndGroupId(tournamentId, groupId);
        return ResponseEntity.ok(tournamentStandingDTOList);
    }

    @PutMapping("/update/tournament/{tournamentId}/group/{groupId}/team/{teamId}")
    public ResponseEntity<TournamentStandingDTO> updateTournamentStanding(@PathVariable Long tournamentId,
                                                                          @PathVariable int groupId,
                                                                          @PathVariable Long teamId,
                                                                          @RequestBody TournamentStandingDTO tournamentStandingDTO){
        TournamentStanding updatedTournamentStanding = tournamentStandingService.updateTournamentStanding(tournamentId, groupId, teamId, tournamentStandingDTO);
        TournamentStandingDTO updatedTournamentStandingDTO = tournamentStandingService.convertToDTO(updatedTournamentStanding);
        return ResponseEntity.ok(updatedTournamentStandingDTO);
    }
}
