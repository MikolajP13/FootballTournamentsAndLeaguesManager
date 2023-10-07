package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.CardDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Card;
import com.mp.footballtournamentsandleaguesmanager.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/add")
    public ResponseEntity<Card> addCard(@RequestBody  Card card){
        Card newCard = cardService.addCard(card);
        return new ResponseEntity<>(newCard, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{cardId}")
    public ResponseEntity<Boolean> deleteCardById(@PathVariable Long cardId){
        return cardService.deleteCardById(cardId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{cardId}")
    public ResponseEntity<CardDTO> updateCardById(@PathVariable Long cardId, @RequestBody CardDTO cardDTO){
        Card updatedCard = cardService.updateCardById(cardId, cardDTO);
        CardDTO updatedCardDTO = cardService.convertToDTO(updatedCard);
        return ResponseEntity.ok(updatedCardDTO);
    }

    @GetMapping("findAll/player/{playerId}")
    public ResponseEntity<List<CardDTO>> getAllByPlayerId(@PathVariable Long playerId){
        return new ResponseEntity<>(cardService.getAllByPlayerId(playerId), HttpStatus.OK);
    }

    @GetMapping("/findAll/match/{matchId}")
    public ResponseEntity<List<CardDTO>> getAllByMatchId(@PathVariable Long matchId){
        return new ResponseEntity<>(cardService.getAllByMatchId(matchId), HttpStatus.OK);
    }

    @GetMapping("/count/player/{playerId}")
    public ResponseEntity<Integer> countCardsByPlayerId(@PathVariable Long playerId){
        return new ResponseEntity<>(cardService.countCardsByPlayerId(playerId), HttpStatus.OK);
    }
}
