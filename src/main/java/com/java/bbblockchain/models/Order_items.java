package com.java.bbblockchain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.java.bbblockchain.common.Utils;
import io.vertx.core.json.JsonObject;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order_items
{
    int sku_id;
    int order_id;
    int quantity;
    float price;
    private  String cratehash;
    private Date createDate;
    private  String hash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Order_items() {
        this.hash = calculateHash();

    }

    public String calculateHash() {
        String calculatedhash = Utils.applySha256(
                sku_id +
                        order_id +
                        quantity+price+cratehash
        );
        return calculatedhash;
    }


    public int getSku_id() {
        return sku_id;
    }

    public void setSku_id(int sku_id) {
        this.sku_id = sku_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCratehash() {
        return cratehash;
    }

    public void setCratehash(String cratehash) {
        this.cratehash = cratehash;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return toJsonObject().toString();
    }

    public JsonObject toJsonObject() {
        return JsonObject.mapFrom(this);
    }

    public static Order_items fromMemberRequestJsonObject(JsonObject obj) {
        return obj.mapTo(Order_items.class);
    }
}
