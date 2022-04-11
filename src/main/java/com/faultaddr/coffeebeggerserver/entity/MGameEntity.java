package com.faultaddr.coffeebeggerserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "game", schema = "CoffeeBegger")
public class MGameEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "participant", nullable = false, length = -1)
    private String participant;
    @Basic
    @Column(name = "result", nullable = true, length = 255)
    private String result;
    @Basic
    @Column(name = "gameId", nullable = true, length = 255)
    private String gameId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
