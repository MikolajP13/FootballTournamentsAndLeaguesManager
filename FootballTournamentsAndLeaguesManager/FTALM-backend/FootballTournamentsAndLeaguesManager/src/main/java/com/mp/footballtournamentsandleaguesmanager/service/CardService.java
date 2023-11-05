package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.CardDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.PlayerCardsDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.TeamCardsDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Card;
import com.mp.footballtournamentsandleaguesmanager.model.Team;
import com.mp.footballtournamentsandleaguesmanager.repository.CardRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.PlayerRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public CardService(CardRepository cardRepository, PlayerRepository playerRepository,
                       TeamRepository teamRepository) {
        this.cardRepository = cardRepository;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public Card addCard(Card card){
        return cardRepository.save(card);
    }
    public Boolean deleteCardById(Long cardId){
        if(cardRepository.existsById(cardId)){
            cardRepository.deleteById(cardId);
            return true;
        }else{
            return false;
        }
    }

    public Card updateCardById(Long cardId, CardDTO cardDTO){
        Card cardToUpdate = this.cardRepository.findById(cardId).orElseThrow();
        cardToUpdate.setMinute(cardDTO.getMinute());
        cardToUpdate.setCardType(cardDTO.getCardType());
        cardToUpdate.setPlayer(playerRepository.findById(cardDTO.getPlayerId()).orElseThrow());
        return cardRepository.save(cardToUpdate);
    }
    public List<CardDTO> getAllByPlayerId(Long playerId){
        Optional<List<Card>> optionalCardList = cardRepository.getAllByPlayerId(playerId);
        List<Card> cardList = optionalCardList.orElse(Collections.emptyList());
        return cardList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<CardDTO> getAllByMatchId(Long matchId){
        Optional<List<Card>> optionalCardList = cardRepository.getAllByMatchId(matchId);
        List<Card> cardList = optionalCardList.orElse(Collections.emptyList());
        return cardList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public int countCardsByPlayerId(Long playerId){
        Optional<Integer> optionalPlayerAssists = cardRepository.countCardsByPlayerId(playerId);
        return optionalPlayerAssists.orElse(0);
    }

    public int countCardsByPlayerIdAndCardType(Long playerId, Card.Type cardType){
        Optional<Integer> optionalPlayerAssists = cardRepository.countCardsByPlayerIdAndCardType(playerId, cardType);
        return optionalPlayerAssists.orElse(0);
    }

    public List<PlayerCardsDTO> getPlayersYellowCardsByLeagueId(Long leagueId){
        Optional<List<PlayerCardsDTO>> optionalPlayerCardsDTOList = cardRepository.getPlayersYellowCardsByLeagueId(leagueId);
        return optionalPlayerCardsDTOList.orElse(Collections.emptyList());
    }

    public List<PlayerCardsDTO> getPlayersRedCardsByLeagueId(Long leagueId){
        Optional<List<PlayerCardsDTO>> optionalPlayerCardsDTOList = cardRepository.getPlayersRedCardsByLeagueId(leagueId);
        return optionalPlayerCardsDTOList.orElse(Collections.emptyList());
    }

    public TeamCardsDTO getCardsOverallByLeagueIdAndTeamId(Long leagueId, Long teamId){
        Optional<TeamCardsDTO> optionalTeamCardsDTO = cardRepository.getCardsOverallByLeagueIdAndTeamId(leagueId, teamId);
        Team team = teamRepository.findById(teamId).orElseThrow();
        return optionalTeamCardsDTO.orElse(new TeamCardsDTO(team.getId(), team.getName(), 0l, 0l));
    }

    public CardDTO convertToDTO(Card card){
        CardDTO dto = new CardDTO();
        dto.setId(card.getId());
        dto.setMatchId(card.getMatch().getId());
        dto.setTeamId(card.getTeam().getId());
        dto.setMinute(card.getMinute());
        dto.setPlayerId(card.getPlayer().getId());
        dto.setPlayerFirstName(card.getPlayer().getFirstName());
        dto.setPlayerLastName(card.getPlayer().getLastName());
        dto.setCardType(card.getCardType());

        return dto;
    }
}
