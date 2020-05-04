package com.ama.lax.models;

public class People {

    private String id;
    private String name;
    private String desc;
    private String img_url;

    public People() {

    }

    public People(String id, String name, String desc, String img_url) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.img_url = img_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
