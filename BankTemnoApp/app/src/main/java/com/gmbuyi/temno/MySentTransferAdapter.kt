package com.gmbuyi.temno

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gmbuyi.temno.api.models.Transfer

/**
 * transfer adapter that formats
 * the Item view of the Listview depending of the sender or receiver of the transfer
 *
 */

class MySentTransferAdapter(context: Context, resource: Int, private val list : List<Transfer>, val currentAccountId: Long) : ArrayAdapter<Transfer>(context,resource,list) {




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


          if (transfer.fromAccount.account_id == currentAccountId){

                info!!.text = "to: " + transfer.toAccount.user.username
              img!!.background = context.resources.getDrawable(R.drawable.ic_transfer_receive,null)
                amount!!.text = "- $"+transfer.amountForTransfer.toString()
                amount.setTextColor(context.resources.getColor(R.color.red,null))


            } else{

                info!!.text = "From: " + transfer.fromAccount.user.username
                img!!.background = context.resources.getDrawable(R.drawable.ic_transfer_send,null)
                amount!!.text = "+ $"+transfer.amountForTransfer.toString()
                amount.setTextColor(context.resources.getColor(android.R.color.holo_green_light,null))
            }


         type!!.text = transfer.typeTransfer.transferTypeDesc



        return view!!

    }


}




