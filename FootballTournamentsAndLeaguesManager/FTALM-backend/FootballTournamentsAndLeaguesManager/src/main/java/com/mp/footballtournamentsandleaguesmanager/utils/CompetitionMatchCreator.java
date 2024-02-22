package com.mp.footballtournamentsandleaguesmanager.utils;

import com.mp.footballtournamentsandleaguesmanager.model.*;
import lombok.Getter;

import java.util.*;

public class CompetitionMatchCreator {

    private static final String BLANK_TEAM_NAME = "BlankTeam";

    public static List<Match> createMatchesForLeague(League league, List<Team> teams, boolean isRematch, boolean isAfterSplit, int numberOfTeams) {
        // shuffle teams
        Collections.shuffle(teams);

        // create league fixture
        return generateRoundRobinSchedule(league, teams, isRematch, 0, isAfterSplit, numberOfTeams);
    }

    public static List<Match> createMatchesForTournament(Tournament tournament, List<Team> teams) {
        // shuffle teams
        Collections.shuffle(teams);

        // create tournament fixture
        return generateRoundRobinSchedule(tournament, teams, false, 0, false, 0);
    }

    public static List<LeagueStanding> createLeagueStanding(League league, List<Team> teams) {
        List<LeagueStanding> leagueStanding = new ArrayList<>();

        for (Team team : teams) {
            LeagueStanding ls = new LeagueStanding();
            ls.setLeague(league);
            ls.setTeam(team);
            ls.setMatches(0);
            ls.setPoints(0);
            ls.setGoalsFor(0);
            ls.setGoalsAgainst(0);
            ls.setWins(0);
            ls.setDraws(0);
            ls.setLosses(0);
            ls.setLeagueStandingType(LeagueStanding.LeagueStandingType.NORMAL);
            leagueStanding.add(ls);
        }

        return leagueStanding;
    }

    public static List<Match> createTournamentPairMatches(Tournament tournament, List<Team> teams, int roundNo, boolean shuffle) {
        List<Match> matches = new ArrayList<>();

        if (shuffle)
            Collections.shuffle(teams);

        for (int i = 0; i < teams.size(); i+=2) {
            Match match = new Match();
            match.setTournament(tournament);
            match.setHomeTeamId(teams.get(i).getId());
            match.setAwayTeamId(teams.get(i+1).getId());
            match.setHomeTeamScore(null);
            match.setAwayTeamScore(null);
            match.setMatchweek(0);
            match.setRound(roundNo);
            match.setMatchProtocolCreated(false);

            matches.add(match);
        }
        return matches;
    }

    public static TournamentStandingAndMatches createTournamentStandingAndMatches(Tournament tournament, List<Team> teams) {
        TournamentStandingAndMatches tournamentStandingAndMatches = new TournamentStandingAndMatches();
        List<TournamentStanding> tournamentStanding = new ArrayList<>();
        List<Match> matches = new ArrayList<>();
        int numberOfGroups = (int) (teams.size() / 4.0);
        Collections.shuffle(teams);

        for (int i = 0; i < numberOfGroups; i++) {
            List<Team> groupTeams = new ArrayList<>();

            for (int j = 0; j < 4; j++) {
                Team team = teams.get(i*4+j);
                TournamentStanding ts = new TournamentStanding();
                ts.setTournament(tournament);
                ts.setTeam(team);
                ts.setMatches(0);
                ts.setPoints(0);
                ts.setGoalsAgainst(0);
                ts.setGoalsFor(0);
                ts.setWins(0);
                ts.setDraws(0);
                ts.setLosses(0);
                ts.setGroupId(i);
                tournamentStanding.add(ts);
                groupTeams.add(team);
            }

            matches.addAll(generateRoundRobinSchedule(tournament, groupTeams, false, i, false, 0));
        }

        tournamentStandingAndMatches.setMatches(matches);
        tournamentStandingAndMatches.setTournamentStandings(tournamentStanding);

        return tournamentStandingAndMatches;
    }

