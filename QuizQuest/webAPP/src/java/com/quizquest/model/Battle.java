
package com.quizquest.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "battles")
public class Battle implements Serializable {
    
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private int battleID;
   private String battleTitle;
   private String battleDescription;

   
    //hibernate thing
     public Battle(){}

    public int getBattleID() {
        return battleID;
    }

    public void setBattleID(int battleID) {
        this.battleID = battleID;
    }

    public String getBattleTitle() {
        return battleTitle;
    }

    public void setBattleTitle(String battleTitle) {
        this.battleTitle = battleTitle;
    }

    public String getBattleDescription() {
        return battleDescription;
    }

    public void setBattleDescription(String battleDescription) {
        this.battleDescription = battleDescription;
    }
   

   
    
}
