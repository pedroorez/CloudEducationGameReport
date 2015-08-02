package com.cloudgamereport.model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "match")
public class Match implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int ID;
    @ManyToOne
    @JoinColumn(name = "userID")
    private Subscription jogador;
    @ManyToOne
    @JoinColumn(name = "gametypeID")
    private Activity atividade;
    
    /*****************************************/

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Subscription getJogador() {
        return jogador;
    }

    public void setJogador(Subscription jogador) {
        this.jogador = jogador;
    }

    public Activity getAtividade() {
        return atividade;
    }

    public void setAtividade(Activity atividade) {
        this.atividade = atividade;
    }
    
    

    
    
}
