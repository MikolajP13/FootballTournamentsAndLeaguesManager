package com.mp.footballtournamentsandleaguesmanager.DTO;

import com.mp.footballtournamentsandleaguesmanager.model.Player;

public class PlayerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Player.Position position;
    private Player.PositionDetail positionDetail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Player.Position getPosition() {
        return position;
    }

    public void setPosition(Player.Position position) {
        this.position = position;
    }

    public Player.PositionDetail getPositionDetail() {
        return positionDetail;
    }

    public void setPositionDetail(Player.PositionDetail positionDetail) {
        this.positionDetail = positionDetail;
    }
}