    private static List<Match> generateRoundRobinSchedule(TournamentLeagueBase competition, List<Team> teams, boolean isRematch, int groupId, boolean isAfterSplit, int numberOfTeams) {
        if (teams.size() % 2 != 0) {
            teams.add(new Team(BLANK_TEAM_NAME));
        }

        int numberOfRounds = teams.size() - 1;
        int matchesPerRound = teams.size() / 2;
        boolean alternate = true;

        List<Integer> offsetArray = generateOffsetArray(teams.size());

        List<Fixture> firstHalfSeasonFixtures = generateFixtures(0, numberOfRounds, matchesPerRound, alternate, offsetArray, teams);
        List<Fixture> secondHalfSeasonFixtures = new ArrayList<>();

        if (isRematch)
            secondHalfSeasonFixtures = generateFixtures(teams.size() - 1, numberOfRounds, matchesPerRound, alternate, offsetArray, teams);

        List<Fixture> allFixtures = new ArrayList<>(firstHalfSeasonFixtures);
        allFixtures.addAll(secondHalfSeasonFixtures);

        return outputFixtures(allFixtures, competition, groupId, isAfterSplit, numberOfTeams);
    }

    private static List<Fixture> generateFixtures(int roundNoOffset, int numberOfRounds, int matchesPerRound, boolean alternate, List<Integer> offsetArray, List<Team> teams) {
        List<Fixture> fixtures = new ArrayList<>();

        for (int roundNo = 1; roundNo <= numberOfRounds; roundNo++) {
            alternate = !alternate;

            List<Integer> homes = getHomes(roundNo, teams.size(), offsetArray, matchesPerRound);
            List<Integer> aways = getAways(roundNo, teams.size(), offsetArray, matchesPerRound);

            for (int matchIndex = 0; matchIndex < matchesPerRound; matchIndex++) {
                int homeTeamId = homes.get(matchIndex);
                int awayTeamId = aways.get(matchIndex);
                Team homeTeam = teams.get(homeTeamId);
                Team awayTeam = teams.get(awayTeamId);

                if (homeTeamId == awayTeamId) {
                    System.err.println("Teams cannot play themselves");
                }

                if (alternate) {
                    fixtures.add(new Fixture(roundNo + roundNoOffset, matchIndex, homeTeamId, awayTeamId, homeTeam, awayTeam));
                } else {
                    fixtures.add(new Fixture(roundNo + roundNoOffset, matchIndex, homeTeamId, awayTeamId, awayTeam, homeTeam));
                }
            }
        }

        return fixtures;
    }

    private static List<Match> outputFixtures(List<Fixture> fixtures, TournamentLeagueBase competition, int groupId, boolean isAfterSplit, int numberOfTeams) {
        int roundNo = 0;
        int startRoundAfterSplit = 0;
        List<Match> matches = new ArrayList<>();

        if (isAfterSplit) {
            if (numberOfTeams %2 == 0)
                numberOfTeams -= 1; // number of opponents

            startRoundAfterSplit = (numberOfTeams*2); // calculate next start round after split
        }

        for (int fixtureId = 0; fixtureId < fixtures.size(); fixtureId++) {
            Fixture fixture = fixtures.get(fixtureId);

            if (fixture.getRoundNo() > roundNo) {
                roundNo = fixture.getRoundNo();
            }

            if (competition instanceof League league) {
                Match match = new Match();
                if (fixtureId < fixtures.size() / 2) {
                    match.setHomeTeamId(fixture.getHomeTeam().getId());
                    match.setAwayTeamId(fixture.getAwayTeam().getId());
                } else {
                    match.setHomeTeamId(fixture.getAwayTeam().getId());
                    match.setAwayTeamId(fixture.getHomeTeam().getId());
                }
                match.setHomeTeamScore(null);
                match.setAwayTeamScore(null);
                match.setLeague(league);
                if (isAfterSplit) {
                    match.setMatchweek(roundNo + startRoundAfterSplit);
                    match.setRound(1);
                } else {
                    match.setMatchweek(roundNo);
                    match.setRound(0);
                }
                match.setMatchProtocolCreated(false);
                matches.add(match);
            } else if (competition instanceof Tournament tournament) {
                Match match = new Match();
                if (fixtureId < fixtures.size() / 2) {
                    match.setHomeTeamId(fixture.getHomeTeam().getId());
                    match.setAwayTeamId(fixture.getAwayTeam().getId());
                } else {
                    match.setHomeTeamId(fixture.getAwayTeam().getId());
                    match.setAwayTeamId(fixture.getHomeTeam().getId());
                }
                match.setHomeTeamScore(null);
                match.setAwayTeamScore(null);
                match.setTournament(tournament);
                match.setMatchweek(groupId);
                if (isAfterSplit) {
                    match.setRound(1);
                } else {
                    match.setRound(0);
                }
                match.setMatchProtocolCreated(false);
                matches.add(match);
            }
        }

        return matches;
    }

