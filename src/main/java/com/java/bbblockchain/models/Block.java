package com.java.bbblockchain.models;



public class Block
{
    private String prevHash;
    private String currHash;
    private  Object object;

    public Block(String prevHash, String currHash, Object object) {
        this.prevHash = prevHash;
        this.currHash = currHash;
        this.object = object;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public String getCurrHash() {
        return currHash;
    }

    public void setCurrHash(String currHash) {
        this.currHash = currHash;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }


}
