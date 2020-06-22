package com.fiek.hapirri.model;

public class Comment {

    private String id;
    private String signature;
    private String comment;

    public Comment(String id, String signature, String comment){
        this.id = id;
        this.signature = signature;
        this.comment = comment;
    }

    public Comment(){}

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
