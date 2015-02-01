package com.ESIa.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;

@Entity
@Table(name = "ImageFiles")
public class ImageFile {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int imageFileID;
    @JsonIgnore
    private String imageType;
    @JsonIgnore
    @ManyToOne
    private User ownerUser;
    @Transient
    private String imgurl;

    /*****************************************************/
    
    
    public int getImageFileID() {
        return imageFileID;
    }

    public void setImageFileID(int imageFileID) {
        this.imageFileID = imageFileID;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getFilename() {
        return imageType + "_" + imageFileID + ".jpg";
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public String getImgurl(){
        imgurl = "/ESIa/res/uploadedimgs/" + imageType +"_" +imageFileID +".jpg";
        return imgurl;
    }
}