package com.rishabh.trelloclone.payloads;


public class UpdateStatusPayLoad {

    private int tID;
    private String status;

    public int gettID() {
        return tID;
    }

    public void settID(int tID) {
        this.tID = tID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UpdateStatusPayLoad{" +
                "tID=" + tID +
                ", status='" + status + '\'' +
                '}';
    }
}
