package com.ESIa.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonIgnore;


@Entity
@Table(name = "Hash")
public class Hash implements Serializable {
    
    @Id
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int hashID;
    private String hashstring;
    private User owneruser;
    @Transient
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

    public String getHash() {
        return hashstring;
    }

    public void setHash(String hash) {
        this.hashstring = hash;
    }

    public User getUser() {
        return owneruser;
    }

    public void setUser(User user) {
        this.owneruser = user;
    }

    public int getHashID() {
        return hashID;
    }

    public void setHashID(int hashID) {
        this.hashID = hashID;
    }
    
    
    
    
}
