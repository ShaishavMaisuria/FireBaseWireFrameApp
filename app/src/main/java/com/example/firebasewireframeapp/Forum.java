package com.example.firebasewireframeapp;


import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class Forum {
//    {createByName=BodSmith, title=testtitle, createAt=Timestamp(seconds=1617336000, nanoseconds=0), desc=tesdesc, createdByUid=afadfafadfadfadf}
String createByName,title,desc,createByUid;
Timestamp createAt;
String forumId;
ArrayList<String> likedBy;

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public Forum(String createByName, String title, String desc, String createByUid, Timestamp createAt, ArrayList<String> likedBy) {
        this.createByName = createByName;
        this.title = title;
        this.desc = desc;
        this.createByUid = createByUid;
        this.createAt = createAt;
        this.forumId = forumId;
        this.likedBy = likedBy;
    }
    //    public Forum(String createByName, String title, String desc, String createByUid, Timestamp createAt) {
//        this.createByName = createByName;
//        this.title = title;
//        this.desc = desc;
//        this.createByUid = createByUid;
//        this.createAt = createAt;
//    }

    public Forum() {
    }

    public ArrayList<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(ArrayList<String> likedBy) {
        this.likedBy = likedBy;
    }

    @Override
    public String toString() {
        return "Forum{" +
                "createByName='" + createByName + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", createByUid='" + createByUid + '\'' +
                ", createAt=" + createAt +
                ", forumId='" + forumId + '\'' +
                ", likedBy=" + likedBy +
                '}';
    }
//    @Override
//    public String toString() {
//        return "Forum{" +
//                "createByName='" + createByName + '\'' +
//                ", title='" + title + '\'' +
//                ", desc='" + desc + '\'' +
//                ", createByUid='" + createByUid + '\'' +
//                ", createAt=" + createAt +
//                '}';
//    }

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
