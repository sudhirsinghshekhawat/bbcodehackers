package com.java.bbblockchain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.java.bbblockchain.common.Utils;
import io.vertx.core.json.JsonObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistributionCenter
{
    private  int dc_id;
    private int grn_id;
    private int picker_id;
    private String vehicle_id;
    private String proof;
    private String hash;

    public  DistributionCenter()
    {
        this.hash = calculateHash();

    }

    public DistributionCenter(int dc_id, int grn_id, int picker_id, String vehicle_id, String proof) {
        this.dc_id = dc_id;
        this.grn_id = grn_id;
        this.picker_id = picker_id;
        this.vehicle_id = vehicle_id;
        this.proof = proof;
        this.hash = calculateHash();
    }

    public String calculateHash()
    {
        String calculatedhash = Utils.applySha256(
                dc_id +
                        grn_id +
                        picker_id +
                        vehicle_id+
                        proof
        );
        return calculatedhash;
    }

    public int getDc_id() {
        return dc_id;
    }


    public void setDc_id(int dc_id) {
        this.dc_id = dc_id;
    }

    public int getGrn_id() {
        return grn_id;
    }

    public void setGrn_id(int grn_id) {
        this.grn_id = grn_id;
    }

    public int getPicker_id() {
        return picker_id;
    }

    public void setPicker_id(int picker_id) {
        this.picker_id = picker_id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
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

    public static DistributionCenter fromMemberRequestJsonObject(JsonObject obj) {
        return obj.mapTo(DistributionCenter.class);
    }
}
