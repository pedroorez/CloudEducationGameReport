 package com.quizquest.model;
   
import java.io.Serializable;
import javax.persistence.*;




@Entity
@Table(name = "Questions")
public class Question implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
     private int questionID;
     private int battleID;
     private String questionText;
     private String option1;
     private String option2;
     private String option3;
     private String option4;
     private int awnser;
     
     //hibernate thing
     public Question(){}
     
    // getsetters
    public int getBattleID() {
        return battleID;
    }

    public void setBattleID(int battleID) {
        this.battleID = battleID;
    }
    
    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getAwnser() {
        return awnser;
    }

    public void setAwnser(int awnser) {
        this.awnser = awnser;
    }
     
     
}
