package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.CardDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.PlayerCardsDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.TeamCardsDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Card;
import com.mp.footballtournamentsandleaguesmanager.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/card")
public class CardController {
    private final CardService cardService;

    @PostMapping("/add")
    public ResponseEntity<Card> addCard(@RequestBody  Card card){
        Card newCard = cardService.addCard(card);
        return new ResponseEntity<>(newCard, HttpStatus.CREATED);
    }

    @PostMapping("/addAll")
    public ResponseEntity<List<Card>> addCards(@RequestBody List<Card> cardList) {
        List<Card> cards = cardService.addCards(cardList);
        return new ResponseEntity<>(cards, HttpStatus.CREATED);
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

    @GetMapping("/countAll/player/{playerId}")
    public ResponseEntity<Integer> countCardsByPlayerId(@PathVariable Long playerId){
        return new ResponseEntity<>(cardService.countCardsByPlayerId(playerId), HttpStatus.OK);
    }

    @GetMapping("/countSpecific/player/{playerId}")
    public ResponseEntity<Integer> countCardsByPlayerIdAndCardType(@PathVariable Long playerId, @RequestParam String cardTypeString){
        Card.Type cardType = Card.Type.valueOf(cardTypeString.toUpperCase());
        return new ResponseEntity<>(cardService.countCardsByPlayerIdAndCardType(playerId, cardType), HttpStatus.OK);
    }

    @GetMapping("/getPlayersYellowCards/league/{leagueId}")
    public ResponseEntity<List<PlayerCardsDTO>> getPlayersYellowCardsByLeagueId(@PathVariable Long leagueId){
        return new ResponseEntity<>(cardService.getPlayersYellowCardsByLeagueId(leagueId), HttpStatus.OK);
    }

    @GetMapping("/getPlayersRedCards/league/{leagueId}")
    public ResponseEntity<List<PlayerCardsDTO>> getPlayersRedCardsByLeagueId(@PathVariable Long leagueId){
        return new ResponseEntity<>(cardService.getPlayersRedCardsByLeagueId(leagueId), HttpStatus.OK);
    }

    @GetMapping("/getPlayersYellowCards/tournament/{tournamentId}")
    public ResponseEntity<List<PlayerCardsDTO>> getPlayersYellowCardsByTournamentId(@PathVariable Long tournamentId){
        return new ResponseEntity<>(cardService.getPlayersYellowCardsByTournamentId(tournamentId), HttpStatus.OK);
    }

    @GetMapping("/getPlayersRedCards/tournament/{tournamentId}")
    public ResponseEntity<List<PlayerCardsDTO>> getPlayersRedCardsByTournamentId(@PathVariable Long tournamentId){
        return new ResponseEntity<>(cardService.getPlayersRedCardsByTournamentId(tournamentId), HttpStatus.OK);
    }

    @GetMapping("/getTeamCards/league/{leagueId}/team/{teamId}")
    public ResponseEntity<TeamCardsDTO> getCardsOverallByLeagueIdAndTeamId(@PathVariable Long leagueId, @PathVariable Long teamId){
        return new ResponseEntity<>(cardService.getCardsOverallByLeagueIdAndTeamId(leagueId, teamId), HttpStatus.OK);
    }

    @GetMapping("/getPlayerCard/{playerId}/match/{matchId}")
    public ResponseEntity<CardDTO> getCardByPlayerIdAndMatchId(@PathVariable Long playerId, @PathVariable Long matchId){
        return new ResponseEntity<>(cardService.getCardByPlayerIdAndMatchId(playerId, matchId), HttpStatus.OK);
    }
}
