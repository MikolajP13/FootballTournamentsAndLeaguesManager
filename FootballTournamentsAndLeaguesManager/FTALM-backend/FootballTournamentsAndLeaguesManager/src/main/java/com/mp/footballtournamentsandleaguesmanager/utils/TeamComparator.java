package com.mp.footballtournamentsandleaguesmanager.utils;

import com.mp.footballtournamentsandleaguesmanager.DTO.MatchDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.TeamCardsDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Standing;
import com.mp.footballtournamentsandleaguesmanager.service.CardService;
import com.mp.footballtournamentsandleaguesmanager.service.MatchService;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.Random;

@AllArgsConstructor
public class TeamComparator<T extends Standing> implements Comparator<T> {
    private final Long competitionId;
    private final static Random random = new Random();
    private final MatchService matchService;
    private final CardService cardService;

    @Override
    public int compare(T firstTeam, T secondTeam) {
        int firstTeamPoints = 0;
        int firstTeamGoalsFirstMatch = 0;
        int firstTeamGoalsSecondMatch = 0;
        Long firstTeamCardPoints;
        int secondTeamPoints = 0;
        int secondTeamGoalsFirstMatch = 0;
        int secondTeamGoalsSecondMatch = 0;
        Long secondTeamCardPoints;

        // 1. points determines the team placement
        if (firstTeam.getPoints() < secondTeam.getPoints()) return 1;
        else if (firstTeam.getPoints() > secondTeam.getPoints()) return -1;

        // 2. equal points, check match/es between these two teams
        boolean protocolCreated = true;
        MatchDTO match1 = this.matchService
                .getMatchByHomeTeamIdAndAwayTeamIdAndMatchProtocolCreatedAndLeagueId(firstTeam.getTeam().getId(),
                        secondTeam.getTeam().getId(), protocolCreated, competitionId);
        MatchDTO match2 = this.matchService
                .getMatchByHomeTeamIdAndAwayTeamIdAndMatchProtocolCreatedAndLeagueId(secondTeam.getTeam().getId(),
                        firstTeam.getTeam().getId(), protocolCreated, competitionId);

        if(match1 != null || match2 != null) {
            if (match1 != null) {
                firstTeamGoalsFirstMatch = match1.getHomeTeamScore();
                secondTeamGoalsFirstMatch = match1.getAwayTeamScore();
                if (firstTeamGoalsFirstMatch > secondTeamGoalsFirstMatch) firstTeamPoints += 3;
                else if (firstTeamGoalsFirstMatch < secondTeamGoalsFirstMatch) secondTeamPoints += 3;
                else {
                    firstTeamPoints += 1;
                    secondTeamPoints += 1;
                }
            }

            if (match2 != null) {
                firstTeamGoalsSecondMatch = match2.getAwayTeamScore();
                secondTeamGoalsSecondMatch = match2.getHomeTeamScore();
                if (firstTeamGoalsSecondMatch > secondTeamGoalsSecondMatch) firstTeamPoints += 3;
                else if (firstTeamGoalsSecondMatch < secondTeamGoalsSecondMatch) secondTeamPoints += 3;
                else {
                    firstTeamPoints += 1;
                    secondTeamPoints += 1;
                }
            }

            if (firstTeamPoints > 0 && secondTeamPoints > 0) {
                // 2.1 points in direct match/es determines the team placement
                if (firstTeamPoints < secondTeamPoints) return 1;

                // 2.2 goals in direct match/es determines the team placement
                if ((firstTeamGoalsFirstMatch + firstTeamGoalsSecondMatch) < (secondTeamGoalsFirstMatch + secondTeamGoalsSecondMatch))
                    return 1;
            }
        }
        // the teams haven't played each other yet
        // 3. goals difference determines the team placement
        if (firstTeam.getGoalsFor() - firstTeam.getGoalsAgainst() < secondTeam.getGoalsFor() - secondTeam.getGoalsAgainst()) return 1;
        else if (firstTeam.getGoalsFor() - firstTeam.getGoalsAgainst() > secondTeam.getGoalsFor() - secondTeam.getGoalsAgainst()) return -1;

        // 4. goals for determines the team placement
        if (firstTeam.getGoalsFor() < secondTeam.getGoalsFor()) return 1;
        else if(firstTeam.getGoalsFor() > secondTeam.getGoalsFor()) return -1;

        // 5. wins determines the team placement
        if (firstTeam.getWins() < secondTeam.getWins()) return 1;
        else if (firstTeam.getWins() > secondTeam.getWins()) return -1;

        // 6. fair play system determines the team placement (red card = 3 points, yellow card = 1 point)
        TeamCardsDTO firstTeamCardResult = cardService.getCardsOverallByLeagueIdAndTeamId(competitionId, firstTeam.getTeam().getId());
        TeamCardsDTO secondTeamCardResult = cardService.getCardsOverallByLeagueIdAndTeamId(competitionId, secondTeam.getTeam().getId());

        firstTeamCardPoints = (firstTeamCardResult.getRedCards() * 3) + firstTeamCardResult.getYellowCards();
        secondTeamCardPoints = (secondTeamCardResult.getRedCards() * 3) + secondTeamCardResult.getYellowCards();

        if (firstTeamCardPoints > secondTeamCardPoints) return 1;
        else if (firstTeamCardPoints < secondTeamCardPoints) return -1;

        // draw
        return random.nextInt(10 - 1) + 1 < 5 ? 1 : -1;
    }

}
