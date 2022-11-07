package com.gmbuyi.temno

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.RadioButton
import com.gmbuyi.temno.api.models.Account
import com.gmbuyi.temno.api.models.Transfer
import com.gmbuyi.temno.api.models.TransferStatus
import com.gmbuyi.temno.api.models.TransferType
import com.gmbuyi.temno.api.services.ConsumingApi
import com.gmbuyi.temno.api.services.TokenInterceptor
import com.gmbuyi.temno.ui.request.RequestFragment
import com.gmbuyi.temno.ui.transfer.TransferFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.math.BigDecimal


/**
 * transfer dialog for creating transfer
 *
 */

@SuppressLint("InflateParams")
class TransferDialog (context: Context, style: Int, transferType: Int, account: Account, listAccount : List<Account>)  {

    private lateinit var toAccount : Account
    private var dialog: Dialog
    private  var  amountInputLayout : TextInputLayout
    private var nameInputLayout : TextInputLayout

    init {


        val builder = AlertDialog.Builder(context,style)
        val act =context as Activity
        val view=act.layoutInflater.inflate(R.layout.transfer_layout,null)
        view.setBackgroundColor(context.resources.getColor(R.color.transparent,null))

         amountInputLayout = view.findViewById(R.id.amount_input_layout)
         nameInputLayout = view.findViewById(R.id.name)

        val sendRadioButton  = view.findViewById<RadioButton>(R.id.radio_send)
        val requestRadioButton = view.findViewById<RadioButton>(R.id.radio_request)
        val confirmButton = view.findViewById<Button>(R.id.confirm_transaction_btn)
        val amount = view.findViewById<TextInputEditText>(R.id.amount_edit)
        val username = view.findViewById<AutoCompleteTextView>(R.id.auto_text)



        val adapter= ArrayAdapter(context ,R.layout.list_account_layout,R.id.username_item,getListOfUsername(listAccount))
        username.threshold = 2
        username.setAdapter(adapter)



        username.setOnItemClickListener { parent, _, position, _ ->
           val value: String =  parent.getItemAtPosition(position).toString()
            username.setText(value)
            toAccount = listAccount[position]
        }


        var desc = TransferType.DESC_SEND
        if (transferType == TransferType.ID_SEND){
            sendRadioButton.toggle()
        } else {
            requestRadioButton.toggle()
            desc = TransferType.DESC_REQUEST
        }



        sendRadioButton.isEnabled = false
        requestRadioButton.isEnabled = false




        confirmButton.setOnClickListener {

            val amountInNumber = BigDecimal(amount.text.toString())
            if (username.text.isNotEmpty() && amountInNumber > BigDecimal(0)){
                val progress = LoadingProgressDialog(context,R.style.TransparentDialog).show()
                val transfer = createTransfer(transferType,desc, toAccount, account, amountInNumber)
                ConsumingApi(context, TokenInterceptor(context))
                   .createTransfer(transfer,progress,this)


            } else {
                  setError()

            }




        }






        builder.setView(view)
        dialog = builder.create()
        dialog.setOnCancelListener {

            val fragment = if (transferType == TransferType.ID_SEND) TransferFragment.newInstance()
                          else RequestFragment.newInstance()

            (context as AccountActivity).supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main,
                fragment
            ).commit()

        }




    }

    fun show(){
        dialog.show()
    }

   fun cancel(){
        dialog.cancel()
    }


    // setting the error message in the InputTextLayout
    fun setError(){

        nameInputLayout.error = "username not found"
        amountInputLayout.error = " or lower balance"

    }


    // generating list of username from list of account
    private fun getListOfUsername(accounts : List<Account>): ArrayList<String> {
        val listUsername = ArrayList<String>()
        for( account in accounts){
            listUsername.add(account.user.username)

        }

        return listUsername

    }



    // Creating transfer Object
    private fun createTransfer(type: Int, desc: String, toAccount: Account, fromAccount: Account, amount : BigDecimal) : Transfer{



        val transferType = TransferType(type, desc)
        val transferStatus =  if (type == TransferType.ID_SEND) TransferStatus(TransferStatus.STATUS_APPROVE, "")
                              else TransferStatus(TransferStatus.STATUS_PENDING, "")


        return Transfer(0, fromAccount,toAccount, transferStatus,transferType, amount)


    }






}