package com.cloudgamereport.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

   
@Entity
@Table(name = "gameTypeValues")
public class GameTypeValue implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int gametypeValueID;
    
    @ManyToOne
    @JoinColumn(name = "gametypeID")
    private GameType gameType;
    
    private String paramName;
    private String paramIdentificator;
    private String paramType;

    
    
    
    
    public int getGametypeValueID() {
        return gametypeValueID;
    }

    public void setGametypeValueID(int gametypeValueID) {
        this.gametypeValueID = gametypeValueID;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamIdentificator() {
        return paramIdentificator;
    }

    public void setParamIdentificator(String paramIdentificator) {
        this.paramIdentificator = paramIdentificator;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    
}
