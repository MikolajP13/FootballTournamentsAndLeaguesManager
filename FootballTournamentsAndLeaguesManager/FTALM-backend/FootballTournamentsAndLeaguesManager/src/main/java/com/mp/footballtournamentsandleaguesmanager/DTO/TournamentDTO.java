package com.mp.footballtournamentsandleaguesmanager.DTO;

public class TournamentDTO {
    private  Long id;
    private String tournamentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }
}
