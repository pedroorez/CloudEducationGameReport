package com.cloudgamereport.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;

@Entity
@Table(name = "Gamelog")
public class Gamelog implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int gamelogID;
    
    @ManyToOne
    @JoinColumn(name = "subscriptionID")    
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private Subscription subscription;
    
    @ManyToOne
    @JoinColumn(name = "ValueIdentificator")
    private GameTypeValue gameTypeValue;
    
    @ManyToOne
    @JoinColumn(name = "gameentryID")
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private Activity gameEntryID;
    
    @ManyToOne
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private Match match;
    private String dataValue;

    /************************************************/


    public Activity getGameEntryID() {
        return gameEntryID;
    }

    public void setGameEntryID(Activity gameEntryID) {
        this.gameEntryID = gameEntryID;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Activity getGameentryID() {
        return gameEntryID;
    }

    public void setGameentryID(Activity gameentryID) {
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
