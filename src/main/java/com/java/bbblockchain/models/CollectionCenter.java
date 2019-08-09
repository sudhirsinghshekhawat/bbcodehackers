package com.java.bbblockchain.models;

import com.java.bbblockchain.common.Utils;
import io.vertx.core.json.JsonObject;

public class CollectionCenter
{
    private int id;
    private int skuid;
    private int quantity ;
    private Double price;
    private String proof;
    private String crateId;
    private String hash;


    public CollectionCenter(int id, int skuid, int quantity, Double price, String proof, String crateId) {
        this.id = id;
        this.skuid = skuid;
        this.quantity = quantity;
        this.price = price;
        this.proof = proof;
        this.crateId = crateId;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedhash = Utils.applySha256(
                id +
                        skuid +
                        quantity +
                        price+
                        proof
                        +crateId
        );
        return calculatedhash;
    }






    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSkuid() {
        return skuid;
    }

    public void setSkuid(int skuid) {
        this.skuid = skuid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public String getCrateId() {
        return crateId;
    }

    public void setCrateId(String crateId) {
        this.crateId = crateId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public JsonObject toJsonObject() {
        return JsonObject.mapFrom(this);
    }

    @Override
    public String toString() {
        return toJsonObject().toString();
    }


}
