package com.techelevator.tenmo.model;

public class TypeTransfer {

    private int transferTypeId;
    private String transferTypeDesc;

    public static final int ID_SEND = 2;
    public static final int ID_REQUEST = 1;


    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }

    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }

}
