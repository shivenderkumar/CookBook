package com.shivenderkumar.kitchenbook.model;

public class Comment {
    private String commentator_emailid;             //to details of comentator
    private String time;                            //change string to time
    private String comment;                         //maybe Sentences or paragraph

    public Comment(String commentator_emailid, String time, String comment) {
        this.commentator_emailid = commentator_emailid;
        this.time = time;
        this.comment = comment;
    }

    public String getCommentator_emailid() {
        return commentator_emailid;
    }

    public void setCommentator_emailid(String commentator_emailid) {
        this.commentator_emailid = commentator_emailid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
