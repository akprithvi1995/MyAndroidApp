package com.example.slayerevenge.travelandentertainment;
//private String [] reviewText;
//private String [] authorName;
//private String [] reviewRating;
//private String [] authorUrl;
//private String [] profileUrl;
//private String [] time;

public class ReviewData {
    private String reviewText, authorName, reviewRating,authorUrl,profileUrl,time;
    private int deford;
    private long epochtime;

    public ReviewData() {
    }

    public ReviewData(String reviewText, String authorName, String reviewRating,String authorUrl,String profileUrl,String time,int deford) {
        this.authorName=authorName;
        this.reviewRating=reviewRating;
        this.reviewText=reviewText;
        this.time=time;
        this.authorUrl=authorUrl;
        this.profileUrl=profileUrl;
        this.deford=deford;

    }

    public String getAuthorName() {
        return authorName;
    }

    public int getDeford() {
        return deford;
    }

    public void setDeford(int deford) {
        this.deford = deford;
    }

    public long getEpochtime() {
        return epochtime;
    }

    public void setEpochtime(long epochtime) {
        this.epochtime = epochtime;
    }

    public void setAuthorName(String name) {
        this.authorName = name;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String name) {
        this.authorUrl = name;
    }
    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String name) {
        this.reviewText = name;
    }

    public String getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(String name) {
        this.reviewRating = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String name) {
        this.time= name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String name) {
        this.profileUrl = name;
    }




}
