package com.ESIa.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Games")
public class Game {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int GameID;
    
    private String gameName;
    private String description;
    @ManyToOne
    @JoinColumn(name = "creatorID")
    private User creator;
    private String parameters;
    
    //transient lists
    @Transient
    private List<Asset> EnemyList;
    @Transient
    private List<Asset> AnswerList;
    @Transient
    private List<Asset> BackgroundAsset;
    @Transient
    private List<Asset> PlayerAsset;

    public Game() {
    }
        
    //*****************************************************//
   
    public int getGameID() {
        return GameID;
    }

    public void setGameID(int GameID) {
        this.GameID = GameID;
    }
 
    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public List<Asset> getEnemyList() {
        return EnemyList;
    }

    public void setEnemyList(List<Asset> EnemyList) {
        this.EnemyList = EnemyList;
    }

    public List<Asset> getAnswerList() {
        return AnswerList;
    }

    public void setAnswerList(List<Asset> AnswerList) {
        this.AnswerList = AnswerList;
    }

    public List<Asset> getBackgroundAsset() {
        return BackgroundAsset;
    }

    public void setBackgroundAsset(List<Asset> BackgroundAsset) {
        this.BackgroundAsset = BackgroundAsset;
    }

    public List<Asset> getPlayerAsset() {
        return PlayerAsset;
    }

    public void setPlayerAsset(List<Asset> PlayerAsset) {
        this.PlayerAsset = PlayerAsset;
    }



  
    
}
