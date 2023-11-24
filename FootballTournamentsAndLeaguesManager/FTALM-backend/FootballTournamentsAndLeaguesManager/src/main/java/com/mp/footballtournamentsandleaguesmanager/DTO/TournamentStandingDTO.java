package com.mp.footballtournamentsandleaguesmanager.DTO;

public class TournamentStandingDTO extends StandingDTO {
    private Long tournamentId;
    private String tournamentName;
    private int groupId; // 1 - A, 2 - B, 3 - C, ...

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
