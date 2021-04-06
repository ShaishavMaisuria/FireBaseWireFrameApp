package com.example.firebasewireframeapp;

import com.google.firebase.Timestamp;

public class Comments {

    String comments,personName,uid;
    Timestamp commentTime;
    String commentId;
    public Comments() {
    }

    public Comments(String comments, String personName, String uid, Timestamp commentTime, String commentId) {
        this.comments = comments;
        this.personName = personName;
        this.uid = uid;
        this.commentTime = commentTime;
        this.commentId = commentId;
    }

    public Comments(String comments, String personName, String uid, Timestamp commentTime) {
        this.comments = comments;
        this.personName = personName;
        this.uid = uid;
        this.commentTime = commentTime;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "comments='" + comments + '\'' +
                ", personName='" + personName + '\'' +
                ", uid='" + uid + '\'' +
                ", commentTime=" + commentTime +
                ", commentId='" + commentId + '\'' +
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
