package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.SubstitutionDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Substitution;
import com.mp.footballtournamentsandleaguesmanager.service.SubstitutionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/substitution")
public class SubstitutionController {
    private final SubstitutionService substitutionService;

    @PostMapping("/add")
    public ResponseEntity<Substitution> addSubstitution(@RequestBody Substitution substitution){
        Substitution newSubstitution = substitutionService.addSubstitution(substitution);
        return new ResponseEntity<>(newSubstitution, HttpStatus.CREATED);
    }

    @PostMapping("/addAll")
    public ResponseEntity<List<Substitution>> addSubstitutions(@RequestBody List<Substitution> substitutionList){
        List<Substitution> substitutions = substitutionService.addSubstitutions(substitutionList);
        return new ResponseEntity<>(substitutions, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{substitutionId}")
    public ResponseEntity<Boolean> deleteSubstitutionById(@PathVariable Long substitutionId){
        return substitutionService.deleteSubstitutionById(substitutionId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{substitutionId}")
    public ResponseEntity<SubstitutionDTO> updateSubstitutionById(@PathVariable Long substitutionId, @RequestBody SubstitutionDTO substitutionDTO){
        Substitution updatedSubstitution = substitutionService.updateSubstitutionById(substitutionId, substitutionDTO);
        SubstitutionDTO updatedSubstitutionDTO = substitutionService.convertToDTO(updatedSubstitution);
        return ResponseEntity.ok(updatedSubstitutionDTO);
    }

    @GetMapping("/findAll/match/{matchId}")
    public ResponseEntity<List<SubstitutionDTO>> getAllByMatchId(@PathVariable Long matchId){
        return new ResponseEntity<>(substitutionService.getAllByMatchId(matchId), HttpStatus.OK);
    }

    @GetMapping("findAll/match/{matchId}/team/{teamId}")
    public ResponseEntity<List<SubstitutionDTO>> getAllByMatchIdAndTeamId(@PathVariable Long matchId, @PathVariable Long teamId){
        return new ResponseEntity<>(substitutionService.getAllByMatchIdAndTeamId(matchId, teamId), HttpStatus.OK);
    }

    @GetMapping("countAll/match/{matchId}/team/{teamId}")
    public ResponseEntity<Integer> countSubstitutionsByMatchIdAndTeamId(@PathVariable Long matchId, @PathVariable Long teamId){
        return new ResponseEntity<>(substitutionService.countSubstitutionsByMatchIdAndTeamId(matchId, teamId), HttpStatus.OK);
    }
 }
