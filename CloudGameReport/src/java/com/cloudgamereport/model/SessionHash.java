package com.cloudgamereport.model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "sessions")
public class SessionHash implements Serializable {
    @Id
    private String sessionHash;
    @ManyToOne
    @JoinColumn(name = "userID")
    private User usuario;
    @ManyToOne
    @JoinColumn(name = "gametypeID")
    private GameType GameType;
    
    
    /*****************************************/
    public String getSessionHash() {
        return sessionHash;
    }

    public void setSessionHash(String sessionHash) {
        this.sessionHash = sessionHash;
    }

    public User getUser() {
        return usuario;
    }

    public void setUser(User userID) {
        this.usuario = userID;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public GameType getGameType() {
        return GameType;
    }

    public void setGameType(GameType GameEntry) {
        this.GameType = GameEntry;
    }




    
    
}
