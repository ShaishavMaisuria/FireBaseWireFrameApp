package com.example.firebasewireframeapp;


import com.google.firebase.Timestamp;

public class Forum {
//    {createByName=BodSmith, title=testtitle, createAt=Timestamp(seconds=1617336000, nanoseconds=0), desc=tesdesc, createdByUid=afadfafadfadfadf}
String createByName,title,desc,createByUid;
Timestamp createAt;

    @Override
    public String toString() {
        return "Forum{" +
                "createByName='" + createByName + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", createByUid='" + createByUid + '\'' +
                ", createAt=" + createAt +
                '}';
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreateByUid() {
        return createByUid;
    }

    public void setCreateByUid(String createByUid) {
        this.createByUid = createByUid;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }
}
