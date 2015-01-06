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
@Table(name = "subscriptions")
public class Subscription implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int subscriptionID;
    
    @ManyToOne
    @JoinColumn(name = "userID")
    private User playerID;
    
    @ManyToOne
    @JoinColumn(name = "classID")
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private Classe classe;
    
    private String status;

 
    
    
    
    
 
    /*******************************************/
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
             
    public int getSubscriptionID() {
        return subscriptionID;
    }

    public void setSubscriptionID(int subscriptionID) {
        this.subscriptionID = subscriptionID;
    }

    public User getPlayerID() {
        return playerID;
    }

    public void setPlayerID(User playerID) {
        this.playerID = playerID;
    }

    public Classe getClassID() {
        return classe;
    }

    public void setClassID(Classe classID) {
        this.classe = classID;
    }


    
    
}
