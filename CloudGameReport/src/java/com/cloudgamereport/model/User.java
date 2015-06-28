package com.cloudgamereport.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "Users")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class User implements Serializable {
    
    @JsonIgnore
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int userID;
    @JsonIgnore
    private String nickname;
    @JsonIgnore
    private String password;
    
    private String fullName;
    
    
    /**************************************/
    
    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
