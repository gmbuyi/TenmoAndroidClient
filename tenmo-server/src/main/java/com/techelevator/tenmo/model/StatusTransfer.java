package com.techelevator.tenmo.model;

public class StatusTransfer {

    private int transferStatusId;
    private String transferStatusDesc;

    public static final int STATUS_APPROVE = 2;
    public static final int STATUS_REJECT = 3;
    public static final int STATUS_PENDING = 1;


    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }
}
