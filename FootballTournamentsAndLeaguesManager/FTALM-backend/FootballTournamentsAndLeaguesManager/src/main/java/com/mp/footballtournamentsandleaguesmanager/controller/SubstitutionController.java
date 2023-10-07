package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.SubstitutionDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Substitution;
import com.mp.footballtournamentsandleaguesmanager.service.SubstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/substitution")
public class SubstitutionController {
    private final SubstitutionService substitutionService;

    @Autowired
    public SubstitutionController(SubstitutionService substitutionService) {
        this.substitutionService = substitutionService;
    }

    @PostMapping("/add")
    public ResponseEntity<Substitution> addSubstitution(@RequestBody Substitution substitution){
        Substitution newSubstitution = substitutionService.addSubstitution(substitution);
        return new ResponseEntity<>(newSubstitution, HttpStatus.CREATED);
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
}
