package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.model.*;
import com.mp.footballtournamentsandleaguesmanager.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TeamsMatchService {
    private final TeamsMatchRepository teamsMatchRepository;
    private final MatchRepository matchRepository;
    private final TournamentRepository tournamentRepository;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;

    public Boolean addTeamsToMatchInCompetition(Long matchId, Long homeTeamId, Long awayTeamId, Long tournamentId, Long leagueId, boolean isLeagueMatch) {
        TeamsMatch newTeamsMatch = new TeamsMatch();
        Optional<Match> matchOptional = matchRepository.findById(matchId);
        Optional<Team> homeTeamOptional = teamRepository.findById(homeTeamId);
        Optional<Team> awayTeamOptional = teamRepository.findById(awayTeamId);

        if(matchOptional.isPresent() && homeTeamOptional.isPresent() && awayTeamOptional.isPresent()) {
            Match match = matchOptional.get();
            Team homeTeam = homeTeamOptional.get();
            Team awayTeam = awayTeamOptional.get();

            newTeamsMatch.setMatch(match);
            newTeamsMatch.setHomeTeam(homeTeam);
            newTeamsMatch.setAwayTeam(awayTeam);

            if(isLeagueMatch){
                League league = leagueRepository.findById(leagueId).orElse(null);
                if (league == null) return false;
                newTeamsMatch.setLeague(league);
            }else {
                Tournament tournament = tournamentRepository.findById(tournamentId).orElse(null);
                if (tournament == null) return false;
                newTeamsMatch.setTournament(tournament);
            }
            teamsMatchRepository.save(newTeamsMatch);
            return true;
        }
        return false;
    }

}

