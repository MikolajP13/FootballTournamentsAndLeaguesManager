package com.mp.footballtournamentsandleaguesmanager.DTO;

public class TournamentTeamDTO {
    private Long teamId;
    private Long tournamentId;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }
}
