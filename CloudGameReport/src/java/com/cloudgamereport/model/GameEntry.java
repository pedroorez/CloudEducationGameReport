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
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "gameEntries")
@OnDelete(action = OnDeleteAction.CASCADE)
public class GameEntry implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int gameEntryID;
    
    @ManyToOne
    @JoinColumn(name = "gameTypeID")
    private GameType gameType;
    
    @ManyToOne
    @JoinColumn(name = "classID")
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private Classe classe;

    private String gameReference;
    private String gameName;
    
    
    
    
    
    
    /***************************************/
    public int getGameEntryID() {
        return gameEntryID;
    }

    public void setGameEntryID(int gameEntryID) {
        this.gameEntryID = gameEntryID;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classID) {
        this.classe = classID;
    }

    public String getGameReference() {
        return gameReference;
    }

    public void setGameReference(String gameReference) {
        this.gameReference = gameReference;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

   
    
}
