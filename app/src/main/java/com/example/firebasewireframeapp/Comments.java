package com.example.firebasewireframeapp;

import com.google.firebase.Timestamp;

public class Comments {

    String comments,personName,uid;
    Timestamp commentTime;

    @Override
    public String toString() {
        return "Comments{" +
                "comment='" + comments + '\'' +
                ", personName='" + personName + '\'' +
                ", uid='" + uid + '\'' +
                ", commentTime=" + commentTime +
                '}';
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }
}
