/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ESIa.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Table(name = "Assets")
public class Asset {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String assetText;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "gameid")
    @OnDelete(action = CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Game game;
    private String assetType; 
    @ManyToOne
    @JoinColumn(name = "imagefile")
    private ImageFile imageFile;
    @ManyToOne
    @JoinColumn(name = "ansfile")
    private Asset rightans;
    /****************************/
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssetText() {
        return assetText;
    }

    public void setAssetText(String assetText) {
        this.assetText = assetText;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public ImageFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(ImageFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public Asset getRightans() {
        return rightans;
    }

    public void setRightans(Asset rightans) {
        this.rightans = rightans;
    }
    
}
