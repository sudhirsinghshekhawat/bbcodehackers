package com.java.bbblockchain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.java.bbblockchain.common.Utils;
import io.vertx.core.json.JsonObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer
{
    private int id;
    private String name;
    private String addressId;
    private String hash;

    public Customer()
    {
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedhash = Utils.applySha256(
                id +
                        name +
                        addressId
        );
        return calculatedhash;
    }




    public Customer(int id, String name, String addressId, String hash) {
        this.id = id;
        this.name = name;
        this.addressId = addressId;
        this.hash = hash;
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

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
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

    public static Customer fromMemberRequestJsonObject(JsonObject obj) {
        return obj.mapTo(Customer.class);
    }
}
