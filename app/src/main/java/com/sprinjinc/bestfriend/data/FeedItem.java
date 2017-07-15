package com.sprinjinc.bestfriend.data;

public class FeedItem {
    private int id, totalComments = 0;
    private String name, status, image, timeStamp, url;
    private Object loves;
    private boolean lovedByCurrentUser = false;

    public FeedItem() {
    }

    public FeedItem(int id, String name, String image, String status,
                    String timeStamp, String url) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
        this.status = status;
        this.timeStamp = timeStamp;
        this.url = url;

        totalComments = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getLoves() {
        return loves;
    }

    public void setLoves(Object loves) {
        this.loves = loves;
    }

    public boolean isLovedByCurrentUser() {
        return lovedByCurrentUser;
    }

    public void setLovedByCurrentUser(boolean lovedByCurrentUser) {
        this.lovedByCurrentUser = lovedByCurrentUser;
    }

    public int getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(int totalComments) {
        this.totalComments = totalComments;
    }
}
