package com.gmbuyi.temno

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.widget.Button
import com.gmbuyi.temno.api.models.Transfer
import com.gmbuyi.temno.api.models.TransferStatus
import com.gmbuyi.temno.api.services.ConsumingApi
import com.gmbuyi.temno.api.services.TokenInterceptor
import com.gmbuyi.temno.ui.request.RequestFragment


/**
 * status dialog for approving or rejecting selected request
 */

class StatusDialog(context: Context, style: Int, transfer: Transfer) {
    private  var dialog : Dialog

    init {

        val builder = AlertDialog.Builder (context, style)
        val act =context as Activity
        val view=act.layoutInflater.inflate(R.layout.status_dialog,null)
        view.setBackgroundColor(context.resources.getColor(R.color.transparent,null))

        val btnApprove = view.findViewById<Button>(R.id.approve)
        val btnReject = view.findViewById<Button>(R.id.reject)

        btnReject.setOnClickListener{

            transfer.transferStatus.transferStatusId = TransferStatus.STATUS_REJECT
            transfer.transferStatus.transferStatusDesc = TransferStatus.DESC_REJECT

            updateTransfer(context,transfer)


        }

        btnApprove.setOnClickListener {
            transfer.transferStatus.transferStatusId = TransferStatus.STATUS_APPROVE
            transfer.transferStatus.transferStatusDesc = TransferStatus.DESC_APPROVE
            val updateTransfer = Transfer(transfer.transferId,
                transfer.toAccount,
                transfer.fromAccount,
                transfer.transferStatus,
                transfer.typeTransfer,
                transfer.amountForTransfer)
            updateTransfer(context,updateTransfer)


        }


        builder.setView(view)
        dialog = builder.create()


        /**
         * refresh  Request Fragment
         * when dialog dismiss
         */

        dialog.setOnCancelListener {

            (context as AccountActivity).supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main,
                RequestFragment.newInstance()
            ).commit()

        }



    }


    fun show(){
        dialog.show()


    }


   private fun cancel(){
       dialog.cancel()
   }




    // updating the transfer by consuming the api
    private fun updateTransfer(context: Context, transfer : Transfer ) {
            val progressDialog = LoadingProgressDialog(context,R.style.TransparentDialog).show()
            ConsumingApi(context , TokenInterceptor(context))
                .updateStatus(transfer,progressDialog)
            cancel()




    }



}