package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.StatusTransfer;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TypeTransfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transfers")
@PreAuthorize("isAuthenticated()")
public class TransferController {

 TransferDao transferDao;
 AccountDao accountDao;


    public TransferController(TransferDao transferDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    @GetMapping
    public List<Transfer> list(@RequestBody Account account){
    return  transferDao.getTransfersList(account);
    }


    @GetMapping("/listSent/{accountId}")
    public List<Transfer> getListSentTransfers(@PathVariable long accountId){
        return  transferDao.sendTransferList(accountId);
    }

    @GetMapping("/listRequest/{accountId}")
    public List<Transfer> getListRequestTransfers(@PathVariable long accountId){
        return  transferDao.requestTransferList(accountId);
    }

    @GetMapping("/listPendingRequest/{accountId}")
    public List<Transfer> getPendingListRequestTransfers(@PathVariable long accountId){
        return  transferDao.pendingRequestTransferList(accountId);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean makeTransfer(@RequestBody Transfer transfer){

         var value = false;

        if (transfer.getTypeTransfer().getTransferTypeId() == TypeTransfer.ID_SEND && transferDao.send(transfer)) {

            accountDao.updateAccount(decreasedBalanceAccount(transfer.getFromAccount(),
                    transfer.getAmountForTransfer()));

            accountDao.updateAccount(increasedBalanceAccount(transfer.getToAccount(),
                                      transfer.getAmountForTransfer()));

            value = true;

        } else if (transfer.getTypeTransfer().getTransferTypeId() == TypeTransfer.ID_REQUEST){

            value = transferDao.request(transfer);

        }

        return value;

    }

   @PutMapping
   @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean updateStatus(@RequestBody Transfer transfer){

         var value = false;
        if ( transfer.getTransferStatus().getTransferStatusId()== StatusTransfer.STATUS_APPROVE
                && transferDao.approveStatus(transfer)){
            accountDao.updateAccount(decreasedBalanceAccount(transfer.getToAccount(),
                    transfer.getAmountForTransfer()));
            accountDao.updateAccount(increasedBalanceAccount(transfer.getFromAccount(),
                    transfer.getAmountForTransfer()));
            value = true;
        } else if (transfer.getTransferStatus().getTransferStatusId() == StatusTransfer.STATUS_REJECT) {

            value = transferDao.rejectStatus(transfer);

        }
        return value;
   }


    private Account increasedBalanceAccount(Account account, BigDecimal gap){
        BigDecimal toAccountBalance = accountDao.getAccount(account.getUser().getId()).getBalance()
                .add(gap);
        account.setBalance(toAccountBalance);
        return account;

    }

    private Account decreasedBalanceAccount(Account account, BigDecimal gap){
        BigDecimal toAccountBalance = accountDao.getAccount(account.getUser().getId()).getBalance()
                .subtract(gap);
        account.setBalance(toAccountBalance);
        return account;

    }

}
