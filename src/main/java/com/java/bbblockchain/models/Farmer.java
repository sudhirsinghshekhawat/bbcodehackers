package com.java.bbblockchain.models;

import com.java.bbblockchain.common.Utils;
import io.vertx.core.json.JsonObject;

public class Farmer
{
    private int id;
    private String place;
    private String name;
    private String address;
    private String image;
    private String phone;
    private Boolean isVerified;
    private String hash;


    public Farmer(int id, String place, String name, String address, String image, String phone, Boolean isVerified) {
        this.id = id;
        this.place = place;
        this.name = name;
        this.address = address;
        this.image = image;
        this.phone = phone;
        this.isVerified = isVerified;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedhash = Utils.applySha256(
                id +
                        place +
                        name
        );
        return calculatedhash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public JsonObject toJsonObject() {
        return JsonObject.mapFrom(this);
    }

    @Override
    public String toString() {
        return toJsonObject().toString();
    }
}
