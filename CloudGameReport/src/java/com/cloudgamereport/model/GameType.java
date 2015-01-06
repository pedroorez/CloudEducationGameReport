package com.cloudgamereport.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   
@Entity
@Table(name = "gametypes")
public class GameType implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int gametypeID;
    private String gameTypeName;

    
    
    
    
    /**********************************/
    public int getGametypeID() {
        return gametypeID;
    }

    public void setGameTypeID(int gametypeID) {
        this.gametypeID = gametypeID;
    }

    public String getGametypeName() {
        return gameTypeName;
    }

    public void setGameTypeName(String gametypeName) {
        this.gameTypeName = gametypeName;
    }
    
    
}
