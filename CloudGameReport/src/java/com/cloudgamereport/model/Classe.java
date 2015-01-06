package com.cloudgamereport.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "classes")
public class Classe implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int classID;
    
    @ManyToOne
    @JoinColumn(name = "userID")
    private User professor;
    
    private String className;
    private String classDescription;

   
    
    /**************************************/
    
    
    public User getProfessor() {
        return professor;
    }

    public void setProfessor(User userID) {
        this.professor = userID;
    }


    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }
    
    
    
}