    private static List<Integer> generateOffsetArray(int teamsCount) {
        List<Integer> offsetArray = new ArrayList<>();

        for (int i = 1; i < teamsCount; i++) {
            offsetArray.add(i);
        }

        offsetArray.addAll(offsetArray);
        return offsetArray;
    }

    private static List<Integer> getHomes(int roundNo, int teamsCount, List<Integer> offsetArray, int matchesPerRound) {
        int offset = teamsCount - roundNo;
        List<Integer> homes = offsetArray.subList(offset, offset + matchesPerRound - 1);

        List<Integer> result = new ArrayList<>();
        result.add(0);
        result.addAll(homes);
        return result;
    }

    private static List<Integer> getAways(int roundNo, int teamsCount, List<Integer> offsetArray, int matchesPerRound) {
        int offset = teamsCount - roundNo + matchesPerRound - 1;
        List<Integer> aways = offsetArray.subList(offset, offset + matchesPerRound);

        List<Integer> result = new ArrayList<>(aways);
        return reverseList(result);
    }

    private static <T> List<T> reverseList(List<T> list) {
        List<T> reversedList = new ArrayList<>(list);
        java.util.Collections.reverse(reversedList);
        return reversedList;
    }

    public static class TournamentStandingAndMatches {
        private List<TournamentStanding> tournamentStandings;
        private List<Match> matches;

        public List<TournamentStanding> getTournamentStandings() {
            return tournamentStandings;
        }

        public void setTournamentStandings(List<TournamentStanding> tournamentStandings) {
            this.tournamentStandings = tournamentStandings;
        }

        public List<Match> getMatches() {
            return matches;
        }

        public void setMatches(List<Match> matches) {
            this.matches = matches;
        }
    }

    static class Fixture {
        private int roundNo;
        private int matchNo;
        private int homeTeamId;
        private int awayTeamId;
        private Team homeTeam;
        private Team awayTeam;

        public Fixture(int roundNo, int matchNo, int homeTeamId, int awayTeamId, Team homeTeam, Team awayTeam) {
            this.roundNo = roundNo;
            this.matchNo = matchNo;
            this.homeTeamId = homeTeamId;
            this.awayTeamId = awayTeamId;
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
        }

        public int getRoundNo() {
            return roundNo;
        }

        public void setRoundNo(int roundNo) {
            this.roundNo = roundNo;
        }

        public int getMatchNo() {
            return matchNo;
        }

        public void setMatchNo(int matchNo) {
            this.matchNo = matchNo;
        }

        public int getHomeTeamId() {
            return homeTeamId;
        }

        public void setHomeTeamId(int homeTeamId) {
            this.homeTeamId = homeTeamId;
        }

        public int getAwayTeamId() {
            return awayTeamId;
        }

        public void setAwayTeamId(int awayTeamId) {
            this.awayTeamId = awayTeamId;
        }

        public Team getHomeTeam() {
            return homeTeam;
        }

        public void setHomeTeam(Team homeTeam) {
            this.homeTeam = homeTeam;
        }

        public Team getAwayTeam() {
            return awayTeam;
        }

        public void setAwayTeam(Team awayTeam) {
            this.awayTeam = awayTeam;
        }
    }
}

