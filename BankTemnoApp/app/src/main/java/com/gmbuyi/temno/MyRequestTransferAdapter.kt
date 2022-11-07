package com.gmbuyi.temno

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gmbuyi.temno.api.models.Transfer
import com.gmbuyi.temno.api.models.TransferStatus


/**
 * Request adapter that formats
 * the Item view of the Listview depending of the request status
 *
 */

class MyRequestTransferAdapter(context: Context, resource: Int, private val list : List<Transfer>, private val currentAccountId : Long ) : ArrayAdapter<Transfer>(context,resource,list), AdapterView.OnItemClickListener {




    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Transfer {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return list[position].transferId
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = convertView
        if(view == null){
            view =LayoutInflater.from(context).inflate(R.layout.transfer_item_layout,parent, false)
        }

        val img = view?.findViewById<ImageView>(R.id.image_transfer)
        val type = view?.findViewById<TextView>(R.id.transfer_type)
        val amount = view?.findViewById<TextView>(R.id.transfer_amount)
        val info = view?.findViewById<TextView>(R.id.account_info)

        val transfer = getItem(position)

        /**
         * add Image Background to Item
         * question mark image for pending request and cancel red image for rejected
         */


        if (transfer.transferStatus.transferStatusId == TransferStatus.STATUS_PENDING){

            img!!.background = context.resources.getDrawable(R.drawable.ic_pending,null)


        } else  {

            img!!.background = context.resources.getDrawable(R.drawable.ic_cancel,null)

        }

        if (currentAccountId == transfer.fromAccount.account_id){
            info!!.text = "to: " + transfer.toAccount.user.username


        } else {
            info!!.text = "From: " + transfer.fromAccount.user.username
        }

        type!!.text = transfer.typeTransfer.transferTypeDesc
        amount!!.text = "$"+transfer.amountForTransfer.toString()





        return view!!

    }




    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


       if (getItem(position).transferStatus.transferStatusId == TransferStatus.STATUS_PENDING
           && getItem(position).fromAccount.account_id != currentAccountId ){

           StatusDialog(context,R.style.TransparentDialog,getItem(position)).show()


        }

    }


}




