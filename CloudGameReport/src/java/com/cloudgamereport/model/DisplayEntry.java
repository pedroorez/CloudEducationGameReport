package com.cloudgamereport.model;

import java.util.List;

public class DisplayEntry {
    private GameTypeValue gameType;
    private List<Gamelog> gamelogList;

    
    
    
    
    
    /************************************************/
    public GameTypeValue getGameType() {
        return gameType;
    }

    public void setGameType(GameTypeValue gameType) {
        this.gameType = gameType;
    }

    public List<Gamelog> getGamelogList() {
        return gamelogList;
    }

    public void setGamelogList(List<Gamelog> gamelogList) {
        this.gamelogList = gamelogList;
    }



    
}
