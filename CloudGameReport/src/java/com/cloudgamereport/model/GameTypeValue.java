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
    
    private String valueName;
    private String valueIdentificator;
    private String displayType;

    
    
    
    /***********************************************/
    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String DisplayType) {
        this.displayType = DisplayType;
    }
    
    public int getGametypeValueID() {
        return gametypeValueID;
    }

    public void setGametypeValueID(int gametypeValueID) {
        this.gametypeValueID = gametypeValueID;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType GameType) {
        this.gameType = GameType;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String ValueName) {
        this.valueName = ValueName;
    }

    public String getValueIdentificator() {
        return valueIdentificator;
    }

    public void setValueIdentificator(String ValueIdentificator) {
        this.valueIdentificator = ValueIdentificator;
    }
    
      
    
    
}
