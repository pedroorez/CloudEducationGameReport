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
@Table(name = "Gamelog")
public class Gamelog implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int gamelogID;
    
    @ManyToOne
    @JoinColumn(name = "subscriptionID")
    private Subscription subscription;
    
    @ManyToOne
    @JoinColumn(name = "ValueIdentificator")
    private GameTypeValue gameTypeValue;
    
    @ManyToOne
    @JoinColumn(name = "gameentryID")
    private GameEntry gameEntryID;
    
    private int matchID;
    private String dataValue;

    
    
    
    
    
    
    /************************************************/
    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public GameEntry getGameentryID() {
        return gameEntryID;
    }

    public void setGameentryID(GameEntry gameentryID) {
        this.gameEntryID = gameentryID;
    }

    public GameTypeValue getGameTypeValue() {
        return gameTypeValue;
    }

    public void setGameTypeValue(GameTypeValue gameTypeValue) {
        this.gameTypeValue = gameTypeValue;
    }

    public int getGamelogID() {
        return gamelogID;
    }

    public void setGamelogID(int gamelogID) {
        this.gamelogID = gamelogID;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }


    
}
