package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.AssistDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Assist;
import com.mp.footballtournamentsandleaguesmanager.service.AssistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assist")
public class AssistController {
    private final AssistService assistService;

    @Autowired
    public AssistController(AssistService assistService) {
        this.assistService = assistService;
    }

    @PostMapping("/add")
    public ResponseEntity<Assist> addAssist(@RequestBody Assist assist){
        Assist newAssist = assistService.addAssists(assist);
        return new ResponseEntity<>(newAssist, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{assistId}")
    public ResponseEntity<Boolean> deleteAssistById(@PathVariable Long assistId){
        return assistService.deleteAssistById(assistId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{assistId}")
    public ResponseEntity<AssistDTO> updateAssistById(@PathVariable Long assistId, @RequestBody AssistDTO assistDTO){
        Assist updatedAssist = assistService.updateAssistById(assistId, assistDTO);
        AssistDTO updatedAssistDTO = assistService.convertToDTO(updatedAssist);
        return ResponseEntity.ok(updatedAssistDTO);
    }

    @GetMapping("/findAll/player/{playerId}")
    public ResponseEntity<List<AssistDTO>> getAllByPlayerId(@PathVariable Long playerId){
        return new ResponseEntity<>(assistService.getAllByPlayerId(playerId), HttpStatus.OK);
    }

    @GetMapping("/findAll/match/{matchId}")
    public ResponseEntity<List<AssistDTO>> getAllByMatchId(@PathVariable Long matchId){
        return new ResponseEntity<>(assistService.getAllByMatchId(matchId), HttpStatus.OK);
    }

    @GetMapping("/count/player/{playerId}")
    public ResponseEntity<Integer> countAssistByPlayerId(@PathVariable Long playerId){
        return new ResponseEntity<>(assistService.countAssistsByPlayerId(playerId), HttpStatus.OK);
    }
}
