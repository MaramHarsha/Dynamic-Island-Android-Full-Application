package com.lock.entity;

public class LockData {
    public String id;
    public String image;
    public String name;
    public String pkg;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public String getPkg() {
        return this.pkg;
    }

    public void setPkg(String str) {
        this.pkg = str;
    }
}
