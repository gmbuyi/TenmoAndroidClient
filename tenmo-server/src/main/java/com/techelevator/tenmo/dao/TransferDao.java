package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    public boolean send(Transfer transfer);
    public boolean request(Transfer transfer);
    public List<Transfer> getTransfersList(Account account);
    public boolean approveStatus(Transfer transfer);
    public boolean rejectStatus(Transfer transfer);
    public List<Transfer> sendTransferList(long accountId);
    public List<Transfer> requestTransferList(long accountId);

    public List<Transfer> pendingRequestTransferList(long accountId);


}
